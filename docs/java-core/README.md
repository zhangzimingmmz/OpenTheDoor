# Java 语言基础学习文档体系

> 一套系统完整的Java语言基础学习资料，从语法基础到新版本特性，涵盖面向对象、函数式编程、反射等核心知识。

## 📚 文档目录

### 基础篇

#### [01 - Java语法基础](./01-Java语法基础.md)
- 数据类型（基本类型、引用类型）
- 运算符（算术、逻辑、位运算）
- 控制流（if/switch、循环、跳转）
- 异常处理（try-catch、自定义异常、异常链）
- 字符串处理
- 数组与多维数组

#### [02 - 面向对象编程](./02-面向对象编程.md)
- 类与对象（定义、构造器、成员变量/方法）
- 封装（访问修饰符、Getter/Setter）
- 继承（extends、super、方法重写）
- 多态（向上转型、向下转型、动态绑定）
- 接口与抽象类（interface、abstract）
- 内部类（成员内部类、局部内部类、匿名内部类、静态内部类）

### 进阶篇

#### [03 - Java高级特性](./03-Java高级特性.md)
- 泛型（泛型类、泛型方法、通配符、类型擦除）
- 注解（内置注解、自定义注解、注解处理器）
- 反射（Class对象、反射调用、反射创建对象）
- 动态代理（JDK动态代理、CGLIB）
- 枚举（enum、枚举方法）
- SPI机制（ServiceLoader）

#### [04 - 函数式编程](./04-函数式编程.md)
- Lambda表达式（语法、函数式接口）
- 方法引用（静态方法、实例方法、构造器引用）
- Stream API（中间操作、终止操作）
- Optional（避免NPE、链式调用）
- 函数式接口（Function、Predicate、Consumer、Supplier）
- 实战案例（数据处理、集合转换）

### 新特性篇

#### [05 - Java新版本特性](./05-Java新版本特性.md)
- **Java 8** - Lambda、Stream、Optional、接口默认方法、日期时间API
- **Java 9** - 模块化、JShell、集合工厂方法
- **Java 10** - var关键字、G1垃圾收集器优化
- **Java 11** - HTTP Client、字符串增强、ZGC
- **Java 14** - Switch表达式、Records预览
- **Java 17** - Sealed Classes、Pattern Matching
- **Java 21** - Virtual Threads、Record Patterns、Sequenced Collections

#### [06 - Java集合框架](./06-Java集合框架.md)
- **List系列** - ArrayList、LinkedList、CopyOnWriteArrayList 源码分析
- **Set系列** - HashSet、TreeSet、LinkedHashSet
- **Map系列** - HashMap、ConcurrentHashMap、TreeMap、LinkedHashMap 深入解析
- **Queue系列** - PriorityQueue、ArrayDeque、BlockingQueue
- **集合工具** - Collections、Arrays 常用方法
- **性能对比** - 各集合底层实现与使用场景

---

## 🚀 学习路径

### 初级阶段（1-2周）
1. 阅读 [Java语法基础](./01-Java语法基础.md)，掌握基本语法
2. 阅读 [面向对象编程](./02-面向对象编程.md)，理解OOP思想
3. 实践：编写简单的类，练习继承和多态

### 中级阶段（2-3周）
4. 学习 [Java高级特性](./03-Java高级特性.md)，掌握泛型、反射、注解
5. 学习 [函数式编程](./04-函数式编程.md)，掌握Stream API
6. 实践：使用泛型重构代码、编写注解处理器、实践Stream数据处理

### 高级阶段（2-3周）
7. 学习 [Java集合框架](./06-Java集合框架.md)，掌握HashMap、ConcurrentHashMap原理
8. 学习 [Java新版本特性](./05-Java新版本特性.md)
9. 实践：手写HashMap、实现LRU缓存、使用新特性改进代码

---

## 💡 快速查找

### 按需求查找

| 需求 | 推荐章节 |
|------|---------|
| 了解基本语法 | [01-语法基础](./01-Java语法基础.md) |
| 学习OOP | [02-面向对象](./02-面向对象编程.md) |
| 掌握泛型 | [03-高级特性](./03-Java高级特性.md#泛型) |
| 学习反射 | [03-高级特性](./03-Java高级特性.md#反射) |
| 学习Lambda | [04-函数式编程](./04-函数式编程.md) |
| Stream操作 | [04-函数式编程](./04-函数式编程.md#stream-api) |
| 学习集合框架 | [06-集合框架](./06-Java集合框架.md) |
| 新版本特性 | [05-新版本特性](./05-Java新版本特性.md) |

### 按知识点查找

| 知识点 | 相关章节 |
|--------|---------|
| **异常处理** | [01](./01-Java语法基础.md) |
| **接口与抽象类** | [02](./02-面向对象编程.md) |
| **泛型** | [03](./03-Java高级特性.md) |
| **注解** | [03](./03-Java高级特性.md) |
| **反射** | [03](./03-Java高级特性.md) |
| **动态代理** | [03](./03-Java高级特性.md) |
| **Lambda** | [04](./04-函数式编程.md), [05](./05-Java新版本特性.md) |
| **Stream** | [04](./04-函数式编程.md), [05](./05-Java新版本特性.md) |
| **HashMap** | [06](./06-Java集合框架.md) |
| **ConcurrentHashMap** | [06](./06-Java集合框架.md) |
| **ArrayList** | [06](./06-Java集合框架.md) |
| **Record** | [05](./05-Java新版本特性.md) |
| **Virtual Thread** | [05](./05-Java新版本特性.md) |

---

## 🎯 核心知识点速查

### 面向对象三大特性

| 特性 | 核心概念 | 关键字 | 作用 |
|------|---------|--------|------|
| **封装 (Encapsulation)** | 隐藏内部实现 | private、protected、public | 保护数据、降低耦合 |
| **继承 (Inheritance)** | 代码复用 | extends、super | 复用代码、建立类层次 |
| **多态 (Polymorphism)** | 同一接口多种实现 | @Override、向上转型 | 灵活扩展、降低耦合 |

### 泛型通配符对比

| 通配符 | 说明 | 使用场景 | 示例 |
|--------|------|---------|------|
| `<T>` | 类型参数 | 定义泛型类/方法 | `class Box<T>` |
| `<?>` | 无界通配符 | 只读操作 | `List<?>` |
| `<? extends T>` | 上界通配符 | 读取数据（协变） | `List<? extends Number>` |
| `<? super T>` | 下界通配符 | 写入数据（逆变） | `List<? super Integer>` |

### Stream常用操作

```
Stream操作分类
├─ 中间操作 (Intermediate)
│   ├─ filter() - 过滤
│   ├─ map() - 映射转换
│   ├─ flatMap() - 扁平化映射
│   ├─ sorted() - 排序
│   ├─ distinct() - 去重
│   └─ limit()/skip() - 限制/跳过
└─ 终止操作 (Terminal)
    ├─ collect() - 收集结果
    ├─ forEach() - 遍历
    ├─ reduce() - 归约
    ├─ count() - 计数
    └─ anyMatch()/allMatch() - 匹配
```

### Java版本重要特性

| 版本 | 重要特性 | 影响 |
|------|---------|------|
| **Java 8** | Lambda、Stream、Optional | ⭐⭐⭐⭐⭐ 革命性更新 |
| **Java 11** | HTTP Client、字符串增强 | ⭐⭐⭐⭐ LTS长期支持版 |
| **Java 17** | Sealed Classes、Pattern Matching | ⭐⭐⭐⭐ LTS长期支持版 |
| **Java 21** | Virtual Threads、Record Patterns | ⭐⭐⭐⭐⭐ LTS+重大特性 |

---

## 🔧 实践项目建议

### 初级项目
- [ ] 学生管理系统（OOP基础练习）
- [ ] 计算器程序（异常处理练习）
- [ ] 图书管理系统（接口与抽象类练习）

### 中级项目
- [ ] 自定义注解框架（注解+反射）
- [ ] 简易ORM框架（反射+泛型）
- [ ] 数据处理工具（Stream API练习）

### 高级项目
- [ ] 依赖注入容器（反射+注解+动态代理）
- [ ] AOP框架实现（动态代理）
- [ ] 插件系统（SPI机制）

---

## 📖 扩展阅读

### 官方文档
- [Java Language Specification](https://docs.oracle.com/javase/specs/)
- [Java SE Documentation](https://docs.oracle.com/en/java/javase/)
- [OpenJDK](https://openjdk.org/)

### 推荐书籍
- 《Java核心技术》（Core Java）- 基础必读
- 《Effective Java》- 最佳实践
- 《深入理解Java虚拟机》- JVM深度

### 在线资源
- [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
- [Baeldung](https://www.baeldung.com/) - Java教程宝库
- [Java Brains](https://javabrains.io/) - 视频教程

---

## 🎓 面试高频知识点

### 必考题（⭐⭐⭐⭐⭐）
1. **面向对象三大特性**（封装、继承、多态）
2. **==与equals()的区别**
3. **String、StringBuilder、StringBuffer的区别**
4. **HashMap的底层实现原理** [06](./06-Java集合框架.md)
5. **ArrayList和LinkedList的区别** [06](./06-Java集合框架.md)
6. **接口与抽象类的区别**
7. **重载与重写的区别**
8. **泛型擦除机制**
9. **反射的原理和应用**
10. **异常分类和处理**

### 高频题（⭐⭐⭐⭐）
1. **HashMap和ConcurrentHashMap的区别** [06](./06-Java集合框架.md)
2. **HashMap的扩容机制** [06](./06-Java集合框架.md)
3. **static、final、this、super的使用**
4. **内部类的分类和使用**
5. **Lambda和匿名内部类的区别**
6. **Stream API的使用**
7. **Optional的使用场景**
8. **JDK动态代理和CGLIB的区别**
9. **枚举的实现原理**
10. **Java 8~21的新特性**

---

## 🤝 贡献

本文档持续更新中，欢迎提出改进建议！

---

## 📝 更新日志

- **2025-10-29** - 新增集合框架章节
  - 新增 [06-Java集合框架](./06-Java集合框架.md)
  - HashMap、ConcurrentHashMap 深入源码分析
  - ArrayList、LinkedList 底层实现与性能对比
  - 面试高频题汇总

- **2025-10** - 初始版本发布
  - 完整的5章节内容
  - 涵盖从基础语法到新版本特性
  - 包含大量代码示例和最佳实践

---

## 📄 许可

本文档采用 [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/) 许可协议。

---

**开始学习：** [01 - Java语法基础 →](./01-Java语法基础.md)

**祝你学习顺利！** 🎉

