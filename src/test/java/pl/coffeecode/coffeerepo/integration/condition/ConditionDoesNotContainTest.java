package pl.coffeecode.coffeerepo.integration.condition;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.doesNotContain;

import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.QueryResult;

@RunWith(JUnitParamsRunner.class)
public class ConditionDoesNotContainTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/condition_does_not_contain.xml";

	@Test
	@Parameters(method = "databases")
	public void does_not_contain_should_ignore_case(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		QueryResult result = dsl
				
				.select(C_NAME)
				.from(VIEW_NAME)
				.where(doesNotContain(C_NAME, "an"))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(3);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String name = value.get(C_NAME).toString().toUpperCase();
				return ! name.contains("AN");
			}
			
		});
	
	}
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
}
