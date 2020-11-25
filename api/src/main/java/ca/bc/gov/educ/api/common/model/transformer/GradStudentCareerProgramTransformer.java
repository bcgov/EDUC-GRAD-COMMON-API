package ca.bc.gov.educ.api.common.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.common.model.dto.GradStudentCareerProgram;
import ca.bc.gov.educ.api.common.model.entity.GradStudentCareerProgramEntity;


@Component
public class GradStudentCareerProgramTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentCareerProgram transformToDTO (GradStudentCareerProgramEntity gradProgramEntity) {
    	GradStudentCareerProgram gradCertificateTypes = modelMapper.map(gradProgramEntity, GradStudentCareerProgram.class);
        return gradCertificateTypes;
    }

    public GradStudentCareerProgram transformToDTO ( Optional<GradStudentCareerProgramEntity> gradProgramEntity ) {
    	GradStudentCareerProgramEntity cae = new GradStudentCareerProgramEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        GradStudentCareerProgram gradCertificateTypes = modelMapper.map(cae, GradStudentCareerProgram.class);
        return gradCertificateTypes;
    }

	public List<GradStudentCareerProgram> transformToDTO (Iterable<GradStudentCareerProgramEntity> gradCertificateTypesEntities ) {
		List<GradStudentCareerProgram> gradCertificateTypesList = new ArrayList<GradStudentCareerProgram>();
        for (GradStudentCareerProgramEntity gradCertificateTypesEntity : gradCertificateTypesEntities) {
        	GradStudentCareerProgram gradCertificateTypes = new GradStudentCareerProgram();
        	gradCertificateTypes = modelMapper.map(gradCertificateTypesEntity, GradStudentCareerProgram.class);            
        	gradCertificateTypesList.add(gradCertificateTypes);
        }
        return gradCertificateTypesList;
    }

    public GradStudentCareerProgramEntity transformToEntity(GradStudentCareerProgram gradCertificateTypes) {
        GradStudentCareerProgramEntity gradCertificateTypesEntity = modelMapper.map(gradCertificateTypes, GradStudentCareerProgramEntity.class);
        return gradCertificateTypesEntity;
    }
}
