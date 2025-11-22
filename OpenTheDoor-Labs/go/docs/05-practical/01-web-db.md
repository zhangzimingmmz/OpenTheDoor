# 实战开发

## 1. Web 开发基础

使用 `net/http` 标准库构建 Web 服务。

```go
http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintf(w, "Hello, %q", html.EscapeString(r.URL.Path))
})

log.Fatal(http.ListenAndServe(":8080", nil))
```

## 2. 数据库操作 (GORM)

GORM 是 Go 语言流行的 ORM 库。

```go
// 定义模型
type Product struct {
  gorm.Model
  Code  string
  Price uint
}

// 连接
db, err := gorm.Open(sqlite.Open("test.db"), &gorm.Config{})

// 迁移
db.AutoMigrate(&Product{})

// 创建
db.Create(&Product{Code: "D42", Price: 100})

// 查询
var product Product
db.First(&product, 1)
```

## 3. RESTful API (Gin)

Gin 是一个高性能的 HTTP Web 框架。

```go
r := gin.Default()
r.GET("/ping", func(c *gin.Context) {
    c.JSON(200, gin.H{
        "message": "pong",
    })
})
r.Run() // 监听并在 0.0.0.0:8080 上启动服务
```

---
查看完整示例：
- [Web基础](../../examples/11-web-basics/)
- [数据库](../../examples/12-database/)
- [REST API](../../examples/13-rest-api/)
- [综合项目](../../examples/14-final-project/)
