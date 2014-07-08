package pl.coffeecode.coffeerepo.integration.condition;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_HAS_CAR;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.equal;

import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.QueryResult;

@RunWith(JUnitParamsRunner.class)
public class ConditionEqualTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/condition_equal.xml";

	@Test
	@Parameters(method = "databases")
	public void should_be_equal(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
				
				.select(C_NAME)
				.from(VIEW_NAME)
				.where(equal(C_NAME, "Pawel"))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(2);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String name = value.get(C_NAME).toString();
				return name.equals("Pawel");
			}
			
		});
	
	}
	
	@Ignore
	@Test
	@Parameters(method = "databases")
	public void should_be_equal_to_boolean_true(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
				
				.select(C_NAME,C_HAS_CAR)
				.from(VIEW_NAME)
				.where(equal(C_HAS_CAR, true))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(3);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return (Boolean) value.get(C_HAS_CAR) == true;
			}
			
		});
	
	}
	
	@Ignore
	@Test
	@Parameters(method = "databases")
	public void should_be_equal_to_boolean_false(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
				
				.select(C_NAME,C_HAS_CAR)
				.from(VIEW_NAME)
				.where(equal(C_HAS_CAR, false))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(1);
		assertThat(rows(result.items())).areNot(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return (Boolean) value.get(C_HAS_CAR);
			}
			
		});
	
	}

	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
}
