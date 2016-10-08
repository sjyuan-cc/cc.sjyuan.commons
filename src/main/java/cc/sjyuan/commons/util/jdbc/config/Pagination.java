package cc.sjyuan.commons.util.jdbc.config;

import java.io.Serializable;

/**
 * One text to display content by page
 * 
 * @author ysjian
 * @version 1.0
 * @email ysjian_pingcx@126.com
 * @QQ 646633781
 * @telephone 18192235667
 * @csdnBlog http://blog.csdn.net/ysjian_pingcx
 * @createTime 2013-12-20
 * @copyRight Merit
 */
public class Pagination implements Serializable {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1811344426032383181L;

	public final static String FIRST_ACTION = "_first"; // 执行跳到第一页操作
	public final static String NEXT_ACTION = "_next"; // 执行跳到下一页操作韩国女装
	public final static String PREVIO_ACTION = "_prev"; // 执行跳到上一页操作
	public final static String LAST_ACTION = "_last"; // 执行跳到最一页操作
	public final static String CURRENT_TAG = "_current"; // 当前页数
	public final static String PAGINATION_ACTION_TAG = "_paginationAction"; // 缓存操作
	public final static String GOTO_PAGE_ACTION = "_gotoPage"; // 执行跳到指定的某一页操作
	public final static String PAGES_GOTO = "pageSelect"; // 执行goto操作时,用户所指定的页数

	public static final int DEFAULT_PAGE_SIZE = 20;// 默认每页显示记录数
	public static final int DEFAULT_CURRENT_PAGE = 1;// 默认当前页

	private int totalRecords; // 总行记录数
	private int totalPages; // 总页数
	private int pageSize; // 分页单位
	private int currentPage; // 当前页数

	/**
	 * 默认构造方法，以默认的方式初始化 currentPage = (DEFAULT_CURRENT_PAGE = 1) pageSize =
	 * (DEFAULT_PAGE_SIZE = 10)
	 */
	public Pagination() {
		init();
	}

	/**
	 * 初始化分页工具的各项值
	 * 
	 * @return
	 */
	public Pagination init() {
		currentPage = DEFAULT_CURRENT_PAGE;
		pageSize = DEFAULT_PAGE_SIZE;
		getTotalPages();
		return this;
	}

	/**
	 * 初始化了totalRecords，仅当totalRecords>0时有效
	 * 
	 * @param totalRecords
	 */
	public Pagination(int totalRecords) {
		this();
		if (totalRecords > 0) {
			this.totalRecords = totalRecords;
		}
		getTotalPages();
	}

	/**
	 * 初始化了totalRecords和pageSize，仅当totalRecords>0且pageSize>0时有效
	 * 
	 * @param totalRecords
	 * @param pageSize
	 */
	public Pagination(int totalRecords, int pageSize) {
		this(totalRecords);
		initPageSize(pageSize);
	}

	/**
	 * 初始化分页单位
	 * 
	 * @param pageSize
	 */
	private void initPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
		getTotalPages();
	}

	/**
	 * 初始化了totalRecords,pageSize,currentPage,仅当totalRecords>0且pageSize>0
	 * 且currentPage>0时有效
	 * 
	 * @param totalRecords
	 * @param pageSize
	 * @param currentPage
	 */
	public Pagination(int totalRecords, int pageSize, int currentPage) {
		this(totalRecords, pageSize);
		initCurrentPage(currentPage);
	}

	/**
	 * 初始化当前页
	 * 
	 * @param currentPage
	 */
	private void initCurrentPage(int currentPage) {
		if (currentPage <= 1) {
			this.currentPage = 1;
		} else {
			if (totalPages > 0 && currentPage > totalPages) {
				this.currentPage = totalPages;
			} else {
				this.currentPage = currentPage;
			}
		}
		getTotalPages();
	}

	/**
	 * 判断是否是首页
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return !hasPrevious();
	}

	/**
	 * 判断是否有上一页
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		return currentPage > 1;
	}

	/**
	 * 判断是否有下一页
	 * 
	 * @return
	 */
	public boolean hasNext() {
		return currentPage * pageSize < totalRecords;
	}

	/**
	 * 判断是否是尾页
	 * 
	 * @return
	 */
	public boolean isLast() {
		return !hasNext();
	}

	/**
	 * 跳到首頁
	 * 
	 * @return
	 */
	public Pagination firstPage() {
		currentPage = 1;
		return this;
	}

	/**
	 * 跳到上一页
	 * 
	 * @return
	 */
	public Pagination previousPage() {
		if (hasPrevious()) {
			gotoPage(currentPage - 1);
		}
		return this;
	}

	/**
	 * 跳到下一页
	 * 
	 * @return
	 */
	public Pagination nextPage() {
		if (hasNext()) {
			gotoPage(currentPage + 1);
		}
		return this;
	}

	/**
	 * 跳到最后一页
	 * 
	 * @return
	 */
	public Pagination lastPage() {
		if (!isLast()) {
			gotoPage(totalPages);
		}
		return this;
	}

	/**
	 * 跳到指定的某一页
	 * 
	 * @param pageNumber
	 * @return
	 */
	public Pagination gotoPage(int pageNumber) {
		initCurrentPage(pageNumber);
		return this;
	}

	/**
	 * 设置总行数
	 * 
	 * @param totalRecords
	 * @return
	 */
	public Pagination setTotalRecords(int totalRecords) {
		if (totalRecords >= 0) {
			this.totalRecords = totalRecords;
		}
		getTotalPages();
		if (totalPages > 0) {
			if (currentPage > totalPages) {
				currentPage = totalPages;
			}
		}
		return this;
	}

	/**
	 * 得到总行数
	 * 
	 * @return
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * 检验页码是否合法
	 * 
	 * @param pageNumber
	 * @return
	 */
	public boolean isPageNoLegal(int pageNumber) {
		return pageNumber > 0 && pageNumber <= getTotalPages();
	}

	/**
	 * 得到分页单位
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置分页单位
	 * 
	 * @param pageSize
	 * @return
	 */
	public Pagination setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
		getTotalPages();
		return this;
	}

	/**
	 * 得到总页数
	 * 
	 * @return
	 */
	public int getTotalPages() {
		totalPages = (int) Math.ceil((double) totalRecords / (double) pageSize);
		return totalPages;
	}

	/**
	 * 得到当前页数
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置当前页数
	 * 
	 * @param currentPage
	 * @return
	 */
	public Pagination setCurrentPage(int currentPage) {
		initCurrentPage(currentPage);
		return this;
	}

	@Override
	public String toString() {
		return "PageHelper[totalRecords=" + totalRecords + ", totalPages="
				+ totalPages + ", pageSize=" + pageSize + ", currentPage="
				+ currentPage;
	}
}
