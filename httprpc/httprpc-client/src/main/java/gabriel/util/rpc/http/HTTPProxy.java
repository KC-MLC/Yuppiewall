package gabriel.util.rpc.http;

import gabriel.util.rpc.http.util.HTTPRPC;
import gabriel.util.rpc.http.util.KEYS;
import gabriel.util.rpc.http.util.RPCHelper;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HTTPProxy implements InvocationHandler {
	private String contextPath;

	public HTTPProxy(String path) {
		this.contextPath = path;
	}

	public Object getService(Class beanClass) {

		// pathAnot.
		return Proxy.newProxyInstance(beanClass.getClassLoader(),
				new Class[] { beanClass }, new HTTPProxy(contextPath + "/"
						+ beanClass.getSimpleName()));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {

			HttpPost httpost = new HttpPost(contextPath);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (args != null) {
				for (int i = 0; i < args.length; i++) {

					nvps.add(new BasicNameValuePair(KEYS.PARAM_KEY + "" + i,
							RPCHelper.serializeObject((Serializable) args[i])));
				}
			}
			// TODO add userContext
			// nvps.add(new ObjectNameValuePair("USERCONTEXT", usercontext));

			nvps.add(new BasicNameValuePair(KEYS.METHOD_KEY, RPCHelper
					.getSignature(method)));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			Header header = response.getFirstHeader("STATUS");
			if (header.getValue().equals("-1")) {
				Exception exception = (Exception) RPCHelper
						.deSerialize(EntityUtils.toByteArray(entity));
				throw exception;
			} else {
				return RPCHelper.deSerialize(EntityUtils.toByteArray(entity));
			}

		} catch (org.apache.http.conn.HttpHostConnectException serverDown) {
			// TODO send email server is down
			serverDown.printStackTrace();
			throw serverDown;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

}
