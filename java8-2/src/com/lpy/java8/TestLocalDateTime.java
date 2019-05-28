package com.lpy.java8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class TestLocalDateTime {

    //1. LocalDate、LocalTime、LocalDateTime
    @Test
    public void test1() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = LocalDateTime.of(2018, 5, 28, 18, 5, 30);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt2.plusYears(20);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ldt2.minusMonths(2);
        System.out.println(ldt4);

        System.out.println("-------------------------------------------");

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getMonth());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getDayOfYear());
        System.out.println(ldt.getDayOfWeek());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
    }

    //2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    @Test
    public void test2() {
        //默认使用 UTC 时区
        Instant ins = Instant.now();
        System.out.println(ins);

        OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println(ins.toEpochMilli());

        System.out.println(ins.getNano());

        Instant ins2 = Instant.ofEpochSecond(5);
        System.out.println(ins2);
    }

    //3.
    //Duration : 用于计算两个“时间”间隔
    //Period : 用于计算两个“日期”间隔
    @Test
    public void test3() {
        Instant ins1 = Instant.now();

        System.out.println("-------------------------------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        Instant ins2 = Instant.now();
        Duration between = Duration.between(ins1, ins2);
        System.out.println(between.getSeconds());

        System.out.println("-------------------------------------------");

        LocalDate now = LocalDate.now();
        LocalDate of = LocalDate.of(2016, 8, 12);
        Period period = Period.between(of, now);
        System.out.println(period.getYears());

        // 月份数值的差
        System.out.println(period.getMonths());

        // 一个月内的天数的差 28号 - 12号 = 16
        System.out.println(period.getDays());

        //一共多少个月
        System.out.println(period.toTotalMonths());
    }

    //4. TemporalAdjuster : 时间校正器
    @Test
    public void test4() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        //更改这个月的第几天
        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        // 下个周日是什么时候
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        // 自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with(l -> {
            LocalDateTime ldt4 = (LocalDateTime) l;

            DayOfWeek dayOfWeek = ldt4.getDayOfWeek();
            if (DayOfWeek.FRIDAY.equals(dayOfWeek)) {
                return ldt4.plusDays(3);
            } else if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });

        System.out.println(ldt5);
    }

    //5. DateTimeFormatter : 解析和格式化日期或时间
    @Test
    public void test5() {
        DateTimeFormatter ild = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");

        LocalDateTime now = LocalDateTime.now();

        // 参数写反了也没关系
        String format = now.format(dtf);
        String format1 = ild.format(now);

        System.out.println(format);
        System.out.println(format1);

        LocalDateTime parse = now.parse(format, dtf);
        System.out.println(parse);
    }

    /**
     * 获取所有的时区
     */
    @Test
    public void test6() {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    @Test
    public void test7() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(now);

        // 带时区的时间
        ZonedDateTime now1 = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(now1);
    }
}






















































