package org.yood.commons.util.exception;
/**
 * 系统异常管理类
 * 
 * @author Ysjian
 * 
 */
public class ExceptionUtils {

	private ExceptionUtils() {
	}

	/**
	 * NullPointerException
	 */
	public static void nullPointerException() {
		throw new NullPointerException();
	}

	public static void nullPointerException(String msg) {
		throw new NullPointerException(msg);
	}

	/**
	 * RuntimeException
	 */
	public static void runtimeException() {
		throw new RuntimeException();
	}

	public static void runtimeException(String msg) {
		throw new RuntimeException(msg);
	}

	public static void runtimeException(Throwable e) {
		throw new RuntimeException(e);
	}

	public static void runtimeException(String msg, Throwable e) {
		throw new RuntimeException(msg, e);
	}

	/**
	 * IllegalArgumentException
	 */
	public static void illegalArgumentException() {
		throw new IllegalArgumentException();
	}

	public static void illegalArgumentException(String msg) {
		throw new IllegalArgumentException(msg);
	}

	public static void illegalArgumentException(Throwable e) {
		throw new IllegalArgumentException(e);
	}

	public static void illegalArgumentException(String msg, Throwable e) {
		throw new IllegalArgumentException(msg, e);
	}
}
