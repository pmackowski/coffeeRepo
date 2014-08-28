package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.DSLLimit;
import pl.coffeecode.coffeerepo.api.Excutable;

public class DSLLimitImpl extends ExcutableImpl implements DSLLimit {

    DSLLimitImpl(QueryExecutor delegate, QueryAttributesImpl attributes) {
        super(delegate, attributes);
    }

    @Override
    public Excutable limit(int numberOfRows, int page) {
        return (Excutable) new ExcutableImpl(delegate, attributes.clone()).addLimit(numberOfRows, page);
    }

}
