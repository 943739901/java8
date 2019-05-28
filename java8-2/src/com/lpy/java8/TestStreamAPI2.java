package com.lpy.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
public class TestStreamAPI2 {
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 59, 6666.66, Employee.Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
			new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
			new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
			new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
	);
	
	//3. 终止操作
	/*
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
	@Test
	public void test1() {
		boolean allMatch = emps.stream()
				.allMatch(e -> Employee.Status.BUSY.equals(e.getStatus()));

		System.out.println(allMatch);

		boolean anyMatch = emps.stream()
				.anyMatch(e -> Employee.Status.BUSY.equals(e.getStatus()));

		System.out.println(anyMatch);

		boolean noneMatch = emps.stream()
				.noneMatch(e -> Employee.Status.BUSY.equals(e.getStatus()));

		System.out.println(noneMatch);
	}

	@Test
	public void test2() {
		Optional<Employee> first = emps.stream()
				.sorted(Comparator.comparing(Employee::getSalary))
				.findFirst();

		System.out.println(first.get());

		System.out.println("-------------------------------------------");

		// 取最小其实就是取第一个
		Optional<Employee> min = emps.stream()
				.min(Comparator.comparing(Employee::getSalary));

		System.out.println(min.get());

		System.out.println("-------------------------------------------");

		Optional<Employee> any = emps.parallelStream()
				.filter(e -> Employee.Status.FREE.equals(e.getStatus()))
				.findAny();

		System.out.println(any.get());
	}

	@Test
	public void test3() {
		long count = emps.stream()
				.filter(e -> Employee.Status.FREE.equals(e.getStatus()))
				.count();

		System.out.println(count);

		Optional<Double> max = emps.stream()
				.map(Employee::getSalary)
				.max(Double::compare);

		System.out.println(max.get());

		Optional<Employee> min = emps.stream()
				.min(Comparator.comparing(Employee::getSalary));

		System.out.println(min.get());
	}

	/**
	 * 注意：流进行了终止操作后，不能再次使用 否则会报错
	 * java.lang.IllegalStateException: stream has already been operated upon or closed
	 */
	@Test
	public void test4() {
		Stream<Employee> stream = emps.stream()
				.filter(e -> Employee.Status.FREE.equals(e.getStatus()));

		//已终止
		long count = stream.count();

		//继续使用则报错
		stream.map(Employee::getSalary)
				.max(Double::compare);
	}
}































































