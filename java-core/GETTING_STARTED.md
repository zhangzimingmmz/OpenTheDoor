# Java Core 项目使用指南

> 快速开始使用本项目进行 Java 核心知识学习

## 🎯 项目目标

本项目是一个系统化的 Java 核心知识学习实验项目，旨在帮助你：

- ✅ 系统学习 Java 基础到高级特性
- ✅ 通过可运行的代码理解概念
- ✅ 准备技术面试
- ✅ 建立完整的知识体系

## 📦 环境准备

### 必需环境
- **JDK 17+** （推荐使用 LTS 版本）
- **Maven 3.6+**
- **IDE**: IntelliJ IDEA（推荐）/ Eclipse / VS Code

### 验证环境

```bash
# 检查Java版本
java -version

# 检查Maven版本
mvn -version
```

## 🚀 快速开始

### 1. 导入项目

#### 使用 IntelliJ IDEA（推荐）

1. 打开 IntelliJ IDEA
2. 选择 `File` → `Open`
3. 选择 `java-core` 目录
4. 等待 Maven 自动导入依赖

#### 使用 Eclipse

1. 打开 Eclipse
2. 选择 `File` → `Import` → `Existing Maven Projects`
3. 选择 `java-core` 目录
4. 点击 `Finish`

### 2. 编译项目

```bash
# 进入项目目录
cd java-core

# 清理并编译
mvn clean compile

# 编译并运行测试
mvn clean test
```

### 3. 运行示例

#### 方式1：在 IDE 中直接运行（推荐）

1. 找到想要运行的示例类（如 `PrimitiveTypesDemo.java`）
2. 右键点击类名
3. 选择 `Run 'PrimitiveTypesDemo.main()'`

#### 方式2：使用 Maven 命令

```bash
# 运行指定类
mvn exec:java -Dexec.mainClass="cn.openthedoor.java.syntax.datatypes.PrimitiveTypesDemo"
```

## 📚 学习路径建议

### 第一阶段：基础巩固（1-2周）

#### Week 1: Java 语法基础

```
Day 1-2: java-syntax-basics/datatypes
├── PrimitiveTypesDemo.java      - 基本数据类型
└── WrapperClassDemo.java        - 包装类

Day 3-4: java-syntax-basics/operators
└── OperatorsDemo.java           - 运算符

Day 5-6: java-syntax-basics/controlflow
├── ConditionalDemo.java         - 条件语句
└── LoopDemo.java                - 循环语句

Day 7: java-syntax-basics/exception + string + array
├── ExceptionDemo.java           - 异常处理
├── StringDemo.java              - 字符串
└── ArrayDemo.java               - 数组
```

#### Week 2: 面向对象编程

```
Day 1-2: java-oop/basics
└── ClassAndObjectDemo.java      - 类与对象

Day 3: java-oop/encapsulation
└── EncapsulationDemo.java       - 封装

Day 4: java-oop/inheritance
└── InheritanceDemo.java         - 继承

Day 5: java-oop/polymorphism
└── PolymorphismDemo.java        - 多态

Day 6-7: 复习和实践
```

### 第二阶段：进阶提升（2-3周）

#### Week 3-4: Java 高级特性

```
Day 1-3: java-advanced/generics
└── GenericsDemo.java            - 泛型

Day 4-5: java-advanced/reflection
└── ReflectionDemo.java          - 反射（需自行创建）

Day 6-7: java-advanced/annotations + proxy
```

#### Week 5: 函数式编程

```
Day 1-2: java-functional
├── LambdaDemo.java              - Lambda表达式
└── StreamDemo.java              - Stream API

Day 3-7: 实践项目
```

### 第三阶段：深入理解（2-3周）

#### Week 6-7: 集合框架

```
Day 1-3: java-collections
└── HashMapDemo.java             - HashMap深入

Day 4-7: 其他集合类学习
```

#### Week 8: 新版本特性

```
Day 1-7: java-new-features
└── 学习Java 8-21新特性
```

## 💡 学习建议

### 学习方法

1. **先理论后实践**
   - 阅读模块的 README
   - 理解核心概念
   - 运行示例代码
   - 观察输出结果

2. **动手实践**
   - 不要只看代码，自己敲一遍
   - 修改参数观察不同结果
   - 尝试扩展示例代码

3. **总结归纳**
   - 每个模块学完后写笔记
   - 整理易错点
   - 记录面试题答案

4. **循序渐进**
   - 按照建议的学习路径进行
   - 不要跳过基础内容
   - 遇到困难及时复习前面内容

### 代码阅读技巧

每个示例文件都包含：

- **类注释** - 学习目标和概述
- **方法注释** - 每个方法的作用
- **行内注释** - 关键代码的解释
- **中英文对照** - 便于理解专业术语

建议阅读顺序：

1. 先看类注释，了解学习目标
2. 运行 main 方法，看整体效果
3. 逐个方法阅读，理解细节
4. 尝试修改代码，验证理解

### 练习建议

每学完一个模块后：

1. **基础练习**
   - 重写示例代码（不看原代码）
   - 完成模块 README 中的练习题

2. **进阶练习**
   - 扩展示例功能
   - 结合多个知识点编写综合示例

3. **面试准备**
   - 记录模块中的面试高频问题
   - 准备标准回答
   - 理解追问的深度话题

## 🛠️ 常用 Maven 命令

```bash
# 清理项目
mvn clean

# 编译项目
mvn compile

# 运行测试
mvn test

# 打包项目
mvn package

# 编译指定模块
mvn compile -pl java-syntax-basics

# 跳过测试编译
mvn compile -DskipTests

# 查看依赖树
mvn dependency:tree
```

## 📖 IDE 使用技巧

### IntelliJ IDEA 快捷键

| 操作 | Windows/Linux | macOS |
|------|---------------|-------|
| 运行当前类 | `Ctrl + Shift + F10` | `Control + Shift + R` |
| 快速修复 | `Alt + Enter` | `Option + Enter` |
| 格式化代码 | `Ctrl + Alt + L` | `Command + Option + L` |
| 查找类 | `Ctrl + N` | `Command + O` |
| 查找文件 | `Ctrl + Shift + N` | `Command + Shift + O` |
| 查看文档 | `Ctrl + Q` | `Control + J` |

### 推荐插件

- **Lombok** - 简化 Java 代码
- **Maven Helper** - Maven 依赖管理
- **Rainbow Brackets** - 彩虹括号
- **CodeGlance** - 代码缩略图

## 🎓 学习资源推荐

### 官方文档
- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [Java Language Specification](https://docs.oracle.com/javase/specs/)
- [OpenJDK](https://openjdk.org/)

### 在线教程
- [Baeldung](https://www.baeldung.com/) - Java 教程宝库
- [Java Brains](https://javabrains.io/) - 视频教程
- [GeeksforGeeks](https://www.geeksforgeeks.org/java/) - Java 教程

### 推荐书籍
- 《Java核心技术》（Core Java） - 基础必读
- 《Effective Java》 - 最佳实践
- 《深入理解Java虚拟机》 - JVM 深度

## ❓ 常见问题

### Q1: 编译时出现编码错误？
**A:** 确保 Maven 使用 UTF-8 编码，项目 pom.xml 已配置：
```xml
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
```

### Q2: 找不到或无法加载主类？
**A:** 
1. 检查包名和类名是否正确
2. 重新编译项目：`mvn clean compile`
3. 在 IDE 中：`File` → `Invalidate Caches / Restart`

### Q3: 如何只运行某个模块？
**A:** 
```bash
cd java-syntax-basics
mvn clean compile
```
或使用 `-pl` 参数：
```bash
mvn compile -pl java-syntax-basics
```

### Q4: 某些 Java 新特性无法使用？
**A:** 确认 JDK 版本 >= 17，在 IDEA 中设置：
- `File` → `Project Structure` → `Project SDK` → 选择 JDK 17+

### Q5: 如何添加自己的练习代码？
**A:** 在对应模块的 `src/main/java` 下创建新的包和类，遵循现有的命名规范。

## 📞 获取帮助

如果在学习过程中遇到问题：

1. 查看模块的 README 文档
2. 阅读代码中的注释
3. 搜索官方文档
4. 查看本项目的 Issue（如有 GitHub 仓库）

## ✅ 学习检查清单

### 基础阶段
- [ ] 掌握 8 种基本数据类型
- [ ] 理解包装类和自动装箱/拆箱
- [ ] 掌握运算符优先级
- [ ] 熟练使用控制流语句
- [ ] 理解异常处理机制
- [ ] 掌握 String、StringBuilder、StringBuffer
- [ ] 掌握数组的使用

### 面向对象阶段
- [ ] 理解类与对象的概念
- [ ] 掌握封装、继承、多态三大特性
- [ ] 理解 this 和 super 关键字
- [ ] 理解 static 关键字
- [ ] 掌握访问修饰符

### 进阶阶段
- [ ] 掌握泛型的使用
- [ ] 理解反射机制
- [ ] 了解动态代理
- [ ] 掌握 Lambda 表达式
- [ ] 熟练使用 Stream API

### 高级阶段
- [ ] 理解 HashMap 底层原理
- [ ] 理解 ConcurrentHashMap 并发机制
- [ ] 了解 Java 8-21 新特性
- [ ] 能够解答面试高频问题

---

**开始学习：** [01 - Java 语法基础 →](java-syntax-basics/)

💪 坚持学习，从基础到精通！

