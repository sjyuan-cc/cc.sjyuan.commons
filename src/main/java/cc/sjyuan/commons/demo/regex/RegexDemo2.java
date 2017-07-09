package cc.sjyuan.commons.demo.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        functionDemo_4();
        functionDemo_3();
        functionDemo_2();
        functionDemo_1();
    }

    public static void functionDemo_4() {
        String str = "da jia hao,ming tian bu fang!";
        String regex = "\\b[a-z]+\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.start() + ":" + matcher.end());
        }
    }

    public static void functionDemo_3() {
        String str = "zhangsanttttxiaoqiangmmmmmmzhaoliu";
        str = str.replaceAll("(.)\\1+", "$1");
        System.out.println(str);
        String tel = "15800001111";//158****1111;
        tel = tel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        System.out.println(tel);

    }

    public static void functionDemo_2() {
        String str = "zhangsanttttxiaoqiangmmmmmmzhaoliu";
        String[] names = str.split("(.)\\1+");//str.split("\\.");
        for (String name : names) {
            System.out.println(name);
        }
    }

    public static void functionDemo_1() {
        String tel = "15800001111";
        String regex = "1[358]\\d{9}";
        boolean b = tel.matches(regex);
        System.out.println(tel + ":" + b);
    }
}
