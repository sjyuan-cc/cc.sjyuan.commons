package org.yood.commons.util.io;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;


public class IOUtils {

	public static final String ENCODING_UTF_8 = "UTF-8";
	public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";
	public static final String ENCODING_GBK = "GBK";
	public static final String ENCODING_GB2312 = "GB2312";

	private IOUtils() {

	}

	public static boolean isFileExits(String filePath) {
		if (filePath == null || "".equals(filePath.trim())) {
			return false;
		}
		return new File(filePath).exists();
	}


	public static String readStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] values = new byte[1024];
		try {
			int length = 0;
			while ((length = is.read(values)) != -1) {
				baos.write(values, 0, length);
			}
			return new String(baos.toString(ENCODING_UTF_8));
		} finally {
			close(baos);
			close(is);
		}
	}

	public static String bufferedReadStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				ENCODING_UTF_8));
		try {
			String line = null;
			StringBuilder result = new StringBuilder();
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} finally {
			br.close();
		}
	}

	public static void copyFile(String source, String target)
			throws  IOException {
		copyFile(new File(source), new File(target));
	}


	public static void copyFile(File source, File target)
			throws  IOException {
		copyFile(new FileInputStream(source), new FileOutputStream(target));
	}

	public static void copyFile(InputStream source, OutputStream target)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(source);
		BufferedOutputStream bos = new BufferedOutputStream(target);
		byte[] value = new byte[2048];
		int length = 0;
		while (-1 != (length = bis.read(value))) {
			bos.write(value, 0, length);
		}
		bos.close();
		bis.close();
	}

	public static String getFileExtName(String file) {
		if (file == null || "".equals(file.trim())) {
			return null;
		}
		String result = file.trim();
		try {
			return result.substring(result.lastIndexOf(""));
		} catch (Exception e) {
			return null;
		}
	}


	public static JSONObject parseHttpInputStrem(InputStream is)
			throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				ENCODING_UTF_8));
		StringBuffer result = new StringBuffer();
		String value = null;
		while ((value = br.readLine()) != null) {
			result.append(value);
			result.append("\n");
		}
		JSONObject jsonObject = JSONObject.parseObject(result.toString());
		return jsonObject;
	}


	public static void close(InputStream is) throws IOException {
		if (is != null) {
			is.close();
		}
	}

	public static void close(OutputStream os) throws IOException {
		if (os != null) {
			os.close();
		}
	}

	public static void close(Reader reader) throws IOException {
		if (reader != null) {
			reader.close();
		}
	}

	public static void close(Writer writer) throws IOException {
		if (writer != null) {
			writer.close();
		}
	}
}