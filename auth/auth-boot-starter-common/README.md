# Auth Boot Starter Common

企业级Spring Boot脚手架 - 公共基础模块

## 功能特性

### ✅ 已实现

1. **统一响应体**
   - `Result<T>` - 统一的API响应格式
   - 支持成功/失败响应
   - 包含链路追踪ID

2. **统一分页**
   - `PageRequest` - 分页请求参数
   - `PageResult<T>` - 分页响应结果
   - 自动计算总页数

3. **异常体系**
   - `BaseException` - 基础异常
   - `BusinessException` - 业务异常
   - `SystemException` - 系统异常
   - `GlobalExceptionHandler` - 全局异常处理器

4. **常量定义**
   - `CommonConstant` - 公共常量
   - `ErrorCode` - 错误码常量

5. **枚举类型**
   - `StatusEnum` - 状态枚举
   - `YesNoEnum` - 是否枚举

6. **工具类**
   - `JsonUtil` - JSON工具类
   - `ValidationUtil` - 参数校验工具类

## 快速开始

### 添加依赖

```xml
<dependency>
    <groupId>cn.zhangziming</groupId>
    <artifactId>auth-boot-starter-common</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 使用示例

#### 1. 统一响应体

```java
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return Result.success(user);
    }
    
    @PostMapping
    public Result<Long> saveUser(@RequestBody User user) {
        Long userId = userService.saveUser(user);
        return Result.success(userId);
    }
}
```

#### 2. 分页查询

```java
@GetMapping("/list")
public Result<PageResult<User>> listUsers(PageRequest pageRequest) {
    PageResult<User> result = userService.listUsers(pageRequest);
    return Result.success(result);
}
```

#### 3. 异常处理

```java
@Service
public class UserService {
    
    public User getUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }
}
```

#### 4. 参数校验

```java
@Service
public class UserService {
    
    public void saveUser(User user) {
        ValidationUtil.notNull(user, "用户对象不能为空");
        ValidationUtil.notBlank(user.getUsername(), "用户名不能为空");
        ValidationUtil.isTrue(user.getAge() >= 18, "年龄必须大于等于18岁");
        
        userMapper.insert(user);
    }
}
```

## 目录结构

```
auth-boot-starter-common/
├── constant/          # 常量定义
│   ├── CommonConstant.java
│   └── ErrorCode.java
├── enums/            # 枚举类型
│   ├── StatusEnum.java
│   └── YesNoEnum.java
├── exception/        # 异常体系
│   ├── BaseException.java
│   ├── BusinessException.java
│   ├── SystemException.java
│   └── GlobalExceptionHandler.java
├── model/            # 通用模型
│   ├── Result.java
│   ├── PageRequest.java
│   └── PageResult.java
└── util/            # 工具类
    ├── JsonUtil.java
    └── ValidationUtil.java
```

## 更新日志

### v1.0.0-SNAPSHOT
- ✅ 初始版本
- ✅ 实现统一响应体
- ✅ 实现异常体系
- ✅ 实现统一分页
- ✅ 实现基础工具类

## 下一步计划

- [ ] 添加更多工具类（加密、日期等）
- [ ] 添加更多通用注解
- [ ] 添加单元测试

