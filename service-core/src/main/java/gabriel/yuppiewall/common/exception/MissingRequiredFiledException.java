package gabriel.yuppiewall.common.exception;

import java.util.List;

/**
 * HTTP_ERROR CODE 400
 * 
 */
@SuppressWarnings("serial")
public class MissingRequiredFiledException extends BusinessException {
	private static final String COZ_ID = "missingparam";

	public MissingRequiredFiledException() {

	}

	public MissingRequiredFiledException(Class<?> clz,
			List<String> fieldHandle, String message) {

		super(clz, fieldHandle, COZ_ID, message);

	}
}
