package pl.coffeecode.coffeerepo.integration;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.asc;
import static pl.coffeecode.coffeerepo.api.Predicate.equal;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.DSL;
import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.api.ViewRepository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@RunWith(JUnitParamsRunner.class)
public class QueryCacheTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/integration.xml";
	
	private Cache<QueryAttributes, QueryResult> cache = CacheBuilder.newBuilder()
		       .maximumSize(1000)
		       .build();
	
	@Test
	@Parameters(method = "databases")
	public void should_put_once_the_same_query_into_cache(SQLDialectDatasource dialectDatasource) {
		// given
		prepare(dialectDatasource);
		DSL cachedDsl = ViewRepository.dsl(dialectDatasource.dataSource(), dialectDatasource.dialect(), cache);
		// when && then	
		assertThat(cache.size()).isEqualTo(0);
		cachedDsl.select(C_NAME,C_LAST_NAME).from(VIEW_NAME).getResult();
		assertThat(cache.size()).isEqualTo(1);
		cachedDsl.select(C_NAME,C_LAST_NAME).from(VIEW_NAME).getResult();
		assertThat(cache.size()).isEqualTo(1);
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_put_different_bindings_into_different_caches(SQLDialectDatasource dialectDatasource) {
		// given
		prepare(dialectDatasource);
		DSL cachedDsl = ViewRepository.dsl(dialectDatasource.dataSource(), dialectDatasource.dialect(), cache);
		// when && then	
		assertThat(cache.size()).isEqualTo(0);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).where(equal(C_NAME, "Pawel")).getResult();
		assertThat(cache.size()).isEqualTo(1);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).where(equal(C_NAME, "Jan")).getResult();
		assertThat(cache.size()).isEqualTo(2);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).where(equal(C_NAME, "Pawel")).getResult();
		assertThat(cache.size()).isEqualTo(2);
	}
	
	
	@Test
	@Parameters(method = "databases")
	public void should_put_different_order_by_into_different_caches(SQLDialectDatasource dialectDatasource) {
		// given
		prepare(dialectDatasource);
		DSL cachedDsl = ViewRepository.dsl(dialectDatasource.dataSource(), dialectDatasource.dialect(), cache);
		// when && then	
		assertThat(cache.size()).isEqualTo(0);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).orderBy(asc(C_LAST_NAME)).getResult();
		assertThat(cache.size()).isEqualTo(1);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).orderBy(asc(C_NAME)).getResult();
		assertThat(cache.size()).isEqualTo(2);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).orderBy(asc(C_LAST_NAME)).getResult();
		assertThat(cache.size()).isEqualTo(2);
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_put_different_limit_into_different_caches(SQLDialectDatasource dialectDatasource) {
		// given
		prepare(dialectDatasource);
		DSL cachedDsl = ViewRepository.dsl(dialectDatasource.dataSource(), dialectDatasource.dialect(), cache);
		// when && then	
		assertThat(cache.size()).isEqualTo(0);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).limit(3,2).getResult();
		assertThat(cache.size()).isEqualTo(1);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).limit(3,5).getResult();
		assertThat(cache.size()).isEqualTo(2);
		cachedDsl.select(C_LAST_NAME).from(VIEW_NAME).limit(3,2).getResult();
		assertThat(cache.size()).isEqualTo(2);
	}
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
	
}
