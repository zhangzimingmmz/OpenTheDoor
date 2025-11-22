package main

import (
	"fmt"

	// 导入本地包
	// 注意：在实际项目中，通常使用模块全名导入，如 "github.com/user/repo/examples/09-packages/mypkg"
	// 这里为了演示方便，假设我们在同一模块下
	"github.com/yourusername/go-learning/examples/09-packages/mypkg"
)

/*
包管理示例

本示例演示：
1. 导入自定义包
2. 访问导出成员 (Public)
3. 无法访问未导出成员 (Private)
4. init() 执行顺序
*/

func init() {
	fmt.Println("[main] 初始化完成 (init执行)")
}

func main() {
	fmt.Println("=== 包与可见性演示 ===")

	// 1. 访问导出的变量
	fmt.Println("访问 PublicVar:", mypkg.PublicVar)

	// 尝试访问私有变量（编译错误）
	// fmt.Println(mypkg.privateVar)

	// 2. 调用导出的函数
	mypkg.PublicFunc()

	// 尝试调用私有函数（编译错误）
	// mypkg.privateFunc()

	// 3. 使用结构体
	obj := mypkg.PublicStruct{
		PublicField: "可以直接赋值",
		// privateField: "不能直接赋值", // 编译错误
	}
	fmt.Printf("结构体: %+v\n", obj)

	// 4. 通过工厂函数创建（推荐方式）
	// 这样可以初始化私有字段
	obj2 := mypkg.NewPublicStruct("公开值", "私有值")
	fmt.Printf("工厂创建: %+v\n", obj2)

	// Java对比：
	// Java通过类路径(Classpath)管理包
	// Go通过Go Modules管理包
	// Go的可见性控制非常简单：首字母大小写
}
