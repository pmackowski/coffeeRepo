package pl.coffeecode.coffeerepo.impl.predicate;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.coffeecode.coffeerepo.api.Order;

public class OrderImpl implements Order {
	
	private final String column;
	private final SortOrder sortOrder;
	
	public OrderImpl(String column, SortOrder sortOrder) {
		checkNotNull(column);
		checkNotNull(sortOrder);
		//checkArgument(isSafe(column));
		this.column = column;
		this.sortOrder = sortOrder;
	}
	
	/*private boolean isSafe(String column) {
		return column.;
	}*/

	public String getColumn() {
		return column;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result
				+ ((sortOrder == null) ? 0 : sortOrder.hashCode());
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
		OrderImpl other = (OrderImpl) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (sortOrder != other.sortOrder)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderImpl [column=" + column + ", sortOrder=" + sortOrder + "]";
	}
	
}
