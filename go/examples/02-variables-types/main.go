package main

import "fmt"

/*
变量和类型示例

本示例演示：
1. 三种变量声明方式
2. Go的基本数据类型
3. 类型转换
4. 常量和iota
5. 指针基础
6. 字符串操作
*/

// 包级别变量声明（只能用var，不能用:=）
var globalVar = "我是全局变量"

// 常量声明
const (
	Pi      = 3.14159
	MaxSize = 100
)

// 使用iota创建枚举
const (
	Monday    = iota // 0
	Tuesday          // 1
	Wednesday        // 2
	Thursday         // 3
	Friday           // 4
	Saturday         // 5
	Sunday           // 6
)

// 文件大小常量（iota高级用法）
const (
	_  = iota             // 跳过0
	KB = 1 << (10 * iota) // 1 << 10 = 1024
	MB                    // 1 << 20 = 1024 * 1024
	GB                    // 1 << 30
	TB                    // 1 << 40
)

func main() {
	fmt.Println("=== 1. 变量声明方式 ===")
	demonstrateVariableDeclaration()

	fmt.Println("\n=== 2. 基本数据类型 ===")
	demonstrateBasicTypes()

	fmt.Println("\n=== 3. 类型转换 ===")
	demonstrateTypeConversion()

	fmt.Println("\n=== 4. 常量和枚举 ===")
	demonstrateConstants()

	fmt.Println("\n=== 5. 指针 ===")
	demonstratePointers()

	fmt.Println("\n=== 6. 字符串 ===")
	demonstrateStrings()

	fmt.Println("\n=== 7. 零值 ===")
	demonstrateZeroValues()
}

// 1. 变量声明方式
func demonstrateVariableDeclaration() {
	// 方式一：完整声明（var + 类型）
	var name1 string = "张三"
	fmt.Printf("方式一: %s\n", name1)

	// 方式二：类型推断
	var name2 = "李四" // 编译器自动推断为string
	fmt.Printf("方式二: %s\n", name2)

	// 方式三：短声明（最常用，只能在函数内）
	name3 := "王五" // 简洁且常用
	fmt.Printf("方式三: %s\n", name3)

	// 批量声明
	var (
		age    int     = 25
		height float64 = 1.75
		isUser bool    = true
	)
	fmt.Printf("批量声明: age=%d, height=%.2f, isUser=%v\n", age, height, isUser)

	// 多变量同时声明
	x, y, z := 1, 2, 3
	fmt.Printf("多变量声明: x=%d, y=%d, z=%d\n", x, y, z)

	// Java对比：
	// String name1 = "张三";  // Java必须声明类型
	// var name2 = "李四";     // Java 10+支持var
	// Go的:=更简洁，但只能在函数内使用
}

// 2. 基本数据类型
func demonstrateBasicTypes() {
	// 整数类型
	var i8 int8 = 127          // -128 到 127
	var i16 int16 = 32767      // -32768 到 32767
	var i32 int32 = 2147483647 // ≈ ±21亿（相当于Java的int）
	var i64 int64 = 9223372036854775807
	var i int = 100 // 最常用，32位或64位

	// 无符号整数（Java没有）
	var u8 uint8 = 255
	var u16 uint16 = 65535
	var u32 uint32 = 4294967295
	var ui uint = 100

	fmt.Printf("有符号整数: i8=%d, i16=%d, i32=%d, i64=%d, i=%d\n", i8, i16, i32, i64, i)
	fmt.Printf("无符号整数: u8=%d, u16=%d, u32=%d, ui=%d\n", u8, u16, u32, ui)

	// 浮点类型
	var f32 float32 = 3.14     // 相当于Java的float
	var f64 float64 = 3.141592 // 相当于Java的double（默认）
	fmt.Printf("浮点数: f32=%f, f64=%f\n", f32, f64)

	// 布尔类型
	var b bool = true
	fmt.Printf("布尔: %v\n", b)

	// 字符串
	var s string = "Hello, 世界"
	fmt.Printf("字符串: %s\n", s)

	// 字符（rune）
	var r rune = '中' // rune是int32的别名，表示Unicode码点
	fmt.Printf("字符: %c (Unicode: %d)\n", r, r)

	// 字节
	var by byte = 'A' // byte是uint8的别名
	fmt.Printf("字节: %c (值: %d)\n", by, by)

	// Java对比：
	// int i = 100;          // Java的int是32位
	// long l = 100L;        // Java的long是64位
	// float f = 3.14f;      // Java的float
	// double d = 3.14;      // Java的double
	// boolean b = true;     // Java的boolean
	// String s = "hello";   // Java的String是类
	// char c = 'A';         // Java的char是16位
}

// 3. 类型转换
func demonstrateTypeConversion() {
	// Go要求显式类型转换（没有自动转换）
	var i int = 42
	var f float64 = float64(i) // 必须显式转换
	var u uint = uint(i)

	fmt.Printf("int -> float64: %d -> %f\n", i, f)
	fmt.Printf("int -> uint: %d -> %d\n", i, u)

	// 浮点到整数（会截断小数部分）
	var pi float64 = 3.14159
	var iPi int = int(pi)
	fmt.Printf("float64 -> int: %f -> %d (截断)\n", pi, iPi)

	// 字符串转换
	str := "65"
	// num, _ := strconv.Atoi(str)  // 字符串转整数（需要导入strconv）
	fmt.Printf("字符串: %s\n", str)

	// Java对比：
	// int i = 42;
	// double d = i;           // Java自动向上转型
	// int j = (int) d;        // Java向下转型需要强制转换
	// Go所有转换都必须显式！
}

// 4. 常量和枚举
func demonstrateConstants() {
	fmt.Printf("Pi = %f\n", Pi)
	fmt.Printf("MaxSize = %d\n", MaxSize)

	// 使用iota枚举
	fmt.Printf("今天是星期%d\n", Wednesday) // 2

	// 文件大小
	fmt.Printf("1 KB = %d bytes\n", KB)
	fmt.Printf("1 MB = %d bytes\n", MB)
	fmt.Printf("1 GB = %d bytes\n", GB)

	// 类型枚举示例
	type Status int
	const (
		StatusPending Status = iota // 0
		StatusRunning               // 1
		StatusSuccess               // 2
		StatusFailed                // 3
	)
	fmt.Printf("任务状态: %d (运行中)\n", StatusRunning)

	// Java对比：
	// public static final double PI = 3.14159;
	// public enum Day { MONDAY, TUESDAY, WEDNESDAY }
	// Go的iota更灵活，可以实现复杂的枚举值
}

// 5. 指针
func demonstratePointers() {
	// 声明和使用指针
	var x int = 10
	var p *int = &x // p是指向x的指针

	fmt.Printf("x的值: %d\n", x)
	fmt.Printf("x的地址: %p\n", &x)
	fmt.Printf("p的值(地址): %p\n", p)
	fmt.Printf("p指向的值: %d\n", *p) // 解引用

	// 通过指针修改值
	*p = 20
	fmt.Printf("修改后x的值: %d\n", x)

	// new函数创建指针
	q := new(int) // 分配内存并返回指针，值为零值
	*q = 30
	fmt.Printf("q指向的值: %d\n", *q)

	// Java对比：
	// Java除了基本类型，所有对象都是引用
	// Java没有显式的指针语法（& 和 *）
	// Integer obj = new Integer(10);  // obj是引用
	// Go的指针更明确，但没有指针运算（更安全）
}

// 6. 字符串
func demonstrateStrings() {
	// 字符串是不可变的
	s := "Hello, 世界"

	// 获取字符串长度（字节数）
	fmt.Printf("字符串: %s\n", s)
	fmt.Printf("字节长度: %d\n", len(s)) // 13（英文1字节，中文3字节）

	// 字符串拼接
	s1 := "Hello"
	s2 := "World"
	s3 := s1 + ", " + s2
	fmt.Printf("拼接: %s\n", s3)

	// 原始字符串（支持多行，不转义）
	raw := `这是一个
多行字符串，
\n 不会被转义`
	fmt.Printf("原始字符串:\n%s\n", raw)

	// 字符串索引（获取字节）
	fmt.Printf("第一个字节: %c\n", s[0]) // 'H'

	// 遍历字符串（按字符）
	for i, r := range "Go语言" {
		fmt.Printf("位置%d: %c\n", i, r)
	}

	// Java对比：
	// String s = "Hello";              // 不可变
	// int len = s.length();            // 字符数（不是字节数）
	// String s3 = s1 + ", " + s2;      // 拼接
	// String raw = """                 // Java 15+ 文本块
	//     多行
	//     字符串
	//     """;
}

// 7. 零值
func demonstrateZeroValues() {
	// Go的变量有零值（不是null/nil）
	var i int
	var f float64
	var b bool
	var s string
	var p *int

	fmt.Printf("int零值: %d\n", i)      // 0
	fmt.Printf("float64零值: %f\n", f)  // 0.0
	fmt.Printf("bool零值: %v\n", b)     // false
	fmt.Printf("string零值: '%s'\n", s) // ""（空字符串）
	fmt.Printf("*int零值: %v\n", p)     // nil

	// Java对比：
	// int i = 0;              // Java基本类型有默认值
	// Integer obj = null;     // Java引用类型默认null
	// Go的字符串零值是""，不是nil
	// Go的bool零值是false，Java也一样
}

/*
运行输出说明：
- 展示了各种变量声明方式
- 演示了所有基本数据类型
- 说明了类型转换的必要性
- 展示了常量和iota的用法
- 演示了指针的基本操作
- 说明了字符串的特性
- 展示了零值的概念

与Java的核心差异：
1. Go的短声明:=更简洁
2. Go有无符号整数类型
3. Go的类型转换必须显式
4. Go的iota实现枚举更灵活
5. Go有显式指针语法
6. Go的string是基本类型
*/
