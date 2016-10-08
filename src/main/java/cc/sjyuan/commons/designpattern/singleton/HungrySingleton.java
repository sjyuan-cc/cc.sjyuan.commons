package cc.sjyuan.commons.designpattern.singleton;



import java.util.logging.Logger;

public class HungrySingleton {

    private static final HungrySingleton INSTANCE = new HungrySingleton();

    private HungrySingleton() {
        Logger.getGlobal().info("HungrySingleton.HungrySingleton()");
    }

    public static HungrySingleton getInstance() {
        return INSTANCE;
    }
}
