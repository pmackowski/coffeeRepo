package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.api.Condition;

import com.google.common.collect.ImmutableList;

import java.util.Objects;

public abstract class BaseCondition implements Condition {

    protected String column;

    protected BaseCondition(String column) {
        this.column = column;
    }

    public ImmutableList<Object> getBindValues() {
        return ImmutableList.of();
    }

    public String getColumn() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BaseCondition other = (BaseCondition) obj;
        return Objects.equals(this.column, other.column);
    }

}
