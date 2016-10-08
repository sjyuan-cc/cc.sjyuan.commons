package cc.sjyuan.commons.util.jdbc.factory;
import cc.sjyuan.commons.util.jdbc.config.Pagination;
import cc.sjyuan.commons.util.jdbc.core.ContentValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Abstract Factory to create SQL
 * 
 * @author Ysjian
 * 
 */
public abstract class AbstractSQLFactory {

	public static final String TAG = AbstractSQLFactory.class.getName();

	/**
	 * Parse the whereClause based on whereColumns
	 * 
	 * @param whereColumns
	 *            the whereColumns
	 * @return the weherClause
	 */
	public static String parseWhereClause(String... whereColumns) {
		StringBuilder result = new StringBuilder();
		if (null != whereColumns && whereColumns.length > 0) {
			for (int i = 0; i < whereColumns.length; i++) {
				result.append(0 == i ? "" : " and ");
				result.append(whereColumns[i]);
				result.append("=?");
			}
			return result.toString();
		} else {
			return "";
		}
	}

	/**
	 * Parse Bean to ContentValus.<br>
	 * the fields of the Bean must be named as the column ignoreCas.<br>
	 * it is not suitable for update case
	 * 
	 * @param bean
	 *            the Bean contains the columns information of the DB table
	 * @return ContentValues Object
	 */
	public static ContentValues parseBeanToContentValues(Object bean) {
		ContentValues contentValues = new ContentValues();
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (Modifier.isPrivate(field.getModifiers())
					&& !Modifier.isStatic(field.getModifiers())) {
				Method method = null;
				try {
					method = clazz.getMethod(createGetter(fieldName));
					contentValues.put(fieldName, method.invoke(bean));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("false target for " + bean, e);
				}
			}
		}
		return contentValues;
	}

	/**
	 * Get the getter sql
	 * 
	 * @param field
	 *            the field
	 * @return the name of getter method
	 */
	public static String createGetter(String field) {
		if (null == field || "".equals(field)) {
			throw new IllegalArgumentException("null or empty field");
		}
		StringBuilder sb = new StringBuilder(field);
		sb.replace(0, 1, String.valueOf(sb.charAt(0)).toUpperCase());
		sb.insert(0, "get");
		return sb.toString();
	}

	/**
	 * Create insert SQL.
	 * 
	 * @param table
	 *            the table
	 * @param contentValues
	 *            this map contains the initial column values for the row. The
	 *            keys should be the column names and the values the column
	 *            values
	 * @return the SQL
	 */
	public static String createInsertSQL(String table,
			ContentValues contentValues) {
		if (ContentValues.isContentValuesEmpty(contentValues)) {
			throw new IllegalArgumentException("ContentValues is empty!");
		}
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO  ");
		insertSql.append(table);
		insertSql.append("(");
		int i = 0;
		StringBuilder questionMarksBuilder = new StringBuilder();
		for (String colName : contentValues.keySet()) {
			insertSql.append((i > 0) ? "," : "");
			insertSql.append(colName);
			questionMarksBuilder.append((i > 0) ? "," : "");
			questionMarksBuilder.append("?");
			i++;
		}
		insertSql.append(") VALUES(");
		insertSql.append(questionMarksBuilder.toString());
		insertSql.append(")");
		Logger.getLogger(TAG).info(insertSql.toString());
		return insertSql.toString();
	}

	/**
	 * Create delete SQL.
	 * 
	 * @param table
	 *            the table to delete from
	 * @param whereClause
	 *            the optional WHERE clause to apply when deleting. Passing null
	 *            will delete all rows.
	 * @return the SQL
	 */
	public static String createDeleteSQL(String table, String whereClause) {
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("DELETE FROM ");
		deleteSql.append(table);
		if (null != whereClause) {
			deleteSql.append(" WHERE ");
			deleteSql.append(whereClause);
		}
		Logger.getLogger(TAG).info(deleteSql.toString());
		return deleteSql.toString();
	}

	/**
	 * Create update SQL.
	 * 
	 * @param table
	 *            the table to insert the row into
	 * @param contentValues
	 *            this map contains the initial column values for the row. The
	 *            keys should be the column names and the values the column
	 *            values
	 * @return the SQL
	 */
	public static String createUpdateSQL(String table,
			ContentValues contentValues, String whereClause) {
		if (ContentValues.isContentValuesEmpty(contentValues)) {
			throw new IllegalArgumentException("ContentValues is empty!");
		}
		StringBuilder updateSql = new StringBuilder(120);
		updateSql.append("UPDATE ");
		updateSql.append(table);
		updateSql.append(" SET ");
		// move all bind args to one array
		int i = 0;
		for (String colName : contentValues.keySet()) {
			updateSql.append((i > 0) ? "," : "");
			updateSql.append(colName);
			updateSql.append("=?");
			i++;
		}
		if (null != whereClause) {
			updateSql.append(" WHERE ");
			updateSql.append(whereClause);
		}
		Logger.getLogger(TAG).info(updateSql.toString());
		return updateSql.toString();
	}

	/**
	 * Create query SQL.
	 * 
	 * @param distinct
	 *            true if you want each row to be unique, false otherwise.
	 * @param table
	 *            The table name to compile the query against.
	 * @param columns
	 *            A list of which columns to return. Passing null will return
	 *            all columns, which is discouraged to prevent reading data from
	 *            storage that isn't going to be used.
	 * @param selection
	 *            A filter declaring which rows to return, formatted as an SQL
	 *            WHERE clause (excluding the WHERE itself). Passing null will
	 *            return all rows for the given table.
	 * @param groupBy
	 *            A filter declaring how to group rows, formatted as an SQL
	 *            GROUP BY clause (excluding the GROUP BY itself). Passing null
	 *            will cause the rows to not be grouped.
	 * @param having
	 *            A filter declare which row groups to include in the cursor, if
	 *            row grouping is being used, formatted as an SQL HAVING clause
	 *            (excluding the HAVING itself). Passing null will cause all row
	 *            groups to be included, and is required when row grouping is
	 *            not being used.
	 * @param orderBy
	 *            How to order the rows, formatted as an SQL ORDER BY clause
	 *            (excluding the ORDER BY itself). Passing null will use the
	 *            default sort order, which may be unordered.
	 * @param pageHelper
	 *            the page info of query.
	 * @return A {@link ResultSet} object, which is positioned before the first
	 *         entry. Note that {@link ResultSet}s are not synchronized, see the
	 *         documentation for more details.
	 * @exception SQLException
	 *                if a database access error occurs; this method is called
	 *                on a closed PreparedStatement or the SQL statement does
	 *                not return a ResultSet object
	 * @see ResultSet
	 * 
	 */
	public abstract String createQuerySQL(boolean distinct, String table,
			String[] columns, String selection, String groupBy, String having,
			String orderBy, Pagination pageHelper);

	/**
	 * Create SQL to delete the table
	 * 
	 * @param table
	 *            the table
	 * @return the query SQL
	 */
	public abstract String createDeleteTableSQL(String table);

	/**
	 * Create SQL to query the all views of current login user,use 'viewName' to
	 * fetch it
	 * 
	 * @param owner
	 *            the owner,MySQL is the serverName,Oracle is the login user
	 * @return the query SQL
	 */
	public abstract String createUserViewsSQL(String owner);

	/**
	 * Create SQL to query the all tables of the login user,use 'tableName' to
	 * fetch it
	 * 
	 * @param omner
	 *            the owner,MySQL is the serverName,Oracle is the login user
	 * @return the query SQL
	 */
	public abstract String createUserTablesSQL(String omner);

	/**
	 * Create SQL to query the primary keys of the table,use 'column' to fetch
	 * it
	 * 
	 * @param owner
	 *            the owner,MySQL is the serverName,Oracle is the login user
	 * @param table
	 *            the table
	 * @return the query SQL
	 */
	public abstract String createTablePrimaryKeysSQL(String owner, String table);

	/**
	 * Create SQL to query the all columns of the table,use 'columnName' to
	 * fetch it
	 * 
	 * @param owner
	 *            the owner,MySQL is the serverName,Oracle is the login user
	 * @param table
	 *            the table
	 * 
	 * @return the query SQL
	 */
	public abstract String createTableColumnsSQL(String owner, String table);
}