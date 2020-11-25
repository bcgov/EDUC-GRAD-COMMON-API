package ca.bc.gov.educ.api.common.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.common.model.dto.GradStudentCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentUngradReasons;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentCareerProgramTransformer;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentUngradReasonsTransformer;
import ca.bc.gov.educ.api.common.repository.GradStudentCareerProgramRepository;
import ca.bc.gov.educ.api.common.repository.GradStudentUngradReasonsRepository;


@Service
public class CommonService {

    @Autowired
    private GradStudentUngradReasonsTransformer gradStudentUngradReasonsTransformer;
    
    @Autowired
    private GradStudentUngradReasonsRepository gradStudentUngradReasonsRepository; 
    
    @Autowired
    private GradStudentCareerProgramRepository gradStudentCareerProgramRepository;  

    @Autowired
    private GradStudentCareerProgramTransformer gradStudentCareerProgramTransformer;

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Transactional
    public List<GradStudentUngradReasons> getAllStudentUngradReasonsList(String pen) {
	        List<GradStudentUngradReasons> gradStudentUngradReasonsList  = new ArrayList<GradStudentUngradReasons>();
	        try {
	        	gradStudentUngradReasonsList = gradStudentUngradReasonsTransformer.transformToDTO(gradStudentUngradReasonsRepository.findByPen(pen));            
	        } catch (Exception e) {
	            logger.debug("Exception:" + e);
	        }

	        return gradStudentUngradReasonsList;
	    }

    @Transactional
  	public List<GradStudentCareerProgram> getAllGradStudentCareerProgramList(String pen) {
  		List<GradStudentCareerProgram> gradStudentCareerProgramList  = new ArrayList<GradStudentCareerProgram>();
          try {
          	gradStudentCareerProgramList = gradStudentCareerProgramTransformer.transformToDTO(gradStudentCareerProgramRepository.findByPen(pen));            
          } catch (Exception e) {
              logger.debug("Exception:" + e);
          }

          return gradStudentCareerProgramList;
  	}
    
}
