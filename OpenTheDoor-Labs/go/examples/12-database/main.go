package main

import (
	"fmt"
	"log"

	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

/*
数据库操作示例 (GORM)

本示例演示：
1. 连接 SQLite 数据库
2. 定义模型 (Model)
3. 自动迁移 (Auto Migration)
4. 增删改查 (CRUD) 操作
*/

// User 用户模型
// gorm.Model 是一个包含了 ID, CreatedAt, UpdatedAt, DeletedAt 的基础结构体
type User struct {
	gorm.Model
	Name string `gorm:"size:255;not null"` // 指定列大小和非空
	Age  int
	Role string `gorm:"default:'user'"` // 默认值
}

func main() {
	// 1. 连接数据库
	// GORM 支持 sqlite, mysql, postgres, sqlserver
	db, err := gorm.Open(sqlite.Open("test.db"), &gorm.Config{})
	if err != nil {
		log.Fatal("failed to connect database:", err)
	}
	fmt.Println("数据库连接成功！")

	// 2. 自动迁移 (Auto Migrate)
	// 自动创建或更新表结构，保持 schema 与 struct 同步
	err = db.AutoMigrate(&User{})
	if err != nil {
		log.Fatal("failed to migrate:", err)
	}
	fmt.Println("表结构迁移完成！")

	// 3. 创建 (Create)
	user := User{Name: "Alice", Age: 20, Role: "admin"}
	result := db.Create(&user) // 传递指针
	if result.Error != nil {
		log.Fatal(result.Error)
	}
	fmt.Printf("创建用户成功: ID=%d, Name=%s\n", user.ID, user.Name)

	// 批量创建
	users := []User{
		{Name: "Bob", Age: 25},
		{Name: "Charlie", Age: 30},
	}
	db.Create(&users)
	fmt.Printf("批量创建了 %d 个用户\n", len(users))

	// 4. 查询 (Read)
	var u User
	// 根据主键查询
	db.First(&u, user.ID)
	fmt.Printf("查询ID=%d: %v\n", user.ID, u)

	// 条件查询
	var admins []User
	db.Where("role = ?", "admin").Find(&admins)
	fmt.Printf("查询管理员数量: %d\n", len(admins))

	// 5. 更新 (Update)
	// 更新单个字段
	db.Model(&u).Update("Age", 22)
	fmt.Printf("更新后Age: %d\n", u.Age)

	// 更新多个字段
	db.Model(&u).Updates(User{Name: "Alice Updated", Role: "super_admin"})
	// 或者使用 map
	// db.Model(&u).Updates(map[string]interface{}{"name": "Alice", "age": 18})

	// 6. 删除 (Delete)
	// GORM 默认开启软删除 (因为 User 嵌入了 gorm.Model)
	db.Delete(&u)
	fmt.Println("用户已删除 (软删除)")

	// 验证删除
	var check User
	result = db.First(&check, u.ID)
	if result.Error == gorm.ErrRecordNotFound {
		fmt.Println("查询不到该用户 (符合预期)")
	}

	// 查询被软删除的记录
	db.Unscoped().First(&check, u.ID)
	fmt.Printf("使用 Unscoped 查找到已删除用户: %s\n", check.Name)

	// 物理删除
	// db.Unscoped().Delete(&u)

	// Java对比：
	// GORM 的 AutoMigrate 类似于 Hibernate 的 hbm2ddl.auto=update
	// GORM 的 CRUD 方法比手写 SQL 或 JDBC 简洁得多
	// 软删除机制在 GORM 中是开箱即用的
}
