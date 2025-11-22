# 控制流程

Go语言只有一种循环结构 `for`，但它能胜任 `while` 和 `do-while` 的工作。

## 1. if-else

Go的 `if` 语句不需要括号，但大括号是必须的。

```go
if x > 0 {
    return x
} else {
    return -x
}
```

可以在 `if` 中进行简短声明：

```go
if err := doSomething(); err != nil {
    return err
}
```

## 2. for 循环

### 标准形式
```go
for i := 0; i < 10; i++ {
    sum += i
}
```

### 类似 while
```go
sum := 1
for sum < 1000 {
    sum += sum
}
```

### 无限循环
```go
for {
    // do something
    if condition {
        break
    }
}
```

### Range 遍历
用于遍历数组、切片、Map、字符串。

```go
for index, value := range slice {
    fmt.Printf("%d: %d\n", index, value)
}

for key, value := range myMap {
    fmt.Printf("%s: %d\n", key, value)
}
```

## 3. switch

Go的 `switch` 默认带有 `break`，不会自动穿透（fallthrough）。

```go
switch os := runtime.GOOS; os {
case "darwin":
    fmt.Println("OS X.")
case "linux":
    fmt.Println("Linux.")
default:
    fmt.Printf("%s.\n", os)
}
```

如果需要穿透，使用 `fallthrough` 关键字。

## 4. defer

`defer` 语句会将函数推迟到外层函数返回之后执行。
推迟调用的函数其参数会立即求值，但函数调用被推迟。

```go
func main() {
    defer fmt.Println("world")
    fmt.Println("hello")
}
// 输出:
// hello
// world
```

多个 `defer` 按照后进先出（LIFO）的顺序执行。

---
[查看示例代码](../../examples/03-control-flow/)
