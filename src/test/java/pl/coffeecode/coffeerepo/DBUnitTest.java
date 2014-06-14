package pl.coffeecode.coffeerepo;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.fest.assertions.core.Condition;
import org.h2.tools.RunScript;
import org.junit.BeforeClass;

import pl.coffeecode.coffeerepo.api.DSL;
import pl.coffeecode.coffeerepo.api.DynamicDSLSelect;
import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.api.ViewRepository;
import pl.coffeecode.coffeerepo.impl.predicate.SortOrder;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Table;

public abstract class DBUnitTest {

	protected abstract String getDatasetFilePath();
	protected DSL dsl;
	protected DynamicDSLSelect dynamicDsl;
	
	@BeforeClass
	public static void createSchema() throws Exception {
		for (SQLDialectDatasource dialectDatasource: Configuration.availableDatabases) {
			RunScript.execute(dialectDatasource.jdbcUrl(), dialectDatasource.jdbcUser(), dialectDatasource.jdbcPassword(),
					dialectDatasource.schemaScript(),
					Charset.defaultCharset(), false);
		}
	}
	
    public void prepare(SQLDialectDatasource dialectDatasource) {
		dsl = ViewRepository.dsl(dialectDatasource.dataSource(), dialectDatasource.dialect());
		dynamicDsl = ViewRepository.dynamicDsl(dialectDatasource.dataSource(), dialectDatasource.dialect());
		try {
			cleanlyInsert(dialectDatasource);
		} catch (Exception exc) {
			throw new DBUnitException("dbunit cleanlyInsert error", exc);
		}
    }
	
    public Object[] databases() {
        return Configuration.availableDatabases;
    }
    
	private void cleanlyInsert(SQLDialectDatasource dialectDatasource) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(dialectDatasource.jdbcDriver(),
				dialectDatasource.jdbcUrl(), dialectDatasource.jdbcUser(), dialectDatasource.jdbcPassword());
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(readDataSet());
		databaseTester.onSetup();
	}
	
	private IDataSet readDataSet() throws Exception {
		URI uri = this.getClass().getClassLoader()
				.getResource(getDatasetFilePath()).toURI();
		File file = new File(uri);
		return new FlatXmlDataSetBuilder().build(file);
	}
	
	protected List<Map<String, Object>> rows(Table<Integer,String,Object> items) {
		return Lists.newArrayList(items.rowMap().values());
	}
	

	protected abstract class RowCondition extends Condition<Map<String,Object>> {
		
	}
	
	protected class RowOrderingNullsLast extends Ordering<Map<String, Object>> {
		
		private Order orders[];
		
		public RowOrderingNullsLast(Order... orders) {
			checkArgument(orders.length > 0);
			this.orders = orders;
		}
		
		@Override
		public int compare(Map<String, Object> left, Map<String, Object> right) {
			int ret = 0;
			for (Order order: orders) {
				int multiple = (order.getSortOrder() == SortOrder.DESC) ? -1 : 1;
				Object leftVal = left.get(order.getColumn());
				Object rightVal = right.get(order.getColumn());
				
				if (leftVal == null && rightVal == null) {
					ret = 0;
					continue;
				}
				if (leftVal == null) {
					return 1; // NULLS LAST
				}
				if (rightVal == null) {
					return -1; // NULLS LAST
				}
				ret = multiple * compareColumn(leftVal, rightVal);
				if (ret != 0) {
					return ret;
				}
			}
			return ret;
		}
		
		private int compareColumn(Object left, Object right) {
			if (left instanceof String) {
				return ((String) left).compareTo((String) right);
			} else if (left instanceof Integer) {
				return ((Integer) left).compareTo((Integer) right);
			} else if (left instanceof BigDecimal) {
				return ((BigDecimal) left).compareTo((BigDecimal) right);
			} else if (left instanceof Date) {
				return ((Date) left).compareTo((Date) right);
			}	
			throw new NullPointerException("not implemented " + left.getClass()); // TODO not implemented
		}
		
	}
	
}
