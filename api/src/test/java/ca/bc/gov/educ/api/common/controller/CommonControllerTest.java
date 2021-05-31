package ca.bc.gov.educ.api.common.controller;


import ca.bc.gov.educ.api.common.model.dto.*;
import ca.bc.gov.educ.api.common.service.CommonService;
import ca.bc.gov.educ.api.common.util.ResponseHelper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CommonControllerTest {

    @Mock
    private CommonService commonService;

    @Mock
    ResponseHelper responseHelper;

    @InjectMocks
    private CommonController commonController;


    @Test
    public void testGetAllStudentUngradReasonsList() {
// UUID
        UUID studentID = UUID.randomUUID();
        // Ungrad Reasons
        GradUngradReasons gradUngradReason = new GradUngradReasons();
        gradUngradReason.setCode("TEST");
        gradUngradReason.setDescription("Test Code Name");

        // Student Ungrad Reasons Data
        List<GradStudentUngradReasons> gradStudentUngradReasonsList = new ArrayList<>();
        GradStudentUngradReasons studentUngradReason1 = new GradStudentUngradReasons();
        studentUngradReason1.setId(UUID.randomUUID());
        studentUngradReason1.setPen("123456789");
        studentUngradReason1.setStudentID(studentID);
        studentUngradReason1.setUngradReasonCode(gradUngradReason.getCode());
        gradStudentUngradReasonsList.add(studentUngradReason1);

        GradStudentUngradReasons studentUngradReason2 = new GradStudentUngradReasons();
        studentUngradReason2.setId(UUID.randomUUID());
        studentUngradReason2.setPen("123456789");
        studentUngradReason2.setStudentID(studentID);
        studentUngradReason2.setUngradReasonCode(gradUngradReason.getCode());
        gradStudentUngradReasonsList.add(studentUngradReason2);

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(commonService.getAllStudentUngradReasonsList(studentID, null)).thenReturn(gradStudentUngradReasonsList);
        commonController.getAllStudentUngradReasonsList(studentID.toString());
        Mockito.verify(commonService).getAllStudentUngradReasonsList(studentID, null);

    }

    @Test
    public void testCreateGradStudentUngradReason() {
        // TODO (jsung)
    }

    @Test
    public void testGetStudentUngradReasons() {
        // TODO (jsung)
    }

    @Test
    public void testGetStudentCareerProgram() {
        // TODO (jsung)
    }

    @Test
    public void testGetAllStudentCareerProgramsList() {
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        // Career Program
        GradCareerProgram gradCareerProgram = new GradCareerProgram();
        gradCareerProgram.setCode("TEST");
        gradCareerProgram.setDescription("Test Code Name");

        // Student Career Program Data
        List<GradStudentCareerProgram> gradStudentCareerProgramList = new ArrayList<>();
        GradStudentCareerProgram studentCareerProgram1 = new GradStudentCareerProgram();
        studentCareerProgram1.setId(UUID.randomUUID());
        studentCareerProgram1.setPen(pen);
        studentCareerProgram1.setStudentID(studentID);
        studentCareerProgram1.setCareerProgramCode(gradCareerProgram.getCode());
        gradStudentCareerProgramList.add(studentCareerProgram1);

        GradStudentCareerProgram studentCareerProgram2 = new GradStudentCareerProgram();
        studentCareerProgram2.setId(UUID.randomUUID());
        studentCareerProgram2.setPen(pen);
        studentCareerProgram2.setStudentID(studentID);
        studentCareerProgram2.setCareerProgramCode(gradCareerProgram.getCode());
        gradStudentCareerProgramList.add(studentCareerProgram2);

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(commonService.getAllGradStudentCareerProgramList(pen, null)).thenReturn(gradStudentCareerProgramList);
        commonController.getAllStudentCareerProgramsList(pen);
        Mockito.verify(commonService).getAllGradStudentCareerProgramList(pen, null);
    }

    @Test
    public void testGetStudentCertificate() {
        // TODO (jsung)
    }

    @Test
    public void testSaveStudentCertificate() {
        // TODO (jsung)
    }

    @Test
    public void testGetStudentReport() {
        // TODO (jsung)
    }

    @Test
    public void testSaveStudentReport() {
        // TODO (jsung)
    }

    @Test
    public void testGetStudentReportByType() {
        // TODO (jsung)
    }

    @Test
    public void testGetStudentCertificateByType() {
        // TODO (jsung)
    }

    @Test
    public void testGetAllStudentCertificateList() {
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        // Certificate Type
        GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        List<GradStudentCertificates> gradStudentCertificatesList = new ArrayList<>();
        GradStudentCertificates studentCertificate1 = new GradStudentCertificates();
        studentCertificate1.setId(UUID.randomUUID());
        studentCertificate1.setPen(pen);
        studentCertificate1.setStudentID(studentID);
        studentCertificate1.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate1);

        GradStudentCertificates studentCertificate2 = new GradStudentCertificates();
        studentCertificate2.setId(UUID.randomUUID());
        studentCertificate2.setPen(pen);
        studentCertificate2.setStudentID(studentID);
        studentCertificate2.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate2);

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(commonService.getAllStudentCertificateList(pen, null)).thenReturn(gradStudentCertificatesList);
        commonController.getAllStudentCertificateList(pen);
        Mockito.verify(commonService).getAllStudentCertificateList(pen, null);
    }

    @Test
    public void testGetAlgorithmRulesList() {
        // UUID
        UUID ID = UUID.randomUUID();
        String programCode = "2018-EN";

        // Student Certificate Types
        List<GradAlgorithmRules> algorithmsRulesList = new ArrayList<>();

        GradAlgorithmRules gradAlgorithmRule1 = new GradAlgorithmRules();
        gradAlgorithmRule1.setId(ID);
        gradAlgorithmRule1.setRuleName("Test1");
        gradAlgorithmRule1.setRuleDescription("Test1 Description");
        gradAlgorithmRule1.setProgramCode(programCode);
        gradAlgorithmRule1.setSortOrder(2);
        algorithmsRulesList.add(gradAlgorithmRule1);

        GradAlgorithmRules gradAlgorithmRule2 = new GradAlgorithmRules();
        gradAlgorithmRule2.setId(ID);
        gradAlgorithmRule2.setRuleName("Test2");
        gradAlgorithmRule2.setRuleDescription("Test2 Description");
        gradAlgorithmRule2.setProgramCode(programCode);
        gradAlgorithmRule2.setSortOrder(1);
        algorithmsRulesList.add(gradAlgorithmRule2);

        Mockito.when(commonService.getAlgorithmRulesList(programCode)).thenReturn(algorithmsRulesList);
        commonController.getAlgorithmRulesList(programCode);
        Mockito.verify(commonService).getAlgorithmRulesList(programCode);
    }

    @Test
    public void testGetAllAlgorithmRulesList() {
        // UUID
        UUID ID = UUID.randomUUID();
        String programCode = "2018-EN";

        // Student Certificate Types
        List<GradAlgorithmRules> algorithmsRulesList = new ArrayList<>();

        GradAlgorithmRules gradAlgorithmRule1 = new GradAlgorithmRules();
        gradAlgorithmRule1.setId(ID);
        gradAlgorithmRule1.setRuleName("Test1");
        gradAlgorithmRule1.setRuleDescription("Test1 Description");
        gradAlgorithmRule1.setProgramCode(programCode);
        gradAlgorithmRule1.setSortOrder(2);
        algorithmsRulesList.add(gradAlgorithmRule1);

        GradAlgorithmRules gradAlgorithmRule2 = new GradAlgorithmRules();
        gradAlgorithmRule2.setId(ID);
        gradAlgorithmRule2.setRuleName("Test2");
        gradAlgorithmRule2.setRuleDescription("Test2 Description");
        gradAlgorithmRule2.setProgramCode(programCode);
        gradAlgorithmRule2.setSortOrder(1);
        algorithmsRulesList.add(gradAlgorithmRule2);

        Mockito.when(commonService.getAllAlgorithmRulesList()).thenReturn(algorithmsRulesList);
        commonController.getAllAlgorithmRulesList();
        Mockito.verify(commonService).getAllAlgorithmRulesList();
    }

    @Test
    public void testGetAllStudentNotes() {
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";

        List<StudentNote> allNotesList = new ArrayList<>();

        StudentNote note1 = new StudentNote();
        note1.setId(UUID.randomUUID());
        note1.setStudentID(studentID.toString());
        note1.setPen(pen);
        note1.setNote("Test1 Comments");
        note1.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
        allNotesList.add(note1);

        StudentNote note2 = new StudentNote();
        note2.setId(UUID.randomUUID());
        note2.setStudentID(studentID.toString());
        note2.setPen(pen);
        note2.setNote("Test2 Comments");
        note2.setUpdatedTimestamp(new Date(System.currentTimeMillis() + 100000L));
        allNotesList.add(note2);

        Mockito.when(commonService.getAllStudentNotes(pen)).thenReturn(allNotesList);
        commonController.getAllStudentNotes(pen);
        Mockito.verify(commonService).getAllStudentNotes(pen);
    }

    @Test
    public void testSaveStudentNotes() {
        // TODO (jsung)
    }

    @Test
    public void testDeleteNotes() {
        // TODO (jsung)
    }


}
