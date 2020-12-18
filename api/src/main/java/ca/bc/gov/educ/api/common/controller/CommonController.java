package ca.bc.gov.educ.api.common.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.common.model.dto.GradStudentCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentUngradReasons;
import ca.bc.gov.educ.api.common.service.CommonService;
import ca.bc.gov.educ.api.common.util.EducGradCommonApiConstants;
import ca.bc.gov.educ.api.common.util.PermissionsContants;
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
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_UNGRAD_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_UNGRAD_REASONS_DATA)
    public List<GradStudentUngradReasons> getAllStudentUngradReasonsList(@PathVariable String pen) { 
    	logger.debug("getAllStudentUngradReasonsList : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return codeService.getAllStudentUngradReasonsList(pen,accessToken);
    }
    
   
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_CAREER_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_CAREER_DATA)
    public List<GradStudentCareerProgram> getAllStudentCareerProgramsList(@PathVariable String pen) { 
    	logger.debug("getAllStudentCareerProgramsList : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return codeService.getAllGradStudentCareerProgramList(pen,accessToken);
    }
    
   
}
