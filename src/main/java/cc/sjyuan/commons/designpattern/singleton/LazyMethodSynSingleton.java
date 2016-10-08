package cc.sjyuan.commons.designpattern.singleton;


import java.util.logging.Logger;

public class LazyMethodSynSingleton {

    private static LazyMethodSynSingleton instance;

    private LazyMethodSynSingleton() {
        Logger.getGlobal().info("LazyMethodSynSingleton.LazyMethodSynSingleton()");
    }

    public static synchronized LazyMethodSynSingleton getInstance() {
        if (null == instance) {
            instance = new LazyMethodSynSingleton();
        }
        return instance;
    }
}
