package pl.coffeecode.coffeerepo.api;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.coffeecode.coffeerepo.api.Predicate.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

public class QueryResultDecoratorTest {
	
	private final int TOTAL_RECORDS = 3;
	
	private QueryResult queryResult = mock(QueryResult.class);
	private Table<Integer, String, Object> table = HashBasedTable.create();
	private CellFunction<Number, Number> doubleAge = new CellFunction<Number, Number>() {

		@Override
		public Number apply(Number input) {
			if (input == null) {
				return 0;
			}
			return input.intValue() * 2;
		}
	};
	
	@Before
	public void init() {
		table.put(1, "NAME", "Pawel");
		table.put(1, "LAST_NAME", "Mackowski");
		table.put(1, "SALARY", 100);
		table.put(1, "BONUS", 20);
		
		table.put(2, "NAME", "Pawel");
		table.put(2, "LAST_NAME", "Buzek");
		table.put(2, "AGE", 20);
		table.put(2, "SALARY", 70);
		
		table.put(3, "NAME", "Michal");
		table.put(3, "LAST_NAME", "Morek");
		table.put(3, "AGE", new java.math.BigDecimal(20));
		table.put(3, "SALARY", new java.math.BigDecimal(20));
		
		when(queryResult.items()).thenReturn(ImmutableTable.copyOf(table));
		when(queryResult.sql()).thenReturn("sql");
		when(queryResult.attributes()).thenReturn(mockAttributes());
	}
	
	@Test
	public void should_apply_cell_function_only_if_column_value_exists() {
		
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("AGE", doubleAge).build();
		
		assertThat(rows(decorated.items())).hasSize(TOTAL_RECORDS);
		assertThat(decorated.items().get(1, "AGE")).isNull();
		assertThat(decorated.items().get(2, "AGE")).isEqualTo(40);
		assertThat(decorated.items().get(3, "AGE")).isEqualTo(40);
	}
	
	@Test
	public void should_apply_row_function_for_existing_column_values() {
		
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("SALARY_AND_BONUS", sum("SALARY","BONUS")).build();
		
		assertThat(rows(decorated.items())).hasSize(TOTAL_RECORDS);
		assertThat(decorated.items().get(1, "SALARY_AND_BONUS")).isEqualTo(120);
		assertThat(decorated.items().get(2, "SALARY_AND_BONUS")).isEqualTo(70);
		assertThat(decorated.items().get(3, "SALARY_AND_BONUS")).isEqualTo(20);
	}
	
	@Test
	public void should_override_current_value() {
		
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("NAME", "Kowalski").build();
		
		assertThat(rows(decorated.items())).hasSize(TOTAL_RECORDS);
		assertThat(decorated.items().get(1, "NAME")).isEqualTo("Kowalski");
		assertThat(decorated.items().get(2, "NAME")).isEqualTo("Kowalski");
	}
	
	@Test
	public void should_not_modify_input_query_result() {
		
		QueryResult before = queryResult;
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
				.column("NAME", "Kowalski")
				.column("SALARY_AND_BONUS", sum("SALARY","BONUS"))
				.removeColumns("LAST_NAME")
				.build();
		
		assertThat(rows(decorated.items())).hasSize(TOTAL_RECORDS);
		assertThat(before).isEqualTo(queryResult).isNotEqualTo(decorated);
	}
	
	@Test
	public void should_nvl_set_only_nullls_to_default_value() {
		
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
				.columnNvl("AGE", -1)
				.build();
		
		assertThat(decorated.items().get(1, "AGE")).isEqualTo(-1);
		assertThat(decorated.items().get(2, "AGE")).isEqualTo(20);
	}
	
	@Test
	public void should_apply_pattern_function() {
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("LAST_NAME", pattern("Person: %s", "LAST_NAME")).build();
		
		assertThat(rows(decorated.items())).hasSize(TOTAL_RECORDS);
		assertThat(decorated.items().get(1, "LAST_NAME")).isEqualTo("Person: Mackowski");
		assertThat(decorated.items().get(2, "LAST_NAME")).isEqualTo("Person: Buzek");
		assertThat(decorated.items().get(3, "LAST_NAME")).isEqualTo("Person: Morek");
	}
	
	@Test
	public void should_apply_lowercase_function() {
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("LAST_NAME", lowerCase()).build();
		
		assertThat(rows(decorated.items())).hasSize(TOTAL_RECORDS);
		assertThat(decorated.items().get(1, "LAST_NAME")).isEqualTo("mackowski");
		assertThat(decorated.items().get(2, "LAST_NAME")).isEqualTo("buzek");
		assertThat(decorated.items().get(3, "LAST_NAME")).isEqualTo("morek");
	}
	
	@Test
	public void should_apply_uppercase_function() {
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("LAST_NAME", upperCase()).build();
		
		assertThat(rows(decorated.items())).hasSize(TOTAL_RECORDS);
		assertThat(decorated.items().get(1, "LAST_NAME")).isEqualTo("MACKOWSKI");
		assertThat(decorated.items().get(2, "LAST_NAME")).isEqualTo("BUZEK");
		assertThat(decorated.items().get(3, "LAST_NAME")).isEqualTo("MOREK");
	}
	
	private List<Map<String, Object>> rows(Table<Integer,String,Object> items) {
		return Lists.newArrayList(items.rowMap().values());
	}
	
	private QueryAttributes mockAttributes() {
		return new QueryAttributes() {
			
			@Override
			public String getViewName() {
				return "viewName";
			}
			
			@Override
			public Integer getPage() {
				return null;
			}
			
			@Override
			public ImmutableList<Order> getOrders() {
				return ImmutableList.of();
			}
			
			@Override
			public Integer getNumberOfRows() {
				return null;
			}
			
			@Override
			public Condition getCondition() {
				return null;
			}
			
			@Override
			public ImmutableList<String> getColumns() {
				return ImmutableList.of("NAME", "LAST_NAME", "SALARY", "BONUS");
			}
			
			@Override
			public ImmutableList<Object> getBindValues() {
				return ImmutableList.of();
			}

			@Override
			public Integer getOffset() {
				return null;
			}
		};
	}
	
}
