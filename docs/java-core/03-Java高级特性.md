# Java 高级特性 (Java Advanced Features)

> 掌握泛型、注解、反射、动态代理等高级特性，提升代码质量和灵活性

## 目录
- [1. 泛型](#1-泛型)
- [2. 注解](#2-注解)
- [3. 反射](#3-反射)
- [4. 动态代理](#4-动态代理)
- [5. 枚举](#5-枚举)
- [6. SPI机制](#6-spi机制)
- [7. 最佳实践](#7-最佳实践)
- [8. 面试高频问题](#8-面试高频问题)

---

## 1. 泛型 (Generics)

泛型提供了编译时类型安全检测机制，允许在定义类、接口和方法时使用类型参数。

### 1.1 为什么需要泛型？

```java
/**
 * 没有泛型的问题 (Problems Without Generics)
 */
public class WithoutGenerics {
    public static void main(String[] args) {
        // 使用Object类型，可以存储任何对象
        List list = new ArrayList();
        list.add("Hello");
        list.add(123);
        list.add(new Date());
        
        // 问题1：需要强制类型转换
        String str = (String) list.get(0);
        
        // 问题2：运行时才发现类型错误
        // String str2 = (String) list.get(1); // ClassCastException!
        
        // 问题3：编译器无法检查类型错误
    }
}

/**
 * 使用泛型的优势 (Advantages of Generics)
 */
public class WithGenerics {
    public static void main(String[] args) {
        // 使用泛型，指定存储的类型
        List<String> list = new ArrayList<>();
        list.add("Hello");
        // list.add(123); // 编译错误！类型不匹配
        
        // 不需要强制类型转换
        String str = list.get(0);
        
        // 编译时就能发现类型错误
    }
}
```

### 1.2 泛型类 (Generic Class)

```java
/**
 * 泛型类示例：自定义容器
 * Generic Class Example: Custom Container
 * 
 * T是类型参数（Type Parameter），命名惯例：
 * - T: Type (类型)
 * - E: Element (元素)
 * - K: Key (键)
 * - V: Value (值)
 * - N: Number (数字)
 */
public class Box<T> {
    
    private T content;
    
    public void set(T content) {
        this.content = content;
    }
    
    public T get() {
        return content;
    }
    
    public boolean isEmpty() {
        return content == null;
    }
}

/**
 * 多类型参数的泛型类
 * Generic Class with Multiple Type Parameters
 */
public class Pair<K, V> {
    
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return "Pair{" + key + "=" + value + "}";
    }
}

/**
 * 使用示例
 */
public class GenericClassDemo {
    public static void main(String[] args) {
        // 使用泛型类
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello");
        String str = stringBox.get(); // 不需要类型转换
        
        Box<Integer> intBox = new Box<>();
        intBox.set(123);
        Integer num = intBox.get();
        
        // 多类型参数
        Pair<String, Integer> pair = new Pair<>("age", 25);
        System.out.println(pair); // Pair{age=25}
    }
}
```

### 1.3 泛型方法 (Generic Method)

```java
/**
 * 泛型方法示例
 * Generic Method Example
 */
public class GenericMethodDemo {
    
    /**
     * 泛型方法：打印数组
     * <T> 声明这是一个泛型方法
     */
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    
    /**
     * 泛型方法：交换数组元素
     */
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * 泛型方法：查找元素
     * 返回泛型类型
     */
    public static <T> T findFirst(T[] array, T target) {
        for (T element : array) {
            if (element.equals(target)) {
                return element;
            }
        }
        return null;
    }
    
    /**
     * 有界类型参数 (Bounded Type Parameter)
     * 限制T必须是Number或其子类
     */
    public static <T extends Number> double sum(T[] array) {
        double sum = 0.0;
        for (T element : array) {
            sum += element.doubleValue();
        }
        return sum;
    }
    
    public static void main(String[] args) {
        // 使用泛型方法
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] strArray = {"A", "B", "C"};
        
        printArray(intArray); // 1 2 3 4 5
        printArray(strArray); // A B C
        
        swap(intArray, 0, 4);
        printArray(intArray); // 5 2 3 4 1
        
        // 有界类型参数
        Integer[] nums = {1, 2, 3};
        Double[] doubles = {1.1, 2.2, 3.3};
        System.out.println(sum(nums));    // 6.0
        System.out.println(sum(doubles)); // 6.6
    }
}
```

### 1.4 通配符 (Wildcards)

#### 无界通配符 `<?>`

```java
/**
 * 无界通配符示例
 * Unbounded Wildcard Example
 */
public class UnboundedWildcardDemo {
    
    /**
     * 打印任意类型的List
     * ? 表示未知类型
     */
    public static void printList(List<?> list) {
        for (Object elem : list) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<String> strList = Arrays.asList("A", "B", "C");
        
        printList(intList); // 1 2 3
        printList(strList); // A B C
        
        // 注意：不能添加元素（除了null）
        List<?> unknownList = intList;
        // unknownList.add(1); // 编译错误！
        unknownList.add(null); // 可以
    }
}
```

#### 上界通配符 `<? extends T>`

```java
/**
 * 上界通配符示例 (Upper Bounded Wildcard)
 * <? extends Number> 表示Number或其子类
 * 
 * 特点：只能读取，不能写入（PECS原则：Producer Extends）
 */
public class UpperBoundedWildcardDemo {
    
    /**
     * 计算数字列表的总和
     * 可以接受Number及其所有子类的List
     */
    public static double sum(List<? extends Number> list) {
        double sum = 0.0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }
    
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);
        
        System.out.println(sum(intList));    // 6.0
        System.out.println(sum(doubleList)); // 6.6
        
        // 只能读取，不能添加
        List<? extends Number> numbers = intList;
        Number num = numbers.get(0); // 可以读取
        // numbers.add(1); // 编译错误！不能添加
    }
}
```

#### 下界通配符 `<? super T>`

```java
/**
 * 下界通配符示例 (Lower Bounded Wildcard)
 * <? super Integer> 表示Integer或其父类
 * 
 * 特点：可以写入，读取为Object（PECS原则：Consumer Super）
 */
public class LowerBoundedWildcardDemo {
    
    /**
     * 添加整数到集合
     * 可以接受Integer及其所有父类的List
     */
    public static void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 3; i++) {
            list.add(i); // 可以添加Integer
        }
    }
    
    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        List<Number> numList = new ArrayList<>();
        List<Object> objList = new ArrayList<>();
        
        addNumbers(intList); // 可以
        addNumbers(numList); // 可以
        addNumbers(objList); // 可以
        
        System.out.println(intList); // [1, 2, 3]
        
        // 可以写入，读取为Object
        List<? super Integer> list = intList;
        list.add(100); // 可以添加
        Object obj = list.get(0); // 只能读取为Object
        // Integer num = list.get(0); // 编译错误！
    }
}
```

#### PECS原则

**PECS：Producer Extends, Consumer Super**

- **Producer（生产者）使用extends**：从集合中读取数据
- **Consumer（消费者）使用super**：向集合中写入数据

```java
/**
 * PECS原则示例
 * PECS Principle Example
 */
public class PECSDemo {
    
    /**
     * 复制列表
     * src是生产者（读取），使用extends
     * dest是消费者（写入），使用super
     */
    public static <T> void copy(
            List<? extends T> src,
            List<? super T> dest) {
        for (T item : src) {
            dest.add(item);
        }
    }
    
    public static void main(String[] args) {
        List<Integer> src = Arrays.asList(1, 2, 3);
        List<Number> dest = new ArrayList<>();
        
        copy(src, dest);
        System.out.println(dest); // [1, 2, 3]
    }
}
```

### 1.5 类型擦除 (Type Erasure)

泛型信息只存在于编译期，运行时会被擦除。

```java
/**
 * 类型擦除示例
 * Type Erasure Example
 */
public class TypeErasureDemo {
    
    public static void main(String[] args) {
        List<String> strList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        
        // 运行时类型相同，都是ArrayList
        System.out.println(strList.getClass() == intList.getClass()); // true
        
        // 通过反射可以绕过泛型检查（不推荐）
        try {
            Method add = strList.getClass().getMethod("add", Object.class);
            add.invoke(strList, 123); // 添加Integer到String列表
            System.out.println(strList); // [123] - 运行时不检查类型
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 类型擦除后的代码
        // List<String> list = new ArrayList<>();
        // 编译后变为：
        // List list = new ArrayList();
    }
}
```

---

## 2. 注解 (Annotations)

注解是一种元数据，为代码提供额外信息，不直接影响代码执行。

### 2.1 内置注解 (Built-in Annotations)

```java
/**
 * 内置注解示例
 * Built-in Annotations Example
 */
public class BuiltInAnnotationsDemo {
    
    /**
     * @Override - 标记方法重写父类方法
     * 编译器会检查是否正确重写
     */
    @Override
    public String toString() {
        return "BuiltInAnnotationsDemo";
    }
    
    /**
     * @Deprecated - 标记过时的方法
     * 使用时编译器会发出警告
     */
    @Deprecated
    public void oldMethod() {
        System.out.println("这是一个过时的方法");
    }
    
    /**
     * @SuppressWarnings - 抑制编译器警告
     * 常用值：
     * - "unchecked" - 未检查的转换
     * - "deprecation" - 使用过时方法
     * - "rawtypes" - 使用原始类型
     * - "all" - 所有警告
     */
    @SuppressWarnings("unchecked")
    public void useRawType() {
        List list = new ArrayList(); // 原始类型，会有警告
        list.add("Hello");
    }
    
    /**
     * @SafeVarargs - 标记方法使用可变参数是安全的
     * Java 7+ 用于泛型可变参数
     */
    @SafeVarargs
    public final <T> void printAll(T... items) {
        for (T item : items) {
            System.out.println(item);
        }
    }
    
    /**
     * @FunctionalInterface - 标记函数式接口
     * Java 8+ 确保接口只有一个抽象方法
     */
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }
}
```

### 2.2 自定义注解 (Custom Annotations)

```java
/**
 * 自定义注解：用户信息
 * Custom Annotation: User Info
 * 
 * @interface 声明注解
 */
@Target(ElementType.TYPE) // 指定注解可以用在哪里
@Retention(RetentionPolicy.RUNTIME) // 指定注解保留到什么时候
@Documented // 包含在JavaDoc中
public @interface UserInfo {
    
    // 注解元素（类似方法定义）
    String name(); // 必填
    
    int age() default 0; // 有默认值，可选
    
    String[] hobbies() default {}; // 数组类型
}

/**
 * 元注解 (Meta-Annotations)
 * 用于注解其他注解的注解
 */

/**
 * @Target - 指定注解的使用目标
 * ElementType取值：
 * - TYPE: 类、接口、枚举
 * - FIELD: 字段
 * - METHOD: 方法
 * - PARAMETER: 参数
 * - CONSTRUCTOR: 构造器
 * - LOCAL_VARIABLE: 局部变量
 * - ANNOTATION_TYPE: 注解
 * - PACKAGE: 包
 */
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface MyAnnotation {
}

/**
 * @Retention - 指定注解的保留策略
 * RetentionPolicy取值：
 * - SOURCE: 源码级别，编译后丢弃
 * - CLASS: 字节码级别，运行时不可用（默认）
 * - RUNTIME: 运行时可用，可以通过反射获取
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RuntimeAnnotation {
}

/**
 * @Documented - 注解包含在JavaDoc中
 */
@Documented
public @interface DocumentedAnnotation {
}

/**
 * @Inherited - 注解可以被继承
 */
@Inherited
public @interface InheritedAnnotation {
}
```

### 2.3 注解的使用

```java
/**
 * 使用自定义注解
 */
@UserInfo(name = "张三", age = 25, hobbies = {"读书", "编程"})
public class User {
    
    @FieldInfo(description = "用户名", maxLength = 20)
    private String username;
    
    @MethodInfo(author = "张三", date = "2025-10-28")
    public void login() {
        System.out.println("用户登录");
    }
}

/**
 * 字段注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldInfo {
    String description();
    int maxLength() default 255;
}

/**
 * 方法注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
    String author();
    String date();
    int version() default 1;
}
```

### 2.4 注解处理器 (Annotation Processor)

```java
/**
 * 运行时注解处理器
 * Runtime Annotation Processor
 */
public class AnnotationProcessorDemo {
    
    public static void main(String[] args) {
        // 获取类上的注解
        Class<User> clazz = User.class;
        
        if (clazz.isAnnotationPresent(UserInfo.class)) {
            UserInfo userInfo = clazz.getAnnotation(UserInfo.class);
            System.out.println("用户名: " + userInfo.name());
            System.out.println("年龄: " + userInfo.age());
            System.out.println("爱好: " + Arrays.toString(userInfo.hobbies()));
        }
        
        // 获取方法上的注解
        try {
            Method method = clazz.getMethod("login");
            if (method.isAnnotationPresent(MethodInfo.class)) {
                MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
                System.out.println("作者: " + methodInfo.author());
                System.out.println("日期: " + methodInfo.date());
                System.out.println("版本: " + methodInfo.version());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        
        // 获取字段上的注解
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldInfo.class)) {
                FieldInfo fieldInfo = field.getAnnotation(FieldInfo.class);
                System.out.println("字段: " + field.getName());
                System.out.println("描述: " + fieldInfo.description());
                System.out.println("最大长度: " + fieldInfo.maxLength());
            }
        }
    }
}
```

---

## 3. 反射 (Reflection)

反射允许程序在运行时检查和操作类、方法、字段等。

### 3.1 获取Class对象

```java
/**
 * 获取Class对象的三种方式
 * Three Ways to Get Class Object
 */
public class GetClassDemo {
    
    public static void main(String[] args) throws ClassNotFoundException {
        // 方式1：通过对象的getClass()方法
        String str = "Hello";
        Class<?> clazz1 = str.getClass();
        
        // 方式2：通过类的.class属性
        Class<?> clazz2 = String.class;
        
        // 方式3：通过Class.forName()方法
        Class<?> clazz3 = Class.forName("java.lang.String");
        
        // 三种方式获取的是同一个Class对象
        System.out.println(clazz1 == clazz2); // true
        System.out.println(clazz2 == clazz3); // true
        
        // 获取类名
        System.out.println("类名: " + clazz1.getName()); // java.lang.String
        System.out.println("简单类名: " + clazz1.getSimpleName()); // String
    }
}
```

### 3.2 反射创建对象

```java
/**
 * 反射创建对象示例
 * Creating Objects via Reflection
 */
public class CreateObjectDemo {
    
    public static void main(String[] args) throws Exception {
        // 方式1：调用无参构造器
        Class<?> clazz = Person.class;
        Person person1 = (Person) clazz.newInstance(); // Java 9后已过时
        
        // 推荐方式：通过Constructor
        Constructor<?> constructor = clazz.getConstructor();
        Person person2 = (Person) constructor.newInstance();
        
        // 方式2：调用有参构造器
        Constructor<?> paramConstructor = 
            clazz.getConstructor(String.class, int.class);
        Person person3 = 
            (Person) paramConstructor.newInstance("张三", 25);
        
        // 调用私有构造器
        Constructor<?> privateConstructor = 
            clazz.getDeclaredConstructor(String.class);
        privateConstructor.setAccessible(true); // 设置可访问
        Person person4 = (Person) privateConstructor.newInstance("李四");
        
        System.out.println(person3);
    }
}

class Person {
    private String name;
    private int age;
    
    public Person() {}
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    private Person(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
```

### 3.3 反射操作字段

```java
/**
 * 反射操作字段示例
 * Field Operations via Reflection
 */
public class FieldReflectionDemo {
    
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Student.class;
        Student student = new Student("张三", 20, "S001");
        
        // 获取所有public字段（包括父类）
        Field[] publicFields = clazz.getFields();
        
        // 获取所有字段（不包括父类）
        Field[] allFields = clazz.getDeclaredFields();
        
        // 获取特定字段
        Field nameField = clazz.getDeclaredField("name");
        
        // 设置可访问（绕过private限制）
        nameField.setAccessible(true);
        
        // 读取字段值
        String name = (String) nameField.get(student);
        System.out.println("原始name: " + name);
        
        // 修改字段值
        nameField.set(student, "李四");
        System.out.println("修改后: " + student);
        
        // 打印所有字段
        for (Field field : allFields) {
            field.setAccessible(true);
            System.out.println(field.getName() + " = " + field.get(student));
        }
    }
}

class Student {
    private String name;
    private int age;
    private String studentId;
    
    public Student(String name, int age, String studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + 
               ", studentId='" + studentId + "'}";
    }
}
```

### 3.4 反射调用方法

```java
/**
 * 反射调用方法示例
 * Method Invocation via Reflection
 */
public class MethodReflectionDemo {
    
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Calculator.class;
        Calculator calc = new Calculator();
        
        // 获取public方法
        Method addMethod = clazz.getMethod("add", int.class, int.class);
        
        // 调用方法
        Object result = addMethod.invoke(calc, 10, 20);
        System.out.println("10 + 20 = " + result);
        
        // 调用私有方法
        Method privateMethod = clazz.getDeclaredMethod("multiply", int.class, int.class);
        privateMethod.setAccessible(true);
        Object result2 = privateMethod.invoke(calc, 5, 6);
        System.out.println("5 * 6 = " + result2);
        
        // 调用静态方法（不需要实例）
        Method staticMethod = clazz.getMethod("power", int.class, int.class);
        Object result3 = staticMethod.invoke(null, 2, 3); // 静态方法传null
        System.out.println("2^3 = " + result3);
        
        // 获取所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    private int multiply(int a, int b) {
        return a * b;
    }
    
    public static int power(int base, int exponent) {
        return (int) Math.pow(base, exponent);
    }
}
```

### 3.5 反射的应用场景

1. **框架开发**：Spring的IoC、AOP
2. **ORM框架**：Hibernate、MyBatis
3. **序列化/反序列化**：JSON、XML转换
4. **动态代理**：JDK动态代理
5. **单元测试**：JUnit、Mockito
6. **注解处理**：运行时读取注解信息

---

## 4. 动态代理 (Dynamic Proxy)

动态代理在运行时动态生成代理类，用于在不修改源代码的情况下增强功能。

### 4.1 JDK动态代理

JDK动态代理基于接口实现，被代理类必须实现接口。

```java
/**
 * JDK动态代理示例
 * JDK Dynamic Proxy Example
 */

// 1. 定义接口
interface UserService {
    void addUser(String username);
    String getUser(int id);
}

// 2. 实现类
class UserServiceImpl implements UserService {
    @Override
    public void addUser(String username) {
        System.out.println("添加用户: " + username);
    }
    
    @Override
    public String getUser(int id) {
        System.out.println("查询用户: " + id);
        return "User" + id;
    }
}

// 3. 创建InvocationHandler
class UserServiceHandler implements InvocationHandler {
    
    private Object target; // 被代理对象
    
    public UserServiceHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) 
            throws Throwable {
        // 前置增强
        System.out.println("===== 开始执行方法: " + method.getName() + " =====");
        long start = System.currentTimeMillis();
        
        // 调用原方法
        Object result = method.invoke(target, args);
        
        // 后置增强
        long end = System.currentTimeMillis();
        System.out.println("===== 方法执行完成，耗时: " + (end - start) + "ms =====");
        
        return result;
    }
}

// 4. 使用动态代理
public class JDKProxyDemo {
    public static void main(String[] args) {
        // 创建真实对象
        UserService userService = new UserServiceImpl();
        
        // 创建InvocationHandler
        InvocationHandler handler = new UserServiceHandler(userService);
        
        // 创建代理对象
        UserService proxy = (UserService) Proxy.newProxyInstance(
            userService.getClass().getClassLoader(), // 类加载器
            userService.getClass().getInterfaces(),  // 接口数组
            handler                                   // InvocationHandler
        );
        
        // 通过代理对象调用方法
        proxy.addUser("张三");
        System.out.println();
        String user = proxy.getUser(123);
        System.out.println("返回值: " + user);
    }
}
```

### 4.2 CGLIB动态代理

CGLIB基于继承实现，可以代理没有实现接口的类。

```java
/**
 * CGLIB动态代理示例
 * CGLIB Dynamic Proxy Example
 * 
 * 需要引入cglib依赖：
 * <dependency>
 *     <groupId>cglib</groupId>
 *     <artifactId>cglib</artifactId>
 *     <version>3.3.0</version>
 * </dependency>
 */

// 1. 目标类（不需要实现接口）
class OrderService {
    public void createOrder(String orderId) {
        System.out.println("创建订单: " + orderId);
    }
    
    public void deleteOrder(String orderId) {
        System.out.println("删除订单: " + orderId);
    }
}

// 2. 创建MethodInterceptor
class OrderServiceInterceptor implements MethodInterceptor {
    
    @Override
    public Object intercept(Object obj, Method method, Object[] args, 
                          MethodProxy proxy) throws Throwable {
        // 前置增强
        System.out.println("===== CGLIB前置增强 =====");
        
        // 调用原方法
        Object result = proxy.invokeSuper(obj, args);
        
        // 后置增强
        System.out.println("===== CGLIB后置增强 =====");
        
        return result;
    }
}

// 3. 使用CGLIB代理
public class CGLIBProxyDemo {
    public static void main(String[] args) {
        // 创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        
        // 设置父类
        enhancer.setSuperclass(OrderService.class);
        
        // 设置回调
        enhancer.setCallback(new OrderServiceInterceptor());
        
        // 创建代理对象
        OrderService proxy = (OrderService) enhancer.create();
        
        // 通过代理对象调用方法
        proxy.createOrder("ORDER001");
    }
}
```

### 4.3 JDK代理 vs CGLIB代理

| 维度 | JDK动态代理 | CGLIB动态代理 |
|------|------------|--------------|
| **实现方式** | 基于接口（Proxy） | 基于继承（字节码） |
| **要求** | 必须实现接口 | 不要求接口 |
| **性能** | 较快 | 较慢（创建时），调用时较快 |
| **final类/方法** | 无影响 | 不能代理 |
| **应用** | Spring AOP（有接口） | Spring AOP（无接口） |

---

## 5. 枚举 (Enum)

枚举是一种特殊的类，用于定义一组常量。

### 5.1 基本用法

```java
/**
 * 枚举基本用法
 * Basic Enum Usage
 */
public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER
}

public class EnumDemo {
    public static void main(String[] args) {
        // 使用枚举
        Season season = Season.SPRING;
        System.out.println(season); // SPRING
        
        // 枚举比较（使用==即可）
        if (season == Season.SPRING) {
            System.out.println("春天来了");
        }
        
        // switch语句
        switch (season) {
            case SPRING:
                System.out.println("春暖花开");
                break;
            case SUMMER:
                System.out.println("夏日炎炎");
                break;
            case AUTUMN:
                System.out.println("秋高气爽");
                break;
            case WINTER:
                System.out.println("冬雪皑皑");
                break;
        }
        
        // 枚举方法
        System.out.println("名称: " + season.name()); // SPRING
        System.out.println("序号: " + season.ordinal()); // 0
        
        // 遍历所有枚举值
        for (Season s : Season.values()) {
            System.out.println(s);
        }
        
        // 字符串转枚举
        Season winter = Season.valueOf("WINTER");
        System.out.println(winter); // WINTER
    }
}
```

### 5.2 枚举的高级用法

```java
/**
 * 枚举高级用法：带字段和方法
 * Advanced Enum Usage: With Fields and Methods
 */
public enum Status {
    
    SUCCESS(200, "成功"),
    NOT_FOUND(404, "未找到"),
    ERROR(500, "服务器错误");
    
    // 枚举字段
    private final int code;
    private final String message;
    
    // 枚举构造器（必须是private）
    private Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    // Getter方法
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    // 根据code获取枚举
    public static Status fromCode(int code) {
        for (Status status : Status.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return code + " - " + message;
    }
}

/**
 * 使用示例
 */
public class StatusDemo {
    public static void main(String[] args) {
        Status status = Status.SUCCESS;
        System.out.println(status); // 200 - 成功
        System.out.println("Code: " + status.getCode()); // 200
        System.out.println("Message: " + status.getMessage()); // 成功
        
        // 根据code查找
        Status error = Status.fromCode(500);
        System.out.println(error); // 500 - 服务器错误
    }
}
```

---

## 6. SPI机制 (Service Provider Interface)

SPI是Java提供的一种服务发现机制，用于动态加载服务实现。

### 6.1 SPI使用示例

```java
/**
 * 1. 定义服务接口
 */
public interface PaymentService {
    void pay(double amount);
}

/**
 * 2. 实现类1：支付宝支付
 */
public class AlipayService implements PaymentService {
    @Override
    public void pay(double amount) {
        System.out.println("支付宝支付: " + amount + "元");
    }
}

/**
 * 3. 实现类2：微信支付
 */
public class WechatPayService implements PaymentService {
    @Override
    public void pay(double amount) {
        System.out.println("微信支付: " + amount + "元");
    }
}

/**
 * 4. 配置文件
 * 在resources/META-INF/services/目录下创建文件：
 * 文件名：com.example.PaymentService（接口全限定名）
 * 文件内容：
 * com.example.AlipayService
 * com.example.WechatPayService
 */

/**
 * 5. 使用SPI加载服务
 */
public class SPIDemo {
    public static void main(String[] args) {
        // 使用ServiceLoader加载所有实现
        ServiceLoader<PaymentService> loader = 
            ServiceLoader.load(PaymentService.class);
        
        // 遍历所有实现
        for (PaymentService service : loader) {
            service.pay(100.0);
        }
    }
}
```

### 6.2 SPI的应用

- **JDBC驱动加载**：`DriverManager`自动加载数据库驱动
- **SLF4J日志框架**：自动发现日志实现（Logback、Log4j）
- **Spring框架**：SpringFactoriesLoader机制
- **Dubbo**：扩展点加载机制

---

## 7. 最佳实践 (Best Practices)

### 7.1 泛型最佳实践

1. **优先使用泛型类型**：避免原始类型
2. **使用有界类型参数**：提供更强的类型检查
3. **遵循PECS原则**：Producer Extends, Consumer Super
4. **不要使用泛型数组**：使用`List<T>`代替`T[]`

### 7.2 注解最佳实践

1. **选择合适的保留策略**：`@Retention`
2. **限制注解使用范围**：`@Target`
3. **提供默认值**：简化使用
4. **文档化注解**：使用`@Documented`

### 7.3 反射最佳实践

1. **避免过度使用**：影响性能和安全性
2. **缓存反射对象**：避免重复获取
3. **处理异常**：反射操作会抛出多种异常
4. **安全访问**：谨慎使用`setAccessible(true)`

---

## 8. 面试高频问题 (Frequently Asked Interview Questions)

### Q1: 什么是泛型？有什么好处？⭐⭐⭐⭐⭐

**答案：**

泛型是JDK 5引入的特性，允许在定义类、接口和方法时使用类型参数。

**好处：**
1. **类型安全**：编译时检查类型错误
2. **消除强制转换**：自动类型转换
3. **代码复用**：同一代码适用多种类型
4. **清晰易读**：明确指定类型

### Q2: 什么是类型擦除？⭐⭐⭐⭐⭐

**答案：**

类型擦除是Java泛型的实现机制，泛型信息只存在于编译期，编译后会被擦除为原始类型（Object或上界）。

**影响：**
- 运行时无法获取泛型类型
- 不能创建泛型数组
- 不能用于重载（编译后签名相同）

### Q3: `<? extends T>` 和 `<? super T>` 的区别？⭐⭐⭐⭐⭐

**答案：**

| 通配符 | 含义 | 读写 | 应用 |
|--------|------|------|------|
| `<? extends T>` | 上界通配符，T或其子类 | 只读 | 生产者（Producer） |
| `<? super T>` | 下界通配符，T或其父类 | 可写 | 消费者（Consumer) |

**PECS原则**：Producer Extends, Consumer Super

### Q4: 注解的作用是什么？⭐⭐⭐⭐

**答案：**

注解是一种元数据，为代码提供额外信息：
1. **编译检查**：`@Override`、`@Deprecated`
2. **代码生成**：Lombok
3. **运行时处理**：Spring的`@Component`、`@Autowired`
4. **文档生成**：JavaDoc

### Q5: 反射有什么缺点？⭐⭐⭐⭐

**答案：**

1. **性能开销**：比直接调用慢10-100倍
2. **安全问题**：破坏封装性，访问私有成员
3. **代码复杂**：需要处理大量异常
4. **内省开销**：增加运行时负担

### Q6: JDK动态代理和CGLIB代理的区别？⭐⭐⭐⭐⭐

**答案：** 见[4.3节](#43-jdk代理-vs-cglib代理)

### Q7: 枚举的优点是什么？⭐⭐⭐

**答案：**

1. **类型安全**：编译时检查
2. **单例保证**：JVM保证唯一性
3. **线程安全**：天然线程安全
4. **可扩展**：可以添加字段和方法
5. **高效**：比较使用`==`即可

### Q8: 什么是SPI？有什么应用？⭐⭐⭐

**答案：**

SPI（Service Provider Interface）是Java提供的服务发现机制，用于解耦接口和实现。

**应用：**
- JDBC驱动加载
- SLF4J日志框架
- Spring Boot自动配置
- Dubbo扩展点

---

## 总结 (Summary)

本章介绍了Java的高级特性：

- ✅ **泛型**：类型安全，消除强制转换
- ✅ **注解**：元数据，增强代码功能
- ✅ **反射**：运行时操作类，提供灵活性
- ✅ **动态代理**：不修改源码增强功能
- ✅ **枚举**：类型安全的常量定义
- ✅ **SPI**：服务发现机制，解耦实现

这些高级特性是框架开发的基础，建议：

1. **理解原理**：深入理解类型擦除、反射机制
2. **实践应用**：尝试实现简单的依赖注入容器
3. **阅读源码**：学习Spring、MyBatis的实现
4. **性能考量**：权衡灵活性和性能

---

**下一篇：** [04 - 函数式编程 →](./04-函数式编程.md)

**返回目录：** [Java 语言基础导航 ←](./README.md)

