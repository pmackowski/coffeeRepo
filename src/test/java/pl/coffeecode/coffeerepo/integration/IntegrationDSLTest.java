package pl.coffeecode.coffeerepo.integration;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_AGE;
import static pl.coffeecode.coffeerepo.Constants.C_DATE_OF_BIRTH;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.asc;
import static pl.coffeecode.coffeerepo.api.Predicate.desc;
import static pl.coffeecode.coffeerepo.api.Predicate.equal;
import static pl.coffeecode.coffeerepo.api.Predicate.isNull;
import static pl.coffeecode.coffeerepo.api.Predicate.or;

import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.QueryResult;

@RunWith(JUnitParamsRunner.class)
public class IntegrationDSLTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/integration.xml";
	
	@Test
	@Parameters(method = "databases")
	public void everything_put_together(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		QueryResult result = dsl
				.select(C_NAME,C_LAST_NAME, C_AGE,C_DATE_OF_BIRTH)
				.from(VIEW_NAME)
				.where(
						or(equal(C_LAST_NAME, "Mackowski"),isNull(C_AGE)))
				.orderBy(
						asc(C_LAST_NAME), 
						desc(C_AGE))
				.limit(5,2)
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(10);
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrderingNullsLast(asc(C_LAST_NAME),desc(C_AGE)));
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				Object lastname = value.get(C_LAST_NAME);
				Object age = value.get(C_AGE);
				return "Mackowski".equals(lastname) || age == null; 
			}
			
		});
	}
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
}
