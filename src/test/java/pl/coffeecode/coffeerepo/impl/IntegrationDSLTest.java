package pl.coffeecode.coffeerepo.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_AGE;
import static pl.coffeecode.coffeerepo.Constants.C_BONUS;
import static pl.coffeecode.coffeerepo.Constants.C_DATE_OF_BIRTH;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_LOGIN;
import static pl.coffeecode.coffeerepo.Constants.C_LAST_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.C_SALARY;
import static pl.coffeecode.coffeerepo.Constants.DATASET_FILE;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.asc;
import static pl.coffeecode.coffeerepo.api.Predicate.desc;
import static pl.coffeecode.coffeerepo.api.Predicate.eq;
import static pl.coffeecode.coffeerepo.api.Predicate.formatDate;
import static pl.coffeecode.coffeerepo.api.Predicate.isNotNull;
import static pl.coffeecode.coffeerepo.api.Predicate.isNull;
import static pl.coffeecode.coffeerepo.api.Predicate.like;
import static pl.coffeecode.coffeerepo.api.Predicate.lowerCase;
import static pl.coffeecode.coffeerepo.api.Predicate.not;
import static pl.coffeecode.coffeerepo.api.Predicate.notEq;
import static pl.coffeecode.coffeerepo.api.Predicate.pattern;
import static pl.coffeecode.coffeerepo.api.Predicate.sum;
import static pl.coffeecode.coffeerepo.api.Predicate.upperCase;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fest.assertions.core.Condition;
import org.junit.Before;
import org.junit.Test;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.api.CellFunction;
import pl.coffeecode.coffeerepo.api.DSL;
import pl.coffeecode.coffeerepo.api.QueryResult;
import pl.coffeecode.coffeerepo.api.QueryResultDecorator;
import pl.coffeecode.coffeerepo.api.SQLDialect;
import pl.coffeecode.coffeerepo.api.ViewRepository;

import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Table;

public class IntegrationDSLTest extends DBUnitTest {
	
	private DSL dsl;

	CellFunction<Date,Date> february = new CellFunction<Date, Date>() {

		@Override
		public Date apply(Date input) {
			input.setMonth(1);
			return input;
		}
	};
	
	@Before
	public void init() {
		dsl = ViewRepository.dsl(dataSource, SQLDialect.H2);
	}
	
	@Test
	public void whereWithLikePredicate() {
		
		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.where(like(C_LAST_NAME, "ckowsk"))
				.getResult();
		
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return value.get(C_LAST_NAME).toString().contains("ckowsk");
			}
			
		});
	
	}
	
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
	
	@Test
	public void whereEqPredicateIsComparedWithInteger() {
		
		QueryResult result = dsl
					
				.select(C_NAME)
				.from(VIEW_NAME)
				.where(
					eq(C_AGE, 99))
				.getResult();
		
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return "Gabrysia".equals(value.get(C_NAME));
			}
			
		});

	}
	
	@Test
	public void whereWithNotEqPredicate() {
		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.where(notEq(C_LAST_NAME, "Mackowski"))
				.getResult();
		
		assertThat(rows(result.items())).areNot(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return "Mackowski".equals(value.get(C_LAST_NAME));
			}
			
		});
	}
	
	@Test
	public void whereWithIsNullPredicate() {
		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME,C_AGE)
				.from(VIEW_NAME)
				.where(isNull(C_AGE))
				.getResult();
		
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return value.get(C_AGE) == null;
			}
			
		});
	}
	
	@Test
	public void whereWithIsNotNullPredicate() {
		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME,C_AGE)
				.from(VIEW_NAME)
				.where(isNotNull(C_AGE))
				.getResult();
		
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return value.get(C_AGE) != null;
			}
			
		});
	}
	
	@Test
	public void whereAnyOfWithEqAndIsNullPredicates() {
		
		QueryResult result = dsl
					
				.select(C_NAME,C_LAST_NAME,C_AGE)
				.from(VIEW_NAME)
				.where( eq(C_LAST_NAME, "Mackowski"))
					.or(isNull(C_AGE))
				.getResult();
		
		assertThat(rows(result.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return "Mackowski".equals(value.get(C_LAST_NAME)) || value.get(C_AGE) == null;
			}
			
		});

	}
	
	@Test
	public void orderByTwoColumns() {
		
		QueryResult result = dsl
					
				.select(C_NAME,C_LAST_NAME)
				.from(VIEW_NAME)
				.orderBy(
						asc(C_LAST_NAME),
						desc(C_NAME))		
				.getResult();
		
		assertThat(rows(result.items())).isSortedAccordingTo(new RowOrdering() {

			@Override
			public int compare(Map<String, Object> left, Map<String, Object> right) {
				int ret = ((String) left.get(C_LAST_NAME)).compareTo((String) right.get(C_LAST_NAME));
				if (ret == 0) {
					ret = ((String) right.get(C_NAME)).compareTo((String) left.get(C_NAME));
				}
				return ret;
			}
			
		});
	}
	
	@Test
	public void everythingPutTogether() {
		QueryResult result = dsl
				.select(C_NAME,C_LAST_NAME, C_AGE,C_DATE_OF_BIRTH)
				.from(VIEW_NAME)
				.where(
						not(eq(C_LAST_NAME, "Mackowska").or(isNull(C_AGE))))
				.orderBy(
						asc(C_LAST_NAME), 
						desc(C_AGE))
				.limit(10,1)
				.getResult();
		
		System.out.println(result);
	}
	
	
	@Test
	public void whereWithEqwdPredicate() {
		
		String DATE_FORMAT = "yyyy-MMM-dd";

		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME, C_AGE, C_DATE_OF_BIRTH, C_LAST_LOGIN, C_SALARY, C_BONUS)
				.from(VIEW_NAME)
				.where(eq(C_LAST_NAME, "Mackowski"))
				.getResult();
	
		QueryResult decoratedResult = QueryResultDecorator.decorate(result)
				.column("FULL_NAME", 				pattern("%s %s (%s)", C_NAME, C_LAST_NAME, C_AGE))
				.columnNvl("BONUS",				    5)
				.column("ALL_SALARY",			 	sum(C_SALARY, C_BONUS))
				.column(C_LAST_NAME, 				upperCase())
				.column(C_DATE_OF_BIRTH, 			formatDate(DATE_FORMAT))
				.columns(lowerCase(), "FULL_NAME", C_NAME)
				.removeColumns(C_LAST_LOGIN)		
				.build();
		
				
		System.out.println(result.items());
		System.out.println(decoratedResult.items());
		
		assertThat(rows(decoratedResult.items())).are(new RowCondition() {

			@Override
			public boolean matches(Map<String,Object> value) {
				return "MACKOWSKI".equals(value.get(C_LAST_NAME));
			}
			
		});
	
	}
/*
	@Test
	public void whereWithEqwdPredicatefwe() {
		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME, C_AGE, C_DATE_OF_BIRTH, C_SALARY, C_BONUS)
				.from(VIEW_NAME)
				.limit(2)
				.getResult();
		
		QueryResult decoratedResult = QueryResultDecorator.decorate(result)
				.column("FULL_NAME", 	pattern("%s %s (%s)", C_NAME, C_LAST_NAME, C_AGE))
				.column("FULL_SALARY",  sum(C_SALARY, C_BONUS))
				.build();
		
		// grupowanie (header, funkcja grupowania, sortowanie, czy mozna zwijac/rozwijac)
		String grid = GridJsonBuilder.with(configuration, decoratedResult)
				.column("FULL_NAME", 		"Osoba",		  	CustomType.UPPER_STR)
				.column(C_DATE_OF_BIRTH,  	"Data urodzenia", 	CustomType.DATE)
				.column("SALARY", 	  		"Zarobki", 			CustomType.MONEY)
				.toJson();
		
		System.out.println(grid);
	
	}
	
	@Test
	public void whereWifwethEqwdPredicatefwe() {
		QueryResult result = dsl
				
				.select(C_NAME,C_LAST_NAME, C_AGE, C_DATE_OF_BIRTH, C_SALARY, C_BONUS)
				.from(VIEW_NAME)
				.limit(2)
				.getResult();
		
		QueryResult decoratedResult = QueryResultDecorator.decorate(result)
				.column("FULL_NAME", 	pattern("%s %s (%s)", C_NAME, C_LAST_NAME, C_AGE))
				.column("FULL_SALARY",  sum(C_SALARY, C_BONUS))
				.build();
		
		// grupowanie (header, funkcja grupowania, sortowanie, czy mozna zwijac/rozwijac)
		String grid = GridJsonBuilder.with(configuration, decoratedResult)
				.column("FULL_NAME", 		"Osoba",		  	CustomType.UPPER_STR)
				.column(C_DATE_OF_BIRTH,  	"Data urodzenia", 	CustomType.DATE)
				.column("SALARY", 	  		"Zarobki", 			CustomType.MONEY)
				.toJson();
		
		System.out.println(grid);
	
	}
	
	
	@Test
	public void wherceeWithEqwdPredicatefwe() {
		Grid grid = new Grid(configuration);
		grid.addHeader("FULL_NAME", 		"Osoba", 			CustomType.UPPER_STR);
		grid.addHeader(C_DATE_OF_BIRTH,  	"Data urodzenia", 	CustomType.DATE);
		grid.addHeader("FULL_SALARY", 	  	"Zarobki", 			CustomType.MONEY);
		
		grid.addRow("Pawel Mackowski", "23/12/1981", "10000");
		grid.addRow("Piotr Mackowski", "23/10/1981", "12000");
		
		String jsonGrid = GridJsonBuilder.with(grid).toJson();
		
		System.out.println(jsonGrid);
	}
	*/
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
