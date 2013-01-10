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

	@Query(value = "select case when (count(er) > 0)  then true else false end from EntityRelation er where er.entityIdLHS = :lhsId and er.entityIdRHS = :rhsId and er.relationshipType=:relationshipType")
	boolean doesRelationExist(@Param("lhsId") String lhsId,
			@Param("rhsId") String rhsId,
			@Param("relationshipType") String relationshipType);
	
	@Query(value = "select er from EntityRelation er where er.entityIdLHS = :lhsId and er.relationshipType=:relationshipType")
	List<EntityRelation> getEntityRHSId(@Param("lhsId") String lhsId,
			@Param("relationshipType") String relationshipType);

}
