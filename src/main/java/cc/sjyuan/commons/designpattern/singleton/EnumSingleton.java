package cc.sjyuan.commons.designpattern.singleton;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

public enum EnumSingleton {

	INSTANCE;

	private Object readResolve() {
		Logger.getGlobal().info("EnumSingleton.readResolve()");
		return INSTANCE;
	}

	private void readObject(ObjectInputStream ois) throws IOException,
			ClassNotFoundException {
		Logger.getGlobal().info("EnumSingleton.readObject()");
		ois.defaultReadObject();
	}
}
