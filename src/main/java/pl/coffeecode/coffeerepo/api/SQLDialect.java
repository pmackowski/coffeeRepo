package pl.coffeecode.coffeerepo.api;

import pl.coffeecode.coffeerepo.impl.driver.H2Driver;

public enum SQLDialect implements DBDriver {
	
	H2(new H2Driver());

	private DBDriver driver;
	
	SQLDialect(DBDriver driver) {
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
