package pl.coffeecode.coffeerepo.impl.predicate.rowfunction;

import java.util.Map;

import pl.coffeecode.coffeerepo.api.RowFunction;

public class Pattern implements RowFunction<String> {
	
	private final String pattern;
	private final String[] columns;
	
	public Pattern(String pattern, String[] columns) {
		this.pattern = pattern;
		this.columns = columns;
	}

	@Override
	public String apply(Map<String, Object> input) {
		String ret = pattern;
		for (String column : columns) {
			ret = ret.replaceFirst("%s", input.get(column) == null ? "" : input.get(column).toString());
		}
		//return String.format(pattern, columns);
		return ret;
	}

}
