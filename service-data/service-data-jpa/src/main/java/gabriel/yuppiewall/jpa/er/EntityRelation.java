package gabriel.yuppiewall.jpa.er;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "entity_relationship")
public class EntityRelation implements Serializable {

	@Column(name = "entity_lhs", nullable = false, insertable = true)
	private String entityIdLHS;
	@Column(name = "entity_rhs", nullable = false, insertable = true)
	private String entityIdRHS;
	@Id
	@Column(name = "relationship_id")
	private String relationshipID;
	@Column(name = "relationship_type", nullable = false, insertable = true)
	private String relationshipType;
	@Column(name = "creation_date", nullable = false, insertable = true)
	private Date createdDate;

	public EntityRelation() {
	}

	public EntityRelation(String entityIDLHS, String entityIDRHS,
			String relationshipType) {
		this.entityIdLHS = entityIDLHS;
		this.entityIdRHS = entityIDRHS;
		this.relationshipType = relationshipType;
		this.createdDate = new Date();
		this.relationshipID = UUID.randomUUID().toString();

	}

	public String getEntityIdLHS() {
		return entityIdLHS;
	}

	public String getEntityIdRHS() {
		return entityIdRHS;
	}

}
