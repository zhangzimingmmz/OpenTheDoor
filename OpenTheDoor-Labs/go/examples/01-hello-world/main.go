package main

import "fmt"

/*
Hello World - Go语言的第一个程序

程序结构说明：
1. package main - 声明这是一个可执行程序（main包）
2. import "fmt" - 导入标准库的fmt包，用于格式化输入输出
3. func main() - 程序的入口函数

与Java对比：
- Java需要声明类，Go不需要
- Java的main需要 public static void，Go只需要 func
- Java的main需要 String[] args参数，Go不需要（通过os.Args获取）
*/

func main() {
	// Println: Print Line的缩写，打印后自动换行
	// 相当于Java的 System.out.println()
	fmt.Println("Hello, World!")

	// 其他输出方式示例：

	// Print: 打印但不换行
	fmt.Print("Hello, ")
	fmt.Print("Go!\n")

	// Printf: 格式化打印（类似Java的printf）
	name := "Go语言"
	version := 1.21
	fmt.Printf("欢迎学习 %s %.2f 版本\n", name, version)

	// Sprintf: 格式化成字符串但不打印
	message := fmt.Sprintf("正在学习 %s", name)
	fmt.Println(message)
}

/*
运行方式：
1. go run main.go          # 直接运行
2. go build && ./01-hello-world  # 编译后运行

预期输出：
Hello, World!
Hello, Go!
欢迎学习 Go语言 1.21 版本
正在学习 Go语言
*/
