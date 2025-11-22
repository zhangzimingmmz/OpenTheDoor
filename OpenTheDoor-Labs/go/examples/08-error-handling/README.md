# 错误处理示例

学习Go语言独特的错误处理机制：Error接口、Panic和Recover。

## 🎯 学习目标

- 理解Go的错误处理哲学（Errors are values）
- 掌握自定义错误类型
- 学会使用 `errors.Is` 和 `errors.As` (Go 1.13+)
- 理解 `panic` 和 `recover` 的正确用法

## 🚀 运行示例

```bash
cd examples/08-error-handling
go run main.go
```

## 📖 核心概念

### 1. error 接口
Go使用内置的 `error` 接口表示错误状态，而不是异常（Exception）。
```go
type error interface {
    Error() string
}
```
通常作为函数的最后一个返回值。

### 2. 错误包装 (Wrapping)
Go 1.13引入了 `%w` 动词来包装错误，保留原始错误链。
- `errors.Is()`: 判断错误链中是否包含特定错误。
- `errors.As()`: 将错误转换为特定类型。

### 3. Panic 和 Recover
- `panic`: 类似于抛出运行时异常，会导致程序崩溃。
- `recover`: 只能在 `defer` 中使用，用于捕获 panic，防止程序崩溃。
**原则**：仅在不可恢复的错误（如数组越界、空指针）时使用 panic，普通业务错误应返回 error。

## 💡 与Java对比

| 特性 | Java | Go |
|------|------|----|
| **错误表示** | Exception (try-catch) | error (返回值) |
| **检查异常** | Checked Exception | 无 |
| **运行时异常** | RuntimeException | panic |
| **捕获异常** | catch | if err != nil |
| **恢复机制** | catch / finally | defer + recover |
| **自定义错误** | extends Exception | 实现 error 接口 |

## 🎓 练习任务

1. 定义一个 `DivideError` 结构体，包含被除数和除数信息。
2. 编写一个除法函数，当除数为0时返回 `DivideError`。
3. 在main函数中调用，并使用 `errors.As` 提取错误详情。
4. 尝试触发一个 panic 并使用 recover 捕获它。

## ➡️ 下一步

- [包管理](../09-packages/) - 组织代码
