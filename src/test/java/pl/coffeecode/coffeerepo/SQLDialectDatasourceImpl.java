package pl.coffeecode.coffeerepo;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

import pl.coffeecode.coffeerepo.api.SQLDialect;
import oracle.jdbc.pool.OracleDataSource;

public class SQLDialectDatasourceImpl implements SQLDialectDatasource {
	
	private SQLDialect dialect;
	private String schemaScript;
	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPassword;
	
	public SQLDialectDatasourceImpl(SQLDialect dialect, String schemaScript, String jdbcDriver,
			String jdbcUrl, String jdbcUser, String jdbcPassword) {
		this.dialect = dialect;
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
			if (dialect == SQLDialect.H2) {
				JdbcDataSource jdbcDataSource = new JdbcDataSource();
				jdbcDataSource.setURL(jdbcUrl);
				jdbcDataSource.setUser(jdbcUser);
				jdbcDataSource.setPassword(jdbcPassword);
				return jdbcDataSource;
			} else if (dialect == SQLDialect.Oracle) {
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
	public SQLDialect dialect() {
		return dialect;
	}

	@Override
	public String toString() {
		return "SQLDialectDatasourceImpl [dialect=" + dialect
				+ ", schemaScript=" + schemaScript + ", jdbcDriver="
				+ jdbcDriver + ", jdbcUrl=" + jdbcUrl + ", jdbcUser="
				+ jdbcUser + ", jdbcPassword=" + jdbcPassword + "]";
	}
	
}
