package ca.bc.gov.educ.api.common.model.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "GRAD_STUDENT_CAREER_PROGRAM")
public class GradStudentCareerProgramEntity {
   
	@Id
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "PEN", nullable = false)
    private String pen; 
	
	@Column(name = "CAREER_PROGRAM_CODE", nullable = true)
    private String careerProgramCode;
	
	@Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
	
	@Column(name = "CREATED_TIMESTAMP", nullable = true)
    private Date createdTimestamp;
	
	@Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
    private Date updatedTimestamp;	
	
}