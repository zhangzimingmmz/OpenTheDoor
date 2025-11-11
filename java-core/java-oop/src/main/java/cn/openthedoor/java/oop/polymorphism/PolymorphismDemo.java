package cn.openthedoor.java.oop.polymorphism;

/**
 * 多态示例
 * Polymorphism Example
 * 
 * 学习目标：
 * 1. 理解多态的概念
 * 2. 掌握向上转型和向下转型
 * 3. 理解动态绑定
 * 4. 掌握instanceof运算符
 * 
 * @author OpenTheDoor
 */
public class PolymorphismDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 多态演示 ===\n");
        
        // 1. 多态的基本使用
        demonstrateBasicPolymorphism();
        
        // 2. 向上转型和向下转型
        demonstrateTypeCasting();
        
        // 3. instanceof运算符
        demonstrateInstanceof();
        
        // 4. 多态的实际应用
        demonstratePracticalUse();
    }
    
    /**
     * 多态的基本使用
     * Basic polymorphism
     */
    private static void demonstrateBasicPolymorphism() {
        System.out.println("--- 多态的基本使用 ---");
        
        // 父类引用指向子类对象（向上转型）
        Animal animal1 = new Dog();
        Animal animal2 = new Cat();
        Animal animal3 = new Bird();
        
        // 同一个方法调用，不同的实现（运行时多态）
        animal1.makeSound(); // 输出：汪汪汪
        animal2.makeSound(); // 输出：喵喵喵
        animal3.makeSound(); // 输出：叽叽叽
        
        System.out.println("\n多态的好处:");
        System.out.println("1. 提高代码的灵活性和可扩展性");
        System.out.println("2. 降低代码的耦合度");
        System.out.println("3. 支持统一的接口调用");
        
        System.out.println();
    }
    
    /**
     * 向上转型和向下转型
     * Upcasting and Downcasting
     */
    private static void demonstrateTypeCasting() {
        System.out.println("--- 向上转型和向下转型 ---");
        
        // 向上转型（自动转换）- Upcasting
        System.out.println("向上转型 (Upcasting):");
        Animal animal = new Dog(); // 自动向上转型
        animal.makeSound();
        animal.eat();
        // animal.bark(); // 编译错误！父类引用不能调用子类特有方法
        
        // 向下转型（强制转换）- Downcasting
        System.out.println("\n向下转型 (Downcasting):");
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal; // 强制向下转型
            dog.bark(); // 现在可以调用子类特有方法了
        }
        
        // ⚠️ 错误的向下转型
        System.out.println("\n⚠️ 错误的向下转型:");
        Animal animal2 = new Cat();
        try {
            Dog dog2 = (Dog) animal2; // ClassCastException!
            dog2.bark();
        } catch (ClassCastException e) {
            System.out.println("转型失败: Cat不能转换为Dog");
        }
        
        System.out.println();
    }
    
    /**
     * instanceof运算符
     * instanceof operator
     */
    private static void demonstrateInstanceof() {
        System.out.println("--- instanceof 运算符 ---");
        
        Animal animal = new Dog();
        
        System.out.println("animal instanceof Dog: " + (animal instanceof Dog));
        System.out.println("animal instanceof Cat: " + (animal instanceof Cat));
        System.out.println("animal instanceof Animal: " + (animal instanceof Animal));
        System.out.println("animal instanceof Object: " + (animal instanceof Object));
        
        System.out.println("\n安全的向下转型:");
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
            System.out.println("成功转换为Dog");
            dog.bark();
        }
        
        System.out.println();
    }
    
    /**
     * 多态的实际应用
     * Practical use of polymorphism
     */
    private static void demonstratePracticalUse() {
        System.out.println("--- 多态的实际应用 ---");
        
        // 多态数组
        System.out.println("1. 多态数组:");
        Animal[] animals = {
            new Dog(),
            new Cat(),
            new Bird()
        };
        
        for (Animal animal : animals) {
            animal.makeSound();
        }
        
        // 多态参数
        System.out.println("\n2. 多态参数:");
        feedAnimal(new Dog());
        feedAnimal(new Cat());
        feedAnimal(new Bird());
        
        System.out.println();
    }
    
    /**
     * 喂养动物（多态参数）
     * Feed animal (polymorphic parameter)
     */
    private static void feedAnimal(Animal animal) {
        System.out.print("正在喂养: ");
        animal.eat();
    }
    
}

/**
 * 父类：动物
 * Parent class: Animal
 */
class Animal {
    
    public void makeSound() {
        System.out.println("动物发出声音");
    }
    
    public void eat() {
        System.out.println("动物正在吃东西");
    }
}

/**
 * 子类：狗
 * Child class: Dog
 */
class Dog extends Animal {
    
    @Override
    public void makeSound() {
        System.out.println("汪汪汪！");
    }
    
    @Override
    public void eat() {
        System.out.println("狗正在吃狗粮");
    }
    
    // 子类特有方法
    public void bark() {
        System.out.println("狗在叫：Woof!");
    }
}

/**
 * 子类：猫
 * Child class: Cat
 */
class Cat extends Animal {
    
    @Override
    public void makeSound() {
        System.out.println("喵喵喵！");
    }
    
    @Override
    public void eat() {
        System.out.println("猫正在吃猫粮");
    }
    
    // 子类特有方法
    public void meow() {
        System.out.println("猫在叫：Meow!");
    }
}

/**
 * 子类：鸟
 * Child class: Bird
 */
class Bird extends Animal {
    
    @Override
    public void makeSound() {
        System.out.println("叽叽叽！");
    }
    
    @Override
    public void eat() {
        System.out.println("鸟正在吃鸟食");
    }
    
    // 子类特有方法
    public void fly() {
        System.out.println("鸟在飞翔");
    }
}

