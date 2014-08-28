package pl.coffeecode.coffeerepo.impl.predicate.cellfunction;

import pl.coffeecode.coffeerepo.api.CellFunction;

@SuppressWarnings("rawtypes")
public class Identity implements CellFunction {

    @Override
    public Object apply(Object input) {
        return input;
    }

}
