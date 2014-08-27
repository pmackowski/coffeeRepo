package pl.coffeecode.coffeerepo;

import javax.sql.DataSource;

public interface SQLDialectDatasource {
	
	String db();
	
	String schemaScript();
	
	String jdbcDriver();
	
	String jdbcUrl();
	
	String jdbcUser();
	
	String jdbcPassword();
	
	DataSource dataSource();
}
