package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionIsNull extends BaseCondition {

    public ConditionIsNull(String column) {
        super(column);
    }

    @Override
    public String toSQL(ConditionVisitor driver) {
        return driver.toSQL(this);
    }

    @Override
    public String toString() {
        return "ConditionIsNull [column=" + column + "]";
    }

}
