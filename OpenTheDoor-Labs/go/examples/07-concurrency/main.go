package main

import "fmt"

/*
并发编程示例

Go的并发特性：
1. Goroutine - 轻量级线程
2. Channel - 通道（goroutine间通信）
3. Select - 多路复用
4. 并发模式

"不要通过共享内存来通信，而应该通过通信来共享内存" - Go的并发哲学
*/

func main() {
	fmt.Println("=== 1. Goroutine 基础 ===")
	demonstrateGoroutine()

	fmt.Println("\n=== 2. Channel 通道 ===")
	demonstrateChannel()

	fmt.Println("\n=== 3. Channel 缓冲 ===")
	demonstrateBufferedChannel()

	fmt.Println("\n=== 4. Select 多路复用 ===")
	demonstrateSelect()

	fmt.Println("\n=== 5. 并发模式 ===")
	demonstratePatterns()
}

// 1. Goroutine 基础
func sayHello(name string) {
	fmt.Printf("Hello, %s!\n", name)
}

func demonstrateGoroutine() {
	// 普通函数调用
	sayHello("同步")

	// goroutine（并发执行）
	go sayHello("异步1")
	go sayHello("异步2")
	go sayHello("异步3")

	// 等待一下（实际应用用WaitGroup或Channel）
	// time.Sleep(100 * time.Millisecond)

	// 匿名goroutine
	go func(msg string) {
		fmt.Println("匿名goroutine:", msg)
	}("Hello")

	// Java对比：
	// new Thread(() -> sayHello("异步")).start();
	// 或 ExecutorService.submit(() -> sayHello("异步"));
	// Goroutine更轻量，创建成千上万个goroutine很常见
}

// 2. Channel 通道
func demonstrateChannel() {
	// 创建channel
	messages := make(chan string)

	// 在goroutine中发送数据
	go func() {
		messages <- "ping" // 发送
	}()

	// 接收数据
	msg := <-messages // 接收
	fmt.Println("收到:", msg)

	// 带参数的goroutine
	ch := make(chan int)
	go sum([]int{1, 2, 3, 4, 5}, ch)
	result := <-ch
	fmt.Println("sum result:", result)

	// Java对比：
	// BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	// new Thread(() -> queue.put("ping")).start();
	// String msg = queue.take();
	// Go的channel是语言级特性，更简洁
}

func sum(numbers []int, result chan int) {
	total := 0
	for _, num := range numbers {
		total += num
	}
	result <- total // 发送结果
}

// 3. 缓冲通道
func demonstrateBufferedChannel() {
	// 无缓冲channel：发送者阻塞直到接收者准备好
	// 缓冲channel：有空间时不阻塞

	ch := make(chan string, 2) // 缓冲大小为2

	ch <- "first"
	ch <- "second"
	// ch <- "third"  // 会阻塞，因为缓冲区满了

	fmt.Println(<-ch)
	fmt.Println(<-ch)

	// 关闭channel
	jobs := make(chan int, 5)
	done := make(chan bool)

	go func() {
		for {
			job, more := <-jobs
			if more {
				fmt.Printf("处理任务 %d\n", job)
			} else {
				fmt.Println("所有任务完成")
				done <- true
				return
			}
		}
	}()

	for i := 1; i <= 3; i++ {
		jobs <- i
	}
	close(jobs) // 关闭channel表示不再发送
	<-done
}

// 4. Select 多路复用
func demonstrateSelect() {
	c1 := make(chan string)
	c2 := make(chan string)

	go func() {
		// time.Sleep(1 * time.Second)
		c1 <- "one"
	}()

	go func() {
		// time.Sleep(2 * time.Second)
		c2 <- "two"
	}()

	// select等待多个channel操作
	for i := 0; i < 2; i++ {
		select {
		case msg1 := <-c1:
			fmt.Println("收到", msg1)
		case msg2 := <-c2:
			fmt.Println("收到", msg2)
		}
	}

	// select with default（非阻塞）
	messages := make(chan string)
	select {
	case msg := <-messages:
		fmt.Println("收到", msg)
	default:
		fmt.Println("没有消息")
	}

	// Java对比：
	// Java的Selector用于NIO，Go的select更通用
	// Java Future.anyOf(futures)处理多个Future
}

// 5. 并发模式
func demonstratePatterns() {
	// 模式1: Worker Pool
	fmt.Println("Worker Pool模式:")
	workerPool()

	// 模式2: Pipeline
	fmt.Println("\nPipeline模式:")
	pipeline()
}

// Worker Pool模式
func workerPool() {
	jobs := make(chan int, 100)
	results := make(chan int, 100)

	// 启动3个worker
	for w := 1; w <= 3; w++ {
		go worker(w, jobs, results)
	}

	// 发送5个任务
	for j := 1; j <= 5; j++ {
		jobs <- j
	}
	close(jobs)

	// 收集结果
	for a := 1; a <= 5; a++ {
		<-results
	}
}

func worker(id int, jobs <-chan int, results chan<- int) {
	for j := range jobs {
		fmt.Printf("worker %d 处理任务 %d\n", id, j)
		results <- j * 2
	}
}

// Pipeline模式
func pipeline() {
	// 阶段1: 生成数字
	gen := func(nums ...int) <-chan int {
		out := make(chan int)
		go func() {
			for _, n := range nums {
				out <- n
			}
			close(out)
		}()
		return out
	}

	// 阶段2: 平方
	sq := func(in <-chan int) <-chan int {
		out := make(chan int)
		go func() {
			for n := range in {
				out <- n * n
			}
			close(out)
		}()
		return out
	}

	// 组装pipeline
	c := gen(2, 3, 4)
	out := sq(c)

	// 消费结果
	for n := range out {
		fmt.Println(n)
	}
}

/*
并发总结：

1. Goroutine:
   - go关键字启动
   - 非常轻量（几KB栈空间）
   - 由Go运行时调度

2. Channel:
   - 类型安全的队列
   - 用于goroutine间通信
   - 可以是无缓冲或缓冲的
   - 可以关闭

3. Select:
   - 多路复用channel操作
   - 类似switch但用于channel
   - 支持default（非阻塞）

4. 并发模式:
   - Worker Pool: 任务分发
   - Pipeline: 数据流处理
   - Fan-out/Fan-in: 并行处理

与Java的核心差异：
1. Goroutine vs Thread:
   - Goroutine更轻量（可创建百万级别）
   - Thread较重（通常几千个就是限制）

2. Channel vs Queue:
   - Channel是语言特性
   - Queue需要导入库

3. Go的并发模型更简单：
   - CSP模型（通信顺序进程）
   - "通过通信来共享内存"
   - 避免显式锁

4. Java的并发模型：
   - 共享内存 + 锁（synchronized, Lock）
   - 需要小心处理竞态条件
   - CompletableFuture, ExecutorService等

Go的并发是其核心优势，简单而强大！
*/
