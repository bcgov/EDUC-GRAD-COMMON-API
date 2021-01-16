package ca.bc.gov.educ.api.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.GradStudentReportEntity;

@Repository
public interface GradStudentReportRepository extends JpaRepository<GradStudentReportEntity, String> {

   	Optional<GradStudentReportEntity> findByPen(String pen);
}
