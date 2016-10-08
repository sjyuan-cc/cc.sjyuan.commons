package cc.sjyuan.commons.designpattern.singleton;

import java.util.logging.Logger;

public class LazyHolderSingleton {

	private static class InstanceHolder {
		private static final LazyHolderSingleton INSTANCE = new LazyHolderSingleton();
	}

	private LazyHolderSingleton() {
		Logger.getGlobal().info("LazyHolderSingleton.LazyHOlderSingleton()");
	}

	public static LazyHolderSingleton getInstance() {
		return InstanceHolder.INSTANCE;
	}
}
