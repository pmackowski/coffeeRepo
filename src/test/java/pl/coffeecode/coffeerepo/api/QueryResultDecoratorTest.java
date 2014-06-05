package pl.coffeecode.coffeerepo.api;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.coffeecode.coffeerepo.api.Predicate.sum;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.coffeecode.coffeerepo.api.CellFunction;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.api.QueryResultDecorator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

public class QueryResultDecoratorTest {
	
	private QueryResult queryResult = mock(QueryResult.class);
	private Table<Integer, String, Object> table = HashBasedTable.create();
	private CellFunction<Integer, Integer> doubleAge = new CellFunction<Integer, Integer>() {

		@Override
		public Integer apply(Integer input) {
			return input * 2;
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
		when(queryResult.items()).thenReturn(ImmutableTable.copyOf(table));
	}
	
	@Test
	public void should_apply_cell_function_only_if_column_value_exists() {
		
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("AGE", doubleAge).build();
		
		assertThat(rows(decorated.items())).hasSize(2);
		assertThat(decorated.items().get(1, "AGE")).isNull();
		assertThat(decorated.items().get(2, "AGE")).isEqualTo(40);
	}
	
	@Test
	public void should_apply_row_function_for_existing_column_values() {
		
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("SALARY_AND_BONUS", sum("SALARY","BONUS")).build();
		
		assertThat(rows(decorated.items())).hasSize(2);
		assertThat(decorated.items().get(1, "SALARY_AND_BONUS")).isEqualTo(120);
		assertThat(decorated.items().get(2, "SALARY_AND_BONUS")).isEqualTo(70);
	}
	
	@Test
	public void should_override_current_value() {
		
		QueryResult decorated = QueryResultDecorator.decorate(queryResult)
		 	.column("NAME", "Kowalski").build();
		
		assertThat(rows(decorated.items())).hasSize(2);
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
		
		assertThat(rows(decorated.items())).hasSize(2);
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
	
	private List<Map<String, Object>> rows(Table<Integer,String,Object> items) {
		return Lists.newArrayList(items.rowMap().values());
	}
	
}
