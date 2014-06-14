package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionDoesNotBeginWith extends BaseConditionWithValue {

	public ConditionDoesNotBeginWith(String column, String value) {
		super(column, value);
	}
	
	@Override
	public String toSQL(ConditionVisitor driver) {
		return driver.toSQL(this);
	}
	
	@Override
	public String toString() {
		return "ConditionDoesNotBeginWith [column=" + column + ", value=" + value + "]";
	}

}