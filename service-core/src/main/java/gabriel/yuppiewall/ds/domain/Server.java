package gabriel.yuppiewall.ds.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Server implements Serializable {

	private String serverContext;
	private Integer size;

	public Server() {
	}

	public Server(String serverContext) {
		this.serverContext = serverContext;
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

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Server [serverContext=" + serverContext + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serverContext == null) ? 0 : serverContext.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Server other = (Server) obj;
		if (serverContext == null) {
			if (other.serverContext != null)
				return false;
		} else if (!serverContext.equals(other.serverContext))
			return false;
		return true;
	}

}
