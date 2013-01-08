package gabriel.yuppiewall.common.util;

import gabriel.yuppiewall.common.meta.FieldDef;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {

	public static List<String> vallidate(Object obj) {
		List<String> errors = new ArrayList<String>();
		Field[] fields = obj.getClass().getFields();
		for (int i = 0; i < fields.length; i++) {
			FieldDef annotations = (FieldDef) fields[i]
					.getAnnotation(FieldDef.class);
			if (annotations != null) {
				try {
					if (annotations.normalize() || annotations.trim()) {
						String value = (String) fields[i].get(obj);
						if (value != null) {
							value = value.trim();
							if (annotations.normalize())
								value = value.toLowerCase();
							if (value.length() == 0)
								value = null;
							fields[i].set(obj, value);
						}
					}
					if (annotations.notnull()) {
						if (fields[i].get(obj) == null) {
							errors.add(annotations.field());
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return (errors.size() == 0) ? null : errors;
	}

}
