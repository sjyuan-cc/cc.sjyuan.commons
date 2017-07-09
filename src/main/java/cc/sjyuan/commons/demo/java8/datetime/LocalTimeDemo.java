package cc.sjyuan.commons.demo.java8.datetime;

import java.time.LocalTime;

public class LocalTimeDemo {

    public static void main(String[] args) {
        LocalTime now = LocalTime.now();
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());
        System.out.println(now);
    }
}
