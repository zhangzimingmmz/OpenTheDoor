# Java 语法基础 (Java Syntax Fundamentals)

> Java语言的基石，掌握数据类型、运算符、控制流和异常处理

## 目录
- [1. 数据类型](#1-数据类型)
- [2. 运算符](#2-运算符)
- [3. 控制流](#3-控制流)
- [4. 异常处理](#4-异常处理)
- [5. 字符串处理](#5-字符串处理)
- [6. 数组](#6-数组)
- [7. 最佳实践](#7-最佳实践)
- [8. 面试高频问题](#8-面试高频问题)

---

## 1. 数据类型 (Data Types)

### 1.1 基本数据类型 (Primitive Types)

Java有8种基本数据类型，存储在栈内存中，效率高。

| 类型 | 字节数 | 位数 | 取值范围 | 默认值 | 包装类 |
|------|-------|------|---------|--------|--------|
| `byte` | 1 | 8 | -128 ~ 127 | 0 | Byte |
| `short` | 2 | 16 | -32,768 ~ 32,767 | 0 | Short |
| `int` | 4 | 32 | -2³¹ ~ 2³¹-1 | 0 | Integer |
| `long` | 8 | 64 | -2⁶³ ~ 2⁶³-1 | 0L | Long |
| `float` | 4 | 32 | 约±3.4E+38 | 0.0f | Float |
| `double` | 8 | 64 | 约±1.7E+308 | 0.0d | Double |
| `char` | 2 | 16 | 0 ~ 65,535 | '\u0000' | Character |
| `boolean` | - | - | true/false | false | Boolean |

#### 代码示例

```java
/**
 * 基本数据类型示例
 * Primitive Data Types Example
 */
public class PrimitiveTypesDemo {
    
    public static void main(String[] args) {
        // 整型 (Integer Types)
        byte byteVar = 127;
        short shortVar = 32767;
        int intVar = 2147483647;
        long longVar = 9223372036854775807L; // 注意：long类型需要加L后缀
        
        // 浮点型 (Floating-Point Types)
        float floatVar = 3.14f; // 注意：float类型需要加f后缀
        double doubleVar = 3.141592653589793;
        
        // 字符型 (Character Type)
        char charVar = 'A';
        char unicodeChar = '\u0041'; // 也是'A'，使用Unicode编码
        
        // 布尔型 (Boolean Type)
        boolean boolVar = true;
        
        // 类型自动转换 (Automatic Type Conversion)
        // 小范围 -> 大范围：byte -> short -> int -> long -> float -> double
        int i = 100;
        long l = i; // 自动转换，无需强制
        double d = l; // 自动转换
        
        System.out.println("自动转换结果: " + d); // 输出：100.0
        
        // 类型强制转换 (Type Casting)
        // 大范围 -> 小范围：需要强制转换，可能丢失精度
        double doubleValue = 3.14;
        int intValue = (int) doubleValue; // 强制转换，小数部分丢失
        
        System.out.println("强制转换结果: " + intValue); // 输出：3
        
        // 注意溢出问题 (Overflow Issue)
        byte maxByte = 127;
        maxByte++; // 溢出，变成-128
        System.out.println("溢出结果: " + maxByte); // 输出：-128
    }
}
```

#### 注意事项 ⚠️

1. **long类型必须加L后缀**，否则会被当作int处理
2. **float类型必须加f后缀**，否则会被当作double处理
3. **整数默认是int类型**，小数默认是double类型
4. **char本质是16位无符号整数**，可以进行算术运算
5. **boolean不能转换为数字**，与C/C++不同

### 1.2 引用数据类型 (Reference Types)

引用类型存储在堆内存中，变量存储的是对象的引用地址。

- **类 (Class)** - 如 `String`、`Date`、自定义类
- **接口 (Interface)** - 如 `List`、`Map`
- **数组 (Array)** - 如 `int[]`、`String[]`

```java
/**
 * 引用类型示例
 * Reference Types Example
 */
public class ReferenceTypesDemo {
    
    public static void main(String[] args) {
        // 字符串 (String)
        String str1 = "Hello"; // 字符串字面量，存储在字符串常量池
        String str2 = new String("Hello"); // 创建新对象，存储在堆中
        
        // 引用比较 vs 值比较
        System.out.println(str1 == str2); // false - 比较引用地址
        System.out.println(str1.equals(str2)); // true - 比较值内容
        
        // 数组 (Array)
        int[] numbers = {1, 2, 3, 4, 5};
        String[] names = new String[3];
        
        // null值 (Null Value)
        String nullStr = null; // 引用类型可以为null
        // int nullInt = null; // 编译错误！基本类型不能为null
        
        // 包装类 (Wrapper Class) - 基本类型的对象表示
        Integer intObj = 100; // 自动装箱 (Autoboxing)
        int intPrimitive = intObj; // 自动拆箱 (Unboxing)
        
        // 包装类缓存机制 (Wrapper Class Caching)
        Integer a = 127; // 使用缓存
        Integer b = 127;
        System.out.println(a == b); // true - 在缓存范围内（-128~127）
        
        Integer c = 128; // 超出缓存范围
        Integer d = 128;
        System.out.println(c == d); // false - 不在缓存范围内
        System.out.println(c.equals(d)); // true - 值相等
    }
}
```

#### 基本类型 vs 引用类型

| 维度 | 基本类型 | 引用类型 |
|------|---------|---------|
| **存储位置** | 栈内存 | 堆内存（引用在栈） |
| **默认值** | 有默认值（0、false等） | null |
| **比较方式** | 直接比较值 | ==比较地址，equals()比较值 |
| **性能** | 快 | 相对慢 |
| **是否可为null** | 否 | 是 |
| **泛型支持** | 否 | 是 |

---

## 2. 运算符 (Operators)

### 2.1 算术运算符 (Arithmetic Operators)

| 运算符 | 说明 | 示例 | 结果 |
|-------|------|------|------|
| `+` | 加法 | `5 + 3` | 8 |
| `-` | 减法 | `5 - 3` | 2 |
| `*` | 乘法 | `5 * 3` | 15 |
| `/` | 除法 | `5 / 3` | 1（整数除法） |
| `%` | 取模 | `5 % 3` | 2 |
| `++` | 自增 | `i++` / `++i` | i+1 |
| `--` | 自减 | `i--` / `--i` | i-1 |

```java
/**
 * 算术运算符示例
 * Arithmetic Operators Example
 */
public class ArithmeticOperatorsDemo {
    
    public static void main(String[] args) {
        // 基本运算
        int a = 10, b = 3;
        System.out.println("加法: " + (a + b)); // 13
        System.out.println("减法: " + (a - b)); // 7
        System.out.println("乘法: " + (a * b)); // 30
        System.out.println("除法: " + (a / b)); // 3 - 整数除法，舍去小数
        System.out.println("取模: " + (a % b)); // 1
        
        // 浮点除法 (Floating-Point Division)
        double result = (double) a / b;
        System.out.println("浮点除法: " + result); // 3.333...
        
        // 自增自减 (Increment and Decrement)
        int i = 5;
        System.out.println("i++: " + i++); // 先使用后自增，输出5，i变为6
        System.out.println("现在i: " + i); // 6
        
        i = 5;
        System.out.println("++i: " + ++i); // 先自增后使用，输出6，i为6
        
        // 特殊情况：除以0
        // System.out.println(10 / 0); // ArithmeticException：整数除以0抛异常
        System.out.println(10.0 / 0); // Infinity - 浮点数除以0得到无穷大
        System.out.println(0.0 / 0); // NaN (Not a Number) - 未定义运算
    }
}
```

### 2.2 关系运算符 (Relational Operators)

| 运算符 | 说明 | 示例 | 结果 |
|-------|------|------|------|
| `==` | 等于 | `5 == 3` | false |
| `!=` | 不等于 | `5 != 3` | true |
| `>` | 大于 | `5 > 3` | true |
| `<` | 小于 | `5 < 3` | false |
| `>=` | 大于等于 | `5 >= 5` | true |
| `<=` | 小于等于 | `5 <= 3` | false |

### 2.3 逻辑运算符 (Logical Operators)

| 运算符 | 说明 | 短路特性 |
|-------|------|----------|
| `&&` | 逻辑与 (AND) | 是 - 左边为false，右边不执行 |
| `\|\|` | 逻辑或 (OR) | 是 - 左边为true，右边不执行 |
| `!` | 逻辑非 (NOT) | 否 |
| `&` | 按位与 | 否 - 两边都执行 |
| `\|` | 按位或 | 否 - 两边都执行 |

```java
/**
 * 逻辑运算符示例
 * Logical Operators Example
 */
public class LogicalOperatorsDemo {
    
    public static void main(String[] args) {
        int a = 5, b = 3;
        
        // 逻辑与 (&&) - 短路运算
        boolean result1 = (a > 0) && (b > 0); // true
        boolean result2 = (a < 0) && (b > 0); // false，第二个条件不执行
        
        // 逻辑或 (||) - 短路运算
        boolean result3 = (a > 0) || (b < 0); // true，第二个条件不执行
        
        // 逻辑非 (!)
        boolean result4 = !(a > b); // false
        
        // 短路特性的实际应用 (Short-Circuit Evaluation)
        String str = null;
        // 利用短路特性避免空指针异常
        if (str != null && str.length() > 0) {
            // 如果str为null，不会执行str.length()，避免NullPointerException
            System.out.println("字符串不为空");
        }
        
        // 非短路运算符 & 和 |
        int x = 10;
        // 两边都会执行
        boolean result5 = (x++ > 5) & (x++ > 15);
        System.out.println("x的值: " + x); // 12 - 两个自增都执行了
    }
}
```

### 2.4 位运算符 (Bitwise Operators)

| 运算符 | 说明 | 示例 | 说明 |
|-------|------|------|------|
| `&` | 按位与 | `5 & 3` | 两位都为1才为1 |
| `\|` | 按位或 | `5 \| 3` | 有一位为1就为1 |
| `^` | 按位异或 | `5 ^ 3` | 两位不同为1 |
| `~` | 按位取反 | `~5` | 0变1，1变0 |
| `<<` | 左移 | `5 << 1` | 相当于乘以2 |
| `>>` | 右移 | `5 >> 1` | 相当于除以2（带符号） |
| `>>>` | 无符号右移 | `5 >>> 1` | 右移，左边补0 |

```java
/**
 * 位运算符应用示例
 * Bitwise Operators Example
 */
public class BitwiseOperatorsDemo {
    
    public static void main(String[] args) {
        // 基本位运算
        int a = 5;  // 二进制: 0101
        int b = 3;  // 二进制: 0011
        
        System.out.println("a & b = " + (a & b));  // 1 (0001)
        System.out.println("a | b = " + (a | b));  // 7 (0111)
        System.out.println("a ^ b = " + (a ^ b));  // 6 (0110)
        System.out.println("~a = " + (~a));        // -6 (取反)
        
        // 移位运算 (Shift Operations)
        System.out.println("5 << 1 = " + (5 << 1));  // 10 - 左移1位，相当于乘以2
        System.out.println("5 >> 1 = " + (5 >> 1));  // 2 - 右移1位，相当于除以2
        
        // 实际应用1：判断奇偶 (Check Odd/Even)
        int num = 15;
        if ((num & 1) == 0) {
            System.out.println(num + " 是偶数");
        } else {
            System.out.println(num + " 是奇数");
        }
        
        // 实际应用2：交换两个数（不用临时变量）
        int x = 10, y = 20;
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        System.out.println("交换后: x=" + x + ", y=" + y); // x=20, y=10
        
        // 实际应用3：快速计算2的幂
        int power = 1 << 10; // 2^10 = 1024
        System.out.println("2的10次方 = " + power);
    }
}
```

### 2.5 其他运算符

#### 三元运算符 (Ternary Operator)

```java
// 语法：条件 ? 表达式1 : 表达式2
int a = 10, b = 20;
int max = (a > b) ? a : b; // max = 20

// 等价于：
int max2;
if (a > b) {
    max2 = a;
} else {
    max2 = b;
}
```

#### instanceof运算符

```java
String str = "Hello";
boolean isString = str instanceof String; // true

Object obj = "Hello";
boolean isStringObj = obj instanceof String; // true - 向上转型后仍可判断
```

---

## 3. 控制流 (Control Flow)

### 3.1 条件语句 (Conditional Statements)

#### if-else 语句

```java
/**
 * if-else 条件语句示例
 * If-Else Conditional Statement Example
 */
public class IfElseDemo {
    
    public static void main(String[] args) {
        int score = 85;
        
        // 单分支 (Single Branch)
        if (score >= 60) {
            System.out.println("及格");
        }
        
        // 双分支 (Two Branches)
        if (score >= 60) {
            System.out.println("及格");
        } else {
            System.out.println("不及格");
        }
        
        // 多分支 (Multiple Branches)
        if (score >= 90) {
            System.out.println("优秀");
        } else if (score >= 80) {
            System.out.println("良好");
        } else if (score >= 70) {
            System.out.println("中等");
        } else if (score >= 60) {
            System.out.println("及格");
        } else {
            System.out.println("不及格");
        }
        
        // 嵌套if (Nested If)
        boolean isWeekend = true;
        if (score >= 60) {
            if (isWeekend) {
                System.out.println("及格了，可以休息！");
            } else {
                System.out.println("及格了，继续努力！");
            }
        }
    }
}
```

#### switch 语句

```java
/**
 * switch 语句示例
 * Switch Statement Example
 * 
 * 适用场景：等值判断，case较多时比if-else更清晰
 * Use Case: Equality checks with multiple cases
 */
public class SwitchDemo {
    
    public static void main(String[] args) {
        // 传统switch (Traditional Switch)
        int dayOfWeek = 3;
        String dayName;
        
        switch (dayOfWeek) {
            case 1:
                dayName = "星期一";
                break; // break防止穿透
            case 2:
                dayName = "星期二";
                break;
            case 3:
                dayName = "星期三";
                break;
            case 4:
                dayName = "星期四";
                break;
            case 5:
                dayName = "星期五";
                break;
            case 6:
            case 7:
                dayName = "周末";
                break;
            default:
                dayName = "无效";
                break;
        }
        
        System.out.println(dayName); // 输出：星期三
        
        // Java 12+ Switch表达式 (Switch Expression)
        // 注意：需要Java 12及以上版本
        String dayType = switch (dayOfWeek) {
            case 1, 2, 3, 4, 5 -> "工作日"; // 箭头语法，自动break
            case 6, 7 -> "周末";
            default -> "无效";
        };
        
        System.out.println(dayType);
        
        // switch支持的类型 (Supported Types)
        // 1. byte, short, int, char
        // 2. 对应的包装类：Byte, Short, Integer, Character
        // 3. enum枚举类型
        // 4. String字符串（Java 7+）
        
        // String类型示例
        String command = "start";
        switch (command) {
            case "start":
                System.out.println("启动");
                break;
            case "stop":
                System.out.println("停止");
                break;
            default:
                System.out.println("未知命令");
        }
    }
}
```

#### switch 注意事项 ⚠️

1. **必须加break**，否则会发生case穿透
2. **default可选**，但建议加上以处理意外情况
3. **case值必须是常量**，不能是变量
4. **不支持long、float、double类型**

### 3.2 循环语句 (Loop Statements)

#### for 循环

```java
/**
 * for 循环示例
 * For Loop Example
 */
public class ForLoopDemo {
    
    public static void main(String[] args) {
        // 基本for循环 (Basic For Loop)
        for (int i = 0; i < 5; i++) {
            System.out.println("i = " + i);
        }
        
        // 多变量for循环 (Multiple Variables)
        for (int i = 0, j = 10; i < j; i++, j--) {
            System.out.println("i=" + i + ", j=" + j);
        }
        
        // 无限循环 (Infinite Loop)
        // for (;;) {
        //     // 无限循环，等同于while(true)
        // }
        
        // 增强for循环 (Enhanced For Loop / For-Each)
        int[] numbers = {1, 2, 3, 4, 5};
        for (int num : numbers) {
            System.out.println(num);
        }
        
        // 注意：增强for循环不能修改数组元素
        for (int num : numbers) {
            num = num * 2; // 不会影响原数组
        }
        System.out.println("原数组第一个元素: " + numbers[0]); // 仍然是1
    }
}
```

#### while 循环

```java
/**
 * while 循环示例
 * While Loop Example
 */
public class WhileLoopDemo {
    
    public static void main(String[] args) {
        // while循环 - 先判断后执行
        int i = 0;
        while (i < 5) {
            System.out.println("i = " + i);
            i++;
        }
        
        // do-while循环 - 先执行后判断，至少执行一次
        int j = 0;
        do {
            System.out.println("j = " + j);
            j++;
        } while (j < 5);
        
        // 区别演示 (Difference Demo)
        int k = 10;
        
        // while：条件不满足，一次都不执行
        while (k < 5) {
            System.out.println("while: 不会执行");
        }
        
        // do-while：至少执行一次
        do {
            System.out.println("do-while: 至少执行一次");
        } while (k < 5);
    }
}
```

#### for vs while 选择

| 场景 | 推荐 | 原因 |
|------|------|------|
| 循环次数已知 | for | 更简洁 |
| 循环次数未知 | while | 更灵活 |
| 至少执行一次 | do-while | 语义明确 |
| 遍历集合/数组 | for-each | 代码简洁 |

### 3.3 跳转语句 (Jump Statements)

```java
/**
 * 跳转语句示例
 * Jump Statements Example
 */
public class JumpStatementsDemo {
    
    public static void main(String[] args) {
        // break：跳出当前循环
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                break; // 跳出循环
            }
            System.out.println("break demo: i = " + i); // 输出0-4
        }
        
        // continue：跳过本次循环，继续下次循环
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                continue; // 跳过偶数
            }
            System.out.println("continue demo: i = " + i); // 输出1,3,5,7,9
        }
        
        // return：从方法中返回
        int result = sum(1, 2);
        System.out.println("sum result: " + result);
        
        // 带标签的break/continue (Labeled Break/Continue)
        outer: for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    break outer; // 跳出外层循环
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
        
        // 带标签的continue
        outer2: for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    continue outer2; // 继续外层循环的下一次迭代
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
    }
    
    // return示例方法
    public static int sum(int a, int b) {
        return a + b; // 返回结果并结束方法
    }
}
```

---

## 4. 异常处理 (Exception Handling)

### 4.1 异常体系 (Exception Hierarchy)

```
Throwable
├── Error（错误，程序无法处理）
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── ...
└── Exception（异常，程序可以处理）
    ├── RuntimeException（运行时异常，非受检异常）
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── ArithmeticException
    │   ├── ClassCastException
    │   └── ...
    └── 其他Exception（受检异常，必须处理）
        ├── IOException
        ├── SQLException
        ├── ClassNotFoundException
        └── ...
```

#### 受检异常 vs 非受检异常

| 类型 | 说明 | 是否强制处理 | 典型例子 |
|------|------|-------------|---------|
| **受检异常 (Checked Exception)** | 编译时异常 | 是 | IOException, SQLException |
| **非受检异常 (Unchecked Exception)** | 运行时异常 | 否 | NullPointerException, ArithmeticException |

### 4.2 异常处理语法 (Exception Handling Syntax)

#### try-catch-finally

```java
/**
 * 异常处理示例
 * Exception Handling Example
 */
public class ExceptionDemo {
    
    public static void main(String[] args) {
        // 基本try-catch (Basic Try-Catch)
        try {
            int result = 10 / 0; // ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("捕获到异常: " + e.getMessage());
            e.printStackTrace(); // 打印堆栈跟踪
        }
        
        // 多个catch块 (Multiple Catch Blocks)
        try {
            String str = null;
            System.out.println(str.length()); // NullPointerException
        } catch (NullPointerException e) {
            System.out.println("空指针异常");
        } catch (Exception e) {
            System.out.println("其他异常");
        }
        
        // finally块 - 无论是否异常都会执行 (Finally Block)
        try {
            System.out.println("try块");
            // return; // 即使有return，finally也会执行
        } catch (Exception e) {
            System.out.println("catch块");
        } finally {
            System.out.println("finally块 - 一定会执行");
            // 常用于释放资源（关闭文件、数据库连接等）
        }
        
        // try-with-resources (Java 7+) - 自动关闭资源
        // 资源必须实现AutoCloseable接口
        try (java.io.FileInputStream fis = new java.io.FileInputStream("test.txt")) {
            // 使用资源
        } catch (java.io.IOException e) {
            System.out.println("文件操作异常");
        }
        // fis会自动关闭，无需手动调用close()
        
        // 多个资源 (Multiple Resources)
        try (
            java.io.FileInputStream fis = new java.io.FileInputStream("input.txt");
            java.io.FileOutputStream fos = new java.io.FileOutputStream("output.txt")
        ) {
            // 使用多个资源
        } catch (java.io.IOException e) {
            System.out.println("文件操作异常");
        }
    }
}
```

#### 异常处理最佳实践 ✅

1. **不要捕获所有异常** - 只捕获你能处理的异常
2. **finally中释放资源** - 或使用try-with-resources
3. **不要忽略异常** - 至少记录日志
4. **异常信息要清晰** - 便于排查问题
5. **早抛出，晚捕获** - 在最合适的层级处理异常

### 4.3 抛出异常 (Throwing Exceptions)

#### throw vs throws

```java
/**
 * 抛出异常示例
 * Throwing Exceptions Example
 */
public class ThrowExceptionDemo {
    
    /**
     * throws：声明方法可能抛出的异常
     * Declares that method may throw exceptions
     */
    public void readFile(String filePath) throws java.io.IOException {
        if (filePath == null) {
            // throw：手动抛出异常
            throw new IllegalArgumentException("文件路径不能为null");
        }
        
        // 模拟文件读取
        throw new java.io.IOException("文件读取失败");
    }
    
    /**
     * 运行时异常不需要声明throws
     * Runtime exceptions don't need throws declaration
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        return a / b;
    }
    
    public static void main(String[] args) {
        ThrowExceptionDemo demo = new ThrowExceptionDemo();
        
        // 调用声明throws的方法，必须处理异常
        try {
            demo.readFile("test.txt");
        } catch (java.io.IOException e) {
            System.out.println("处理IO异常: " + e.getMessage());
        }
        
        // 运行时异常可以不处理（但建议处理）
        try {
            demo.divide(10, 0);
        } catch (ArithmeticException e) {
            System.out.println("处理算术异常: " + e.getMessage());
        }
    }
}
```

### 4.4 自定义异常 (Custom Exceptions)

```java
/**
 * 自定义业务异常
 * Custom Business Exception
 * 
 * 继承Exception：受检异常
 * 继承RuntimeException：非受检异常
 */
public class BusinessException extends Exception {
    
    private int errorCode;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}

/**
 * 使用自定义异常
 * Using Custom Exception
 */
class UserService {
    
    public void createUser(String username) throws BusinessException {
        if (username == null || username.isEmpty()) {
            throw new BusinessException(1001, "用户名不能为空");
        }
        
        if (username.length() < 3) {
            throw new BusinessException(1002, "用户名长度不能小于3");
        }
        
        // 业务逻辑...
        System.out.println("用户创建成功: " + username);
    }
    
    public static void main(String[] args) {
        UserService service = new UserService();
        
        try {
            service.createUser("ab");
        } catch (BusinessException e) {
            System.out.println("错误码: " + e.getErrorCode());
            System.out.println("错误信息: " + e.getMessage());
        }
    }
}
```

### 4.5 异常链 (Exception Chaining)

```java
/**
 * 异常链示例
 * Exception Chaining Example
 * 
 * 保留原始异常信息，便于排查问题
 */
public class ExceptionChainingDemo {
    
    public void method1() throws BusinessException {
        try {
            method2();
        } catch (java.io.IOException e) {
            // 将底层异常包装成业务异常，保留原始异常
            throw new BusinessException("业务处理失败", e);
        }
    }
    
    public void method2() throws java.io.IOException {
        throw new java.io.IOException("文件不存在");
    }
    
    public static void main(String[] args) {
        ExceptionChainingDemo demo = new ExceptionChainingDemo();
        
        try {
            demo.method1();
        } catch (BusinessException e) {
            System.out.println("顶层异常: " + e.getMessage());
            System.out.println("原始异常: " + e.getCause().getMessage());
            
            // 打印完整的异常链
            e.printStackTrace();
        }
    }
}
```

---

## 5. 字符串处理 (String Handling)

### 5.1 String 类

#### String 特性

- **不可变性 (Immutable)** - String对象创建后不能修改
- **字符串常量池 (String Pool)** - 字面量存储在常量池中
- **线程安全** - 因为不可变

```java
/**
 * String 类示例
 * String Class Example
 */
public class StringDemo {
    
    public static void main(String[] args) {
        // 创建字符串的两种方式
        String str1 = "Hello"; // 字面量，存储在字符串常量池
        String str2 = new String("Hello"); // 创建新对象，存储在堆中
        
        System.out.println(str1 == str2); // false - 不同引用
        System.out.println(str1.equals(str2)); // true - 值相同
        
        // 字符串常量池 (String Pool)
        String s1 = "Java";
        String s2 = "Java";
        System.out.println(s1 == s2); // true - 指向同一个常量池对象
        
        // intern()方法 - 将字符串放入常量池
        String s3 = new String("Java").intern();
        System.out.println(s1 == s3); // true - intern()返回常量池引用
        
        // 字符串拼接
        String s4 = "Hello" + " " + "World"; // 编译期优化为"Hello World"
        String s5 = "Hello World";
        System.out.println(s4 == s5); // true
        
        String prefix = "Hello";
        String s6 = prefix + " World"; // 运行时拼接，创建新对象
        System.out.println(s6 == s5); // false
        
        // 常用方法 (Common Methods)
        String text = "Hello, World!";
        
        System.out.println("长度: " + text.length()); // 13
        System.out.println("字符: " + text.charAt(0)); // H
        System.out.println("子串: " + text.substring(7, 12)); // World
        System.out.println("包含: " + text.contains("World")); // true
        System.out.println("开头: " + text.startsWith("Hello")); // true
        System.out.println("结尾: " + text.endsWith("!")); // true
        System.out.println("索引: " + text.indexOf("World")); // 7
        System.out.println("替换: " + text.replace("World", "Java")); // Hello, Java!
        System.out.println("大写: " + text.toUpperCase()); // HELLO, WORLD!
        System.out.println("小写: " + text.toLowerCase()); // hello, world!
        System.out.println("去空格: " + "  Hello  ".trim()); // Hello
        
        // 分割字符串 (Split String)
        String csv = "Java,Python,C++";
        String[] langs = csv.split(",");
        for (String lang : langs) {
            System.out.println(lang);
        }
        
        // 字符串比较 (String Comparison)
        String a = "abc";
        String b = "abd";
        System.out.println(a.compareTo(b)); // 负数 - a小于b
        System.out.println(a.compareToIgnoreCase("ABC")); // 0 - 忽略大小写相等
    }
}
```

### 5.2 StringBuilder 和 StringBuffer

| 类 | 可变性 | 线程安全 | 性能 | 使用场景 |
|---|--------|---------|------|---------|
| **String** | 不可变 | 安全 | 慢（频繁拼接） | 少量字符串操作 |
| **StringBuilder** | 可变 | 不安全 | 快 | 单线程大量拼接 |
| **StringBuffer** | 可变 | 安全（synchronized） | 中等 | 多线程大量拼接 |

```java
/**
 * StringBuilder 和 StringBuffer 示例
 * StringBuilder and StringBuffer Example
 */
public class StringBuilderDemo {
    
    public static void main(String[] args) {
        // StringBuilder示例 (StringBuilder Example)
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        System.out.println(sb.toString()); // Hello World
        
        // 链式调用 (Method Chaining)
        String result = new StringBuilder()
                .append("Java")
                .append(" ")
                .append("Programming")
                .toString();
        System.out.println(result); // Java Programming
        
        // 常用方法 (Common Methods)
        StringBuilder builder = new StringBuilder("Hello");
        builder.insert(5, " World"); // 插入
        System.out.println(builder); // Hello World
        
        builder.delete(5, 11); // 删除
        System.out.println(builder); // Hello
        
        builder.reverse(); // 反转
        System.out.println(builder); // olleH
        
        // 性能对比 (Performance Comparison)
        long start, end;
        
        // String拼接 - 慢
        start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += i; // 每次都创建新对象
        }
        end = System.currentTimeMillis();
        System.out.println("String拼接耗时: " + (end - start) + "ms");
        
        // StringBuilder拼接 - 快
        start = System.currentTimeMillis();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb2.append(i); // 在原对象上修改
        }
        end = System.currentTimeMillis();
        System.out.println("StringBuilder拼接耗时: " + (end - start) + "ms");
    }
}
```

---

## 6. 数组 (Arrays)

### 6.1 一维数组 (One-Dimensional Arrays)

```java
/**
 * 一维数组示例
 * One-Dimensional Array Example
 */
public class ArrayDemo {
    
    public static void main(String[] args) {
        // 数组声明和初始化 (Array Declaration and Initialization)
        
        // 方式1：先声明后赋值
        int[] arr1 = new int[5]; // 默认值为0
        arr1[0] = 1;
        arr1[1] = 2;
        
        // 方式2：声明时初始化
        int[] arr2 = {1, 2, 3, 4, 5};
        
        // 方式3：new方式初始化
        int[] arr3 = new int[]{1, 2, 3, 4, 5};
        
        // 数组长度 (Array Length)
        System.out.println("数组长度: " + arr2.length); // 5
        
        // 遍历数组 (Iterating Arrays)
        
        // 普通for循环
        for (int i = 0; i < arr2.length; i++) {
            System.out.println("arr2[" + i + "] = " + arr2[i]);
        }
        
        // 增强for循环（推荐）
        for (int num : arr2) {
            System.out.println(num);
        }
        
        // 数组拷贝 (Array Copying)
        int[] source = {1, 2, 3, 4, 5};
        int[] dest = new int[5];
        
        // 方法1：System.arraycopy()
        System.arraycopy(source, 0, dest, 0, source.length);
        
        // 方法2：Arrays.copyOf()
        int[] copy = java.util.Arrays.copyOf(source, source.length);
        
        // 方法3：clone()
        int[] clone = source.clone();
        
        // 数组排序 (Array Sorting)
        int[] unsorted = {5, 2, 8, 1, 9};
        java.util.Arrays.sort(unsorted);
        System.out.println("排序后: " + java.util.Arrays.toString(unsorted));
        
        // 数组填充 (Array Fill)
        int[] filled = new int[5];
        java.util.Arrays.fill(filled, 10);
        System.out.println("填充后: " + java.util.Arrays.toString(filled));
        
        // 数组比较 (Array Comparison)
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2, 3};
        System.out.println(a1 == a2); // false - 不同对象
        System.out.println(java.util.Arrays.equals(a1, a2)); // true - 内容相同
        
        // 数组转字符串 (Array to String)
        System.out.println(java.util.Arrays.toString(a1)); // [1, 2, 3]
    }
}
```

### 6.2 多维数组 (Multi-Dimensional Arrays)

```java
/**
 * 多维数组示例
 * Multi-Dimensional Array Example
 */
public class MultiDimensionalArrayDemo {
    
    public static void main(String[] args) {
        // 二维数组 (Two-Dimensional Array)
        
        // 规则二维数组
        int[][] matrix1 = new int[3][4]; // 3行4列
        
        // 直接初始化
        int[][] matrix2 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        
        // 不规则二维数组（锯齿数组）
        int[][] jaggedArray = new int[3][];
        jaggedArray[0] = new int[2];
        jaggedArray[1] = new int[3];
        jaggedArray[2] = new int[4];
        
        // 遍历二维数组 (Iterating 2D Array)
        
        // 普通for循环
        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[i].length; j++) {
                System.out.print(matrix2[i][j] + " ");
            }
            System.out.println();
        }
        
        // 增强for循环
        for (int[] row : matrix2) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
        
        // 三维数组 (Three-Dimensional Array)
        int[][][] cube = new int[2][3][4]; // 2x3x4
        
        // 打印二维数组 (Print 2D Array)
        System.out.println(java.util.Arrays.deepToString(matrix2));
        // 输出：[[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]
    }
}
```

### 6.3 数组常见操作

```java
/**
 * 数组常见操作示例
 * Common Array Operations Example
 */
public class ArrayOperationsDemo {
    
    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9, 3, 7};
        
        // 1. 查找最大值/最小值 (Find Max/Min)
        int max = arr[0];
        int min = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
            if (num < min) min = num;
        }
        System.out.println("最大值: " + max + ", 最小值: " + min);
        
        // 2. 求和与平均值 (Sum and Average)
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        double average = (double) sum / arr.length;
        System.out.println("和: " + sum + ", 平均值: " + average);
        
        // 3. 数组反转 (Reverse Array)
        int[] reversed = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - 1 - i];
        }
        System.out.println("反转后: " + java.util.Arrays.toString(reversed));
        
        // 4. 二分查找 (Binary Search) - 数组必须已排序
        int[] sorted = {1, 2, 3, 5, 7, 8, 9};
        int index = java.util.Arrays.binarySearch(sorted, 5);
        System.out.println("5的位置: " + index); // 3
        
        // 5. 数组去重 (Remove Duplicates)
        int[] withDuplicates = {1, 2, 2, 3, 3, 3, 4, 5};
        java.util.Set<Integer> set = new java.util.HashSet<>();
        for (int num : withDuplicates) {
            set.add(num);
        }
        Integer[] unique = set.toArray(new Integer[0]);
        System.out.println("去重后: " + java.util.Arrays.toString(unique));
    }
}
```

---

## 7. 最佳实践 (Best Practices)

### 7.1 代码规范

1. **命名规范**
   - 类名：`PascalCase`（每个单词首字母大写）
   - 方法名和变量名：`camelCase`（驼峰命名）
   - 常量：`UPPER_CASE`（全大写，下划线分隔）
   - 包名：`lowercase`（全小写）

2. **代码格式**
   - 缩进使用4个空格
   - 左花括号不换行
   - 运算符两边加空格
   - 每行一条语句

3. **注释规范**
   - 类和方法加JavaDoc注释
   - 复杂逻辑加行内注释
   - 注释要准确，不要冗余

### 7.2 性能优化

1. **字符串拼接使用StringBuilder**
   ```java
   // ❌ 不推荐
   String result = "";
   for (int i = 0; i < 1000; i++) {
       result += i;
   }
   
   // ✅ 推荐
   StringBuilder sb = new StringBuilder();
   for (int i = 0; i < 1000; i++) {
       sb.append(i);
   }
   String result = sb.toString();
   ```

2. **避免在循环中创建对象**
   ```java
   // ❌ 不推荐
   for (int i = 0; i < 1000; i++) {
       String s = new String("test"); // 每次都创建新对象
   }
   
   // ✅ 推荐
   String s = "test"; // 常量池复用
   for (int i = 0; i < 1000; i++) {
       // 使用s
   }
   ```

3. **合理使用包装类缓存**
   ```java
   // Integer缓存-128~127，使用valueOf而非new
   Integer a = Integer.valueOf(100); // 使用缓存
   Integer b = new Integer(100); // 不使用缓存
   ```

### 7.3 安全编码

1. **避免空指针异常**
   ```java
   // ✅ 使用Optional（Java 8+）
   String str = null;
   String result = Optional.ofNullable(str).orElse("默认值");
   
   // ✅ 提前判空
   if (str != null && str.length() > 0) {
       // 使用str
   }
   ```

2. **数组越界检查**
   ```java
   int[] arr = {1, 2, 3};
   int index = 5;
   
   // ✅ 访问前检查
   if (index >= 0 && index < arr.length) {
       int value = arr[index];
   }
   ```

3. **资源及时关闭**
   ```java
   // ✅ 使用try-with-resources
   try (FileInputStream fis = new FileInputStream("file.txt")) {
       // 使用资源
   } // 自动关闭
   ```

---

## 8. 面试高频问题 (Frequently Asked Interview Questions)

### Q1: == 和 equals() 的区别？⭐⭐⭐⭐⭐

**答案：**

- `==`：比较基本类型的值，比较引用类型的地址
- `equals()`：比较对象的内容（需要重写）

```java
// 基本类型
int a = 10, b = 10;
System.out.println(a == b); // true - 比较值

// 引用类型
String s1 = new String("Hello");
String s2 = new String("Hello");
System.out.println(s1 == s2); // false - 不同对象
System.out.println(s1.equals(s2)); // true - 内容相同
```

### Q2: String、StringBuilder、StringBuffer 的区别？⭐⭐⭐⭐⭐

**答案：**

| 维度 | String | StringBuilder | StringBuffer |
|------|--------|---------------|--------------|
| **可变性** | 不可变 | 可变 | 可变 |
| **线程安全** | 安全 | 不安全 | 安全（synchronized） |
| **性能** | 慢（频繁拼接） | 快 | 中等 |
| **使用场景** | 少量操作 | 单线程大量拼接 | 多线程大量拼接 |

### Q3: Java 中的异常分类？⭐⭐⭐⭐

**答案：**

- **Throwable**
  - **Error**：系统错误，程序无法处理（如OutOfMemoryError）
  - **Exception**：程序可以处理的异常
    - **RuntimeException**：运行时异常（非受检），可以不处理
    - **其他Exception**：受检异常，必须处理

### Q4: final、finally、finalize 的区别？⭐⭐⭐⭐

**答案：**

- **final**：关键字，修饰类（不可继承）、方法（不可重写）、变量（常量）
- **finally**：异常处理中的块，无论是否异常都会执行
- **finalize()**：Object类的方法，对象被GC前调用（已过时）

### Q5: 重载（Overload）和重写（Override）的区别？⭐⭐⭐⭐⭐

**答案：**

| 维度 | 重载 (Overload) | 重写 (Override) |
|------|----------------|----------------|
| **位置** | 同一个类 | 父类和子类 |
| **方法名** | 相同 | 相同 |
| **参数列表** | 不同 | 相同 |
| **返回类型** | 可以不同 | 相同或子类 |
| **访问修饰符** | 可以不同 | 不能更严格 |
| **发生时机** | 编译期 | 运行期（多态） |

```java
// 重载示例
class Calculator {
    public int add(int a, int b) { return a + b; }
    public double add(double a, double b) { return a + b; }
}

// 重写示例
class Animal {
    public void sound() { System.out.println("动物叫"); }
}
class Dog extends Animal {
    @Override
    public void sound() { System.out.println("汪汪"); }
}
```

### Q6: Java 中的基本数据类型及其占用字节数？⭐⭐⭐⭐⭐

**答案：**
- byte (1字节)
- short (2字节)
- int (4字节)
- long (8字节)
- float (4字节)
- double (8字节)
- char (2字节)
- boolean (不确定，JVM实现相关)

### Q7: 如何实现两个变量的交换？⭐⭐⭐

**答案：**

```java
// 方法1：临时变量
int temp = a;
a = b;
b = temp;

// 方法2：算术运算（可能溢出）
a = a + b;
b = a - b;
a = a - b;

// 方法3：位运算（推荐）
a = a ^ b;
b = a ^ b;
a = a ^ b;
```

### Q8: switch 支持哪些数据类型？⭐⭐⭐⭐

**答案：**
- 整型：byte、short、int、char（及其包装类）
- 枚举类型（Java 5+）
- String类型（Java 7+）
- 不支持：long、float、double、boolean

### Q9: 包装类的缓存范围是多少？⭐⭐⭐

**答案：**
- Byte、Short、Integer、Long：-128 ~ 127
- Character：0 ~ 127
- Boolean：true、false
- Float、Double：无缓存

```java
Integer a = 100; // 使用缓存
Integer b = 100;
System.out.println(a == b); // true

Integer c = 200; // 不使用缓存
Integer d = 200;
System.out.println(c == d); // false
```

### Q10: 数组和集合的区别？⭐⭐⭐⭐

**答案：**

| 维度 | 数组 | 集合 |
|------|------|------|
| **长度** | 固定 | 可变 |
| **类型** | 可存基本类型和对象 | 只能存对象 |
| **功能** | 基本 | 丰富（排序、查找等） |
| **性能** | 快 | 相对慢 |

---

## 总结 (Summary)

本章介绍了Java语言的基础语法，包括：

- ✅ **数据类型**：8种基本类型 + 引用类型
- ✅ **运算符**：算术、关系、逻辑、位运算
- ✅ **控制流**：if-else、switch、for、while
- ✅ **异常处理**：try-catch-finally、自定义异常
- ✅ **字符串**：String、StringBuilder、StringBuffer
- ✅ **数组**：一维数组、多维数组、常用操作

掌握这些基础语法是学习Java的第一步，建议：

1. **多敲代码**：每个示例都自己实现一遍
2. **理解原理**：不要死记硬背，理解背后的机制
3. **解决问题**：尝试用学到的知识解决实际问题
4. **查漏补缺**：对照面试题检验自己的掌握程度

---

**下一篇：** [02 - 面向对象编程 →](./02-面向对象编程.md)

**返回目录：** [Java 语言基础导航 ←](./README.md)

