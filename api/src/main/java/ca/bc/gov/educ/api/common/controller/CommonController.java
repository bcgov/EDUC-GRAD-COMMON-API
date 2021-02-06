package ca.bc.gov.educ.api.common.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.common.model.dto.GradStudentCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentReports;
import ca.bc.gov.educ.api.common.model.dto.GradStudentUngradReasons;
import ca.bc.gov.educ.api.common.service.CommonService;
import ca.bc.gov.educ.api.common.util.ApiResponseModel;
import ca.bc.gov.educ.api.common.util.EducGradCommonApiConstants;
import ca.bc.gov.educ.api.common.util.GradValidation;
import ca.bc.gov.educ.api.common.util.PermissionsContants;
import ca.bc.gov.educ.api.common.util.ResponseHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping(EducGradCommonApiConstants.GRAD_COMMON_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Common endpoints.", description = "This Read API is for Reading Common endpoints.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_UNGRAD_REASONS_DATA","READ_GRAD_STUDENT_CAREER_DATA"})})
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    CommonService codeService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_UNGRAD_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_UNGRAD_REASONS_DATA)
    public ResponseEntity<List<GradStudentUngradReasons>> getAllStudentUngradReasonsList(@PathVariable String pen) { 
    	logger.debug("getAllStudentUngradReasonsList : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return response.GET(codeService.getAllStudentUngradReasonsList(pen,accessToken));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_UNGRAD_BY_REASON_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_UNGRAD_REASONS_DATA)
    public ResponseEntity<Boolean> getStudentUngradReasons(@PathVariable String reasonCode) { 
    	logger.debug("getStudentUngradReasons : ");
        return response.GET(codeService.getStudentUngradReasons(reasonCode));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_CAREER_PROGRAM_BY_CAREER_PROGRAM_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_CAREER_DATA)
    public ResponseEntity<Boolean> getStudentCareerProgram(@PathVariable String cpCode) { 
    	logger.debug("getStudentCareerProgram : ");
        return response.GET(codeService.getStudentCareerProgram(cpCode));
    }
    
   
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_CAREER_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_CAREER_DATA)
    public ResponseEntity<List<GradStudentCareerProgram>> getAllStudentCareerProgramsList(@PathVariable String pen) { 
    	logger.debug("getAllStudentCareerProgramsList : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return response.GET(codeService.getAllGradStudentCareerProgramList(pen,accessToken));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_CERTIFICATE_BY_CERTIFICATE_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_CERTIFICATES)
    public ResponseEntity<Boolean> getStudentCertifcate(@PathVariable String certificateType) { 
    	logger.debug("getStudentCertifcate : ");
        return response.GET(codeService.getStudentCertificate(certificateType));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_REPORT_BY_REPORT_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_REPORTS)
    public ResponseEntity<Boolean> getStudentReport(@PathVariable String reasonType) { 
    	logger.debug("getStudentReport : ");
        return response.GET(codeService.getStudentReport(reasonType));
    }
    
    
    @PostMapping (EducGradCommonApiConstants.STUDENT_REPORT)
    @PreAuthorize(PermissionsContants.UPDATE_GRADUATION_STUDENT_REPORTS)
    public ResponseEntity<ApiResponseModel<GradStudentReports>> saveStudentReport(@RequestBody GradStudentReports gradStudentReports) {
        logger.debug("Save student Grad Report for PEN: " + gradStudentReports.getPen());
        validation.requiredField(gradStudentReports.getPen(), "Pen");
        return response.UPDATED(codeService.saveGradReports(gradStudentReports));
    }
    
    @GetMapping(EducGradCommonApiConstants.STUDENT_REPORT)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_REPORTS)
    public ResponseEntity<InputStreamResource> getStudentReportByType(
    		@RequestParam(value = "pen", required = true) String pen,
    		@RequestParam(value = "reportType", required = true) String reportType) { 
    	logger.debug("getStudentReportByType : ");
    	return codeService.getStudentReportByType(pen,reportType);
    }
   
}
