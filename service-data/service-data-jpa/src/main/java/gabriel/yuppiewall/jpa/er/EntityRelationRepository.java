package gabriel.yuppiewall.jpa.er;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "entityRelationRepository")
public interface EntityRelationRepository extends
		JpaSpecificationExecutor<EntityRelation>,
		JpaRepository<EntityRelation, String> {

	@Query(value = "select case when (count(er) > 0)  then true else false end from EntityRelation er where er.entityIDLHS = :lhsID and er.entityIDRHS = :rhsID and er.relationshipType=:relationshipType")
	boolean doesRelationExist(@Param("lhsID") String lhsID,
			@Param("rhsID") String rhsID,
			@Param("relationshipType") String relationshipType);
	
	@Query(value = "select er from EntityRelation er where er.entityIDLHS = :lhsID and er.relationshipType=:relationshipType")
	List<EntityRelation> getEntityRHSID(@Param("lhsID") String lhsID,
			@Param("relationshipType") String relationshipType);

}
