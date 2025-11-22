# 错误处理

Go 语言使用 `error` 值来表示错误状态。

## 1. error 接口

```go
type error interface {
    Error() string
}
```

通常函数会返回一个 `error` 值，调用的代码应当判断这个错误是否等于 `nil` 来进行错误处理。

```go
i, err := strconv.Atoi("42")
if err != nil {
    fmt.Printf("couldn't convert number: %v\n", err)
    return
}
fmt.Println("Converted integer:", i)
```

## 2. 自定义错误

通过实现 `Error()` 方法来自定义错误类型。

```go
type MyError struct {
    When time.Time
    What string
}

func (e *MyError) Error() string {
    return fmt.Sprintf("at %v, %s", e.When, e.What)
}
```

## 3. Panic 和 Recover

### Panic
`panic` 表示非常严重的问题，通常是不可恢复的错误（如数组越界）。它会停止当前函数的执行，并开始逐层向上执行 defer 语句。

### Recover
`recover` 是一个内建函数，用于重新获得 panic 协程的控制。
`recover` 只能在 `defer` 函数中有效。

```go
func main() {
    defer func() {
        if r := recover(); r != nil {
            fmt.Println("Recovered in main", r)
        }
    }()
    panic("oops")
}
```

---
[查看示例代码](../../examples/08-error-handling/)
