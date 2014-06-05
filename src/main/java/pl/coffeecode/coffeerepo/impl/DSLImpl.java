package pl.coffeecode.coffeerepo.impl;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.api.DBDriver;
import pl.coffeecode.coffeerepo.api.DSL;
import pl.coffeecode.coffeerepo.api.DSLFrom;
import pl.coffeecode.coffeerepo.api.QueryResult;

import com.google.common.cache.LoadingCache;

public class DSLImpl implements DSL {
	
	protected final QueryExecutor delegate;
	
	public DSLImpl(DataSource dataSource, DBDriver sqlDialect) {
		delegate = new QueryExecutor(dataSource, sqlDialect);
	}

	public DSLImpl(DataSource dataSource, DBDriver sqlDialect, LoadingCache<String,QueryResult> cache) {
		delegate = new QueryCache(dataSource, sqlDialect, cache);
	}
	
	@Override
	public DSLFrom select(String... columnNames) {
		return new DSLFromImpl(delegate, columnNames);
	}
	
}
