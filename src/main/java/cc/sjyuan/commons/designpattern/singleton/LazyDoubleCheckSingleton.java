package cc.sjyuan.commons.designpattern.singleton;


import java.util.logging.Logger;

public class LazyDoubleCheckSingleton {

	private volatile static LazyDoubleCheckSingleton instance;

	private LazyDoubleCheckSingleton() {
		Logger.getGlobal().info("LazyDoubleCheckSingleton.LazyDoubleCheckSingleton()");
	}

	public static LazyDoubleCheckSingleton getInstance() {
		if (null == instance) {
			synchronized (LazyDoubleCheckSingleton.class) {
				if (null == instance) {
					instance = new LazyDoubleCheckSingleton();
				}
			}
		}
		return instance;
	}
}
