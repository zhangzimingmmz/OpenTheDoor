# 结构体和接口示例

深入理解Go语言的面向对象编程核心：结构体（Struct）和接口（Interface）。

## 🎯 学习目标

- 掌握结构体的定义、嵌入（组合）和方法
- 理解接口的隐式实现机制（Duck Typing）
- 学会使用空接口 `interface{}`
- 掌握类型断言和类型Switch

## 🚀 运行示例

```bash
cd examples/06-structs-interfaces
go run main.go
```

## 📖 核心概念

### 1. 结构体 (Struct)
Go没有 `class`，而是使用 `struct` 来定义自定义类型。
- **组合优于继承**：Go通过结构体嵌入（Embedding）来实现代码复用，而不是继承。
- **方法**：通过定义接收者（Receiver）将函数关联到结构体。

### 2. 接口 (Interface)
Go的接口是**隐式实现**的。
- 只要一个类型实现了接口定义的所有方法，它就实现了该接口。
- 不需要 `implements` 关键字。
- **空接口** `interface{}` 可以接收任何类型的值（类似Java的 `Object`）。

## 💡 与Java对比

| 特性 | Java | Go |
|------|------|----|
| **类型定义** | `class` | `struct` |
| **继承** | `extends` | 嵌入 (Embedding) |
| **接口实现** | `implements` | 隐式实现 (只要方法匹配) |
| **多态** | 基于继承/接口 | 基于接口 |
| **构造函数** | 构造器 | 工厂函数 (如 `NewUser()`) |
| **通用类型** | `Object` | `interface{}` (或 `any` in Go 1.18+) |

## 🎓 练习任务

1. 定义一个 `Shape` 接口，包含 `Area()` 和 `Perimeter()` 方法。
2. 实现 `Circle` 和 `Rectangle` 结构体，并实现 `Shape` 接口。
3. 编写一个函数，接收 `Shape` 切片并计算总面积。
4. 尝试使用类型断言判断一个 `Shape` 是否是 `Circle`。

## ➡️ 下一步

- [并发编程](../07-concurrency/) - Go的杀手级特性
