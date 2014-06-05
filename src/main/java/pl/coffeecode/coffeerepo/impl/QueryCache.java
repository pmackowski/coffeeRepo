package pl.coffeecode.coffeerepo.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.api.DBDriver;
import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;

import com.google.common.cache.LoadingCache;

public class QueryCache extends QueryExecutor {
	
	private final LoadingCache<String,QueryResult> cache;
	
	QueryCache(DataSource dataSource, DBDriver sqlDialect, LoadingCache<String,QueryResult> cache) {
		super(dataSource, sqlDialect);
		checkNotNull(cache);
		this.cache = cache;
	}
	
	@Override
	public QueryResult getResult(QueryAttributes attributes) {
		String sql = getSQL(attributes); 
		try {
			return (QueryResult) cache.get(sql);
		} catch (ExecutionException ex) {
			return super.getResult(attributes);
		}
	}
	
}
