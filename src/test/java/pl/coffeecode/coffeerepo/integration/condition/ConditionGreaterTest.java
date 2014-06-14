package pl.coffeecode.coffeerepo.integration.condition;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_AGE;
import static pl.coffeecode.coffeerepo.Constants.C_DATE_OF_BIRTH;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.greater;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.QueryResult;

@RunWith(JUnitParamsRunner.class)
public class ConditionGreaterTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/condition_greater.xml";

	@Test
	@Parameters(method = "databases")
	public void should_return_greater_than_35(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
				
				.select(C_AGE)
				.from(VIEW_NAME)
				.where(greater(C_AGE, 35))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(1);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				Number age = (Number) value.get(C_AGE);
				return age.intValue() > 35;
			}
			
		});
	
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_ignore_case_when_comparing_strings(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		QueryResult result = dsl
				
				.select(C_NAME)
				.from(VIEW_NAME)
				.where(greater(C_NAME, "kabana"))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(4);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String name = value.get(C_NAME).toString().toUpperCase();
				return name.compareTo("KABANA") > 0;
			}
			
		});
	
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_return_greater_than_date(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1981);
		QueryResult result = dsl
				
				.select(C_DATE_OF_BIRTH, C_AGE)
				.from(VIEW_NAME)
				.where(greater(C_DATE_OF_BIRTH, new java.sql.Date(cal.getTime().getTime()))) // necessary for Oracle
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(5);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				Date dateOfBirth = (Date) value.get(C_DATE_OF_BIRTH);
				return cal.getTime().before(dateOfBirth);
			}
			
		});
	
	}
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
}