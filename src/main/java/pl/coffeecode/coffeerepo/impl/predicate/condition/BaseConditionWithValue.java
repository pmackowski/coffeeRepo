package pl.coffeecode.coffeerepo.impl.predicate.condition;

import com.google.common.collect.ImmutableList;

public abstract class BaseConditionWithValue extends BaseCondition {
	
	protected Object value;

	protected BaseConditionWithValue(String column, Object value) {
		super(column);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public ImmutableList<Object> getBindValues() {
		return ImmutableList.of(value);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseConditionWithValue other = (BaseConditionWithValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
