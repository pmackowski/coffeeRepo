package pl.coffeecode.coffeerepo.impl.predicate.condition;

public class ConditionEqual extends BaseConditionWithValue {
	
	public ConditionEqual(String column, Object value) {
		super(column, value);
	}

	@Override
	public String toSQL() {
		return column + "='" + value + "'";
	}
	
	@Override
	public String toString() {
		return "ConditionEqual [column=" + column + ", value=" + value + "]";
	}
	
}