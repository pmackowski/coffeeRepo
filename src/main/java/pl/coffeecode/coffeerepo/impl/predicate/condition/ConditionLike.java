package pl.coffeecode.coffeerepo.impl.predicate.condition;

public class ConditionLike extends BaseConditionWithValue {

	public ConditionLike(String column, String value) {
		super(column, value);
	}

	@Override
	public String toSQL() {
		return column + " like '%" + value + "%'";
	}
	
	@Override
	public String toString() {
		return "ConditionLike [column=" + column + ", value=" + value + "]";
	}

}
