package pl.coffeecode.coffeerepo.integration;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_DATE_OF_BIRTH;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_AGE;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.asc;
import static pl.coffeecode.coffeerepo.api.Predicate.desc;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.QueryResult;

@RunWith(JUnitParamsRunner.class)
public class OrderByClauseTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/order_by.xml";
	
	@Test
	@Parameters(method = "databases")
	public void order_by_string_asc_nulls_last(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						asc(C_LAST_NAME))
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(asc(C_LAST_NAME)));
	}
	
	@Test
	@Parameters(method = "databases")
	public void order_by_string_desc_nulls_last(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						desc(C_LAST_NAME))
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(desc(C_LAST_NAME)));
	}
	
	@Test
	@Parameters(method = "databases")
	public void order_by_date_asc_nulls_last(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_DATE_OF_BIRTH,C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						asc(C_DATE_OF_BIRTH))		
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(asc(C_DATE_OF_BIRTH)));
	}
	
	@Test
	@Parameters(method = "databases")
	public void order_by_date_desc_nulls_last(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_DATE_OF_BIRTH,C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						desc(C_DATE_OF_BIRTH))		
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(desc(C_DATE_OF_BIRTH)));
	}
	
	@Test
	@Parameters(method = "databases")
	public void order_by_number_asc_nulls_last(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_AGE,C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						asc(C_AGE))		
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(asc(C_AGE)));
	}
	
	@Test
	@Parameters(method = "databases")
	public void order_by_number_desc_nulls_last(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_AGE,C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						desc(C_AGE))		
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(desc(C_AGE)));
	}
	
	@Test
	@Parameters(method = "databases")
	public void order_by_two_string_columns(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						asc(C_LAST_NAME),
						desc(C_NAME))		
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(asc(C_LAST_NAME),desc(C_NAME)));
	}
	
	@Test
	@Parameters(method = "databases")
	public void order_by_date_and_string_columns(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
					
				.select(C_DATE_OF_BIRTH,C_LAST_NAME, C_NAME)
				.from(VIEW_NAME)
				.orderBy(
						asc(C_DATE_OF_BIRTH),
						desc(C_LAST_NAME))		
				.getResult();
		
		assertThat(result.items().isEmpty()).isFalse();
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(asc(C_DATE_OF_BIRTH),desc(C_LAST_NAME)));
	}
	
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
	
}

