# 综合实战项目：简易博客系统 API

这是本学习路线的最终实战项目，将整合之前学到的所有知识点。

## 🎯 项目目标

构建一个功能完整的博客后端 API，包含：
- **Web 框架**: 使用 Gin 处理 HTTP 请求
- **数据库**: 使用 GORM + SQLite 存储数据
- **并发**: 使用 Goroutine 处理后台统计任务
- **架构**: 采用简单的 MVC 分层结构 (Handler -> Service -> Model)
- **功能**: 用户注册、发布文章、评论文章

## 🛠️ 技术栈

- **Gin**: Web 路由与中间件
- **GORM**: 数据库 ORM
- **SQLite**: 轻量级数据库
- **Go Standard Lib**: time, fmt, errors 等

## 🚀 运行项目

```bash
cd examples/14-final-project
go run main.go
```

## 📖 API 接口文档

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/register | 用户注册 |
| GET | /api/posts | 获取文章列表 |
| POST | /api/posts | 发布文章 (需认证 - 模拟) |
| GET | /api/posts/:id | 获取文章详情 |
| POST | /api/posts/:id/comments | 发表评论 |

## 💡 设计亮点

1. **分层设计**: 虽然在一个文件中，但代码逻辑清晰地分为了 Model, Service, Handler 层。
2. **并发处理**: 每次查看文章详情时，会启动一个 Goroutine 异步增加浏览量，不阻塞主请求。
3. **错误处理**: 统一的错误响应格式。
4. **数据库关联**: User -> Post (Has Many), Post -> Comment (Has Many)。

## 🎓 扩展挑战

1. **JWT 认证**: 目前仅模拟认证，尝试集成 `golang-jwt/jwt` 实现真正的 Token 认证。
2. **分页**: 为文章列表添加分页功能 (`page`, `page_size`)。
3. **单元测试**: 为 Service 层编写单元测试。
4. **Docker化**: 编写 Dockerfile 将应用容器化。

祝贺你完成了 Go 语言的入门学习！🎉
