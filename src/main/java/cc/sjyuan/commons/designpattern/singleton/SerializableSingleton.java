package cc.sjyuan.commons.designpattern.singleton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Logger;

public class SerializableSingleton implements Serializable {

    private static final long serialVersionUID = -6876964357644803313L;

    private static class InstanceHolder {
        private static final SerializableSingleton INSTANCE = new SerializableSingleton();
    }

    private SerializableSingleton() {
    }

    public static SerializableSingleton getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private Object readResolve() {
        Logger.getGlobal().info("SerializableSingleton.readResolve()");
        return InstanceHolder.INSTANCE;
    }

    private void readObject(ObjectInputStream ois) throws IOException,
                                                          ClassNotFoundException {
        Logger.getGlobal().info("SerializableSingleton.readObject()");
        ois.defaultReadObject();
    }
}
