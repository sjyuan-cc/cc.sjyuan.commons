package cc.sjyuan.commons.demo.java8.lambda;

public class LambdaDemo2 {

    public static void main(String[] args) {
        new Action() {
            @Override
            public void print(String msg) {
                System.out.println(msg);
            }
        }.print("Traditional print");


        Action action = msg -> System.out.println(msg);
        action.print("Lambda print");
    }


    interface Action {
        void print(String msg);
    }
}
