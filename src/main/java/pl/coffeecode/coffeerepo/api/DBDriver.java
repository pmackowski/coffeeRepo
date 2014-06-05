package pl.coffeecode.coffeerepo.api;

public interface DBDriver {
	
	String createSQL(QueryAttributes attributes);
	
	String createCountSQL(QueryAttributes attributes);
}
