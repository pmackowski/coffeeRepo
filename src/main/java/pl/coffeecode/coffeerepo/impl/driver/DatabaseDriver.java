package pl.coffeecode.coffeerepo.impl.driver;

import java.util.List;

import pl.coffeecode.coffeerepo.api.QueryAttributes;

public interface DatabaseDriver {

    String createSQL(QueryAttributes attributes);

    String createCountSQL(QueryAttributes attributes);

    List<Object> convertBindValues(List<Object> bindValues);

}