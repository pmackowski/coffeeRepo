package pl.coffeecode.coffeerepo.api;

import com.google.common.collect.ImmutableList;

public interface QueryAttributes {

    String getViewName();

    ImmutableList<String> getColumns();

    Condition getCondition();

    ImmutableList<Order> getOrders();

    Integer getNumberOfRows();

    Integer getPage();

    Integer getOffset();

    ImmutableList<Object> getBindValues();
}
