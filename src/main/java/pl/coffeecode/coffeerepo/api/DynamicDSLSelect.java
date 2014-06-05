package pl.coffeecode.coffeerepo.api;

public interface DynamicDSLSelect {
	
	DynamicDSLFrom select(String... columnNames);

}
