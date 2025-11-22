# Web开发基础示例

学习使用 Go 标准库 `net/http` 构建简单的 Web 服务器。

## 🎯 学习目标

- 理解 `net/http` 包的基本用法
- 掌握 HTTP 处理函数 (`http.HandleFunc`)
- 理解 `http.Handler` 接口
- 学会处理 GET/POST 请求参数

## 🚀 运行示例

```bash
cd examples/11-web-basics
go run main.go
```

访问: http://localhost:8080/hello

## 📖 核心概念

### 1. 路由与处理
Go 标准库提供了简单的路由功能。
```go
http.HandleFunc("/path", handlerFunc)
```

### 2. HandlerFunc
处理函数的签名必须是：
```go
func(w http.ResponseWriter, r *http.Request)
```
- `w`: 用于写入响应 (状态码, Body)
- `r`: 包含请求信息 (Method, URL, Body, Header)

## 💡 与Java对比

| 特性 | Java (Servlet) | Go (net/http) |
|------|----------------|---------------|
| **容器** | Tomcat / Jetty | 内置 HTTP Server |
| **接口** | `HttpServlet` | `http.Handler` |
| **请求对象** | `HttpServletRequest` | `*http.Request` |
| **响应对象** | `HttpServletResponse` | `http.ResponseWriter` |
| **并发模型** | 线程池 (Thread per request) | Goroutine per request |

## 🎓 练习任务

1. 添加一个 `/time` 接口，返回当前服务器时间。
2. 编写一个中间件 (Middleware)，记录每个请求的处理时间。
3. 尝试从 URL 查询参数中获取 `name`，如 `/hello?name=Go`。

## ➡️ 下一步

- [数据库操作](../12-database/) - 持久化存储
