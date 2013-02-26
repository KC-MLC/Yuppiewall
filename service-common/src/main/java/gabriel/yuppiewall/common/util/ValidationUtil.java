package gabriel.yuppiewall.common.util;

import gabriel.yuppiewall.common.exception.InvalidParameterValueException;

public class ValidationUtil {

	public static <T> T notNull(T t, String message) {
		if (t == null) {
			throw new InvalidParameterValueException(message);
		}
		return t;
	}

	public static String notNull(String value, String message) {
		if (value == null || (value = value.trim()).isEmpty()) {
			throw new InvalidParameterValueException(message);
		}
		return value;
	}

	public static String notNullNormalize(String value, String message) {
		if (value == null || (value = value.trim().toLowerCase()).isEmpty()) {
			throw new InvalidParameterValueException(message);
		}
		return value;
	}
}
