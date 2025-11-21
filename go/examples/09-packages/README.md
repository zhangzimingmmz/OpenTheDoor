# 包管理与可见性示例

学习Go语言的包（Package）系统，理解模块化开发和可见性规则。

## 🎯 学习目标

- 理解包的定义与导入
- 掌握可见性规则（导出 vs 未导出）
- 理解 `init()` 函数的作用
- 掌握 `go mod` 的基本使用

## 🚀 运行示例

```bash
cd examples/09-packages
go run main.go
```

## 📖 核心概念

### 1. 包定义
每个 Go 文件都必须以 `package <name>` 开头。
- **可执行包**: `package main`，包含 `main()` 函数。
- **库包**: 通常与目录名相同，如 `package math`。

### 2. 可见性 (Visibility)
Go 使用**首字母大小写**来控制访问权限（无需 `public`/`private` 关键字）。
- **大写开头** (e.g., `Calculate`): **导出** (Public)，其他包可以访问。
- **小写开头** (e.g., `calculate`): **未导出** (Private)，仅当前包内可见。

### 3. init 函数
- 每个包可以有多个 `init()` 函数。
- 在 `main()` 之前自动执行。
- 常用于初始化全局变量或注册驱动。

## 💡 与Java对比

| 特性 | Java | Go |
|------|------|----|
| **命名空间** | `package com.example.util;` | `package util` |
| **导入** | `import com.example.util.*;` | `import "example/util"` |
| **Public** | `public class/method` | 首字母大写 (e.g., `User`) |
| **Private** | `private` | 首字母小写 (e.g., `user`) |
| **Protected** | `protected` | 无直接对应 (通过包内可见性) |
| **初始化** | 静态代码块 `static {}` | `init()` 函数 |

## 🎓 练习任务

1. 在 `mypkg` 中添加一个新的导出函数 `Hello()`。
2. 尝试在 `main.go` 中调用 `mypkg` 中未导出的函数或变量，观察编译器报错。
3. 添加一个 `init()` 函数到 `main.go`，观察执行顺序。

## ➡️ 下一步

- [文件与IO操作](../10-file-io/) - 读写文件与数据处理
