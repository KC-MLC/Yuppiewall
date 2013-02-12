package gabriel.yuppiewall.common.exception;


@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	private String message;
	private String cozId;

	public BusinessException() {
	}

	public BusinessException(String cozId, String message) {

		this.cozId = cozId;
		this.message = message;
	}

	public BusinessException(String message2) {
		this.message = message2;
	}

	public String getMessage() {
		return message;
	}
}
