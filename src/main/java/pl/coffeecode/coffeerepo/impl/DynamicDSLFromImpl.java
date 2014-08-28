package pl.coffeecode.coffeerepo.impl;

import pl.coffeecode.coffeerepo.api.DynamicDSL;
import pl.coffeecode.coffeerepo.api.DynamicDSLFrom;

public class DynamicDSLFromImpl extends AbstractDSL implements DynamicDSLFrom {

    public DynamicDSLFromImpl(QueryExecutor delegate, String... columnNames) {
        super(delegate, columnNames);
    }

    @Override
    public DynamicDSL from(String viewName) {
        return (DynamicDSL) new DynamicDSLImpl(delegate, attributes.clone()).addFrom(viewName);
    }

}
