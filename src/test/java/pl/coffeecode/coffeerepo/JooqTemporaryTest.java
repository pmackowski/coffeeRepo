package pl.coffeecode.coffeerepo;

import static org.jooq.impl.DSL.fieldByName;
import static pl.coffeecode.coffeerepo.Constants.*;

import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.junit.Before;
import org.junit.Test;

public class JooqTemporaryTest {
	
	private Settings settings = null;
	
	@Before
	public void setUp() {
		settings = new Settings();
		settings.setStatementType(StatementType.STATIC_STATEMENT);
	}
	
	@Test
	public void hereWriteQueryForChoosenSQLDialectAndSeeOutput() {
		
		DSLContext create = org.jooq.impl.DSL.using(org.jooq.SQLDialect.H2, settings);
		
		String sql = create
				.select(fieldByName(VIEW_NAME,C_NAME),fieldByName(VIEW_NAME,"LAST_NAME"))
				.from(VIEW_NAME)
				.getSQL();
		
		String sql2 = create
				.select(fieldByName(VIEW_NAME,C_NAME),fieldByName(VIEW_NAME,"LAST_NAME"))
				.from(VIEW_NAME)
				.getSQL();
		
		
		System.out.println(sql);
		System.out.println(sql2);
	}

}
