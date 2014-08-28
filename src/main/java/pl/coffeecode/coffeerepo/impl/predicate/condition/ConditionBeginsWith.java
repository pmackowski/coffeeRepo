package pl.coffeecode.coffeerepo.impl.predicate.condition;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

public class ConditionBeginsWith extends BaseConditionWithValue {

    public ConditionBeginsWith(String column, String value) {
        super(column, value);
    }

    @Override
    public String toString() {
        return "ConditionBeginsWith [column=" + column + ", value=" + value + "]";
    }

    @Override
    public String toSQL(ConditionVisitor driver) {
        return driver.toSQL(this);
    }

}