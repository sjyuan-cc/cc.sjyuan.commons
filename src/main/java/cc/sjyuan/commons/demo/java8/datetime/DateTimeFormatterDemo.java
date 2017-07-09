package cc.sjyuan.commons.demo.java8.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateTimeFormatterDemo {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        LocalDate parse = LocalDate.parse("2015.09.10 08:08:08", formatter);
        System.out.println(parse);


        List<String> orgs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.insert(0, ">>" + "Z");
        for (String org : orgs) {
            if (org != null) {
                sb.insert(0, ">>" + org);
            }
        }
        if (sb.toString().startsWith(">>")){
            System.out.println(sb.toString().substring(2));
        }
        System.out.println(sb);
    }
}

