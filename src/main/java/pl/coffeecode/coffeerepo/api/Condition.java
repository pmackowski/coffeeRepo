package pl.coffeecode.coffeerepo.api;

import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;

import com.google.common.collect.ImmutableList;

public interface Condition {
	
	String toSQL(ConditionVisitor visitor);
	
	ImmutableList<Object> getBindValues();
}
