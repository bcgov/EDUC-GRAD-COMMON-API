package ca.bc.gov.educ.api.common.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.GradStudentUngradReasonsEntity;

@Repository
public interface GradStudentUngradReasonsRepository extends JpaRepository<GradStudentUngradReasonsEntity, UUID> {

	List<GradStudentUngradReasonsEntity> findByPen(String pen);

	@Query("select c from GradStudentUngradReasonsEntity c where c.ungradReasonCode=:reasonCode")
	List<GradStudentUngradReasonsEntity> existsByReasonCode(String reasonCode);

	List<GradStudentUngradReasonsEntity> findByStudentID(UUID studentID);
}
