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
@Table(name = "GRAD_ALGORITHM_RULES")
public class GradAlgorithmRulesEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "RULE_NAME", nullable = false)
    private String ruleName; 
	
	@Column(name = "RULE_IMPLEMENTATION", nullable = false)
    private String ruleImplementation;
	
	@Column(name = "RULE_DESCRIPTION", nullable = false)
    private String ruleDescription;
	
	@Column(name = "SORT_ORDER", nullable = false)
    private Integer sortOrder;
	
	@Column(name = "FK_GRAD_PROGRAM_CODE", nullable = false)
    private String programCode;
	
	@Column(name = "SPECIAL_PROGRAM", nullable = false)
    private String specialProgramCode;	

	
}