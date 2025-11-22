# Go语言最佳实践

基于Go社区经验总结的最佳实践指南，帮助Java开发者快速掌握Go的习惯用法。

## 📝 代码风格

### 1. 命名规范

```go
// ✅ 好的命名
type UserService struct {}
func NewUserService() *UserService {}
var maxConnections = 100
const DefaultTimeout = 30

// ❌ 避免的命名
type userservice struct {}      // 类型名应该驼峰
type User_Service struct {}     // 不使用下划线
var MAX_CONNECTIONS = 100       // 常量不需要全大写
```

**规则**：
- 大写字母开头 = 导出（public）
- 小写字母开头 = 未导出（private）  
- 使用驼峰命名（camelCase），不用下划线
- 缩写词保持一致：`URL`、`HTTP`、`ID`（全大写或全小写）

### 2. 包命名

```go
// ✅ 好的包名
package http
package json
package user

// ❌ 避免的包名
package httpUtil    // 不加util后缀
package go_user     // 不用下划线
package user_service // 包名应该简短
```

**规则**：
- 小写，单个单词
- 避免generic名称如util、common
- 包名应该是名词，描述其功能

### 3. 接口命名

```go
// ✅ 单方法接口用-er后缀
type Reader interface {
    Read(p []byte) (n int, err error)
}

type Writer interface {
    Write(p []byte) (n int, err error)
}

// ✅ 多方法接口用名词
type FileSystem interface {
    Open(name string) (File, error)
    Create(name string) (File, error)
    Remove(name string) error
}

// ❌ 避免
type IReader interface {}  // 不加I前缀（Java习惯）
```

---

## 🎯 错误处理

### 1. 优先返回错误

```go
// ✅ 推荐方式
func divide(a, b float64) (float64, error) {
    if b == 0 {
        return 0, errors.New("division by zero")
    }
    return a / b, nil
}

// 使用
result, err := divide(10, 0)
if err != nil {
    return err  // 立即处理错误
}
// 继续正常逻辑
```

### 2. 自定义错误类型

```go
// ✅ 实现error接口
type ValidationError struct {
    Field string
    Message string
}

func (e *ValidationError) Error() string {
    return fmt.Sprintf("%s: %s", e.Field, e.Message)
}

// ✅ 使用errors.New或fmt.Errorf
err := errors.New("something went wrong")
err := fmt.Errorf("user %s not found", username)

// ✅ Go 1.13+ 错误包装
err := fmt.Errorf("failed to save user: %w", originalErr)
```

### 3. 错误检查模式

```go
// ✅ 立即检查，快速返回
if err := doSomething(); err != nil {
    return fmt.Errorf("doSomething failed: %w", err)
}

// ✅ 多个可能的错误
result, err := operation()
if err != nil {
    switch {
    case errors.Is(err, ErrNotFound):
        // 处理未找到
    case errors.Is(err, ErrPermission):
        // 处理权限错误
    default:
        return err
    }
}
```

---

## 🔧 结构体和接口

### 1. 使用组合而非继承

```go
// ✅ 使用嵌入（组合）
type Engine struct {
    Power int
}

func (e Engine) Start() {
    fmt.Println("Engine started")
}

type Car struct {
    Engine  // 嵌入
    Brand string
}

car := Car{
    Engine: Engine{Power: 200},
    Brand: "Toyota",
}
car.Start()  // 可以直接调用Engine的方法
```

### 2. 接口设计

```go
// ✅ 小接口更好
type Reader interface {
    Read(p []byte) (n int, err error)
}

type Writer interface {
    Write(p []byte) (n int, err error)
}

// 组合小接口
type ReadWriter interface {
    Reader
    Writer
}

// ❌ 避免大而全的接口
type Everything interface {
    Read()
    Write()
    Open()
    Close()
    // ... 太多方法
}
```

### 3. 指针接收者 vs 值接收者

```go
type Point struct {
    X, Y int
}

// 值接收者：不修改接收者
func (p Point) Distance() float64 {
    return math.Sqrt(float64(p.X*p.X + p.Y*p.Y))
}

// 指针接收者：需要修改接收者
func (p *Point) Move(dx, dy int) {
    p.X += dx
    p.Y += dy
}
```

**选择规则**：
- 需要修改接收者 → 使用指针
- 接收者很大 → 使用指针（避免复制）
- 一致性：同一类型的方法应统一使用指针或值

---

## ⚡ 并发

### 1. 使用Channel通信

```go
// ✅ 推荐：使用channel
func worker(jobs <-chan int, results chan<- int) {
    for job := range jobs {
        results <- process(job)
    }
}

// ❌ 避免：共享内存+锁（除非必要）
var (
    counter int
    mu      sync.Mutex
)

func increment() {
    mu.Lock()
    counter++
    mu.Unlock()
}
```

**格言**："不要通过共享内存来通信，而应通过通信来共享内存"

### 2. 使用 sync.WaitGroup

```go
// ✅ 等待goroutine完成
var wg sync.WaitGroup

for i := 0; i < 10; i++ {
    wg.Add(1)
    go func(id int) {
        defer wg.Done()
        processTask(id)
    }(i)
}

wg.Wait()  // 等待所有goroutine完成
```

### 3. Context传递

```go
// ✅ 使用context控制超时和取消
func fetchData(ctx context.Context, url string) ([]byte, error) {
    req, _ := http.NewRequestWithContext(ctx, "GET", url, nil)
    
    resp, err := client.Do(req)
    if err != nil {
        return nil, err
    }
    defer resp.Body.Close()
    
    return io.ReadAll(resp.Body)
}

// 使用
ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
defer cancel()
data, err := fetchData(ctx, "https://api.example.com/data")
```

---

## 📦 包和模块

### 1. 包组织

```go
// ✅ 好的包结构
project/
├── main.go
├── handler/        // HTTP处理器
├── service/        // 业务逻辑
├── model/          // 数据模型
├── repository/     // 数据访问
└── config/         // 配置

// ❌ 避免
project/
├── util/           // 避免generic包名
├── common/
└── helper/
```

### 2. 依赖管理

```bash
# 初始化模块
go mod init github.com/username/project

# 添加依赖
go get github.com/gin-gonic/gin@latest

# 清理未使用的依赖
go mod tidy

# 查看依赖
go list -m all
```

---

## 测试

### 1. 表驱动测试

```go
// ✅ 推荐的测试方式
func TestAdd(t *testing.T) {
    tests := []struct {
        name string
        a, b int
        want int
    }{
        {"positive", 2, 3, 5},
        {"negative", -1, -1, -2},
        {"zero", 0, 0, 0},
    }
    
    for _, tt := range tests {
        t.Run(tt.name, func(t *testing.T) {
            got := add(tt.a, tt.b)
            if got != tt.want {
                t.Errorf("add(%d, %d) = %d; want %d", 
                    tt.a, tt.b, got, tt.want)
            }
        })
    }
}
```

### 2. 基准测试

```go
func BenchmarkFibonacci(b *testing.B) {
    for i := 0; i < b.N; i++ {
        fibonacci(20)
    }
}
```

---

## 🚀 性能优化

### 1. 避免不必要的内存分配

```go
// ❌ 每次循环都分配
for i := 0; i < n; i++ {
    s := make([]int, 1000)
    // use s
}

// ✅ 复用切片
s := make([]int, 1000)
for i := 0; i < n; i++ {
    s = s[:0]  // 重置但不释放内存
    // use s
}
```

### 2. 使用strings.Builder

```go
// ❌ 字符串拼接（频繁分配）
var s string
for i := 0; i < 100; i++ {
    s += strconv.Itoa(i)
}

// ✅ 使用Builder
var builder strings.Builder
for i := 0; i < 100; i++ {
    builder.WriteString(strconv.Itoa(i))
}
s := builder.String()
```

### 3. sync.Pool复用对象

```go
var bufferPool = sync.Pool{
    New: func() interface{} {
        return new(bytes.Buffer)
    },
}

func processData(data []byte) {
    buf := bufferPool.Get().(*bytes.Buffer)
    defer bufferPool.Put(buf)
    buf.Reset()
    
    // use buf
}
```

---

## 🛠️ 工具使用

### 1. 代码格式化

```bash
# 格式化所有代码
go fmt ./...

# 或使用gofmt
gofmt -w .

# 使用goimports（自动添加/删除import）
goimports -w .
```

### 2. 代码检查

```bash
# go vet - 静态分析
go vet ./...

# golangci-lint - 综合linter
golangci-lint run
```

### 3. 代码生成

```bash
# stringer - 为枚举生成String方法
go generate

# mockgen - 生成mock对象
mockgen -source=interface.go -destination=mock.go
```

---

## ⚠️ 常见陷阱

### 1. Range循环变量

```go
// ❌ 错误：所有goroutine使用同一个变量
for _, v := range values {
    go func() {
        fmt.Println(v)  // v被所有goroutine共享
    }()
}

// ✅ 正确：传递副本
for _, v := range values {
    go func(val int) {
        fmt.Println(val)
    }(v)
}
```

### 2. nil切片 vs 空切片

```go
var s1 []int        // nil切片
s2 := []int{}       // 空切片
s3 := make([]int, 0) // 空切片

// 它们的行为大多相同，但JSON编码不同
json.Marshal(s1)  // "null"
json.Marshal(s2)  // "[]"
```

### 3. defer在循环中

```go
// ❌ 可能导致资源泄漏
for _, file := range files {
    f, _ := os.Open(file)
    defer f.Close()  // 所有defer在函数结束时执行
    // process file
}

// ✅ 使用函数包装
for _, file := range files {
    func() {
        f, _ := os.Open(file)
        defer f.Close()
        // process file
    }()
}
```

---

## 📚 推荐阅读

1. **官方文档**
   - [Effective Go](https://go.dev/doc/effective_go)
   - [Go Code Review Comments](https://github.com/golang/go/wiki/CodeReviewComments)

2. **代码风格**
   - [Uber Go Style Guide](https://github.com/uber-go/guide/blob/master/style.md)
   - [Google Go Style Guide](https://google.github.io/styleguide/go/)

3. **性能优化**
   - [Go Performance Tips](https://github.com/dgryski/go-perfbook)

---

## ✅ 快速检查清单

编码前检查：
- [ ] 是否遵循命名规范（大小写、驼峰）？
- [ ] 错误处理是否完善？
- [ ] 是否使用了组合而非继承？
- [ ] 接口是否足够小？
- [ ] 并发是否使用了channel通信？
- [ ] 是否添加了必要的测试？

提交前检查：
- [ ] 运行 `go fmt ./...`
- [ ] 运行 `go vet ./...`
- [ ] 运行 `go test ./...`
- [ ] 检查 `go mod tidy`

---

**记住**：写Go代码时，简洁和清晰比聪明和复杂更重要！
