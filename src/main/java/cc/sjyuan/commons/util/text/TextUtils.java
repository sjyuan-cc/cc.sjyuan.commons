package cc.sjyuan.commons.util.text;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Text Util
 *
 * @author Ysjian
 *
 */
public class TextUtils {

	/**
	 * Judge whether the text is null or empty
	 *
	 * @param arg0
	 * @return
	 */
	public static boolean isEmpty(String arg0) {
		return null == arg0 || "".equals(arg0);
	}

	/**
	 * Compare two string
	 *
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean compare(String arg0, String arg1) {
		if (null == arg0 && null == arg1) {
			return true;
		}
		if (null != arg0) {
			return arg0.equals(arg1);
		} else if (null != arg1) {
			return arg1.equals(arg0);
		}
		return true;
	}

	/**
	 * Connect objects as string
	 *
	 * @param args
	 *            the object to be connected
	 * @return string after connected
	 */
	public static String connect(Object... args) {
		StringBuilder result = new StringBuilder();
		if (null != args && args.length > 0) {
			for (Object object : args) {
				result.append(null == object ? "" : object.toString());
			}
		}
		return "";
	}

	/**
	 * Make UUID of style like as 3fd556fdert89dfegy9f6d5g65jk23h5
	 *
	 * @return
	 */
	public static String makeUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * Generate the number suffix
	 *
	 * @param no
	 * @return
	 */
	public static String makeNoPostfix(int no) {
		String result = null;
		if (no < 1) {
			no = 1;
		}
		result = no < 10 ? ("000" + no) : (no < 100 ? ("00" + no)
				: (no < 1000 ? ("0" + no) : no + ""));
		return result;
	}

	/**
	 * Split top size of a string
	 *
	 * @param conent
	 *            内容
	 * @param maxDisplaySize
	 *            指定最大显示
	 * @return
	 */
	public static String makeShortContent(String conent, int maxDisplaySize) {
		if (null == conent || "".equals(conent)) {
			return conent;
		}
		if (maxDisplaySize < 1) {
			throw new IllegalArgumentException("maxSize is below 1");
		}
		int count = 0;
		char[] charArray = conent.toCharArray();
		int index = 0;
		try {
			for (char c : charArray) {
				count += String.valueOf(c).getBytes("GBK").length;
				if (count > maxDisplaySize * 2) {
					break;
				}
				index++;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (index >= charArray.length) {
			return conent;
		} else {
			return conent.substring(0, index) + "...";
		}
	}
}
