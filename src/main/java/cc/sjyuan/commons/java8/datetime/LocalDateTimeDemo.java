package cc.sjyuan.commons.java8.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDemo {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        now.withDayOfMonth(8);
        System.out.println(now);
        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
