package gabriel.yuppiewall.common.exception;


/**
 * HTTP_ERROR CODE 400
 * 
 */
@SuppressWarnings("serial")
public class MissingRequiredFiledException extends BusinessException {
	private static final String COZ_ID = "missingparam";

	public MissingRequiredFiledException() {

	}

	public MissingRequiredFiledException(String message) {
		super(COZ_ID, message);
	}
}
