package com.lpy.exer;

import com.lpy.java8.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestStreamAPI {

	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 79, 6666.66, Employee.Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
			new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
			new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
			new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
	);

	@Test
	public void test1() {
		Integer[] array = {1, 2, 3, 4, 5};

		Arrays.stream(array)
				.map(e -> e * e)
				.forEach(System.out::println);
	}

	@Test
	public void test2() {
		System.out.println(emps.stream()
				.map(x -> 1)
				.reduce(Integer::sum).get());
	}

}
