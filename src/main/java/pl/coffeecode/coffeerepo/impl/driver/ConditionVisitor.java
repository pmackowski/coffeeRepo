package pl.coffeecode.coffeerepo.impl.driver;

import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionBeginsWith;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionContains;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionDoesNotBeginWith;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionDoesNotContain;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionGreater;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionGreaterOrEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNotNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionIsNull;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLess;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionLessOrEqual;
import pl.coffeecode.coffeerepo.impl.predicate.condition.ConditionNotEqual;

public interface ConditionVisitor {
	
	String toSQL(ConditionBeginsWith condition);

	String toSQL(ConditionContains conditionContains);

	String toSQL(ConditionDoesNotBeginWith conditionDoesNotBeginWith);

	String toSQL(ConditionDoesNotContain conditionDoesNotContain);

	String toSQL(ConditionEqual conditionEqual);

	String toSQL(ConditionGreater conditionGreater);

	String toSQL(ConditionGreaterOrEqual conditionGreaterOrEqual);

	String toSQL(ConditionIsNotNull conditionIsNotNull);

	String toSQL(ConditionIsNull conditionIsNull);

	String toSQL(ConditionLess conditionLess);

	String toSQL(ConditionLessOrEqual conditionLessOrEqual);

	String toSQL(ConditionNotEqual conditionNotEqual);
	
}
