package org.yood.commons.util.jdbc.config;

import org.yood.commons.util.jdbc.core.JdbcMgr;

/**
 * Ejdbc连接
 * 
 * @author 袁慎建
 * @version 1.0
 * @email ysjian_pingcx@126.com
 * @QQ 646633781
 * @telephone 18192235667
 * @csdnBlog http://blog.csdn.net/ysjian_pingcx
 * @createTime 2014年6月29日
 */
public enum EJdbcConnectity {
	/** Oracle */
	ORACLE(JdbcMgr.DB_TYPE_ORACLE, "oracle.jdbc.OracleDriver",
			"jdbc:oracle:thin:@%s:%s:%s"),
	/** MySQL */
	MYSQL(JdbcMgr.DB_TYPE_MYSQL, "com.mysql.jdbc.Driver", "jdbc:mysql://%s:%s/%s"),
	/** SQLServer */
	SQLSERVER(JdbcMgr.DB_TYPE_SQLSERVER,
			"com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"jdbc:sqlserver://%s:%s;databaseName=%s");

	private String driver;
	private String urlTemplate;
	private String dbType;

	EJdbcConnectity(String dbType, String driver, String urlTemplate) {
		this.dbType = dbType;
		this.driver = driver;
		this.urlTemplate = urlTemplate;
	}

	public String driver() {
		return driver;
	}

	public String urlTemplate() {
		return urlTemplate;
	}

	public String dbType() {
		return dbType;
	}

	/**
	 * 更具数据库类型名称转换成EJdbcType枚举类
	 * 
	 * @param dbType
	 * @return
	 */
	public static EJdbcConnectity toEJdbcType(String dbType) {
		if (JdbcMgr.DB_TYPE_ORACLE.equals(dbType)) {
			return EJdbcConnectity.ORACLE;
		} else if (JdbcMgr.DB_TYPE_MYSQL.equals(dbType)) {
			return EJdbcConnectity.MYSQL;
		} else if (JdbcMgr.DB_TYPE_SQLSERVER.equals(dbType)) {
			return EJdbcConnectity.SQLSERVER;
		}
		throw new IllegalArgumentException("invalid param dbType-->" + dbType);
	}
}
