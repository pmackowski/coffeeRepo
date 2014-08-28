package pl.coffeecode.coffeerepo.impl.predicate;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.impl.predicate.ConditionExpression.Operator;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNotNull;

public class ConditionExpressionTest {

    @Test
    public void bind_values() {
        Condition c1 = new ConditionEqual("col1", 7);
        Condition c2 = new ConditionIsNotNull("col2");
        Condition c3 = new ConditionEqual("col3", "abc3");
        Condition c4 = new ConditionEqual("col4", "abc4");
        Condition c5 = new ConditionEqual("col5", "abc5");

        Condition and = new ConditionExpression(Operator.AND, ImmutableList.of(c1, c2));
        Condition or = new ConditionExpression(Operator.OR, ImmutableList.of(c3, c4));
        ConditionExpression cond = new ConditionExpression(Operator.AND, ImmutableList.of(and, or, c5));

        assertThat(cond.getBindValues()).containsExactly(7, "abc3", "abc4", "abc5");
    }

}
