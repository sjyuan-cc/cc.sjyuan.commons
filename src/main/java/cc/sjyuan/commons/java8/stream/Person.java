package cc.sjyuan.commons.java8.stream;

public class Person {


    public enum Sex {
        MALE,
        FEMALE
    }

    private int id;
    private Sex sex;
    private String name;
    private double height;

    public Person() {
    }

    public Person(int id, Sex sex, String name, double height) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", height=" + height +
                '}';
    }
}