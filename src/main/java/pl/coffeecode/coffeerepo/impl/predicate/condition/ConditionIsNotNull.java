package pl.coffeecode.coffeerepo.impl.predicate.condition;

public class ConditionIsNotNull extends BaseCondition {

	public ConditionIsNotNull(String column) {
		super(column);
	}

	@Override
	public String toSQL() {
		return column + " IS NOT NULL";
	}

	@Override
	public String toString() {
		return "ConditionIsNotNull [column=" + column + "]";
	}

}