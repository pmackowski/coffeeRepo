package pl.coffeecode.coffeerepo.impl.predicate;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ConditionExpression implements Condition {

	protected Operator operator;
	protected ImmutableList<Condition> conditions;
	
	protected ConditionExpression() {}
	
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
		for (Condition condition: conditions) {
			bindVariables.addAll(condition.getBindValues());
		}
		return ImmutableList.copyOf(bindVariables);
	}
	
	public Condition and(Condition condition) {
		return new ConditionExpression(Operator.AND, ImmutableList.of(this,condition));
	}

	public Condition or(Condition condition) {
		return new ConditionExpression(Operator.OR, ImmutableList.of(this,condition));
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
		builder.delete(builder.length()-4, builder.length()-1);
		return builder.toString();
	}
	
	public enum Operator {
		NOT, AND, OR;
	}
	
	@Override
	public String toString() {
		return "ConditionExpression [operator=" + operator + ", conditions=" + conditions	+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConditionExpression other = (ConditionExpression) obj;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (operator != other.operator)
			return false;
		return true;
	}

}
 