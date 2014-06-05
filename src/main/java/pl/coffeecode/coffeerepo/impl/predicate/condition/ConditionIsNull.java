package pl.coffeecode.coffeerepo.impl.predicate.condition;

public class ConditionIsNull extends BaseCondition {
	
	public ConditionIsNull(String column) {
		super(column);
	}

	@Override
	public String toSQL() {
		return column + " IS NULL";
	}

	@Override
	public String toString() {
		return "ConditionIsNull [column=" + column + "]";
	}

}
