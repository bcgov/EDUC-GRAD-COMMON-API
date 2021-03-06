package ca.bc.gov.educ.api.common.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.GradAlgorithmRulesEntity;

@Repository
public interface GradAlgorithmRulesRepository extends JpaRepository<GradAlgorithmRulesEntity, UUID> {
   	
   	@Query("select c from GradAlgorithmRulesEntity c where c.programCode=:programCode and c.isActive='Y'")
	List<GradAlgorithmRulesEntity> getAlgorithmRulesByProgramCode(String programCode);
}
