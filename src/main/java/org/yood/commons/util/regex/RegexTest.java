package org.yood.commons.util.regex;

import java.util.TreeSet;

public class RegexTest {

    public static void main(String[] args) {
        test_1();
        test_2();
        test_3();
    }

    public static void test_3() {
        String mail = "abc1@sina.com.cn";
        String regex = "\\w+@\\w+(\\.\\w+)+";//1@1.1
        boolean b = mail.matches(regex);

        System.out.println(mail + ":" + b);
    }

    public static void test_1() {
        String str = "袁袁袁.......慎慎慎慎慎......建建建建建建";
        str = str.replaceAll("\\.+", "");

        System.out.println(str);
        str = str.replaceAll("(.)\\1+", "$1");
        System.out.println(str);
    }

    public static void test_2() {
        String ip_str = "192.168.10.34  127.0.0.1  3.3.3.3  105.70.11.55";
        ip_str = ip_str.replaceAll("(\\d+)", "00$1");
        System.out.println(ip_str);

        ip_str = ip_str.replaceAll("0*(\\d{3})", "$1");
        System.out.println(ip_str);

        String[] ips = ip_str.split(" +");
        TreeSet<String> ts = new TreeSet<>();

        for (String ip : ips) {
            ts.add(ip);
        }
        for (String ip : ts) {
            System.out.println(ip.replaceAll("0*(\\d+)", "$1"));
        }
    }
}


