package ca.bc.gov.educ.api.common.service;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ca.bc.gov.educ.api.common.model.dto.GradCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentCareerProgram;
import ca.bc.gov.educ.api.common.model.dto.GradStudentReports;
import ca.bc.gov.educ.api.common.model.dto.GradStudentUngradReasons;
import ca.bc.gov.educ.api.common.model.dto.GradUngradReasons;
import ca.bc.gov.educ.api.common.model.entity.GradStudentCareerProgramEntity;
import ca.bc.gov.educ.api.common.model.entity.GradStudentCertificatesEntity;
import ca.bc.gov.educ.api.common.model.entity.GradStudentReportsEntity;
import ca.bc.gov.educ.api.common.model.entity.GradStudentUngradReasonsEntity;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentCareerProgramTransformer;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentCertificatesTransformer;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentReportTransformer;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentReportsTransformer;
import ca.bc.gov.educ.api.common.model.transformer.GradStudentUngradReasonsTransformer;
import ca.bc.gov.educ.api.common.repository.GradStudentCareerProgramRepository;
import ca.bc.gov.educ.api.common.repository.GradStudentCertificatesRepository;
import ca.bc.gov.educ.api.common.repository.GradStudentReportRepository;
import ca.bc.gov.educ.api.common.repository.GradStudentReportsRepository;
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
    
    @Autowired
    private GradStudentCertificatesTransformer gradStudentCertificatesTransformer;
    
    @Autowired
    private GradStudentCertificatesRepository gradStudentCertificatesRepository; 
    
    @Autowired
    private GradStudentReportsTransformer gradStudentReportsTransformer;
    
    @Autowired
    private GradStudentReportsRepository gradStudentReportsRepository; 
    
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

	public GradStudentReports saveGradReports(GradStudentReports gradStudentReports) {
		GradStudentReportsEntity toBeSaved = gradStudentReportsTransformer.transformToEntity(gradStudentReports);
		Optional<GradStudentReportsEntity> existingEnity = gradStudentReportsRepository.findByPenAndGradReportTypeCode(gradStudentReports.getPen(), gradStudentReports.getGradReportTypeCode());
		if(existingEnity.isPresent()) {
			GradStudentReportsEntity gradEntity = existingEnity.get();
			if(gradStudentReports.getReport() != null) {
				gradEntity.setReport(gradStudentReports.getReport());
			}			
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(gradEntity));
		}else {
			return gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.save(toBeSaved));
		}
	}
	
	public ResponseEntity<InputStreamResource> getStudentReportByType(String pen, String reportType) {
		GradStudentReports studentReport = gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByPenAndReportTypeCode(pen,reportType));
		if(studentReport != null) {
			if(reportType.equalsIgnoreCase("ACHV")) {
				if(studentReport.getReport() != null) {
					byte[] reportByte = Base64.decodeBase64(new String(studentReport.getReport()).getBytes(StandardCharsets.US_ASCII));
					ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
				    HttpHeaders headers = new HttpHeaders();
			        headers.add("Content-Disposition", "inline; filename=studentachievementreport.pdf");
				    return ResponseEntity
			                .ok()
			                .headers(headers)
			                .contentType(MediaType.APPLICATION_PDF)
			                .body(new InputStreamResource(bis));
				}
			}else if(reportType.equalsIgnoreCase("TRAN")) {
				if(studentReport.getReport() != null) {
					byte[] reportByte = Base64.decodeBase64(new String(studentReport.getReport()).getBytes(StandardCharsets.US_ASCII));
					ByteArrayInputStream bis = new ByteArrayInputStream(reportByte);
				    HttpHeaders headers = new HttpHeaders();
			        headers.add("Content-Disposition", "inline; filename=studenttranscriptreport.pdf");
				    return ResponseEntity
			                .ok()
			                .headers(headers)
			                .contentType(MediaType.APPLICATION_PDF)
			                .body(new InputStreamResource(bis));
				}
			}
		}
		return null;
	}

	public boolean getStudentCertificate(String certificateType) {
		List<GradStudentCertificatesEntity> gradList = gradStudentCertificatesRepository.existsByCertificateTypeCode(certificateType);
		if(gradList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}

	public boolean getStudentReport(String reasonType) {
		List<GradStudentReportsEntity> gradList = gradStudentReportsRepository.existsByReportTypeCode(reasonType);
		if(gradList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	
    
}
