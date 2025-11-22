#!/bin/bash

# ========================================
# Auth Boot Starter API 测试脚本
# ========================================

# 配置
BASE_URL="${BASE_URL:-http://localhost:8080}"
TENANT_ID="DEFAULT"

# 颜色定义
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 测试结果统计
TOTAL=0
SUCCESS=0
FAILED=0

# 测试函数
test_api() {
    local name=$1
    local method=$2
    local url=$3
    local data=$4
    local token=$5
    
    TOTAL=$((TOTAL + 1))
    echo -e "\n${YELLOW}[Test $TOTAL]${NC} $name"
    echo "  URL: $method $url"
    
    if [ -n "$data" ]; then
        echo "  Data: $data"
    fi
    
    # 构建curl命令
    if [ "$method" = "GET" ]; then
        if [ -n "$token" ]; then
            response=$(curl -s -w "\n%{http_code}" -X GET "$url" \
                -H "Authorization: Bearer $token")
        else
            response=$(curl -s -w "\n%{http_code}" -X GET "$url")
        fi
    else
        if [ -n "$token" ]; then
            response=$(curl -s -w "\n%{http_code}" -X $method "$url" \
                -H "Content-Type: application/json" \
                -H "Authorization: Bearer $token" \
                -d "$data")
        else
            response=$(curl -s -w "\n%{http_code}" -X $method "$url" \
                -H "Content-Type: application/json" \
                -d "$data")
        fi
    fi
    
    # 分离响应体和状态码
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    
    # 判断结果
    if [ "$http_code" -ge 200 ] && [ "$http_code" -lt 300 ]; then
        echo -e "  ${GREEN}✅ PASS${NC} (HTTP $http_code)"
        SUCCESS=$((SUCCESS + 1))
        echo "  Response: $body" | head -c 200
        echo "..."
    else
        echo -e "  ${RED}❌ FAIL${NC} (HTTP $http_code)"
        FAILED=$((FAILED + 1))
        echo "  Response: $body"
    fi
    
    # 返回响应体（用于提取数据）
    echo "$body"
}

# 打印标题
print_title() {
    echo -e "\n${YELLOW}========================================${NC}"
    echo -e "${YELLOW}  $1${NC}"
    echo -e "${YELLOW}========================================${NC}"
}

# 开始测试
echo "========================================="
echo "  Auth Boot Starter API 测试"
echo "========================================="
echo "Base URL: $BASE_URL"
echo "Tenant ID: $TENANT_ID"
echo ""

# ========================================
# 一、认证接口测试
# ========================================
print_title "一、认证接口测试"

# 1.1 用户登录
login_response=$(test_api \
    "用户登录" \
    "POST" \
    "$BASE_URL/api/auth/login" \
    "{\"username\":\"admin\",\"password\":\"admin123\",\"tenantId\":\"$TENANT_ID\"}")

# 提取Token
ACCESS_TOKEN=$(echo "$login_response" | grep -o '"accessToken":"[^"]*"' | cut -d'"' -f4)
REFRESH_TOKEN=$(echo "$login_response" | grep -o '"refreshToken":"[^"]*"' | cut -d'"' -f4)

if [ -z "$ACCESS_TOKEN" ]; then
    echo -e "${RED}错误: 无法获取AccessToken，后续测试将失败${NC}"
    echo "请确保："
    echo "1. 服务器已启动 (mvn spring-boot:run)"
    echo "2. 数据库已初始化 (scripts/init.sql + test-data.sql)"
    echo "3. 测试用户存在 (admin/admin123)"
    exit 1
else
    echo -e "${GREEN}AccessToken 已获取: ${ACCESS_TOKEN:0:50}...${NC}"
fi

# 1.2 获取当前用户信息
test_api \
    "获取当前用户信息" \
    "GET" \
    "$BASE_URL/api/auth/info" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 1.3 刷新Token
test_api \
    "刷新Token" \
    "POST" \
    "$BASE_URL/api/auth/refresh" \
    "{\"refreshToken\":\"$REFRESH_TOKEN\"}" \
    "" > /dev/null

# ========================================
# 二、用户管理接口测试
# ========================================
print_title "二、用户管理接口测试"

# 2.1 分页查询用户列表
test_api \
    "分页查询用户列表" \
    "GET" \
    "$BASE_URL/api/user/list?pageNum=1&pageSize=10" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 2.2 创建用户
timestamp=$(date +%s)
create_user_response=$(test_api \
    "创建用户" \
    "POST" \
    "$BASE_URL/api/user" \
    "{\"username\":\"testuser_$timestamp\",\"password\":\"test123456\",\"nickname\":\"测试用户\",\"email\":\"test@example.com\",\"phone\":\"13800138000\",\"status\":1,\"userType\":1,\"tenantId\":\"$TENANT_ID\"}" \
    "$ACCESS_TOKEN")

# 提取新创建的用户ID
NEW_USER_ID=$(echo "$create_user_response" | grep -o '"data":[0-9]*' | grep -o '[0-9]*')

if [ -n "$NEW_USER_ID" ]; then
    echo -e "${GREEN}新用户ID: $NEW_USER_ID${NC}"
    
    # 2.3 查询用户详情
    test_api \
        "查询用户详情" \
        "GET" \
        "$BASE_URL/api/user/$NEW_USER_ID" \
        "" \
        "$ACCESS_TOKEN" > /dev/null
    
    # 2.4 更新用户
    test_api \
        "更新用户" \
        "PUT" \
        "$BASE_URL/api/user/$NEW_USER_ID" \
        "{\"nickname\":\"测试用户-已更新\"}" \
        "$ACCESS_TOKEN" > /dev/null
    
    # 2.5 修改用户状态
    test_api \
        "修改用户状态" \
        "PUT" \
        "$BASE_URL/api/user/$NEW_USER_ID/status?status=0" \
        "" \
        "$ACCESS_TOKEN" > /dev/null
    
    # 2.6 删除用户
    test_api \
        "删除用户" \
        "DELETE" \
        "$BASE_URL/api/user/$NEW_USER_ID" \
        "" \
        "$ACCESS_TOKEN" > /dev/null
fi

# ========================================
# 三、角色管理接口测试
# ========================================
print_title "三、角色管理接口测试"

# 3.1 查询所有角色
test_api \
    "查询所有角色" \
    "GET" \
    "$BASE_URL/api/role/all" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 3.2 分页查询角色
test_api \
    "分页查询角色" \
    "GET" \
    "$BASE_URL/api/role/list?pageNum=1&pageSize=10" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 3.3 创建角色
timestamp=$(date +%s)
create_role_response=$(test_api \
    "创建角色" \
    "POST" \
    "$BASE_URL/api/role" \
    "{\"roleCode\":\"TEST_ROLE_$timestamp\",\"roleName\":\"测试角色\",\"description\":\"用于测试\",\"status\":1,\"sort\":100}" \
    "$ACCESS_TOKEN")

NEW_ROLE_ID=$(echo "$create_role_response" | grep -o '"data":[0-9]*' | grep -o '[0-9]*')

if [ -n "$NEW_ROLE_ID" ]; then
    echo -e "${GREEN}新角色ID: $NEW_ROLE_ID${NC}"
    
    # 3.4 查询角色详情
    test_api \
        "查询角色详情" \
        "GET" \
        "$BASE_URL/api/role/$NEW_ROLE_ID" \
        "" \
        "$ACCESS_TOKEN" > /dev/null
    
    # 3.5 删除角色
    test_api \
        "删除角色" \
        "DELETE" \
        "$BASE_URL/api/role/$NEW_ROLE_ID" \
        "" \
        "$ACCESS_TOKEN" > /dev/null
fi

# ========================================
# 四、权限管理接口测试
# ========================================
print_title "四、权限管理接口测试"

# 4.1 查询所有权限
test_api \
    "查询所有权限" \
    "GET" \
    "$BASE_URL/api/permission/all" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 4.2 查询用户权限
test_api \
    "查询用户权限" \
    "GET" \
    "$BASE_URL/api/permission/user/1" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 4.3 查询用户权限编码
test_api \
    "查询用户权限编码" \
    "GET" \
    "$BASE_URL/api/permission/user/1/codes" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# ========================================
# 五、菜单管理接口测试
# ========================================
print_title "五、菜单管理接口测试"

# 5.1 查询菜单树
test_api \
    "查询菜单树" \
    "GET" \
    "$BASE_URL/api/menu/tree" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 5.2 查询用户菜单树
test_api \
    "查询用户菜单树" \
    "GET" \
    "$BASE_URL/api/menu/user/1" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# 5.3 查询所有菜单
test_api \
    "查询所有菜单" \
    "GET" \
    "$BASE_URL/api/menu/list" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# ========================================
# 六、登出测试
# ========================================
print_title "六、登出测试"

test_api \
    "用户登出" \
    "POST" \
    "$BASE_URL/api/auth/logout" \
    "" \
    "$ACCESS_TOKEN" > /dev/null

# ========================================
# 测试总结
# ========================================
echo ""
echo "========================================="
echo "  测试总结"
echo "========================================="
echo -e "总计: $TOTAL"
echo -e "${GREEN}成功: $SUCCESS${NC}"
echo -e "${RED}失败: $FAILED${NC}"
echo ""

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}🎉 所有测试通过！${NC}"
    exit 0
else
    echo -e "${RED}⚠️  有测试失败，请检查日志${NC}"
    exit 1
fi

