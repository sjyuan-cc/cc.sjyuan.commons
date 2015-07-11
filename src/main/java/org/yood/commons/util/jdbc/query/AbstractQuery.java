package org.yood.commons.util.jdbc.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象结果集管理者，负责从结果集中抽取信息封装对象
 * 
 */
public abstract class AbstractQuery<T> {

	/**
	 * 根据结果集合返回对象
	 * 
	 * @param rs
	 *            查询结果集
	 * @return 返回单个对象
	 * @throws SQLException
	 */
	public final T queryObject(ResultSet rs) throws SQLException {
		if (rs == null) {
			throw new NullPointerException("null ResultSet value input");
		}
		T object = null;
		try {
			while (null != rs && rs.next()) {
				object = queryOneRow(rs);
			}
		} finally {
			closeResultSet(rs);
		}
		return object;
	}

	/**
	 * 根据结果集合返回对象集合
	 * 
	 * @param rs
	 *            查询结果集
	 * 
	 * @return 返回单个对象
	 * @throws SQLException
	 */
	public final List<T> queryObjectList(ResultSet rs) throws SQLException {
		if (rs == null) {
			throw new NullPointerException("null ResultSet value input");
		}
		List<T> objects = new ArrayList<T>();
		try {
			while (null != rs && rs.next()) {
				T object = queryOneRow(rs);
				if (object != null) {
					objects.add(object);
				}
			}
		} finally {
			closeResultSet(rs);
		}
		return objects;
	}

	/**
	 * 获得一行记录，子类必须覆盖此方法
	 * 
	 * @param rs
	 *            查询结果集
	 * 
	 * @return 返回单个对象
	 * 
	 * @throws SQLException
	 */
	protected abstract T queryOneRow(ResultSet rs) throws SQLException;

	/**
	 * 统计个数
	 * 
	 * @param rs
	 *            查询结果集
	 * 
	 * @return 返回单个对象
	 * @throws SQLException
	 */
	public int count(ResultSet rs) throws SQLException {
		try {
			while (rs.next()) {
				return rs.getInt(1);
			}
		} finally {
			closeResultSet(rs);
		}
		return 0;
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
