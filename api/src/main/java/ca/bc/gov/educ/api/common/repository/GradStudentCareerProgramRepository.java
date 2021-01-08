package ca.bc.gov.educ.api.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.GradStudentCareerProgramEntity;

@Repository
public interface GradStudentCareerProgramRepository extends JpaRepository<GradStudentCareerProgramEntity, String> {

    List<GradStudentCareerProgramEntity> findAll();

	List<GradStudentCareerProgramEntity> findByPen(String pen);

	@Query("select c from GradStudentCareerProgramEntity c where c.careerProgramCode=:cpCode")
	List<GradStudentCareerProgramEntity> existsByCareerProgramCode(String cpCode);

}
