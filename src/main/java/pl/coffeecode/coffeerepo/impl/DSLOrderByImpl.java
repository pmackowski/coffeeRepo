package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.DSLLimit;
import pl.coffeecode.coffeerepo.api.DSLOrderBy;
import pl.coffeecode.coffeerepo.api.Order;

public class DSLOrderByImpl extends DSLLimitImpl implements DSLOrderBy {

	DSLOrderByImpl(QueryExecutor delegate, QueryAttributesImpl attributes) {
		super(delegate, attributes);
	}

	@Override
	public DSLLimit orderBy(Order order, Order... orders) {
		return (DSLLimit) new DSLLimitImpl(delegate, attributes.clone()).addOrderBy(order, orders);
	}

}
