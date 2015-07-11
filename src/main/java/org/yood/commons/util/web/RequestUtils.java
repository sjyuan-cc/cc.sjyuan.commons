package org.yood.commons.util.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源列别缓存
 * 
 * @author Ysjian
 * 
 */
public class RequestUtils {

	private RequestUtils() {
	}

	/** 请求重定向 */
	public static final String REQUEST_REDIRECT = "redirect:";
	/** 请求转发 */
	public static final String REQUEST_FORWARD = "forward:";

	public static void redirect(HttpServletResponse response, String url)
			throws IOException {
		response.sendRedirect(url);
	}

	public static void forward(ServletRequest request,
			ServletResponse response, String url) throws IOException,
			ServletException {
		request.getRequestDispatcher(url).forward(request, response);
	}

	public static String getServerDir(ServletRequest request, String uploadDir) {
		return request.getServletContext().getRealPath("/")
				+ (uploadDir == null ? "" : uploadDir);
	}

	public static void getServerDir(ServletRequest request) {
		Enumeration<String> attributeNames = request.getParameterNames();
		while (attributeNames.hasMoreElements()) {
//			String nextElement = attributeNames.nextElement();
		}
	}
}
