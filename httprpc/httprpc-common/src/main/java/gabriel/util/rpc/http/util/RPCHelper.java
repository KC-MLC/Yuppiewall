package gabriel.util.rpc.http.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.codec.binary.Base64;

public class RPCHelper {

	public static String getSignature(Method method) {

		StringBuilder sig = new StringBuilder(method.getReturnType().getName()
				+ "_" + method.getName() + "_");
		Class<?>[] params = method.getParameterTypes();
		for (int i = 0; i < params.length; i++) {
			Class<?> class1 = params[i];
			sig.append(class1.getName() + "_");
		}
		return sig.toString();

	}

	public static String serializeObject(Serializable object) {
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bais);
			oos.writeObject(object);
			bais.flush();
			bais.close();

			return new String(Base64.encodeBase64(bais.toByteArray()));
		} catch (Exception ignore) {
			// TODO
			ignore.printStackTrace();
		}
		return null;
	}

	public static Object deSerialize(String value) {

		try {
			if (value.length() == 0)
				return null;
			ByteArrayInputStream is = new ByteArrayInputStream(
					Base64.decodeBase64(value));
			ObjectInputStream ois = new ObjectInputStream(is);
			return ois.readObject();
		} catch (Exception ignore) {
			// TODO
			ignore.printStackTrace();
		}
		return null;
	}

	public static Object deSerialize(byte[] value) {

		try {
			if (value.length == 0)
				return null;
			ByteArrayInputStream is = new ByteArrayInputStream(
					Base64.decodeBase64(Base64.encodeBase64(value)));
			ObjectInputStream ois = new ObjectInputStream(is);
			return ois.readObject();
		} catch (Exception ignore) {
			// TODO
			ignore.printStackTrace();
		}
		return null;
	}
}