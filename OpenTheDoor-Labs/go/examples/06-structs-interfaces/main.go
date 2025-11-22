package main

import (
	"fmt"
	"math"
)

/*
结构体和接口示例

本示例演示：
1. 结构体定义与方法
2. 结构体嵌入（组合）
3. 接口定义与隐式实现
4. 空接口与类型断言
*/

func main() {
	fmt.Println("=== 1. 结构体与方法 ===")
	demonstrateStructs()

	fmt.Println("\n=== 2. 接口与多态 ===")
	demonstrateInterfaces()

	fmt.Println("\n=== 3. 空接口与类型断言 ===")
	demonstrateEmptyInterface()
}

// --- 1. 结构体定义 ---

// User 用户结构体
type User struct {
	ID       int
	Username string
	email    string // 小写开头，包外不可见（private）
}

// 构造函数（工厂模式）
func NewUser(id int, username, email string) *User {
	return &User{
		ID:       id,
		Username: username,
		email:    email,
	}
}

// 方法：值接收者（不会修改原对象）
func (u User) String() string {
	return fmt.Sprintf("User[ID=%d, Name=%s]", u.ID, u.Username)
}

// 方法：指针接收者（可以修改原对象）
func (u *User) UpdateEmail(newEmail string) {
	u.email = newEmail
}

// Admin 管理员（嵌入User实现组合）
type Admin struct {
	User  // 匿名嵌入
	Level int
}

func demonstrateStructs() {
	// 创建对象
	u1 := User{ID: 1, Username: "Alice", email: "alice@example.com"}
	u2 := NewUser(2, "Bob", "bob@example.com")

	fmt.Println(u1.String()) // 调用方法
	fmt.Println(u2.String())

	// 修改状态
	u1.UpdateEmail("alice.new@example.com")
	fmt.Println("Updated Email:", u1.email) // 同包内可以访问私有字段

	// 组合（继承的效果）
	admin := Admin{
		User:  User{ID: 3, Username: "Admin", email: "admin@sys.com"},
		Level: 1,
	}

	// 直接访问嵌入字段的方法和属性
	fmt.Println("Admin Name:", admin.Username)
	fmt.Println("Admin String:", admin.String()) // 复用了User的方法

	// Java对比：
	// Java使用 extends 继承
	// Go使用匿名嵌入实现组合，"组合优于继承"
}

// --- 2. 接口定义 ---

// Shape 形状接口
type Shape interface {
	Area() float64
	Perimeter() float64
}

// Circle 圆形
type Circle struct {
	Radius float64
}

// Circle实现Shape接口（隐式，无需implements）
func (c Circle) Area() float64 {
	return math.Pi * c.Radius * c.Radius
}

func (c Circle) Perimeter() float64 {
	return 2 * math.Pi * c.Radius
}

// Rectangle 矩形
type Rectangle struct {
	Width, Height float64
}

// Rectangle实现Shape接口
func (r Rectangle) Area() float64 {
	return r.Width * r.Height
}

func (r Rectangle) Perimeter() float64 {
	return 2 * (r.Width + r.Height)
}

// 多态函数
func printShapeInfo(s Shape) {
	fmt.Printf("类型: %T\n", s)
	fmt.Printf("面积: %.2f\n", s.Area())
	fmt.Printf("周长: %.2f\n", s.Perimeter())
}

func demonstrateInterfaces() {
	c := Circle{Radius: 5}
	r := Rectangle{Width: 10, Height: 5}

	// 多态调用
	printShapeInfo(c)
	printShapeInfo(r)

	// 接口切片
	shapes := []Shape{c, r}
	totalArea := 0.0
	for _, s := range shapes {
		totalArea += s.Area()
	}
	fmt.Printf("总面积: %.2f\n", totalArea)

	// Java对比：
	// Java: class Circle implements Shape
	// Go: 只要实现了方法，自动视为实现了接口
}

// --- 3. 空接口与类型断言 ---

func describe(i interface{}) {
	fmt.Printf("(%v, %T)\n", i, i)
}

func demonstrateEmptyInterface() {
	// 空接口可以接收任何类型
	var any interface{}

	any = 42
	describe(any)

	any = "hello"
	describe(any)

	// 类型断言
	s, ok := any.(string) // 安全断言
	if ok {
		fmt.Println("是字符串:", s)
	} else {
		fmt.Println("不是字符串")
	}

	// 类型Switch
	doTypeSwitch(21)
	doTypeSwitch("hello")
	doTypeSwitch(true)

	// Java对比：
	// interface{} 类似于 Java 的 Object
	// 类型断言 类似于 (String) obj
	// 类型Switch 类似于 if (obj instanceof String)
}

func doTypeSwitch(i interface{}) {
	switch v := i.(type) {
	case int:
		fmt.Printf("两倍整数: %d\n", v*2)
	case string:
		fmt.Printf("字符串长度: %d\n", len(v))
	default:
		fmt.Printf("未知类型: %T\n", v)
	}
}
