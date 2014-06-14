package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionNotEqual extends BaseConditionWithValue {
	
	public ConditionNotEqual(String column, Object value) {
		super(column, value);
	}
	
	@Override
	public String toSQL(ConditionVisitor driver) {
		return driver.toSQL(this);
	}
	
	@Override
	public String toString() {
		return "ConditionNotEqual [column=" + column + ", value=" + value + "]";
	}

}