package com.lpy.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/*
 * 一、Stream API 的操作步骤：
 * 
 * 1. 创建 Stream
 * 
 * 2. 中间操作
 * 
 * 3. 终止操作(终端操作)
 */
public class TestStreamaAPI {

    //1. 创建 Stream
    @Test
    public void test1() {
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        Stream<String> stringStream = list.parallelStream(); //获取一个并行流

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        //4. 创建无限流
        //迭代
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 2).limit(10);
        iterate.forEach(System.out::println);

        //生成  Supplier 不需要参数 直接生成就好
        Stream<Double> generate = Stream.generate(Math::random).limit(10);
//        Stream<Integer> generate1 = Stream.generate(() -> (int) (Math.random() * 100)).limit(10);
        generate.forEach(System.out::println);
    }

    //2. 中间操作
    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );
    /**
     * 	筛选与切片
     * 	filter——接收 Lambda ， 从流中排除某些元素。
     * 	limit——截断流，使其元素不超过给定数量。
     * 	skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
     * 	distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */
    @Test
    public void test2() {
        //内部迭代：迭代操作 Stream API 内部完成
        Stream<Employee> stream = emps.stream().filter(e -> {
            System.out.println("测试中间操作");
            return e.getAge() <= 35;
        });

        //上面部分是不会输出任何结果的
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);
    }

    //外部迭代
    @Test
    public void test3() {
        Iterator<Employee> iterator = emps.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 短路
     *
     * 如果使用limit限制了，在找到3个后就不会再继续查找了
     *
     * 输出结果：
     * 短路
     * Employee [id=102, name=李四, age=59, salary=6666.66]
     * 短路
     * Employee [id=101, name=张三, age=18, salary=9999.99]
     * 短路
     * 短路
     * Employee [id=104, name=赵六, age=8, salary=7777.77]
     */
    @Test
    public void test4() {
        emps.stream().filter(e -> {
            System.out.println("短路");  // 类似 &&  ||
            return e.getSalary() >= 5000;
        }).limit(3)
        .forEach(System.out::println);
    }

    /**
     * 和limit相反 skip 跳过前面几个
     */
    @Test
    public void test5() {
        emps.parallelStream().filter(e -> e.getSalary() >= 5000)
            .skip(2)
            .forEach(System.out::println);
    }

    /**
     * 去重
     * 需要重写equals  hashCode
     */
    @Test
    public void test6() {
        emps.stream()
            .distinct()
            .forEach(System.out::println);
    }
}

















































































