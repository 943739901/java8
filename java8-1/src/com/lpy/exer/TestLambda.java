package com.lpy.exer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.lipengyu.java8.Employee;

public class TestLambda {
	
	List<Employee> emps = Arrays.asList(
			new Employee(101, "张三", 18, 9999.99),
			new Employee(102, "李四", 59, 6666.66),
			new Employee(103, "王五", 28, 3333.33),
			new Employee(104, "赵六", 8, 7777.77),
			new Employee(105, "田七", 38, 5555.55)
	);
	
	@Test
	public void test1() {
		Collections.sort(emps, (x, y)
				-> x.getAge() == y.getAge()
				? x.getName().compareTo(y.getName())
				: -Integer.compare(x.getAge(), y.getAge()));

		emps.forEach(System.out::println);
	}

	@Test
	public void test2() {
		String trimStr = strHandler("\t\t\t java8可真溜   ", String::trim);
		System.out.println(trimStr);

		String upper = strHandler("abcdef", String::toUpperCase);
		System.out.println(upper);

		String newStr = strHandler("java8可真溜", (str) -> str.substring(2, 5));
		System.out.println(newStr);
	}

	public String strHandler(String s, MyFunction myFunction) {
		return myFunction.getValue(s);
	}

	@Test
	public void test3() {
		operatLong(11L , 12L, (x, y) -> x + y);

		System.out.println(operatLong1(10L , 20L, (x, y) -> x * y));
	}

	public void operatLong(Long x, Long y, MyFunction2<Long, Long> myFunction2) {
		System.out.println(myFunction2.getValue(x, y));
	}

	public Long operatLong1(Long x, Long y, MyFunction2<Long, Long> myFunction2) {
		return myFunction2.getValue(x, y);
	}

}
