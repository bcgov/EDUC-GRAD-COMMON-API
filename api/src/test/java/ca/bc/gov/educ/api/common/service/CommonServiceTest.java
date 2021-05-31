package ca.bc.gov.educ.api.common.service;

import ca.bc.gov.educ.api.common.model.dto.*;
import ca.bc.gov.educ.api.common.model.transformer.*;
import ca.bc.gov.educ.api.common.repository.*;
import ca.bc.gov.educ.api.common.util.EducGradCommonApiConstants;

import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CommonServiceTest {

    @Autowired
    EducGradCommonApiConstants constants;

    @Autowired
    private CommonService commonService;

    @MockBean
    private GradAlgorithmRulesRepository gradAlgorithmRulesRepository;

    @MockBean
    private GradStudentCareerProgramRepository gradStudentCareerProgramRepository;

    @MockBean
    private GradStudentCertificatesRepository gradStudentCertificatesRepository;

    @MockBean
    private GradStudentReportsRepository gradStudentReportsRepository;

    @MockBean
    private GradStudentUngradReasonsRepository gradStudentUngradReasonsRepository;

    @MockBean
    private StudentNoteRepository studentNoteRepository;

    @MockBean
    private GradStudentUngradReasonsTransformer gradStudentUngradReasonsTransformer;

    @MockBean
    private GradStudentCareerProgramTransformer gradStudentCareerProgramTransformer;

    @MockBean
    private GradStudentCertificatesTransformer gradStudentCertificatesTransformer;

    @MockBean
    private GradAlgorithmRulesTransformer gradAlgorithmRulesTransformer;

    @MockBean
    private GradStudentReportsTransformer gradStudentReportsTransformer;

    @MockBean
    private StudentNoteTransformer studentNoteTransformer;


    @MockBean
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @After
    public void tearDown() {

    }

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

        when(gradStudentUngradReasonsTransformer.transformToDTO(gradStudentUngradReasonsRepository.findByStudentID(studentID))).thenReturn(gradStudentUngradReasonsList);

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getUngradReasonByCodeUrl(),gradUngradReason.getCode()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradUngradReasons.class)).thenReturn(Mono.just(gradUngradReason));

        var result = commonService.getAllStudentUngradReasonsList(studentID, "accessToken");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(0).getUngradReasonCode()).isEqualTo(gradUngradReason.getCode());
        assertThat(result.get(0).getUngradReasonName()).isEqualTo(gradUngradReason.getDescription());
        assertThat(result.get(1).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(1).getUngradReasonCode()).isEqualTo(gradUngradReason.getCode());
        assertThat(result.get(1).getUngradReasonName()).isEqualTo(gradUngradReason.getDescription());
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

        when(gradStudentCareerProgramTransformer.transformToDTO(gradStudentCareerProgramRepository.findByPen(pen))).thenReturn(gradStudentCareerProgramList);

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getCareerProgramByCodeUrl(), gradCareerProgram.getCode()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradCareerProgram.class)).thenReturn(Mono.just(gradCareerProgram));

        var result = commonService.getAllGradStudentCareerProgramList(pen, "accessToken");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(0).getCareerProgramCode()).isEqualTo(gradCareerProgram.getCode());
        assertThat(result.get(0).getCareerProgramName()).isEqualTo(gradCareerProgram.getDescription());
        assertThat(result.get(1).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(1).getCareerProgramCode()).isEqualTo(gradCareerProgram.getCode());
        assertThat(result.get(1).getCareerProgramName()).isEqualTo(gradCareerProgram.getDescription());
    }

    @Test
    public void testGetStudentCertificate() {
        // TODO (jsung)
    }

    @Test
    public void testSaveStudentCertificates() {
        // TODO (jsung)
    }

    @Test
    public void testGetStudentReport() {
        // TODO (jsung)
    }

    @Test
    public void testSaveStudentReports() {
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

        when(gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByPen(pen))).thenReturn(gradStudentCertificatesList);

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getCertificateByCodeUrl(), gradCertificateType.getCode()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradCertificateTypes.class)).thenReturn(Mono.just(gradCertificateType));

        var result = commonService.getAllStudentCertificateList(pen, "accessToken");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(0).getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
        assertThat(result.get(0).getGradCertificateTypeDesc()).isEqualTo(gradCertificateType.getDescription());
        assertThat(result.get(1).getStudentID()).isEqualTo(studentID);
        assertThat(result.get(1).getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
        assertThat(result.get(1).getGradCertificateTypeDesc()).isEqualTo(gradCertificateType.getDescription());
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

        when(gradAlgorithmRulesTransformer.transformToDTO(gradAlgorithmRulesRepository.getAlgorithmRulesByProgramCode(programCode))).thenReturn(algorithmsRulesList);

        var result = commonService.getAlgorithmRulesList(programCode);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getRuleName()).isEqualTo(gradAlgorithmRule2.getRuleName());
        assertThat(result.get(1).getRuleName()).isEqualTo(gradAlgorithmRule1.getRuleName());

    }

    @Test
    public void testGetAllAlgorithmRulesList() {
        // UUID
        UUID ID = UUID.randomUUID();
        String programCode1 = "2018-EN";
        String programCode2 = "2018-FI";

        // Student Certificate Types
        List<GradAlgorithmRules> algorithmsRulesList = new ArrayList<>();

        GradAlgorithmRules gradAlgorithmRule1 = new GradAlgorithmRules();
        gradAlgorithmRule1.setId(ID);
        gradAlgorithmRule1.setRuleName("Test1");
        gradAlgorithmRule1.setRuleDescription("Test1 Description");
        gradAlgorithmRule1.setProgramCode(programCode1);
        gradAlgorithmRule1.setSortOrder(2);
        algorithmsRulesList.add(gradAlgorithmRule1);

        GradAlgorithmRules gradAlgorithmRule2 = new GradAlgorithmRules();
        gradAlgorithmRule2.setId(ID);
        gradAlgorithmRule2.setRuleName("Test2");
        gradAlgorithmRule2.setRuleDescription("Test2 Description");
        gradAlgorithmRule2.setProgramCode(programCode1);
        gradAlgorithmRule2.setSortOrder(1);
        algorithmsRulesList.add(gradAlgorithmRule2);

        GradAlgorithmRules gradAlgorithmRule3 = new GradAlgorithmRules();
        gradAlgorithmRule3.setId(ID);
        gradAlgorithmRule3.setRuleName("Test3");
        gradAlgorithmRule3.setRuleDescription("Test3 Description");
        gradAlgorithmRule3.setProgramCode(programCode2);
        gradAlgorithmRule3.setSortOrder(2);
        algorithmsRulesList.add(gradAlgorithmRule3);

        GradAlgorithmRules gradAlgorithmRule4 = new GradAlgorithmRules();
        gradAlgorithmRule4.setId(ID);
        gradAlgorithmRule4.setRuleName("Test4");
        gradAlgorithmRule4.setRuleDescription("Test4 Description");
        gradAlgorithmRule4.setProgramCode(programCode2);
        gradAlgorithmRule4.setSortOrder(1);
        algorithmsRulesList.add(gradAlgorithmRule4);

        when(gradAlgorithmRulesTransformer.transformToDTO(gradAlgorithmRulesRepository.findAll())).thenReturn(algorithmsRulesList);

        var result = commonService.getAllAlgorithmRulesList();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get(0).getRuleName()).isEqualTo(gradAlgorithmRule2.getRuleName());
        assertThat(result.get(1).getRuleName()).isEqualTo(gradAlgorithmRule1.getRuleName());
        assertThat(result.get(2).getRuleName()).isEqualTo(gradAlgorithmRule4.getRuleName());
        assertThat(result.get(3).getRuleName()).isEqualTo(gradAlgorithmRule3.getRuleName());

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

        when(studentNoteTransformer.transformToDTO(studentNoteRepository.findByPen(pen))).thenReturn(allNotesList);

        var result = commonService.getAllStudentNotes(pen);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getStudentID()).isEqualTo(studentID.toString());
        assertThat(result.get(1).getStudentID()).isEqualTo(studentID.toString());
        assertThat(result.get(0).getNote()).isEqualTo(note2.getNote());
        assertThat(result.get(1).getNote()).isEqualTo(note1.getNote());
    }

    @Test
    public void testSaveStudentNote() {
        // TODO (jsung)
    }

    @Test
    public void testDeleteNote() {
        // TODO (jsung)
    }


}
