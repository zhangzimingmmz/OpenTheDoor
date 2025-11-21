# Hello World 示例

你的第一个Go程序！

## 📝 程序说明

这是一个最简单的Go程序，它会在终端输出"Hello, World!"。

## 🎯 学习目标

- 理解Go程序的基本结构
- 了解package和import的作用
- 学会运行Go程序

## 📖 与Java对比

### Java版本

```java
// HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

```bash
# 编译
javac HelloWorld.java
# 运行
java HelloWorld
```

### Go版本

```go
// main.go
package main

import "fmt"

func main() {
    fmt.Println("Hello, World!")
}
```

```bash
# 直接运行
go run main.go

# 或编译后运行
go build
./01-hello-world
```

## 🔍 代码解析

### 1. package声明

```go
package main
```

- 每个Go文件必须属于一个包（package）
- `main` 包是特殊的，表示这是一个可执行程序
- 库包会使用其他名称，如 `package utils`

**Java对比**：类似Java的 `package com.example`，但Go的包名通常是单词，不使用域名反向格式。

### 2. import语句

```go
import "fmt"
```

- 导入标准库中的 `fmt` 包（格式化输入输出）
- 多个导入可以使用括号：
  ```go
  import (
      "fmt"
      "time"
  )
  ```

**Java对比**：类似 `import java.util.*`，但Go不支持通配符导入。

### 3. main函数

```go
func main() {
    fmt.Println("Hello, World!")
}
```

- `func` 关键字声明函数
- `main` 函数是程序的入口点
- 无需 `public static`等修饰符
- `fmt.Println()` 打印并换行

**Java对比**：Go的main函数更简洁，不需要参数（命令行参数通过 `os.Args` 获取）。

## 🚀 运行方式

### 方式一：go run（推荐开发时使用）

```bash
$ go run main.go
Hello, World!
```

- 编译并立即运行
- 不生成可执行文件
- 适合快速测试

### 方式二：go build

```bash
$ go build
$ ./01-hello-world
Hello, World!
```

- 生成可执行文件
- 可以分发和部署
- 文件名默认为目录名

### 方式三：指定输出文件名

```bash
$ go build -o hello
$ ./hello
Hello, World!
```

## 💡 知识点总结

### Go程序的基本结构

```go
package main           // 1. 包声明

import "fmt"          // 2. 导入依赖

func main() {         // 3. 主函数
    // 程序逻辑
}
```

### 与Java的主要差异

| 特性 | Java | Go |
|------|------|-----|
| **文件名** | 必须与类名一致 | 任意（通常main.go） |
| **类声明** | 需要class | 不需要 |
| **访问修饰符** | public/private/protected | 首字母大小写控制 |
| **编译运行** | 两步：javac + java | 一步：go run |
| **main签名** | `public static void main(String[] args)` | `func main()` |

## 🎓 扩展练习

1. **修改输出内容**
   ```go
   fmt.Println("你好，Go语言！")
   ```

2. **多行输出**
   ```go
   fmt.Println("第一行")
   fmt.Println("第二行")
   fmt.Println("第三行")
   ```

3. **格式化输出**
   ```go
   name := "张三"
   fmt.Printf("你好，%s!\n", name)
   ```

4. **获取命令行参数**
   ```go
   package main
   
   import (
       "fmt"
       "os"
   )
   
   func main() {
       if len(os.Args) > 1 {
           fmt.Println("Hello,", os.Args[1])
       } else {
           fmt.Println("Hello, World!")
       }
   }
   ```
   
   运行：`go run main.go 张三`

## 📚 相关资源

- [fmt包文档](https://pkg.go.dev/fmt)
- [Go语言之旅 - Hello World](https://tour.golang.org/welcome/1)

## ➡️ 下一步

学习完成后，继续：
- [变量和类型](../02-variables-types/) - 学习Go的类型系统
- [基础语法文档](../../docs/02-basics/) - 深入理解Go语法
