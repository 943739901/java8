package com.lpy.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/*
 * 一、 Stream 的操作步骤
 * 
 * 1. 创建 Stream
 * 
 * 2. 中间操作
 * 
 * 3. 终止操作
 */
public class TestStreamAPI1 {
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 59, 6666.66),
			new Employee(101, "张三", 18, 9999.99),
			new Employee(103, "王五", 28, 3333.33),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(105, "田七", 38, 5555.55)
	);

	//2. 中间操作
	/**
	 * 映射
	 * 	map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
	 * 	flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
	@Test
	public void test1() {
		Stream<String> str = emps.stream()
				.map(Employee::getName);

		str.forEach(System.out::println);

		System.out.println("-------------------------------------------");

		List<String> strings = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

		Stream<String> stringStream = strings.stream().map(String::toUpperCase);
		stringStream.forEach(System.out::println);

		System.out.println("-------------------------------------------");

		Stream<Stream<Character>> streamStream = strings.stream()
				.map(TestStreamAPI1::filterCharacter);

		streamStream.forEach(ss -> {
			ss.forEach(System.out::println);
		});

		System.out.println("---------------------------------------------");

		Stream<Character> characterStream = strings.stream()
				.flatMap(TestStreamAPI1::filterCharacter);

		characterStream.forEach(System.out::println);
	}

	/**
	 * 为什么不直接toCharArray 之后转成list就好了
	 * List里面需要对象 toCharArray 返回的是char[];
	 * 最后会变成：
	 * List<char[]> chars = Arrays.asList(str.toCharArray());
	 * @param str
	 * @return
	 */
	public static Stream<Character> filterCharacter(String str) {
		List<Character> list = new ArrayList<>();

		for (Character ch : str.toCharArray()) {
			list.add(ch);
		}
		return list.stream();
	}

	/**
	 * sorted() Comparable ——自然排序
	 * sorted(Comparator com)——定制排序
	 */
	@Test
	public void test2() {
		emps.stream()
			.map(Employee::getName)
			.sorted()
			.forEach(System.out::println);

		System.out.println("-------------------------------------------");

		emps.stream()
			.sorted((x, y) -> {
				if(x.getAge() == y.getAge()) {
					return x.getName().compareTo(y.getName());
				} else {
					return Integer.compare(x.getAge(), y.getAge());
				}
			}).forEach(System.out::println);

		System.out.println("-------------------------------------------");

		emps.stream()
				.sorted(Comparator
						.comparing(Employee::getAge)
						.thenComparing(Employee::getName))
		.forEach(System.out::println);
	}
}

































