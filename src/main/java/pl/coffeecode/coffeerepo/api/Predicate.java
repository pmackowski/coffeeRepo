package pl.coffeecode.coffeerepo.api;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.ImmutableList;

import pl.coffeecode.coffeerepo.impl.predicate.ConditionExpression;
import pl.coffeecode.coffeerepo.impl.predicate.OrderImpl;
import pl.coffeecode.coffeerepo.impl.predicate.SortOrder;
import pl.coffeecode.coffeerepo.impl.predicate.ConditionExpression.Operator;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.FormatDate;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.LowerCase;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.Money;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.UpperCase;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionBeginsWith;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionDoesNotBeginWith;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionDoesNotContain;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionGreater;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionGreaterOrEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNotNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLessOrEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionContains;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionNotEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLess;
import pl.coffeecode.coffeerepo.impl.predicate.rowfunction.Pattern;
import pl.coffeecode.coffeerepo.impl.predicate.rowfunction.Sum;

public class Predicate {
	
	public static Condition or(Condition... conditions) {	
		return new ConditionExpression(Operator.OR, ImmutableList.copyOf(conditions));
	}
	
	public static Condition and(Condition... conditions) {	
		return new ConditionExpression(Operator.AND, ImmutableList.copyOf(conditions));
	}
	
	public static Condition or(List<Condition> conditions) {	
		if (conditions.size() == 1) { // TODO
			return conditions.get(0);
		}
		return new ConditionExpression(Operator.OR, ImmutableList.copyOf(conditions));
	}
	
	public static Condition and(List<Condition> conditions) {
		if (conditions.size() == 1) { // TODO
			return conditions.get(0);
		}
		return new ConditionExpression(Operator.AND, ImmutableList.copyOf(conditions));
	}
	
	public static Condition not(Condition condition) {
		return new ConditionExpression(Operator.NOT, ImmutableList.of(condition));
	}

	public static Condition equal(String column, Object value) {
		return new ConditionEqual(column, value);
	}
		
	public static Condition notEqual(String column, Object value) {
		return new ConditionNotEqual(column, value);
	}
	
	public static Condition less(String column, Object value) {
		return new ConditionLess(column, value);
	}
	
	public static Condition lessOrEqual(String column, Object value) {
		return new ConditionLessOrEqual(column, value);
	}
	
	public static Condition greater(String column, Object value) {
		return new ConditionGreater(column, value);
	}
	
	/**
	 * ignorecase for strings
	 * @param column
	 * @param value
	 * @return
	 */
	public static Condition greaterOrEqual(String column, Object value) {
		return new ConditionGreaterOrEqual(column, value);
	}
	
	public static Condition beginsWith(String column, String value) {
		return new ConditionBeginsWith(column, value);
	}
	
	public static Condition doesNotBeginWith(String column, String value) {
		return new ConditionDoesNotBeginWith(column, value);
	}
	
	public static Condition contains(String column, String value) {
		return new ConditionContains(column, value);
	}
	
	public static Condition doesNotContain(String column, String value) {
		return new ConditionDoesNotContain(column, value);
	}
	
	public static Condition isNull(String column) {
		return new ConditionIsNull(column);
	}
	
	public static Condition isNotNull(String column) {
		return new ConditionIsNotNull(column);
	}
	
	/**
	 * sort in ascending order, nulls last
	 * @param column
	 * @return
	 */
	public static Order asc(String column) {
		return new OrderImpl(column, SortOrder.ASC);
	}
	
	/**
	 * sort in descending order, nulls last
	 * @param column
	 * @return
	 */
	public static Order desc(String column) {
		return new OrderImpl(column, SortOrder.DESC);
	}

	public static CellFunction<Object,String> lowerCase() {
		return new LowerCase();
	}
	
	public static CellFunction<Object,String> upperCase() {
		return new UpperCase();
	}
	
	public static CellFunction<Date,String> formatDate(String format) {
		return new FormatDate(format);
	}
	
	public static CellFunction<Number,String> money(Locale locale) {
		return new Money(locale);
	}
	
	public static RowFunction<String> pattern(String pattern, String... columns) {
		return new Pattern(pattern, columns);
	}
	
	public static RowFunction<Number> sum(String... columns) {
		return new Sum(columns);
	}
}
