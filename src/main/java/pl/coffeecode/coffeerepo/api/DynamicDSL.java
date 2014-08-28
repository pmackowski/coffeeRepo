package pl.coffeecode.coffeerepo.api;

public interface DynamicDSL extends Excutable {

    DynamicDSL where(Condition condition);

    DynamicDSL whereIgnoreNulls(Condition... conditions);

    DynamicDSL orderBy(Order order, Order... orders);

    DynamicDSL appendOrderBy(Order... orders);

    DynamicDSL appendOrderByIgnoreNulls(Order... orders);

    DynamicDSL limit(int numberOfRows, int page);
}
