package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionEqual extends BaseConditionWithValue {

    public ConditionEqual(String column, Object value) {
        super(column, value);
    }

    @Override
    public String toSQL(ConditionVisitor driver) {
        return driver.toSQL(this);
    }

    @Override
    public String toString() {
        return "ConditionEqual [column=" + column + ", value=" + value + "]";
    }

}