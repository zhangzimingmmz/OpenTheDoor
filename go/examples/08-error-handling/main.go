package main

import (
	"errors"
	"fmt"
	"os"
)

/*
错误处理示例

本示例演示：
1. 基本错误创建与处理
2. 自定义错误类型
3. 错误包装与解包 (Go 1.13+)
4. Panic 和 Recover
*/

func main() {
	fmt.Println("=== 1. 基本错误处理 ===")
	demonstrateBasicError()

	fmt.Println("\n=== 2. 自定义错误类型 ===")
	demonstrateCustomError()

	fmt.Println("\n=== 3. 错误包装 (Go 1.13+) ===")
	demonstrateErrorWrapping()

	fmt.Println("\n=== 4. Panic 和 Recover ===")
	demonstratePanicRecover()
	fmt.Println("程序正常结束")
}

// --- 1. 基本错误处理 ---

// 预定义错误（哨兵错误）
var ErrNotFound = errors.New("resource not found")

func findUser(id int) (string, error) {
	if id < 0 {
		return "", fmt.Errorf("invalid id: %d", id)
	}
	if id == 0 {
		return "", ErrNotFound
	}
	return "UserAlice", nil
}

func demonstrateBasicError() {
	// 正常情况
	if name, err := findUser(1); err != nil {
		fmt.Println("Error:", err)
	} else {
		fmt.Println("Found:", name)
	}

	// 错误情况
	if _, err := findUser(0); err != nil {
		// 判断特定错误
		if err == ErrNotFound {
			fmt.Println("未找到用户")
		} else {
			fmt.Println("其他错误:", err)
		}
	}

	// Java对比：
	// try {
	//     String name = findUser(id);
	// } catch (NotFoundException e) {
	//     ...
	// }
}

// --- 2. 自定义错误类型 ---

// ValidationError 自定义验证错误
type ValidationError struct {
	Field   string
	Message string
}

// 实现 error 接口
func (e *ValidationError) Error() string {
	return fmt.Sprintf("validation failed on field '%s': %s", e.Field, e.Message)
}

func validate(username string) error {
	if len(username) == 0 {
		return &ValidationError{Field: "username", Message: "cannot be empty"}
	}
	return nil
}

func demonstrateCustomError() {
	err := validate("")
	if err != nil {
		fmt.Println("Error:", err)

		// 类型断言获取详细信息
		if vErr, ok := err.(*ValidationError); ok {
			fmt.Printf("字段: %s, 原因: %s\n", vErr.Field, vErr.Message)
		}
	}
}

// --- 3. 错误包装 (Go 1.13+) ---

var ErrPermission = errors.New("permission denied")

func openFile(filename string) error {
	if _, err := os.Open(filename); err != nil {
		// 使用 %w 包装错误
		return fmt.Errorf("failed to open file %s: %w", filename, ErrPermission)
	}
	return nil
}

func demonstrateErrorWrapping() {
	err := openFile("non-existent.txt")
	if err != nil {
		fmt.Println("完整错误:", err)

		// errors.Is: 判断错误链中是否包含特定错误
		if errors.Is(err, ErrPermission) {
			fmt.Println("检测到权限错误！")
		}

		// errors.Unwrap: 解包一层
		inner := errors.Unwrap(err)
		fmt.Println("内部错误:", inner)
	}
}

// --- 4. Panic 和 Recover ---

func triggerPanic() {
	fmt.Println("准备触发 panic...")
	panic("something went terribly wrong")
	// fmt.Println("这行代码不会执行")
}

func safeCall() {
	// defer 必须在 panic 之前声明
	defer func() {
		// recover 捕获 panic
		if r := recover(); r != nil {
			fmt.Println("捕获到 panic:", r)
			fmt.Println("从 panic 中恢复，程序继续运行")
		}
	}()

	triggerPanic()
}

func demonstratePanicRecover() {
	fmt.Println("调用可能崩溃的函数...")
	safeCall()
	fmt.Println("safeCall 返回后继续执行")

	// Java对比：
	// try {
	//     triggerException();
	// } catch (RuntimeException e) {
	//     System.out.println("Caught: " + e);
	// }
}
