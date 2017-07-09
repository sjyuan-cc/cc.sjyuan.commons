package cc.sjyuan.commons.util.encryptor;
import java.security.MessageDigest;

public final class MD5Utils {

	private static final String METHOD_MD5 = "MD5";

	private MD5Utils() {
		throw new IllegalStateException();
	}

	public static String encryptByMD5(String param) {
		return encrypt(param, METHOD_MD5);
	}

	private static String encrypt(String param, String algorithm) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(algorithm);
			byte[] byteArray = param.getBytes("ISO-8859-1");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append(0);
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
