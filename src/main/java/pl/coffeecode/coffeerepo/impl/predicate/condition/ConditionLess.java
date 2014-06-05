package pl.coffeecode.coffeerepo.impl.predicate.condition;

public class ConditionLess extends BaseConditionWithValue {
	
	public ConditionLess(String column, Object value) {
		super(column, value);
	}

	@Override
	public String toSQL() {
		return column + " < " + value ;
	}
	
	@Override
	public String toString() {
		return "LessCondition [column=" + column + ", value=" + value + "]";
	}
	
}
