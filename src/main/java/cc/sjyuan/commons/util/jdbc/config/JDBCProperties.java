package cc.sjyuan.commons.util.jdbc.config;
/**
 * JDBC属性相关实体类，此类负责对客户端传入的数据库IP、端口号、用户名、密码进行封装和转换
 * 
 * @author Ysjian
 * 
 */
public class JDBCProperties {

	/** 默认IP：127.0.0.1 */
	public static final String DEFAULT_SERVER_IP = "127.0.0.1";
	/** 默认端口：1521 */
	public static final String DEFAULT_SERVER_PORT = "1521";
	/** 默认数据库类型：ORACLE */
	public static final EJdbcConnectity DEFAULT_DB_DYPE = EJdbcConnectity.ORACLE;

	private EJdbcConnectity dbType = DEFAULT_DB_DYPE;
	private String serverIp = DEFAULT_SERVER_IP;
	private String serverPort = DEFAULT_SERVER_PORT;
	private String serverName = "";
	private String userName = "";
	private String password = "";

	public JDBCProperties() {
	}

	public JDBCProperties(EJdbcConnectity dbType, String serverIp, String port,
			String serverName, String userName, String password) {
		this.dbType = dbType;
		this.serverIp = serverIp;
		this.serverPort = port;
		this.serverName = serverName;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * <p>
	 * 连接性实体类，此类绑定在JDBCProperties对线上，不可在外部实例化，如果需要修改相关参数，
	 * 通过修改所依赖的JDBCProperties对象，重新构建一次
	 * 
	 * @see JDBCProperties#buildConnectity()
	 * 
	 * @author Ysjian
	 * 
	 */
	public class Connectity {

		private String url;
		private String userName;
		private String password;

		private Connectity(String url) {
			userName = JDBCProperties.this.userName;
			password = JDBCProperties.this.password;
			this.url = url;
		}

		public String getUrl() {
			return url;
		}

		public String getUserName() {
			return userName;
		}

		public String getPassword() {
			return password;
		}

		@Override
		public String toString() {
			return "Connectity [url=" + url + ", userName=" + userName
					+ ", password=" + password + "]";
		}
	}

	/**
	 * <p>
	 * 构建连接对象，此方法是唯一构建<code>Connectity</code>的入口
	 * 
	 * @return 返回一个连接对象
	 */
	public JDBCProperties.Connectity buildConnectity() {
		String specialUrl = dbType.urlTemplate();
		Connectity connectity = new Connectity(String.format(specialUrl,
				serverIp, serverPort, serverName));
		return connectity;
	}

	public EJdbcConnectity getDbType() {
		return dbType;
	}

	public void setDbType(EJdbcConnectity dbType) {
		this.dbType = dbType;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "JDBCObject [dbType=" + dbType + ", serverIp=" + serverIp
				+ ", port=" + serverPort + ", serverName=" + serverName
				+ ", userName=" + userName + ", password=" + password + "]";
	}
}
