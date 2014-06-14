package pl.coffeecode.coffeerepo.impl.driver;

import static pl.coffeecode.coffeerepo.api.Predicate.*;
import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.impl.driver.h2.H2Driver;

import com.google.common.collect.ImmutableList;

@RunWith(JUnitParamsRunner.class)
public class CommonDatabaseDriverTest {

	private CommonDatabaseDriver commonDriver = new H2Driver();
	
    public Object[] columnNames() {
    	boolean quoteAdded = true;
    	boolean viewNameAdded = true;
        return $( 
        		$(asList("col1"),		  	!quoteAdded, 	!viewNameAdded, 	"col1"),
        		$(asList("col1"),		  	quoteAdded, 	viewNameAdded, 		"\"view1\".\"col1\""),
        		$(asList("col1"),		  	quoteAdded, 	!viewNameAdded, 	"\"col1\""),
        		$(asList("col1", "col2"),  	quoteAdded, 	viewNameAdded, 		"\"view1\".\"col1\",\"view1\".\"col2\""),
        		$(asList("col1", "col2"), 	!quoteAdded, 	viewNameAdded, 		"view1.col1,view1.col2"),
        		$(asList("col1", "col2"),  	quoteAdded, 	!viewNameAdded, 	"\"col1\",\"col2\""),
        		$(asList("col1", "col2"),  	!quoteAdded, 	!viewNameAdded, 	"col1,col2")
        );
    }
	
	@Test
	@Parameters(method = "columnNames")
	public void should_create_column_names(List<String> columns, boolean quoteAdded, boolean viewNameAdded, String expected) {
		// given
		QueryAttributes attributes = mock(QueryAttributes.class);
		when(attributes.getColumns()).thenReturn(ImmutableList.copyOf(columns));
		when(attributes.getViewName()).thenReturn("view1");
		// when
		String actual = commonDriver.columnNames(attributes, quoteAdded, viewNameAdded);
		// then
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void should_create_select_with_from_clause() {
		// given
		QueryAttributes attributes = mock(QueryAttributes.class);
		when(attributes.getColumns()).thenReturn(ImmutableList.of("col1"));
		when(attributes.getViewName()).thenReturn("view1");
		// when
		String actual = commonDriver.selectWithFromClause(attributes);
		// then
		assertThat(actual).isEqualTo("select col1 from view1");
	}
	
    public Object[] orders() {
        return $( 
        		$(ImmutableList.<Order>of(),			""),
        		$(asList(asc("col1")),					" order by col1 asc NULLS LAST"),
        		$(asList(asc("col1"), desc("col2")),	" order by col1 asc NULLS LAST,col2 desc NULLS LAST")
        );
    }
	
	@Test
	@Parameters(method = "orders")
	public void should_create_order_clause(List<Order> orders, String expected) {
		// given
		QueryAttributes attributes = mock(QueryAttributes.class);
		when(attributes.getOrders()).thenReturn(ImmutableList.copyOf(orders));
		// when
		String actual = commonDriver.orderClause(attributes);
		// then
		assertThat(actual).isEqualTo(expected);
	}
}
