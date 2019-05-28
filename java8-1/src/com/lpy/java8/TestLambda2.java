package com.lpy.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Test;

/*
 * 一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
 * 						    箭头操作符将 Lambda 表达式拆分成两部分：
 * 
 * 左侧：Lambda 表达式的参数列表
 * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
 * 
 * 语法格式一：无参数，无返回值
 * 		() -> System.out.println("Hello Lambda!");
 * 
 * 语法格式二：有一个参数，并且无返回值
 * 		(x) -> System.out.println(x)
 * 
 * 语法格式三：若只有一个参数，小括号可以省略不写
 * 		x -> System.out.println(x)
 * 
 * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
 *		Comparator<Integer> com = (x, y) -> {
 *			System.out.println("函数式接口");
 *			return Integer.compare(x, y);
 *		};
 *
 * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
 * 		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
 * 
 * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
 * 		(Integer x, Integer y) -> Integer.compare(x, y);
 * 
 * 上联：左右遇一括号省
 * 下联：左侧推断类型省
 * 横批：能省则省
 * 
 * 二、Lambda 表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
 * 			 可以检查是否是函数式接口
 */
public class TestLambda2 {

	@Test
	public void test1() {
		int num = 0;//jdk 1.7 前，必须是 final

		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello World!" + num);
			}
		};

		r.run();

		System.out.println("-------------------------------");

		Runnable r1 = () -> System.out.println("Hello Lambda!");
		r1.run();
	}

	@Test
	public void test2() {
		Consumer<String> con = System.out::println;
		con.accept("java8可真溜");
	}

	@Test
	public void test3() {
		Comparator<Integer> com = (x, y) -> {
			System.out.println("函数式接口");
			return Integer.compare(x, y);
		};
	}

	@Test
	public void test4() {
		Comparator<Integer> com = Integer::compare;
	}

	/**
	 * 测试类型推断
	 */
	@Test
	public void test5() {
		// 可以直接赋值是因为有类型推断
//		String[] strs = {"aaa", "bbb", "ccc"};
		// 分开写是不被允许的
//		String[] strs1;
//		strs1 = {"aaa", "bbb", "ccc"};

		// 后面尖括号中的类型就是由  List<E> -> ArrayList<E> 推断出的
		List<String> list = new ArrayList<>();

		// map种的<> 是类型推断  (jdk1.7会有问题？, 没有尝试)
		show(new HashMap<>());
	}

	public void show(Map<String, Integer> map){

	}

	//需求：对一个数进行运算
	@Test
	public void test6(){
		Integer num = operation(100, (x) -> x * x);
		System.out.println(num);

		System.out.println(operation(100, (x) -> x + 100));
	}

	public Integer operation(Integer num, MyFun mf) {
		return mf.getValue(num);
	}
}














































































