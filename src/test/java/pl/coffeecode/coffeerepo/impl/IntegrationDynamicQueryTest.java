package pl.coffeecode.coffeerepo.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.DATASET_FILE;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.eq;

import java.util.List;
import java.util.Map;

import org.fest.assertions.core.Condition;
import org.junit.Test;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.api.DynamicDSLSelect;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.api.SQLDialect;
import pl.coffeecode.coffeerepo.api.ViewRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Table;

public class IntegrationDynamicQueryTest extends DBUnitTest{
	
	private DynamicDSLSelect dsl = ViewRepository.dynamicDsl(dataSource, SQLDialect.H2);
	
	@Test
	public void whereWithEqPredicate() {
		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.where(eq(C_LAST_NAME, "Mackowski"))
				.getResult();
		
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return "Mackowski".equals(value.get(C_LAST_NAME));
			}
			
		});
	
	}
	
	private abstract class RowCondition extends Condition<Map<String,Object>> {
		
	}
	
	private abstract class RowOrdering extends Ordering<Map<String, Object>> {
		
	}
	
	private List<Map<String, Object>> rows(Table<Integer,String,Object> items) {
		return Lists.newArrayList(items.rowMap().values());
	}
	
	@Override
	protected String getDatasetFilePath() {
		return DATASET_FILE;
	}

}
