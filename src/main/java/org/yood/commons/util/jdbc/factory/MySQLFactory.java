package org.yood.commons.util.jdbc.factory;
import java.util.logging.Logger;

import org.yood.commons.util.jdbc.SQLUtils;
import org.yood.commons.util.jdbc.config.Pagination;
import org.yood.commons.util.text.TextUtils;

/**
 * MySQL Factory used when connect to MySQL database
 *
 * @author Ysjian
 *
 */
public class MySQLFactory extends AbstractSQLFactory {

	public static final String TAG = MySQLFactory.class.getName();

	private static class InstanceHolder{
		private static final MySQLFactory INSTANCE = new MySQLFactory();
	}
	
	private MySQLFactory(){}
	
	public static MySQLFactory getInstances(){
		return InstanceHolder.INSTANCE;
	}
	
	@Override
	public String createDeleteTableSQL(String table) {
		String sql = "DROP TABLE IF EXISTS %s";
		sql = String.format(sql, table);
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createUserViewsSQL(String owner) {
		String sql = "SELECT table_name viewName FROM INFORMATION_SCHEMA.VIEWS where table_schema='%s'";
		sql = String.format(sql, owner);
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createUserTablesSQL(String owner) {
		String sql = "SELECT table_name FROM information_schema.TABLES  WHERE table_schema = '%s' and table_type='BASE TABLE'";
		sql = String.format(sql, owner);
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createTablePrimaryKeysSQL(String owner, String table) {
		String sql = "SELECT COLUMN_NAME,COLUMN_TYPE,COLUMN_KEY FROM information_schema.columns WHERE table_name='%s'AND TABLE_SCHEMA = '%s' AND COLUMN_KEY = 'PRI'";
		sql = String.format(sql, table, owner);
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createTableColumnsSQL(String owner, String table) {
		String sql = "SELECT COLUMN_NAME columnName FROM information_schema.columns WHERE table_name='%s'AND TABLE_SCHEMA = '%s';";
		sql = String.format(sql, table, owner);
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createQuerySQL(boolean distinct, String table,
			String[] columns, String selection, String groupBy, String having,
			String orderBy, Pagination pageHelper) {
		if (TextUtils.isEmpty(groupBy) && !TextUtils.isEmpty(having)) {
			throw new IllegalArgumentException(
					"HAVING clauses are only permitted when using a groupBy clause");
		}
		StringBuilder query = new StringBuilder(120);
		query.append("SELECT ");
		if (distinct) {
			query.append("DISTINCT ");
		}
		if (columns != null && columns.length != 0) {
			SQLUtils.appendColumns(query, columns);
		} else {
			query.append("* ");
		}
		query.append("FROM ");
		query.append(table);
		SQLUtils.packageSQLClauses(selection, groupBy, having, orderBy, query);
		if (null != pageHelper) {
			query.append(" LIMIT ");
			int currentPage = pageHelper.getCurrentPage();
			int pageSize = pageHelper.getPageSize();
			int begin = (currentPage - 1) * pageSize + 1;
			query.append(begin);
			query.append(" , ");
			query.append(pageSize);
		}
		Logger.getLogger(TAG).info(query.toString());
		return query.toString();
	}
}
