package com.lpy.java8;

import org.junit.Test;

import java.util.Optional;

/*
 * 一、Optional 容器类：用于尽量避免空指针异常
 * 	Optional.of(T t) : 创建一个 Optional 实例
 * 	Optional.empty() : 创建一个空的 Optional 实例
 * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * 	isPresent() : 判断是否包含值
 * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 */
public class TestOptional {

    @Test
    public void test1() {
        Optional<Employee> op = Optional.of(new Employee());
        Employee emp = op.get();
        System.out.println(emp);
    }

    @Test
    public void test2() {
        Optional<Object> o = Optional.ofNullable(null);
        System.out.println(o.get());

        Optional<Object> empty = Optional.empty();
        System.out.println(empty.get());
    }

    @Test
    public void test3() {
        Optional<Employee> op = Optional.ofNullable(null);
//        Optional<Employee> op = Optional.ofNullable(new Employee("lisi"));
        if (op.isPresent()) {
            System.out.println(op.get());
        }

        // orElse 传入的是对象
        Employee emp = op.orElse(new Employee("zhangsan"));
        System.out.println(emp);


        // orElseGet 可以写lambda表达式获取
        Employee emp2 = op.orElseGet(() -> new Employee());
        System.out.println(emp2);
    }

    @Test
    public void test4() {
        Optional<Employee> op = Optional.of(new Employee(101, "张三", 18, 9999.99));

        // public<U> Optional<U> map(Function<? super T, ? extends U> mapper)
        Optional<String> s = op.map(Employee::getName);
        System.out.println(s.get());

        // 要求Optional
        // public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
        Optional<String> s1 = op.flatMap(e -> Optional.of(e.getName()));
        System.out.println(s1.get());
    }

    @Test
    public void test5() {
        Man man = new Man();
        String godnessName = getGodnessName(man);
        System.out.println(godnessName);
    }

    //需求：获取一个男人心中女神的名字
    public String getGodnessName(Man man) {
        if (man != null) {
            Godness god = man.getGod();
            if (god != null) {
                return god.getName();
            }
        }
        return "苍老师";
    }

    //运用 Optional 的实体类
    @Test
    public void test6() {
//        Optional<Godness> godness = Optional.ofNullable(new Godness("林志玲"));
        Optional<Godness> godness = Optional.ofNullable(null);
        Optional<NewMan> newMan = Optional.ofNullable(new NewMan(godness));

        String name = getGodnessName2(newMan);
        System.out.println(name);
    }

    public String getGodnessName2(Optional<NewMan> newMan) {
        return newMan.orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("苍老师"))
                .getName();
    }
}
