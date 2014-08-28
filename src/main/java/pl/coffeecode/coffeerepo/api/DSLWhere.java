package pl.coffeecode.coffeerepo.api;

public interface DSLWhere extends DSLOrderBy {

    DSLOrderBy where(Condition condition);

}
