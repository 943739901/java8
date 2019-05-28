package com.lpy.java8;

@FunctionalInterface
public interface MyFun {

    Integer getValue(Integer num);

    // 如果多加方法就会报
    // Multiple non-overriding abstract methods found in interface com.lipengyu.java8.MyFun
    //void getA();
}
