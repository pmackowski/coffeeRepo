package pl.coffeecode.coffeerepo.api;

import pl.coffeecode.coffeerepo.impl.predicate.SortOrder;

public interface Order {

    String getColumn();

    SortOrder getSortOrder();

}
