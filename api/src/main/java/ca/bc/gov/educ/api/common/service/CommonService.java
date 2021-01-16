package ca.bc.gov.educ.api.common.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ca.bc.gov.educ.api.common.model.dto.GradCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentReport;
import ca.bc.gov.educ.api.common.model.dto.GradStudentUngradReasons;
import ca.bc.gov.educ.api.common.model.dto.GradUngradReasons;
import ca.bc.gov.educ.api.common.model.entity.GradStudentCareerProgramEntity;
import ca.bc.gov.educ.api.common.model.entity.GradStudentReportEntity;
import ca.bc.gov.educ.api.common.model.entity.GradStudentUngradReasonsEntity;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentCareerProgramTransformer;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentReportTransformer;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentUngradReasonsTransformer;
import ca.bc.gov.educ.api.common.repository.GradStudentCareerProgramRepository;
import ca.bc.gov.educ.api.common.repository.GradStudentReportRepository;
import ca.bc.gov.educ.api.common.repository.GradStudentUngradReasonsRepository;
import ca.bc.gov.educ.api.common.util.EducGradCommonApiConstants;
import ca.bc.gov.educ.api.common.util.EducGradCommonApiUtils;


@Service
public class CommonService {

    @Autowired
    private GradStudentUngradReasonsTransformer gradStudentUngradReasonsTransformer;
    
    @Autowired
    private GradStudentUngradReasonsRepository gradStudentUngradReasonsRepository; 
    
    @Autowired
    private GradStudentCareerProgramRepository gradStudentCareerProgramRepository;
    
    @Autowired
    private GradStudentReportRepository gradStudentReportRepository;
    
    @Autowired
    private GradStudentReportTransformer gradStudentReportTransformer;

    @Autowired
    private GradStudentCareerProgramTransformer gradStudentCareerProgramTransformer;
    
    @Value(EducGradCommonApiConstants.ENDPOINT_UNGRAD_REASON_BY_CODE_URL)
    private String getUngradReasonByCodeURL;
    
    @Value(EducGradCommonApiConstants.ENDPOINT_CAREER_PROGRAM_BY_CODE_URL)
    private String getCareerProgramByCodeURL;
    
    @Autowired
    RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Transactional
    public List<GradStudentUngradReasons> getAllStudentUngradReasonsList(String pen, String accessToken) {
	        List<GradStudentUngradReasons> gradStudentUngradReasonsList  = new ArrayList<GradStudentUngradReasons>();
	        HttpHeaders httpHeaders = EducGradCommonApiUtils.getHeaders(accessToken);
	        try {
	        	gradStudentUngradReasonsList = gradStudentUngradReasonsTransformer.transformToDTO(gradStudentUngradReasonsRepository.findByPen(pen));  
	        	gradStudentUngradReasonsList.forEach(sC -> {
	        		GradUngradReasons ungradReasonObj = restTemplate.exchange(String.format(getUngradReasonByCodeURL,sC.getUngradReasonCode()), HttpMethod.GET,
	        				new HttpEntity<>(httpHeaders), GradUngradReasons.class).getBody();
	        		if(ungradReasonObj != null) {
	        			sC.setUngradReasonName(ungradReasonObj.getDescription());
	        		}
	        	});
	        } catch (Exception e) {
	            logger.debug("Exception:" + e);
	        }

	        return gradStudentUngradReasonsList;
	    }

    @Transactional
  	public List<GradStudentCareerProgram> getAllGradStudentCareerProgramList(String pen, String accessToken) {
  		List<GradStudentCareerProgram> gradStudentCareerProgramList  = new ArrayList<GradStudentCareerProgram>();
  		 HttpHeaders httpHeaders = EducGradCommonApiUtils.getHeaders(accessToken);
  		try {
          	gradStudentCareerProgramList = gradStudentCareerProgramTransformer.transformToDTO(gradStudentCareerProgramRepository.findByPen(pen)); 
          	gradStudentCareerProgramList.forEach(sC -> {
        		GradCareerProgram gradCareerProgram = restTemplate.exchange(String.format(getCareerProgramByCodeURL,sC.getCareerProgramCode()), HttpMethod.GET,
        				new HttpEntity<>(httpHeaders), GradCareerProgram.class).getBody();
        		if(gradCareerProgram != null) {
        			sC.setCareerProgramName(gradCareerProgram.getDescription());
        		}
        	});
          } catch (Exception e) {
              logger.debug("Exception:" + e);
          }

          return gradStudentCareerProgramList;
  	}

    public boolean getStudentUngradReasons(String reasonCode) {
		List<GradStudentUngradReasonsEntity> gradList = gradStudentUngradReasonsRepository.existsByReasonCode(reasonCode);
		if(gradList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}

	public boolean getStudentCareerProgram(String cpCode) {
		List<GradStudentCareerProgramEntity> gradList = gradStudentCareerProgramRepository.existsByCareerProgramCode(cpCode);
		if(gradList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}

	public GradStudentReport saveGradReports(String pen, GradStudentReport gradStudentReport) {
		GradStudentReportEntity toBeSaved = gradStudentReportTransformer.transformToEntity(gradStudentReport);
		Optional<GradStudentReportEntity> existingEnity = gradStudentReportRepository.findById(pen);
		if(existingEnity.isPresent()) {
			GradStudentReportEntity gradEntity = existingEnity.get();
			if(gradStudentReport.getStudentAchievementReport() != null) {
				gradEntity.setStudentAchievementReport(gradStudentReport.getStudentAchievementReport());
			}
			if(gradStudentReport.getStudentTranscriptReport() != null) {
				gradEntity.setStudentTranscriptReport(gradStudentReport.getStudentTranscriptReport());
			}
			return gradStudentReportTransformer.transformToDTO(gradStudentReportRepository.save(gradEntity));
		}else {
			return gradStudentReportTransformer.transformToDTO(gradStudentReportRepository.save(toBeSaved));
		}
	}
	
    
}
