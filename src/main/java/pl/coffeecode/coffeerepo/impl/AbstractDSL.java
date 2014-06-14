package pl.coffeecode.coffeerepo.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.api.QueryAttributes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class AbstractDSL {
	
	private static final String TOO_MANY_INVOCATIONS_MSG = "too many invocations of method '%s'";
	
	protected QueryExecutor delegate;
	protected QueryAttributesImpl attributes;
	
	public AbstractDSL(QueryExecutor delegate, String... columns) {
		this.delegate = delegate;
		this.attributes = new QueryAttributesImpl(columns);
	}
	
	public AbstractDSL(QueryExecutor delegate, QueryAttributesImpl attributes) {
		this.delegate = delegate;
		this.attributes = attributes;
	}
	
	protected AbstractDSL addFrom(String viewName) {
		checkInvocations(attributes.viewName, "from");
		checkNotNull(viewName);
		attributes.viewName = viewName;
		return this;
	}
	
	protected AbstractDSL addWhere(Condition condition) {
		checkInvocations(attributes.condition, "where");
		checkNotNull(condition);
		attributes.condition = condition;
		return this;
	}
	
	protected AbstractDSL addOrderBy(Order order, Order... orders) {
		checkInvocations(attributes.orders, "orderBy");
		attributes.orders = ImmutableList.<Order>builder()
				.add(order)
				.addAll(Lists.newArrayList(orders))
				.build();
		return this;
	}
	
	protected AbstractDSL addLimit(int numberOfRows, int page) {
		checkInvocations(attributes.numberOfRows, "limit");
		checkArgument(numberOfRows > 0, "not positive numberOfRows: %s", numberOfRows);
		checkArgument(page > 0, "not positive page: %s", page);

		attributes.numberOfRows = numberOfRows;
		attributes.page = page;
		return this;
	}
	
	protected void checkInvocations(Integer i, String methodName) {
		checkState(i == null, TOO_MANY_INVOCATIONS_MSG, methodName);
	}
	
	protected void checkInvocations(String str, String methodName) {
		checkState(str == null, TOO_MANY_INVOCATIONS_MSG, methodName);
	}
	
	protected void checkInvocations(Condition condition, String methodName) {
		checkState(condition == null, TOO_MANY_INVOCATIONS_MSG, methodName);
	}
	
	protected void checkInvocations(ImmutableList<?> list, String methodName) {
		checkState(list.isEmpty(), TOO_MANY_INVOCATIONS_MSG, methodName);
	}
	
	protected static class QueryAttributesImpl implements QueryAttributes, Cloneable {
		
		protected ImmutableList<String> columns = ImmutableList.of();
		
		protected String viewName;
		
		protected Integer numberOfRows;
		
		protected Integer page;
		
		protected Condition condition;
		
		protected ImmutableList<Order> orders = ImmutableList.of(); 
		
		public QueryAttributesImpl(String... columns) {
			this.columns = ImmutableList.copyOf(columns);
		}
		
		private QueryAttributesImpl(ImmutableList<String> columns,
				String viewName, Integer numberOfRows, Integer page,
				Condition condition, ImmutableList<Order> orders) {
			this.columns = columns;
			this.viewName = viewName;
			this.numberOfRows = numberOfRows;
			this.page = page;
			this.condition = condition;
			this.orders = orders;
		}

		@Override
		public String getViewName() {
			return viewName;
		}

		@Override
		public ImmutableList<String> getColumns() {
			return columns;
		}
		
		@Override
		public Condition getCondition() {
			return condition;
		}
		
		@Override
		public ImmutableList<Object> getBindValues() {
			return condition == null ? ImmutableList.of() : condition.getBindValues();
		}
		
		@Override
		public ImmutableList<Order> getOrders() {
			return orders;
		}
		
		@Override
		public Integer getNumberOfRows() {
			return numberOfRows;
		}
		
		@Override
		public Integer getPage() {
			return page;
		}
		
		@Override
		public Integer getOffset() {
			if (numberOfRows == null || page == null) {
				return null;
			}
			return numberOfRows * (page - 1);
		}
		
		public QueryAttributesImpl clone() {
			return new QueryAttributesImpl(columns, viewName, numberOfRows, page, condition, orders);
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((columns == null) ? 0 : columns.hashCode());
			result = prime * result
					+ ((condition == null) ? 0 : condition.hashCode());
			result = prime * result
					+ ((numberOfRows == null) ? 0 : numberOfRows.hashCode());
			result = prime * result
					+ ((orders == null) ? 0 : orders.hashCode());
			result = prime * result + ((page == null) ? 0 : page.hashCode());
			result = prime * result
					+ ((viewName == null) ? 0 : viewName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			QueryAttributesImpl other = (QueryAttributesImpl) obj;
			if (columns == null) {
				if (other.columns != null)
					return false;
			} else if (!columns.equals(other.columns))
				return false;
			if (condition == null) {
				if (other.condition != null)
					return false;
			} else if (!condition.equals(other.condition))
				return false;
			if (numberOfRows == null) {
				if (other.numberOfRows != null)
					return false;
			} else if (!numberOfRows.equals(other.numberOfRows))
				return false;
			if (orders == null) {
				if (other.orders != null)
					return false;
			} else if (!orders.equals(other.orders))
				return false;
			if (page == null) {
				if (other.page != null)
					return false;
			} else if (!page.equals(other.page))
				return false;
			if (viewName == null) {
				if (other.viewName != null)
					return false;
			} else if (!viewName.equals(other.viewName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "QueryAttributesImpl [columns=" + columns + ", viewName="
					+ viewName + ", numberOfRows=" + numberOfRows + ", page="
					+ page + ", condition=" + condition + ", bindValues=" + getBindValues() + ", orders="
					+ orders + "]";
		}

	}
	
}
