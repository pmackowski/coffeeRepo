package pl.coffeecode.coffeerepo.impl.predicate.cellfunction;

import java.text.NumberFormat;
import java.util.Locale;

import pl.coffeecode.coffeerepo.api.CellFunction;

public class Money implements CellFunction<Number, String> {
	
	private Locale locale;
	
	public Money(Locale locale) {
		this.locale = locale;
	}
	
	@Override
	public String apply(Number input) {
		NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
		return fmt.format(input);
	}

}
