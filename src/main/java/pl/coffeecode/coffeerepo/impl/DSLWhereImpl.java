package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.api.DSLCondition;
import pl.coffeecode.coffeerepo.api.DSLWhere;

public class DSLWhereImpl extends DSLConditionImpl implements DSLWhere {
	
	DSLWhereImpl(QueryExecutor delegate, QueryAttributesImpl attributes) {
		super(delegate, attributes);
	}

	@Override
	public DSLCondition where(Condition condition, Condition... conditions) {
		return (DSLCondition) new DSLConditionImpl(delegate, attributes.clone()).addWhere(condition, conditions);
	}

}
