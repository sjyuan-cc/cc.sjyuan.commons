package org.yood.commons.util.web;

import org.springframework.web.multipart.MultipartFile;
import org.yood.commons.util.text.TextUtils;
import org.yood.commons.util.io.ImageUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传和下载工具类
 * 
 * @author Ysjian
 *
 */
public class FileUpDownUtils {

	public static final String UPLOAD_PATH = "upload/activity_header";
	public static final String PROJECT_PATH = "zeus";

	private FileUpDownUtils() {
	}

	/**
	 * 上传文件到服务器
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String saveFileToServer(MultipartFile file,
			HttpServletRequest request, String uploadPath)
			throws IllegalStateException, IOException {
		if (!file.isEmpty()) {
			String dir = getContextUploadPath(request, uploadPath);
			String fileName = file.getOriginalFilename();
			fileName = TextUtils.makeUuid()
					+ fileName.substring(fileName.lastIndexOf(""));
			File saveFile = new File(dir, fileName);
			file.transferTo(saveFile);
			ImageUtils.compressImageByWidth(saveFile.getAbsolutePath(),
					saveFile.getAbsolutePath(), 250);
			String result = saveFile.getAbsolutePath();
			result = result.substring(
					result.lastIndexOf(PROJECT_PATH) + PROJECT_PATH.length()
							+ 1).replace("\\", "/");
			return result;
		}
		return null;
	}

	/**
	 * 修改图片信息时上传图片，删除原来图片
	 * 
	 * @param uploadDir
	 * @param request
	 * @param file
	 * @param preImg
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String uploadImgOnModify(String uploadDir,
			HttpServletRequest request, MultipartFile file, String preImg)
			throws IllegalStateException, IOException {
		preImg = preImg.substring(preImg.lastIndexOf("/") + 1);
		deleteServerFile(request, uploadDir, preImg);
		return saveFileToServer(file, request, uploadDir);
	}

	/**
	 * 删除服务器上的文件
	 * 
	 * @param request
	 * @param uploadPath
	 * @param fileName
	 * @return
	 */
	public static boolean deleteServerFile(HttpServletRequest request,
			String uploadPath, String fileName) {
		String contextUploadPath = getContextUploadPath(request, uploadPath);
		File file = new File(contextUploadPath, fileName);
		return file.delete();
	}

	/**
	 * 获取上下文上传路径
	 * 
	 * @param request
	 * @param uploadPath
	 * @return
	 */
	public static String getContextUploadPath(HttpServletRequest request,
			String uploadPath) {
		String path = request.getSession().getServletContext()
				.getRealPath(uploadPath == null ? UPLOAD_PATH : uploadPath);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir.getAbsolutePath();
	}

	public static boolean isFileSizeOverflow(MultipartFile file, long maxSize) {
		return file.getSize() > maxSize;
	}
}
