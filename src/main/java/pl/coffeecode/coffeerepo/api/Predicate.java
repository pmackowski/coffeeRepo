package pl.coffeecode.coffeerepo.api;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.ImmutableList;

import pl.coffeecode.coffeerepo.impl.predicate.ConditionImpl;
import pl.coffeecode.coffeerepo.impl.predicate.OrderImpl;
import pl.coffeecode.coffeerepo.impl.predicate.SortOrder;
import pl.coffeecode.coffeerepo.impl.predicate.ConditionImpl.Operator;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.FormatDate;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.LowerCase;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.Money;
import pl.coffeecode.coffeerepo.impl.predicate.cellfunction.UpperCase;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNotNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLike;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionNotEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLess;
import pl.coffeecode.coffeerepo.impl.predicate.rowfunction.Pattern;
import pl.coffeecode.coffeerepo.impl.predicate.rowfunction.Sum;

public class Predicate {
	
	public static Condition or(List<Condition> conditions) {	
		if (conditions.size() == 1) { // TODO
			return conditions.get(0);
		}
		return new ConditionImpl(Operator.OR, ImmutableList.copyOf(conditions));
	}
	
	public static Condition and(List<Condition> conditions) {
		if (conditions.size() == 1) { // TODO
			return conditions.get(0);
		}
		return new ConditionImpl(Operator.AND, ImmutableList.copyOf(conditions));
	}
	
	public static Condition not(Condition condition) {
		return condition.not();
	}

	public static Condition eq(String column, Object value) {
		return new ConditionEqual(column, value);
	}
		
	public static Condition notEq(String column, Object value) {
		return new ConditionNotEqual(column, value);
	}
	
	public static ConditionLike like(String column, String value) {
		return new ConditionLike(column, value);
	}
	
	public static ConditionLess less(String column, Object value) {
		return new ConditionLess(column, value);
	}
	
	public static Condition isNull(String column) {
		return new ConditionIsNull(column);
	}
	
	public static Condition isNotNull(String column) {
		return new ConditionIsNotNull(column);
	}
	
	public static Order asc(String column) {
		return new OrderImpl(column, SortOrder.ASC);
	}
	
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
