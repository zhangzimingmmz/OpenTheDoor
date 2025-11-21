# Java vs Go 对比总结

作为Java开发者学习Go的核心对比指南。

## 🎯 设计哲学

### Java
- **面向对象**：一切皆对象
- **企业级**：完善的生态，适合大型复杂系统
- **详细明确**：显式的类型声明和错误处理

### Go
- **简洁实用**：少即是多（Less is more）
- **高并发**：为云原生和微服务设计
- **快速高效**：编译快、运行快、部署简单

---

## 📊 语法对比

### 1. Hello World

#### Java
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

#### Go
```go
package main

import "fmt"

func main() {
    fmt.Println("Hello, World!")
}
```

**差异**：Go不需要类，更简洁。

---

### 2. 变量声明

#### Java
```java
String name = "张三";
int age = 25;
var city = "北京";  // Java 10+
```

#### Go
```go
var name string = "张三"  // 完整声明
var age = 25             // 类型推断
city := "北京"           // 短声明（最常用）
```

**差异**：Go的`:=`更简洁，但只能在函数内使用。

---

### 3. 函数

#### Java
```java
public static int add(int a, int b) {
    return a + b;
}

// 多返回值需要自定义类
public static class Result {
    int quotient;
    int remainder;
}

public static Result divide(int a, int b) {
    return new Result(a / b, a % b);
}
```

#### Go
```go
func add(a, b int) int {
    return a + b
}

// 原生支持多返回值
func divide(a, b int) (int, int) {
    return a / b, a % b
}

// 常用于错误处理
func divide(a, b float64) (float64, error) {
    if b == 0 {
        return 0, errors.New("除数不能为0")
    }
    return a / b, nil
}
```

**差异**：
- Go支持多返回值
- Go不需要访问修饰符（首字母大小写控制）
- Go的错误处理模式：`(result, error)`

---

### 4. 面向对象

#### Java
```java
public class Rectangle {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double area() {
        return width * height;
    }
    
    public void scale(double factor) {
        this.width *= factor;
        this.height *= factor;
    }
}

// 使用
Rectangle rect = new Rectangle(10, 5);
rect.scale(2);
```

#### Go
```go
type Rectangle struct {
    Width  float64
    Height float64
}

func (r Rectangle) Area() float64 {
    return r.Width * r.Height
}

func (r *Rectangle) Scale(factor float64) {
    r.Width *= factor
    r.Height *= factor
}

// 使用
rect := Rectangle{Width: 10, Height: 5}
rect.Scale(2)
```

**差异**：
- Go没有类，使用struct + 方法
- Go没有构造函数（使用工厂函数）
- Go使用组合而非继承
- Go使用接口但无需显式implements

---

### 5. 接口

#### Java
```java
public interface Shape {
    double area();
}

public class Circle implements Shape {
    private double radius;
    
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
```

#### Go
```go
type Shape interface {
    Area() float64
}

type Circle struct {
    Radius float64
}

// 隐式实现接口（无需声明）
func (c Circle) Area() float64 {
    return math.Pi * c.Radius * c.Radius
}
```

**差异**：
- Go的接口是隐式实现（Duck Typing）
- Go的接口通常很小（1-3个方法）
- Go推荐"小接口，大组合"

---

### 6. 错误处理

#### Java
```java
public int divide(int a, int b) throws ArithmeticException {
    if (b == 0) {
        throw new ArithmeticException("除数不能为0");
    }
    return a / b;
}

// 使用
try {
    int result = divide(10, 0);
} catch (ArithmeticException e) {
    System.out.println("错误: " + e.getMessage());
}
```

#### Go
```go
func divide(a, b float64) (float64, error) {
    if b == 0 {
        return 0, errors.New("除数不能为0")
    }
    return a / b, nil
}

// 使用
if result, err := divide(10, 0); err != nil {
    fmt.Println("错误:", err)
} else {
    fmt.Println("结果:", result)
}
```

**差异**：
- Java使用异常（try-catch）
- Go使用多返回值（value, error）
- Go的错误处理更显式
- Go有panic/recover，但仅用于严重错误

---

### 7. 并发

#### Java
```java
// Thread
new Thread(() -> {
    System.out.println("异步执行");
}).start();

// ExecutorService
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(() -> {
    System.out.println("任务执行");
});

// CompletableFuture
CompletableFuture.runAsync(() -> {
    System.out.println("异步任务");
});
```

#### Go
```go
// Goroutine （非常轻量）
go func() {
    fmt.Println("异步执行")
}()

// Channel（goroutine间通信）
ch := make(chan string)

go func() {
    ch <- "消息"
}()

msg := <-ch
fmt.Println(msg)

// Worker Pool
jobs := make(chan int, 100)
results := make(chan int, 100)

for w := 1; w <= 3; w++ {
    go worker(w, jobs, results)
}
```

**差异**：
- Goroutine vs Thread：goroutine更轻量（可创建百万级）
- Channel vs Queue：channel是语言特性
- Go的CSP模型 vs Java的共享内存模型
- Go："通过通信共享内存"
- Java："通过共享内存通信"（需要锁）

---

## 🔄 类型系统对比

| 特性 | Java | Go |
|------|------|-----|
| **类型系统** | 强类型，静态 | 强类型，静态 |
| **类型推断** | var（Java 10+） | := 或 var |
| **泛型** | 支持（Java 5+） | 支持（Go 1.18+） |
| **null** | null（引用类型） | nil（指针、接口等） |
| **零值** | 基本类型有默认值 | 所有类型都有零值 |
| **类型转换** | 支持自动向上转型 | 必须显式转换 |

---

## 📦 包管理对比

| 特性 | Java | Go |
|------|------|-----|
| **包管理工具** | Maven, Gradle | go mod（内置） |
| **依赖声明** | pom.xml, build.gradle | go.mod |
| **包命名** | com.example.project | github.com/user/repo |
| **导入** | import com.example.* | import "fmt" |
| **中央仓库** | Maven Central | pkg.go.dev |

---

## 🏗️ 项目结构对比

### Java (Maven)
```
project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       └── Main.java
│   │   └── resources/
│   └── test/
│       └── java/
├── target/
└── pom.xml
```

### Go
```
project/
├── main.go
├── handler/
│   └── user.go
├── model/
│   └── user.go
├── go.mod
└── go.sum
```

**差异**：Go的结构更扁平，按功能组织。

---

## ⚡ 性能对比

| 特性 | Java | Go |
|------|------|-----|
| **编译速度** | 较慢 | 非常快 |
| **启动时间** | 较慢（JVM启动） | 快（原生编译） |
| **内存占用** | 较高（JVM + 堆） | 较低 |
| **垃圾回收** | 多种GC算法 | 并发标记清除 |
| **并发性能** | 好 | 优秀（goroutine） |
| **部署** | 需要JVM | 单个可执行文件 |

---

## 🎓 学习曲线

### Java → Go 的优势
✅ 已经了解静态类型  
✅ 已经了解编译型语言  
✅ 已经了解并发概念  
✅ Go语法更简单

### 需要适应的差异
⚠️ 没有类和继承（使用组合）  
⚠️ 错误处理方式不同  
⚠️ 接口是隐式实现  
⚠️ 指针（但比C++简单）  
⚠️ 没有异常（使用error）

---

## 🌟 各自适用场景

### Java 更适合
- 企业级复杂应用
- Android开发
- 大数据处理（Hadoop, Spark）
- 需要成熟框架（Spring等）
- 团队已有Java生态

### Go 更适合
- 云原生应用（Docker, Kubernetes）
- 微服务
- API服务器
- 命令行工具
- 需要高并发的网络服务
- DevOps工具

---

## 📝 概念映射表

| Java概念 | Go概念 | 说明 |
|----------|--------|------|
| `class` | `struct` + 方法 | Go用组合代替继承 |
| `interface` | `interface` | Go是隐式实现 |
| `extends` | 组合（embedding） | Go没有继承 |
| `implements` | 隐式实现 | 只要有方法就实现了接口 |
| `public`/`private` | 大小写 | 大写=public,小写=private |
| `package` | `package` | 类似但更简单 |
| `import` | `import` | Go不支持通配符 |
| `try-catch` | `if err != nil` | 显式错误处理 |
| `Thread` | `goroutine` | Goroutine更轻量 |
| `synchronized` | `sync.Mutex` | Go推荐用channel |
| `Stream` | `channel` | 不同的抽象 |
| `Optional` | 多返回值 | `(value, error)` |
| `null` | `nil` | 类似但只用于部分类型 |
| `toString()` | `String()` 方法 | 实现Stringer接口 |
| `equals()` | `==` 或自定义 | struct可直接比较 |

---

## 💡 最佳迁移路径

1. **第1-2周**：基础语法、控制流程
2. **第3-4周**：函数、struct、接口
3. **第5-6周**：并发编程（重点）
4. **第7-8周**：Web开发、实战项目

---

## 🔗 推荐资源

- [Go语言之旅](https://tour.golang.org/)（中文）
- [Effective Go](https://go.dev/doc/effective_go)
- [Go by Example](https://gobyexample.com/)
- [Go标准库文档](https://pkg.go.dev/std)

---

**记住**：Go的哲学是"简单"和"务实"。不要用Java的思维方式写Go，拥抱Go的习惯用法！
