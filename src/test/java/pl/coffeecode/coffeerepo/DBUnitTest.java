package pl.coffeecode.coffeerepo;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class DBUnitTest {

	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	protected abstract String getDatasetFilePath();
	
	@BeforeClass
	public static void createSchema() throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD,
				"src/test/resources/dbunit/schema.sql",
				Charset.defaultCharset(), false);
	}

	@Before
	public void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet();
		cleanlyInsert(dataSet);
	}

	private IDataSet readDataSet() throws Exception {
		URI uri = this.getClass().getClassLoader()
				.getResource(getDatasetFilePath()).toURI();
		File file = new File(uri);
		return new FlatXmlDataSetBuilder().build(file);
	}

	private void cleanlyInsert(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER,
				JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	protected static DataSource dataSource;
	static {
		JdbcDataSource jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.setURL(JDBC_URL);
		jdbcDataSource.setUser(USER);
		jdbcDataSource.setPassword(PASSWORD);
		dataSource = jdbcDataSource;
	}

}
