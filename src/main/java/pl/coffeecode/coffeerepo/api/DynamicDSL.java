package pl.coffeecode.coffeerepo.api;

public interface DynamicDSL extends Excutable {

	DynamicDSL where(Condition condition, Condition... conditions);
	
	DynamicDSL whereIgnoreNulls(Condition... conditions);
	
	DynamicDSL and(Condition condition);
	
	DynamicDSL or(Condition condition);
	
	DynamicDSL orderBy(Order order, Order... orders);
	
	DynamicDSL appendOrderBy(Order... orders);
	
	DynamicDSL appendOrderByIgnoreNulls(Order... orders);
	
	DynamicDSL limit(int numberOfRows, int page);
}
