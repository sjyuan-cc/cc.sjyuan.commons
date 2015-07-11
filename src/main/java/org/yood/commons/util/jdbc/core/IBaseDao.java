package org.yood.commons.util.jdbc.core;

import org.yood.commons.util.jdbc.config.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 袁慎建
 * @version 1.0
 * @email ysjian_pingcx@126.com
 * @QQ 646633781
 * @telephone 18192235667
 * @csdnBlog http://blog.csdn.net/ysjian_pingcx
 * @createTime 2014年6月29日
 */
public interface IBaseDao {

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param table         the table to insert the row into
     * @param contentValues this map contains the initial column values for the row. The
     *                      keys should be the column names and the values the column
     *                      values
     * @param whereClause   the optional WHERE clause to apply when deleting. Passing null
     *                      will delete all rows.
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     * @throws SQLException if a database access error occurs; this method is called
     *                      on a closed <code>PreparedStatement</code> or the SQL
     *                      statement does not return a <code>ResultSet</code> object
     */
    int update(String table, ContentValues contentValues, String whereClause,
               Object... whereArgs) throws SQLException;

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param table       the table to delete from
     * @param whereClause the optional WHERE clause to apply when deleting. Passing null
     *                    will delete all rows.
     * @param whereArgs   You may include ?s in the where clause, which will be replaced
     *                    by the values from whereArgs. The values will be bound as
     *                    Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     * @throws SQLException if a database access error occurs; this method is called
     *                      on a closed <code>PreparedStatement</code> or the SQL
     *                      statement does not return a <code>ResultSet</code> object
     */
    int delete(String table, String whereClause, Object... whereArgs)
            throws SQLException;

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param table         the table
     * @param contentValues this map contains the initial column values for the row. The
     *                      keys should be the column names and the values the column
     *                      values
     * @return
     * @throws SQLException if a database access error occurs; this method is called
     *                      on a closed <code>PreparedStatement</code> or the SQL
     *                      statement does not return a <code>ResultSet</code> object
     */
    long insert(String table, ContentValues contentValues) throws SQLException;

    /**
     * Query the given URL, returning a {@link ResultSet} over the result set.
     *
     * @param distinct      true if you want each row to be unique, false otherwise.
     * @param table         The table name to compile the query against.
     * @param columns       A list of which columns to return. Passing null will return
     *                      all columns, which is discouraged to prevent reading data from
     *                      storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return, formatted as an SQL
     *                      WHERE clause (excluding the WHERE itself). Passing null will
     *                      return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the
     *                      values from selectionArgs, in order that they appear in the
     *                      selection. The values will be bound as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted as an SQL
     *                      GROUP BY clause (excluding the GROUP BY itself). Passing null
     *                      will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in the cursor, if
     *                      row grouping is being used, formatted as an SQL HAVING clause
     *                      (excluding the HAVING itself). Passing null will cause all row
     *                      groups to be included, and is required when row grouping is
     *                      not being used.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause
     *                      (excluding the ORDER BY itself). Passing null will use the
     *                      default sort order, which may be unordered.
     * @param pageHelper    the page info of query.
     * @return A {@link ResultSet} object, which is positioned before the first
     * entry. Note that {@link ResultSet}s are not synchronized, see the
     * documentation for more details.
     * @throws SQLException if a database access error occurs; this method is called
     *                      on a closed <code>PreparedStatement</code> or the SQL
     *                      statement does not return a <code>ResultSet</code> object
     * @see ResultSet
     */
    ResultSet query(boolean distinct, String table, String[] columns,
                    String selection, Object[] selectionArgs, String groupBy,
                    String having, String orderBy, Pagination pageHelper)
            throws SQLException;
}
