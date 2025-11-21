# 控制流程示例

演示Go语言的控制流程结构：if、for、switch、defer。

## 🎯 学习目标

- 掌握if/else条件语句
- 理解for循环的多种用法（Go只有for）
- 学会switch语句
- 了解defer延迟执行

## 🚀 运行示例

```bash
cd examples/03-control-flow
go run main.go
```

## 📖 核心要点

### 与Java的关键差异

1. **无需括号**：条件语句不需要括号
   - Go: `if x > 0 { }`
   - Java: `if (x > 0) { }`

2. **只有for**：Go没有while
   - `for { }` 相当于 `while(true)`
   - `for condition { }` 相当于 `while(condition)`

3. **switch无需break**：自动break
   - Go的switch默认不会fall through
   - Java需要显式break

4. **defer**：Go独有的延迟执行机制
   - 常用于资源清理
   - 类似Java的try-finally

## 🎓 练习任务

1. 修改if条件，测试不同分支
2. 尝试for的各种形式
3. 编写switch处理业务逻辑
4. 使用defer确保资源释放

## ➡️ 下一步

- [函数示例](../04-functions/)
