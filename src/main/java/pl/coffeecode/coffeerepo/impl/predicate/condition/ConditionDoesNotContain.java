package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionDoesNotContain extends BaseConditionWithValue {

	public ConditionDoesNotContain(String column, String value) {
		super(column, "%" + value + "%");
	}
	
	@Override
	public String toSQL(ConditionVisitor driver) {
		return driver.toSQL(this);
	}
	
	@Override
	public String toString() {
		return "ConditionDoesNotContain [column=" + column + ", value=" + value + "]";
	}

}