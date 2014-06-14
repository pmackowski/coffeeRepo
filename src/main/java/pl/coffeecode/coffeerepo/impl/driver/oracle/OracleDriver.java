package pl.coffeecode.coffeerepo.impl.driver.oracle;

import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.impl.driver.CommonDatabaseDriver;
import pl.coffeecode.coffeerepo.impl.driver.ConditionVisitor;
import pl.coffeecode.coffeerepo.impl.driver.DatabaseDriver;

public class OracleDriver extends CommonDatabaseDriver implements DatabaseDriver {
	
	@Override
	public String createSQL(QueryAttributes attributes) {
		String mainSelect = new StringBuilder()
			.append(selectWithFromClause(attributes))
			.append(whereClause(attributes))
			.append(orderClause(attributes))
			.toString();
		
		if (attributes.getNumberOfRows() == null) {
			return mainSelect;
		}
		return selectWithLimitClause(attributes, mainSelect);
		
	}
	
	private String selectWithLimitClause(QueryAttributes attributes, String mainSelect) {
		int fromOffset = attributes.getOffset();
		int toOffset = fromOffset + attributes.getNumberOfRows();
		
		return new StringBuilder()
				.append("select ").append(columnNames(attributes)).append(" from (")
						.append("select ").append("t.*").append(", rownum rn ").append(" from (")
							.append(mainSelect).append(") t where rownum <= ").append(toOffset)
						.append(") where rn > ").append(fromOffset)
				.toString();
		
	}
	
	@Override
	protected ConditionVisitor getConditionVisitor() {
		return new OracleConditionVisitor();
	}

}
