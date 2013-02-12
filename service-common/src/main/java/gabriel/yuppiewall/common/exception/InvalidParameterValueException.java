package gabriel.yuppiewall.common.exception;

/**
 * HTTP_ERROR CODE 400
 * 
 */
@SuppressWarnings("serial")
public class InvalidParameterValueException extends BusinessException {

	private static final String COZ_ID = "invalidparameter";

	public InvalidParameterValueException() {
	}



	public InvalidParameterValueException(
			String message) {
		super(COZ_ID, message);
	}
}