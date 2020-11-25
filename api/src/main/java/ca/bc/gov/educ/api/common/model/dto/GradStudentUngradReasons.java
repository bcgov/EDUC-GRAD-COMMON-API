package ca.bc.gov.educ.api.common.model.dto;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradStudentUngradReasons {

	private UUID id;
	private String pen;
	private String ungradReasonCode;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	
	@Override
	public String toString() {
		return "GradCountry [id=" + id + ", pen=" + pen + ", ungradReasonCode=" + ungradReasonCode + ", createdBy="
				+ createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + "]";
	}
	
	
	
}
