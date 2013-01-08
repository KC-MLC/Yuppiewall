package gabriel.yuppiewall.um.domain;

import java.io.Serializable;
import java.security.Principal;

@SuppressWarnings("serial")
public class PrimaryPrincipal implements Principal, Serializable {

	private String uuid;

	public PrimaryPrincipal() {

	}

	public PrimaryPrincipal(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getName() {
		return uuid;
	}

}
