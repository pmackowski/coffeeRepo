package pl.coffeecode.coffeerepo.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;

import com.google.common.cache.Cache;

public class QueryCache extends QueryExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryCache.class);

    private final Cache<QueryAttributes, QueryResult> cache;

    QueryCache(DataSource dataSource, DatabaseDriver sqlDialect, Cache<QueryAttributes, QueryResult> cache) {
        super(dataSource, sqlDialect);
        checkNotNull(cache);
        this.cache = cache;
    }

    @Override
    public QueryResult getResult(final QueryAttributes attributes) {
        try {
            return (QueryResult) cache.get(attributes,
                    new Callable<QueryResult>() {
                        @Override
                        public QueryResult call() {
                            return QueryCache.super.getResult(attributes);
                        }
                    });
        } catch (ExecutionException ex) {
            LOGGER.warn("Problem with loading from cache, we are trying to retrive data from database!");
            return super.getResult(attributes);
        }
    }

}
