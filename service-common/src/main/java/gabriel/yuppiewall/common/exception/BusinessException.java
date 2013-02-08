package gabriel.yuppiewall.common.exception;

import gabriel.yuppiewall.common.meta.FieldDef;

import java.util.List;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	private String message;
	private String cozId;
	private FieldDef[] field;

	public BusinessException() {
	}

	public BusinessException(Class<?> clz, String fieldHandle, String cozId,
			String message) {
		try {
			this.field = new FieldDef[] { clz.getField(fieldHandle)
					.getAnnotation(FieldDef.class) };
		} catch (Exception e) {
			e.printStackTrace();
			// this.field = fieldHandle;
		}
		this.cozId = cozId;
		this.message = message;
	}

	public BusinessException(Class<?> clz, List<String> fieldHandles,
			String cozId, String message) {
		this.field = new FieldDef[fieldHandles.size()];
		try {
			for (int i = 0, max = fieldHandles.size(); i < max; i++) {
				String fieldHandle = fieldHandles.get(i);
				this.field[i] = clz.getField(fieldHandle).getAnnotation(
						FieldDef.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// this.field = fieldHandle;
		}
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
