package ca.bc.gov.educ.api.common.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GRAD_STUDENT_UNGRAD_REASONS")
public class GradStudentUngradReasonsEntity extends BaseEntity {
   	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "FK_GRAD_STUDENT_PEN", nullable = false)
    private String pen; 
	
	@Column(name = "FK_GRAD_UNGRAD_REASON_CODE", nullable = true)
    private String ungradReasonCode;
	
	@Column(name = "FK_GRAD_STUDENT_STUDENT_ID", nullable = false)
    private UUID studentID;
		
}