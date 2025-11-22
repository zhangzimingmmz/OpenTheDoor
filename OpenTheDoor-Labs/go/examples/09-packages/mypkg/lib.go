package mypkg

import "fmt"

// PublicVar 导出的变量 (Public)
var PublicVar = "我是公共变量"

// privateVar 未导出的变量 (Private)
var privateVar = "我是私有变量"

// PublicStruct 导出的结构体
type PublicStruct struct {
	PublicField  string // 导出的字段
	privateField string // 未导出的字段
}

// NewPublicStruct 工厂函数（通常用于创建结构体实例）
func NewPublicStruct(p, v string) *PublicStruct {
	return &PublicStruct{
		PublicField:  p,
		privateField: v,
	}
}

// PublicFunc 导出的函数
func PublicFunc() {
	fmt.Println("调用了 PublicFunc")
	privateFunc() // 包内部可以调用私有函数
}

// privateFunc 未导出的函数
func privateFunc() {
	fmt.Println("调用了 privateFunc (仅包内可见)")
}

// init 函数：包被导入时自动执行
func init() {
	fmt.Println("[mypkg] 初始化完成 (init执行)")
}
