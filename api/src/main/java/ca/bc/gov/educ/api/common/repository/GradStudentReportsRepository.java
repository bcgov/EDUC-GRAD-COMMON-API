package ca.bc.gov.educ.api.common.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.GradStudentReportsEntity;

@Repository
public interface GradStudentReportsRepository extends JpaRepository<GradStudentReportsEntity, UUID> {

   	Optional<GradStudentReportsEntity> findByPenAndGradReportTypeCode(String pen,String gradReportTypeCode);
   	
   	@Query("select c from GradStudentReportsEntity c where c.gradReportTypeCode=:reportType")
	List<GradStudentReportsEntity> existsByReportTypeCode(String reportType);
}
