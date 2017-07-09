package cc.sjyuan.commons.demo.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {
        List<Person> persons = initPersons();


        Stream<Person> stream = persons.stream();
        stream.filter(person -> person.getSex() == Person.Sex.FEMALE).
                mapToDouble(person -> person.getHeight()).
                forEach(height -> System.out.println("height:" + height));
    }


    private static List<Person> initPersons() {
        List<Person> result = new ArrayList<>();
        result.add(new Person(1, Person.Sex.MALE, "Shenjian Yuan", 1.75));
        result.add(new Person(2, Person.Sex.FEMALE, "Rui Zhang", 1.63));
        result.add(new Person(3, Person.Sex.FEMALE, "Rui Zhang2", 1.62));
        result.add(new Person(4, Person.Sex.FEMALE, "Rui Zhang3", 1.61));
        return result;
    }
}
