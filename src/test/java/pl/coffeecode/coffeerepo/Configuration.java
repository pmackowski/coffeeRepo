package pl.coffeecode.coffeerepo;


public class Configuration {

    private final static String SCHEMA_DIRECTORY =
            "src/test/resources/dbunit/schema/";

    public static final SQLDialectDatasource H2Database =
            new SQLDialectDatasourceImpl(
                    "H2",
                    SCHEMA_DIRECTORY + "h2.sql",
                    "org.h2.Driver",
                    "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                    "sa", "");

    public static final SQLDialectDatasource OracleDatabase =
            new SQLDialectDatasourceImpl(
                    "Oracle",
                    SCHEMA_DIRECTORY + "oracle.sql",
                    "oracle.jdbc.driver.OracleDriver",
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "hr", "hr");

    public static final SQLDialectDatasource[] availableDatabases = {
            H2Database, OracleDatabase
    };

}
