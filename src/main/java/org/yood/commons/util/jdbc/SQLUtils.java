package org.yood.commons.util.jdbc;
public final class SQLUtils {

	private SQLUtils() {
	}

	public static void packageSQLClauses(String selection, String groupBy,
			String having, String orderBy, StringBuilder query) {
		SQLUtils.appendClause(query, " WHERE ", selection);
		SQLUtils.appendClause(query, " GROUP BY ", groupBy);
		SQLUtils.appendClause(query, " HAVING ", having);
		SQLUtils.appendClause(query, " ORDER BY ", orderBy);
	}

	public static void appendClause(StringBuilder s, String name, String clause) {
		if (null != clause && clause.length() > 0) {
			s.append(name);
			s.append(clause);
		}
	}

	/**
	 * Add the names that are non-null in columns to s, separating them with
	 * commas.
	 */
	public static void appendColumns(StringBuilder s, String[] columns) {
		int n = columns.length;
		for (int i = 0; i < n; i++) {
			String column = columns[i];
			if (column != null) {
				if (i > 0) {
					s.append(", ");
				}
				s.append(column);
			}
		}
		s.append(' ');
	}
}
