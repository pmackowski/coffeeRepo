package pl.coffeecode.coffeerepo.impl;

import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.api.DynamicDSL;
import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.api.Predicate;

public class DynamicDSLImpl extends ExcutableImpl implements DynamicDSL {

    public DynamicDSLImpl(QueryExecutor delegate, QueryAttributesImpl attributes) {
        super(delegate, attributes);
    }

    @Override
    public DynamicDSL where(Condition condition) {
        return (DynamicDSL) newDynamicDSLImpl().addWhere(condition);
    }

    @Override
    public DynamicDSL whereIgnoreNulls(Condition... conditions) {
        return (DynamicDSL) newDynamicDSLImpl().addWhereIgnoreNulls(conditions);
    }

    @Override
    public DynamicDSL orderBy(Order order, Order... orders) {
        return (DynamicDSL) newDynamicDSLImpl().addOrderBy(order, orders);
    }

    @Override
    public DynamicDSL appendOrderBy(Order... orders) {
        return (DynamicDSL) newDynamicDSLImpl().addAppendOrderBy(orders);
    }

    @Override
    public DynamicDSL appendOrderByIgnoreNulls(Order... orders) {
        return (DynamicDSL) newDynamicDSLImpl().addAppendOrderByIgnoreNulls(orders);
    }

    @Override
    public DynamicDSL limit(int numberOfRows, int page) {
        return (DynamicDSL) newDynamicDSLImpl().addLimit(numberOfRows, page);
    }

    private DynamicDSLImpl newDynamicDSLImpl() {
        return new DynamicDSLImpl(delegate, attributes.clone());
    }

    private DynamicDSL addWhereIgnoreNulls(Condition... conditions) {
        checkInvocations(attributes.condition, "where");
        List<Condition> conds = FluentIterable
                .from(Lists.newArrayList(conditions))
                .filter(Predicates.notNull())
                .toList();

        if (!Iterables.isEmpty(conds)) {
            attributes.condition = Predicate.and(conds);
        }
        return this;
    }

    private DynamicDSL addAppendOrderBy(Order... orders) {

        attributes.orders = ImmutableList.<Order>builder()
                .addAll(attributes.orders)
                .addAll(Lists.newArrayList(orders))
                .build();

        return this;
    }

    private DynamicDSL addAppendOrderByIgnoreNulls(Order... orders) {
        List<Order> ords = FluentIterable
                .from(Lists.newArrayList(orders))
                .filter(Predicates.notNull())
                .toList();

        attributes.orders = ImmutableList.<Order>builder()
                .addAll(attributes.orders)
                .addAll(ords)
                .build();
        return this;
    }
}
