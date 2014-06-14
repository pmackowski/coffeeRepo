package pl.coffeecode.coffeerepo.impl;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.api.DSL;
import pl.coffeecode.coffeerepo.api.DSLFrom;
import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;

import com.google.common.cache.Cache;

public class DSLImpl implements DSL {
	
	protected final QueryExecutor delegate;
	
	public DSLImpl(DataSource dataSource, DatabaseDriver sqlDialect) {
		delegate = new QueryExecutor(dataSource, sqlDialect);
	}

	public DSLImpl(DataSource dataSource, DatabaseDriver sqlDialect, Cache<QueryAttributes,QueryResult> cache) {
		delegate = new QueryCache(dataSource, sqlDialect, cache);
	}
	
	@Override
	public DSLFrom select(String... columnNames) {
		return new DSLFromImpl(delegate, columnNames);
	}
	
}
