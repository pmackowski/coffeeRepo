package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.api.DSLOrderBy;
import pl.coffeecode.coffeerepo.api.DSLWhere;

public class DSLWhereImpl extends DSLOrderByImpl implements DSLWhere {
	
	DSLWhereImpl(QueryExecutor delegate, QueryAttributesImpl attributes) {
		super(delegate, attributes);
	}

	@Override
	public DSLOrderBy where(Condition condition) {
		return (DSLOrderBy) new DSLOrderByImpl(delegate, attributes.clone()).addWhere(condition);
	}

}
