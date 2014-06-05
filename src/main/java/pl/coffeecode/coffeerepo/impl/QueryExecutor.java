package pl.coffeecode.coffeerepo.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.coffeecode.coffeerepo.api.DBDriver;
import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

public class QueryExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(QueryExecutor.class);
	
	protected final DataSource dataSource;
	protected final DBDriver sqlDialect;

	QueryExecutor(DataSource dataSource, DBDriver sqlDialect) {
		checkNotNull(dataSource);
		checkNotNull(sqlDialect);
		this.dataSource = dataSource;
		this.sqlDialect = sqlDialect;
	}

	public QueryResult getResult(QueryAttributes attributes) {
		checkNotNull(attributes);
		String sql = getSQL(attributes);
		logger.debug(sql);
		ImmutableTable<Integer,String,Object> table = executeQuery(sql);
		
		Integer totalRecords = null;
		if (attributes.getNumberOfRows() != null) {
			String countSql = getCountSQL(attributes);
			totalRecords = executeCountQuery(countSql);
		}
		return new QueryResultImpl(table, sql, attributes, totalRecords);
	}
	
	protected final String getSQL(QueryAttributes attributes) {
		return sqlDialect.createSQL(attributes);
	}
	
	protected final String getCountSQL(QueryAttributes attributes) {
		return sqlDialect.createCountSQL(attributes);
	}
	
	protected final ImmutableTable<Integer,String, Object> executeQuery(String sql) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		Table<Integer,String, Object> table = HashBasedTable.create();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData(); // mozna przekazac wiecej informacji

			int i = 0;
			while (rs.next()) {
				i++;
				for (int j=1; j<=rsmd.getColumnCount(); j++){
					String column = rsmd.getColumnName(j);
					if (rs.getObject(column) != null) {
						table.put(i, column, rs.getObject(column));
					}	
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// TODO error handling
		} finally {
			try  { if (rs != null) rs.close(); } catch (SQLException e) {  } 
			try  { if (stmt != null) stmt.close(); } catch (SQLException e) {  }
			try  { if (connection != null) connection.close(); } catch (SQLException e) {  }
		}
		return ImmutableTable.copyOf(table);
	}
	
	protected final int executeCountQuery(String sql) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = -1;
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getInt("count(*)");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// TODO error handling
		} finally {
			try  { if (rs != null) rs.close(); } catch (SQLException e) {  } 
			try  { if (stmt != null) stmt.close(); } catch (SQLException e) {  }
			try  { if (connection != null) connection.close(); } catch (SQLException e) {  }
		}
		return count; // TODO jest do dupy
	}

	
}
