package ca.bc.gov.educ.api.common.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.common.model.dto.GradStudentCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentUngradReasons;
import ca.bc.gov.educ.api.common.service.CommonService;
import ca.bc.gov.educ.api.common.util.EducGradCommonApiConstants;

@CrossOrigin
@RestController
@RequestMapping(EducGradCommonApiConstants.GRAD_COMMON_API_ROOT_MAPPING)
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    CommonService codeService;
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_UNGRAD_MAPPING)
    public List<GradStudentUngradReasons> getAllStudentUngradReasonsList(@PathVariable String pen) { 
    	logger.debug("getAllStudentUngradReasonsList : ");
        return codeService.getAllStudentUngradReasonsList(pen);
    }
    
   
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_CAREER_MAPPING)
    public List<GradStudentCareerProgram> getAllStudentCareerProgramsList(@PathVariable String pen) { 
    	logger.debug("getAllStudentCareerProgramsList : ");
        return codeService.getAllGradStudentCareerProgramList(pen);
    }
    
   
}
