package pl.coffeecode.coffeerepo.impl;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.api.DynamicDSLFrom;
import pl.coffeecode.coffeerepo.api.DynamicDSLSelect;
import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;

import com.google.common.cache.Cache;

public class DynamicDSLSelectImpl implements DynamicDSLSelect {
	
	protected final QueryExecutor delegate;
	
	public DynamicDSLSelectImpl(DataSource dataSource, DatabaseDriver sqlDialect) {
		delegate = new QueryExecutor(dataSource, sqlDialect);
	}
	
	public DynamicDSLSelectImpl(DataSource dataSource, DatabaseDriver sqlDialect, Cache<QueryAttributes,QueryResult> cache) {
		delegate = new QueryCache(dataSource, sqlDialect, cache);
	}
	
	@Override
	public DynamicDSLFrom select(String... columnNames) {
		return new DynamicDSLFromImpl(delegate, columnNames);
	}
	
}
