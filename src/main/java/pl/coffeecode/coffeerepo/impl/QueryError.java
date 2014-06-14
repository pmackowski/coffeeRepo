package pl.coffeecode.coffeerepo.impl;

import java.util.List;

public class QueryError extends RuntimeException {
	
	private static final long serialVersionUID = -1051579606360530992L;
	
	public QueryError(String sql, List<Object> bindValues, Throwable throwable) {
		super(String.format("SQL [%s] with bind values %s", sql, bindValues), throwable);
	}
	
}
