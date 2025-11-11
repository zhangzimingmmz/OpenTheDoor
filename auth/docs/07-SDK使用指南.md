# SDK使用指南

## 📦 SDK介绍

auth-spring-boot-starter 是一个开箱即用的Spring Boot Starter，可以让你的应用快速集成认证授权功能。

### 核心功能

- ✅ 自动配置认证拦截器
- ✅ 注解式权限控制
- ✅ Token自动续期
- ✅ 用户上下文自动注入
- ✅ 统一异常处理
- ✅ 请求头自动传递

## 🚀 快速开始

### 1. 添加依赖

```xml
<dependency>
    <groupId>cn.zhangziming</groupId>
    <artifactId>auth-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置文件

```yaml
auth:
  # 认证服务器配置
  server:
    url: http://localhost:8080          # 认证服务地址
    timeout: 5000                        # 超时时间(毫秒)
  
  # OAuth客户端配置
  client:
    id: your-client-id                   # 客户端ID
    secret: your-client-secret           # 客户端密钥
  
  # Token配置
  token:
    header-name: Authorization           # Token请求头名称
    prefix: Bearer                       # Token前缀
    auto-refresh: true                   # 是否自动刷新Token
    refresh-threshold: 300               # 刷新阈值(秒)
  
  # 拦截器配置
  interceptor:
    enabled: true                        # 是否启用拦截器
    exclude-paths:                       # 排除路径
      - /public/**
      - /static/**
      - /actuator/**
  
  # 权限配置
  permission:
    enabled: true                        # 是否启用权限验证
    cache-enabled: true                  # 是否缓存权限
    cache-time: 1800                     # 缓存时间(秒)
  
  # 租户配置
  tenant:
    enabled: false                       # 是否启用多租户
    header-name: X-Tenant-Id            # 租户ID请求头名称
```

### 3. 启用自动配置

```java
@SpringBootApplication
@EnableAuthClient  // 启用认证客户端
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 🎯 注解使用

### @RequireLogin - 登录验证

用于标记需要登录才能访问的接口。

```java
@RestController
@RequestMapping("/api")
public class ApiController {
    
    // 单个接口需要登录
    @RequireLogin
    @GetMapping("/user/info")
    public Result getUserInfo() {
        UserInfo user = UserContext.getCurrentUser();
        return Result.success(user);
    }
    
    // 整个Controller需要登录
    @RequireLogin
    @RestController
    @RequestMapping("/api/protected")
    public class ProtectedController {
        // 所有接口都需要登录
    }
}
```

### @RequirePermission - 权限验证

用于标记需要特定权限才能访问的接口。

```java
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    // 需要单个权限
    @RequirePermission("user:read")
    @GetMapping("/list")
    public Result list() {
        return Result.success();
    }
    
    // 需要多个权限（AND关系）
    @RequirePermission(value = {"user:read", "user:export"}, logical = Logical.AND)
    @GetMapping("/export")
    public Result export() {
        return Result.success();
    }
    
    // 需要任意一个权限（OR关系）
    @RequirePermission(value = {"user:read", "user:write"}, logical = Logical.OR)
    @GetMapping("/view/{id}")
    public Result view(@PathVariable Long id) {
        return Result.success();
    }
}
```

### @RequireRole - 角色验证

用于标记需要特定角色才能访问的接口。

```java
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    // 需要单个角色
    @RequireRole("ADMIN")
    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable Long id) {
        return Result.success();
    }
    
    // 需要多个角色（AND关系）
    @RequireRole(value = {"ADMIN", "SUPER_ADMIN"}, logical = Logical.AND)
    @PostMapping("/system/config")
    public Result updateConfig() {
        return Result.success();
    }
    
    // 需要任意一个角色（OR关系）
    @RequireRole(value = {"ADMIN", "MANAGER"}, logical = Logical.OR)
    @GetMapping("/reports")
    public Result reports() {
        return Result.success();
    }
}
```

### @IgnoreAuth - 忽略认证

用于在需要认证的Controller中排除某些接口。

```java
@RestController
@RequestMapping("/api/user")
@RequireLogin  // 整个Controller需要登录
public class UserController {
    
    // 这个接口不需要登录
    @IgnoreAuth
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO dto) {
        return Result.success();
    }
    
    // 其他接口需要登录
    @GetMapping("/profile")
    public Result profile() {
        return Result.success();
    }
}
```

## 🔧 核心API

### UserContext - 用户上下文

获取当前登录用户信息。

```java
@Service
public class BusinessService {
    
    public void doSomething() {
        // 获取当前用户完整信息
        UserInfo user = UserContext.getCurrentUser();
        
        // 获取用户ID
        Long userId = UserContext.getUserId();
        
        // 获取用户名
        String username = UserContext.getUsername();
        
        // 获取租户ID
        String tenantId = UserContext.getTenantId();
        
        // 获取用户角色
        List<String> roles = UserContext.getRoles();
        
        // 获取用户权限
        List<String> permissions = UserContext.getPermissions();
        
        // 检查是否有某个权限
        boolean hasPermission = UserContext.hasPermission("user:read");
        
        // 检查是否有某个角色
        boolean hasRole = UserContext.hasRole("ADMIN");
        
        // 清除当前上下文（一般不需要手动调用）
        UserContext.clear();
    }
}
```

### AuthClient - 认证客户端

直接调用认证服务的API。

```java
@Service
public class UserService {
    
    @Autowired
    private AuthClient authClient;
    
    // 登录
    public LoginResponse login(String username, String password) {
        return authClient.login(username, password);
    }
    
    // 登出
    public void logout(String token) {
        authClient.logout(token);
    }
    
    // 刷新Token
    public TokenResponse refreshToken(String refreshToken) {
        return authClient.refreshToken(refreshToken);
    }
    
    // 验证Token
    public boolean validateToken(String token) {
        return authClient.validateToken(token);
    }
    
    // 获取用户信息
    public UserInfoResponse getUserInfo(String token) {
        return authClient.getUserInfo(token);
    }
    
    // 检查用户权限
    public boolean checkPermission(Long userId, String permission) {
        return authClient.checkPermission(userId, permission);
    }
    
    // 获取用户权限列表
    public List<String> getUserPermissions(Long userId) {
        return authClient.getUserPermissions(userId);
    }
}
```

### PermissionService - 权限服务

编程式权限验证。

```java
@Service
public class OrderService {
    
    @Autowired
    private PermissionService permissionService;
    
    public void deleteOrder(Long orderId) {
        // 检查当前用户是否有删除权限
        if (!permissionService.hasPermission("order:delete")) {
            throw new PermissionException("无权限删除订单");
        }
        
        // 检查是否有任意一个权限
        if (permissionService.hasAnyPermission("order:delete", "order:admin")) {
            // 执行删除
        }
        
        // 检查是否有所有权限
        if (permissionService.hasAllPermissions("order:read", "order:delete")) {
            // 执行操作
        }
        
        // 检查角色
        if (permissionService.hasRole("ADMIN")) {
            // 执行操作
        }
    }
}
```

### TokenManager - Token管理器

管理Token的生命周期。

```java
@Service
public class TokenService {
    
    @Autowired
    private TokenManager tokenManager;
    
    public void manageToken() {
        // 获取当前Token
        String token = tokenManager.getCurrentToken();
        
        // 刷新Token
        TokenResponse newToken = tokenManager.refreshToken();
        
        // 验证Token是否有效
        boolean isValid = tokenManager.validateToken(token);
        
        // 获取Token过期时间
        Long expireTime = tokenManager.getTokenExpireTime(token);
        
        // 检查Token是否即将过期
        boolean willExpire = tokenManager.willExpireSoon(token, 300);
        
        // 撤销Token（登出）
        tokenManager.revokeToken(token);
    }
}
```

## 🔌 扩展点

### 自定义权限验证器

```java
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    
    @Override
    public boolean hasPermission(UserInfo user, String permission) {
        // 自定义权限验证逻辑
        
        // 示例：超级管理员拥有所有权限
        if (user.getRoles().contains("SUPER_ADMIN")) {
            return true;
        }
        
        // 示例：从数据库或缓存查询权限
        return userPermissionRepository.hasPermission(user.getUserId(), permission);
    }
    
    @Override
    public boolean hasRole(UserInfo user, String role) {
        // 自定义角色验证逻辑
        return user.getRoles().contains(role);
    }
}
```

### 自定义Token提取器

```java
@Component
public class CustomTokenExtractor implements TokenExtractor {
    
    @Override
    public String extractToken(HttpServletRequest request) {
        // 从Header提取
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        
        // 从Cookie提取
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        // 从URL参数提取
        return request.getParameter("token");
    }
}
```

### 自定义异常处理

```java
@ControllerAdvice
public class AuthExceptionHandler {
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Result> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Result.error(401, "认证失败: " + e.getMessage()));
    }
    
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<Result> handlePermissionException(PermissionException e) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(Result.error(403, "权限不足: " + e.getMessage()));
    }
    
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Result> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Result.error(1003, "Token已过期"));
    }
}
```

### 自定义拦截器

```java
@Component
public class CustomAuthInterceptor extends AuthInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 调用父类的认证逻辑
        boolean authenticated = super.preHandle(request, response, handler);
        
        if (authenticated) {
            // 自定义逻辑，例如记录访问日志
            logAccess(request);
        }
        
        return authenticated;
    }
    
    private void logAccess(HttpServletRequest request) {
        UserInfo user = UserContext.getCurrentUser();
        log.info("用户 {} 访问了 {}", user.getUsername(), request.getRequestURI());
    }
}
```

## 🎨 高级特性

### 多租户支持

```java
// 启用多租户
@Configuration
@EnableMultiTenant
public class TenantConfig {
    
    @Bean
    public TenantResolver tenantResolver() {
        return new HeaderTenantResolver("X-Tenant-Id");
    }
}

// 使用租户信息
@Service
public class DataService {
    
    public void queryData() {
        String tenantId = UserContext.getTenantId();
        // 根据租户ID查询数据
    }
}
```

### 数据权限

```java
// 数据权限注解
@DataPermission(type = DataScopeType.DEPT)
@GetMapping("/list")
public Result list() {
    // 自动根据用户的数据权限过滤数据
    return Result.success();
}

// 自定义数据权限
@DataPermission(
    type = DataScopeType.CUSTOM,
    sqlFilter = "dept_id IN (SELECT dept_id FROM user_dept WHERE user_id = #{userId})"
)
@GetMapping("/dept-users")
public Result deptUsers() {
    return Result.success();
}
```

### 缓存策略

```java
// 配置缓存
@Configuration
public class CacheConfig {
    
    @Bean
    public PermissionCache permissionCache() {
        return new RedisPermissionCache(redisTemplate);
    }
}

// 清除用户缓存
@Service
public class UserService {
    
    @Autowired
    private PermissionCache permissionCache;
    
    public void updateUserPermissions(Long userId) {
        // 更新权限后清除缓存
        permissionCache.evict(userId);
    }
}
```

### 异步权限加载

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    
    @Bean
    public AsyncPermissionLoader asyncPermissionLoader() {
        return new AsyncPermissionLoader();
    }
}

// 使用异步加载
@Service
public class PermissionService {
    
    @Autowired
    private AsyncPermissionLoader permissionLoader;
    
    @Async
    public CompletableFuture<List<String>> loadUserPermissions(Long userId) {
        return permissionLoader.load(userId);
    }
}
```

## 🧪 测试支持

### 单元测试

```java
@SpringBootTest
@AutoConfigureMockAuthClient  // 自动配置Mock认证客户端
public class ServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}, permissions = {"user:read"})
    public void testGetUser() {
        // 模拟登录用户
        UserInfo user = UserContext.getCurrentUser();
        assertEquals("admin", user.getUsername());
        assertTrue(UserContext.hasRole("ADMIN"));
    }
    
    @Test
    @WithMockUser(userId = 1L, username = "test")
    public void testBusinessLogic() {
        // 测试业务逻辑
        Long userId = UserContext.getUserId();
        assertEquals(1L, userId);
    }
}
```

### 集成测试

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureAuthClient
public class ControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private MockAuthServer mockAuthServer;  // Mock认证服务器
    
    @Test
    public void testProtectedEndpoint() {
        // 模拟登录获取Token
        String token = mockAuthServer.mockLogin("admin", "admin123");
        
        // 携带Token访问接口
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Result> response = restTemplate.exchange(
            "/api/user/info",
            HttpMethod.GET,
            entity,
            Result.class
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

## 📝 最佳实践

### 1. 权限粒度设计

```java
// 推荐：使用细粒度权限
@RequirePermission("user:create")    // 创建用户
@RequirePermission("user:read")      // 查询用户
@RequirePermission("user:update")    // 更新用户
@RequirePermission("user:delete")    // 删除用户

// 不推荐：权限粒度过粗
@RequirePermission("user")  // 太粗，不够灵活
```

### 2. 异常处理

```java
@Service
public class UserService {
    
    public void deleteUser(Long userId) {
        // 方式一：使用注解（推荐）
        // @RequirePermission("user:delete")
        
        // 方式二：手动检查
        if (!UserContext.hasPermission("user:delete")) {
            throw new PermissionException("无权限删除用户");
        }
        
        // 业务逻辑
    }
}
```

### 3. 性能优化

```java
// 启用权限缓存
auth.permission.cache-enabled=true

// 批量加载权限
@Service
public class PermissionService {
    
    @Cacheable(value = "user-permissions", key = "#userId")
    public List<String> getUserPermissions(Long userId) {
        return authClient.getUserPermissions(userId);
    }
}
```

### 4. 安全建议

```java
// 1. 使用HTTPS传输
// 2. 定期刷新Token
// 3. 敏感操作二次验证
// 4. 记录审计日志

@Service
@Slf4j
public class SensitiveOperationService {
    
    @RequirePermission("system:config:update")
    @AuditLog(operation = "更新系统配置", level = AuditLevel.HIGH)
    public void updateSystemConfig(ConfigDTO config) {
        // 敏感操作，记录日志
        log.warn("用户 {} 更新了系统配置", UserContext.getUsername());
        
        // 执行更新
    }
}
```

## 🔗 相关资源

- [API接口文档](./04-API接口文档.md)
- [快速开始指南](./06-快速开始.md)
- [常见问题FAQ](./08-常见问题.md)

---

**需要帮助？** 查看[完整文档](./README.md)或联系技术支持

