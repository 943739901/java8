package com.lpy.java8;

/**
 * 集成的类 和 多个接口 方法重名
 * 接口默认方法 类优先 原则
 *
 * 如果实现多个接口， 方法重名 则需要用下面的方法实现
 */
public class SubClass /*extends MyClass*/ implements MyFun,MyInterface{

	@Override
	public String getName() {
		// 根据不同接口调用不同方法
		return MyInterface.super.getName();
	}
}
