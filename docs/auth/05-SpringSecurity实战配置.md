# Spring Security实战配置

## 目录
- [基础配置](#基础配置)
- [表单登录配置](#表单登录配置)
- [HTTP Basic认证](#http-basic认证)
- [Remember-Me功能](#remember-me功能)
- [会话管理策略](#会话管理策略)
- [CSRF防护配置](#csrf防护配置)
- [方法级安全](#方法级安全)
- [多种认证方式组合](#多种认证方式组合)

---

## 基础配置

### 最小配置

```java
/**
 * 最简单的Spring Security配置
 */
@Configuration
@EnableWebSecurity
public class MinimalSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
        
        return new InMemoryUserDetailsManager(user);
    }
}
```

### 生产环境基础配置

```java
/**
 * 生产环境推荐配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // 启用方法级安全
public class ProductionSecurityConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 授权配置
            .authorizeHttpRequests(authz -> authz
                // 公开资源
                .requestMatchers(
                    "/",
                    "/public/**",
                    "/static/**",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**",
                    "/error"
                ).permitAll()
                
                // API端点
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**").authenticated()
                
                // 管理端点
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/actuator/**").hasRole("ADMIN")
                
                // 其他所有请求需要认证
                .anyRequest().authenticated()
            )
            
            // 表单登录
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            
            // 登出
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            
            // 记住我
            .rememberMe(remember -> remember
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(86400)
            )
            
            // 会话管理
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login?invalid")
                .maximumSessions(1)
                .expiredUrl("/login?expired")
            )
            
            // 安全头
            .headers(headers -> headers
                .frameOptions().deny()
                .xssProtection().enable()
                .contentTypeOptions().enable()
                .httpStrictTransportSecurity()
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
            );
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
```

### 数据库表结构

```sql
-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 角色表
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255)
);

-- 用户角色关联表
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- 权限表
CREATE TABLE permissions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255)
);

-- 角色权限关联表
CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- Remember-Me持久化表
CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);
```

---

## 表单登录配置

### 基础表单登录

```java
@Configuration
@EnableWebSecurity
public class FormLoginConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(form -> form
                // 自定义登录页面
                .loginPage("/login")
                
                // 登录表单提交的URL
                .loginProcessingUrl("/perform_login")
                
                // 登录成功后的默认跳转URL
                .defaultSuccessUrl("/dashboard", true)
                
                // 登录失败URL
                .failureUrl("/login?error=true")
                
                // 表单参数名
                .usernameParameter("username")
                .passwordParameter("password")
                
                // 允许所有人访问登录页
                .permitAll()
            );
        
        return http.build();
    }
}
```

### 自定义登录成功处理器

```java
/**
 * 自定义登录成功处理器
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private LoginHistoryService loginHistoryService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Authentication authentication) 
            throws IOException {
        
        // 1. 记录登录历史
        String username = authentication.getName();
        String ipAddress = getClientIP(request);
        loginHistoryService.recordLogin(username, ipAddress, true);
        
        // 2. 根据请求类型返回不同响应
        if (isAjaxRequest(request)) {
            // AJAX请求：返回JSON
            handleAjaxResponse(response, authentication);
        } else {
            // 普通请求：重定向
            handleRedirect(request, response, authentication);
        }
    }
    
    private void handleAjaxResponse(HttpServletResponse response, 
                                    Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        // 生成Token（用于前后端分离场景）
        String token = tokenProvider.createToken(authentication);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "登录成功");
        result.put("token", token);
        result.put("username", authentication.getName());
        result.put("authorities", authentication.getAuthorities());
        
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
    
    private void handleRedirect(HttpServletRequest request,
                               HttpServletResponse response,
                               Authentication authentication) throws IOException {
        
        // 根据角色重定向到不同页面
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        String targetUrl = "/dashboard";
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                targetUrl = "/admin/dashboard";
                break;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                targetUrl = "/user/dashboard";
            }
        }
        
        // 如果有保存的请求，重定向到原请求
        SavedRequest savedRequest = new HttpSessionRequestCache()
            .getRequest(request, response);
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
        }
        
        new DefaultRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
    
    private boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(ajaxHeader);
    }
    
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
```

### 自定义登录失败处理器

```java
/**
 * 自定义登录失败处理器
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private LoginHistoryService loginHistoryService;
    
    @Autowired
    private LoginAttemptService loginAttemptService;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                       HttpServletResponse response,
                                       AuthenticationException exception) 
            throws IOException {
        
        String username = request.getParameter("username");
        String ipAddress = getClientIP(request);
        
        // 1. 记录失败次数
        loginAttemptService.loginFailed(username);
        loginHistoryService.recordLogin(username, ipAddress, false);
        
        // 2. 生成错误消息
        String errorMessage = getErrorMessage(exception);
        
        // 3. 根据请求类型返回不同响应
        if (isAjaxRequest(request)) {
            handleAjaxResponse(response, errorMessage);
        } else {
            handleRedirect(request, response, errorMessage);
        }
    }
    
    private String getErrorMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "用户名或密码错误";
        } else if (exception instanceof DisabledException) {
            return "账户已被禁用";
        } else if (exception instanceof LockedException) {
            return "账户已被锁定";
        } else if (exception instanceof AccountExpiredException) {
            return "账户已过期";
        } else if (exception instanceof CredentialsExpiredException) {
            return "密码已过期";
        } else {
            return "登录失败：" + exception.getMessage();
        }
    }
    
    private void handleAjaxResponse(HttpServletResponse response, String errorMessage) 
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", errorMessage);
        
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
    
    private void handleRedirect(HttpServletRequest request,
                               HttpServletResponse response,
                               String errorMessage) throws IOException {
        String redirectUrl = "/login?error=true&message=" + 
            URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
    
    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
```

### 登录页面HTML

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>登录</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
    <div class="login-container">
        <h2>用户登录</h2>
        
        <!-- 错误消息 -->
        <div th:if="${param.error}" class="alert alert-danger">
            <span th:text="${param.message} ?: '用户名或密码错误'"></span>
        </div>
        
        <!-- 登出消息 -->
        <div th:if="${param.logout}" class="alert alert-success">
            您已成功登出
        </div>
        
        <!-- 登录表单 -->
        <form th:action="@{/perform_login}" method="post">
            <div class="form-group">
                <label for="username">用户名:</label>
                <input type="text" id="username" name="username" 
                       required autofocus class="form-control">
            </div>
            
            <div class="form-group">
                <label for="password">密码:</label>
                <input type="password" id="password" name="password" 
                       required class="form-control">
            </div>
            
            <div class="form-group">
                <label>
                    <input type="checkbox" name="remember-me"> 记住我
                </label>
            </div>
            
            <!-- CSRF Token -->
            <input type="hidden" th:name="${_csrf.parameterName}" 
                   th:value="${_csrf.token}">
            
            <button type="submit" class="btn btn-primary">登录</button>
        </form>
        
        <div class="links">
            <a href="/register">注册新账号</a> |
            <a href="/forgot-password">忘记密码</a>
        </div>
    </div>
</body>
</html>
```

---

## HTTP Basic认证

### 基础配置

```java
@Configuration
@EnableWebSecurity
public class BasicAuthConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .csrf().disable();  // Basic认证通常禁用CSRF
        
        return http.build();
    }
}
```

### 自定义Basic认证入口点

```java
/**
 * 自定义Basic认证入口点
 */
@Component
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) 
            throws IOException {
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"API\"");
        response.setContentType("application/json;charset=UTF-8");
        
        Map<String, Object> error = new HashMap<>();
        error.put("status", 401);
        error.put("error", "Unauthorized");
        error.put("message", "需要认证才能访问此资源");
        error.put("path", request.getRequestURI());
        
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}

/**
 * 配置自定义入口点
 */
@Configuration
public class BasicAuthConfig {
    
    @Autowired
    private CustomBasicAuthenticationEntryPoint authenticationEntryPoint;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> basic
                .authenticationEntryPoint(authenticationEntryPoint)
            );
        
        return http.build();
    }
}
```

### Basic认证客户端示例

```java
/**
 * 使用RestTemplate调用Basic认证保护的API
 */
@Service
public class ApiClient {
    
    public String callProtectedApi() {
        String url = "https://api.example.com/data";
        String username = "user";
        String password = "password";
        
        // 方式1: 使用HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
            url, HttpMethod.GET, entity, String.class
        );
        
        return response.getBody();
    }
    
    public String callProtectedApiV2() {
        // 方式2: 使用RestTemplate with BasicAuthenticationInterceptor
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
            new BasicAuthenticationInterceptor("user", "password")
        );
        
        return restTemplate.getForObject("https://api.example.com/data", String.class);
    }
}
```

---

## Remember-Me功能

### 基于Token的Remember-Me

```java
@Configuration
public class RememberMeConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .rememberMe(remember -> remember
                // Token有效期（秒）
                .tokenValiditySeconds(86400 * 7)  // 7天
                
                // Remember-Me参数名
                .rememberMeParameter("remember-me")
                
                // Remember-Me Cookie名
                .rememberMeCookieName("remember-me")
                
                // Cookie域
                .rememberMeCookieDomain("example.com")
                
                // 密钥（用于生成Token签名）
                .key("uniqueAndSecretKey")
                
                // UserDetailsService
                .userDetailsService(userDetailsService)
                
                // 使用安全Cookie
                .useSecureCookie(true)
                
                // Token持久化仓库
                .tokenRepository(persistentTokenRepository())
            );
        
        return http.build();
    }
    
    /**
     * 持久化Token仓库（推荐）
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 首次运行时创建表（生产环境应手动创建）
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
```

### 自定义Remember-Me服务

```java
/**
 * 自定义Remember-Me服务
 */
@Component
public class CustomRememberMeServices extends PersistentTokenBasedRememberMeServices {
    
    @Autowired
    private UserActivityService userActivityService;
    
    public CustomRememberMeServices(String key, 
                                   UserDetailsService userDetailsService,
                                   PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }
    
    @Override
    protected void onLoginSuccess(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Authentication successfulAuthentication) {
        
        String username = successfulAuthentication.getName();
        String ipAddress = request.getRemoteAddr();
        
        // 记录用户活动
        userActivityService.recordRememberMeLogin(username, ipAddress);
        
        super.onLoginSuccess(request, response, successfulAuthentication);
    }
    
    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        
        // 可以添加额外的验证逻辑
        String ipAddress = request.getRemoteAddr();
        
        // 检查IP是否在黑名单中
        if (isBlacklistedIP(ipAddress)) {
            throw new RememberMeAuthenticationException("IP地址被禁止");
        }
        
        return super.processAutoLoginCookie(cookieTokens, request, response);
    }
    
    private boolean isBlacklistedIP(String ipAddress) {
        // 实现IP黑名单检查逻辑
        return false;
    }
}
```

---

## 会话管理策略

### 会话并发控制

```java
@Configuration
public class SessionManagementConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session
                // 会话创建策略
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                
                // 无效会话URL
                .invalidSessionUrl("/login?invalid")
                
                // 会话固定攻击防护
                .sessionFixation().migrateSession()
                
                // 并发会话控制
                .maximumSessions(1)  // 同一用户最多1个会话
                    .maxSessionsPreventsLogin(false)  // false: 踢掉旧会话; true: 拒绝新登录
                    .expiredUrl("/login?expired")
                    .sessionRegistry(sessionRegistry())
            );
        
        return http.build();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
```

### 会话超时配置

```properties
# application.properties

# 会话超时时间（30分钟）
server.servlet.session.timeout=30m

# Session Cookie配置
server.servlet.session.cookie.name=JSESSIONID
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.same-site=strict
server.servlet.session.cookie.max-age=1800
```

### 获取在线用户列表

```java
/**
 * 在线用户管理
 */
@Service
public class OnlineUserService {
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    /**
     * 获取所有在线用户
     */
    public List<OnlineUser> getAllOnlineUsers() {
        List<OnlineUser> onlineUsers = new ArrayList<>();
        
        List<Object> principals = sessionRegistry.getAllPrincipals();
        
        for (Object principal : principals) {
            List<SessionInformation> sessions = 
                sessionRegistry.getAllSessions(principal, false);
            
            for (SessionInformation session : sessions) {
                OnlineUser onlineUser = new OnlineUser();
                onlineUser.setUsername(principal.toString());
                onlineUser.setSessionId(session.getSessionId());
                onlineUser.setLastRequest(session.getLastRequest());
                onlineUser.setExpired(session.isExpired());
                
                onlineUsers.add(onlineUser);
            }
        }
        
        return onlineUsers;
    }
    
    /**
     * 踢出用户
     */
    public void kickOutUser(String username) {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        
        for (Object principal : principals) {
            if (principal.toString().equals(username)) {
                List<SessionInformation> sessions = 
                    sessionRegistry.getAllSessions(principal, false);
                
                for (SessionInformation session : sessions) {
                    session.expireNow();  // 使会话立即过期
                }
                break;
            }
        }
    }
    
    /**
     * 获取用户的会话数
     */
    public int getUserSessionCount(String username) {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        
        for (Object principal : principals) {
            if (principal.toString().equals(username)) {
                return sessionRegistry.getAllSessions(principal, false).size();
            }
        }
        
        return 0;
    }
}

/**
 * 在线用户DTO
 */
@Data
public class OnlineUser {
    private String username;
    private String sessionId;
    private Date lastRequest;
    private boolean expired;
}
```

---

## CSRF防护配置

### 基础CSRF配置

```java
@Configuration
public class CsrfConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                // 使用Cookie存储CSRF Token
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                
                // 忽略某些路径的CSRF保护
                .ignoringRequestMatchers("/api/public/**", "/webhooks/**")
                
                // 自定义Token请求头名称
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
            );
        
        return http.build();
    }
}
```

### 前端集成CSRF

**JavaScript示例（使用Cookie中的Token）：**

```javascript
// 从Cookie获取CSRF Token
function getCsrfToken() {
    const name = 'XSRF-TOKEN=';
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookies = decodedCookie.split(';');
    
    for (let cookie of cookies) {
        cookie = cookie.trim();
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length);
        }
    }
    return '';
}

// 发送POST请求时包含CSRF Token
fetch('/api/data', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'X-XSRF-TOKEN': getCsrfToken()  // CSRF Token
    },
    body: JSON.stringify(data)
})
.then(response => response.json())
.then(data => console.log(data));
```

**Axios配置：**

```javascript
// Axios会自动从Cookie中读取XSRF-TOKEN并添加到请求头
axios.defaults.xsrfCookieName = 'XSRF-TOKEN';
axios.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';

// 发送请求
axios.post('/api/data', data)
    .then(response => console.log(response.data));
```

### API场景的CSRF配置

```java
/**
 * 前后端分离场景的CSRF配置
 */
@Configuration
public class ApiCsrfConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                // 对于纯API，可以禁用CSRF（如果使用JWT Token）
                .disable()
            )
            
            // 或者对特定路径禁用CSRF
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            );
        
        return http.build();
    }
}
```

**注意：** 如果使用JWT Token进行认证，通常可以禁用CSRF保护，因为Token不会自动被浏览器发送（不像Cookie）。

---

## 方法级安全

### 启用方法安全

```java
@Configuration
@EnableMethodSecurity(
    prePostEnabled = true,   // 启用@PreAuthorize和@PostAuthorize
    securedEnabled = true,   // 启用@Secured
    jsr250Enabled = true     // 启用@RolesAllowed
)
public class MethodSecurityConfig {
}
```

### @PreAuthorize注解

```java
/**
 * 使用@PreAuthorize进行方法级授权
 */
@Service
public class UserService {
    
    /**
     * 只有ADMIN角色可以访问
     */
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * 需要特定权限
     */
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    /**
     * 多个角色之一
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    /**
     * 复杂表达式
     */
    @PreAuthorize("hasRole('USER') and #user.username == authentication.name")
    public User updateProfile(User user) {
        return userRepository.save(user);
    }
    
    /**
     * 用户只能访问自己的数据
     */
    @PreAuthorize("#userId == authentication.principal.id")
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
    
    /**
     * 自定义权限检查
     */
    @PreAuthorize("@customSecurityService.canAccessUser(#userId)")
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}

/**
 * 自定义权限检查服务
 */
@Service("customSecurityService")
public class CustomSecurityService {
    
    public boolean canAccessUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            return false;
        }
        
        // 管理员可以访问所有用户
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        
        // 用户只能访问自己
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getId().equals(userId);
        }
        
        return false;
    }
}
```

### @PostAuthorize注解

```java
/**
 * 方法执行后进行权限检查
 */
@Service
public class DocumentService {
    
    /**
     * 返回结果后检查权限
     * 如果返回的文档不属于当前用户，抛出AccessDeniedException
     */
    @PostAuthorize("returnObject.owner == authentication.name")
    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow();
    }
    
    /**
     * 过滤返回的集合，只保留用户有权访问的项
     */
    @PostFilter("filterObject.owner == authentication.name or hasRole('ADMIN')")
    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }
}
```

### @Secured和@RolesAllowed注解

```java
@Service
public class ProductService {
    
    /**
     * @Secured注解（简单角色检查）
     */
    @Secured("ROLE_ADMIN")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
    
    /**
     * @RolesAllowed注解（JSR-250标准）
     */
    @RolesAllowed("ADMIN")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
```

---

## 多种认证方式组合

### 同时支持表单登录和JWT

```java
@Configuration
@EnableWebSecurity
public class MultiAuthConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/login", "/register").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )
            
            // 表单登录（用于Web页面）
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
            )
            
            // JWT认证（用于API）
            .addFilterBefore(jwtAuthenticationFilter, 
                            UsernamePasswordAuthenticationFilter.class)
            
            // 会话管理
            .sessionManagement(session -> session
                // API使用无状态，Web使用有状态
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            
            // CSRF：API禁用，Web启用
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            );
        
        return http.build();
    }
}
```

### 同时支持Basic和Bearer Token

```java
@Configuration
public class MultiTokenAuthConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().authenticated()
            )
            
            // HTTP Basic认证
            .httpBasic(Customizer.withDefaults())
            
            // OAuth2资源服务器（Bearer Token）
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            )
            
            .csrf().disable();
        
        return http.build();
    }
}
```

---

## 总结

本章介绍了Spring Security的各种实战配置：

1. **表单登录**：自定义登录页、成功/失败处理器
2. **HTTP Basic**：API认证的基础方式
3. **Remember-Me**：提升用户体验的记住我功能
4. **会话管理**：并发控制、超时设置、在线用户管理
5. **CSRF防护**：保护应用免受跨站请求伪造攻击
6. **方法级安全**：细粒度的权限控制
7. **多种认证方式**：灵活组合不同的认证机制

**继续学习：**
- [上一章：Spring Security核心架构](./04-SpringSecurity核心架构.md)
- [下一章：Spring Security OAuth2集成](./06-SpringSecurity-OAuth2.md)

