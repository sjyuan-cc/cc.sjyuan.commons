package cc.sjyuan.commons.demo.java8.lambda;

public class LambdaDemo1 {
    public static void main(String[] args) {
        new Runnable() {
            @Override
            public void run() {
                System.out.println("Traditional Runnable execute...");
            }
        }.run();

        int a = 1;
        Runnable runnable = () -> System.out.println("LambdaDemo1 Runnable execute..." + a);

        runnable.run();
    }
}


