# RESTful API 示例

使用Gin框架构建完整的RESTful API服务。

## 🎯 学习目标

- 掌握Gin框架基础
- 理解RESTful API设计
- 学会路由和中间件
- 掌握JSON处理
- 了解错误处理

## 📦 安装依赖

```bash
cd examples/13-rest-api
go get -u github.com/gin-gonic/gin
```

## 🚀 运行示例

```bash
go run main.go
```

服务器将在 `http://localhost:8080` 启动

## 📖 API端点

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/v1/tasks | 获取所有任务 |
| GET | /api/v1/tasks/:id | 获取单个任务 |
| POST | /api/v1/tasks | 创建任务 |
| PUT | /api/v1/tasks/:id | 更新任务 |
| DELETE | /api/v1/tasks/:id | 删除任务 |
| GET | /health | 健康检查 |

## 🧪 测试API

### 使用curl

```bash
# 获取所有任务
curl http://localhost:8080/api/v1/tasks

# 获取单个任务
curl http://localhost:8080/api/v1/tasks/1

# 创建任务
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"新任务","description":"测试","completed":false}'

# 更新任务
curl -X PUT http://localhost:8080/api/v1/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"更新任务","description":"已修改","completed":true}'

# 删除任务
curl -X DELETE http://localhost:8080/api/v1/tasks/1
```

### 使用Postman

1. 导入Collection
2. 设置base URL：`http://localhost:8080`
3. 测试各个端点

## 💡 与Spring Boot对比

| 特性 | Gin | Spring Boot |
|------|-----|-------------|
| 性能 | 极快 | 较快 |
| 启动时间 | 毫秒级 | 秒级 |
| 代码量 | 少 | 多 |
| 注解 | 无 | 大量注解 |
| 依赖注入 | 无（手动） | 自动 |

## 🎓 下一步

- 集成数据库（GORM）
- 添加JWT认证
- 实现中间件
- 编写单元测试
- 容器化部署
