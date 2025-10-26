# Spring Security核心架构

## 目录
- [架构概览](#架构概览)
- [过滤器链机制](#过滤器链机制)
- [认证架构](#认证架构)
- [授权架构](#授权架构)
- [核心组件详解](#核心组件详解)
- [Security Context](#security-context)
- [自定义扩展](#自定义扩展)

---

## 架构概览

### Spring Security架构图

```
HTTP Request
    │
    ▼
┌─────────────────────────────────────────────────────┐
│         DelegatingFilterProxy (Servlet Filter)       │
│              (由Spring容器管理)                       │
└─────────────────┬───────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────┐
│           FilterChainProxy (springSecurityFilterChain)│
│                Bean name: "springSecurityFilterChain" │
└─────────────────┬───────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────┐
│              SecurityFilterChain                     │
│  ┌───────────────────────────────────────────────┐  │
│  │  1. SecurityContextPersistenceFilter          │  │
│  │  2. CsrfFilter                                │  │
│  │  3. LogoutFilter                              │  │
│  │  4. UsernamePasswordAuthenticationFilter      │  │
│  │  5. BasicAuthenticationFilter                 │  │
│  │  6. RequestCacheAwareFilter                   │  │
│  │  7. SecurityContextHolderAwareRequestFilter   │  │
│  │  8. AnonymousAuthenticationFilter             │  │
│  │  9. SessionManagementFilter                   │  │
│  │ 10. ExceptionTranslationFilter                │  │
│  │ 11. FilterSecurityInterceptor (授权)          │  │
│  └───────────────────────────────────────────────┘  │
└─────────────────┬───────────────────────────────────┘
                  │
                  ▼
            Application Code
```

### 核心模块

```
┌──────────────────────────────────────────────────┐
│          Spring Security Core                     │
├──────────────────────────────────────────────────┤
│  Authentication (认证)                            │
│  • AuthenticationManager                         │
│  • AuthenticationProvider                        │
│  • UserDetailsService                            │
├──────────────────────────────────────────────────┤
│  Authorization (授权)                             │
│  • AccessDecisionManager                         │
│  • AccessDecisionVoter                           │
│  • SecurityMetadataSource                        │
├──────────────────────────────────────────────────┤
│  Security Context (安全上下文)                    │
│  • SecurityContext                               │
│  • SecurityContextHolder                         │
├──────────────────────────────────────────────────┤
│  Configuration (配置)                             │
│  • SecurityFilterChain                           │
│  • HttpSecurity                                  │
│  • WebSecurity                                   │
└──────────────────────────────────────────────────┘
```

---

## 过滤器链机制

### 过滤器链工作原理

Spring Security的核心是一系列过滤器（Filter），按照特定顺序组成过滤器链。

**过滤器执行顺序：**

```java
/**
 * Spring Security默认过滤器链顺序
 */
public enum FilterOrderEnum {
    
    FIRST(-100),
    
    CHANNEL_FILTER(100),  // 强制HTTPS
    
    SECURITY_CONTEXT_FILTER(200),  // SecurityContextPersistenceFilter
    
    CONCURRENT_SESSION_FILTER(300),
    
    HEADERS_FILTER(400),  // 添加安全响应头
    
    CSRF_FILTER(500),  // CSRF防护
    
    LOGOUT_FILTER(600),  // 登出处理
    
    X509_FILTER(700),
    
    PRE_AUTH_FILTER(800),
    
    CAS_FILTER(900),
    
    FORM_LOGIN_FILTER(1000),  // UsernamePasswordAuthenticationFilter
    
    OPENID_FILTER(1100),
    
    LOGIN_PAGE_FILTER(1200),
    
    DIGEST_AUTH_FILTER(1300),
    
    BEARER_TOKEN_AUTH_FILTER(1400),  // OAuth2/JWT
    
    BASIC_AUTH_FILTER(1500),  // BasicAuthenticationFilter
    
    REQUEST_CACHE_FILTER(1600),
    
    SERVLET_API_FILTER(1700),
    
    JAAS_API_FILTER(1800),
    
    REMEMBER_ME_FILTER(1900),
    
    ANONYMOUS_FILTER(2000),  // 匿名认证
    
    SESSION_MANAGEMENT_FILTER(2100),
    
    EXCEPTION_TRANSLATION_FILTER(2200),  // 异常处理
    
    FILTER_SECURITY_INTERCEPTOR(2300),  // 授权决策
    
    SWITCH_USER_FILTER(2400),
    
    LAST(Integer.MAX_VALUE);
    
    private final int order;
}
```

### 关键过滤器详解

#### 1. SecurityContextPersistenceFilter

**作用：** 在请求开始时加载SecurityContext，请求结束时保存SecurityContext。

```java
/**
 * SecurityContextPersistenceFilter简化实现
 */
public class SecurityContextPersistenceFilter extends GenericFilterBean {
    
    private SecurityContextRepository repo = new HttpSessionSecurityContextRepository();
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 1. 从Session加载SecurityContext
        SecurityContext contextBeforeChain = repo.loadContext(
            new HttpRequestResponseHolder(httpRequest, httpResponse)
        );
        
        try {
            // 2. 设置到SecurityContextHolder
            SecurityContextHolder.setContext(contextBeforeChain);
            
            // 3. 继续过滤器链
            chain.doFilter(httpRequest, httpResponse);
            
        } finally {
            // 4. 请求结束后，保存SecurityContext到Session
            SecurityContext contextAfterChain = SecurityContextHolder.getContext();
            SecurityContextHolder.clearContext();
            repo.saveContext(contextAfterChain, httpRequest, httpResponse);
        }
    }
}
```

#### 2. UsernamePasswordAuthenticationFilter

**作用：** 处理表单登录请求。

```java
/**
 * UsernamePasswordAuthenticationFilter核心逻辑
 */
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    
    public UsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                               HttpServletResponse response) 
            throws AuthenticationException {
        
        // 1. 从请求中提取用户名和密码
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        
        // 2. 创建未认证的Authentication对象
        UsernamePasswordAuthenticationToken authRequest = 
            new UsernamePasswordAuthenticationToken(username, password);
        
        // 3. 设置详情（IP地址等）
        setDetails(request, authRequest);
        
        // 4. 委托给AuthenticationManager进行认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }
    
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
    }
}
```

#### 3. BasicAuthenticationFilter

**作用：** 处理HTTP Basic认证。

```java
/**
 * BasicAuthenticationFilter核心逻辑
 */
public class BasicAuthenticationFilter extends OncePerRequestFilter {
    
    private AuthenticationManager authenticationManager;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain chain) 
            throws IOException, ServletException {
        
        // 1. 检查Authorization头
        String header = request.getHeader("Authorization");
        
        if (header == null || !header.toLowerCase().startsWith("basic ")) {
            chain.doFilter(request, response);
            return;
        }
        
        try {
            // 2. 解析Base64编码的凭证
            String[] tokens = extractAndDecodeHeader(header);
            String username = tokens[0];
            String password = tokens[1];
            
            // 3. 创建认证令牌
            UsernamePasswordAuthenticationToken authRequest = 
                new UsernamePasswordAuthenticationToken(username, password);
            
            // 4. 认证
            Authentication authResult = authenticationManager.authenticate(authRequest);
            
            // 5. 设置到SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authResult);
            
            chain.doFilter(request, response);
            
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            // 返回401响应
            response.setHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    
    private String[] extractAndDecodeHeader(String header) throws IOException {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded = Base64.getDecoder().decode(base64Token);
        String token = new String(decoded, StandardCharsets.UTF_8);
        
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
```

#### 4. ExceptionTranslationFilter

**作用：** 处理认证和授权异常。

```java
/**
 * ExceptionTranslationFilter核心逻辑
 */
public class ExceptionTranslationFilter extends GenericFilterBean {
    
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AccessDeniedHandler accessDeniedHandler;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (AuthenticationException e) {
            // 处理认证异常（401）
            handleAuthenticationException(request, response, e);
        } catch (AccessDeniedException e) {
            // 处理授权异常（403）
            handleAccessDeniedException(request, response, e);
        }
    }
    
    private void handleAuthenticationException(ServletRequest request,
                                              ServletResponse response,
                                              AuthenticationException e) 
            throws IOException, ServletException {
        // 保存请求信息，认证后可以重定向回来
        requestCache.saveRequest((HttpServletRequest) request, (HttpServletResponse) response);
        
        // 触发认证入口点（通常是跳转到登录页）
        authenticationEntryPoint.commence(
            (HttpServletRequest) request, 
            (HttpServletResponse) response, 
            e
        );
    }
    
    private void handleAccessDeniedException(ServletRequest request,
                                            ServletResponse response,
                                            AccessDeniedException e) 
            throws IOException, ServletException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // 未认证用户访问受保护资源 → 重定向到登录页
            authenticationEntryPoint.commence(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                new InsufficientAuthenticationException("Full authentication is required")
            );
        } else {
            // 已认证但权限不足 → 403
            accessDeniedHandler.handle(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                e
            );
        }
    }
}
```

#### 5. FilterSecurityInterceptor

**作用：** 执行授权决策，检查用户是否有权限访问资源。

```java
/**
 * FilterSecurityInterceptor核心逻辑
 */
public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }
    
    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        // 1. 获取请求URL需要的权限配置
        Collection<ConfigAttribute> attributes = 
            securityMetadataSource.getAttributes(fi);
        
        // 2. 如果该URL不需要权限，直接放行
        if (attributes == null || attributes.isEmpty()) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }
        
        // 3. 获取当前认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 4. 进行授权决策
        try {
            this.accessDecisionManager.decide(authentication, fi, attributes);
        } catch (AccessDeniedException e) {
            throw e;  // 将被ExceptionTranslationFilter捕获
        }
        
        // 5. 授权通过，继续过滤器链
        fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    }
}
```

### 自定义过滤器

```java
/**
 * 自定义JWT认证过滤器
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) 
            throws ServletException, IOException {
        
        try {
            // 1. 从请求中提取JWT Token
            String token = extractToken(request);
            
            // 2. 验证Token
            if (token != null && tokenProvider.validateToken(token)) {
                
                // 3. 从Token获取用户名
                String username = tokenProvider.getUsername(token);
                
                // 4. 加载用户详情
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // 5. 创建认证对象
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                
                authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // 6. 设置到SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }
        
        // 7. 继续过滤器链
        filterChain.doFilter(request, response);
    }
    
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

/**
 * 配置自定义过滤器
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            // 在UsernamePasswordAuthenticationFilter之前添加JWT过滤器
            .addFilterBefore(jwtAuthenticationFilter, 
                            UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

---

## 认证架构

### 认证流程

```
                    attemptAuthentication()
UsernamePasswordAuthenticationFilter
            │
            ▼
┌───────────────────────────────────────┐
│      AuthenticationManager            │
│    (通常是ProviderManager)             │
└───────────┬───────────────────────────┘
            │ authenticate()
            ▼
┌───────────────────────────────────────┐
│   AuthenticationProvider (循环尝试)    │
│   ┌─────────────────────────────────┐ │
│   │ DaoAuthenticationProvider       │ │
│   │  ├─ UserDetailsService          │ │
│   │  └─ PasswordEncoder             │ │
│   └─────────────────────────────────┘ │
│   ┌─────────────────────────────────┐ │
│   │ LdapAuthenticationProvider      │ │
│   └─────────────────────────────────┘ │
│   ┌─────────────────────────────────┐ │
│   │ CustomAuthenticationProvider    │ │
│   └─────────────────────────────────┘ │
└───────────┬───────────────────────────┘
            │
            ▼ 返回Authentication (已认证)
SecurityContextHolder.setContext()
```

### 核心接口

#### 1. Authentication

**表示认证信息的接口。**

```java
/**
 * Authentication接口
 */
public interface Authentication extends Principal, Serializable {
    
    /**
     * 权限集合
     */
    Collection<? extends GrantedAuthority> getAuthorities();
    
    /**
     * 凭证（通常是密码）
     * 认证后通常会被清除
     */
    Object getCredentials();
    
    /**
     * 详细信息（如IP地址、Session ID）
     */
    Object getDetails();
    
    /**
     * 身份信息（通常是UserDetails对象或用户名）
     */
    Object getPrincipal();
    
    /**
     * 是否已认证
     */
    boolean isAuthenticated();
    
    /**
     * 设置认证状态
     */
    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
```

**常用实现类：**

```java
// 1. UsernamePasswordAuthenticationToken
UsernamePasswordAuthenticationToken token = 
    new UsernamePasswordAuthenticationToken(
        username,      // principal
        password,      // credentials
        authorities    // authorities (认证后设置)
    );

// 2. AnonymousAuthenticationToken
AnonymousAuthenticationToken anonymous = 
    new AnonymousAuthenticationToken(
        "key",
        "anonymousUser",
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
    );

// 3. RememberMeAuthenticationToken
RememberMeAuthenticationToken rememberMe = 
    new RememberMeAuthenticationToken(
        "key",
        userDetails,
        authorities
    );
```

#### 2. AuthenticationManager

**认证管理器接口，负责协调认证过程。**

```java
/**
 * AuthenticationManager接口
 */
public interface AuthenticationManager {
    
    /**
     * 执行认证
     * @param authentication 未认证的Authentication对象
     * @return 已认证的Authentication对象
     * @throws AuthenticationException 认证失败
     */
    Authentication authenticate(Authentication authentication) 
        throws AuthenticationException;
}
```

**主要实现：ProviderManager**

```java
/**
 * ProviderManager简化实现
 */
public class ProviderManager implements AuthenticationManager {
    
    private List<AuthenticationProvider> providers;
    private AuthenticationManager parent;
    
    @Override
    public Authentication authenticate(Authentication authentication) 
            throws AuthenticationException {
        
        Class<? extends Authentication> toTest = authentication.getClass();
        AuthenticationException lastException = null;
        
        // 遍历所有Provider
        for (AuthenticationProvider provider : providers) {
            
            // 检查Provider是否支持该类型的Authentication
            if (!provider.supports(toTest)) {
                continue;
            }
            
            try {
                // 尝试认证
                Authentication result = provider.authenticate(authentication);
                
                if (result != null) {
                    // 认证成功
                    copyDetails(authentication, result);
                    return result;
                }
            } catch (AuthenticationException e) {
                lastException = e;
            }
        }
        
        // 如果有父AuthenticationManager，委托给父
        if (parent != null) {
            try {
                return parent.authenticate(authentication);
            } catch (AuthenticationException e) {
                lastException = e;
            }
        }
        
        // 所有Provider都无法认证
        if (lastException != null) {
            throw lastException;
        }
        
        throw new ProviderNotFoundException("No AuthenticationProvider found");
    }
}
```

#### 3. AuthenticationProvider

**具体的认证提供者。**

```java
/**
 * AuthenticationProvider接口
 */
public interface AuthenticationProvider {
    
    /**
     * 执行认证
     */
    Authentication authenticate(Authentication authentication) 
        throws AuthenticationException;
    
    /**
     * 是否支持该类型的Authentication
     */
    boolean supports(Class<?> authentication);
}
```

**DaoAuthenticationProvider（最常用）：**

```java
/**
 * DaoAuthenticationProvider简化实现
 */
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                 UsernamePasswordAuthenticationToken authentication) 
            throws AuthenticationException {
        
        // 1. 检查密码是否为空
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("Credentials cannot be null");
        }
        
        String presentedPassword = authentication.getCredentials().toString();
        
        // 2. 验证密码
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
    }
    
    @Override
    protected UserDetails retrieveUser(String username,
                                      UsernamePasswordAuthenticationToken authentication) 
            throws AuthenticationException {
        
        try {
            // 从UserDetailsService加载用户
            UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
            
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null");
            }
            
            return loadedUser;
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }
}
```

#### 4. UserDetailsService

**加载用户特定数据的核心接口。**

```java
/**
 * UserDetailsService接口
 */
public interface UserDetailsService {
    
    /**
     * 根据用户名加载用户信息
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```

**实现示例：**

```java
/**
 * 自定义UserDetailsService实现
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. 从数据库查询用户
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
        
        // 2. 转换为Spring Security的UserDetails
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(getAuthorities(user))
            .accountExpired(user.isAccountExpired())
            .accountLocked(user.isAccountLocked())
            .credentialsExpired(user.isCredentialsExpired())
            .disabled(!user.isEnabled())
            .build();
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        // 添加角色（注意：角色需要ROLE_前缀）
        user.getRoles().forEach(role -> 
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()))
        );
        
        // 添加权限
        user.getPermissions().forEach(permission -> 
            authorities.add(new SimpleGrantedAuthority(permission.getName()))
        );
        
        return authorities;
    }
}
```

#### 5. UserDetails

**核心用户信息接口。**

```java
/**
 * UserDetails接口
 */
public interface UserDetails extends Serializable {
    
    /**
     * 权限集合
     */
    Collection<? extends GrantedAuthority> getAuthorities();
    
    /**
     * 密码
     */
    String getPassword();
    
    /**
     * 用户名
     */
    String getUsername();
    
    /**
     * 账户是否未过期
     */
    boolean isAccountNonExpired();
    
    /**
     * 账户是否未锁定
     */
    boolean isAccountNonLocked();
    
    /**
     * 凭证是否未过期
     */
    boolean isCredentialsNonExpired();
    
    /**
     * 账户是否可用
     */
    boolean isEnabled();
}
```

**自定义UserDetails实现：**

```java
/**
 * 自定义UserDetails实现
 */
public class CustomUserDetails implements UserDetails {
    
    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;
    private final Collection<? extends GrantedAuthority> authorities;
    
    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.accountNonExpired = !user.isAccountExpired();
        this.credentialsNonExpired = !user.isCredentialsExpired();
        this.accountNonLocked = !user.isAccountLocked();
        this.authorities = mapAuthorities(user.getRoles());
    }
    
    private Collection<? extends GrantedAuthority> mapAuthorities(Set<Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
    }
    
    // Getter方法
    public Long getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
```

#### 6. PasswordEncoder

**密码编码器接口。**

```java
/**
 * PasswordEncoder接口
 */
public interface PasswordEncoder {
    
    /**
     * 编码原始密码
     */
    String encode(CharSequence rawPassword);
    
    /**
     * 验证原始密码与编码密码是否匹配
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);
    
    /**
     * 是否需要重新编码（可选，用于升级加密算法）
     */
    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
```

**常用实现：**

```java
/**
 * PasswordEncoder配置
 */
@Configuration
public class PasswordEncoderConfig {
    
    /**
     * BCrypt编码器（推荐）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);  // strength: 12
    }
    
    /**
     * 委托密码编码器（支持多种算法）
     */
    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());
        
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}

/**
 * 使用示例
 */
@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void createUser(String username, String rawPassword) {
        // 加密密码
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // 存储到数据库: {bcrypt}$2a$12$...
        
        // 验证密码
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
```

---

## 授权架构

### 授权流程

```
FilterSecurityInterceptor
        │
        ▼
┌───────────────────────────────────────┐
│  SecurityMetadataSource               │
│  获取访问资源所需的权限配置             │
└───────────┬───────────────────────────┘
            │
            ▼
┌───────────────────────────────────────┐
│  AccessDecisionManager                │
│  决定是否授权访问                      │
└───────────┬───────────────────────────┘
            │
            ▼
┌───────────────────────────────────────┐
│  AccessDecisionVoter (投票器)         │
│  ┌─────────────────────────────────┐ │
│  │ RoleVoter                       │ │
│  │ AuthenticatedVoter              │ │
│  │ WebExpressionVoter              │ │
│  │ Custom Voter                    │ │
│  └─────────────────────────────────┘ │
└───────────┬───────────────────────────┘
            │
            ▼
      ACCESS_GRANTED / ACCESS_DENIED
```

### 核心接口

#### 1. AccessDecisionManager

```java
/**
 * AccessDecisionManager接口
 */
public interface AccessDecisionManager {
    
    /**
     * 决定是否授予访问权限
     * @param authentication 当前认证信息
     * @param object 被保护的对象（FilterInvocation、MethodInvocation等）
     * @param configAttributes 访问所需的权限配置
     * @throws AccessDeniedException 如果访问被拒绝
     */
    void decide(Authentication authentication, Object object,
               Collection<ConfigAttribute> configAttributes) 
        throws AccessDeniedException, InsufficientAuthenticationException;
    
    boolean supports(ConfigAttribute attribute);
    
    boolean supports(Class<?> clazz);
}
```

**实现策略：**

```java
/**
 * 1. AffirmativeBased - 一票通过（默认）
 * 只要有一个Voter投赞成票，就授予访问权限
 */
public class AffirmativeBased extends AbstractAccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object,
                      Collection<ConfigAttribute> configAttributes) 
            throws AccessDeniedException {
        
        int deny = 0;
        
        for (AccessDecisionVoter voter : getDecisionVoters()) {
            int result = voter.vote(authentication, object, configAttributes);
            
            switch (result) {
                case AccessDecisionVoter.ACCESS_GRANTED:
                    return;  // 通过
                    
                case AccessDecisionVoter.ACCESS_DENIED:
                    deny++;
                    break;
                    
                default:
                    break;
            }
        }
        
        if (deny > 0) {
            throw new AccessDeniedException("Access is denied");
        }
        
        // 检查是否允许全弃权
        checkAllowIfAllAbstainDecisions();
    }
}

/**
 * 2. ConsensusBased - 少数服从多数
 * 赞成票多于反对票则授予访问权限
 */
public class ConsensusBased extends AbstractAccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object,
                      Collection<ConfigAttribute> configAttributes) 
            throws AccessDeniedException {
        
        int grant = 0;
        int deny = 0;
        
        for (AccessDecisionVoter voter : getDecisionVoters()) {
            int result = voter.vote(authentication, object, configAttributes);
            
            switch (result) {
                case AccessDecisionVoter.ACCESS_GRANTED:
                    grant++;
                    break;
                    
                case AccessDecisionVoter.ACCESS_DENIED:
                    deny++;
                    break;
            }
        }
        
        if (grant > deny) {
            return;  // 通过
        }
        
        if (deny > grant) {
            throw new AccessDeniedException("Access is denied");
        }
        
        // 平票处理
        if ((grant == deny) && (grant != 0)) {
            if (this.allowIfEqualGrantedDeniedDecisions) {
                return;
            } else {
                throw new AccessDeniedException("Access is denied");
            }
        }
        
        checkAllowIfAllAbstainDecisions();
    }
}

/**
 * 3. UnanimousBased - 一票否决
 * 所有Voter都投赞成票才授予访问权限
 */
public class UnanimousBased extends AbstractAccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object,
                      Collection<ConfigAttribute> configAttributes) 
            throws AccessDeniedException {
        
        int grant = 0;
        
        for (AccessDecisionVoter voter : getDecisionVoters()) {
            int result = voter.vote(authentication, object, configAttributes);
            
            switch (result) {
                case AccessDecisionVoter.ACCESS_GRANTED:
                    grant++;
                    break;
                    
                case AccessDecisionVoter.ACCESS_DENIED:
                    throw new AccessDeniedException("Access is denied");
            }
        }
        
        if (grant > 0) {
            return;  // 全部赞成，通过
        }
        
        checkAllowIfAllAbstainDecisions();
    }
}
```

#### 2. AccessDecisionVoter

```java
/**
 * AccessDecisionVoter接口
 */
public interface AccessDecisionVoter<S> {
    
    int ACCESS_GRANTED = 1;   // 赞成
    int ACCESS_ABSTAIN = 0;   // 弃权
    int ACCESS_DENIED = -1;   // 反对
    
    /**
     * 投票
     */
    int vote(Authentication authentication, S object, 
            Collection<ConfigAttribute> attributes);
    
    boolean supports(ConfigAttribute attribute);
    
    boolean supports(Class<?> clazz);
}
```

**常用Voter：**

```java
/**
 * RoleVoter - 角色投票器
 */
public class RoleVoter implements AccessDecisionVoter<Object> {
    
    private String rolePrefix = "ROLE_";
    
    @Override
    public int vote(Authentication authentication, Object object,
                   Collection<ConfigAttribute> attributes) {
        
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        
        int result = ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;
                
                // 检查用户是否拥有所需角色
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        
        return result;
    }
    
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return (attribute.getAttribute() != null) 
            && attribute.getAttribute().startsWith(rolePrefix);
    }
}

/**
 * 自定义Voter示例 - IP地址投票器
 */
public class IpAddressVoter implements AccessDecisionVoter<FilterInvocation> {
    
    private static final String IP_PREFIX = "IP_";
    private static final String IP_LOCAL_HOST = "IP_LOCAL_HOST";
    
    @Override
    public int vote(Authentication authentication, FilterInvocation fi,
                   Collection<ConfigAttribute> attributes) {
        
        int result = ACCESS_ABSTAIN;
        
        for (ConfigAttribute attribute : attributes) {
            if (!this.supports(attribute)) {
                continue;
            }
            
            result = ACCESS_DENIED;
            String clientIp = fi.getRequest().getRemoteAddr();
            
            if (IP_LOCAL_HOST.equals(attribute.getAttribute())) {
                if ("127.0.0.1".equals(clientIp) || "0:0:0:0:0:0:0:1".equals(clientIp)) {
                    return ACCESS_GRANTED;
                }
            }
            
            // 可以添加更多IP检查逻辑
        }
        
        return result;
    }
    
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute() != null 
            && attribute.getAttribute().startsWith(IP_PREFIX);
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
```

---

## Security Context

### SecurityContext和SecurityContextHolder

```java
/**
 * SecurityContext接口
 */
public interface SecurityContext extends Serializable {
    
    /**
     * 获取当前认证信息
     */
    Authentication getAuthentication();
    
    /**
     * 设置认证信息
     */
    void setAuthentication(Authentication authentication);
}
```

**SecurityContextHolder - 存储SecurityContext的工具类：**

```java
/**
 * SecurityContextHolder使用示例
 */
public class SecurityContextExample {
    
    /**
     * 获取当前认证信息
     */
    public static Authentication getCurrentAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }
    
    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = getCurrentAuthentication();
        if (authentication == null) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    
    /**
     * 获取当前用户详情
     */
    public static UserDetails getCurrentUserDetails() {
        Authentication authentication = getCurrentAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
    
    /**
     * 检查是否已认证
     */
    public static boolean isAuthenticated() {
        Authentication authentication = getCurrentAuthentication();
        return authentication != null 
            && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken);
    }
    
    /**
     * 检查是否拥有角色
     */
    public static boolean hasRole(String role) {
        Authentication authentication = getCurrentAuthentication();
        if (authentication == null) {
            return false;
        }
        
        String roleWithPrefix = "ROLE_" + role;
        return authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals(roleWithPrefix));
    }
    
    /**
     * 手动设置认证信息（谨慎使用）
     */
    public static void setAuthentication(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
    
    /**
     * 清除认证信息
     */
    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}
```

**SecurityContextHolder存储策略：**

```java
/**
 * SecurityContextHolder有三种存储策略
 */
public class SecurityContextHolderExample {
    
    /**
     * 1. MODE_THREADLOCAL（默认）
     * 使用ThreadLocal存储，每个线程独立
     */
    public static void threadLocalMode() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_THREADLOCAL);
        
        // 在当前线程中设置
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        // 新线程中无法访问
        new Thread(() -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // auth 为 null
        }).start();
    }
    
    /**
     * 2. MODE_INHERITABLETHREADLOCAL
     * 使用InheritableThreadLocal，子线程可以继承父线程的SecurityContext
     */
    public static void inheritableThreadLocalMode() {
        SecurityContextHolder.setStrategyName(
            SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        // 子线程可以访问
        new Thread(() -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // auth 不为 null
        }).start();
    }
    
    /**
     * 3. MODE_GLOBAL
     * 全局模式，JVM中所有线程共享同一个SecurityContext
     * 仅适用于独立应用，不适用于服务器应用
     */
    public static void globalMode() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
    }
}
```

---

## 核心组件详解

### 1. SecurityConfigurer

```java
/**
 * SecurityConfigurer是配置Spring Security的核心接口
 */
public interface SecurityConfigurer<O, B extends SecurityBuilder<O>> {
    
    /**
     * 初始化
     */
    void init(B builder) throws Exception;
    
    /**
     * 配置
     */
    void configure(B builder) throws Exception;
}
```

### 2. HttpSecurity

```java
/**
 * HttpSecurity配置示例
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 授权配置
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/data").hasAuthority("DATA_WRITE")
                .anyRequest().authenticated()
            )
            
            // 表单登录
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            
            // 登出
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            )
            
            // 记住我
            .rememberMe(remember -> remember
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400)  // 24小时
                .rememberMeParameter("remember-me")
            )
            
            // 会话管理
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login?expired")
            )
            
            // CSRF
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            
            // 异常处理
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((request, response, ex) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied");
                })
            );
        
        return http.build();
    }
}
```

---

## 自定义扩展

### 1. 自定义AuthenticationProvider

```java
/**
 * 自定义认证提供者
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) 
            throws AuthenticationException {
        
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // 自定义认证逻辑
        User user = userService.findByUsername(username);
        
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        
        if (!user.isEnabled()) {
            throw new DisabledException("账户已禁用");
        }
        
        if (user.isLocked()) {
            throw new LockedException("账户已锁定");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        
        // 认证成功，创建Authentication对象
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
        
        return new UsernamePasswordAuthenticationToken(
            username,
            password,
            authorities
        );
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

/**
 * 配置自定义AuthenticationProvider
 */
@Configuration
public class AuthConfig {
    
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        auth.authenticationProvider(customAuthenticationProvider);
        
        return auth.build();
    }
}
```

### 2. 自定义AccessDecisionVoter

```java
/**
 * 时间段访问控制Voter
 */
public class TimeBasedVoter implements AccessDecisionVoter<Object> {
    
    private static final String TIME_PREFIX = "TIME_";
    
    @Override
    public int vote(Authentication authentication, Object object,
                   Collection<ConfigAttribute> attributes) {
        
        int result = ACCESS_ABSTAIN;
        LocalTime now = LocalTime.now();
        
        for (ConfigAttribute attribute : attributes) {
            if (!supports(attribute)) {
                continue;
            }
            
            result = ACCESS_DENIED;
            String value = attribute.getAttribute();
            
            if (value.equals("TIME_BUSINESS_HOURS")) {
                // 工作时间：9:00 - 18:00
                if (now.isAfter(LocalTime.of(9, 0)) && 
                    now.isBefore(LocalTime.of(18, 0))) {
                    return ACCESS_GRANTED;
                }
            }
        }
        
        return result;
    }
    
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute() != null 
            && attribute.getAttribute().startsWith(TIME_PREFIX);
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
```

---

## 总结

Spring Security核心架构包括：

1. **过滤器链**：请求的第一道防线，负责认证和授权的前置处理
2. **认证架构**：AuthenticationManager → AuthenticationProvider → UserDetailsService
3. **授权架构**：AccessDecisionManager → AccessDecisionVoter
4. **Security Context**：存储和传递认证信息
5. **扩展机制**：提供丰富的扩展点，支持自定义认证和授权逻辑

理解这些核心组件的交互方式是掌握Spring Security的关键。

**继续学习：**
- [上一章：Java认证框架对比](./03-Java认证框架对比.md)
- [下一章：Spring Security实战配置](./05-SpringSecurity实战配置.md)

