package gabriel.yuppiewall.jpa.marketdata.domain;

import gabriel.yuppiewall.ds.domain.Server;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "region_server")
public class JPAServer implements Serializable {

	@Id
	@Column(name = "server_context")
	private String serverContext;

	@Column(name = "server_size", nullable = false, updatable = false)
	private Integer size;

	public JPAServer() {
	}

	public JPAServer(Server server) {

		this.serverContext = server.getServerContext();
		this.size = server.getSize();
	}
}