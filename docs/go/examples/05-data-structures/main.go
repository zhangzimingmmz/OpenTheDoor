package main

import "fmt"

/*
数据结构示例

Go的核心数据结构：
1. 数组 (Array)
2. 切片 (Slice) - 最常用
3. 映射 (Map)
4. 结构体 (Struct)
*/

func main() {
	fmt.Println("=== 1. 数组 (Array) ===")
	demonstrateArrays()

	fmt.Println("\n=== 2. 切片 (Slice) ===")
	demonstrateSlices()

	fmt.Println("\n=== 3. 映射 (Map) ===")
	demonstrateMaps()

	fmt.Println("\n=== 4. 结构体 (Struct) ===")
	demonstrateStructs()
}

// 1. 数组
func demonstrateArrays() {
	// 数组：固定长度，长度是类型的一部分
	var arr1 [5]int                 // 零值：[0 0 0 0 0]
	arr2 := [5]int{1, 2, 3, 4, 5}   // 初始化
	arr3 := [...]int{1, 2, 3, 4, 5} // 自动推断长度

	fmt.Printf("arr1: %v\n", arr1)
	fmt.Printf("arr2: %v\n", arr2)
	fmt.Printf("arr3: %v, len=%d\n", arr3, len(arr3))

	// 访问和修改
	arr2[0] = 10
	fmt.Printf("修改后: %v\n", arr2)

	// 数组是值类型（复制整个数组）
	arr4 := arr2 // 复制
	arr4[0] = 999
	fmt.Printf("arr2: %v\n", arr2) // 不变
	fmt.Printf("arr4: %v\n", arr4) // 改变

	// Java对比：
	// int[] arr = new int[5];
	// int[] arr = {1, 2, 3, 4, 5};
	// Java的数组是引用类型，Go的是值类型
}

// 2. 切片 - Go最重要的数据结构
func demonstrateSlices() {
	// 创建切片
	var s1 []int               // nil切片
	s2 := []int{}              // 空切片
	s3 := []int{1, 2, 3, 4, 5} // 字面量
	s4 := make([]int, 5)       // make：长度5，容量5
	s5 := make([]int, 3, 10)   // 长度3，容量10

	fmt.Printf("s1: %v, len=%d, cap=%d\n", s1, len(s1), cap(s1))
	fmt.Printf("s3: %v, len=%d, cap=%d\n", s3, len(s3), cap(s3))
	fmt.Printf("s5: %v, len=%d, cap=%d\n", s5, len(s5), cap(s5))

	// append：添加元素
	s2 = append(s2, 1)
	s2 = append(s2, 2, 3, 4)
	s2 = append(s2, s3...) // 展开切片
	fmt.Printf("append后: %v\n", s2)

	// 切片操作（索引）
	numbers := []int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
	fmt.Printf("numbers[2:5]: %v\n", numbers[2:5]) // [2 3 4]
	fmt.Printf("numbers[:3]: %v\n", numbers[:3])   // [0 1 2]
	fmt.Printf("numbers[7:]: %v\n", numbers[7:])   // [7 8 9]
	fmt.Printf("numbers[:]: %v\n", numbers[:])     // 全部

	// copy切片
	dst := make([]int, 3)
	copy(dst, numbers)
	fmt.Printf("copy: %v\n", dst)

	// Java对比：
	// List<Integer> list = new ArrayList<>();
	// list.add(1);
	// Go的切片更底层但性能更好
}

// 3. 映射 (Map)
func demonstrateMaps() {
	// 创建map
	var m1 map[string]int      // nil map，不能直接使用
	m2 := make(map[string]int) // 空map
	m3 := map[string]int{      // 字面量
		"apple":  5,
		"banana": 3,
		"orange": 7,
	}

	fmt.Printf("m3: %v\n", m3)

	// 添加和修改
	m2["one"] = 1
	m2["two"] = 2
	m2["three"] = 3
	fmt.Printf("m2: %v\n", m2)

	// 获取值
	value := m2["two"]
	fmt.Printf("m2[\"two\"] = %d\n", value)

	// 检查键是否存在
	value, exists := m2["four"]
	if exists {
		fmt.Printf("找到: %d\n", value)
	} else {
		fmt.Println("四 不存在")
	}

	// 删除键
	delete(m2, "one")
	fmt.Printf("删除后: %v\n", m2)

	// 遍历map
	fmt.Println("遍历m3:")
	for key, value := range m3 {
		fmt.Printf("  %s: %d\n", key, value)
	}

	// Java对比：
	// Map<String, Integer> map = new HashMap<>();
	// map.put("one", 1);
	// map.get("one");
	// map.containsKey("one");
	// Go的map是内置类型，使用更简洁
}

// 4. 结构体 (Struct)
type Person struct {
	Name  string
	Age   int
	Email string
}

type Address struct {
	Street string
	City   string
}

type Employee struct {
	Person // 嵌入（组合）
	Address
	Department string
	Salary     float64
}

func demonstrateStructs() {
	// 创建结构体
	p1 := Person{Name: "张三", Age: 25, Email: "zhangsan@example.com"}
	p2 := Person{"李四", 30, "lisi@example.com"} // 按顺序
	var p3 Person                              // 零值

	fmt.Printf("p1: %+v\n", p1)
	fmt.Printf("p2: %+v\n", p2)
	fmt.Printf("p3: %+v\n", p3)

	// 访问字段
	fmt.Printf("姓名: %s, 年龄: %d\n", p1.Name, p1.Age)

	// 修改字段
	p1.Age = 26
	fmt.Printf("修改后: %+v\n", p1)

	// 指针
	p4 := &Person{Name: "王五", Age: 28}
	p4.Age = 29 // 自动解引用
	fmt.Printf("p4: %+v\n", p4)

	// 嵌入（组合）
	emp := Employee{
		Person:     Person{Name: "赵六", Age: 35},
		Address:    Address{Street: "中关村大街", City: "北京"},
		Department: "技术部",
		Salary:     15000,
	}
	fmt.Printf("员工: %+v\n", emp)
	fmt.Printf("姓名: %s, 城市: %s\n", emp.Name, emp.City) // 直接访问嵌入字段

	// 结构体切片
	people := []Person{
		{Name: "Alice", Age: 25},
		{Name: "Bob", Age: 30},
		{Name: "Charlie", Age: 35},
	}

	for _, person := range people {
		fmt.Printf("  %s: %d岁\n", person.Name, person.Age)
	}

	// Java对比：
	// public class Person {
	//     private String name;
	//     private int age;
	//     // getters/setters
	// }
	// Person p = new Person();
	// p.setName("张三");
	// Go的结构体更简洁，字段直接访问
}

/*
数据结构总结：

1. 数组 (Array):
   - 固定长度
   - 值类型（复制整个数组）
   - 较少使用，通常用切片代替

2. 切片 (Slice):
   - 动态长度
   - 引用类型
   - 最常用的序列类型
   - len()长度, cap()容量
   - append()添加元素

3. 映射 (Map):
   - 键值对集合
   - 无序
   - 引用类型
   - 内置类型，使用简洁

4. 结构体 (Struct):
   - 自定义类型
   - 值类型
   - 使用组合代替继承
   - 字段直接访问（大写=导出）

与Java的核心差异：
1. Go的数组是值类型，Java是引用类型
2. Go的切片类似ArrayList但更底层
3. Go的map是内置类型，Java需要导入
4. Go用struct+方法代替类
5. Go用组合代替继承
*/
