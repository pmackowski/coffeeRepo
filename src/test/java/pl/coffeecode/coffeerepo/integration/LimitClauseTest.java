package pl.coffeecode.coffeerepo.integration;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_ID;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.asc;

import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.QueryResult;

@RunWith(JUnitParamsRunner.class)
public class LimitClauseTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/limit.xml";
	
	private final int TOTAL_RECORDS_IN_LIMIT_XML = 12;
	
	@Test
	@Parameters(method = "databases")
	public void should_return_first_page(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		int numberOfRows = 10;
		int page = 1;
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.limit(numberOfRows, page)
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(TOTAL_RECORDS_IN_LIMIT_XML);
		assertThat(rows(result.items())).hasSize(10);
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_return_middle_page(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		int numberOfRows = 4;
		int page = 2;
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.limit(numberOfRows, page)
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(TOTAL_RECORDS_IN_LIMIT_XML);
		assertThat(rows(result.items())).hasSize(4);
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_return_last_page(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		int numberOfRows = 9;
		int page = 2;
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.limit(numberOfRows, page)
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(TOTAL_RECORDS_IN_LIMIT_XML);
		assertThat(rows(result.items())).hasSize(3);
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_return_no_records_for_non_existing_page(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		int numberOfRows = 8;
		int page = 200;
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.limit(numberOfRows, page)
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(TOTAL_RECORDS_IN_LIMIT_XML);
		assertThat(rows(result.items())).hasSize(0);
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_consult_order_by_clause(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		int numberOfRows = 2;
		int page = 1;
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(asc(C_LAST_NAME))
				.limit(numberOfRows, page)
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(TOTAL_RECORDS_IN_LIMIT_XML);
		assertThat(rows(result.items())).hasSize(2);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String lastname = value.get(C_LAST_NAME).toString();
				return "Augustyniak".equals(lastname) || "Abacki".equals(lastname);
			}
			
		});
	}
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
	
}
