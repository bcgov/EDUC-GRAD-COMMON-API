package ca.bc.gov.educ.api.common.service;

import ca.bc.gov.educ.api.common.model.dto.*;
import ca.bc.gov.educ.api.common.model.entity.*;
import ca.bc.gov.educ.api.common.model.transformer.*;
import ca.bc.gov.educ.api.common.repository.*;
import ca.bc.gov.educ.api.common.util.EducGradCommonApiConstants;

import ca.bc.gov.educ.api.common.util.GradBusinessRuleException;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
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
        // UUID
        UUID studentID = UUID.randomUUID();
        // Ungrad Reasons
        GradUngradReasons gradUngradReason = new GradUngradReasons();
        gradUngradReason.setCode("TEST");
        gradUngradReason.setDescription("Test Code Name");

        // Student Ungrad Reasons
        GradStudentUngradReasons studentUngradReason = new GradStudentUngradReasons();
        studentUngradReason.setPen("123456789");
        studentUngradReason.setStudentID(studentID);
        studentUngradReason.setUngradReasonCode(gradUngradReason.getCode());

        // Student Ungrad Reasons Entity
        GradStudentUngradReasonsEntity studentUngradReasonEntity = new GradStudentUngradReasonsEntity();
        studentUngradReasonEntity.setPen("123456789");
        studentUngradReasonEntity.setStudentID(studentID);
        studentUngradReasonEntity.setUngradReasonCode(gradUngradReason.getCode());

        when(this.gradStudentUngradReasonsTransformer.transformToEntity(studentUngradReason)).thenReturn(studentUngradReasonEntity);

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getUngradReasonByCodeUrl(),gradUngradReason.getCode()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradUngradReasons.class)).thenReturn(Mono.just(gradUngradReason));

        when(this.gradStudentUngradReasonsTransformer.transformToDTO(this.gradStudentUngradReasonsRepository.save(studentUngradReasonEntity))).thenReturn(studentUngradReason);

        var result = commonService.createGradStudentUngradReasons(studentUngradReason, "accessToken");

        assertThat(result).isNotNull();
    }

    @Test
    public void testCreateGradStudentUngradReasonWithExistingEntity_thenReturnBusinessException() {
        // UUID
        UUID ungradReasonID = UUID.randomUUID();
        UUID studentID = UUID.randomUUID();
        // Ungrad Reasons
        GradUngradReasons gradUngradReason = new GradUngradReasons();
        gradUngradReason.setCode("TEST");
        gradUngradReason.setDescription("Test Code Name");

        // Student Ungrad Reasons
        GradStudentUngradReasons studentUngradReason = new GradStudentUngradReasons();
        studentUngradReason.setId(ungradReasonID);
        studentUngradReason.setPen("123456789");
        studentUngradReason.setStudentID(studentID);
        studentUngradReason.setUngradReasonCode(gradUngradReason.getCode());

        // Student Ungrad Reasons Entity
        GradStudentUngradReasonsEntity studentUngradReasonEntity = new GradStudentUngradReasonsEntity();
        studentUngradReasonEntity.setId(ungradReasonID);
        studentUngradReasonEntity.setPen("123456789");
        studentUngradReasonEntity.setStudentID(studentID);
        studentUngradReasonEntity.setUngradReasonCode(gradUngradReason.getCode());

        Optional<GradStudentUngradReasonsEntity> optional = Optional.of(studentUngradReasonEntity);

        when(this.gradStudentUngradReasonsTransformer.transformToEntity(studentUngradReason)).thenReturn(studentUngradReasonEntity);
        when(this.gradStudentUngradReasonsRepository.findById(ungradReasonID)).thenReturn(optional);

        try {
            var result = commonService.createGradStudentUngradReasons(studentUngradReason, "accessToken");
            Assertions.fail("Business Exception should have been thrown!");
        } catch (GradBusinessRuleException gbre) {
            assertThat(gbre.getMessage()).isNotNull();
        }
    }

    @Test
    public void testGetStudentUngradReasons() {
        // UUID
        UUID studentID = UUID.randomUUID();
        // Ungrad Reasons
        GradUngradReasons gradUngradReason = new GradUngradReasons();
        gradUngradReason.setCode("TEST");
        gradUngradReason.setDescription("Test Code Name");

        // Student Ungrad Reasons Data
        List<GradStudentUngradReasonsEntity> gradStudentUngradReasonsList = new ArrayList<>();
        GradStudentUngradReasonsEntity studentUngradReason = new GradStudentUngradReasonsEntity();
        studentUngradReason.setId(UUID.randomUUID());
        studentUngradReason.setPen("123456789");
        studentUngradReason.setStudentID(studentID);
        studentUngradReason.setUngradReasonCode(gradUngradReason.getCode());
        gradStudentUngradReasonsList.add(studentUngradReason);

        when(gradStudentUngradReasonsRepository.existsByReasonCode(gradUngradReason.getCode())).thenReturn(gradStudentUngradReasonsList);
        var result = commonService.getStudentUngradReasons(gradUngradReason.getCode());
        assertThat(result).isTrue();

    }

    @Test
    public void testGetStudentCareerProgram() {
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        // Career Program
        GradCareerProgram gradCareerProgram = new GradCareerProgram();
        gradCareerProgram.setCode("TEST");
        gradCareerProgram.setDescription("Test Code Name");

        // Student Career Program Data
        List<GradStudentCareerProgramEntity> gradStudentCareerProgramList = new ArrayList<>();
        GradStudentCareerProgramEntity studentCareerProgram = new GradStudentCareerProgramEntity();
        studentCareerProgram.setId(UUID.randomUUID());
        studentCareerProgram.setPen(pen);
        studentCareerProgram.setStudentID(studentID);
        studentCareerProgram.setCareerProgramCode(gradCareerProgram.getCode());
        gradStudentCareerProgramList.add(studentCareerProgram);

        when(gradStudentCareerProgramRepository.existsByCareerProgramCode(gradCareerProgram.getCode())).thenReturn(gradStudentCareerProgramList);
        var result = commonService.getStudentCareerProgram(gradCareerProgram.getCode());
        assertThat(result).isTrue();
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
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        // Certificate Type
        GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        List<GradStudentCertificatesEntity> gradStudentCertificatesList = new ArrayList<>();
        GradStudentCertificatesEntity studentCertificate = new GradStudentCertificatesEntity();
        studentCertificate.setId(UUID.randomUUID());
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());
        gradStudentCertificatesList.add(studentCertificate);

        when(gradStudentCertificatesRepository.existsByCertificateTypeCode(gradCertificateType.getCode())).thenReturn(gradStudentCertificatesList);
        var result = commonService.getStudentCertificate(gradCertificateType.getCode());
        assertThat(result).isTrue();
    }

    @Test
    public void testSaveGradStudentCertificates_thenReturnCreateSuccess() {
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        // Certificate Type
        GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        GradStudentCertificates studentCertificate = new GradStudentCertificates();
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setCertificate("Test Certificate Body");
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());

        // Student Certificate Types Entity
        GradStudentCertificatesEntity studentCertificateEntity = new GradStudentCertificatesEntity();
        studentCertificateEntity.setPen(pen);
        studentCertificateEntity.setStudentID(studentID);
        studentCertificateEntity.setGradCertificateTypeCode(gradCertificateType.getCode());

        Optional<GradStudentCertificatesEntity> optionalEmpty = Optional.empty();

        when(this.gradStudentCertificatesTransformer.transformToEntity(studentCertificate)).thenReturn(studentCertificateEntity);
        when(this.gradStudentCertificatesRepository.findByPenAndGradCertificateTypeCode(pen, gradCertificateType.getCode())).thenReturn(optionalEmpty);
        when(this.gradStudentCertificatesTransformer.transformToDTO(this.gradStudentCertificatesRepository.save(studentCertificateEntity))).thenReturn(studentCertificate);

        var result = commonService.saveGradCertificates(studentCertificate);

        assertThat(result).isNotNull();
        assertThat(result.getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
    }

    @Test
    public void testSaveGradStudentCertificatesWithExistingOne_thenReturnUpdateSuccess() {
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        // Certificate Type
        GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        GradStudentCertificates studentCertificate = new GradStudentCertificates();
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setCertificate("Test Certificate Body");
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());

        // Student Certificate Types Entity
        GradStudentCertificatesEntity studentCertificateEntity = new GradStudentCertificatesEntity();
        studentCertificateEntity.setPen(pen);
        studentCertificateEntity.setStudentID(studentID);
        studentCertificateEntity.setGradCertificateTypeCode(gradCertificateType.getCode());

        Optional<GradStudentCertificatesEntity> optional = Optional.of(studentCertificateEntity);

        when(this.gradStudentCertificatesTransformer.transformToEntity(studentCertificate)).thenReturn(studentCertificateEntity);
        when(this.gradStudentCertificatesRepository.findByPenAndGradCertificateTypeCode(pen, gradCertificateType.getCode())).thenReturn(optional);
        when(this.gradStudentCertificatesTransformer.transformToDTO(this.gradStudentCertificatesRepository.save(studentCertificateEntity))).thenReturn(studentCertificate);

        var result = commonService.saveGradCertificates(studentCertificate);

        assertThat(result).isNotNull();
        assertThat(result.getGradCertificateTypeCode()).isEqualTo(gradCertificateType.getCode());
    }

    @Test
    public void testGetStudentReport() {
        List<GradStudentReportsEntity> reportList = new ArrayList<>();
        GradStudentReportsEntity report = new GradStudentReportsEntity();
        report.setId(UUID.randomUUID());
        report.setGradReportTypeCode("TEST");
        report.setPen("123456789");
        report.setStudentID(UUID.randomUUID());
        report.setReport("TEST Report Body");
        reportList.add(report);

        when(this.gradStudentReportsRepository.existsByReportTypeCode("TEST")).thenReturn(reportList);
        var result = commonService.getStudentReport("TEST");
        assertThat(result).isTrue();
    }

    @Test
    public void testSaveGradReports_thenReturnCreateSuccess() {
        // ID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        String reportTypeCode = "TEST";

        GradStudentReports gradStudentReport = new GradStudentReports();
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        GradStudentReportsEntity gradStudentReportEntity = new GradStudentReportsEntity();
        gradStudentReportEntity.setGradReportTypeCode(reportTypeCode);
        gradStudentReportEntity.setPen(pen);
        gradStudentReportEntity.setStudentID(studentID);
        gradStudentReportEntity.setReport("TEST Report Body");

        Optional<GradStudentReportsEntity> optionalEmpty = Optional.empty();

        when(this.gradStudentReportsTransformer.transformToEntity(gradStudentReport)).thenReturn(gradStudentReportEntity);
        when(this.gradStudentReportsRepository.findByPenAndGradReportTypeCode(pen, reportTypeCode)).thenReturn(optionalEmpty);
        when(this.gradStudentReportsTransformer.transformToDTO(this.gradStudentReportsRepository.save(gradStudentReportEntity))).thenReturn(gradStudentReport);

        var result = commonService.saveGradReports(gradStudentReport);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getGradReportTypeCode()).isEqualTo(gradStudentReport.getGradReportTypeCode());
    }

    @Test
    public void testSaveGradReportsWithExistingOne_thenReturnUpdateSuccess() {
        // ID
        UUID reportID = UUID.randomUUID();
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        String reportTypeCode = "TEST";

        GradStudentReports gradStudentReport = new GradStudentReports();
        gradStudentReport.setId(reportID);
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        GradStudentReportsEntity gradStudentReportEntity = new GradStudentReportsEntity();
        gradStudentReportEntity.setId(reportID);
        gradStudentReportEntity.setGradReportTypeCode(reportTypeCode);
        gradStudentReportEntity.setPen(pen);
        gradStudentReportEntity.setStudentID(studentID);
        gradStudentReportEntity.setReport("TEST Report Body");

        Optional<GradStudentReportsEntity> optional = Optional.of(gradStudentReportEntity);

        when(this.gradStudentReportsTransformer.transformToEntity(gradStudentReport)).thenReturn(gradStudentReportEntity);
        when(this.gradStudentReportsRepository.findByPenAndGradReportTypeCode(pen, reportTypeCode)).thenReturn(optional);
        when(this.gradStudentReportsTransformer.transformToDTO(this.gradStudentReportsRepository.save(gradStudentReportEntity))).thenReturn(gradStudentReport);

        var result = commonService.saveGradReports(gradStudentReport);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID);
        assertThat(result.getGradReportTypeCode()).isEqualTo(gradStudentReport.getGradReportTypeCode());
    }

    @Test
    public void testGetStudentReportByType() {
        // ID
        UUID reportID = UUID.randomUUID();
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        String reportTypeCode = "TEST";

        GradStudentReports gradStudentReport = new GradStudentReports();
        gradStudentReport.setId(reportID);
        gradStudentReport.setGradReportTypeCode(reportTypeCode);
        gradStudentReport.setPen(pen);
        gradStudentReport.setStudentID(studentID);
        gradStudentReport.setReport("TEST Report Body");

        when(gradStudentReportsTransformer.transformToDTO(gradStudentReportsRepository.findByPenAndGradReportTypeCode(pen, reportTypeCode))).thenReturn(gradStudentReport);
        var result = commonService.getStudentReportByType(pen, reportTypeCode);
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).isEqualTo("[inline; filename=student_TEST_report.pdf]");
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    public void testGetStudentCertificateByType() {
        // UUID
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";
        // Certificate Type
        GradCertificateTypes gradCertificateType = new GradCertificateTypes();
        gradCertificateType.setCode("TEST");
        gradCertificateType.setDescription("Test Code Name");

        // Student Certificate Types
        GradStudentCertificates studentCertificate = new GradStudentCertificates();
        studentCertificate.setId(UUID.randomUUID());
        studentCertificate.setPen(pen);
        studentCertificate.setStudentID(studentID);
        studentCertificate.setCertificate("TEST Certificate Body");
        studentCertificate.setGradCertificateTypeCode(gradCertificateType.getCode());

        when(gradStudentCertificatesTransformer.transformToDTO(gradStudentCertificatesRepository.findByPenAndGradCertificateTypeCode(pen, gradCertificateType.getCode()))).thenReturn(studentCertificate);
        var result = commonService.getStudentCertificateByType(pen, gradCertificateType.getCode());
        assertThat(result).isNotNull();
        assertThat(result.getHeaders().get("Content-Disposition").toString()).isEqualTo("[inline; filename=student_TEST_certificate.pdf]");
        assertThat(result.getBody()).isNotNull();
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
    public void testSaveStudentNote_thenReturnCreateSuccess() {
        // ID
        UUID noteID = UUID.randomUUID();
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";

        StudentNote studentNote = new StudentNote();
        studentNote.setStudentID(studentID.toString());
        studentNote.setPen(pen);
        studentNote.setNote("Test Note Body");

        StudentNoteEntity studentNoteEntity = new StudentNoteEntity();
        studentNoteEntity.setId(noteID);
        studentNoteEntity.setStudentID(studentID);
        studentNoteEntity.setPen(pen);
        studentNoteEntity.setNote("Test Note Body");

        Optional<StudentNoteEntity> optional = Optional.of(studentNoteEntity);

        when(this.studentNoteTransformer.transformToEntity(studentNote)).thenReturn(studentNoteEntity);
        when(this.studentNoteTransformer.transformToDTO(this.studentNoteRepository.save(studentNoteEntity))).thenReturn(studentNote);

        var result = commonService.saveStudentNote(studentNote);

        assertThat(result).isNotNull();
        assertThat(result.getStudentID()).isEqualTo(studentID.toString());
        assertThat(result.getNote()).isEqualTo(studentNote.getNote());
    }

    @Test
    public void testSaveStudentNoteWithExistingOne_thenReturnUpdateSuccess() {
        // ID
        UUID noteID = UUID.randomUUID();
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";

        StudentNote studentNote = new StudentNote();
        studentNote.setId(noteID);
        studentNote.setStudentID(studentID.toString());
        studentNote.setPen(pen);
        studentNote.setNote("Test Note Body");

        StudentNoteEntity studentNoteEntity = new StudentNoteEntity();
        studentNoteEntity.setId(noteID);
        studentNoteEntity.setStudentID(studentID);
        studentNoteEntity.setPen(pen);
        studentNoteEntity.setNote("Test Note Body");

        Optional<StudentNoteEntity> optional = Optional.of(studentNoteEntity);

        when(this.studentNoteTransformer.transformToEntity(studentNote)).thenReturn(studentNoteEntity);
        when(this.studentNoteRepository.findById(noteID)).thenReturn(optional);
        when(this.studentNoteTransformer.transformToDTO(this.studentNoteRepository.save(studentNoteEntity))).thenReturn(studentNote);

        var result = commonService.saveStudentNote(studentNote);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(noteID);
        assertThat(result.getStudentID()).isEqualTo(studentID.toString());
        assertThat(result.getNote()).isEqualTo(studentNote.getNote());
    }

    @Test
    public void testDeleteNote() {
        // ID
        UUID noteID = UUID.randomUUID();
        UUID studentID = UUID.randomUUID();
        String pen = "123456789";

        StudentNoteEntity studentNoteEntity = new StudentNoteEntity();
        studentNoteEntity.setId(noteID);
        studentNoteEntity.setStudentID(studentID);
        studentNoteEntity.setPen(pen);
        studentNoteEntity.setNote("Test Note Body");

        Optional<StudentNoteEntity> optional = Optional.of(studentNoteEntity);

        when(this.studentNoteRepository.findById(noteID)).thenReturn(optional);

        var result = commonService.deleteNote(noteID);

        assertThat(result).isEqualTo(1);

    }

    @Test
    public void testDeleteNoteWhenGivenNoteIdDoesNotExist() {
        UUID noteID = UUID.randomUUID();
        Optional<StudentNoteEntity> optional = Optional.empty();

        when(this.studentNoteRepository.findById(noteID)).thenReturn(optional);

        var result = commonService.deleteNote(noteID);

        assertThat(result).isEqualTo(0);

    }
}
