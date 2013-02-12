package gabriel.yuppiewall.common.exception;

/**
 * HTTP_ERROR CODE 409
 * 
 */
@SuppressWarnings("serial")
public class EntityAlreadyExistsException extends BusinessException {

	private static final String COZ_ID = "duplicate";

	public EntityAlreadyExistsException() {

	}

	public EntityAlreadyExistsException(String message) {

		super(COZ_ID, message);

	}
}
