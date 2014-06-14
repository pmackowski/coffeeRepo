package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionIsNotNull extends BaseCondition {

	public ConditionIsNotNull(String column) {
		super(column);
	}

	@Override
	public String toSQL(ConditionVisitor driver) {
		return driver.toSQL(this);
	}

	@Override
	public String toString() {
		return "ConditionIsNotNull [column=" + column + "]";
	}

}