package pl.coffeecode.coffeerepo.impl.predicate.rowfunction;

import java.util.Map;

import pl.coffeecode.coffeerepo.api.RowFunction;

public class Nvl implements RowFunction<Object> {
	
	private final String column;
	private final Object defaultValue;
	
	public Nvl(String column, Object defaultValue) {
		this.column = column;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public Object apply(Map<String, Object> input) {
		if (input.containsKey(column)) {
			return input.get(column); 
		}
		return defaultValue;
	}

}
