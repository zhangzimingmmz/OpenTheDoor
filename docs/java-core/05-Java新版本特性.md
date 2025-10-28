# Java 新版本特性 (Java New Version Features)

> 从Java 8到Java 21的重要新特性，紧跟Java发展趋势

## 目录
- [Java 8 (2014) - LTS](#java-8-2014---lts)
- [Java 9 (2017)](#java-9-2017)
- [Java 10 (2018)](#java-10-2018)
- [Java 11 (2018) - LTS](#java-11-2018---lts)
- [Java 12-16 (2019-2021)](#java-12-16-2019-2021)
- [Java 17 (2021) - LTS](#java-17-2021---lts)
- [Java 18-20 (2022-2023)](#java-18-20-2022-2023)
- [Java 21 (2023) - LTS](#java-21-2023---lts)
- [版本选择建议](#版本选择建议)
- [面试高频问题](#面试高频问题)

---

## Java 8 (2014) - LTS

**⭐⭐⭐⭐⭐ 革命性更新，最重要的版本之一**

### 1. Lambda表达式 (Lambda Expressions)

```java
// 传统方式
List<String> list = Arrays.asList("A", "B", "C");
Collections.sort(list, new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
});

// Lambda方式
Collections.sort(list, (s1, s2) -> s1.compareTo(s2));
```

### 2. Stream API

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// 传统方式：求偶数的平方和
int sum = 0;
for (Integer num : numbers) {
    if (num % 2 == 0) {
        sum += num * num;
    }
}

// Stream方式
int streamSum = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .reduce(0, Integer::sum);
```

### 3. Optional类

```java
// 传统null检查
public String getUserName(User user) {
    if (user != null) {
        return user.getName();
    }
    return "Unknown";
}

// Optional方式
public String getUserName(Optional<User> user) {
    return user.map(User::getName).orElse("Unknown");
}
```

### 4. 接口默认方法 (Default Methods)

```java
public interface Vehicle {
    
    // 抽象方法
    void start();
    
    // 默认方法（Java 8+）
    default void stop() {
        System.out.println("车辆停止");
    }
    
    // 静态方法（Java 8+）
    static void checkEngine() {
        System.out.println("检查引擎");
    }
}

class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("汽车启动");
    }
    
    // 可以选择重写默认方法
    @Override
    public void stop() {
        System.out.println("汽车停止");
    }
}
```

### 5. 新的日期时间API (java.time)

```java
// 旧API的问题：线程不安全，设计混乱
Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

// 新API：不可变、线程安全
LocalDate today = LocalDate.now();
LocalDate birthday = LocalDate.of(2000, 1, 1);
LocalDateTime now = LocalDateTime.now();
ZonedDateTime zonedNow = ZonedDateTime.now();

// 日期计算
LocalDate nextWeek = today.plusWeeks(1);
LocalDate lastMonth = today.minusMonths(1);

// 格式化
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String formatted = now.format(formatter);

// 解析
LocalDateTime parsed = LocalDateTime.parse("2025-10-28 10:30:00", formatter);

// 计算时间差
long daysBetween = ChronoUnit.DAYS.between(birthday, today);
System.out.println("距离生日: " + daysBetween + "天");
```

### 6. 方法引用 (Method References)

```java
// Lambda表达式
list.forEach(s -> System.out.println(s));

// 方法引用
list.forEach(System.out::println);
```

### 7. 重复注解 (Repeating Annotations)

```java
@Repeatable(Schedules.class)
@interface Schedule {
    String day();
}

@interface Schedules {
    Schedule[] value();
}

@Schedule(day = "Monday")
@Schedule(day = "Wednesday")
public class Task {
}
```

---

## Java 9 (2017)

### 1. 模块化系统 (Project Jigsaw)

```java
// module-info.java
module com.example.myapp {
    requires java.sql;
    requires com.example.utils;
    
    exports com.example.myapp.api;
}
```

### 2. JShell (交互式编程工具)

```bash
$ jshell
jshell> int x = 10
x ==> 10

jshell> System.out.println(x * 2)
20
```

### 3. 集合工厂方法

```java
// Java 8及之前
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");
List<String> immutableList = Collections.unmodifiableList(list);

// Java 9+
List<String> list = List.of("A", "B", "C"); // 不可变
Set<String> set = Set.of("A", "B", "C");
Map<String, Integer> map = Map.of("A", 1, "B", 2, "C", 3);

// Map.ofEntries()用于更多键值对
Map<String, Integer> bigMap = Map.ofEntries(
    Map.entry("A", 1),
    Map.entry("B", 2),
    Map.entry("C", 3)
);
```

### 4. 私有接口方法

```java
public interface MyInterface {
    
    default void method1() {
        commonLogic();
    }
    
    default void method2() {
        commonLogic();
    }
    
    // Java 9+: 私有方法
    private void commonLogic() {
        System.out.println("通用逻辑");
    }
}
```

### 5. Stream API增强

```java
// takeWhile() - 从头取元素直到条件为false
Stream.of(1, 2, 3, 4, 5, 6)
    .takeWhile(n -> n < 4)
    .forEach(System.out::println); // 1, 2, 3

// dropWhile() - 从头丢弃元素直到条件为false
Stream.of(1, 2, 3, 4, 5, 6)
    .dropWhile(n -> n < 4)
    .forEach(System.out::println); // 4, 5, 6

// ofNullable() - 创建可能为null的Stream
Stream<String> stream = Stream.ofNullable(null);
```

---

## Java 10 (2018)

### 1. 局部变量类型推断 (var)

```java
// Java 9及之前
Map<String, List<String>> map = new HashMap<>();
List<String> list = new ArrayList<>();

// Java 10+: var关键字
var map = new HashMap<String, List<String>>();
var list = new ArrayList<String>();
var str = "Hello"; // String类型
var num = 10; // int类型

// 注意：var不能用于
// - 方法参数
// - 方法返回类型
// - 成员变量
// - 初始值为null的变量

// ✅ 适用场景
for (var item : list) {
    System.out.println(item);
}

try (var input = new FileInputStream("file.txt")) {
    // ...
}

// ❌ 不适用场景
// var x = null; // 编译错误
// var[] array = {1, 2, 3}; // 编译错误
```

### 2. 不可变集合的copyOf()方法

```java
List<String> original = new ArrayList<>();
original.add("A");
original.add("B");

// 创建不可变副本
List<String> copy = List.copyOf(original);
```

---

## Java 11 (2018) - LTS

**⭐⭐⭐⭐ 长期支持版本，广泛使用**

### 1. 字符串增强

```java
// isBlank() - 判断是否为空或只包含空白字符
"   ".isBlank(); // true
"Hello".isBlank(); // false

// lines() - 分割为行的Stream
String multiline = "Line1\nLine2\nLine3";
multiline.lines().forEach(System.out::println);

// strip() / stripLeading() / stripTrailing() - 去除空白
"  Hello  ".strip(); // "Hello"
"  Hello  ".stripLeading(); // "Hello  "
"  Hello  ".stripTrailing(); // "  Hello"

// repeat() - 重复字符串
"Java ".repeat(3); // "Java Java Java "
```

### 2. 文件读写增强

```java
// 读取文件为字符串
String content = Files.readString(Path.of("file.txt"));

// 写入字符串到文件
Files.writeString(Path.of("file.txt"), "Hello World");
```

### 3. HTTP Client API (标准)

```java
// Java 11之前需要使用第三方库（如Apache HttpClient）
// Java 11+内置HTTP Client

HttpClient client = HttpClient.newHttpClient();

HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/data"))
    .GET()
    .build();

// 同步请求
HttpResponse<String> response = client.send(request, 
    HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());

// 异步请求
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out::println);
```

### 4. Lambda参数使用var

```java
// Java 11之前
BiFunction<Integer, Integer, Integer> add = 
    (Integer a, Integer b) -> a + b;

// Java 11+: Lambda参数可以使用var
BiFunction<Integer, Integer, Integer> add2 = 
    (var a, var b) -> a + b;

// 使用场景：添加注解
BiFunction<Integer, Integer, Integer> add3 = 
    (@NonNull var a, @NonNull var b) -> a + b;
```

---

## Java 12-16 (2019-2021)

### Java 12: Switch表达式 (预览)

```java
// 传统switch语句
String result;
switch (day) {
    case MONDAY:
    case FRIDAY:
        result = "工作日";
        break;
    case SATURDAY:
    case SUNDAY:
        result = "周末";
        break;
    default:
        result = "未知";
}

// Java 12+: Switch表达式
String result = switch (day) {
    case MONDAY, FRIDAY -> "工作日";
    case SATURDAY, SUNDAY -> "周末";
    default -> "未知";
};
```

### Java 13: 文本块 (Text Blocks) (预览)

```java
// 传统多行字符串
String html = "<html>\n" +
              "  <body>\n" +
              "    <p>Hello</p>\n" +
              "  </body>\n" +
              "</html>";

// Java 13+: 文本块
String html = """
    <html>
      <body>
        <p>Hello</p>
      </body>
    </html>
    """;

// JSON示例
String json = """
    {
      "name": "张三",
      "age": 25,
      "city": "北京"
    }
    """;
```

### Java 14: Switch表达式 (正式)、Records (预览)

```java
// Switch表达式正式版
var result = switch (day) {
    case MONDAY, FRIDAY -> {
        System.out.println("工作日");
        yield "需要工作"; // yield返回值
    }
    case SATURDAY, SUNDAY -> "周末";
    default -> "未知";
};

// Records (预览)
// 传统POJO
class Person {
    private final String name;
    private final int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String name() { return name; }
    public int age() { return age; }
    
    @Override
    public boolean equals(Object o) { /* ... */ }
    @Override
    public int hashCode() { /* ... */ }
    @Override
    public String toString() { /* ... */ }
}

// Java 14+: Record
record Person(String name, int age) {}
// 自动生成：构造器、getter、equals、hashCode、toString
```

### Java 15: 文本块 (正式)、Sealed Classes (预览)

```java
// Sealed Classes - 限制继承
public sealed class Shape
    permits Circle, Rectangle, Triangle {
}

final class Circle extends Shape { }
final class Rectangle extends Shape { }
final class Triangle extends Shape { }

// 现在不能再继承Shape了
// class Square extends Shape { } // 编译错误！
```

### Java 16: Records (正式)、Pattern Matching for instanceof (正式)

```java
// 传统instanceof
if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.length());
}

// Java 16+: Pattern Matching
if (obj instanceof String str) {
    System.out.println(str.length()); // 直接使用str
}
```

---

## Java 17 (2021) - LTS

**⭐⭐⭐⭐ 长期支持版本，推荐生产使用**

### 1. Sealed Classes (正式)

```java
// 密封类：限制哪些类可以继承
public sealed class Vehicle
    permits Car, Truck, Motorcycle {
    
    private String brand;
    
    public Vehicle(String brand) {
        this.brand = brand;
    }
}

// 子类必须是final、sealed或non-sealed
final class Car extends Vehicle {
    public Car(String brand) {
        super(brand);
    }
}

sealed class Truck extends Vehicle
    permits PickupTruck {
    public Truck(String brand) {
        super(brand);
    }
}

non-sealed class Motorcycle extends Vehicle {
    public Motorcycle(String brand) {
        super(brand);
    }
}

// Motorcycle是non-sealed，可以被任意继承
class SportBike extends Motorcycle {
    public SportBike(String brand) {
        super(brand);
    }
}
```

### 2. Pattern Matching for switch (预览)

```java
// 传统方式
static String formatter(Object obj) {
    String formatted = "unknown";
    if (obj instanceof Integer i) {
        formatted = String.format("int %d", i);
    } else if (obj instanceof Long l) {
        formatted = String.format("long %d", l);
    } else if (obj instanceof Double d) {
        formatted = String.format("double %f", d);
    } else if (obj instanceof String s) {
        formatted = String.format("String %s", s);
    }
    return formatted;
}

// Java 17+: Pattern Matching for switch
static String formatter(Object obj) {
    return switch (obj) {
        case Integer i -> String.format("int %d", i);
        case Long l    -> String.format("long %d", l);
        case Double d  -> String.format("double %f", d);
        case String s  -> String.format("String %s", s);
        default        -> obj.toString();
    };
}
```

### 3. 增强的伪随机数生成器

```java
// Java 17+: 新的随机数API
RandomGenerator generator = RandomGenerator.of("L64X128MixRandom");
int randomInt = generator.nextInt();
double randomDouble = generator.nextDouble();
```

---

## Java 18-20 (2022-2023)

### Java 18: UTF-8默认字符集、简单Web服务器

```java
// 简单HTTP服务器
jwebserver -p 8080
```

### Java 19: Virtual Threads (预览)、Record Patterns (预览)

```java
// Virtual Threads (预览)
Thread.startVirtualThread(() -> {
    System.out.println("虚拟线程运行中");
});

// Record Patterns (预览)
record Point(int x, int y) {}

static void printPoint(Object obj) {
    if (obj instanceof Point(int x, int y)) {
        System.out.println("x = " + x + ", y = " + y);
    }
}
```

### Java 20: Record Patterns (二次预览)、Pattern Matching for switch (四次预览)

---

## Java 21 (2023) - LTS

**⭐⭐⭐⭐⭐ 最新LTS版本，重大更新**

### 1. Virtual Threads (正式) - 虚拟线程

```java
/**
 * 虚拟线程：轻量级线程，可以创建数百万个
 * Virtual Threads: Lightweight threads
 */

// 传统线程
Thread thread = new Thread(() -> {
    System.out.println("传统线程");
});
thread.start();

// Java 21+: 虚拟线程
Thread virtualThread = Thread.startVirtualThread(() -> {
    System.out.println("虚拟线程");
});

// 使用Executor
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 1_000_000; i++) {
        executor.submit(() -> {
            Thread.sleep(Duration.ofSeconds(1));
            return "Done";
        });
    }
} // executor自动等待所有任务完成

// 性能对比
// 传统线程：创建10,000个线程会消耗大量内存
// 虚拟线程：可以轻松创建1,000,000个虚拟线程
```

### 2. Sequenced Collections - 有序集合

```java
// 新增的有序集合接口
interface SequencedCollection<E> extends Collection<E> {
    SequencedCollection<E> reversed(); // 反转视图
    void addFirst(E e); // 添加到开头
    void addLast(E e);  // 添加到末尾
    E getFirst();       // 获取第一个元素
    E getLast();        // 获取最后一个元素
    E removeFirst();    // 移除第一个元素
    E removeLast();     // 移除最后一个元素
}

// 使用示例
List<String> list = new ArrayList<>();
list.addFirst("A"); // 添加到开头
list.addLast("C");  // 添加到末尾
String first = list.getFirst(); // 获取第一个
String last = list.getLast();   // 获取最后一个

// 反转视图
List<String> reversed = list.reversed();
```

### 3. Record Patterns (正式)

```java
record Point(int x, int y) {}

// Record Pattern Matching
static void printPoint(Object obj) {
    if (obj instanceof Point(int x, int y)) {
        System.out.println("Point: x=" + x + ", y=" + y);
    }
}

// 嵌套Record Patterns
record Rectangle(Point topLeft, Point bottomRight) {}

static void printRectangle(Object obj) {
    if (obj instanceof Rectangle(Point(int x1, int y1), Point(int x2, int y2))) {
        System.out.println("Rectangle: (" + x1 + "," + y1 + 
                         ") to (" + x2 + "," + y2 + ")");
    }
}
```

### 4. Pattern Matching for switch (正式)

```java
// 完整的Pattern Matching for switch
static String testObject(Object obj) {
    return switch (obj) {
        case null -> "Null";
        case Integer i when i > 0 -> "Positive Integer: " + i;
        case Integer i when i < 0 -> "Negative Integer: " + i;
        case Integer i -> "Zero";
        case Long l -> "Long: " + l;
        case Double d -> "Double: " + d;
        case String s when s.length() > 5 -> "Long String: " + s;
        case String s -> "Short String: " + s;
        case Point(int x, int y) -> "Point: " + x + "," + y;
        default -> "Unknown type";
    };
}
```

### 5. String Templates (预览)

```java
// 传统字符串拼接
String name = "张三";
int age = 25;
String message = "姓名: " + name + ", 年龄: " + age;

// Java 21+: String Templates (预览)
// String message = STR."姓名: \{name}, 年龄: \{age}";
```

---

## 版本选择建议

### 生产环境推荐

| 版本 | 推荐度 | 说明 |
|------|-------|------|
| **Java 8** | ⭐⭐⭐ | 稳定、生态完善，但即将EOL |
| **Java 11** | ⭐⭐⭐⭐ | LTS，稳定可靠，广泛使用 |
| **Java 17** | ⭐⭐⭐⭐⭐ | LTS，推荐新项目使用 |
| **Java 21** | ⭐⭐⭐⭐ | 最新LTS，适合新项目，需测试 |

### 学习建议

1. **基础学习**：从Java 8开始，掌握Lambda、Stream、Optional
2. **进阶学习**：学习Java 11~17的新特性，了解Records、Sealed Classes
3. **前沿技术**：关注Java 21的Virtual Threads、Pattern Matching
4. **实战应用**：在项目中逐步应用新特性

---

## 面试高频问题

### Q1: Java 8有哪些重要特性？⭐⭐⭐⭐⭐

**答案：**
1. Lambda表达式
2. Stream API
3. Optional类
4. 接口默认方法
5. 新的日期时间API (java.time)
6. 方法引用
7. CompletableFuture
8. Nashorn JavaScript引擎

### Q2: Java 11相比Java 8有哪些改进？⭐⭐⭐⭐

**答案：**
1. HTTP Client API (标准化)
2. 字符串增强 (isBlank, lines, strip, repeat)
3. 文件读写增强
4. Lambda参数支持var
5. 移除JavaEE和CORBA模块
6. ZGC垃圾收集器 (实验性)
7. Epsilon垃圾收集器

### Q3: Record和普通类有什么区别？⭐⭐⭐⭐

**答案：**

Record是不可变数据类，自动生成：
- 私有final字段
- 构造器
- Getter方法 (不带get前缀)
- equals()、hashCode()、toString()

**区别：**
- Record不能继承其他类 (但可以实现接口)
- Record的字段都是final
- Record不能声明实例变量
- Record更简洁，减少样板代码

### Q4: Sealed Classes的作用是什么？⭐⭐⭐⭐

**答案：**

Sealed Classes限制哪些类可以继承它，提供更严格的继承控制。

**好处：**
1. 明确类层次结构
2. 增强类型安全
3. 编译器可以做完整性检查
4. 配合Pattern Matching使用更强大

### Q5: 虚拟线程有什么优势？⭐⭐⭐⭐⭐

**答案：**

**优势：**
1. **轻量级**：可以创建数百万个虚拟线程
2. **低开销**：内存占用小，上下文切换快
3. **简化并发编程**：可以用同步代码风格编写异步逻辑
4. **提高吞吐量**：特别适合I/O密集型应用

**对比：**
- 传统线程：对应OS线程，创建/销毁开销大
- 虚拟线程：JVM管理，由Carrier线程调度

### Q6: 应该选择哪个Java版本？⭐⭐⭐⭐

**答案：**

**生产环境：**
- 保守：Java 11 (LTS，稳定)
- 推荐：Java 17 (LTS，现代化)
- 激进：Java 21 (最新LTS)

**学习：**
- 必学：Java 8 (Lambda、Stream)
- 推荐：Java 11-17 (Records、Sealed Classes)
- 了解：Java 21 (Virtual Threads)

**考虑因素：**
1. 项目需求
2. 团队技术栈
3. 第三方库兼容性
4. 长期维护成本

---

## 总结 (Summary)

Java版本演进路线：

- **Java 8 (2014)**：Lambda、Stream、Optional - 革命性更新
- **Java 9 (2017)**：模块化、JShell
- **Java 11 (2018)**：LTS，HTTP Client、字符串增强
- **Java 17 (2021)**：LTS，Sealed Classes、Pattern Matching
- **Java 21 (2023)**：LTS，Virtual Threads、Sequenced Collections

**学习建议：**

1. **掌握Java 8**：Lambda、Stream、Optional是基础
2. **了解Java 11-17**：Records、Sealed Classes、Pattern Matching
3. **关注Java 21**：Virtual Threads是未来趋势
4. **实践应用**：在项目中逐步应用新特性

---

**上一篇：** [← 04 - 函数式编程](./04-函数式编程.md)

**返回目录：** [Java 语言基础导航 ←](./README.md)

