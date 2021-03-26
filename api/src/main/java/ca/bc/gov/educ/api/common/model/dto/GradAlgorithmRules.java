package ca.bc.gov.educ.api.common.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class GradAlgorithmRules extends BaseModel {

	private UUID id; 
	private String ruleName; 
	private String ruleImplementation;
	private String ruleDescription;
	private Integer sortOrder;
	private String programCode;
	private String specialProgramCode;	
	
}
