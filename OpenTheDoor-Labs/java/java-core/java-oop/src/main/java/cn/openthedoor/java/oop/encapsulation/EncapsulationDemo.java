package cn.openthedoor.java.oop.encapsulation;

/**
 * 封装示例
 * Encapsulation Example
 * 
 * 学习目标：
 * 1. 理解封装的概念和作用
 * 2. 掌握访问修饰符（private, default, protected, public）
 * 3. 掌握Getter和Setter的使用
 * 4. 理解数据验证的重要性
 * 
 * @author OpenTheDoor
 */
public class EncapsulationDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 封装演示 ===\n");
        
        // 1. 不使用封装的问题
        demonstrateWithoutEncapsulation();
        
        // 2. 使用封装的好处
        demonstrateWithEncapsulation();
        
        // 3. 访问修饰符
        demonstrateAccessModifiers();
    }
    
    /**
     * 不使用封装的问题
     * Problems without encapsulation
     */
    private static void demonstrateWithoutEncapsulation() {
        System.out.println("--- 不使用封装的问题 ---");
        
        BadPerson person = new BadPerson();
        person.name = "张三";
        person.age = 25;
        
        System.out.println("正常情况: " + person);
        
        // 问题：可以直接修改为非法值
        person.age = -10; // 年龄为负数，不合理！
        System.out.println("⚠️ 可以设置非法值: " + person);
        
        System.out.println();
    }
    
    /**
     * 使用封装的好处
     * Benefits of encapsulation
     */
    private static void demonstrateWithEncapsulation() {
        System.out.println("--- 使用封装的好处 ---");
        
        GoodPerson person = new GoodPerson();
        person.setName("李四");
        person.setAge(30);
        
        System.out.println("正常情况: " + person);
        
        // 好处：通过setter进行数据验证
        System.out.println("\n尝试设置非法值:");
        person.setAge(-10); // 会被拒绝
        person.setAge(200);  // 会被拒绝
        
        System.out.println("数据仍然有效: " + person);
        
        System.out.println("\n封装的优点:");
        System.out.println("1. 隐藏实现细节");
        System.out.println("2. 数据验证和保护");
        System.out.println("3. 提高代码可维护性");
        System.out.println("4. 降低耦合度");
        
        System.out.println();
    }
    
    /**
     * 访问修饰符
     * Access modifiers
     */
    private static void demonstrateAccessModifiers() {
        System.out.println("--- 访问修饰符 ---");
        
        System.out.println("Java访问修饰符及其范围:");
        System.out.println("┌─────────────┬────────┬────────┬────────┬────────┐");
        System.out.println("│  修饰符      │ 同一类  │ 同一包  │ 子类    │ 任何类  │");
        System.out.println("├─────────────┼────────┼────────┼────────┼────────┤");
        System.out.println("│  private    │   ✓    │   ✗    │   ✗    │   ✗    │");
        System.out.println("│  default    │   ✓    │   ✓    │   ✗    │   ✗    │");
        System.out.println("│  protected  │   ✓    │   ✓    │   ✓    │   ✗    │");
        System.out.println("│  public     │   ✓    │   ✓    │   ✓    │   ✓    │");
        System.out.println("└─────────────┴────────┴────────┴────────┴────────┘");
        
        AccessModifierDemo demo = new AccessModifierDemo();
        demo.testAccess();
        
        System.out.println();
    }
    
}

/**
 * 不好的示例：不使用封装
 * Bad example: Without encapsulation
 */
class BadPerson {
    public String name;
    public int age;
    
    @Override
    public String toString() {
        return "BadPerson{name='" + name + "', age=" + age + "}";
    }
}

/**
 * 好的示例：使用封装
 * Good example: With encapsulation
 */
class GoodPerson {
    // 私有成员变量 - Private member variables
    private String name;
    private int age;
    
    // 公共的Getter方法
    public String getName() {
        return name;
    }
    
    // 公共的Setter方法（带数据验证）
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("⚠️ 姓名不能为空");
            return;
        }
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            System.out.println("⚠️ 年龄必须在0-150之间，当前值: " + age);
            return;
        }
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "GoodPerson{name='" + name + "', age=" + age + "}";
    }
}

/**
 * 访问修饰符演示类
 * Access modifier demonstration class
 */
class AccessModifierDemo {
    
    private String privateField = "私有字段";
    String defaultField = "默认字段";      // 默认访问权限（包内可见）
    protected String protectedField = "受保护字段";
    public String publicField = "公共字段";
    
    public void testAccess() {
        // 在同一类中，所有修饰符都可以访问
        System.out.println("\n同一类中访问:");
        System.out.println("  private: " + privateField);
        System.out.println("  default: " + defaultField);
        System.out.println("  protected: " + protectedField);
        System.out.println("  public: " + publicField);
    }
}

