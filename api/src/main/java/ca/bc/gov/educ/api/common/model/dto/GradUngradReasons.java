package ca.bc.gov.educ.api.common.model.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradUngradReasons {

	private String code;	
	private String description;	
	private String createdBy;	
	private Date createdTimestamp;	
	private String updatedBy;	
	private Date updatedTimestamp;
	
	@Override
	public String toString() {
		return "GradUngradReasons [code=" + code + ", description=" + description + ", createdBy=" + createdBy
				+ ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp="
				+ updatedTimestamp + "]";
	}
	
	
}
