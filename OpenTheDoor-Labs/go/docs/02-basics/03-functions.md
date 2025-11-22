# 函数与方法

## 1. 函数 (Function)

函数是Go的基本代码块。

```go
func add(x int, y int) int {
    return x + y
}
```

### 多返回值
Go函数可以返回多个值，常用于返回结果和错误。

```go
func swap(x, y string) (string, string) {
    return y, x
}
```

### 命名返回值
返回值可以被命名，它们会被视为定义在函数顶部的变量。

```go
func split(sum int) (x, y int) {
    x = sum * 4 / 9
    y = sum - x
    return // 裸返回
}
```

## 2. 闭包 (Closure)
Go函数可以是闭包。闭包是一个函数值，它引用了其函数体之外的变量。

```go
func adder() func(int) int {
    sum := 0
    return func(x int) int {
        sum += x
        return sum
    }
}
```

## 3. 方法 (Method)
Go没有类。不过你可以为结构体类型定义方法。
方法就是一类带特殊的 **接收者** 参数的函数。

```go
type Vertex struct {
    X, Y float64
}

// (v Vertex) 是接收者
func (v Vertex) Abs() float64 {
    return math.Sqrt(v.X*v.X + v.Y*v.Y)
}
```

### 指针接收者
如果你想在方法中修改接收者，或者接收者很大，应该使用指针接收者。

```go
func (v *Vertex) Scale(f float64) {
    v.X = v.X * f
    v.Y = v.Y * f
}
```

---
[查看示例代码](../../examples/04-functions/)
