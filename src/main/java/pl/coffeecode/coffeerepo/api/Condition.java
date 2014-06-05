package pl.coffeecode.coffeerepo.api;

public interface Condition {
	
	Condition and(Condition cond);
	
	Condition or(Condition cond);

	Condition not();
	
	String toSQL();
	
}
