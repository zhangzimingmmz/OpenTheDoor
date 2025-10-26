# Spring Security OAuth2集成

## 目录
- [OAuth2架构概览](#oauth2架构概览)
- [授权服务器配置](#授权服务器配置)
- [资源服务器配置](#资源服务器配置)
- [OAuth2客户端配置](#oauth2客户端配置)
- [JWT Token配置](#jwt-token配置)
- [OAuth2与Spring Security集成](#oauth2与spring-security集成)
- [实战案例](#实战案例)

---

## OAuth2架构概览

### Spring Security OAuth2组件

```
┌──────────────────────────────────────────────────────┐
│         Spring Security OAuth2 Architecture           │
├──────────────────────────────────────────────────────┤
│                                                       │
│  ┌─────────────────────────────────────────────┐    │
│  │      Authorization Server (授权服务器)       │    │
│  │  • 用户认证                                  │    │
│  │  • 授权管理                                  │    │
│  │  • Token颁发                                 │    │
│  └─────────────────────────────────────────────┘    │
│                       │                              │
│                       │ Token                        │
│                       ▼                              │
│  ┌─────────────────────────────────────────────┐    │
│  │      Resource Server (资源服务器)           │    │
│  │  • Token验证                                 │    │
│  │  • 资源保护                                  │    │
│  │  • 权限检查                                  │    │
│  └─────────────────────────────────────────────┘    │
│                       ▲                              │
│                       │ API Request                  │
│                       │                              │
│  ┌─────────────────────────────────────────────┐    │
│  │      OAuth2 Client (客户端)                 │    │
│  │  • 获取授权                                  │    │
│  │  • Token管理                                 │    │
│  │  • 调用受保护资源                            │    │
│  └─────────────────────────────────────────────┘    │
│                                                       │
└──────────────────────────────────────────────────────┘
```

### Maven依赖

```xml
<!-- Spring Boot 3.x -->
<dependencies>
    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- OAuth2授权服务器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
    </dependency>
    
    <!-- OAuth2资源服务器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
    
    <!-- OAuth2客户端 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    
    <!-- JWT支持 -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-oauth2-jose</artifactId>
    </dependency>
</dependencies>
```

---

## 授权服务器配置

### 基础授权服务器

```java
/**
 * OAuth2授权服务器配置
 * Spring Authorization Server (新版本)
 */
@Configuration
public class AuthorizationServerConfig {
    
    /**
     * 授权服务器安全配置
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) 
            throws Exception {
        
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        
        http
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());  // 启用OpenID Connect
        
        http
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint("/login")
                )
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            );
        
        return http.build();
    }
    
    /**
     * 默认安全配置
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) 
            throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());
        
        return http.build();
    }
    
    /**
     * 注册客户端
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // 客户端1：授权码模式
        RegisteredClient authorizationCodeClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("client-app")
            .clientSecret("{noop}secret")  // {noop}表示不加密（测试用）
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-app")
            .redirectUri("http://127.0.0.1:8080/authorized")
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .scope("read")
            .scope("write")
            .clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(true)  // 需要用户同意
                .build())
            .tokenSettings(TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofHours(1))
                .refreshTokenTimeToLive(Duration.ofDays(7))
                .reuseRefreshTokens(false)
                .build())
            .build();
        
        // 客户端2：客户端凭证模式（服务器间调用）
        RegisteredClient clientCredentialsClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("service-client")
            .clientSecret("{noop}service-secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scope("api.read")
            .scope("api.write")
            .build();
        
        // 保存到内存（生产环境应使用数据库）
        return new InMemoryRegisteredClientRepository(
            authorizationCodeClient, 
            clientCredentialsClient
        );
    }
    
    /**
     * JWK源（用于JWT签名）
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRSAKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    
    /**
     * 生成RSA密钥对
     */
    private RSAKey generateRSAKey() {
        KeyPair keyPair = generateRSAKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        return new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }
    
    private KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * JWT解码器
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
    
    /**
     * 授权服务器设置
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
            .issuer("http://localhost:9000")  // 授权服务器地址
            .build();
    }
}
```

### 数据库存储客户端

```java
/**
 * 使用数据库存储注册客户端
 */
@Configuration
public class JdbcClientConfig {
    
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }
    
    /**
     * 初始化客户端数据
     */
    @Bean
    public CommandLineRunner initClients(RegisteredClientRepository repository) {
        return args -> {
            RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("my-client")
                .clientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/authorized")
                .scope("read")
                .scope("write")
                .build();
            
            repository.save(client);
        };
    }
}
```

**数据库表结构（Spring提供的schema）：**

```sql
CREATE TABLE oauth2_registered_client (
    id VARCHAR(100) PRIMARY KEY,
    client_id VARCHAR(100) NOT NULL,
    client_id_issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret VARCHAR(200),
    client_secret_expires_at TIMESTAMP,
    client_name VARCHAR(200) NOT NULL,
    client_authentication_methods VARCHAR(1000) NOT NULL,
    authorization_grant_types VARCHAR(1000) NOT NULL,
    redirect_uris VARCHAR(1000),
    scopes VARCHAR(1000) NOT NULL,
    client_settings VARCHAR(2000) NOT NULL,
    token_settings VARCHAR(2000) NOT NULL,
    UNIQUE KEY uk_client_id (client_id)
);

CREATE TABLE oauth2_authorization (
    id VARCHAR(100) PRIMARY KEY,
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    authorization_grant_type VARCHAR(100) NOT NULL,
    authorized_scopes VARCHAR(1000),
    attributes TEXT,
    state VARCHAR(500),
    authorization_code_value TEXT,
    authorization_code_issued_at TIMESTAMP,
    authorization_code_expires_at TIMESTAMP,
    authorization_code_metadata TEXT,
    access_token_value TEXT,
    access_token_issued_at TIMESTAMP,
    access_token_expires_at TIMESTAMP,
    access_token_metadata TEXT,
    access_token_type VARCHAR(100),
    access_token_scopes VARCHAR(1000),
    oidc_id_token_value TEXT,
    oidc_id_token_issued_at TIMESTAMP,
    oidc_id_token_expires_at TIMESTAMP,
    oidc_id_token_metadata TEXT,
    refresh_token_value TEXT,
    refresh_token_issued_at TIMESTAMP,
    refresh_token_expires_at TIMESTAMP,
    refresh_token_metadata TEXT
);

CREATE TABLE oauth2_authorization_consent (
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name VARCHAR(200) NOT NULL,
    authorities VARCHAR(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);
```

### 自定义Token增强

```java
/**
 * 自定义JWT Token内容
 */
@Component
public class CustomTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void customize(JwtEncodingContext context) {
        if (context.getTokenType().getValue().equals("access_token")) {
            // 获取用户信息
            Authentication principal = context.getPrincipal();
            String username = principal.getName();
            
            User user = userRepository.findByUsername(username).orElse(null);
            
            if (user != null) {
                // 添加自定义声明
                context.getClaims()
                    .claim("user_id", user.getId())
                    .claim("email", user.getEmail())
                    .claim("department", user.getDepartment())
                    .claim("roles", user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()));
            }
        }
    }
}

/**
 * 注册Token自定义器
 */
@Configuration
public class TokenConfig {
    
    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource,
                                                  OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(tokenCustomizer);
        
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        
        return new DelegatingOAuth2TokenGenerator(
            jwtGenerator, 
            accessTokenGenerator, 
            refreshTokenGenerator
        );
    }
}
```

---

## 资源服务器配置

### 基础资源服务器

```java
/**
 * OAuth2资源服务器配置
 */
@Configuration
@EnableWebSecurity
public class ResourceServerConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**").authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            )
            .csrf().disable();
        
        return http.build();
    }
    
    /**
     * JWT认证转换器
     * 将JWT中的信息转换为Spring Security的Authentication
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = 
            new JwtGrantedAuthoritiesConverter();
        
        // 从JWT的"roles"字段提取角色
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        // 添加"ROLE_"前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        
        JwtAuthenticationConverter jwtAuthenticationConverter = 
            new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        
        return jwtAuthenticationConverter;
    }
    
    /**
     * JWT解码器配置
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // 方式1: 使用授权服务器的JWK Set URI
        return JwtDecoders.fromIssuerLocation("http://localhost:9000");
        
        // 方式2: 使用公钥
        // return NimbusJwtDecoder.withPublicKey(publicKey).build();
        
        // 方式3: 使用对称密钥（HMAC）
        // SecretKey key = new SecretKeySpec("secret".getBytes(), "HmacSHA256");
        // return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
```

### application.yml配置

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          # 授权服务器的JWK Set URI
          jwk-set-uri: http://localhost:9000/oauth2/jwks
          # 或使用issuer-uri（自动发现JWK Set URI）
          issuer-uri: http://localhost:9000
```

### 自定义JWT验证

```java
/**
 * 自定义JWT验证器
 */
@Component
public class CustomJwtValidator implements OAuth2TokenValidator<Jwt> {
    
    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        List<OAuth2Error> errors = new ArrayList<>();
        
        // 1. 验证audience
        List<String> audience = jwt.getAudience();
        if (audience == null || !audience.contains("my-resource-server")) {
            errors.add(new OAuth2Error("invalid_token", "Invalid audience", null));
        }
        
        // 2. 验证自定义声明
        String userId = jwt.getClaimAsString("user_id");
        if (userId == null) {
            errors.add(new OAuth2Error("invalid_token", "Missing user_id claim", null));
        }
        
        // 3. 其他自定义验证...
        
        return errors.isEmpty() ? 
            OAuth2TokenValidatorResult.success() : 
            OAuth2TokenValidatorResult.failure(errors);
    }
}

/**
 * 配置自定义验证器
 */
@Configuration
public class JwtConfig {
    
    @Autowired
    private CustomJwtValidator customJwtValidator;
    
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation("http://localhost:9000");
        
        // 添加自定义验证器
        OAuth2TokenValidator<Jwt> withIssuer = 
            JwtValidators.createDefaultWithIssuer("http://localhost:9000");
        OAuth2TokenValidator<Jwt> combinedValidator = 
            new DelegatingOAuth2TokenValidator<>(withIssuer, customJwtValidator);
        
        jwtDecoder.setJwtValidator(combinedValidator);
        
        return jwtDecoder;
    }
}
```

### 资源服务器API示例

```java
/**
 * 受保护的API端点
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", jwt.getSubject());
        userInfo.put("email", jwt.getClaimAsString("email"));
        userInfo.put("roles", jwt.getClaimAsStringList("roles"));
        userInfo.put("user_id", jwt.getClaimAsString("user_id"));
        
        return userInfo;
    }
    
    /**
     * 需要特定角色
     */
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    /**
     * 需要特定scope
     */
    @GetMapping("/data")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public List<Data> getData() {
        return dataService.findAll();
    }
    
    /**
     * 检查自定义声明
     */
    @GetMapping("/department-data")
    public List<Data> getDepartmentData(@AuthenticationPrincipal Jwt jwt) {
        String department = jwt.getClaimAsString("department");
        return dataService.findByDepartment(department);
    }
}
```

---

## OAuth2客户端配置

### 基础客户端配置

```yaml
# application.yml
spring:
  security:
    oauth2:
      client:
        registration:
          # 自定义授权服务器
          my-auth-server:
            client-id: client-app
            client-secret: secret
            client-name: My App
            scope:
              - openid
              - profile
              - read
              - write
            authorization-grant-type: authorization_code
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
          
          # GitHub OAuth
          github:
            client-id: your-github-client-id
            client-secret: your-github-client-secret
            scope:
              - read:user
              - user:email
          
          # Google OAuth
          google:
            client-id: your-google-client-id
            client-secret: your-google-client-secret
            scope:
              - openid
              - profile
              - email
        
        provider:
          my-auth-server:
            issuer-uri: http://localhost:9000
            # 或手动配置各个端点
            # authorization-uri: http://localhost:9000/oauth2/authorize
            # token-uri: http://localhost:9000/oauth2/token
            # user-info-uri: http://localhost:9000/userinfo
            # jwk-set-uri: http://localhost:9000/oauth2/jwks
```

### 客户端安全配置

```java
/**
 * OAuth2客户端安全配置
 */
@Configuration
@EnableWebSecurity
public class OAuth2ClientConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(oauth2UserService())
                )
            )
            .oauth2Client(Customizer.withDefaults());
        
        return http.build();
    }
    
    /**
     * 自定义OAuth2用户服务
     */
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        
        return userRequest -> {
            OAuth2User oauth2User = delegate.loadUser(userRequest);
            
            // 同步用户到本地数据库
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            syncUserToDatabase(registrationId, oauth2User);
            
            return oauth2User;
        };
    }
    
    private void syncUserToDatabase(String provider, OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        
        // 查找或创建用户
        User user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUsername(name);
                newUser.setProvider(provider);
                newUser.setEnabled(true);
                return userRepository.save(newUser);
            });
    }
}
```

### 使用OAuth2客户端调用API

```java
/**
 * 使用OAuth2RestTemplate调用受保护的API
 */
@Service
public class ApiClientService {
    
    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;
    
    /**
     * 调用需要OAuth2认证的API
     */
    public String callProtectedApi() {
        // 获取OAuth2 Token
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId("my-auth-server")
            .principal(SecurityContextHolder.getContext().getAuthentication())
            .build();
        
        OAuth2AuthorizedClient authorizedClient = 
            authorizedClientManager.authorize(authorizeRequest);
        
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        
        // 使用Token调用API
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:8090/api/data",
            HttpMethod.GET,
            entity,
            String.class
        );
        
        return response.getBody();
    }
    
    /**
     * 使用WebClient（响应式）
     */
    public Mono<String> callProtectedApiReactive() {
        return webClient
            .get()
            .uri("http://localhost:8090/api/data")
            .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction
                .clientRegistrationId("my-auth-server"))
            .retrieve()
            .bodyToMono(String.class);
    }
    
    @Bean
    public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
            new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        
        return WebClient.builder()
            .apply(oauth2Client.oauth2Configuration())
            .build();
    }
}
```

---

## JWT Token配置

### JWT生成和验证

```java
/**
 * JWT工具类
 */
@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration:3600000}")
    private long expiration;
    
    private Key key;
    
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * 生成JWT Token
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }
    
    /**
     * 从Token获取用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        
        return claims.getSubject();
    }
    
    /**
     * 验证Token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
```

### RS256算法（非对称加密）

```java
/**
 * 使用RSA密钥对进行JWT签名
 */
@Configuration
public class JwtRSAConfig {
    
    @Value("${jwt.private-key}")
    private RSAPrivateKey privateKey;
    
    @Value("${jwt.public-key}")
    private RSAPublicKey publicKey;
    
    /**
     * JWT编码器（用于生成Token）
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    
    /**
     * JWT解码器（用于验证Token）
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
    
    /**
     * 生成Token
     */
    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(authentication.getName())
            .claim("scope", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" ")))
            .build();
        
        return jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
```

**生成RSA密钥对：**

```bash
# 生成私钥
openssl genrsa -out private.pem 2048

# 从私钥生成公钥
openssl rsa -in private.pem -pubout -out public.pem

# 转换为PKCS8格式（Java需要）
openssl pkcs8 -topk8 -inform PEM -in private.pem -out private_key.pem -nocrypt
```

**配置文件：**

```yaml
jwt:
  private-key: classpath:private_key.pem
  public-key: classpath:public.pem
```

---

## OAuth2与Spring Security集成

### 统一认证入口

```java
/**
 * 同时支持OAuth2和本地认证
 */
@Configuration
@EnableWebSecurity
public class UnifiedAuthConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/login", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
            )
            
            // 表单登录（本地用户）
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
            )
            
            // OAuth2登录（第三方用户）
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
            )
            
            // 资源服务器（JWT验证）
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            );
        
        return http.build();
    }
}
```

### 统一用户信息

```java
/**
 * 统一的用户信息接口
 */
public interface UnifiedUser {
    String getUsername();
    String getEmail();
    Collection<? extends GrantedAuthority> getAuthorities();
}

/**
 * 本地用户
 */
public class LocalUser implements UnifiedUser, UserDetails {
    // UserDetails实现...
}

/**
 * OAuth2用户适配器
 */
public class OAuth2UserAdapter implements UnifiedUser {
    
    private final OAuth2User oauth2User;
    
    public OAuth2UserAdapter(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }
    
    @Override
    public String getUsername() {
        return oauth2User.getAttribute("name");
    }
    
    @Override
    public String getEmail() {
        return oauth2User.getAttribute("email");
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }
}

/**
 * 获取当前用户的工具类
 */
@Component
public class CurrentUserService {
    
    public UnifiedUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof UserDetails) {
            return (LocalUser) principal;
        } else if (principal instanceof OAuth2User) {
            return new OAuth2UserAdapter((OAuth2User) principal);
        } else if (principal instanceof Jwt) {
            return new JwtUserAdapter((Jwt) principal);
        }
        
        return null;
    }
}
```

---

## 实战案例

### 完整的微服务认证架构

```
┌─────────────────┐
│  Frontend (SPA)  │
└────────┬─────────┘
         │
         │ OAuth2 Login / JWT
         │
┌────────▼──────────────────────────────────────┐
│        API Gateway (资源服务器)                 │
│  • JWT验证                                     │
│  • 路由转发                                     │
└────────┬──────────────────────────────────────┘
         │
    ┌────┴────┬────────────┬────────────┐
    │         │            │            │
┌───▼───┐ ┌──▼───┐   ┌───▼────┐  ┌───▼────┐
│ User  │ │Order │   │Product │  │Payment │
│Service│ │Service│   │Service │  │Service │
└───────┘ └──────┘   └────────┘  └────────┘
         (微服务 - 都是资源服务器)

┌──────────────────────────────┐
│  Authorization Server        │
│  • 用户认证                   │
│  • Token颁发                  │
│  • 用户管理                   │
└──────────────────────────────┘
```

**API Gateway配置：**

```java
/**
 * API网关 - 资源服务器配置
 */
@Configuration
public class GatewaySecurityConfig {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/api/public/**").permitAll()
                .pathMatchers("/api/admin/**").hasRole("ADMIN")
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            );
        
        return http.build();
    }
}
```

**微服务间调用（Client Credentials模式）：**

```java
/**
 * 服务间调用 - 使用Client Credentials
 */
@Service
public class OrderService {
    
    @Autowired
    private WebClient webClient;
    
    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;
    
    public Mono<Product> getProduct(String productId) {
        return webClient
            .get()
            .uri("http://product-service/api/products/{id}", productId)
            .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction
                .clientRegistrationId("product-service-client"))
            .retrieve()
            .bodyToMono(Product.class);
    }
}

/**
 * OAuth2配置
 */
@Configuration
public class OAuth2Config {
    
    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
        
        OAuth2AuthorizedClientProvider authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .clientCredentials()  // 服务间调用
                .build();
        
        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
            new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientRepository);
        
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        
        return authorizedClientManager;
    }
}
```

---

## 总结

本章介绍了Spring Security OAuth2的完整集成：

1. **授权服务器**：Token颁发、客户端管理、自定义Token
2. **资源服务器**：JWT验证、权限检查、自定义验证
3. **OAuth2客户端**：第三方登录、API调用
4. **JWT配置**：对称/非对称加密、Token生成和验证
5. **微服务架构**：完整的认证授权解决方案

**最佳实践：**
- 使用授权码+PKCE模式（前端应用）
- 使用Client Credentials模式（服务间调用）
- JWT使用RS256算法（非对称加密）
- Token有效期设置合理（Access Token短期，Refresh Token长期）
- 实现Token刷新机制

**继续学习：**
- [上一章：Spring Security实战配置](./05-SpringSecurity实战配置.md)
- [下一章：代码示例集](./07-代码示例集.md)

