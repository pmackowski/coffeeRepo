package pl.coffeecode.coffeerepo.impl.predicate;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.coffeecode.coffeerepo.api.Order;

import java.util.Objects;

public class OrderImpl implements Order {
	
	private final String column;
	private final SortOrder sortOrder;
	
	public OrderImpl(String column, SortOrder sortOrder) {
		checkNotNull(column);
		checkNotNull(sortOrder);
		this.column = column;
		this.sortOrder = sortOrder;
	}

	public String getColumn() {
		return column;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

    @Override
    public int hashCode() {
        return Objects.hash(column, sortOrder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OrderImpl other = (OrderImpl) obj;
        return Objects.equals(this.column, other.column) && Objects.equals(this.sortOrder, other.sortOrder);
    }

    @Override
	public String toString() {
		return "OrderImpl [column=" + column + ", sortOrder=" + sortOrder + "]";
	}
	
}
