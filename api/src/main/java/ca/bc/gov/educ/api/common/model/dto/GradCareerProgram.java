package ca.bc.gov.educ.api.common.model.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradCareerProgram {
	
	private String code; 
	private String description; 
	private Date startDate; 
	private Date endDate;	
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	
	@Override
	public String toString() {
		return "GradCareerProgram [code=" + code + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp
				+ ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}	
	
	
}
