package pl.coffeecode.coffeerepo;

import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.h2.jdbcx.JdbcDataSource;

public class SQLDialectDatasourceImpl implements SQLDialectDatasource {
	
	private String db;
	private String schemaScript;
	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPassword;
	
	public SQLDialectDatasourceImpl(String db, String schemaScript, String jdbcDriver,
			String jdbcUrl, String jdbcUser, String jdbcPassword) {
		this.db = db;
		this.schemaScript = schemaScript;
		this.jdbcDriver = jdbcDriver;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUser = jdbcUser;
		this.jdbcPassword = jdbcPassword;
	}

	@Override
	public String schemaScript() {
		return schemaScript;
	}

	@Override
	public String jdbcDriver() {
		return jdbcDriver;
	}

	@Override
	public String jdbcUrl() {
		return jdbcUrl;
	}

	@Override
	public String jdbcUser() {
		return jdbcUser;
	}

	@Override
	public String jdbcPassword() {
		return jdbcPassword;
	}

	@Override
	public DataSource dataSource() { // TODO
		try {
			if ("H2".equals(db)) {
				JdbcDataSource jdbcDataSource = new JdbcDataSource();
				jdbcDataSource.setURL(jdbcUrl);
				jdbcDataSource.setUser(jdbcUser);
				jdbcDataSource.setPassword(jdbcPassword);
				return jdbcDataSource;
			} else if ("Oracle".equals(db)) {
				OracleDataSource oracleDS = new OracleDataSource();
	            oracleDS.setURL(jdbcUrl);
	            oracleDS.setUser(jdbcUser);
	            oracleDS.setPassword(jdbcPassword);
	            return oracleDS;
			}
		} catch (SQLException ex) {
			
		}
		return null;
		
	}

	@Override
	public String toString() {
		return "SQLDialectDatasourceImpl [" 
				+ "schemaScript=" + schemaScript + ", jdbcDriver="
				+ jdbcDriver + ", jdbcUrl=" + jdbcUrl + ", jdbcUser="
				+ jdbcUser + ", jdbcPassword=" + jdbcPassword + "]";
	}

	@Override
	public String db() {
		return db;
	}
	
}
