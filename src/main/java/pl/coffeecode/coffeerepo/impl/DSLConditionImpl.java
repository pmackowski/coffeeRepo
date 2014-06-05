package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.api.DSLCondition;

public class DSLConditionImpl extends DSLOrderByImpl implements DSLCondition {

	DSLConditionImpl(QueryExecutor delegate, QueryAttributesImpl attributes) {
		super(delegate, attributes);
	}

	@Override
	public DSLCondition and(Condition condition) {
		return (DSLCondition) new DSLConditionImpl(delegate, attributes.clone()).addAnd(condition);
	}

	@Override
	public DSLCondition or(Condition condition) {
		return (DSLCondition) new DSLConditionImpl(delegate, attributes.clone()).addOr(condition);
	}

}
