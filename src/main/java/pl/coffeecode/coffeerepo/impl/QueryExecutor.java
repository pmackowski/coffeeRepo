package pl.coffeecode.coffeerepo.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

public class QueryExecutor {
	
	private static final int NOT_ALLOWED_TOTAL_RECORDS = -1;
	
	private static final Logger logger = LoggerFactory.getLogger(QueryExecutor.class);
	
	protected final DataSource dataSource;
	protected final DatabaseDriver databaseDriver;

	QueryExecutor(DataSource dataSource, DatabaseDriver databaseDriver) {
		checkNotNull(dataSource);
		checkNotNull(databaseDriver);
		this.dataSource = dataSource;
		this.databaseDriver = databaseDriver;
	}

	public QueryResult getResult(QueryAttributes attributes) {
		checkNotNull(attributes);
		String sql = getSQL(attributes);
		List<Object> bindValues = databaseDriver.convertBindValues(attributes.getBindValues());
		ImmutableTable<Integer,String,Object> table = executeQuery(sql, bindValues);
		
		int totalRecords = NOT_ALLOWED_TOTAL_RECORDS;
		if (attributes.getNumberOfRows() != null) {
			String countSql = getCountSQL(attributes);
			totalRecords = executeCountQuery(countSql, bindValues);
		} else {
			totalRecords = table.rowKeySet().size();
		}
		QueryResult queryResult = new QueryResultImpl(table, sql, attributes, totalRecords);
		logger.debug("{}", queryResult);
		return queryResult;
	}
	
	protected final String getSQL(QueryAttributes attributes) {
		return databaseDriver.createSQL(attributes);
	}
	
	protected final String getCountSQL(QueryAttributes attributes) {
		return databaseDriver.createCountSQL(attributes);
	}
	
	protected final ImmutableTable<Integer,String, Object> executeQuery(String sql, List<Object> bindValues) {
		logger.debug("SQL [{}] with bind values {}", sql, bindValues);
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Table<Integer,String, Object> table = HashBasedTable.create();
		
		try {
			connection = dataSource.getConnection();
			stmt = prepareStatement(connection, sql, bindValues);
			rs = stmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();

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
			return ImmutableTable.copyOf(table);
		} catch (SQLException e) {
			throw new QueryError(sql, bindValues, e);
		} finally {
			try  { if (rs != null) rs.close(); } catch (SQLException e) {  } 
			try  { if (stmt != null) stmt.close(); } catch (SQLException e) {  }
			try  { if (connection != null) connection.close(); } catch (SQLException e) {  }
		}
		
	}
	
	protected final int executeCountQuery(String sql, List<Object> bindValues) {
		logger.debug("SQL [{}] with bind values {}", sql, bindValues);
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			stmt = prepareStatement(connection, sql, bindValues);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("count(*)");
		} catch (SQLException e) {
			throw new QueryError(sql, bindValues, e);
		} finally {
			try  { if (rs != null) rs.close(); } catch (SQLException e) {  } 
			try  { if (stmt != null) stmt.close(); } catch (SQLException e) {  }
			try  { if (connection != null) connection.close(); } catch (SQLException e) {  }
		}
	}

	private PreparedStatement prepareStatement(Connection connection, String sql, List<Object> bindValues) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(sql);
		int i = 1;
		for (Object bindValue: bindValues) {
			stmt.setObject(i++, bindValue);
		}
		return stmt;
	}
	
}
