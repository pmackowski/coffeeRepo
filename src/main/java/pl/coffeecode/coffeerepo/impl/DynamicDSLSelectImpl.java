package pl.coffeecode.coffeerepo.impl;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.api.DBDriver;
import pl.coffeecode.coffeerepo.api.DynamicDSLFrom;
import pl.coffeecode.coffeerepo.api.DynamicDSLSelect;
import pl.coffeecode.coffeerepo.api.QueryResult;

import com.google.common.cache.LoadingCache;

public class DynamicDSLSelectImpl implements DynamicDSLSelect {
	
	protected final QueryExecutor delegate;
	
	public DynamicDSLSelectImpl(DataSource dataSource, DBDriver sqlDialect) {
		delegate = new QueryExecutor(dataSource, sqlDialect);
	}
	
	public DynamicDSLSelectImpl(DataSource dataSource, DBDriver sqlDialect, LoadingCache<String,QueryResult> cache) {
		delegate = new QueryCache(dataSource, sqlDialect, cache);
	}
	
	@Override
	public DynamicDSLFrom select(String... columnNames) {
		return new DynamicDSLFromImpl(delegate, columnNames);
	}
	
}
