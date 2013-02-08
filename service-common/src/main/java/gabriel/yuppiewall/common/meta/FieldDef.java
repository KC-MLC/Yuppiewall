package gabriel.yuppiewall.common.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDef {
	String field();

	boolean notnull()default false;

	boolean normalize()default false;

	boolean trim()default false;	
}