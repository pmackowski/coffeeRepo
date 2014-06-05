package pl.coffeecode.coffeerepo.api;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.impl.DSLImpl;
import pl.coffeecode.coffeerepo.impl.DynamicDSLSelectImpl;

import com.google.common.cache.LoadingCache;

public abstract class ViewRepository {
	
	public static DSL dsl(DataSource dataSource, SQLDialect sqlDialect) {
		return new DSLImpl(dataSource, sqlDialect);
	}

	public static DSL dsl(DataSource dataSource, SQLDialect sqlDialect, LoadingCache<String,QueryResult> cache) {
		return new DSLImpl(dataSource, sqlDialect, cache);
	}

	public static DynamicDSLSelect dynamicDsl(DataSource dataSource, SQLDialect sqlDialect) {
		return new DynamicDSLSelectImpl(dataSource, sqlDialect);
	}
	
	public static DynamicDSLSelect dynamicDsl(DataSource dataSource, SQLDialect sqlDialect, LoadingCache<String,QueryResult> cache) {
		return new DynamicDSLSelectImpl(dataSource, sqlDialect, cache);
	}
	
}
