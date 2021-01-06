package ca.bc.gov.educ.api.common.model.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class GradCareerProgram extends BaseModel {
	
	private String code; 
	private String description; 
	private Date startDate; 
	private Date endDate;	
}
