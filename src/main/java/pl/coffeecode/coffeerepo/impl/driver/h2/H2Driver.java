package pl.coffeecode.coffeerepo.impl.driver.h2;

import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.impl.driver.CommonDatabaseDriver;
import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;

public class H2Driver extends CommonDatabaseDriver implements DatabaseDriver {

    @Override
    public String createSQL(QueryAttributes attributes) {

        StringBuilder builder = new StringBuilder()
                .append(selectWithFromClause(attributes))
                .append(whereClause(attributes))
                .append(orderClause(attributes));

        if (attributes.getNumberOfRows() != null) {
            builder.append(" limit ").append(attributes.getNumberOfRows())
                    .append(" offset ").append(attributes.getOffset());
        }

        return builder.toString();
    }

    @Override
    protected ConditionVisitor getConditionVisitor() {
        return new H2ConditionVisitor();
    }

}
