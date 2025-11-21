# Go语言学习项目 🚀

这是一个为Java开发者设计的系统化Go语言学习项目，包含完整的学习文档、演示代码和实战项目。

## 📚 项目简介

本项目旨在帮助有Java开发经验的工程师快速掌握Go语言。通过对比学习、实践演练和项目实战，你将能够：

- ✅ 理解Go语言的核心概念和设计哲学
- ✅ 掌握Go与Java的关键差异
- ✅ 学会使用Go进行并发编程
- ✅ 构建实际的Web应用和RESTful API
- ✅ 理解Go生态系统和最佳实践

## 🗺️ 学习路线图

预计学习时间：**8周**（每周投入10-15小时）

### 阶段一：基础入门（第1-2周）

| 主题 | 内容 | 文档 | 示例代码 |
|------|------|------|----------|
| 环境搭建 | Go安装、工具配置、第一个程序 | [查看文档](core/01-getting-started/) | [示例](examples/01-hello-world/) |
| 基础语法 | 变量、类型、运算符、常量 | [查看文档](core/02-basics/) | [示例](examples/02-variables-types/) |
| 控制流程 | if/for/switch/defer | [查看文档](core/02-basics/) | [示例](examples/03-control-flow/) |

### 阶段二：核心概念（第3-4周）

| 主题 | 内容 | 文档 | 示例代码 |
|------|------|------|----------|
| 函数 | 函数定义、多返回值、闭包、方法 | [查看文档](core/02-basics/) | [示例](examples/04-functions/) |
| 数据结构 | 数组、切片、映射、结构体 | [查看文档](core/02-basics/) | [示例](examples/05-data-structures/) |
| 面向对象 | 结构体、接口、组合 | [查看文档](core/03-intermediate/) | [示例](examples/06-structs-interfaces/) |

### 阶段三：进阶特性（第5-6周）

| 主题 | 内容 | 文档 | 示例代码 |
|------|------|------|----------|
| 并发编程 | Goroutine、Channel、Select | [查看文档](core/03-intermediate/) | [示例](examples/07-concurrency/) |
| 错误处理 | error接口、panic/recover | [查看文档](core/03-intermediate/) | [示例](examples/08-error-handling/) |
| 包管理 | 包组织、go mod、依赖管理 | [查看文档](core/03-intermediate/) | [示例](examples/09-packages/) |

### 阶段四：实战应用（第7-8周）

| 主题 | 内容 | 文档 | 示例代码 |
|------|------|------|----------|
| 文件IO | 文件操作、JSON/XML处理 | [查看文档](core/04-advanced/) | [示例](examples/10-file-io/) |
| Web开发 | HTTP服务器、路由、中间件 | [查看文档](core/05-practical/) | [示例](examples/11-web-basics/) |
| 数据库 | database/sql、GORM | [查看文档](core/05-practical/) | [示例](examples/12-database/) |
| RESTful API | Gin框架、认证、CRUD | [查看文档](core/05-practical/) | [示例](examples/13-rest-api/) |
| 综合项目 | 博客系统/任务管理API | [查看文档](core/05-practical/) | [示例](examples/14-final-project/) |

## 📁 项目结构

```
.
├── README.md                     # 本文件
├── go.mod                        # Go模块文件
├── docs/                         # 学习文档
│   ├── 01-getting-started/      # 入门指南
│   ├── 02-basics/               # 基础语法
│   ├── 03-intermediate/         # 进阶内容
│   ├── 04-advanced/             # 高级特性
│   └── 05-practical/            # 实战项目
├── examples/                     # 示例代码（14个主题）
│   ├── 01-hello-world/
│   ├── 02-variables-types/
│   ├── ...
│   └── 14-final-project/
└── summaries/                    # 总结文档
    ├── java-vs-go.md            # Java与Go对比
    ├── best-practices.md        # 最佳实践
    └── resources.md             # 学习资源
```

## 🎯 学习方法建议

1. **按顺序学习**：建议按照阶段顺序学习，每个主题都建立在前面的基础上
2. **动手实践**：每个示例都要自己运行和修改，加深理解
3. **对比思考**：注意文档中标注的"与Java对比"部分，理解差异
4. **完成练习**：每个主题末尾都有练习题，务必完成
5. **写总结**：学完每个阶段后，用自己的话总结关键点

## 🔧 环境要求

- **Go版本**：1.21或更高
- **IDE推荐**：
  - VS Code + Go扩展
  - GoLand（JetBrains，Java开发者会很熟悉）
- **可选工具**：
  - Git（版本管理）
  - Postman（API测试）
  - MySQL/PostgreSQL（数据库练习）

## 🚀 快速开始

```bash
# 1. 克隆或进入项目目录
cd /Users/zhangziming/IdeaProjects/go

# 2. 初始化Go模块（如果还没有）
go mod init github.com/yourusername/go-learning

# 3. 运行第一个示例
cd examples/01-hello-world
go run main.go

# 4. 开始学习之旅！
# 按照学习路线图，从第一个主题开始
```

## 📖 核心资源

### 官方文档
- [Go官方网站](https://golang.org/)
- [Go语言之旅](https://tour.golang.org/welcome/1)（中文版）
- [Go标准库文档](https://pkg.go.dev/std)

### 推荐阅读
详见：[resources.md](summaries/resources.md)

### Java vs Go 快速对比
详见：[java-vs-go.md](summaries/java-vs-go.md)

## 💡 特色功能

- ✨ **Java对比**：每个主题都包含与Java的对比说明
- 📝 **详细注释**：所有代码都有中文注释和说明
- 🎓 **练习题**：巩固每个主题的知识点
- 🏗️ **实战项目**：最后一个综合项目整合所有知识
- 📊 **最佳实践**：总结Go开发的常见模式和陷阱

## 📝 学习进度追踪

在学习过程中，建议在这里记录你的进度：

- [ ] 阶段一：基础入门
  - [ ] 01-环境搭建
  - [ ] 02-基础语法
  - [ ] 03-控制流程
- [ ] 阶段二：核心概念
  - [ ] 04-函数
  - [ ] 05-数据结构
  - [ ] 06-面向对象
- [ ] 阶段三：进阶特性
  - [ ] 07-并发编程
  - [ ] 08-错误处理
  - [ ] 09-包管理
- [ ] 阶段四：实战应用
  - [ ] 10-文件IO
  - [ ] 11-Web开发
  - [ ] 12-数据库
  - [ ] 13-RESTful API
  - [ ] 14-综合项目

## 🤝 贡献

这是你的个人学习项目，随时添加你自己的笔记、示例和总结！

## 📄 许可证

本项目仅用于个人学习目的。

---

**开始你的Go语言学习之旅吧！** 💪

如有问题，请参考各个主题的文档，或查看 [summaries/resources.md](summaries/resources.md) 获取更多帮助资源。
