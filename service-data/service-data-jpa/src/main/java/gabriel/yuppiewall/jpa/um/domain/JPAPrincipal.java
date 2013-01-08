package gabriel.yuppiewall.jpa.um.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

@SuppressWarnings("serial")
@Entity
@Table(name = "principal")
public class JPAPrincipal implements Serializable {

	@Id
	@Column(name = "principal_id")
	private String id;

	public JPAPrincipal() {
	}

	public JPAPrincipal(PrimaryPrincipal user) {
		this.id = user.getName();
	}

	public PrimaryPrincipal getPrimaryPrincipal() {
		return new PrimaryPrincipal(id);
	}

}
