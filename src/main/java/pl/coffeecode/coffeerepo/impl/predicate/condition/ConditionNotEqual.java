package pl.coffeecode.coffeerepo.impl.predicate.condition;

public class ConditionNotEqual extends BaseConditionWithValue {
	
	public ConditionNotEqual(String column, Object value) {
		super(column, value);
	}

	@Override
	public String toSQL() {
		return column + "!='" + value + "'";
	}

	@Override
	public String toString() {
		return "ConditionNotEqual [column=" + column + ", value=" + value + "]";
	}

}