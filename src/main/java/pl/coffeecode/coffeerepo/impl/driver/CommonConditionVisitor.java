package pl.coffeecode.coffeerepo.impl.driver;

import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionBeginsWith;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionContains;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionDoesNotBeginWith;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionDoesNotContain;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionGreater;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionGreaterOrEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNotNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLess;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLessOrEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionNotEqual;

public class CommonConditionVisitor implements ConditionVisitor {

	@Override
	public String toSQL(ConditionBeginsWith condition) {
		return "upper(" + condition.getColumn() + ") like upper(? || '%')";
	}

	@Override
	public String toSQL(ConditionContains condition) {
		return "upper(" + condition.getColumn() + ") like upper('%' || ? || '%')";
	}

	@Override
	public String toSQL(ConditionDoesNotBeginWith condition) {
		return "upper(" + condition.getColumn() + ") not like upper(? || '%')";
	}

	@Override
	public String toSQL(ConditionDoesNotContain condition) {
		return "upper(" + condition.getColumn() + ") not like upper('%' || ? || '%')";
	}

	@Override
	public String toSQL(ConditionEqual condition) {
		return condition.getColumn() + "= ?";
	}

	@Override
	public String toSQL(ConditionGreater condition) {
		Object value = condition.getValue();
		if (value instanceof String) {
			return "upper(" + condition.getColumn() + ") > upper(?)";
		}
		return condition.getColumn() + "> ?";
	}

	@Override
	public String toSQL(ConditionGreaterOrEqual condition) {
		Object value = condition.getValue();
		if (value instanceof String) {
			return "upper(" + condition.getColumn() + ") >= upper(?)";
		}
		return condition.getColumn() + ">= ?";
	}

	@Override
	public String toSQL(ConditionIsNotNull condition) {
		return condition.getColumn() + " IS NOT NULL";
	}

	@Override
	public String toSQL(ConditionIsNull condition) {
		return condition.getColumn() + " IS NULL";
	}

	@Override
	public String toSQL(ConditionLess condition) {
		Object value = condition.getValue();
		if (value instanceof String) {
			return "upper(" + condition.getColumn() + ") < upper(?)";
		}
		return condition.getColumn() + "< ?";
	}

	@Override
	public String toSQL(ConditionLessOrEqual condition) {
		Object value = condition.getValue();
		if (value instanceof String) {
			return "upper(" + condition.getColumn() + ") <= upper(?)";
		}
		return condition.getColumn() + "<= ?";
	}

	@Override
	public String toSQL(ConditionNotEqual condition) {
		return condition.getColumn() + "!=?";
	}
	
}
