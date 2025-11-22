package main

import "fmt"

/*
控制流程示例

Go的控制流程包括：
1. if/else - 条件语句
2. for - 循环(唯一的循环结构)
3. switch - 分支语句
4. defer - 延迟执行

与Java的关键差异：
- 条件表达式不需要括号
- Go只有for，没有while
- switch默认break（不会fall through）
- defer是Go独有的特性
*/

func main() {
	fmt.Println("=== 1. if/else 条件语句 ===")
	demonstrateIf()

	fmt.Println("\n=== 2. for 循环 ===")
	demonstrateFor()

	fmt.Println("\n=== 3. switch 分支语句 ===")
	demonstrateSwitch()

	fmt.Println("\n=== 4. defer 延迟执行 ===")
	demonstrateDefer()
}

// 1. if/else 条件语句
func demonstrateIf() {
	// 基本if语句（不需要括号）
	age := 20
	if age >= 18 {
		fmt.Println("成年人")
	}

	// if-else
	score := 85
	if score >= 90 {
		fmt.Println("优秀")
	} else if score >= 80 {
		fmt.Println("良好")
	} else if score >= 60 {
		fmt.Println("及格")
	} else {
		fmt.Println("不及格")
	}

	// if的简短语句（变量作用域仅在if块内）
	if num := 10; num > 5 {
		fmt.Printf("num=%d 大于5\n", num)
	}
	// num在这里不可访问

	// 实际应用：错误检查
	if err := checkAge(15); err != nil {
		fmt.Printf("错误: %v\n", err)
	} else {
		fmt.Println("年龄检查通过")
	}

	// Java对比：
	// if (age >= 18) { }       // Java需要括号
	// if (int num = 10; num > 5) { }  // Java 10+ 也支持初始化语句
}

func checkAge(age int) error {
	if age < 18 {
		return fmt.Errorf("年龄不足18岁")
	}
	return nil
}

// 2. for 循环
func demonstrateFor() {
	// 形式一：完整的for循环（类似Java）
	fmt.Println("完整for循环:")
	for i := 0; i < 5; i++ {
		fmt.Printf("%d ", i)
	}
	fmt.Println()

	// 形式二：条件循环（类似while）
	fmt.Println("条件循环（类似while）:")
	j := 0
	for j < 3 {
		fmt.Printf("%d ", j)
		j++
	}
	fmt.Println()

	// 形式三：无限循环（类似while(true)）
	fmt.Println("无限循环（带break）:")
	k := 0
	for {
		if k >= 3 {
			break
		}
		fmt.Printf("%d ", k)
		k++
	}
	fmt.Println()

	// 形式四：range遍历（Go特有）
	fmt.Println("range遍历数组:")
	numbers := []int{10, 20, 30, 40, 50}
	for index, value := range numbers {
		fmt.Printf("numbers[%d]=%d ", index, value)
	}
	fmt.Println()

	// 只要值，不要索引
	fmt.Println("只遍历值:")
	for _, value := range numbers {
		fmt.Printf("%d ", value)
	}
	fmt.Println()

	// 只要索引
	fmt.Println("只遍历索引:")
	for index := range numbers {
		fmt.Printf("%d ", index)
	}
	fmt.Println()

	// range遍历字符串
	fmt.Println("遍历字符串:")
	for i, char := range "Go语言" {
		fmt.Printf("[%d]%c ", i, char)
	}
	fmt.Println()

	// continue和break
	fmt.Println("使用continue和break:")
	for i := 0; i < 10; i++ {
		if i%2 == 0 {
			continue // 跳过偶数
		}
		if i > 7 {
			break // 大于7停止
		}
		fmt.Printf("%d ", i)
	}
	fmt.Println()

	// Java对比：
	// for (int i = 0; i < 5; i++) { }      // Java的for
	// while (j < 3) { }                     // Java的while
	// while (true) { }                      // Java的无限循环
	// for (int num : numbers) { }           // Java的增强for
	// Go只有for，但形式灵活
}

// 3. switch 分支语句
func demonstrateSwitch() {
	// 基本switch
	day := 3
	switch day {
	case 1:
		fmt.Println("星期一")
	case 2:
		fmt.Println("星期二")
	case 3:
		fmt.Println("星期三")
	case 4:
		fmt.Println("星期四")
	case 5:
		fmt.Println("星期五")
	case 6, 7: // 多个值
		fmt.Println("周末")
	default:
		fmt.Println("无效的日期")
	}

	// switch带简短语句
	switch hour := 14; {
	case hour < 12:
		fmt.Println("上午")
	case hour < 18:
		fmt.Println("下午")
	default:
		fmt.Println("晚上")
	}

	// 无条件switch（替代多个if-else）
	score := 85
	switch {
	case score >= 90:
		fmt.Println("A")
	case score >= 80:
		fmt.Println("B")
	case score >= 70:
		fmt.Println("C")
	case score >= 60:
		fmt.Println("D")
	default:
		fmt.Println("F")
	}

	// fallthrough（显式继续下一个case）
	num := 2
	fmt.Print("fallthrough示例: ")
	switch num {
	case 1:
		fmt.Print("一 ")
	case 2:
		fmt.Print("二 ")
		fallthrough // 继续执行下一个case
	case 3:
		fmt.Print("三 ")
	}
	fmt.Println()

	// 类型switch（接口的类型判断）
	var x interface{} = 42
	switch v := x.(type) {
	case int:
		fmt.Printf("整数: %d\n", v)
	case string:
		fmt.Printf("字符串: %s\n", v)
	case bool:
		fmt.Printf("布尔: %v\n", v)
	default:
		fmt.Printf("未知类型\n")
	}

	// Java对比：
	// switch (day) {
	//     case 1:
	//         System.out.println("星期一");
	//         break;  // Java需要break
	//     case 2:
	//         System.out.println("星期二");
	//         break;
	// }
	// Go的switch默认break，更安全
	// Java 14+ 支持switch表达式，但Go的switch更强大
}

// 4. defer 延迟执行
func demonstrateDefer() {
	fmt.Println("开始")

	// defer会在函数返回前执行
	defer fmt.Println("defer 1: 最后执行")
	defer fmt.Println("defer 2: 倒数第二执行")
	defer fmt.Println("defer 3: 倒数第三执行")

	fmt.Println("中间")

	// defer的典型用途：资源清理
	demoFileOperation()

	// defer with 匿名函数
	x := 1
	defer func() {
		fmt.Printf("defer中的x: %d\n", x) // 会使用最终的x值
	}()
	x = 2

	fmt.Println("结束")

	// 输出顺序：
	// 开始
	// 中间
	// (文件操作输出)
	// 结束
	// defer中的x: 2
	// defer 3: 倒数第三执行
	// defer 2: 倒数第二执行
	// defer 1: 最后执行

	// Java对比：
	// try {
	//     // 操作
	// } finally {
	//     // 清理  // 类似defer
	// }
	// 或 Java 7+ try-with-resources
	// Go的defer更灵活，可以在任何地方使用
}

// defer的实际应用示例
func demoFileOperation() {
	fmt.Println("开始文件操作")

	// 假设打开文件（实际应该是 file, err := os.Open(...)）
	defer fmt.Println("关闭文件（defer确保执行）")

	fmt.Println("读取文件")
	fmt.Println("处理数据")

	// 无论是否发生错误，defer都会执行
	// 类似于Java的finally块
}

// defer的执行顺序示例
func deferOrder() {
	fmt.Println("=== defer执行顺序 ===")

	for i := 0; i < 5; i++ {
		defer fmt.Printf("%d ", i)
	}
	fmt.Println()

	// 输出：4 3 2 1 0（后进先出，栈的顺序）
}

/*
控制流程总结：

1. if/else:
   - 不需要括号
   - 支持简短语句
   - 常用于错误检查

2. for:
   - Go唯一的循环结构
   - 四种形式：完整、条件、无限、range
   - range用于遍历数组、切片、map、字符串

3. switch:
   - 默认break（不会fall through）
   - 支持多个值
   - 支持无条件switch
   - 支持类型switch

4. defer:
   - 延迟执行，函数返回前执行
   - 后进先出（LIFO）
   - 常用于资源清理
   - 类似Java的finally

与Java的核心差异：
1. 条件不需要括号
2. 只有for，没有while/do-while
3. switch默认break
4. defer是Go独有特性
*/
