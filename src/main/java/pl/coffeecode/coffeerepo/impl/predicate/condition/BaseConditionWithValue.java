package pl.coffeecode.coffeerepo.impl.predicate.condition;

import com.google.common.collect.ImmutableList;

import java.util.Objects;

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
        return 31 * super.hashCode() + Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final BaseConditionWithValue other = (BaseConditionWithValue) obj;
        return Objects.equals(this.value, other.value);
    }

}
