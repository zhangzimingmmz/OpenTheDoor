# 结构体与接口

## 1. 结构体 (Struct)

结构体是字段的集合。

```go
type Vertex struct {
    X int
    Y int
}

v := Vertex{1, 2}
v.X = 4
```

### 结构体字面量

```go
var (
    v1 = Vertex{1, 2}  // 类型为 Vertex
    v2 = Vertex{X: 1}  // Y:0 被隐式赋予
    v3 = Vertex{}      // X:0 Y:0
    p  = &Vertex{1, 2} // 类型为 *Vertex
)
```

## 2. 接口 (Interface)

接口类型是由一组方法签名定义的集合。
接口类型的变量可以保存任何实现了这些方法的值。

```go
type Abser interface {
    Abs() float64
}
```

### 隐式实现
类型通过实现一个接口的所有方法来实现该接口。没有显式的声明（如 `implements`）。

```go
type MyFloat float64

func (f MyFloat) Abs() float64 {
    if f < 0 {
        return float64(-f)
    }
    return float64(f)
}
// MyFloat 实现了 Abser 接口
```

### 空接口
指定了零个方法的接口值被称为空接口：`interface{}`。
空接口可以保存任何类型的值。

```go
var i interface{}
i = 42
i = "hello"
```

### 类型断言
提供了访问接口值底层具体值的方式。

```go
t := i.(T)      // 如果i不是T类型，会panic
t, ok := i.(T)  // 安全断言
```

### 类型选择 (Type Switch)

```go
switch v := i.(type) {
case int:
    fmt.Printf("Twice %v is %v\n", v, v*2)
case string:
    fmt.Printf("%q is %v bytes long\n", v, len(v))
default:
    fmt.Printf("I don't know about type %T!\n", v)
}
```

---
[查看示例代码](../../examples/06-structs-interfaces/)
