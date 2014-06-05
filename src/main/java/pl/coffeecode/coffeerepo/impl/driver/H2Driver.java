package pl.coffeecode.coffeerepo.impl.driver;

import java.util.List;

import pl.coffeecode.coffeerepo.api.DBDriver;
import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.api.QueryAttributes;

import com.google.common.collect.ImmutableList;

public class H2Driver implements DBDriver {

	@Override
	public String createSQL(QueryAttributes attributes) {
		
		String viewName = attributes.getViewName();
		ImmutableList<String> columnNames = attributes.getColumns(); 
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");

		for (String column : columnNames) {
				sb.append("\"").append(viewName).append("\".\"").append(column).append("\"").append(",");
		}
		sb.deleteCharAt(sb.length() -1);
		
		sb.append(" from " + viewName);
		
		if (attributes.getCondition() != null ) {
			sb.append(" where " + attributes.getCondition().toSQL());
		}	
		
		if (! attributes.getOrders().isEmpty()) {
			sb.append(order(attributes.getOrders()));
		}
		
		if (attributes.getNumberOfRows() != null) {
			int offset = attributes.getNumberOfRows() * (attributes.getPage() - 1);
			sb.append(" limit ").append(attributes.getNumberOfRows())
			  .append(" offset ").append(offset); 
		}
		
		return sb.toString();
	}
	
	@Override
	public String createCountSQL(QueryAttributes attributes) {
		
		String viewName = attributes.getViewName();
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from " + viewName);
		if (attributes.getCondition() != null ) {
			sb.append(" where " + attributes.getCondition().toSQL());
		}	
		return sb.toString();
	}
	
	
	private String order(List<Order> orders) {
		StringBuilder builder = new StringBuilder();
		builder.append(" order by ");
		
		for (Order order : orders) {
			builder.append(order.getColumn()).append(" ").append(order.getSortOrder().toSQL()).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

}
