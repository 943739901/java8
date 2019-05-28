package com.lpy.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/*
 * Java8 内置的四大核心函数式接口
 * 
 * Consumer<T> : 消费型接口
 * 		void accept(T t);
 * 
 * Supplier<T> : 供给型接口
 * 		T get(); 
 * 
 * Function<T, R> : 函数型接口
 * 		R apply(T t);
 * 
 * Predicate<T> : 断言型接口
 * 		boolean test(T t);
 * 
 */
public class TestLambda3 {

	//Predicate<T> 断言型接口：
	@Test
	public void test4() {
		List<String> list = Arrays.asList("Hello", "atguigu", "Lambda", "www", "ok");
		List<String> strings = filterStr(list, x -> x.length() > 3);
		strings.forEach(System.out::println);
	}

	//需求：将满足条件的字符串，放入集合中
	public List<String> filterStr(List<String> strings, Predicate<String> predicate) {
		List<String> list = new ArrayList<>();
		strings.forEach(x -> {
			if(predicate.test(x)) {
				list.add(x);
			}
		});
		return list;
	}

	//Function<T, R> 函数型接口：
	@Test
	public void Test3() {
		String s = strHandler("\t\t\t 去啊哈哈哈好吧    ", String::trim);
		System.out.println(s);

		String s1 = strHandler(s, x -> x.substring(2, 5));
		System.out.println(s1);
	}

	//需求：用于处理字符串
	public String strHandler(String s, Function<String, String> function) {
		return function.apply(s);
	}

	//Supplier<T> 供给型接口 :
	@Test
	public void test2() {
		List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
		numList.forEach(System.out::println);
	}

	//需求：产生指定个数的整数，并放入集合中
	public List<Integer> getNumList(int num, Supplier<Integer> supplier) {
		List<Integer> list = new ArrayList<>();

		for (int i = 0; i < num; i++) {
			list.add(supplier.get());
		}
		return list;
	}

	//Consumer<T> 消费型接口 :
	@Test
	public void test1() {
		happy(100, m -> System.out.println("我一会要去吃点好吃的 " + m));
	}

	public void happy(double money, Consumer<Double> consumer) {
		consumer.accept(money);
	}
}
