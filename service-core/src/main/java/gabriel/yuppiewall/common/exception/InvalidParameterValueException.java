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

	public InvalidParameterValueException(String message) {
		super(message);
	}

	public InvalidParameterValueException(Class<?> clz, String fieldHandle,
			String message) {
		super(clz, fieldHandle, COZ_ID, message);
	}
}