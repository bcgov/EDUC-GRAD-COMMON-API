package ca.bc.gov.educ.api.common.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GRAD_STUDENT_UNGRAD_REASONS")
public class GradStudentUngradReasonsEntity extends BaseEntity {
   	
	@Id
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "PEN", nullable = false)
    private String pen; 
	
	@Column(name = "FK_GRAD_UNGRAD_REASON_CODE", nullable = true)
    private String ungradReasonCode;
		
}