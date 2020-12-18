package ca.bc.gov.educ.api.common.model.dto;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradStudentCareerProgram {

	private UUID id;
	private String pen; 	
	private String careerProgramCode;	
	private String careerProgramName;
	private String createdBy;
	private Date createdTimestamp;	
	private String updatedBy;	
	private Date updatedTimestamp;
	
	@Override
	public String toString() {
		return "GradStudentCareerProgram [id=" + id + ", pen=" + pen + ", careerProgramCode=" + careerProgramCode
				+ ", careerProgramName=" + careerProgramName + ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
	
	
	
	
}
