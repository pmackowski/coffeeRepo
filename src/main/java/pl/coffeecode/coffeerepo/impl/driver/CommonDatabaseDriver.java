package pl.coffeecode.coffeerepo.impl.driver;

import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.api.QueryAttributes;

import com.google.common.collect.ImmutableList;

public abstract class CommonDatabaseDriver implements DatabaseDriver {
	
	private final static String EMPTY_STRING = "";
	
	protected abstract ConditionVisitor getConditionVisitor();
	
	protected String columnNames(QueryAttributes attributes) {
		return columnNames(attributes, false, false);
	}
	
	protected String columnNames(QueryAttributes attributes, boolean quoteAdded, boolean viewNameAdded) {
		StringBuilder builder = new StringBuilder();
		ImmutableList<String> columnNames = attributes.getColumns();
		String viewName = attributes.getViewName();
		String quoteOrEmpty = quoteAdded ? "\"" : EMPTY_STRING;
		String viewNameOrEmpty = viewNameAdded ? quoteOrEmpty + viewName + quoteOrEmpty + "." : EMPTY_STRING;
		for (String column : columnNames) {
			builder.append(viewNameOrEmpty).append(quoteOrEmpty).append(column).append(quoteOrEmpty).append(",");
		}
		builder.deleteCharAt(builder.length() -1);
		return builder.toString();
		
	}
	
	protected String selectWithFromClause(QueryAttributes attributes) {
		return selectWithFromClause(attributes, false, false);
	}
	
	protected String selectWithFromClause(QueryAttributes attributes, boolean quoteAdded, boolean viewNameAdded) {
		String viewName = attributes.getViewName();
		return new StringBuilder()
				.append("select ")
				.append(columnNames(attributes, quoteAdded, viewNameAdded))
				.append(" from " + viewName)
				.toString();
	}
	
	protected String selectCountWithFromClause(QueryAttributes attributes) {
		return "select count(*) from " + attributes.getViewName();
	}
	
	protected String whereClause(QueryAttributes attributes) {
		if (attributes.getCondition() == null ) {
			return EMPTY_STRING;
		}
		return " where " + attributes.getCondition().toSQL(getConditionVisitor());
	}
	
	protected String orderClause(QueryAttributes attributes) {
		if (attributes.getOrders().isEmpty()) {
			return EMPTY_STRING;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(" order by ");
		
		for (Order order : attributes.getOrders()) {
			builder.append(order.getColumn()).append(" ").append(order.getSortOrder().toSQL()).append(" NULLS LAST,");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
	
	@Override
	public String createCountSQL(QueryAttributes attributes) {
		return new StringBuilder()
			.append(selectCountWithFromClause(attributes))
			.append(whereClause(attributes))
			.toString();
		
	}
	
}
