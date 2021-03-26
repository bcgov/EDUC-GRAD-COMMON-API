package ca.bc.gov.educ.api.common.model.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "GRAD_ALGORITHM_RULES")
public class GradAlgorithmRulesEntity {
   
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
	
	@Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
	
	@Column(name = "CREATED_TIMESTAMP", nullable = true)
    private Date createdTimestamp;
	
	@Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
    private Date updatedTimestamp;
	
	@PrePersist
	protected void onCreate() {
		this.updatedBy = "GRADUATION";
		this.createdBy = "GRADUATION";
		this.createdTimestamp = new Date(System.currentTimeMillis());
		this.updatedTimestamp = new Date(System.currentTimeMillis());

	}

	@PreUpdate
	protected void onPersist() {
		this.updatedTimestamp = new Date(System.currentTimeMillis());
		this.updatedBy = "GRADUATION";
		if (StringUtils.isBlank(createdBy)) {
			createdBy = "GRADUATION";
		}
		if (this.createdTimestamp == null) {
			this.createdTimestamp = new Date(System.currentTimeMillis());
		}
	}
	
}