# 认证授权学习文档体系

> 一套完整的Java认证授权学习资料，从基础概念到高级实践，涵盖Spring Security、OAuth2、JWT等主流技术栈。

## 📚 文档目录

### 基础篇

#### [01 - 认证授权基础](./01-认证授权基础.md)
- 认证与授权的核心概念
- 会话管理机制
- 密码学基础（哈希、加密、签名）
- 常见攻击方式与防护（CSRF、XSS、SQL注入）
- 安全最佳实践

#### [02 - 认证协议与标准](./02-认证协议与标准.md)
- Cookie/Session机制详解
- Token认证（JWT、JWE、JWS）
- OAuth 2.0协议完整指南
- OpenID Connect (OIDC)
- SAML 2.0
- 单点登录(SSO)实现

### 框架篇

#### [03 - Java认证框架对比](./03-Java认证框架对比.md)
- Spring Security（最全面）
- Apache Shiro（轻量级）
- JAAS（Java标准）
- Keycloak（IAM平台）
- Pac4j（多协议）
- 框架选型决策树

### Spring Security深入篇

#### [04 - Spring Security核心架构](./04-SpringSecurity核心架构.md)
- 过滤器链机制
- 认证架构（AuthenticationManager、Provider）
- 授权架构（AccessDecisionManager、Voter）
- 核心组件详解
- Security Context管理
- 自定义扩展点

#### [05 - Spring Security实战配置](./05-SpringSecurity实战配置.md)
- 基础配置与生产环境配置
- 表单登录（成功/失败处理器）
- HTTP Basic认证
- Remember-Me功能
- 会话管理与并发控制
- CSRF防护配置
- 方法级安全

#### [06 - Spring Security OAuth2集成](./06-SpringSecurity-OAuth2.md)
- 授权服务器配置（Spring Authorization Server）
- 资源服务器配置
- OAuth2客户端配置
- JWT Token生成与验证（RS256/HS256）
- Token自定义与增强
- 微服务OAuth2架构

### 实战篇

#### [07 - 代码示例集](./07-代码示例集.md)
- 基础用户名密码认证（完整项目）
- JWT Token实现（Access Token + Refresh Token）
- OAuth2授权码模式示例
- 微服务认证架构
- 前后端分离认证方案
- 前端集成示例（JavaScript）

#### [08 - 高级主题与最佳实践](./08-高级主题与最佳实践.md)
- 多租户认证（数据隔离、租户识别）
- 分布式会话管理（Redis Session）
- 微服务安全架构（网关认证、服务间调用）
- 性能优化策略（缓存、批量处理）
- 审计日志与监控
- 安全最佳实践汇总

---

## 🚀 学习路径

### 初级阶段（1-2周）
1. 阅读 [认证授权基础](./01-认证授权基础.md)，理解核心概念
2. 阅读 [认证协议与标准](./02-认证协议与标准.md)，了解主流协议
3. 实践：搭建简单的用户名密码认证系统

### 中级阶段（2-3周）
4. 阅读 [Java认证框架对比](./03-Java认证框架对比.md)，选择适合的框架
5. 深入学习 [Spring Security核心架构](./04-SpringSecurity核心架构.md)
6. 实践 [Spring Security实战配置](./05-SpringSecurity实战配置.md)
7. 实践：使用Spring Security构建完整的认证授权系统

### 高级阶段（3-4周）
8. 学习 [Spring Security OAuth2集成](./06-SpringSecurity-OAuth2.md)
9. 参考 [代码示例集](./07-代码示例集.md) 实现JWT、OAuth2
10. 研究 [高级主题与最佳实践](./08-高级主题与最佳实践.md)
11. 实践：构建微服务认证架构、实现多租户系统

---

## 💡 快速查找

### 按需求查找

| 需求 | 推荐章节 |
|------|---------|
| 了解基础概念 | [01-基础](./01-认证授权基础.md) |
| 学习JWT | [02-协议](./02-认证协议与标准.md) → [07-示例](./07-代码示例集.md) |
| 学习OAuth2 | [02-协议](./02-认证协议与标准.md) → [06-OAuth2](./06-SpringSecurity-OAuth2.md) |
| 选择框架 | [03-框架对比](./03-Java认证框架对比.md) |
| Spring Security入门 | [04-架构](./04-SpringSecurity核心架构.md) → [05-配置](./05-SpringSecurity实战配置.md) |
| 实战代码 | [07-代码示例集](./07-代码示例集.md) |
| 微服务安全 | [08-高级主题](./08-高级主题与最佳实践.md) |
| 性能优化 | [08-高级主题](./08-高级主题与最佳实践.md#性能优化策略) |
| 安全审计 | [08-高级主题](./08-高级主题与最佳实践.md#审计日志与监控) |

### 按技术栈查找

| 技术栈 | 相关章节 |
|--------|---------|
| **Spring Boot** | [03](./03-Java认证框架对比.md), [04](./04-SpringSecurity核心架构.md), [05](./05-SpringSecurity实战配置.md), [06](./06-SpringSecurity-OAuth2.md), [07](./07-代码示例集.md) |
| **JWT** | [02](./02-认证协议与标准.md), [06](./06-SpringSecurity-OAuth2.md), [07](./07-代码示例集.md) |
| **OAuth 2.0** | [02](./02-认证协议与标准.md), [06](./06-SpringSecurity-OAuth2.md) |
| **微服务** | [06](./06-SpringSecurity-OAuth2.md), [07](./07-代码示例集.md), [08](./08-高级主题与最佳实践.md) |
| **Redis** | [01](./01-认证授权基础.md), [08](./08-高级主题与最佳实践.md) |

---

## 🎯 核心知识点速查

### 认证方式对比

| 方式 | 适用场景 | 优点 | 缺点 |
|------|---------|------|------|
| **Session/Cookie** | 传统Web应用 | 简单、服务器控制强 | 不适合分布式 |
| **JWT Token** | 前后端分离、微服务 | 无状态、易扩展 | 无法主动吊销 |
| **OAuth2** | 第三方登录、开放API | 标准化、安全 | 实现复杂 |
| **SAML** | 企业SSO | 成熟、功能全 | 重量级 |

### Spring Security核心组件

```
SecurityFilterChain
    ├─ SecurityContextPersistenceFilter
    ├─ UsernamePasswordAuthenticationFilter
    │       └─ AuthenticationManager
    │               └─ AuthenticationProvider
    │                       └─ UserDetailsService
    └─ FilterSecurityInterceptor
            └─ AccessDecisionManager
                    └─ AccessDecisionVoter
```

### OAuth2授权模式

1. **授权码模式（Authorization Code）** - 最安全，适用于有后端的应用
2. **隐式模式（Implicit）** - 已弃用，不推荐
3. **密码模式（Password）** - 高度信任的应用
4. **客户端模式（Client Credentials）** - 服务器间调用

---

## 🔧 实践项目建议

### 初级项目
- [ ] 基础用户注册登录系统
- [ ] Session管理与Remember-Me功能
- [ ] 角色权限管理（RBAC）

### 中级项目
- [ ] JWT Token认证系统（含刷新机制）
- [ ] 集成第三方OAuth2登录（GitHub、Google）
- [ ] 实现API速率限制和安全审计

### 高级项目
- [ ] 微服务认证授权架构（Gateway + JWT）
- [ ] 多租户SaaS平台认证系统
- [ ] 完整的IAM（身份访问管理）系统

---

## 📖 扩展阅读

### 官方文档
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Authorization Server](https://spring.io/projects/spring-authorization-server)
- [RFC 6749 - OAuth 2.0](https://datatracker.ietf.org/doc/html/rfc6749)
- [RFC 7519 - JWT](https://datatracker.ietf.org/doc/html/rfc7519)
- [OpenID Connect](https://openid.net/connect/)

### 安全资源
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [OWASP Cheat Sheet Series](https://cheatsheetseries.owasp.org/)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)

### 开源项目参考
- [Spring Security Samples](https://github.com/spring-projects/spring-security-samples)
- [Keycloak](https://github.com/keycloak/keycloak)
- [JHipster](https://github.com/jhipster/generator-jhipster) - 集成了完整的认证授权

---

## 🤝 贡献

本文档持续更新中，欢迎提出改进建议！

---

## 📝 更新日志

- **2025-10** - 初始版本发布
  - 完整的8章节内容
  - 涵盖从基础到高级的完整知识体系
  - 提供实战代码示例

---

## 📄 许可

本文档采用 [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/) 许可协议。

---

**开始学习：** [01 - 认证授权基础 →](./01-认证授权基础.md)

**祝你学习顺利！** 🎉

