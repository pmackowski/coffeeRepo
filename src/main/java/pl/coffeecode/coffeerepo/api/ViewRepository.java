package pl.coffeecode.coffeerepo.api;

import javax.sql.DataSource;

import pl.coffeecode.coffeerepo.impl.DSLImpl;
import pl.coffeecode.coffeerepo.impl.DynamicDSLSelectImpl;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriverResolver;

import com.google.common.cache.Cache;

public abstract class ViewRepository {
	
	public static DSL dsl(DataSource dataSource) {
		return new DSLImpl(dataSource, resolveSQLDialect(dataSource));
	}

	public static DSL dsl(DataSource dataSource, Cache<QueryAttributes,QueryResult> cache) {
		return new DSLImpl(dataSource, resolveSQLDialect(dataSource), cache);
	}

	public static DynamicDSLSelect dynamicDsl(DataSource dataSource) {
		return new DynamicDSLSelectImpl(dataSource, resolveSQLDialect(dataSource));
	}
	
	public static DynamicDSLSelect dynamicDsl(DataSource dataSource, Cache<QueryAttributes,QueryResult> cache) {
		return new DynamicDSLSelectImpl(dataSource, resolveSQLDialect(dataSource), cache);
	}

	private static DatabaseDriver resolveSQLDialect(DataSource dataSource) {
		return new DatabaseDriverResolver().resolveDatabase(dataSource);
	}
	
}
