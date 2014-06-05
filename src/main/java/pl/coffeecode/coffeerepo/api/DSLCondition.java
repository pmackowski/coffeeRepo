package pl.coffeecode.coffeerepo.api;

public interface DSLCondition extends DSLOrderBy {
	
	DSLCondition and(Condition condition);
	
	DSLCondition or(Condition condition);
}
