package pl.coffeecode.coffeerepo.impl.predicate.cellfunction;

import pl.coffeecode.coffeerepo.api.CellFunction;

public class UpperCase implements CellFunction<Object,String> {
	
	@Override
	public String apply(Object input) {
		return input.toString().toUpperCase();
	}
}	