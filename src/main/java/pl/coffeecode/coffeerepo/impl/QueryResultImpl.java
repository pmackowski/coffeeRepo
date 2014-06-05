package pl.coffeecode.coffeerepo.impl;

import java.util.Map;
import java.util.Map.Entry;

import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;

import com.google.common.collect.Table;

public class QueryResultImpl implements QueryResult {
	
	private Table<Integer,String,Object> items;
	private String sql;
	private QueryAttributes queryAttributes;
	private Integer totalRecords;
	
	public QueryResultImpl(Table<Integer,String,Object> items, String sql, QueryAttributes queryAttributes, Integer totalRecords) {
		this.items = items;
		this.sql = sql;
		this.queryAttributes = queryAttributes;
		this.totalRecords = totalRecords;
	}

	public Table<Integer,String,Object> items() {
		return items;
	}

	public String sql() {
		return sql;
	}
	
	public QueryAttributes attributes() {
		return queryAttributes;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("QueryResultImpl [\n");
		builder.append("sql = ").append(sql).append("\n");
		builder.append("queryAttributes = ").append(queryAttributes).append("\n");
		
		for (Entry<Integer, Map<String, Object>> entry : items().rowMap().entrySet()) {
			builder.append("\t").append(entry).append("\n");
		}
		builder.append("]");
		return builder.toString();

	}
	
}
