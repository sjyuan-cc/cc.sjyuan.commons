package cc.sjyuan.commons.java8.datetime;

import java.time.LocalDate;

public class LocalDateDemo {

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());
        System.out.println(now);
    }
}
