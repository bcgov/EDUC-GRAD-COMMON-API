package ca.bc.gov.educ.api.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.GradStudentUngradReasonsEntity;

@Repository
public interface GradStudentUngradReasonsRepository extends JpaRepository<GradStudentUngradReasonsEntity, String> {

	List<GradStudentUngradReasonsEntity> findByPen(String pen);
}
