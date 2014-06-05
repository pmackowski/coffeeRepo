package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.Excutable;
import pl.coffeecode.coffeerepo.api.QueryResult;

public class ExcutableImpl extends AbstractDSL implements Excutable {

	ExcutableImpl(QueryExecutor delegate, QueryAttributesImpl attributes) {
		super(delegate, attributes);
	}

	@Override
	public QueryResult getResult() {
		return delegate.getResult(attributes);
	}

}
