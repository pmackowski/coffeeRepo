package pl.coffeecode.coffeerepo.impl.predicate;

import static com.google.common.base.Preconditions.checkNotNull;
import pl.coffeecode.coffeerepo.api.Condition;

import com.google.common.collect.ImmutableList;

public class ConditionImpl implements Condition {

	private Operator operator;
	private ImmutableList<Condition> conditions;
	
	protected ConditionImpl() {}
	
	public ConditionImpl(Operator operator, ImmutableList<Condition> conditions) {
		checkNotNull(operator, "Operator must be not null.");
		this.operator = operator;
		this.conditions = conditions;
	}

	public String toSQL() {
		if (operator == Operator.NOT) {
			return notOperatorToSQL();
		}
		return binaryOperatorToSQL();
	}
	
	public Condition and(Condition condition) {
		return new ConditionImpl(Operator.AND, ImmutableList.of(this,condition));
	}

	public Condition or(Condition condition) {
		return new ConditionImpl(Operator.OR, ImmutableList.of(this,condition));
	}

	public Condition not() {
		return new ConditionImpl(Operator.NOT, ImmutableList.<Condition>of(this));
	}
	
	private String notOperatorToSQL() {
		StringBuilder builder = new StringBuilder();
		builder.append(operator);
		builder.append(" ( ").append(conditions.get(0).toSQL()).append(" )");
		return builder.toString();
	}
	
	private String binaryOperatorToSQL() {
		StringBuilder builder = new StringBuilder();
		for (Condition cond : conditions) {
			builder.append("( ").append(cond.toSQL()).append(" )");
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
		return "ConditionImpl [operator=" + operator + ", conditions=" + conditions	+ "]";
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
		ConditionImpl other = (ConditionImpl) obj;
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
 