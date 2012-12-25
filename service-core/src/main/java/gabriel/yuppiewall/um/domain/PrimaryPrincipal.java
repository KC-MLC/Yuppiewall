package gabriel.yuppiewall.um.domain;

import java.security.Principal;

public class PrimaryPrincipal implements Principal {

	private String uuid;

	@Override
	public String getName() {
		return uuid;
	}

}
