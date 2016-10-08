package cc.sjyuan.commons.util.jdbc.factory;

import java.util.logging.Logger;

import cc.sjyuan.commons.util.text.TextUtils;
import cc.sjyuan.commons.util.jdbc.config.Pagination;
import cc.sjyuan.commons.util.jdbc.SQLUtils;

/**
 * Oracle Factory
 * 
 * @author Ysjian
 * 
 */
public class OracleFactory extends AbstractSQLFactory {

	public static final String TAG = AbstractSQLFactory.class.getName();

	private static class InstanceHolder {
		private static final OracleFactory INSTANCE = new OracleFactory();
	}

	private OracleFactory() {
	}

	public static OracleFactory getInstances() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String createDeleteTableSQL(String table) {
		String sql = "DROP TABLE %s CASCADE CONSTRAINTS";
		sql = String.format(sql, table);
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createUserViewsSQL(String omner) {
		String sql = "SELECT VIEW_NAME viewName FROM ALL_VIEWS WHERE OWNER = '%s' ";
		sql = String.format(sql, omner);
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createUserTablesSQL(String owner) {
		String sql = "SELECT table_name tableName FROM all_tables where owner = '%s'";
		sql = String.format(sql, owner.toUpperCase());
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createTablePrimaryKeysSQL(String owner, String table) {
		String sql = "select cu.column_name columnName from user_cons_columns cu,user_constraints au "
				+ "where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' "
				+ "and au.table_name = '%s' and cu.owner = '%s'";
		sql = String.format(sql, table.toUpperCase(), owner.toUpperCase());
		Logger.getLogger(TAG).info(sql);
		return sql;
	}

	@Override
	public String createTableColumnsSQL(String owner, String table) {
		String sql = "select column_name columnName from all_tab_columns where table_name = '%s' and owner = '%s'";
		sql = String.format(sql, table.toUpperCase(), owner.toUpperCase());
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
			query.append(" * ");
		}
		if (null != pageHelper) {
			query.append("FROM (SELECT ROWNUM RN, t.* FROM ");
			query.append(table);
			query.append(" t ");
			SQLUtils.packageSQLClauses(selection, groupBy, having, orderBy,
					query);
			int currentPage = pageHelper.getCurrentPage();
			int pageSize = pageHelper.getPageSize();
			int begin = (currentPage - 1) * pageSize + 1;
			int end = pageSize * currentPage + 1;
			query.append(")  WHERE RN >= ");
			query.append(begin);
			query.append(" AND RN < ");
			query.append(end);
		} else {
			query.append(" FROM ");
			query.append(table);
			SQLUtils.packageSQLClauses(selection, groupBy, having, orderBy,
					query);
		}
		Logger.getLogger(TAG).info(query.toString());
		return query.toString();
	}
}
