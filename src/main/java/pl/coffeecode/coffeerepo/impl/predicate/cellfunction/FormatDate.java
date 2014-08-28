package pl.coffeecode.coffeerepo.impl.predicate.cellfunction;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.coffeecode.coffeerepo.api.CellFunction;

public class FormatDate implements CellFunction<Date, String> {

    private final String format;

    public FormatDate(String format) {
        this.format = format;
    }

    @Override
    public String apply(Date input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(input);
    }
}
