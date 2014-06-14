package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.api.Condition;

import com.google.common.collect.ImmutableList;

public abstract class BaseCondition implements Condition {
	
	protected String column;
	
	public ImmutableList<Object> getBindValues() {
		return ImmutableList.of();
	}
	
	protected BaseCondition(String column) {
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseCondition other = (BaseCondition) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		return true;
	}
	
}
