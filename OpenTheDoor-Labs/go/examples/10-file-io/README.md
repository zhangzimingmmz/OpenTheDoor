# 文件与IO操作示例

学习Go语言中的文件读写、缓冲IO以及JSON处理。

## 🎯 学习目标

- 掌握文件读取 (`os.ReadFile`, `os.Open`)
- 掌握文件写入 (`os.WriteFile`, `os.Create`)
- 理解缓冲IO (`bufio`) 的使用
- 学会 JSON 的序列化与反序列化 (`encoding/json`)

## 🚀 运行示例

```bash
cd examples/10-file-io
go run main.go
```

## 📖 核心概念

### 1. os 包
提供平台无关的操作系统功能接口，包括文件操作。
- `os.Open()`: 打开文件（只读）
- `os.Create()`: 创建或截断文件
- `os.ReadFile()`: 读取整个文件到内存（便捷方法）

### 2. bufio 包
提供带缓冲的 I/O 操作，性能更好，适合大文件或逐行读取。
- `bufio.NewScanner()`: 逐行读取
- `bufio.NewWriter()`: 带缓冲写入

### 3. encoding/json
Go 内置的 JSON 处理库。
- `json.Marshal()`: 结构体 -> JSON 字符串
- `json.Unmarshal()`: JSON 字符串 -> 结构体
- **Tag**: 使用 `` `json:"name"` `` 控制字段映射

## 💡 与Java对比

| 特性 | Java | Go |
|------|------|----|
| **文件类** | `java.io.File`, `Path` | `os.File` |
| **读取所有** | `Files.readAllBytes()` | `os.ReadFile()` |
| **缓冲流** | `BufferedReader` | `bufio.Scanner` / `Reader` |
| **JSON库** | Jackson / Gson | `encoding/json` (标准库) |
| **序列化注解** | `@JsonProperty` | `` `json:"..."` `` tag |

## 🎓 练习任务

1. 修改程序，尝试追加内容到文件而不是覆盖（使用 `os.OpenFile` 和 `os.O_APPEND`）。
2. 创建一个包含复杂嵌套结构的 JSON 并解析它。
3. 编写一个程序，统计一个文本文件中的行数。

## ➡️ 下一步

- [Web开发基础](../11-web-basics/) - HTTP服务
