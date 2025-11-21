package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
)

/*
RESTful API 示例

使用Gin框架构建一个简单的任务管理API

功能：
- GET    /tasks          获取所有任务
- GET    /tasks/:id      获取单个任务
- POST   /tasks          创建任务
- PUT    /tasks/:id      更新任务
- DELETE /tasks/:id      删除任务

与Java Spring Boot对比：
- Gin类似Spring MVC，但更轻量
- 路由定义更简洁
- JSON处理更直接
*/

// Task 任务模型
type Task struct {
	ID          int    `json:"id"`
	Title       string `json:"title" binding:"required"`
	Description string `json:"description"`
	Completed   bool   `json:"completed"`
}

// 内存存储（实际应用应使用数据库）
var tasks = []Task{
	{ID: 1, Title: "学习Go基础", Description: "变量、函数、接口", Completed: true},
	{ID: 2, Title: "学习Go并发", Description: "goroutine和channel", Completed: false},
	{ID: 3, Title: "构建REST API", Description: "使用Gin框架", Completed: false},
}

var nextID = 4

func main() {
	// 创建Gin路由器
	router := gin.Default()

	// 定义路由
	v1 := router.Group("/api/v1")
	{
		v1.GET("/tasks", getTasks)
		v1.GET("/tasks/:id", getTask)
		v1.POST("/tasks", createTask)
		v1.PUT("/tasks/:id", updateTask)
		v1.DELETE("/tasks/:id", deleteTask)
	}

	// 健康检查
	router.GET("/health", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"status": "ok"})
	})

	// 启动服务器
	fmt.Println("Server starting on :8080")
	if err := router.Run(":8080"); err != nil {
		log.Fatal(err)
	}

	// Java Spring Boot对比：
	// @RestController
	// @RequestMapping("/api/v1")
	// public class TaskController {
	//     @GetMapping("/tasks")
	//     public List<Task> getTasks() { ... }
	// }
	// Go的路由定义更集中、更直观
}

// GET /api/v1/tasks - 获取所有任务
func getTasks(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"success": true,
		"data":    tasks,
		"total":   len(tasks),
	})

	// Java对比：
	// return ResponseEntity.ok(tasks);
}

// GET /api/v1/tasks/:id - 获取单个任务
func getTask(c *gin.Context) {
	id := c.Param("id")

	for _, task := range tasks {
		if fmt.Sprint(task.ID) == id {
			c.JSON(http.StatusOK, gin.H{
				"success": true,
				"data":    task,
			})
			return
		}
	}

	c.JSON(http.StatusNotFound, gin.H{
		"success": false,
		"error":   "任务未找到",
	})
}

// POST /api/v1/tasks - 创建任务
func createTask(c *gin.Context) {
	var newTask Task

	// 绑定JSON到结构体（自动验证required字段）
	if err := c.ShouldBindJSON(&newTask); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"success": false,
			"error":   err.Error(),
		})
		return
	}

	// 分配ID
	newTask.ID = nextID
	nextID++

	// 添加到列表
	tasks = append(tasks, newTask)

	c.JSON(http.StatusCreated, gin.H{
		"success": true,
		"data":    newTask,
	})

	// Java对比：
	// @PostMapping("/tasks")
	// public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
	//     taskService.save(task);
	//     return ResponseEntity.status(HttpStatus.CREATED).body(task);
	// }
}

// PUT /api/v1/tasks/:id - 更新任务
func updateTask(c *gin.Context) {
	id := c.Param("id")
	var updatedTask Task

	if err := c.ShouldBindJSON(&updatedTask); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"success": false,
			"error":   err.Error(),
		})
		return
	}

	// 查找并更新
	for i, task := range tasks {
		if fmt.Sprint(task.ID) == id {
			updatedTask.ID = task.ID
			tasks[i] = updatedTask

			c.JSON(http.StatusOK, gin.H{
				"success": true,
				"data":    updatedTask,
			})
			return
		}
	}

	c.JSON(http.StatusNotFound, gin.H{
		"success": false,
		"error":   "任务未找到",
	})
}

// DELETE /api/v1/tasks/:id - 删除任务
func deleteTask(c *gin.Context) {
	id := c.Param("id")

	for i, task := range tasks {
		if fmt.Sprint(task.ID) == id {
			// 删除元素
			tasks = append(tasks[:i], tasks[i+1:]...)

			c.JSON(http.StatusOK, gin.H{
				"success": true,
				"message": "任务已删除",
			})
			return
		}
	}

	c.JSON(http.StatusNotFound, gin.H{
		"success": false,
		"error":   "任务未找到",
	})
}

/*
测试API的方法：

1. 使用curl:
   # 获取所有任务
   curl http://localhost:8080/api/v1/tasks

   # 创建任务
   curl -X POST http://localhost:8080/api/v1/tasks \
     -H "Content-Type: application/json" \
     -d '{"title":"新任务","description":"测试创建","completed":false}'

   # 更新任务
   curl -X PUT http://localhost:8080/api/v1/tasks/1 \
     -H "Content-Type: application/json" \
     -d '{"title":"更新的任务","description":"已修改","completed":true}'

   # 删除任务
   curl -X DELETE http://localhost:8080/api/v1/tasks/1

2. 使用Postman或Insomnia

3. 编写测试代码（见下节）

运行前需要安装依赖：
go get -u github.com/gin-gonic/gin
*/

// JSON处理示例
type Response struct {
	Success bool        `json:"success"`
	Data    interface{} `json:"data,omitempty"`
	Error   string      `json:"error,omitempty"`
}

func jsonExample() {
	// 编码JSON
	task := Task{ID: 1, Title: "示例", Completed: false}
	jsonData, _ := json.Marshal(task)
	fmt.Println(string(jsonData))

	// 解码JSON
	jsonStr := `{"id":2,"title":"解码示例","completed":true}`
	var decodedTask Task
	json.Unmarshal([]byte(jsonStr), &decodedTask)
	fmt.Printf("%+v\n", decodedTask)

	// Java对比：
	// ObjectMapper mapper = new ObjectMapper();
	// String json = mapper.writeValueAsString(task);
	// Task task = mapper.readValue(json, Task.class);
}

/*
API设计总结：

1. Gin框架特点：
   - 高性能（基于httprouter）
   - 路由分组
   - 中间件支持
   - JSON验证
   - 错误处理

2. 与Spring Boot对比：
   | 特性 | Gin | Spring Boot |
   |------|-----|-------------|
   | 性能 | 非常快 | 较快 |
   | 启动时间 | 毫秒级 | 秒级 |
   | 内存占用 | 低 | 中等偏高 |
   | 学习曲线 | 平缓 | 陡峭 |
   | 生态系统 | 精简 | 完善 |

3. 最佳实践：
   - 使用路由分组组织API
   - 统一响应格式
   - 适当的错误处理
   - 数据验证（binding tags）
   - 中间件处理日志、认证等

4. 下一步：
   - 集成数据库（GORM）
   - 添加JWT认证
   - 实现分页
   - 添加单元测试
   - 部署到生产环境
*/
