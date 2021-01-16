package ca.bc.gov.educ.api.common.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.common.model.dto.GradStudentReport;
import ca.bc.gov.educ.api.common.model.entity.GradStudentReportEntity;


@Component
public class GradStudentReportTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentReport transformToDTO (GradStudentReportEntity gradStudentReportEntity) {
    	GradStudentReport gradCertificateTypes = modelMapper.map(gradStudentReportEntity, GradStudentReport.class);
        return gradCertificateTypes;
    }

    public GradStudentReport transformToDTO ( Optional<GradStudentReportEntity> gradStudentReportEntity ) {
    	GradStudentReportEntity cae = new GradStudentReportEntity();
        if (gradStudentReportEntity.isPresent())
            cae = gradStudentReportEntity.get();

        GradStudentReport gradCertificateTypes = modelMapper.map(cae, GradStudentReport.class);
        return gradCertificateTypes;
    }

	public List<GradStudentReport> transformToDTO (Iterable<GradStudentReportEntity> gradCertificateTypesEntities ) {
		List<GradStudentReport> gradCertificateTypesList = new ArrayList<GradStudentReport>();
        for (GradStudentReportEntity gradCertificateTypesEntity : gradCertificateTypesEntities) {
        	GradStudentReport gradCertificateTypes = new GradStudentReport();
        	gradCertificateTypes = modelMapper.map(gradCertificateTypesEntity, GradStudentReport.class);            
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public GradStudentReportEntity transformToEntity(GradStudentReport gradCertificateTypes) {
        GradStudentReportEntity gradCertificateTypesEntity = modelMapper.map(gradCertificateTypes, GradStudentReportEntity.class);
        return gradCertificateTypesEntity;
    }
}
