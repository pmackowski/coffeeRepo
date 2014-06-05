package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.DSLFrom;
import pl.coffeecode.coffeerepo.api.DSLWhere;

public class DSLFromImpl extends AbstractDSL implements DSLFrom {
	
	public DSLFromImpl(QueryExecutor delegate, String... columnNames) {
		super(delegate, columnNames); 
	}
	
	@Override
	public DSLWhere from(String viewName) {
		return (DSLWhere) new DSLWhereImpl(delegate, attributes.clone()).addFrom(viewName);
	}

}
