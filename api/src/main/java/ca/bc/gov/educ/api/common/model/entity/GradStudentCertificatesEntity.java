package ca.bc.gov.educ.api.common.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GRAD_STUDENT_CERTIFICATES")
public class GradStudentCertificatesEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "PEN", nullable = false)
    private String pen; 
	
	@Lob
    @Column(name = "CERTIFICATE", columnDefinition="CLOB")
	private String certificate;
	
	@Column(name = "FK_GRAD_CERTIFICATE_TYPES_CODE", nullable = false)
    private String gradCertificateTypeCode;	
}