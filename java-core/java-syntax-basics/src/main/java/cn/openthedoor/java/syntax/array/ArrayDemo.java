package cn.openthedoor.java.syntax.array;

import java.util.Arrays;

/**
 * 数组示例
 * Array Example
 * 
 * 学习目标：
 * 1. 掌握数组的声明和初始化
 * 2. 掌握数组的遍历
 * 3. 掌握多维数组
 * 4. 掌握Arrays工具类
 * 5. 了解数组的常见操作
 * 
 * @author OpenTheDoor
 */
public class ArrayDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 数组演示 ===\n");
        
        // 1. 数组声明和初始化 (Array Declaration and Initialization)
        demonstrateArrayInitialization();
        
        // 2. 数组遍历 (Array Traversal)
        demonstrateArrayTraversal();
        
        // 3. 数组操作 (Array Operations)
        demonstrateArrayOperations();
        
        // 4. 多维数组 (Multidimensional Arrays)
        demonstrateMultidimensionalArrays();
        
        // 5. Arrays工具类 (Arrays Utility Class)
        demonstrateArraysUtility();
        
        // 6. 数组实战 (Practical Array Operations)
        demonstratePracticalOperations();
    }
    
    /**
     * 数组声明和初始化
     * Array Declaration and Initialization
     */
    private static void demonstrateArrayInitialization() {
        System.out.println("--- 数组声明和初始化 ---");
        
        // 方式1：声明后分配空间
        int[] arr1 = new int[5]; // 默认值为0
        System.out.println("方式1 - 声明后分配: " + Arrays.toString(arr1));
        
        // 方式2：声明并初始化
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("方式2 - 声明并初始化: " + Arrays.toString(arr2));
        
        // 方式3：使用new关键字初始化
        int[] arr3 = new int[]{10, 20, 30, 40, 50};
        System.out.println("方式3 - new关键字: " + Arrays.toString(arr3));
        
        // 不同类型的数组 (Different types of arrays)
        double[] doubleArr = {1.1, 2.2, 3.3};
        String[] stringArr = {"apple", "banana", "orange"};
        boolean[] booleanArr = {true, false, true};
        
        System.out.println("double数组: " + Arrays.toString(doubleArr));
        System.out.println("String数组: " + Arrays.toString(stringArr));
        System.out.println("boolean数组: " + Arrays.toString(booleanArr));
        
        // 数组的默认值 (Default values)
        System.out.println("\n数组的默认值:");
        int[] intArr = new int[3];
        double[] dArr = new double[3];
        boolean[] bArr = new boolean[3];
        String[] sArr = new String[3];
        
        System.out.println("int默认值: " + Arrays.toString(intArr)); // [0, 0, 0]
        System.out.println("double默认值: " + Arrays.toString(dArr)); // [0.0, 0.0, 0.0]
        System.out.println("boolean默认值: " + Arrays.toString(bArr)); // [false, false, false]
        System.out.println("String默认值: " + Arrays.toString(sArr)); // [null, null, null]
        
        System.out.println();
    }
    
    /**
     * 数组遍历
     * Array Traversal
     */
    private static void demonstrateArrayTraversal() {
        System.out.println("--- 数组遍历 ---");
        
        int[] arr = {10, 20, 30, 40, 50};
        
        // 方式1：for循环 (for loop)
        System.out.println("方式1 - for循环:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        
        // 方式2：增强for循环 (enhanced for loop)
        System.out.println("\n方式2 - 增强for循环:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // 方式3：Arrays.toString() (Arrays.toString())
        System.out.println("\n方式3 - Arrays.toString():");
        System.out.println(Arrays.toString(arr));
        
        // 方式4：Java 8 Stream (Java 8 Stream)
        System.out.println("\n方式4 - Stream (Java 8+):");
        Arrays.stream(arr).forEach(num -> System.out.print(num + " "));
        System.out.println();
        
        // 倒序遍历 (Reverse traversal)
        System.out.println("\n倒序遍历:");
        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        
        System.out.println();
    }
    
    /**
     * 数组操作
     * Array Operations
     */
    private static void demonstrateArrayOperations() {
        System.out.println("--- 数组操作 ---");
        
        int[] arr = {5, 2, 8, 1, 9, 3, 7};
        
        // 1. 数组长度 (Array length)
        System.out.println("数组长度: " + arr.length);
        
        // 2. 访问元素 (Access elements)
        System.out.println("第一个元素: " + arr[0]);
        System.out.println("最后一个元素: " + arr[arr.length - 1]);
        
        // 3. 修改元素 (Modify elements)
        arr[0] = 100;
        System.out.println("修改后的数组: " + Arrays.toString(arr));
        arr[0] = 5; // 恢复
        
        // 4. 查找元素 (Search element)
        int target = 8;
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                index = i;
                break;
            }
        }
        System.out.println("\n元素" + target + "的索引: " + index);
        
        // 5. 最大值和最小值 (Max and Min)
        int max = arr[0];
        int min = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
            if (num < min) min = num;
        }
        System.out.println("最大值: " + max);
        System.out.println("最小值: " + min);
        
        // 6. 求和与平均值 (Sum and Average)
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        double average = (double) sum / arr.length;
        System.out.println("总和: " + sum);
        System.out.println("平均值: " + average);
        
        // 7. 数组复制 (Array copy)
        System.out.println("\n数组复制:");
        int[] copy1 = arr; // 引用复制，指向同一数组
        int[] copy2 = arr.clone(); // 浅复制，创建新数组
        int[] copy3 = Arrays.copyOf(arr, arr.length); // 使用Arrays工具类
        
        System.out.println("原数组: " + Arrays.toString(arr));
        copy1[0] = 999;
        System.out.println("引用复制后原数组: " + Arrays.toString(arr)); // 已改变
        arr[0] = 5; // 恢复
        
        copy2[0] = 888;
        System.out.println("clone后原数组: " + Arrays.toString(arr)); // 未改变
        
        System.out.println();
    }
    
    /**
     * 多维数组
     * Multidimensional Arrays
     */
    private static void demonstrateMultidimensionalArrays() {
        System.out.println("--- 多维数组 ---");
        
        // 二维数组声明和初始化 (2D array declaration and initialization)
        System.out.println("二维数组:");
        
        // 方式1：声明后分配空间
        int[][] matrix1 = new int[3][4]; // 3行4列
        
        // 方式2：声明并初始化
        int[][] matrix2 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        
        // 打印二维数组
        System.out.println("方式2初始化的二维数组:");
        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[i].length; j++) {
                System.out.printf("%4d", matrix2[i][j]);
            }
            System.out.println();
        }
        
        // 使用增强for循环
        System.out.println("\n使用增强for循环:");
        for (int[] row : matrix2) {
            for (int num : row) {
                System.out.printf("%4d", num);
            }
            System.out.println();
        }
        
        // 不规则数组 (Jagged array)
        System.out.println("\n不规则数组:");
        int[][] jagged = new int[3][];
        jagged[0] = new int[]{1, 2};
        jagged[1] = new int[]{3, 4, 5};
        jagged[2] = new int[]{6, 7, 8, 9};
        
        for (int[] row : jagged) {
            System.out.println(Arrays.toString(row));
        }
        
        // 三维数组 (3D array)
        System.out.println("\n三维数组:");
        int[][][] cube = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}}
        };
        
        System.out.println("三维数组的第一个元素: " + cube[0][0][0]);
        System.out.println("三维数组的最后一个元素: " + cube[1][1][1]);
        
        System.out.println();
    }
    
    /**
     * Arrays工具类
     * Arrays Utility Class
     */
    private static void demonstrateArraysUtility() {
        System.out.println("--- Arrays 工具类 ---");
        
        int[] arr = {5, 2, 8, 1, 9, 3, 7};
        System.out.println("原数组: " + Arrays.toString(arr));
        
        // 1. 排序 (Sort)
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        System.out.println("\n排序后: " + Arrays.toString(sorted));
        
        // 2. 二分查找 (Binary Search) - 必须先排序
        int target = 7;
        int index = Arrays.binarySearch(sorted, target);
        System.out.println("\n二分查找" + target + ": 索引=" + index);
        
        // 3. 填充 (Fill)
        int[] filled = new int[5];
        Arrays.fill(filled, 10);
        System.out.println("\n填充数组: " + Arrays.toString(filled));
        
        // 4. 比较 (Equals)
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        int[] arr3 = {1, 2, 4};
        System.out.println("\n数组比较:");
        System.out.println("arr1.equals(arr2): " + Arrays.equals(arr1, arr2)); // true
        System.out.println("arr1.equals(arr3): " + Arrays.equals(arr1, arr3)); // false
        
        // 5. 复制 (Copy)
        int[] original = {1, 2, 3, 4, 5};
        int[] copy1 = Arrays.copyOf(original, 3); // 复制前3个
        int[] copy2 = Arrays.copyOf(original, 7); // 长度增加，新元素为0
        int[] copy3 = Arrays.copyOfRange(original, 1, 4); // 复制索引1-3
        
        System.out.println("\n数组复制:");
        System.out.println("原数组: " + Arrays.toString(original));
        System.out.println("复制前3个: " + Arrays.toString(copy1));
        System.out.println("复制并扩展到7: " + Arrays.toString(copy2));
        System.out.println("复制索引1-3: " + Arrays.toString(copy3));
        
        // 6. Stream操作 (Stream operations - Java 8+)
        System.out.println("\nStream操作 (Java 8+):");
        int[] numbers = {1, 2, 3, 4, 5};
        int sum = Arrays.stream(numbers).sum();
        double average = Arrays.stream(numbers).average().orElse(0);
        int max = Arrays.stream(numbers).max().orElse(0);
        
        System.out.println("总和: " + sum);
        System.out.println("平均值: " + average);
        System.out.println("最大值: " + max);
        
        System.out.println();
    }
    
    /**
     * 数组实战操作
     * Practical Array Operations
     */
    private static void demonstratePracticalOperations() {
        System.out.println("--- 数组实战操作 ---");
        
        // 1. 数组反转 (Reverse array)
        System.out.println("1. 数组反转:");
        int[] arr1 = {1, 2, 3, 4, 5};
        System.out.println("原数组: " + Arrays.toString(arr1));
        reverseArray(arr1);
        System.out.println("反转后: " + Arrays.toString(arr1));
        
        // 2. 数组去重 (Remove duplicates)
        System.out.println("\n2. 数组去重:");
        int[] arr2 = {1, 2, 2, 3, 3, 3, 4, 5, 5};
        System.out.println("原数组: " + Arrays.toString(arr2));
        int[] unique = removeDuplicates(arr2);
        System.out.println("去重后: " + Arrays.toString(unique));
        
        // 3. 数组合并 (Merge arrays)
        System.out.println("\n3. 数组合并:");
        int[] arr3 = {1, 2, 3};
        int[] arr4 = {4, 5, 6};
        int[] merged = mergeArrays(arr3, arr4);
        System.out.println("arr1: " + Arrays.toString(arr3));
        System.out.println("arr2: " + Arrays.toString(arr4));
        System.out.println("合并: " + Arrays.toString(merged));
        
        // 4. 数组旋转 (Rotate array)
        System.out.println("\n4. 数组旋转:");
        int[] arr5 = {1, 2, 3, 4, 5};
        System.out.println("原数组: " + Arrays.toString(arr5));
        rotateArray(arr5, 2);
        System.out.println("右旋转2位: " + Arrays.toString(arr5));
        
        // 5. 寻找数组中第二大的数 (Find second largest)
        System.out.println("\n5. 寻找第二大的数:");
        int[] arr6 = {5, 2, 8, 1, 9, 3, 7};
        System.out.println("数组: " + Arrays.toString(arr6));
        int secondLargest = findSecondLargest(arr6);
        System.out.println("第二大的数: " + secondLargest);
        
        System.out.println();
    }
    
    /**
     * 反转数组
     * Reverse array
     */
    private static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            // 交换元素
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            
            left++;
            right--;
        }
    }
    
    /**
     * 数组去重（需要先排序）
     * Remove duplicates from sorted array
     */
    private static int[] removeDuplicates(int[] arr) {
        if (arr.length == 0) return arr;
        
        Arrays.sort(arr); // 先排序
        
        int[] temp = new int[arr.length];
        int j = 0;
        
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != arr[i + 1]) {
                temp[j++] = arr[i];
            }
        }
        temp[j++] = arr[arr.length - 1]; // 添加最后一个元素
        
        return Arrays.copyOf(temp, j);
    }
    
    /**
     * 合并两个数组
     * Merge two arrays
     */
    private static int[] mergeArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
    
    /**
     * 旋转数组
     * Rotate array
     */
    private static void rotateArray(int[] arr, int k) {
        k = k % arr.length;
        reverseArray(arr, 0, arr.length - 1);
        reverseArray(arr, 0, k - 1);
        reverseArray(arr, k, arr.length - 1);
    }
    
    private static void reverseArray(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
    /**
     * 寻找第二大的数
     * Find second largest number
     */
    private static int findSecondLargest(int[] arr) {
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        
        for (int num : arr) {
            if (num > max) {
                secondMax = max;
                max = num;
            } else if (num > secondMax && num < max) {
                secondMax = num;
            }
        }
        
        return secondMax;
    }
    
}

