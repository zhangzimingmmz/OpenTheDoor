package cn.openthedoor.java.oop.inheritance;

/**
 * 继承示例
 * Inheritance Example
 * 
 * 学习目标：
 * 1. 理解继承的概念和作用
 * 2. 掌握extends关键字
 * 3. 掌握super关键字
 * 4. 理解方法重写（Override）
 * 5. 理解Object类
 * 
 * @author OpenTheDoor
 */
public class InheritanceDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 继承演示 ===\n");
        
        // 1. 基本继承
        demonstrateBasicInheritance();
        
        // 2. 方法重写
        demonstrateMethodOverride();
        
        // 3. super关键字
        demonstrateSuperKeyword();
        
        // 4. 构造器调用链
        demonstrateConstructorChain();
    }
    
    /**
     * 基本继承
     * Basic inheritance
     */
    private static void demonstrateBasicInheritance() {
        System.out.println("--- 基本继承 ---");
        
        // 创建父类对象
        Animal animal = new Animal();
        animal.setName("动物");
        animal.setAge(5);
        System.out.println(animal);
        animal.eat();
        animal.sleep();
        
        System.out.println();
        
        // 创建子类对象
        Dog dog = new Dog();
        dog.setName("旺财");
        dog.setAge(3);
        dog.setBreed("金毛");
        System.out.println(dog);
        dog.eat();    // 继承自父类
        dog.sleep();  // 继承自父类
        dog.bark();   // 子类特有方法
        
        System.out.println();
    }
    
    /**
     * 方法重写
     * Method override
     */
    private static void demonstrateMethodOverride() {
        System.out.println("--- 方法重写 ---");
        
        Animal animal = new Animal();
        animal.setName("普通动物");
        animal.eat(); // 调用父类方法
        
        System.out.println();
        
        Dog dog = new Dog();
        dog.setName("小黑");
        dog.eat(); // 调用重写后的方法
        
        Cat cat = new Cat();
        cat.setName("小白");
        cat.eat(); // 调用重写后的方法
        
        System.out.println();
    }
    
    /**
     * super关键字
     * super keyword
     */
    private static void demonstrateSuperKeyword() {
        System.out.println("--- super 关键字 ---");
        
        System.out.println("super的三种用法:");
        System.out.println("1. super.成员变量 - 访问父类成员变量");
        System.out.println("2. super.方法() - 调用父类方法");
        System.out.println("3. super() - 调用父类构造器");
        
        System.out.println();
        
        Dog dog = new Dog("大黄", 4, "拉布拉多");
        dog.showInfo();
        
        System.out.println();
    }
    
    /**
     * 构造器调用链
     * Constructor chain
     */
    private static void demonstrateConstructorChain() {
        System.out.println("--- 构造器调用链 ---");
        
        System.out.println("创建Dog对象时的构造器调用顺序:");
        Dog dog = new Dog("小花", 2, "哈士奇");
        
        System.out.println("\n继承的特点:");
        System.out.println("1. Java只支持单继承（一个类只能继承一个父类）");
        System.out.println("2. Java支持多层继承");
        System.out.println("3. 所有类都继承自Object类");
        System.out.println("4. 子类继承父类的所有非private成员");
        System.out.println("5. 子类不能继承父类的构造器");
        
        System.out.println();
    }
    
}

/**
 * 父类：动物
 * Parent class: Animal
 */
class Animal {
    
    // 成员变量
    private String name;
    private int age;
    
    // 无参构造器
    public Animal() {
        System.out.println("Animal无参构造器被调用");
    }
    
    // 有参构造器
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Animal有参构造器被调用");
    }
    
    // Getter和Setter
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    // 成员方法
    public void eat() {
        System.out.println(name + " 正在吃东西");
    }
    
    public void sleep() {
        System.out.println(name + " 正在睡觉");
    }
    
    @Override
    public String toString() {
        return "Animal{name='" + name + "', age=" + age + "}";
    }
}

/**
 * 子类：狗
 * Child class: Dog
 */
class Dog extends Animal {
    
    // 子类特有属性
    private String breed; // 品种
    
    // 无参构造器
    public Dog() {
        super(); // 调用父类无参构造器（可以省略，编译器会自动添加）
        System.out.println("Dog无参构造器被调用");
    }
    
    // 有参构造器
    public Dog(String name, int age, String breed) {
        super(name, age); // 显式调用父类有参构造器
        this.breed = breed;
        System.out.println("Dog有参构造器被调用");
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    // 方法重写 (Method Override)
    // @Override注解表示这是一个重写的方法
    @Override
    public void eat() {
        System.out.println(getName() + " (狗) 正在吃狗粮");
    }
    
    // 子类特有方法
    public void bark() {
        System.out.println(getName() + " 正在叫：汪汪汪！");
    }
    
    // 使用super调用父类方法
    public void showInfo() {
        System.out.println("使用super调用父类方法:");
        super.eat(); // 调用父类的eat()方法
        this.eat();  // 调用当前类的eat()方法
    }
    
    @Override
    public String toString() {
        return "Dog{name='" + getName() + "', age=" + getAge() + 
               ", breed='" + breed + "'}";
    }
}

/**
 * 子类：猫
 * Child class: Cat
 */
class Cat extends Animal {
    
    public Cat() {
        System.out.println("Cat构造器被调用");
    }
    
    // 方法重写
    @Override
    public void eat() {
        System.out.println(getName() + " (猫) 正在吃猫粮");
    }
    
    // 子类特有方法
    public void meow() {
        System.out.println(getName() + " 正在叫：喵喵喵！");
    }
}

