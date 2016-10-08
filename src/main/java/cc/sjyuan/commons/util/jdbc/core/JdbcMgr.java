package cc.sjyuan.commons.util.jdbc.core;
import cc.sjyuan.commons.util.jdbc.config.JDBCProperties;
import cc.sjyuan.commons.util.jdbc.config.EJdbcConnectity;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * JDBC manager，response to create and manage JDBC
 * 
 * @author Ysjian
 * @version 1.0
 * @email ysjian_pingcx@126.com
 * @QQ 646633781
 * @telephone 18192235667
 * @csdnBlog http://blog.csdn.net/ysjian_pingcx
 * @createTime 2013-10-20
 * @copyRight yjian_pingcx
 */
public abstract class JdbcMgr {

	/** ORACLE */
	public static final String DB_TYPE_ORACLE = "ORACLE";
	/** MYSQL */
	public static final String DB_TYPE_MYSQL = "MYSQL";
	/** SQLSERVER */
	public static final String DB_TYPE_SQLSERVER = "SQLSERVER";
	/** H2 */
	public static final String DB_TYPE_H2 = "H2";
	/** DB2 */
	public static final String DB_TYPE_DB2 = "DB2";
	/** ODBC */
	public static final String DB_TYPE_ODBC = "ODBC";
	/** INFORMIX */
	public static final String DB_TYPE_INFORMIX = "INFORMIX";
	/** POSTGRESQL */
	public static final String DB_TYPE_POSTGRESQL = "POSTGRESQL";

	public static final String DEFAULT_DB_PROPERTIES_FILE_NAME = "DB.properties";

	public static final int CONFIG_MODE_EXTERNAL = 1;

	public static final String FLAG_DB_TYPE = "DB_TYPE";
	public static final String FLAG_DRIVER = "_DRIVER";
	public static final String FLAG_URL = "_URL";
	public static final String FLAG_USER_NAME = "_USER_NAME";
	public static final String FLAG_PASSWORD = "_PASSWORD";

	public static final String PREFIX_DRIVER = "DRIVER_";
	public static final String PREFIX_URL = "URL_";
	public static final String PREFIX_USERNAME = "USERNAME_";
	public static final String PREFIX_PASSWORD = "PASSWORD_";

	private static Properties properties;
	private static String initDbType;

	private String currentDbType; // db type
	private String driver; // driver class name
	private String url; // connection string
	private String userName; // user name to login database
	private String password; // password to login database

	private static Map<String, Map<String, String>> dbParams = new HashMap<String, Map<String, String>>();

	/** load the resource file */
	static {
		loadProperties(DEFAULT_DB_PROPERTIES_FILE_NAME);
		initDbType = properties.getProperty(FLAG_DB_TYPE);
		loadParamsFromExternalProperties(initDbType);
	}

	/**
	 * load Properties file to obtain the config info of Database
	 * 
	 * @param propertiesFile
	 *            properties file name
	 */
	private static void loadProperties(String propertiesFile) {
		try {
			properties = new Properties();
			properties.load(JdbcMgr.class.getResourceAsStream("/"
					+ propertiesFile));
		} catch (IOException e) {
			throw new RuntimeException(
					"File is not found,check you file location,"
							+ "make sure it is in the same dir with the class which is accessing it!",
					e);
		}
	}

	/**
	 * load the parameters to cache
	 */
	private static void loadParamsFromExternalProperties(String dbType) {
		if (null == dbParams.get(dbType)) {
			if (null == properties) {
				loadProperties(DEFAULT_DB_PROPERTIES_FILE_NAME);
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put(
					FLAG_DRIVER,
					verifyNullValue(PREFIX_DRIVER,
							properties.getProperty(PREFIX_DRIVER + dbType)));
			params.put(
					FLAG_URL,
					verifyNullValue(PREFIX_URL,
							properties.getProperty(PREFIX_URL + dbType)));
			params.put(
					FLAG_USER_NAME,
					verifyNullValue(PREFIX_USERNAME,
							properties.getProperty(PREFIX_USERNAME + dbType)));
			params.put(
					FLAG_PASSWORD,
					verifyNullValue(PREFIX_PASSWORD,
							properties.getProperty(PREFIX_PASSWORD + dbType)));
			dbParams.put(dbType, params);
		}
	}

	/**
	 * Verify if is null of each parameters from DB.properties file
	 * 
	 * @param prefix
	 *            prefix of parameter in the DB.properties file
	 * @param value
	 *            value of the parameter
	 * @return if not null return it,otherwise throws IllegalArgumentException
	 */
	public static String verifyNullValue(String prefix, String value) {
		if (null == value) {
			throw new IllegalArgumentException(
					"null value,Please check you DB.properties file is correct to spell like as "
							+ prefix + "[db type on upper case!]");
		}
		return value;
	}

	/**
	 * Create instance
	 * 
	 * @exception IllegalArgumentException
	 *                if the database type is not correct
	 */
	public JdbcMgr() {
		currentDbType = initDbType;
		initDbConfig(currentDbType);
	}

	/**
	 * Init the config of DB
	 * 
	 * @param dbType
	 */
	private void initDbConfig(String dbType) {
		Map<String, String> param = dbParams.get(dbType.toUpperCase());
		if (param != null) {
			driver = param.get(FLAG_DRIVER);
			url = param.get(FLAG_URL);
			userName = param.get(FLAG_USER_NAME);
			password = param.get(FLAG_PASSWORD);
			return;
		}
		throw new IllegalArgumentException("invalid database type param["
				+ dbType + "]");
	}

	/**
	 * Get the initialized database type assign in the DB.properties file
	 * 
	 * @return the initialized database type string
	 */
	public String getInitDBType() {
		return initDbType;
	}

	/**
	 * Get the current database type assign in the DB.properties file
	 * 
	 * @return the current database type string
	 */
	public String getCurrentDbType() {
		return currentDbType;
	}

	/** Connect to the database */
	private Connection connectToDB() {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("fail to load class for " + driver, e);
		} catch (SQLException e) {
			throw new RuntimeException("fail to connect to db for \nurl = "
					+ url.toString() + "\nuserName = " + userName
					+ "\npassWord = " + password, e);
		}
		return connection;
	}

	/**
	 * Reset the connection of DB
	 * 
	 * @param jdbcProperties
	 *            the JdbcProperties Bean
	 * @return new Connection
	 */
	public Connection resetConnection(JDBCProperties jdbcProperties) {
		JDBCProperties.Connectity connectity = jdbcProperties.buildConnectity();
		return resetConnection(jdbcProperties.getDbType(), connectity.getUrl(),
				connectity.getUserName(), connectity.getPassword());
	}

	/**
	 * Reset the connection of DB
	 * 
	 * @param jdbcConnectity
	 *            Enum Constant of EJdbcConnectity
	 * @param url
	 *            the url to DB
	 * @param userName
	 *            the userName to login DB
	 * @param password
	 *            the password to login DB
	 */
	private void resetConnectionParams(EJdbcConnectity jdbcConnectity,
			String url, String userName, String password) {
		currentDbType = jdbcConnectity.dbType();
		driver = jdbcConnectity.driver();
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Reset the connection of DB
	 * 
	 * @param jdbcConnectity
	 *            the Enum Constant of EJdbcConnectity
	 * @param serverIp
	 *            the IP
	 * @param port
	 *            the port
	 * @param serverName
	 *            the serverName
	 * @param userName
	 *            the userName to login DB
	 * @param password
	 *            the password to login DB
	 * @return new Connection Object
	 * @see JdbcMgr#resetConnection(EJdbcConnectity, String, String, String,
	 *      String, String)
	 */
	public Connection resetConnection(EJdbcConnectity jdbcConnectity,
			String serverIp, String port, String serverName, String userName,
			String password) {
		JDBCProperties jdbcProperties = new JDBCProperties(jdbcConnectity,
				serverIp, port, serverName, userName, password);
		return resetConnection(jdbcProperties);
	}

	/**
	 * Reset the connection of DB
	 * 
	 * @param jdbcConnectity
	 *            the Enum Constant of EJdbcConnectity
	 * @param url
	 *            the url to DB
	 * @param userName
	 *            the userName to login DB
	 * @param password
	 *            the password to login DB
	 * @return new Connection Object
	 */
	public synchronized Connection resetConnection(
			EJdbcConnectity jdbcConnectity, String url, String userName,
			String password) {
		resetConnectionParams(jdbcConnectity, url, userName, password);
		return connectToDB();
	}

	/**
	 * Reset the connection of DB
	 * 
	 * @param dbType
	 *            the DB type
	 * @param url
	 *            the url to DB
	 * @param userName
	 *            the userName to login DB
	 * @param password
	 *            the password to login DB
	 * @return new Connection Object
	 * @see JdbcMgr#resetConnection(EJdbcConnectity, String, String, String)
	 * @see EJdbcConnectity#toEJdbcType(String)
	 */
	public Connection resetConnection(String dbType, String url,
			String userName, String password) {
		EJdbcConnectity eJdbcType = EJdbcConnectity.toEJdbcType(dbType);
		return resetConnection(eJdbcType, url, userName, password);
	}

	/**
	 * Prepare statement
	 * 
	 * @param sql
	 *            the sql
	 * @param bindArgs
	 *            the parameters to bound
	 */
	protected PreparedStatement preparedStatement(String sql,
			Object... bindArgs) {
		if (sql == null) {
			throw new NullPointerException("null value input");
		}
		return preparedStatement(connectToDB(), sql, bindArgs);
	}

	private PreparedStatement preparedStatement(Connection connection,
			String sql, Object... bindArgs) {
		PreparedStatement pstm = null;
		try {
			pstm = connection.prepareStatement(sql);
			if (bindArgs != null && bindArgs.length > 0) {
				for (int i = 0; i < bindArgs.length; i++) {
					pstm.setObject(i + 1, bindArgs[i]);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(
					"fail to set preparedStatement for sql: " + sql, e);
		}
		return pstm;
	}

	/**
	 * Execute query
	 * 
	 * @param sql
	 *            the query sql
	 * @param bindArgs
	 *            the parameters to bound
	 * @return the ResultSet after query
	 * @exception SQLException
	 *                if a database access error occurs; this method is called
	 *                on a closed <code>PreparedStatement</code> or the SQL
	 *                statement does not return a <code>ResultSet</code> object
	 */
	public ResultSet execQuery(String sql, Object... bindArgs)
			throws SQLException {
		Connection connectToDB = null;
		PreparedStatement preparedStatement = null;
		connectToDB = connectToDB();
		preparedStatement = preparedStatement(connectToDB, sql, bindArgs);
		return preparedStatement.executeQuery();
	}

	/**
	 * Query Object from DB
	 * 
	 * @param resultSetHandler
	 *            the handler to handle ResultSet
	 * @param sql
	 *            the SQL to Execute
	 * @param bindArgs
	 *            the parameters to binding the Executive SQL
	 * @return the type decided when received it
	 * @exception SQLException
	 *                if a database access error occurs; this method is called
	 *                on a closed <code>PreparedStatement</code> or the SQL
	 *                statement does not return a <code>ResultSet</code> object
	 */
	public <T> T query(ResultSetHandler<T> resultSetHandler, String sql,
			Object... bindArgs) throws SQLException {

		ResultSet execQuery = null;
		try {
			execQuery = execQuery(sql, bindArgs);
			return resultSetHandler.handle(execQuery);
		} finally {
			JdbcMgr.closeResultSetCascade(execQuery);
		}
	}

	/**
	 * 
	 * @author 袁慎建
	 * @version 1.0
	 * @email ysjian_pingcx@126.com
	 * @QQ 646633781
	 * @telephone 18192235667
	 * @csdnBlog http://blog.csdn.net/ysjian_pingcx
	 * @createTime 2014年6月29日
	 */
	public interface ResultSetHandler<T> {

		/**
		 * Process the ResultSet from DB
		 * 
		 * @param rs
		 *            the ResultSet
		 * @return
		 */
		T handle(ResultSet rs);
	}

	/**
	 * Execute update
	 * 
	 * @param sql
	 *            the update sql
	 * @param bindArgs
	 *            the parameters to bound
	 * @return the rows be affected
	 * @exception SQLException
	 *                if a database access error occurs; this method is called
	 *                on a closed <code>PreparedStatement</code> or the SQL
	 *                statement does not return a <code>ResultSet</code> object
	 */
	public int execUpdate(String sql, Object... bindArgs) throws SQLException {
		int result = -1;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = preparedStatement(connectToDB(), sql, bindArgs);
			result = preparedStatement.executeUpdate();
			return result;
		} finally {
			JdbcMgr.closeStatementCascade(preparedStatement);
		}
	}

	/**
	 * Execute update batch
	 * 
	 * @param sql
	 *            the update sql
	 * @param bindArgs
	 *            the parameters to bound
	 * @return the rows be affected
	 * @exception SQLException
	 *                if a database access error occurs; this method is called
	 *                on a closed <code>PreparedStatement</code> or the SQL
	 *                statement does not return a <code>ResultSet</code> object
	 */
	public int[] execUpdateBatch(String sql, List<List<Object>> bindArgs)
			throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = preparedStatement(connectToDB(), sql);
			for (List<Object> list : bindArgs) {
				if (null != list && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						preparedStatement.setObject(i + 1, list.get(i));
					}
				}
			}
			return preparedStatement.executeBatch();
		} finally {
			JdbcMgr.closeStatementCascade(preparedStatement);
		}
	}

	/**
	 * Get the connection
	 * 
	 * @return the Connection instance
	 */
	public Connection getConnection() {
		return connectToDB();
	}

	/**
	 * Close the connection
	 * 
	 * @param connection
	 *            the connection
	 * @throws SQLException
	 */
	public static void closeConnection(Connection connection)
			throws SQLException {
		if (null != connection) {
			connection.close();
			connection = null;
		}
	}

	/**
	 * Close the ResultSet
	 * 
	 * @param resultSet
	 *            the ResultSet
	 * @throws SQLException
	 */
	public static void closeResultSet(ResultSet resultSet) throws SQLException {
		if (null != resultSet) {
			resultSet.close();
			resultSet = null;
		}
	}

	/**
	 * Close the Statement
	 * 
	 * @param statement
	 *            the Statement
	 * @throws SQLException
	 */
	public static void closeStatement(Statement statement) throws SQLException {
		if (null != statement) {
			statement.close();
			statement = null;
		}
	}

	/**
	 * Release the resource
	 * 
	 * @param connection
	 *            the connection to DB
	 * @param statement
	 *            the statement
	 * @param resultSet
	 *            the resultSet
	 * @throws SQLException
	 */
	public static void release(Connection connection, Statement statement,
			ResultSet resultSet) throws SQLException {
		try {
			closeResultSet(resultSet);
		} finally {
			try {
				closeStatement(statement);
			} finally {
				closeConnection(connection);
			}
		}
	}

	/**
	 * Close the Statement cascade with its Connection
	 * 
	 * @param statement
	 *            the Statement
	 * @throws SQLException
	 */
	public static void closeStatementCascade(Statement statement)
			throws SQLException {
		Connection connection = null;
		if (null != statement) {
			connection = statement.getConnection();
		}
		release(connection, statement, null);
	}

	/**
	 * Close the ResultSet cascade with its Statement and its Connection
	 * 
	 * @param resultSet
	 *            the ResultSet
	 * @throws SQLException
	 */
	public static void closeResultSetCascade(ResultSet resultSet)
			throws SQLException {
		Connection connection = null;
		Statement statement = null;
		if (null != resultSet) {
			statement = resultSet.getStatement();
			if (null != statement) {
				connection = statement.getConnection();
			}
		}
		release(connection, statement, resultSet);
	}
}