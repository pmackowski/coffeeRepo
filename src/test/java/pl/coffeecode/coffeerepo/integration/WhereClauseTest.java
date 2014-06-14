package pl.coffeecode.coffeerepo.integration;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_ID;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.and;
import static pl.coffeecode.coffeerepo.api.Predicate.equal;
import static pl.coffeecode.coffeerepo.api.Predicate.not;
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
public class WhereClauseTest extends DBUnitTest {
	
	private static final String DATASET_FILE = "dbunit/where.xml";
	
	@Test
	@Parameters(method = "databases")
	public void should_return_Pawel_Mackowski(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.where(and( equal(C_LAST_NAME, "Mackowski"), equal(C_NAME, "Pawel")))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(2);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String lastname = value.get(C_LAST_NAME).toString();
				String name = value.get(C_NAME).toString();
				return "Mackowski".equals(lastname) && "Pawel".equals(name);
			}
			
		});
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_return_all_persons_with_Mackowski_lastname_or_Boozydar_name(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.where(or( equal(C_LAST_NAME, "Mackowski"), equal(C_NAME, "Boozydar")))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(5);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String lastname = null;
				if (value.get(C_LAST_NAME) != null) {
					lastname = value.get(C_LAST_NAME).toString();
				}
				String name = value.get(C_NAME).toString();
				return "Mackowski".equals(lastname) || "Boozydar".equals(name);
			}
			
		});
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_return_all_persons_except_Mackowski_lastname(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.where(not( equal(C_LAST_NAME, "Mackowski")))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(6); // because two are null and also should not be returned
		assertThat(rows(result.items())).areNot(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String lastname = null;
				if (value.get(C_LAST_NAME) != null) {
					lastname = value.get(C_LAST_NAME).toString();
				}
				return "Mackowski".equals(lastname);
			}
			
		});
	}
	
	@Test
	@Parameters(method = "databases")
	public void should_return_Antek_Smietanka_and_not_Antek_Mackowski(SQLDialectDatasource dialectDatasource) {
		prepare(dialectDatasource);
		
		QueryResult result = dsl
				
				.select(C_ID, C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.where(and(equal(C_NAME, "Antek"), not( equal(C_LAST_NAME, "Mackowski"))))
				.getResult();
		
		assertThat(result.getTotalRecords()).isEqualTo(1);
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				String lastname = null;
				if (value.get(C_LAST_NAME) != null) {
					lastname = value.get(C_LAST_NAME).toString();
				}
				return "Smietanka".equals(lastname);
			}
			
		});
	}
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}
	
}
