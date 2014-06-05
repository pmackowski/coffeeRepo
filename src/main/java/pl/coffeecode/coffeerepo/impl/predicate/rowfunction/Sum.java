package pl.coffeecode.coffeerepo.impl.predicate.rowfunction;

import java.util.Map;

import pl.coffeecode.coffeerepo.api.RowFunction;

public class Sum implements RowFunction<Number> {
	
	private String[] columns;
	
	public Sum(String[] columns) {
		this.columns = columns;
	}

	@Override
	public Number apply(Map<String, Object> input) {
		int sum = 0;
		for (String column : columns) {
			sum += (Integer) (input.get(column) == null ? 0 : input.get(column));
		}
		return sum;
	}

}
