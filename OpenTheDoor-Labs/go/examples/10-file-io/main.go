package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"os"
)

/*
文件与IO操作示例

本示例演示：
1. 简单文件读写 (os.WriteFile, os.ReadFile)
2. 缓冲IO与逐行读取 (bufio)
3. JSON 序列化与反序列化
*/

// Config 配置结构体 (用于JSON演示)
type Config struct {
	ServerName string   `json:"server_name"`
	Port       int      `json:"port"`
	Enabled    bool     `json:"enabled"`
	Tags       []string `json:"tags,omitempty"` // omitempty: 如果为空则不输出
}

func main() {
	fmt.Println("=== 1. 简单文件读写 ===")
	demonstrateSimpleIO()

	fmt.Println("\n=== 2. 缓冲IO (逐行读取) ===")
	demonstrateBufferedIO()

	fmt.Println("\n=== 3. JSON 处理 ===")
	demonstrateJSON()
}

// 1. 简单文件读写
func demonstrateSimpleIO() {
	filename := "test.txt"
	content := "Hello, Go File IO!\n这是第二行。"

	// 写入文件
	// 0644 是文件权限 (Unix风格: rw-r--r--)
	err := os.WriteFile(filename, []byte(content), 0644)
	if err != nil {
		fmt.Println("写入失败:", err)
		return
	}
	fmt.Println("文件写入成功:", filename)

	// 读取文件
	data, err := os.ReadFile(filename)
	if err != nil {
		fmt.Println("读取失败:", err)
		return
	}
	fmt.Printf("读取内容:\n%s\n", string(data))

	// 清理
	// os.Remove(filename)
}

// 2. 缓冲IO (逐行读取)
func demonstrateBufferedIO() {
	filename := "lines.txt"

	// 创建文件并写入多行
	file, err := os.Create(filename)
	if err != nil {
		fmt.Println("创建失败:", err)
		return
	}

	writer := bufio.NewWriter(file)
	writer.WriteString("Line 1: Go is fast\n")
	writer.WriteString("Line 2: Go is simple\n")
	writer.WriteString("Line 3: Go is fun\n")
	writer.Flush() // 确保写入磁盘
	file.Close()

	// 打开文件进行读取
	file, err = os.Open(filename)
	if err != nil {
		fmt.Println("打开失败:", err)
		return
	}
	defer file.Close()

	// 使用 Scanner 逐行读取
	scanner := bufio.NewScanner(file)
	lineCount := 0
	fmt.Println("逐行读取内容:")
	for scanner.Scan() {
		lineCount++
		fmt.Printf("第 %d 行: %s\n", lineCount, scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("扫描错误:", err)
	}

	// Java对比：
	// BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
	// String line;
	// while ((line = reader.readLine()) != null) { ... }
}

// 3. JSON 处理
func demonstrateJSON() {
	// 结构体 -> JSON (Marshal)
	config := Config{
		ServerName: "MyGoServer",
		Port:       8080,
		Enabled:    true,
		Tags:       []string{"web", "api"},
	}

	jsonData, err := json.MarshalIndent(config, "", "  ") // Indent用于美化输出
	if err != nil {
		fmt.Println("JSON编码失败:", err)
		return
	}
	fmt.Printf("JSON输出:\n%s\n", string(jsonData))

	// JSON -> 结构体 (Unmarshal)
	jsonStr := `{"server_name": "NewServer", "port": 9090, "enabled": false}`
	var newConfig Config

	err = json.Unmarshal([]byte(jsonStr), &newConfig)
	if err != nil {
		fmt.Println("JSON解码失败:", err)
		return
	}
	fmt.Printf("解码后结构体: %+v\n", newConfig)

	// Java对比：
	// ObjectMapper mapper = new ObjectMapper();
	// String json = mapper.writeValueAsString(obj);
	// Config c = mapper.readValue(json, Config.class);
}
