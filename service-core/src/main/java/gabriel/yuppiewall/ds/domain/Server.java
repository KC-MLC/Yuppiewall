package gabriel.yuppiewall.ds.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Server implements Serializable {

	private String serverContext;
	private Integer size;

	public Server() {
	}

	public Server(String serverContext, Integer size) {
		this.serverContext = serverContext;
		this.size = size;
	}

	public String getServerContext() {
		return serverContext;
	}

	public Integer getSize() {
		return size;
	}

}
