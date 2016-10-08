package cc.sjyuan.commons.java8.datetime;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatterDemo {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.parse("2015-09-10 08:08:08"));
    }
}

