package ca.bc.gov.educ.api.common.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class BaseModel {
	private String createdBy;	
	private Date createdTimestamp;	
	private String updatedBy;	
	private Date updatedTimestamp;
}
