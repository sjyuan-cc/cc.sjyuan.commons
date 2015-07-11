package org.yood.commons.util.encryptor;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Des Util
 * 
 * @author Ysjian
 *
 */
public class DESUtils {

	/** The default key */
	private static final String DEFAULT_KEY = "1234567812345678";

	private static Cipher encryptCipher;

	private static Cipher decryptCipher;

	private DESUtils() {
	}

	/**
	 * 指定密钥构造方法
	 *
	 * @param keyStr
	 *            指定的密钥
	 * @throws Exception
	 */
	static {
		try {
			Key key = getKey(DEFAULT_KEY);
			encryptCipher = initCipher(Cipher.ENCRYPT_MODE, key);
			decryptCipher = initCipher(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Cipher initCipher(int opmode, Key key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(opmode, key);
		return cipher;
	}

	/**
	 * 加密字节数组
	 *
	 * @param arrIn
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] encrypt(byte[] arrIn)
			throws IllegalBlockSizeException, BadPaddingException {
		return encryptCipher.doFinal(arrIn);
	}

	/**
	 * 加密字符串
	 *
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */
	public static String encrypt(String strIn)
			throws IllegalBlockSizeException, BadPaddingException {
		return asHex(encrypt(strIn.getBytes()));
	}

	/**
	 * 解密字节数组
	 *
	 * @param arrIn
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] arrIn)
			throws IllegalBlockSizeException, BadPaddingException {
		return decryptCipher.doFinal(arrIn);
	}

	/**
	 * 解密字符串
	 *
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */
	public static String decrypt(String strIn)
			throws IllegalBlockSizeException, BadPaddingException {
		return new String(decrypt(asBin(strIn)));
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 *
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private static Key getKey(String keyStr) throws Exception {
		byte[] arrBTmp = keyStr.getBytes();

		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new SecretKeySpec(arrB, "DES");
		return key;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813
	 *
	 * @param buf
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 */
	public static String asHex(byte[] buf) {
		StringBuilder result = new StringBuilder(buf.length * 2);
		for (byte b : buf) {
			// 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
			String value = Integer.toHexString(0xFF & b);
			// 每个字节8为，转为16进制标志，2个16进制位
			result.append(1 == value.length() ? ("0" + value) : (value));
		}
		return result.toString();
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813
	 *
	 * @param buf
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 */
	public static String asHex1(byte[] buf) {
		final String HEX = "0123456789abcdef";
		StringBuilder result = new StringBuilder(buf.length * 2);
		for (byte b : buf) {
			// 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			result.append(HEX.charAt((b >> 4) & 0x0f));
			// 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			result.append(HEX.charAt(b & 0x0f));
		}
		return result.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组 互为可逆的转换过程
	 *
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 */
	public static byte[] asBin(String strIn) {
		byte[] arrTemp = strIn.getBytes();
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] result = new byte[arrTemp.length / 2];
		for (int i = 0; i < arrTemp.length; i = i + 2) {
			String strTmp = new String(arrTemp, i, 2);
			result[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return result;
	}
}
