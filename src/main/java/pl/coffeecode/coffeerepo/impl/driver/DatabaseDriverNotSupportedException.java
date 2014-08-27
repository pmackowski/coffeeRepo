package pl.coffeecode.coffeerepo.impl.driver;

/**
 * Created by mcp on 2014-08-27.
 */
public class DatabaseDriverNotSupportedException extends RuntimeException {

    public DatabaseDriverNotSupportedException(String message) {
        super(message);
    }

}
