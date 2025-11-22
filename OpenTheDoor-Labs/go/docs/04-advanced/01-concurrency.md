# 并发编程

Go 语言在语言层面原生支持并发。

## 1. Goroutine

Goroutine 是由 Go 运行时管理的轻量级线程。

```go
go f(x, y, z)
```

会启动一个新的 Goroutine 并执行 `f(x, y, z)`。

## 2. Channel (信道)

信道是带有类型的管道，你可以通过它用信道操作符 `<-` 来发送或者接收值。

```go
ch <- v    // 将 v 发送至信道 ch。
v := <-ch  // 从 ch 接收值并赋予 v。
```

### 创建信道
```go
ch := make(chan int)
```

### 缓冲信道
```go
ch := make(chan int, 100)
```
仅当信道的缓冲区填满后，向其发送数据才会阻塞。当缓冲区为空时，接收方会阻塞。

### Range 和 Close
发送者可以通过 `close` 关闭一个信道来表示没有需要发送的值了。
接收者可以通过 `v, ok := <-ch` 测试信道是否被关闭。

循环 `for i := range c` 会不断从信道接收值，直到它被关闭。

## 3. Select

`select` 语句使一个 Goroutine 可以等待多个通信操作。
`select` 会阻塞到某个分支可以继续执行为止，这时就会执行该分支。当多个分支都准备好时，会随机选择一个执行。

```go
select {
case c <- x:
    x, y = y, x+y
case <-quit:
    fmt.Println("quit")
    return
default:
    // 当其他分支都不满足时执行
}
```

## 4. sync.Mutex

如果只是想保证每次只有一个 Goroutine 能访问一个共享的变量，可以使用互斥锁。

```go
var mu sync.Mutex

mu.Lock()
// 临界区
mu.Unlock()
```

---
[查看示例代码](../../examples/07-concurrency/)
