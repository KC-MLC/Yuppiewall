package gabriel.yuppiewall.um.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PrimaryPrincipal implements Serializable {

	private String uuid;

	public PrimaryPrincipal() {

	}

	public PrimaryPrincipal(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return uuid;
	}

}
