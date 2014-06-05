package pl.coffeecode.coffeerepo.api;

import com.google.common.collect.Table;

public interface QueryResult {
	
	String sql();
	
	QueryAttributes attributes();
	
	Table<Integer,String,Object> items();
	
	Integer getTotalRecords();
	
}
