package pl.coffeecode.coffeerepo.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static pl.coffeecode.coffeerepo.api.Predicate.asc;
import static pl.coffeecode.coffeerepo.api.Predicate.desc;
import static pl.coffeecode.coffeerepo.api.Predicate.eq;
import static pl.coffeecode.coffeerepo.api.Predicate.isNotNull;
import static pl.coffeecode.coffeerepo.api.Predicate.isNull;
import static pl.coffeecode.coffeerepo.api.Predicate.not;
import static pl.coffeecode.coffeerepo.api.Predicate.notEq;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.SQLDialect;
import pl.coffeecode.coffeerepo.impl.predicate.ConditionImpl;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNotNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionNotEqual;

import com.google.common.collect.ImmutableList;


public class QueryAttributesBuilderTest {
	/*
	QueryAttributesBuilder<DynamicDSLImpl> dynamicQuery = null;
	
	QueryAttributes attr = null;
	
	@Before
	public void autowire() {
		DataSource dataSource = mock(DataSource.class);
		SQLDialect dialect = SQLDialect.H2;
		
		dynamicQuery = new DynamicDSLImpl(dataSource, dialect);
		attr = dynamicQuery.attributes;
	}
	
	@Test
	public void select() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2");
		
		// then
		assertThat(attr.getColumns()).containsExactly("col1","col2");
		assertThat(attr.getViewName()).isNull();
	}
	
	@Test
	public void from() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1");
		
		// then
		assertThat(attr.getColumns()).containsExactly("col1","col2");
		assertThat(attr.getViewName()).isEqualTo("view1");
	}
	
	@Test
	public void where_eq() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(eq("col1", 7));
		
		// then
		assertThat(attr.getColumns()).containsExactly("col1","col2");
		assertThat(attr.getViewName()).isEqualTo("view1");
		assertThat(attr.getCondition()).isEqualTo(new ConditionEqual<Integer>("col1",7));
	}
	
	@Test
	public void where_notEq() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(notEq("col1", "abc"));
		
		// then
		assertThat(attr.getColumns()).containsExactly("col1","col2");
		assertThat(attr.getViewName()).isEqualTo("view1");
		assertThat(attr.getCondition()).isEqualTo(new ConditionNotEqual<String>("col1","abc"));
	}
	
	@Test
	public void where_isNull() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(isNull("col1"));
		
		// then
		assertThat(attr.getColumns()).containsExactly("col1","col2");
		assertThat(attr.getViewName()).isEqualTo("view1");
		assertThat(attr.getCondition()).isEqualTo(new ConditionIsNull("col1"));
	}
	
	@Test
	public void where_isNotNull() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(isNotNull("col1"));
		
		// then
		assertThat(attr.getColumns()).containsExactly("col1","col2");
		assertThat(attr.getViewName()).isEqualTo("view1");
		assertThat(attr.getCondition()).isEqualTo(new ConditionIsNotNull("col1"));
	}
	
	@Test
	public void where_not_condition() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(not(isNull("col1")));
		
		// then
		assertThat(attr.getCondition()).isEqualTo(
				new ConditionImpl(ConditionImpl.Operator.NOT, ImmutableList.of(isNull("col1")))
		);
	}
	
	@Test
	public void where_and_condition() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(isNull("col1"), eq("col2",4));
		
		// then
		assertThat(attr.getCondition()).isEqualTo(
				new ConditionImpl(ConditionImpl.Operator.AND, 
						ImmutableList.of(isNull("col1"), eq("col2",4))));
	}
	
	@Test
	public void where_or_condition() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(isNull("col1").or(eq("col2",4)));
		
		// then
		assertThat(attr.getCondition()).isEqualTo(
				new ConditionImpl(ConditionImpl.Operator.OR, ImmutableList.of(isNull("col1"), eq("col2",4))));
	}
	
	@Test
	public void where_nested_condition() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.where(not(isNull("col1").or(eq("col2",4).and(eq("col2","ww")))));
		
		// then
		assertThat(attr.getCondition()).isEqualTo(
				new ConditionImpl(ConditionImpl.Operator.NOT, 
						ImmutableList.<Condition>of(
								new ConditionImpl(ConditionImpl.Operator.OR, 
										ImmutableList.<Condition>of(isNull("col1"), 
												new ConditionImpl(ConditionImpl.Operator.AND,	
														ImmutableList.of(eq("col2",4),eq("col2","ww")))		
												)))));
	}
	
	@Test(expected=NullPointerException.class)
	public void should_order_by_null_thows_null_pointer_exception() {
		// when
		Order nullOrder = null;
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.orderBy(asc("col3"),nullOrder);
	}
	
	@Test
	public void order_by_one_predicate() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.orderBy(asc("col3"));
		// then
		assertThat(attr.getOrders()).containsExactly(asc("col3"));
	}
	
	@Test
	public void order_by_many_predicates() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.orderBy(asc("col3"),desc("col1"),asc("col4"));
		// then
		assertThat(attr.getOrders()).containsExactly(asc("col3"),desc("col1"),asc("col4"));
	}
		
	@Test
	public void limit_number_of_rows() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.limit(10);
		// then
		assertThat(attr.getNumberOfRows()).isEqualTo(10);		
	}
	
	@Test
	public void limit_number_of_rows_and_offset() {
		// when
		dynamicQuery = dynamicQuery
						.select("col1","col2")
						.from("view1")
						.limit(10, 5);
		// then
		assertThat(attr.getNumberOfRows()).isEqualTo(10);
		assertThat(attr.getOffset()).isEqualTo(5);

	}
	
	@Test(expected=IllegalStateException.class)
	public void should_too_many_select_throw_illegal_state_exception() {
		dynamicQuery
			.select("col1","col2")
			.select("col13")
			.from("view1");
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_too_many_from_throw_illegal_state_exception() {
		dynamicQuery
			.select("col1","col2")
			.from("view1")
			.from("view2");
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_too_many_where_throw_illegal_state_exception() {
		dynamicQuery
			.select("col1","col2")
			.from("view1")
			.where(eq("col2",5))
			.where(eq("col1",4));
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_too_many_order_by_throw_illegal_state_exception() {
		dynamicQuery
			.select("col1","col2")
			.from("view1")
			.orderBy(asc("col1"))
			.orderBy(desc("col2"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_too_many_limit_throw_illegal_state_exception() {
		dynamicQuery
			.select("col1","col2")
			.from("view1")
			.limit(5)
			.limit(66);
	}
	*/
}
