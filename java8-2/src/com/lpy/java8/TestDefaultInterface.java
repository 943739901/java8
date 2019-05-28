package com.lpy.java8;

public class TestDefaultInterface {
	
	public static void main(String[] args) {
		MyFun myFun = new SubClass();
		System.out.println(myFun.getName());

		MyInterface.show();
	}

}
