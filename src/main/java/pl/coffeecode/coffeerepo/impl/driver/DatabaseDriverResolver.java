package pl.coffeecode.coffeerepo.impl.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coffeecode.coffeerepo.impl.driver.h2.H2Driver;
import pl.coffeecode.coffeerepo.impl.driver.oracle.OracleDriver;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DatabaseDriverResolver {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseDriverResolver.class);

    public DatabaseDriver resolveDatabase(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseName = metaData.getDatabaseProductName();

            if ("H2".equals(databaseName)) {
                logger.info("H2 driver detected");
                return new H2Driver();
            }
            if ("Oracle".equals(databaseName)) {
                logger.info("Oracle driver detected");
                return new OracleDriver();
            }
            throw new DatabaseDriverNotSupportedException(databaseName + " is not supported yet!");
        } catch (SQLException e) {
            logger.error("No database driver detected! Error message {}", e.getMessage());
            return null;
        } finally {
            closeConnection(connection);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
        }
    }
}
