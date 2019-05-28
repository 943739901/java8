package com.lpy.java8;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStreamAPI3 {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 79, 6666.66, Employee.Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
    );

    //3. 终止操作

    /**
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
     * <p>
     * public interface BinaryOperator<T> extends BiFunction<T,T,T>
     * R apply(T t, U u);
     */
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer reduce = list.stream()
                .reduce(0, (x, y) -> x + y);

        System.out.println(reduce);

        System.out.println("-------------------------------------------");

        Optional<Double> reduce1 = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);

        System.out.println(reduce1.get());
		/*
			BigDecimal.ROUND_HALF_UP表示四舍五入，
			BigDecimal.ROUND_HALF_DOWN也是五舍六入，
			BigDecimal.ROUND_UP表示进位处理（就是直接加1），
			BigDecimal.ROUND_DOWN表示直接去掉尾数。
		 */
        // 取后两位小数
        BigDecimal bigDecimal = new BigDecimal(reduce1.get()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(bigDecimal.doubleValue());
    }

    /**
     * 需求：搜索名字中 “六” 出现的次数
     * <p>
     * map-reduce 模式
     * <p>
     * 仔细理解一下
     * Optional<Double> reduce1 = emps.stream()
     * .map(Employee::getSalary)
     * .reduce(Double::sum);
     * 就会明白下面的也是类似:
     * 提取所有的名字
     * 提取所有名字的字母
     * 根据返回字母匹配返回1/0
     * 计算和
     */
    @Test
    public void test2() {
        Optional<Integer> liu = emps.stream()
                .map(Employee::getName)
                .flatMap(TestStreamAPI1::filterCharacter)
                .map(ch -> {
                    // 直接 '六'.equals(ch) 是不行的
                    if (new Character('六').equals(ch)) {
                        return 1;
                    } else {
                        return 0;
                    }
                })
                .reduce(Integer::sum);

        System.out.println(liu.get());
    }


    /**
     * collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test3() {
        List<String> list = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        list.forEach(System.out::println);

        System.out.println("-------------------------------------------");

        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());

        set.forEach(System.out::println);

        System.out.println("-------------------------------------------");

        HashSet<String> hashSet = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));

        hashSet.forEach(System.out::println);
    }
    //虽然很多都可以简化，但本测试方法的重点是讲述collect的一些功能

    /**
     * 最大值
     */
    @Test
    public void test4() {
        Optional<Double> max = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare));

        System.out.println(max.get());

        // 简化上面的
        Optional<Double> max1 = emps.stream()
                .map(Employee::getSalary).max(Double::compare);
    }

    @Test
    public void test5() {
        Optional<Employee> min = emps.stream()
                .collect(Collectors.minBy((x, y) -> Double.compare(x.getSalary(), y.getSalary())));
        System.out.println(min.get());

        // 简化一次
        Optional<Employee> min1 = emps.stream().min((x, y) -> Double.compare(x.getSalary(), y.getSalary()));
        // 简化二次
        Optional<Employee> min2 = emps.stream().min(Comparator.comparingDouble(Employee::getSalary));
    }

    /**
     * 求和
     */
    @Test
    public void test6() {
        Double summingDouble = emps.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(summingDouble);

		/*
			可以map多种
			map
			mapToDouble
			mapToInt
			mapToLong
			flatMap
			...
		 */
        //简化
        Double summingDouble1 = emps.stream().mapToDouble(Employee::getSalary).sum();
        System.out.println(summingDouble1);
    }

    /**
     * 平均值 和 数量
     */
    @Test
    public void test7() {
        Double averagingDouble = emps.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

        System.out.println(averagingDouble);

        Long counting = emps.stream()
                .collect(Collectors.counting());

        System.out.println(counting);

        // 简化
        Long counting1 = emps.stream().count();
        //  ......
        Long counting2 = (long) emps.size();

        System.out.println("-------------------------------------------");

        DoubleSummaryStatistics dss = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println(dss.getSum());
        System.out.println(dss.getAverage());
        System.out.println(dss.getMax());
        System.out.println(dss.getCount());
        System.out.println(dss.getMin());
    }

    /**
     * 阿里巴巴java开发手册：
     * 使用entrySet 遍历Map 类集合KV，而不是keySet 方式进行遍历。
     * <p>
     * keySet 其实是遍历了 2次，一次是转为 Iterator 对象，另一次是从 hashMap 中取出
     * key 所对应的 value。而 entrySet 只是遍历了一次就把 key 和value 都放到了entry 中，效
     * 率更高。如果是JDK8，使用Map.foreach方法。
     * <p>
     * 分组
     */
    @Test
    public void test8() {
        Map<Employee.Status, List<Employee>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));

        map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
    }

    /**
     * 多级分组
     */
    @Test
    public void test9() {
        Map<Employee.Status, Map<String, List<Employee>>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (e.getAge() >= 60)
                        return "老年";
                    else if (e.getAge() >= 35)
                        return "中年";
                    else
                        return "成年";
                })));

        map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
    }

    /**
     * 分区 符合条件的 不符合条件的
     * <p>
     * 第二个参数猜测是分组后放在那种数据结构里
     */
    @Test
    public void test10() {
        Map<Boolean, List<Employee>> map = emps.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() >= 5000, Collectors.toCollection(LinkedList::new)));

        map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
    }

    /**
     * public static Collector<CharSequence, ?, String> joining(CharSequence delimiter,
     * CharSequence prefix,
     * CharSequence suffix)
     */
    @Test
    public void test11() {
        String join = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "----", "----"));

        System.out.println(join);
    }

    /**
     * map-reduce
     */
    @Test
    public void test12() {
        Optional<Double> reduce = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.reducing(Double::sum));

        System.out.println(reduce.get());

        Optional<Double> reduce1 = emps.stream()
                .map(Employee::getSalary).reduce(Double::sum);
    }

    /**
     * 流还有很多方法，需要自己去摸索
     * peek 很有意思的方法
     * <p>
     * peek是个中间操作，它提供了一种对流中所有元素操作的方法，而不会把这个流消费掉（foreach会把流消费掉），然后你可以继续对流进行其他操作。
     */
    @Test
    public void test13() {
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

}

































































