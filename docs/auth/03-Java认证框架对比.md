# Java认证框架对比

## 目录
- [框架概览](#框架概览)
- [Spring Security](#spring-security)
- [Apache Shiro](#apache-shiro)
- [JAAS](#jaas)
- [Keycloak](#keycloak)
- [Pac4j](#pac4j)
- [框架选型建议](#框架选型建议)

---

## 框架概览

### 主流Java认证框架对比表

| 框架 | 类型 | 复杂度 | 学习曲线 | 社区活跃度 | 适用场景 |
|------|------|--------|---------|-----------|---------|
| **Spring Security** | 全面安全框架 | 高 | 陡峭 | ⭐⭐⭐⭐⭐ | Spring生态应用 |
| **Apache Shiro** | 轻量安全框架 | 中 | 平缓 | ⭐⭐⭐ | 独立应用、简单需求 |
| **JAAS** | Java标准API | 低 | 中等 | ⭐⭐ | Java SE应用 |
| **Keycloak** | IAM平台 | 高 | 平缓 | ⭐⭐⭐⭐ | 企业SSO、微服务 |
| **Pac4j** | 多协议客户端 | 中 | 平缓 | ⭐⭐⭐ | 多协议集成 |

### 功能对比矩阵

| 功能 | Spring Security | Apache Shiro | JAAS | Keycloak | Pac4j |
|------|----------------|--------------|------|----------|-------|
| **认证** | ✅ | ✅ | ✅ | ✅ | ✅ |
| **授权** | ✅ | ✅ | ✅ | ✅ | ✅ |
| **会话管理** | ✅ | ✅ | ❌ | ✅ | ✅ |
| **记住我** | ✅ | ✅ | ❌ | ✅ | ✅ |
| **OAuth2支持** | ✅ | ⚠️ | ❌ | ✅ | ✅ |
| **OIDC支持** | ✅ | ❌ | ❌ | ✅ | ✅ |
| **SAML支持** | ✅ | ❌ | ❌ | ✅ | ✅ |
| **JWT支持** | ✅ | ⚠️ | ❌ | ✅ | ✅ |
| **LDAP集成** | ✅ | ✅ | ✅ | ✅ | ❌ |
| **密码加密** | ✅ | ✅ | ❌ | ✅ | ❌ |
| **CSRF防护** | ✅ | ❌ | ❌ | N/A | ❌ |
| **方法安全** | ✅ | ✅ | ❌ | N/A | ❌ |
| **Web安全** | ✅ | ✅ | ❌ | N/A | ✅ |
| **微服务支持** | ✅ | ⚠️ | ❌ | ✅ | ✅ |
| **管理界面** | ❌ | ❌ | ❌ | ✅ | ❌ |
| **多租户** | ⚠️ | ⚠️ | ❌ | ✅ | ❌ |

✅ 完全支持 | ⚠️ 部分支持/需额外配置 | ❌ 不支持 | N/A 不适用

---

## Spring Security

### 概述

**Spring Security** 是Spring生态中的安全框架，提供全面的认证和授权解决方案。

**官网：** https://spring.io/projects/spring-security

### 核心特性

1. **全面的认证支持**
   - 表单登录
   - HTTP Basic/Digest
   - OAuth2/OIDC
   - SAML2
   - LDAP
   - 自定义认证

2. **灵活的授权机制**
   - URL级别授权
   - 方法级别授权
   - ACL（访问控制列表）
   - 表达式支持（SpEL）

3. **防护机制**
   - CSRF防护
   - Session固定攻击防护
   - 安全头配置
   - 密码加密

### 架构概览

```
┌─────────────────────────────────────────────────────────┐
│                    Security Filter Chain                 │
├─────────────────────────────────────────────────────────┤
│ SecurityContextPersistenceFilter                        │
│ LogoutFilter                                            │
│ UsernamePasswordAuthenticationFilter                    │
│ BasicAuthenticationFilter                               │
│ RequestCacheAwareFilter                                 │
│ SecurityContextHolderAwareRequestFilter                 │
│ AnonymousAuthenticationFilter                           │
│ SessionManagementFilter                                 │
│ ExceptionTranslationFilter                              │
│ FilterSecurityInterceptor                               │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│              AuthenticationManager                       │
│                       │                                  │
│                       ▼                                  │
│           ProviderManager (delegates to)                │
│                       │                                  │
│         ┌─────────────┼─────────────┐                   │
│         ▼             ▼             ▼                   │
│   DaoAuthentication  Ldap...   Custom...                │
│      Provider       Provider    Provider                │
└─────────────────────────────────────────────────────────┘
```

### 快速开始

**Maven依赖：**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**基础配置：**
```java
/**
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        // 内存用户（仅用于测试）
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("USER", "ADMIN")
            .build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }
}
```

**自定义UserDetailsService（数据库）：**
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(getAuthorities(user))
            .accountExpired(!user.isAccountNonExpired())
            .accountLocked(!user.isAccountNonLocked())
            .credentialsExpired(!user.isCredentialsNonExpired())
            .disabled(!user.isEnabled())
            .build();
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }
}
```

### 优缺点

**优点：**
- ✅ Spring生态深度集成
- ✅ 功能全面，企业级标准
- ✅ 社区活跃，文档丰富
- ✅ 持续更新，紧跟安全趋势
- ✅ 支持最新协议（OAuth2、OIDC）
- ✅ 强大的扩展性

**缺点：**
- ❌ 学习曲线陡峭
- ❌ 配置复杂（新版本有改善）
- ❌ 过滤器链调试困难
- ❌ 与非Spring项目集成困难
- ❌ 文档有时滞后于版本更新

### 适用场景

- Spring Boot/Spring MVC应用
- 企业级应用
- 需要全面安全特性的项目
- OAuth2/OIDC集成
- 微服务架构

---

## Apache Shiro

### 概述

**Apache Shiro** 是一个轻量级的Java安全框架，易于使用和理解。

**官网：** https://shiro.apache.org/

### 核心概念

```
┌─────────────────────────────────────────────┐
│              Application Code                │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│              Subject (当前用户)              │
│  login(), logout(), hasRole(), isPermitted() │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│           SecurityManager (核心)             │
│     Authenticator | Authorizer | Session     │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│               Realm (数据源)                 │
│    JDBC | LDAP | Custom | IniRealm          │
└─────────────────────────────────────────────┘
```

**核心组件：**
1. **Subject**: 当前操作用户
2. **SecurityManager**: 安全管理器（核心）
3. **Realm**: 安全数据源
4. **Authenticator**: 认证器
5. **Authorizer**: 授权器
6. **SessionManager**: 会话管理器
7. **CacheManager**: 缓存管理器

### 快速开始

**Maven依赖：**
```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-starter</artifactId>
    <version>1.13.0</version>
</dependency>
```

**配置文件（shiro.ini）：**
```ini
[main]
# 定义Realm
myRealm = com.example.MyCustomRealm
securityManager.realms = $myRealm

# 密码匹配器
credentialsMatcher = org.apache.shiro.authc.credential.Sha256CredentialsMatcher
myRealm.credentialsMatcher = $credentialsMatcher

# 缓存管理器
cacheManager = org.apache.shiro.cache.ehcache.EhCacheManager
securityManager.cacheManager = $cacheManager

[urls]
# URL权限配置
/login = anon
/logout = logout
/public/** = anon
/admin/** = authc, roles[admin]
/api/** = authc
/** = authc
```

**Shiro配置类：**
```java
/**
 * Shiro配置
 */
@Configuration
public class ShiroConfig {
    
    /**
     * Shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        filterFactory.setSecurityManager(securityManager);
        
        // 登录URL
        filterFactory.setLoginUrl("/login");
        // 成功URL
        filterFactory.setSuccessUrl("/index");
        // 未授权URL
        filterFactory.setUnauthorizedUrl("/403");
        
        // 定义过滤规则
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/logout", "logout");
        filterChainMap.put("/static/**", "anon");
        filterChainMap.put("/admin/**", "authc,roles[admin]");
        filterChainMap.put("/user/**", "authc,roles[user]");
        filterChainMap.put("/**", "authc");
        
        filterFactory.setFilterChainDefinitionMap(filterChainMap);
        
        return filterFactory;
    }
    
    /**
     * SecurityManager
     */
    @Bean
    public SecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }
    
    /**
     * 自定义Realm
     */
    @Bean
    public Realm realm() {
        CustomRealm realm = new CustomRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }
    
    /**
     * 密码匹配器
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");
        matcher.setHashIterations(1024);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }
    
    /**
     * 启用Shiro注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
```

**自定义Realm：**
```java
/**
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {
    
    @Autowired
    private UserService userService;
    
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        User user = userService.findByUsername(username);
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        // 添加角色
        info.setRoles(user.getRoles());
        
        // 添加权限
        info.setStringPermissions(user.getPermissions());
        
        return info;
    }
    
    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
            throws AuthenticationException {
        
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        
        if (user.isLocked()) {
            throw new LockedAccountException("账户已锁定");
        }
        
        // 返回认证信息
        // Shiro会自动比较密码
        return new SimpleAuthenticationInfo(
            username,                    // principal
            user.getPassword(),          // credentials
            ByteSource.Util.bytes(user.getSalt()), // salt
            getName()                    // realm name
        );
    }
}
```

**使用示例：**
```java
/**
 * 登录控制器
 */
@Controller
public class LoginController {
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        
        try {
            subject.login(token);
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            return "redirect:/login?error=unknown";
        } catch (IncorrectCredentialsException e) {
            return "redirect:/login?error=password";
        } catch (LockedAccountException e) {
            return "redirect:/login?error=locked";
        }
    }
    
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }
}

/**
 * 使用注解进行权限控制
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @RequiresAuthentication  // 需要认证
    @GetMapping("/me")
    public User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        return userService.findByUsername(username);
    }
    
    @RequiresRoles("admin")  // 需要admin角色
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @RequiresPermissions("user:delete")  // 需要user:delete权限
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
    
    // 编程式权限检查
    @PostMapping("/{id}/activate")
    public void activateUser(@PathVariable Long id) {
        Subject subject = SecurityUtils.getSubject();
        
        if (subject.hasRole("admin") || subject.isPermitted("user:activate")) {
            userService.activate(id);
        } else {
            throw new UnauthorizedException("没有权限");
        }
    }
}
```

### 权限字符串

Shiro使用简单的字符串表示权限，支持通配符：

```java
// 格式: resource:action:instance

// 示例
"user:create"           // 创建用户
"user:update:123"       // 更新ID为123的用户
"user:*"               // 用户的所有操作
"user:*:123"           // 对ID为123的用户的所有操作
"*:view"               // 查看所有资源
"*"                    // 所有权限

// 检查权限
subject.isPermitted("user:create");
subject.isPermitted("user:update:123");
subject.isPermittedAll("user:view", "user:create");
```

### 优缺点

**优点：**
- ✅ 简单易学，上手快
- ✅ 轻量级，无依赖Spring
- ✅ 灵活的权限模型
- ✅ 良好的会话管理
- ✅ 支持多种数据源
- ✅ 代码侵入性小

**缺点：**
- ❌ 社区不如Spring Security活跃
- ❌ OAuth2/OIDC支持较弱
- ❌ 现代协议支持有限
- ❌ 更新频率较低
- ❌ 与Spring Boot集成不如Spring Security顺滑

### 适用场景

- 独立的Java应用
- 简单的Web应用
- 不使用Spring的项目
- 对安全需求不太复杂的场景
- 需要灵活权限模型的项目

---

## JAAS

### 概述

**JAAS (Java Authentication and Authorization Service)** 是Java标准平台的一部分，提供了基础的认证和授权API。

**官网：** https://docs.oracle.com/javase/8/docs/technotes/guides/security/jaas/JAASRefGuide.html

### 核心概念

```
LoginContext
    │
    ├─ LoginModule (认证模块)
    │       │
    │       ├─ JndiLoginModule
    │       ├─ Krb5LoginModule
    │       └─ Custom LoginModule
    │
    └─ Subject (认证主体)
            │
            ├─ Principals (身份)
            └─ Credentials (凭证)
```

### 配置示例

**JAAS配置文件（jaas.conf）：**
```
MyApp {
    com.example.MyLoginModule required
        debug=true
        userDatabase="users.properties";
        
    com.sun.security.auth.module.Krb5LoginModule optional
        useTicketCache=true;
};
```

**自定义LoginModule：**
```java
/**
 * 自定义登录模块
 */
public class MyLoginModule implements LoginModule {
    
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;
    
    private boolean succeeded = false;
    private boolean commitSucceeded = false;
    private String username;
    private char[] password;
    private Principal userPrincipal;
    
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                          Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }
    
    @Override
    public boolean login() throws LoginException {
        // 创建回调获取用户名和密码
        NameCallback nameCallback = new NameCallback("Username: ");
        PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
        
        try {
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
            username = nameCallback.getName();
            password = passwordCallback.getPassword();
            passwordCallback.clearPassword();
            
            // 验证用户名和密码
            if (authenticate(username, password)) {
                succeeded = true;
                return true;
            } else {
                throw new FailedLoginException("认证失败");
            }
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        }
    }
    
    @Override
    public boolean commit() throws LoginException {
        if (!succeeded) {
            return false;
        }
        
        // 创建Principal并添加到Subject
        userPrincipal = new UserPrincipal(username);
        subject.getPrincipals().add(userPrincipal);
        
        // 清除密码
        username = null;
        Arrays.fill(password, ' ');
        password = null;
        
        commitSucceeded = true;
        return true;
    }
    
    @Override
    public boolean abort() throws LoginException {
        if (!succeeded) {
            return false;
        } else if (succeeded && !commitSucceeded) {
            // 登录成功但提交失败
            succeeded = false;
            username = null;
            if (password != null) {
                Arrays.fill(password, ' ');
                password = null;
            }
            userPrincipal = null;
        } else {
            // 提交成功，调用logout
            logout();
        }
        return true;
    }
    
    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        commitSucceeded = false;
        username = null;
        if (password != null) {
            Arrays.fill(password, ' ');
            password = null;
        }
        userPrincipal = null;
        return true;
    }
    
    private boolean authenticate(String username, char[] password) {
        // 实现认证逻辑
        return true;
    }
}

/**
 * 用户Principal
 */
public class UserPrincipal implements Principal, Serializable {
    private final String name;
    
    public UserPrincipal(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
}
```

**使用JAAS进行认证：**
```java
/**
 * JAAS认证示例
 */
public class JAASExample {
    
    public static void main(String[] args) {
        // 设置配置文件
        System.setProperty("java.security.auth.login.config", "jaas.conf");
        
        try {
            // 创建LoginContext
            LoginContext lc = new LoginContext("MyApp", new MyCallbackHandler());
            
            // 执行登录
            lc.login();
            
            // 获取认证的Subject
            Subject subject = lc.getSubject();
            System.out.println("认证成功: " + subject.getPrincipals());
            
            // 以特定Subject身份执行操作
            Subject.doAs(subject, (PrivilegedAction<Void>) () -> {
                // 执行需要认证的操作
                System.out.println("当前用户: " + Subject.getSubject(AccessController.getContext()));
                return null;
            });
            
            // 登出
            lc.logout();
            
        } catch (LoginException e) {
            System.err.println("认证失败: " + e.getMessage());
        }
    }
}

/**
 * 自定义CallbackHandler
 */
public class MyCallbackHandler implements CallbackHandler {
    
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                NameCallback nc = (NameCallback) callback;
                nc.setName("user");
            } else if (callback instanceof PasswordCallback) {
                PasswordCallback pc = (PasswordCallback) callback;
                pc.setPassword("password".toCharArray());
            } else {
                throw new UnsupportedCallbackException(callback, "Unsupported callback");
            }
        }
    }
}
```

### 优缺点

**优点：**
- ✅ Java标准API，无需额外依赖
- ✅ 可插拔的认证模块
- ✅ 适合Java SE应用
- ✅ 与JVM安全模型深度集成

**缺点：**
- ❌ API较为底层，使用复杂
- ❌ 缺少现代Web安全特性
- ❌ 没有内置的授权管理
- ❌ 社区支持有限
- ❌ 不适合Web应用

### 适用场景

- Java SE桌面应用
- 需要与JVM安全模型集成
- 企业环境（Kerberos认证）
- 遗留系统维护

---

## Keycloak

### 概述

**Keycloak** 是RedHat开源的身份和访问管理（IAM）解决方案，提供完整的认证、授权、用户管理功能。

**官网：** https://www.keycloak.org/

### 核心特性

1. **单点登录（SSO）**
   - OAuth 2.0
   - OpenID Connect
   - SAML 2.0

2. **身份代理（Identity Brokering）**
   - 集成外部IdP（Google、Facebook、GitHub等）
   - LDAP/Active Directory集成

3. **用户联邦（User Federation）**
   - LDAP/AD同步
   - 自定义用户存储

4. **管理功能**
   - Web管理控制台
   - 用户管理
   - 角色和权限管理
   - 客户端管理

5. **安全特性**
   - 多因素认证（MFA）
   - 密码策略
   - 会话管理
   - 暴力破解防护

### 架构

```
┌──────────────────────────────────────────────────────┐
│                 Keycloak Server                       │
│  ┌────────────────────────────────────────────────┐  │
│  │          Admin Console (管理界面)               │  │
│  └────────────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────────────┐  │
│  │     Authentication SPI (认证服务)               │  │
│  │  OAuth2 | OIDC | SAML | Custom                 │  │
│  └────────────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────────────┐  │
│  │         User Storage SPI (用户存储)             │  │
│  │  Database | LDAP | Custom                       │  │
│  └────────────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────────────┐  │
│  │        Identity Provider (身份提供者)           │  │
│  │  Google | Facebook | GitHub | SAML             │  │
│  └────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────┘
                      │
        ┌─────────────┼─────────────┐
        ▼             ▼             ▼
    ┌───────┐    ┌───────┐    ┌───────┐
    │ App A │    │ App B │    │ App C │
    └───────┘    └───────┘    └───────┘
```

### 快速开始

**1. 使用Docker启动Keycloak：**
```bash
docker run -d \
  --name keycloak \
  -p 8080:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:latest \
  start-dev
```

**2. 访问管理控制台：**
```
URL: http://localhost:8080
用户名: admin
密码: admin
```

**3. 创建Realm、Client、User**

**4. Spring Boot集成：**

```xml
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-spring-boot-starter</artifactId>
    <version>23.0.0</version>
</dependency>
```

```yaml
# application.yml
keycloak:
  realm: myrealm
  auth-server-url: http://localhost:8080
  resource: my-app
  credentials:
    secret: your-client-secret
  use-resource-role-mappings: true
  bearer-only: true

spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: my-app
            client-secret: your-client-secret
            scope: openid,profile,email
            authorization-grant-type: authorization_code
            redirect-uri: "{baseUrl}/login/oauth2/code/keycloak"
        provider:
          keycloak:
            issuer-uri: http://localhost:8080/realms/myrealm
            user-name-attribute: preferred_username
```

```java
/**
 * Keycloak配置
 */
@Configuration
@EnableWebSecurity
public class KeycloakSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            );
        
        return http.build();
    }
}

/**
 * 使用Keycloak用户信息
 */
@RestController
public class UserController {
    
    @GetMapping("/api/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal OidcUser user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", user.getPreferredUsername());
        userInfo.put("email", user.getEmail());
        userInfo.put("name", user.getFullName());
        userInfo.put("roles", user.getAuthorities());
        return userInfo;
    }
}
```

### 优缺点

**优点：**
- ✅ 功能全面，开箱即用
- ✅ 完整的管理界面
- ✅ 支持多种协议
- ✅ 强大的用户管理
- ✅ 多租户支持
- ✅ 活跃的社区
- ✅ 适合微服务架构

**缺点：**
- ❌ 相对重量级
- ❌ 需要独立部署
- ❌ 学习曲线较陡
- ❌ 资源占用较多
- ❌ 定制化可能复杂

### 适用场景

- 企业SSO解决方案
- 微服务架构
- 多应用统一认证
- 需要用户管理界面
- OAuth2/OIDC标准化实现
- 多租户SaaS应用

---

## Pac4j

### 概述

**Pac4j** 是一个通用的Java安全框架，支持多种认证协议，可以集成到各种Java Web框架中。

**官网：** https://www.pac4j.org/

### 核心特性

1. **多协议支持**
   - OAuth 2.0
   - OIDC
   - SAML
   - CAS
   - HTTP (Basic, Digest, Form)
   - JWT
   - LDAP
   - Database

2. **多框架集成**
   - Spring Boot/Spring MVC
   - Jakarta EE
   - Play Framework
   - Vert.x
   - Spark Java
   - Ratpack

### 快速开始

```xml
<dependency>
    <groupId>org.pac4j</groupId>
    <artifactId>spring-webmvc-pac4j</artifactId>
    <version>6.0.0</version>
</dependency>
<dependency>
    <groupId>org.pac4j</groupId>
    <artifactId>pac4j-oauth</artifactId>
    <version>5.7.0</version>
</dependency>
<dependency>
    <groupId>org.pac4j</groupId>
    <artifactId>pac4j-oidc</artifactId>
    <version>5.7.0</version>
</dependency>
```

```java
/**
 * Pac4j配置
 */
@Configuration
public class Pac4jConfig {
    
    @Bean
    public Config config() {
        // OAuth配置
        final GitHubClient gitHubClient = new GitHubClient("clientId", "clientSecret");
        final GoogleOidcClient googleOidcClient = new GoogleOidcClient("clientId", "clientSecret");
        
        // 表单认证
        final FormClient formClient = new FormClient("/login", 
            new SimpleTestUsernamePasswordAuthenticator());
        
        // JWT认证
        final JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
        jwtAuthenticator.addSignatureConfiguration(new SecretSignatureConfiguration("secret"));
        final HeaderClient jwtClient = new HeaderClient("Authorization", "Bearer ", jwtAuthenticator);
        
        // 创建Clients
        final Clients clients = new Clients("/callback", 
            gitHubClient, googleOidcClient, formClient, jwtClient);
        
        // 创建Config
        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ADMIN"));
        config.addAuthorizer("custom", new CustomAuthorizer());
        
        return config;
    }
    
    @Bean
    public SecurityInterceptor securityInterceptor(Config config) {
        return new SecurityInterceptor(config, "FormClient");
    }
}

/**
 * 使用Pac4j保护端点
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping("/profile")
    public UserProfile getProfile(@RequestAttribute("pac4jUserProfile") UserProfile profile) {
        return profile;
    }
    
    @GetMapping("/admin")
    @RequireAnyRole("ADMIN")
    public String adminArea() {
        return "Admin area";
    }
}
```

### 优缺点

**优点：**
- ✅ 支持多种认证协议
- ✅ 框架无关，易于集成
- ✅ 配置灵活
- ✅ 文档清晰

**缺点：**
- ❌ 社区相对较小
- ❌ 不如Spring Security全面
- ❌ 需要手动配置较多
- ❌ 生态不如主流框架丰富

### 适用场景

- 需要支持多种认证协议
- 非Spring项目
- 需要框架无关的解决方案
- 快速集成第三方登录

---

## 框架选型建议

### 决策树

```
需要完整的IAM解决方案？
    ├─ 是 → Keycloak
    └─ 否 ↓

使用Spring生态？
    ├─ 是 → Spring Security
    └─ 否 ↓

需要多协议支持？
    ├─ 是 → Pac4j
    └─ 否 ↓

需求简单且轻量级？
    ├─ 是 → Apache Shiro
    └─ 否 ↓

Java SE应用？
    ├─ 是 → JAAS
    └─ 否 → Spring Security (最全面)
```

### 场景推荐

| 场景 | 推荐框架 | 理由 |
|------|---------|------|
| Spring Boot微服务 | Spring Security | 深度集成，完整支持 |
| 企业SSO | Keycloak | 开箱即用的IAM平台 |
| 简单Web应用 | Apache Shiro | 轻量级，易上手 |
| 多应用SSO | Keycloak + Spring Security | Keycloak提供认证中心 |
| 非Spring项目 | Pac4j 或 Apache Shiro | 框架无关 |
| 桌面应用 | JAAS | Java标准API |
| OAuth/OIDC客户端 | Pac4j | 多协议支持好 |
| 复杂权限需求 | Spring Security | 强大的授权机制 |

### 技术栈对应

```
Spring Boot → Spring Security (首选)
Jakarta EE → JAAS (标准) 或 Pac4j (现代)
Play Framework → Pac4j
Vert.x → Pac4j
传统Servlet → Shiro 或 Spring Security
微服务架构 → Keycloak + Spring Security
```

---

## 总结

本文对比了Java生态中主流的认证授权框架：

1. **Spring Security** - 功能最全面，Spring生态首选
2. **Apache Shiro** - 轻量级，简单易用
3. **JAAS** - Java标准API，适合Java SE
4. **Keycloak** - 完整的IAM平台，企业SSO首选
5. **Pac4j** - 多协议支持，框架无关

**选择建议：**
- 新项目使用Spring Boot → **Spring Security**
- 需要SSO和用户管理 → **Keycloak**
- 简单项目或非Spring → **Apache Shiro**
- 需要多协议集成 → **Pac4j**

**继续学习：**
- [上一章：认证协议与标准](./02-认证协议与标准.md)
- [下一章：Spring Security核心架构](./04-SpringSecurity核心架构.md)

