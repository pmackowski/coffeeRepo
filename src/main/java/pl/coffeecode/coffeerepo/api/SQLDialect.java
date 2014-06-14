package pl.coffeecode.coffeerepo.api;

import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;
import pl.coffeecode.coffeerepo.impl.driver.h2.H2Driver;
import pl.coffeecode.coffeerepo.impl.driver.oracle.OracleDriver;

public enum SQLDialect implements DatabaseDriver {
	
	H2(new H2Driver()),
	Oracle(new OracleDriver());

	private DatabaseDriver driver;
	
	SQLDialect(DatabaseDriver driver) {
		this.driver = driver;
	}
	
	public String createSQL(QueryAttributes attributes) {
		return driver.createSQL(attributes);
	}

	@Override
	public String createCountSQL(QueryAttributes attributes) {
		return driver.createCountSQL(attributes);
	}

}
