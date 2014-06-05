package pl.coffeecode.coffeerepo.api;

public interface DSLWhere extends DSLCondition {
	
	DSLCondition where(Condition condition, Condition... conditions);

}
