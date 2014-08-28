package pl.coffeecode.coffeerepo.api;

public interface DSLOrderBy extends DSLLimit {

    DSLLimit orderBy(Order order, Order... orders);

}
