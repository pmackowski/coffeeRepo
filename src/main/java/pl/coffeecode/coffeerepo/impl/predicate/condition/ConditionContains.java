package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionContains extends BaseConditionWithValue {

	public ConditionContains(String column, String value) {
		super(column, value);
	}
	
	@Override
	public String toSQL(ConditionVisitor driver) {
		return driver.toSQL(this);
	}
	
	@Override
	public String toString() {
		return "ConditionContains [column=" + column + ", value=" + value + "]";
	}

}
