package pl.coffeecode.coffeerepo;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.api.SQLDialect;

public interface SQLDialectDatasource {
	
	SQLDialect dialect();
	
	String schemaScript();
	
	String jdbcDriver();
	
	String jdbcUrl();
	
	String jdbcUser();
	
	String jdbcPassword();
	
	DataSource dataSource();
}
