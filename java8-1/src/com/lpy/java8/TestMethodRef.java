package com.lpy.java8;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

/*
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * 
 * 1. 对象的引用 :: 实例方法名
 * 
 * 2. 类名 :: 静态方法名
 * 
 * 3. 类名 :: 实例方法名
 * 
 * 注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * 
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * 
 * 1. 类名 :: new
 * 
 * 三、数组引用
 * 
 * 	类型[] :: new;
 * 
 * 
 */
public class TestMethodRef {
    /**
     * 有机会详细了解一下ArrayList的initialCapacity
     * 有参构造 ： public ArrayList(int initialCapacity){}
     */
    @Test
    public void test8() {
        Function<Integer, String[]> function = x -> new String[x];
        Function<Integer, String[]> function1 = String[]::new;

        System.out.println(function.apply(10).length);
        System.out.println(function1.apply(10).length);

        Function<Integer, Employee[]> function2 = Employee[]::new;
        Function<Integer, ArrayList> function3 = ArrayList::new;

        System.out.println(function2.apply(10).length);
        System.out.println(getArrayListCapacity(function3.apply(20)));
    }


    public int getArrayListCapacity(ArrayList<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[])field.get(arrayList);
            return objects.length;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 构造器引用
     * 调用了有参构造方法
     *
     * 如果两个构造方法的参数相同怎么办？
     * ***不可以写同参数列表的构造方法。报错***
     */
    @Test
    public void test7() {
        Function<String, Employee> function = Employee::new;

        BiFunction<String, Integer, Employee> biFunction = Employee::new;
    }

    /**
     * 调用了无参构造方法
     */
    @Test
    public void test6() {
        Supplier<Employee> supplier = () -> new Employee();
        Supplier<Employee> supplier1 = Employee::new;

        System.out.println(supplier.get());
        System.out.println(supplier1.get());
    }

    /**
     * //类名 :: 实例方法名
     *
     * ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
     */
    @Test
    public void test5() {

        // BiPredicate<String, String> predicate  传入String, String 返回boolean
        // x.equals(y)  实例方法的调用者类型为String, 参数列表String, 返回boolean
        BiPredicate<String, String> predicate = (x, y) -> x.equals(y);
        BiPredicate<String, String> predicate1 = String::equals;

        System.out.println(predicate.test("a", "a"));
        System.out.println(predicate1.test("a", "b"));

        // 传入Employee 返回String
        // 实例方法的调用者类型为Employee, 无其他参数, 返回值类型为String 所以可以使用
        Function<Employee, String> function = e -> e.show();
        Function<Employee, String> function1 = Employee::show;

        System.out.println(function.apply(new Employee()));
        System.out.println(function1.apply(new Employee()));

    }

    //类名 :: 静态方法名
    @Test
    public void test4() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> comparator1 = Integer::compare;

        System.out.println(comparator.compare(1, 2));
        System.out.println(comparator1.compare(4, 3));
    }

    @Test
    public void test3() {
        BiFunction<Double, Double, Double> function = (x, y) -> Math.max(x, y);
        BiFunction<Double, Double, Double> function1 = Math::max;

        System.out.println(function.apply(1.2, 2.3));
        System.out.println(function1.apply(3.4, 4.2));

    }

    //对象的引用 :: 实例方法名
    @Test
    public void test2() {
        Employee emp = new Employee(101, "张三", 18, 9999.99);
        Supplier<String> supplier = () -> emp.getName();
        Supplier<String> supplier1 = emp::getName;

        System.out.println(supplier.get());
        System.out.println(supplier1.get());

    }

    @Test
    public void test1() {
        PrintStream printStream = System.out;
        Consumer<String> consumer = s -> printStream.println(s);
        Consumer<String> consumer1 = printStream::println;
        Consumer<String> consumer2 = System.out::println;

        consumer.accept("我是一号选手");
        consumer1.accept("我是二号选手");
        consumer2.accept("我是三号选手");
    }
}


























































