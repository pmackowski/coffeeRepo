package pl.coffeecode.coffeerepo;

public class DBUnitException extends RuntimeException {

	private static final long serialVersionUID = 5041472164641016482L;

	public DBUnitException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
