# API接口文档

## 📡 接口规范

### 基础信息

- **Base URL**: `http://localhost:8080/api/v1`
- **协议**: HTTP/HTTPS
- **数据格式**: JSON
- **字符编码**: UTF-8

### 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1698758232000,
  "traceId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 错误码定义

| 错误码 | 说明 | HTTP状态码 |
|-------|------|-----------|
| 200 | 成功 | 200 |
| 400 | 请求参数错误 | 400 |
| 401 | 未认证 | 401 |
| 403 | 无权限 | 403 |
| 404 | 资源不存在 | 404 |
| 500 | 服务器错误 | 500 |
| 1001 | 用户名或密码错误 | 400 |
| 1002 | Token无效 | 401 |
| 1003 | Token已过期 | 401 |
| 1004 | 用户已被禁用 | 403 |
| 1005 | 用户已被锁定 | 403 |
| 2001 | 用户不存在 | 404 |
| 2002 | 用户名已存在 | 400 |
| 2003 | 邮箱已存在 | 400 |
| 2004 | 手机号已存在 | 400 |
| 3001 | 无权限访问 | 403 |
| 3002 | 角色不存在 | 404 |
| 4001 | 租户不存在 | 404 |
| 4002 | 租户已过期 | 403 |

### 请求头

```
Authorization: Bearer {access_token}
X-Tenant-Id: {tenant_id}
Content-Type: application/json
```

## 🔐 认证接口

### 1. 用户登录

**接口**: `POST /auth/login`

**请求示例**:
```json
{
  "username": "admin",
  "password": "admin123",
  "loginType": 1,
  "captcha": "1234",
  "captchaKey": "uuid-key"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIs...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
    "tokenType": "Bearer",
    "expiresIn": 7200,
    "userInfo": {
      "userId": 1,
      "username": "admin",
      "nickname": "超级管理员",
      "avatar": "http://...",
      "roles": ["SUPER_ADMIN"],
      "permissions": ["*:*:*"]
    }
  }
}
```

### 2. 刷新Token

**接口**: `POST /auth/refresh`

**请求示例**:
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIs...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
    "expiresIn": 7200
  }
}
```

### 3. 用户登出

**接口**: `POST /auth/logout`

**请求头**:
```
Authorization: Bearer {access_token}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "登出成功"
}
```

### 4. 获取当前用户信息

**接口**: `GET /auth/userinfo`

**请求头**:
```
Authorization: Bearer {access_token}
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "userId": 1,
    "username": "admin",
    "nickname": "管理员",
    "email": "admin@example.com",
    "phone": "13800138000",
    "avatar": "http://...",
    "roles": ["ADMIN"],
    "permissions": ["user:read", "user:write"]
  }
}
```

### 5. 获取验证码

**接口**: `GET /auth/captcha`

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "captchaKey": "uuid-key",
    "captchaImage": "data:image/png;base64,iVBORw0KG...",
    "expiresIn": 300
  }
}
```

## 👤 用户管理接口

### 1. 用户列表

**接口**: `GET /user/list`

**请求参数**:
```
username: string (可选)
status: int (可选)
pageNum: int (默认1)
pageSize: int (默认10)
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "total": 100,
    "list": [
      {
        "userId": 1,
        "username": "admin",
        "nickname": "管理员",
        "email": "admin@example.com",
        "phone": "13800138000",
        "status": 1,
        "createTime": "2024-01-01 00:00:00"
      }
    ]
  }
}
```

### 2. 创建用户

**接口**: `POST /user/create`

**请求示例**:
```json
{
  "username": "zhangsan",
  "password": "123456",
  "nickname": "张三",
  "email": "zhangsan@example.com",
  "phone": "13800138001",
  "roleIds": [2, 3]
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "userId": 2
  }
}
```

### 3. 更新用户

**接口**: `PUT /user/update/{userId}`

**请求示例**:
```json
{
  "nickname": "李四",
  "email": "lisi@example.com",
  "phone": "13800138002",
  "status": 1
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "更新成功"
}
```

### 4. 删除用户

**接口**: `DELETE /user/delete/{userId}`

**响应示例**:
```json
{
  "code": 200,
  "message": "删除成功"
}
```

### 5. 修改密码

**接口**: `POST /user/change-password`

**请求示例**:
```json
{
  "oldPassword": "123456",
  "newPassword": "654321"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "密码修改成功"
}
```

### 6. 重置密码

**接口**: `POST /user/reset-password/{userId}`

**请求示例**:
```json
{
  "newPassword": "123456"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "密码重置成功"
}
```

## 🎭 角色管理接口

### 1. 角色列表

**接口**: `GET /role/list`

**请求参数**:
```
roleName: string (可选)
status: int (可选)
pageNum: int
pageSize: int
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "total": 10,
    "list": [
      {
        "roleId": 1,
        "roleCode": "ADMIN",
        "roleName": "管理员",
        "status": 1,
        "createTime": "2024-01-01 00:00:00"
      }
    ]
  }
}
```

### 2. 创建角色

**接口**: `POST /role/create`

**请求示例**:
```json
{
  "roleCode": "MANAGER",
  "roleName": "经理",
  "remark": "部门经理角色",
  "permissionIds": [1, 2, 3]
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "roleId": 4
  }
}
```

### 3. 更新角色

**接口**: `PUT /role/update/{roleId}`

**请求示例**:
```json
{
  "roleName": "高级经理",
  "remark": "更新备注",
  "status": 1
}
```

### 4. 删除角色

**接口**: `DELETE /role/delete/{roleId}`

### 5. 角色权限配置

**接口**: `POST /role/{roleId}/permissions`

**请求示例**:
```json
{
  "permissionIds": [1, 2, 3, 4, 5]
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "权限配置成功"
}
```

## 🔑 权限管理接口

### 1. 权限列表

**接口**: `GET /permission/list`

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "permissionId": 1,
      "permissionCode": "user:read",
      "permissionName": "用户查询",
      "permissionType": 2,
      "parentId": 0,
      "children": []
    }
  ]
}
```

### 2. 权限树

**接口**: `GET /permission/tree`

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "permissionId": 1,
      "permissionName": "系统管理",
      "children": [
        {
          "permissionId": 2,
          "permissionName": "用户管理",
          "children": [
            {
              "permissionId": 3,
              "permissionCode": "user:read",
              "permissionName": "用户查询"
            }
          ]
        }
      ]
    }
  ]
}
```

### 3. 创建权限

**接口**: `POST /permission/create`

**请求示例**:
```json
{
  "permissionCode": "dept:read",
  "permissionName": "部门查询",
  "permissionType": 2,
  "parentId": 1
}
```

### 4. 用户权限查询

**接口**: `GET /permission/user/{userId}`

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "permissions": ["user:read", "user:write", "role:read"],
    "roles": ["ADMIN"]
  }
}
```

## 📋 菜单管理接口

### 1. 菜单树

**接口**: `GET /menu/tree`

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "menuId": 1,
      "menuName": "系统管理",
      "path": "/system",
      "icon": "system",
      "children": [
        {
          "menuId": 2,
          "menuName": "用户管理",
          "path": "/system/user",
          "component": "system/user/index"
        }
      ]
    }
  ]
}
```

### 2. 用户菜单

**接口**: `GET /menu/user-menu`

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "menuId": 1,
      "menuName": "首页",
      "path": "/dashboard",
      "icon": "dashboard",
      "component": "dashboard/index"
    }
  ]
}
```

### 3. 创建菜单

**接口**: `POST /menu/create`

**请求示例**:
```json
{
  "menuName": "系统监控",
  "menuCode": "SYSTEM_MONITOR",
  "parentId": 0,
  "menuType": 1,
  "path": "/monitor",
  "icon": "monitor",
  "sortOrder": 1
}
```

## 🏢 租户管理接口

### 1. 租户列表

**接口**: `GET /tenant/list`

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "total": 10,
    "list": [
      {
        "tenantId": 1,
        "tenantCode": "T001",
        "tenantName": "示例公司",
        "status": 1,
        "expireTime": "2025-12-31 23:59:59"
      }
    ]
  }
}
```

### 2. 创建租户

**接口**: `POST /tenant/create`

**请求示例**:
```json
{
  "tenantCode": "T002",
  "tenantName": "新公司",
  "contactName": "张三",
  "contactPhone": "13800138000",
  "contactEmail": "contact@example.com",
  "expireTime": "2025-12-31",
  "accountLimit": 100
}
```

### 3. 租户配置

**接口**: `GET /tenant/{tenantId}/config`

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "tokenExpireTime": 7200,
    "passwordPolicy": {
      "minLength": 8,
      "requireUpperCase": true,
      "requireNumber": true
    },
    "loginPolicy": {
      "maxFailCount": 5,
      "lockTime": 900
    }
  }
}
```

## 🔐 OAuth 2.0接口

### 1. 授权码获取

**接口**: `GET /oauth/authorize`

**请求参数**:
```
response_type: code
client_id: web-client
redirect_uri: http://localhost:3000/callback
scope: all
state: random-state
```

**重定向响应**:
```
http://localhost:3000/callback?code=AUTH_CODE&state=random-state
```

### 2. Token获取

**接口**: `POST /oauth/token`

**请求示例（授权码模式）**:
```json
{
  "grant_type": "authorization_code",
  "code": "AUTH_CODE",
  "client_id": "web-client",
  "client_secret": "secret",
  "redirect_uri": "http://localhost:3000/callback"
}
```

**请求示例（密码模式）**:
```json
{
  "grant_type": "password",
  "username": "admin",
  "password": "admin123",
  "client_id": "mobile-client",
  "client_secret": "secret"
}
```

**响应示例**:
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIs...",
  "refresh_token": "eyJhbGciOiJIUzI1NiIs...",
  "token_type": "Bearer",
  "expires_in": 7200,
  "scope": "all"
}
```

### 3. Token校验

**接口**: `POST /oauth/check_token`

**请求参数**:
```
token: eyJhbGciOiJIUzI1NiIs...
```

**响应示例**:
```json
{
  "active": true,
  "client_id": "web-client",
  "username": "admin",
  "scope": ["all"],
  "exp": 1698765432
}
```

## 📊 日志查询接口

### 1. 登录日志

**接口**: `GET /log/login`

**请求参数**:
```
username: string (可选)
status: int (可选)
startTime: string (可选)
endTime: string (可选)
pageNum: int
pageSize: int
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "total": 100,
    "list": [
      {
        "logId": 1,
        "username": "admin",
        "ipAddress": "192.168.1.1",
        "location": "北京市",
        "browser": "Chrome",
        "os": "Windows 10",
        "status": 1,
        "message": "登录成功",
        "loginTime": "2024-01-01 10:00:00"
      }
    ]
  }
}
```

### 2. 操作日志

**接口**: `GET /log/operation`

**请求参数**:
```
username: string (可选)
module: string (可选)
startTime: string (可选)
endTime: string (可选)
pageNum: int
pageSize: int
```

## 📈 监控接口

### 1. 在线用户

**接口**: `GET /monitor/online-users`

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "total": 10,
    "users": [
      {
        "userId": 1,
        "username": "admin",
        "ipAddress": "192.168.1.1",
        "loginTime": "2024-01-01 10:00:00",
        "lastActiveTime": "2024-01-01 10:30:00"
      }
    ]
  }
}
```

### 2. 强制下线

**接口**: `POST /monitor/force-logout/{userId}`

**响应示例**:
```json
{
  "code": 200,
  "message": "强制下线成功"
}
```

---

**相关文档**：
- [架构设计文档](./02-架构设计.md)
- [快速开始指南](./06-快速开始.md)

