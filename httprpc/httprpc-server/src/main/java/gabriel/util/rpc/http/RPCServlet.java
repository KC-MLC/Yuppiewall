package gabriel.util.rpc.http;

import gabriel.util.rpc.http.util.KEYS;
import gabriel.util.rpc.http.util.RPCHelper;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RPCServlet extends HttpServlet {

	private final Map<String, Method> METHOD_MAP = new HashMap<String, Method>();
	{
		Method[] methods = this.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			METHOD_MAP.put(RPCHelper.getSignature(method), method);
		}
	}

	private final ThreadLocal<HttpServletRequest> perThreadRequest = new ThreadLocal<HttpServletRequest>();

	private final ThreadLocal<HttpServletResponse> perThreadResponse = new ThreadLocal<HttpServletResponse>();

	public final void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		String status = null;
		Serializable ret = null;
		ObjectOutputStream oos = null;
		ServletOutputStream sos = null;
		try {
			response.setContentType("text/html");

			sos = response.getOutputStream();
			oos = new ObjectOutputStream(sos);

			perThreadRequest.set(request);
			perThreadResponse.set(response);
			preProcess();

			String mkey = request.getParameter(KEYS.METHOD_KEY);
			Method method = METHOD_MAP.get(mkey);
			Class<?>[] params = method.getParameterTypes();
			Object[] values = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				values[i] = RPCHelper.deSerialize(request
						.getParameter(KEYS.PARAM_KEY + "" + i));
			}

			try {
				ret = (Serializable) method.invoke(this, values);
				status = KEYS.STATUS_OK_VALUE;

			} catch (Exception e) {
				e.printStackTrace();
				ret = e.getCause();
				status = KEYS.STATUS_ERROR_VALUE;
			}

		} catch (IOException ignore) {
			ignore.printStackTrace();
			ret = ignore.getCause();
			status = KEYS.STATUS_ERROR_VALUE;
		} finally {
			response.setHeader(KEYS.STATUS_KEY, status);
			try {
				oos.writeObject(ret);
				oos.close();
				sos.close();
			} catch (Exception ignore) {

				ignore.printStackTrace();
			}
		}

	}

	protected void preProcess() {
	}

}