package gabriel.yuppiewall.jpa.er;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "entity_relationship")
public class EntityRelation implements Serializable {

	private String entityIDLHS;
	private String entityIDRHS;
	@Id
	@Column(name = "relationship_id")
	private String relationshipID;

	private String relationshipType;
	private Date createdDate;
}
