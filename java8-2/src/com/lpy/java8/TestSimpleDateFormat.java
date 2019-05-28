package com.lpy.java8;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestSimpleDateFormat {

	/**
	 * 多线程环境下  SimpleDateFormat 会报错
	 *
	 * java.util.concurrent.ExecutionException: java.lang.NumberFormatException: multiple points
	 * java.util.concurrent.ExecutionException: java.lang.NumberFormatException: For input string: ""
	 * 解决办法
	 *  1、建议在每个方法中都new一个新的SimpleDateFormat对象，这样子就可以避免这种问题。
	 *
	 *  2、也可以使用保存线程局部变量的ThreadLocal对象来保存每一个线程的SimpleDateFormat对象。
	 *
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Callable<Date> task = () -> sdf.parse("20190528");

		ExecutorService pool = Executors.newFixedThreadPool(10);

		List<Future<Date>> results = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			results.add(pool.submit(task));
		}

		for (Future<Date> future : results) {
			System.out.println(future.get());
		}
		pool.shutdown();
	}

	@Test
	public void test2() throws Exception {
		Callable<Date> task = () -> DateFormatThreadLocal.convert("20190528");

		ExecutorService pool = Executors.newFixedThreadPool(10);

		List<Future<Date>> results = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			results.add(pool.submit(task));
		}

		for (Future<Date> future : results) {
			System.out.println(future.get());
		}
		pool.shutdown();
	}

	@Test
	public void test3() throws Exception {
		DateTimeFormatter df = DateTimeFormatter.BASIC_ISO_DATE;
		DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyyMMdd");

		Callable<LocalDate> task = () -> LocalDate.parse("20190528", df);

		ExecutorService pool = Executors.newFixedThreadPool(10);

		List<Future<LocalDate>> results = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}

		for (Future<LocalDate> future : results) {
			System.out.println(future.get());
		}

		pool.shutdown();
	}
}













































