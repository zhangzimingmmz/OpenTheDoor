package main

import "fmt"

/*
函数示例

Go函数的特性：
1. 多返回值（Java没有）
2. 命名返回值
3. 可变参数
4. 函数作为值
5. 匿名函数和闭包
6. 方法（接收者函数）
*/

func main() {
	fmt.Println("=== 1. 基本函数 ===")
	demonstrateBasicFunctions()

	fmt.Println("\n===2. 多返回值 ===")
	demonstrateMultipleReturns()

	fmt.Println("\n=== 3. 可变参数 ===")
	demonstrateVariadicFunctions()

	fmt.Println("\n=== 4. 函数作为值 ===")
	demonstrateFunctionValues()

	fmt.Println("\n=== 5. 匿名函数和闭包 ===")
	demonstrateClosures()

	fmt.Println("\n=== 6. 方法（接收者） ===")
	demonstrateMethods()
}

// 1. 基本函数
func add(a int, b int) int {
	return a + b
}

// 参数类型相同可以合并
func subtract(a, b int) int {
	return a - b
}

// 无返回值
func printMessage(msg string) {
	fmt.Println(msg)
}

func demonstrateBasicFunctions() {
	result1 := add(10, 20)
	fmt.Printf("10 + 20 = %d\n", result1)

	result2 := subtract(30, 15)
	fmt.Printf("30 - 15 = %d\n", result2)

	printMessage("Hello, Functions!")

	// Java对比：
	// public static int add(int a, int b) { return a + b; }
	// Go不需要public/static关键字，首字母大写即为public
}

// 2. 多返回值（Go特有优势）
func divide(a, b float64) (float64, error) {
	if b == 0 {
		return 0, fmt.Errorf("除数不能为0")
	}
	return a / b, nil
}

// 命名返回值
func getNameAndAge() (name string, age int) {
	name = "张三"
	age = 25
	return // 自动返回命名的变量
}

func demonstrateMultipleReturns() {
	// 接收多个返回值
	result, err := divide(10, 2)
	if err != nil {
		fmt.Printf("错误: %v\n", err)
	} else {
		fmt.Printf("10 / 2 = %.2f\n", result)
	}

	// 忽略错误（使用下划线）
	result2, _ := divide(20, 4)
	fmt.Printf("20 / 4 = %.2f\n", result2)

	// 命名返回值
	name, age := getNameAndAge()
	fmt.Printf("姓名: %s, 年龄: %d\n", name, age)

	// Java对比：
	// Java不支持多返回值，通常需要：
	// - 返回数组或集合
	// - 创建专门的返回对象
	// - 使用out参数
	// Go的多返回值更优雅，特别适合错误处理
}

// 3. 可变参数
func sum(numbers ...int) int {
	total := 0
	for _, num := range numbers {
		total += num
	}
	return total
}

func printf(format string, args ...interface{}) {
	fmt.Printf(format, args...)
}

func demonstrateVariadicFunctions() {
	fmt.Printf("sum(1, 2, 3) = %d\n", sum(1, 2, 3))
	fmt.Printf("sum(1, 2, 3, 4, 5) = %d\n", sum(1, 2, 3, 4, 5))

	// 传递切片
	nums := []int{10, 20, 30}
	fmt.Printf("sum(nums...) = %d\n", sum(nums...))

	printf("姓名: %s, 年龄: %d\n", "李四", 30)

	// Java对比：
	// public static int sum(int... numbers) { }
	// Go和Java的可变参数语法相似，但Go用...前缀
}

// 4. 函数作为值
func operate(a, b int, op func(int, int) int) int {
	return op(a, b)
}

func demonstrateFunctionValues() {
	// 函数赋值给变量
	addFunc := func(x, y int) int {
		return x + y
	}

	multiplyFunc := func(x, y int) int {
		return x * y
	}

	fmt.Printf("使用add函数: %d\n", operate(10, 5, addFunc))
	fmt.Printf("使用multiply函数: %d\n", operate(10, 5, multiplyFunc))

	// Java对比：
	// Java 8+ 使用函数式接口和Lambda：
	// BiFunction<Integer, Integer, Integer> addFunc = (x, y) -> x + y;
	// Go的函数是一等公民，使用更直接
}

// 5. 匿名函数和闭包
func counter() func() int {
	count := 0
	return func() int {
		count++
		return count
	}
}

func demonstrateClosures() {
	// 匿名函数
	func(msg string) {
		fmt.Println("匿名函数:", msg)
	}("Hello")

	// 闭包：捕获外部变量
	c1 := counter()
	fmt.Printf("计数器1: %d\n", c1())
	fmt.Printf("计数器1: %d\n", c1())
	fmt.Printf("计数器1: %d\n", c1())

	c2 := counter()
	fmt.Printf("计数器2: %d\n", c2())

	// 实用闭包示例
	multiplier := func(factor int) func(int) int {
		return func(x int) int {
			return x * factor
		}
	}

	double := multiplier(2)
	triple := multiplier(3)

	fmt.Printf("double(5) = %d\n", double(5))
	fmt.Printf("triple(5) = %d\n", triple(5))

	// Java对比：
	// Java也支持闭包（Lambda捕获变量），但Go更直观
}

// 6. 方法（接收者函数）
type Rectangle struct {
	width, height float64
}

// 值接收者方法
func (r Rectangle) Area() float64 {
	return r.width * r.height
}

// 指针接收者方法（可修改接收者）
func (r *Rectangle) Scale(factor float64) {
	r.width *= factor
	r.height *= factor
}

// String方法（类似Java的toString）
func (r Rectangle) String() string {
	return fmt.Sprintf("Rectangle(%.2f x %.2f)", r.width, r.height)
}

func demonstrateMethods() {
	rect := Rectangle{width: 10, height: 5}

	fmt.Printf("矩形: %v\n", rect)
	fmt.Printf("面积: %.2f\n", rect.Area())

	// 调用指针方法（自动取地址）
	rect.Scale(2)
	fmt.Printf("缩放后: %v\n", rect)
	fmt.Printf("新面积: %.2f\n", rect.Area())

	// Java对比：
	// class Rectangle {
	//     double width, height;
	//     double area() { return width * height; }
	//     void scale(double factor) { width *= factor; height *= factor; }
	//     @Override String toString() { ... }
	// }
	// Go用方法接收者代替类，更灵活
}

/*
函数总结：

1. 基本函数：
   - 不需要class包装
   - 首字母大写=public，小写=private

2. 多返回值：
   - Go的杀手级特性
   - 常用于返回(结果, 错误)
   - 命名返回值可提高可读性

3. 可变参数：
   - ...Type语法
   - 切片展开用...

4. 函数是一等公民：
   - 可赋值给变量
   - 可作为参数
   - 可作为返回值

5. 闭包：
   - 捕获外部变量
   - 实现状态封装

6. 方法：
   - 通过接收者关联到类型
   - 值接收者vs指针接收者
   - Go没有class，但有方法

与Java的核心差异：
1. 支持多返回值（Java没有）
2. 函数是一等公民（Java需要函数式接口）
3. 使用方法接收者代替类方法
4. defer可以在函数内任意位置使用
*/
