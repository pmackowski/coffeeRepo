package pl.coffeecode.coffeerepo.impl.driver;

import pl.coffeecode.coffeerepo.api.QueryAttributes;

public interface DatabaseDriver {
	
	String createSQL(QueryAttributes attributes);
	
	String createCountSQL(QueryAttributes attributes);
	
}