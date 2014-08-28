package pl.coffeecode.coffeerepo.impl.predicate;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Objects;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ConditionExpression implements Condition {

    protected Operator operator;
    protected ImmutableList<Condition> conditions;

    private ConditionExpression() {
    }

    public ConditionExpression(Operator operator, ImmutableList<Condition> conditions) {
        checkNotNull(operator, "Operator must be not null.");
        checkNotNull(conditions);
        this.operator = operator;
        this.conditions = conditions;
    }

    @Override
    public String toSQL(ConditionVisitor visitor) {
        checkNotNull(visitor);
        if (operator == Operator.NOT) {
            return notOperatorToSQL(visitor);
        }
        return binaryOperatorToSQL(visitor);
    }

    public ImmutableList<Object> getBindValues() {
        List<Object> bindVariables = Lists.newArrayList();
        for (Condition condition : conditions) {
            bindVariables.addAll(condition.getBindValues());
        }
        return ImmutableList.copyOf(bindVariables);
    }

    public Condition and(Condition condition) {
        return new ConditionExpression(Operator.AND, ImmutableList.of(this, condition));
    }

    public Condition or(Condition condition) {
        return new ConditionExpression(Operator.OR, ImmutableList.of(this, condition));
    }

    public Condition not() {
        return new ConditionExpression(Operator.NOT, ImmutableList.<Condition>of(this));
    }

    private String notOperatorToSQL(ConditionVisitor visitor) {
        StringBuilder builder = new StringBuilder();
        builder.append(operator);
        builder.append(" ( ").append(conditions.get(0).toSQL(visitor)).append(" )");
        return builder.toString();
    }

    private String binaryOperatorToSQL(ConditionVisitor visitor) {
        StringBuilder builder = new StringBuilder();
        for (Condition cond : conditions) {
            builder.append("( ").append(cond.toSQL(visitor)).append(" )");
            builder.append(" ").append(operator).append(" ");
        }
        builder.delete(builder.length() - 4, builder.length() - 1);
        return builder.toString();
    }

    public enum Operator {
        NOT, AND, OR;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, conditions);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ConditionExpression other = (ConditionExpression) obj;
        return Objects.equals(this.operator, other.operator) && Objects.equals(this.conditions, other.conditions);
    }

    @Override
    public String toString() {
        return "ConditionExpression [operator=" + operator + ", conditions=" + conditions + "]";
    }

}
 