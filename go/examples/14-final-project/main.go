package main

import (
	"fmt"
	"log"
	"net/http"
	"strconv"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

/*
综合实战项目：简易博客系统 API

本文件包含了一个完整的微型后端应用，整合了：
1. Gin Web 框架
2. GORM 数据库操作
3. Goroutine 并发处理
4. 结构体与接口
5. 错误处理

为了教学方便，所有代码放在一个文件中，实际项目中应分包管理 (model, service, handler)。
*/

// ==========================================
// 1. Model 层 (数据模型)
// ==========================================

// User 用户
type User struct {
	gorm.Model
	Username string `gorm:"uniqueIndex;not null" json:"username"`
	Password string `json:"-"` // 密码不返回给前端
	Posts    []Post `json:"posts,omitempty"`
}

// Post 文章
type Post struct {
	gorm.Model
	Title    string    `json:"title" binding:"required"`
	Content  string    `json:"content" binding:"required"`
	UserID   uint      `json:"user_id"`
	User     User      `json:"-"` // 关联用户，但不返回详细信息
	Comments []Comment `json:"comments,omitempty"`
	Views    int       `json:"views"` // 浏览量
}

// Comment 评论
type Comment struct {
	gorm.Model
	Content string `json:"content" binding:"required"`
	PostID  uint   `json:"post_id"`
	UserID  uint   `json:"user_id"`
	User    User   `json:"user"` // 返回评论者信息
}

// 全局数据库实例
var db *gorm.DB

// ==========================================
// 2. Service 层 (业务逻辑)
// ==========================================

// InitDB 初始化数据库
func InitDB() {
	var err error
	db, err = gorm.Open(sqlite.Open("blog.db"), &gorm.Config{})
	if err != nil {
		log.Fatal("Failed to connect to database:", err)
	}

	// 自动迁移
	err = db.AutoMigrate(&User{}, &Post{}, &Comment{})
	if err != nil {
		log.Fatal("Failed to migrate database:", err)
	}
	fmt.Println("Database initialized successfully")
}

// IncrementViews 异步增加浏览量 (并发演示)
func IncrementViews(postID uint) {
	// 模拟耗时操作，不阻塞主线程
	go func() {
		// 可以在这里添加更复杂的统计逻辑
		time.Sleep(100 * time.Millisecond)

		// 使用互斥锁或原子操作在实际高并发场景中更安全
		// 这里利用数据库的原子更新
		db.Model(&Post{}).Where("id = ?", postID).UpdateColumn("views", gorm.Expr("views + ?", 1))
		fmt.Printf("Post %d views incremented (Async)\n", postID)
	}()
}

// ==========================================
// 3. Handler 层 (HTTP 处理)
// ==========================================

// RegisterHandler 用户注册
func RegisterHandler(c *gin.Context) {
	var user User
	if err := c.ShouldBindJSON(&user); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// 简单密码处理（实际应哈希）
	if result := db.Create(&user); result.Error != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to register user"})
		return
	}

	c.JSON(http.StatusCreated, gin.H{"message": "User registered successfully", "user": user})
}

// CreatePostHandler 发布文章
func CreatePostHandler(c *gin.Context) {
	var post Post
	if err := c.ShouldBindJSON(&post); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// 模拟从 Context 获取当前登录用户ID (实际应从 JWT 解析)
	// 这里硬编码为 ID=1 的用户
	post.UserID = 1

	if result := db.Create(&post); result.Error != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to create post"})
		return
	}

	c.JSON(http.StatusCreated, post)
}

// GetPostsHandler 获取文章列表
func GetPostsHandler(c *gin.Context) {
	var posts []Post
	// 预加载 User 信息，但不加载 Comments
	result := db.Preload("User").Find(&posts)
	if result.Error != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to fetch posts"})
		return
	}

	c.JSON(http.StatusOK, posts)
}

// GetPostDetailHandler 获取文章详情
func GetPostDetailHandler(c *gin.Context) {
	id := c.Param("id")
	var post Post

	// 预加载评论和评论者
	result := db.Preload("User").Preload("Comments").Preload("Comments.User").First(&post, id)
	if result.Error != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Post not found"})
		return
	}

	// 异步增加浏览量
	IncrementViews(post.ID)

	c.JSON(http.StatusOK, post)
}

// CreateCommentHandler 发表评论
func CreateCommentHandler(c *gin.Context) {
	postIDStr := c.Param("id")
	postID, _ := strconv.Atoi(postIDStr)

	var comment Comment
	if err := c.ShouldBindJSON(&comment); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	comment.PostID = uint(postID)
	comment.UserID = 1 // 模拟当前用户

	if result := db.Create(&comment); result.Error != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to create comment"})
		return
	}

	c.JSON(http.StatusCreated, comment)
}

// ==========================================
// 4. Main 入口
// ==========================================

func main() {
	// 初始化数据库
	InitDB()

	// 初始化 Gin
	r := gin.Default()

	// 路由组
	api := r.Group("/api")
	{
		api.POST("/register", RegisterHandler)

		api.GET("/posts", GetPostsHandler)
		api.POST("/posts", CreatePostHandler)
		api.GET("/posts/:id", GetPostDetailHandler)

		api.POST("/posts/:id/comments", CreateCommentHandler)
	}

	// 启动服务器
	fmt.Println("Server starting on :8080...")
	if err := r.Run(":8080"); err != nil {
		log.Fatal("Server failed to start:", err)
	}
}

// 辅助工具：并发安全的计数器（仅作示例，未使用）
type SafeCounter struct {
	v   map[string]int
	mux sync.Mutex
}

func (c *SafeCounter) Inc(key string) {
	c.mux.Lock()
	c.v[key]++
	c.mux.Unlock()
}
