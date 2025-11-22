package cn.openthedoor.java.oop.basics;

/**
 * 类与对象示例
 * Class and Object Example
 * 
 * 学习目标：
 * 1. 理解类和对象的概念
 * 2. 掌握类的定义（成员变量、构造器、方法）
 * 3. 掌握this关键字的使用
 * 4. 理解static关键字
 * 
 * @author OpenTheDoor
 */
public class ClassAndObjectDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 类与对象演示 ===\n");
        
        // 1. 创建对象 (Create objects)
        demonstrateObjectCreation();
        
        // 2. 访问成员 (Access members)
        demonstrateMemberAccess();
        
        // 3. this关键字 (this keyword)
        demonstrateThisKeyword();
        
        // 4. static关键字 (static keyword)
        demonstrateStaticKeyword();
    }
    
    /**
     * 创建对象
     * Create objects
     */
    private static void demonstrateObjectCreation() {
        System.out.println("--- 创建对象 ---");
        
        // 使用无参构造器
        Student student1 = new Student();
        System.out.println("student1: " + student1);
        
        // 使用有参构造器
        Student student2 = new Student("张三", 20);
        System.out.println("student2: " + student2);
        
        // 使用全参构造器
        Student student3 = new Student("李四", 22, "2021001");
        System.out.println("student3: " + student3);
        
        System.out.println();
    }
    
    /**
     * 访问成员
     * Access members
     */
    private static void demonstrateMemberAccess() {
        System.out.println("--- 访问成员 ---");
        
        Student student = new Student("王五", 21, "2021002");
        
        // 访问成员变量（通过getter）
        System.out.println("姓名: " + student.getName());
        System.out.println("年龄: " + student.getAge());
        System.out.println("学号: " + student.getStudentId());
        
        // 修改成员变量（通过setter）
        student.setAge(22);
        System.out.println("修改后年龄: " + student.getAge());
        
        // 调用成员方法
        student.study();
        student.introduce();
        
        System.out.println();
    }
    
    /**
     * this关键字
     * this keyword
     */
    private static void demonstrateThisKeyword() {
        System.out.println("--- this 关键字 ---");
        
        // this用于区分成员变量和局部变量
        // this用于调用其他构造器
        // this用于返回当前对象
        
        Student student = new Student("赵六", 23);
        
        // 链式调用（方法返回this）
        student.setName("赵六").setAge(24).setStudentId("2021003");
        System.out.println("链式调用后: " + student);
        
        System.out.println();
    }
    
    /**
     * static关键字
     * static keyword
     */
    private static void demonstrateStaticKeyword() {
        System.out.println("--- static 关键字 ---");
        
        // static成员属于类，不属于对象
        System.out.println("创建前学生总数: " + Student.getStudentCount());
        
        new Student("A", 20);
        new Student("B", 21);
        new Student("C", 22);
        
        System.out.println("创建后学生总数: " + Student.getStudentCount());
        
        // static常量
        System.out.println("学校名称: " + Student.SCHOOL_NAME);
        
        System.out.println();
    }
    
}

/**
 * 学生类
 * Student Class
 */
class Student {
    
    // 成员变量（实例变量）- Instance Variables
    private String name;        // 姓名
    private int age;            // 年龄
    private String studentId;   // 学号
    
    // 静态变量（类变量）- Static Variables
    private static int studentCount = 0; // 学生总数
    
    // 静态常量 - Static Constant
    public static final String SCHOOL_NAME = "Java大学";
    
    // 无参构造器 (No-Argument Constructor)
    public Student() {
        studentCount++;
        System.out.println("调用无参构造器");
    }
    
    // 有参构造器 (Parameterized Constructor)
    public Student(String name, int age) {
        this(); // 调用无参构造器
        this.name = name;
        this.age = age;
        System.out.println("调用两参构造器");
    }
    
    // 全参构造器 (All-Args Constructor)
    public Student(String name, int age, String studentId) {
        this(name, age); // 调用两参构造器
        this.studentId = studentId;
        System.out.println("调用全参构造器");
    }
    
    // Getter 方法
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    // Setter 方法（返回this支持链式调用）
    public Student setName(String name) {
        this.name = name;
        return this; // 返回当前对象
    }
    
    public Student setAge(int age) {
        this.age = age;
        return this;
    }
    
    public Student setStudentId(String studentId) {
        this.studentId = studentId;
        return this;
    }
    
    // 成员方法 - Instance Method
    public void study() {
        System.out.println(name + " 正在学习...");
    }
    
    public void introduce() {
        System.out.println("大家好，我是 " + name + "，今年 " + age + " 岁");
    }
    
    // 静态方法 - Static Method
    public static int getStudentCount() {
        return studentCount;
    }
    
    // toString方法
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", studentId='" + studentId + "'}";
    }
}

