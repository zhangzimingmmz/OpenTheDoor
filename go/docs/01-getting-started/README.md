# 环境搭建指南

欢迎开始你的Go语言学习之旅！本文档将指导你完成Go开发环境的搭建。

## 📦 安装Go

### macOS安装

#### 方法一：使用Homebrew（推荐）

```bash
# 安装Go
brew install go

# 验证安装
go version
```

#### 方法二：官方安装包

1. 访问 [Go官方下载页面](https://golang.org/dl/)
2. 下载macOS版本的.pkg安装包
3. 双击安装包，按照提示完成安装

### 验证安装

```bash
$ go version
go version go1.21.x darwin/amd64
```

## ⚙️ 配置Go环境

### 环境变量配置

Go会使用以下环境变量（通常默认值就够用）：

```bash
# 查看Go环境配置
go env

# 重要的环境变量
GOROOT    # Go安装路径（自动设置）
GOPATH    # Go工作空间（默认：~/go）
GOMODCACHE # 模块缓存路径
GOPROXY   # 模块代理（用于下载依赖）
```

### 配置国内代理（可选但推荐）

由于网络原因，建议配置Go模块代理：

```bash
# 配置七牛云代理
go env -w GO111MODULE=on
go env -w GOPROXY=https://goproxy.cn,direct

# 或者配置阿里云代理
go env -w GOPROXY=https://mirrors.aliyun.com/goproxy/,direct
```

## 🛠️ 选择开发工具

### 选项一：VS Code（轻量级，推荐初学者）

1. **安装VS Code**
   - 访问 [VS Code官网](https://code.visualstudio.com/)
   - 下载并安装

2. **安装Go扩展**
   - 打开VS Code
   - 点击左侧扩展图标（或按 `Cmd+Shift+X`）
   - 搜索 "Go"
   - 安装由Go Team at Google发布的官方扩展

3. **安装Go工具**
   - 打开一个.go文件
   - VS Code会提示安装Go工具
   - 点击"Install All"安装所有推荐工具

### 选项二：GoLand（功能强大，Java开发者会很熟悉）

1. **下载GoLand**
   - 访问 [JetBrains官网](https://www.jetbrains.com/go/)
   - 下载并安装（提供30天试用）

2. **配置Go SDK**
   - 打开GoLand
   - Settings → Go → GOROOT
   - 选择Go安装路径

> **对Java开发者的建议**：如果你习惯使用IntelliJ IDEA，GoLand会让你感觉非常熟悉。但VS Code也是很好的选择，更轻量级。

## 📝 第一个Go程序

### 1. 创建项目

```bash
# 创建项目目录
mkdir hello-world
cd hello-world

# 初始化Go模块
go mod init hello-world
```

### 2. 创建main.go

```go
package main

import "fmt"

func main() {
    fmt.Println("Hello, World!")
}
```

### 3. 运行程序

```bash
# 直接运行
go run main.go

# 或者编译后运行
go build
./hello-world
```

## 🎯 与Java的对比

| 特性 | Java | Go |
|------|------|-----|
| **编译** | `javac` + `java` | `go build` 或 `go run` |
| **包管理** | Maven/Gradle | go mod（内置） |
| **项目结构** | src/main/java | 扁平化结构 |
| **入口点** | `public static void main(String[] args)` | `func main()` |
| **包声明** | `package com.example;` | `package main` |

## 🔧 Go命令行工具

Go提供了一套强大的命令行工具：

```bash
go run      # 编译并运行程序
go build    # 编译程序
go test     # 运行测试
go mod      # 模块管理
go get      # 下载依赖
go fmt      # 格式化代码
go vet      # 代码检查
go doc      # 查看文档
```

### 关键工具说明

#### `go run` - 快速运行
```bash
# 编译并立即运行，适合开发测试
go run main.go
```

#### `go build` - 编译程序
```bash
# 编译生成可执行文件
go build

# 指定输出文件名
go build -o myapp

# 交叉编译（例如编译Windows版本）
GOOS=windows GOARCH=amd64 go build
```

#### `go mod` - 依赖管理
```bash
# 初始化模块
go mod init <module-name>

# 添加依赖
go get github.com/gin-gonic/gin

# 整理依赖（删除未使用的）
go mod tidy

# 查看依赖
go list -m all
```

#### `go fmt` - 代码格式化
```bash
# 格式化当前目录下所有.go文件
go fmt ./...

# VS Code会自动在保存时格式化
```

> **Java对比**：`go fmt` 相当于Java的代码格式化工具，但Go社区有统一的格式规范，不需要配置。

## 💡 常见问题

### Q1: GOPATH是什么？还需要设置吗？

**A**: GOPATH是Go 1.11之前的工作空间概念。现在使用Go Modules后：
- ✅ 不需要手动设置GOPATH
- ✅ 可以在任意目录创建项目
- ✅ 依赖由go.mod管理

### Q2: go.mod文件是什么？

**A**: 类似于Java的pom.xml或build.gradle，用于：
- 声明模块名称
- 管理依赖版本
- 记录依赖的精确版本

### Q3: 为什么要配置GOPROXY？

**A**: 
- 某些Go模块托管在国外服务器上
- 使用国内代理可以加速下载
- 类似于配置Maven的国内镜像

### Q4: 如何选择IDE？

**A**: 
- **初学者/轻量级**：VS Code + Go扩展
- **重度开发/熟悉JetBrains**：GoLand
- **命令行爱好者**：Vim/Neovim + vim-go

## ✅ 环境检查清单

完成以下检查，确保环境配置正确：

- [ ] `go version` 显示Go版本号
- [ ] `go env` 显示环境配置
- [ ] 已配置GOPROXY（可选但推荐）
- [ ] 已安装并配置IDE/编辑器
- [ ] 成功运行第一个Hello World程序

## 🎓 下一步

环境搭建完成后，继续学习：
- [Hello World示例](../../examples/01-hello-world/) - 第一个完整示例
- [基础语法](../02-basics/) - Go语言基础知识

---

**提示**：如果遇到问题，可以使用 `go help <command>` 查看帮助，例如 `go help mod`。
