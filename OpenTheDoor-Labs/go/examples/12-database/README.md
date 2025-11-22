# 数据库操作示例

学习如何在Go中使用数据库，重点介绍 **GORM** (Go Object Relational Mapping) 库。

## 🎯 学习目标

- 了解 `database/sql` 标准库
- 掌握 **GORM** 框架的基本使用 (CRUD)
- 理解模型定义与自动迁移 (Auto Migration)
- 学会简单的查询构建

## 📦 依赖安装

本示例使用 SQLite 作为演示数据库（无需安装额外软件）。

```bash
cd examples/12-database
go get -u gorm.io/gorm
go get -u gorm.io/driver/sqlite
```

## 🚀 运行示例

```bash
go run main.go
```
运行后会在当前目录生成 `test.db` 文件。

## 📖 核心概念

### 1. GORM vs JDBC/Hibernate
- **GORM** 是 Go 语言中最流行的 ORM 库，类似于 Java 的 **Hibernate** 或 **JPA**。
- 它支持 MySQL, PostgreSQL, SQLite, SQL Server 等。
- **特性**：全功能 ORM、关联 (Has One, Has Many)、钩子 (Hooks)、预加载、事务等。

### 2. 模型定义
使用结构体定义数据模型，通过 tag (`gorm:"..."`) 配置列属性。
```go
type Product struct {
    gorm.Model // 包含 ID, CreatedAt, UpdatedAt, DeletedAt
    Code  string
    Price uint
}
```

## 💡 与Java对比

| 特性 | Java (JPA/Hibernate) | Go (GORM) |
|------|----------------------|-----------|
| **实体定义** | `@Entity` class | `struct` |
| **主键** | `@Id` | `gorm:"primaryKey"` (或嵌入 `gorm.Model`) |
| **列名映射** | `@Column(name="...")` | `gorm:"column:..."` |
| **查询** | Repository / JPQL | `db.Where(...).Find(...)` |
| **迁移** | Flyway / Liquibase | `db.AutoMigrate(...)` |

## 🎓 练习任务

1. 修改模型，添加 `Email` 字段到 `User` 结构体。
2. 运行程序，观察 GORM 是否自动更新了数据库表结构。
3. 尝试编写一个查询，查找所有年龄大于 20 的用户。
4. 尝试使用事务 (`db.Transaction`) 插入两个用户，模拟其中一个失败回滚的情况。

## ➡️ 下一步

- [综合实战项目](../14-final-project/) - 将所有知识整合
