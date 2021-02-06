package ca.bc.gov.educ.api.common.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.GradStudentCertificatesEntity;

@Repository
public interface GradStudentCertificatesRepository extends JpaRepository<GradStudentCertificatesEntity, UUID> {

   	Optional<GradStudentCertificatesEntity> findByPenAndGradCertificateTypeCode(String pen,String certificateTypeCode);
   	
   	@Query("select c from GradStudentCertificatesEntity c where c.gradCertificateTypeCode=:certificateType")
	List<GradStudentCertificatesEntity> existsByCertificateTypeCode(String certificateType);
}
