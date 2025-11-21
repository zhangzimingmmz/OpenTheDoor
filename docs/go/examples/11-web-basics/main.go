package main

import (
	"fmt"
	"log"
	"net/http"
)

/*
Web开发基础示例 (net/http)

本示例演示：
1. 启动 HTTP 服务器
2. 注册路由处理函数
3. 处理 GET/POST 请求
4. 读取查询参数和表单数据
*/

func main() {
	// 1. 注册路由
	// HandleFunc 将 URL 路径映射到处理函数
	http.HandleFunc("/", homeHandler)
	http.HandleFunc("/hello", helloHandler)
	http.HandleFunc("/login", loginHandler)

	// 2. 启动服务器
	fmt.Println("Server starting on :8080...")
	// ListenAndServe 会阻塞，直到服务器关闭
	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		log.Fatal("Server failed:", err)
	}
}

// 首页处理器
func homeHandler(w http.ResponseWriter, r *http.Request) {
	// 检查路径，防止 "/" 匹配所有未定义的路径
	if r.URL.Path != "/" {
		http.NotFound(w, r)
		return
	}
	fmt.Fprintf(w, "Welcome to Go Web Server!")
}

// Hello 处理器 (处理 GET 参数)
func helloHandler(w http.ResponseWriter, r *http.Request) {
	// 获取查询参数 ?name=xxx
	query := r.URL.Query()
	name := query.Get("name")
	if name == "" {
		name = "Guest"
	}

	fmt.Fprintf(w, "Hello, %s!", name)
}

// Login 处理器 (处理 POST 表单)
func loginHandler(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case "GET":
		// 返回简单的 HTML 表单
		html := `
		<html>
		<body>
			<form method="POST" action="/login">
				Username: <input type="text" name="username"><br>
				Password: <input type="password" name="password"><br>
				<input type="submit" value="Login">
			</form>
		</body>
		</html>`
		w.Write([]byte(html))

	case "POST":
		// 解析表单数据
		if err := r.ParseForm(); err != nil {
			http.Error(w, "ParseForm() err: "+err.Error(), http.StatusBadRequest)
			return
		}

		username := r.FormValue("username")
		password := r.FormValue("password")

		fmt.Fprintf(w, "Login Received: User=%s, Password=%s\n", username, password)

	default:
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
	}
}
