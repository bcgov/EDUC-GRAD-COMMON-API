package ca.bc.gov.educ.api.common.util;

import java.util.Date;

public class EducGradCommonApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_COMMON_API_ROOT_MAPPING = "/api/" + API_VERSION + "/common";
    public static final String GET_ALL_STUDENT_CAREER_MAPPING = "/studentcareerprogram/pen/{pen}";
    public static final String STUDENT_REPORT = "/studentreport";
    public static final String STUDENT_CERTIFICATE = "/studentcertificate";
    public static final String STUDENT_CERTIFICATE_BY_PEN = "/studentcertificate/{pen}";
    public static final String GET_ALL_ALGORITHM_RULES_MAPPING="/algorithm-rules";
    
    public static final String GET_ALGORITHM_RULES_MAIN_PROGRAM = "/algorithm-rules/main/{programCode}";
    public static final String GET_ALGORITHM_RULES_SPECIAL_PROGRAM = "/algorithm-rules/special/{programCode}/{specialProgramCode}";
        
       
    public static final String GET_ALL_STUDENT_UNGRAD_MAPPING = "/studentungradreason/pen/{pen}";
    public static final String GET_STUDENT_UNGRAD_BY_REASON_CODE_MAPPING = "/ungrad/{reasonCode}";
    public static final String GET_STUDENT_CAREER_PROGRAM_BY_CAREER_PROGRAM_CODE_MAPPING = "/career/{cpCode}";
    public static final String GET_STUDENT_CERTIFICATE_BY_CERTIFICATE_CODE_MAPPING = "/certificate/{certificateTypeCode}";
    public static final String GET_STUDENT_REPORT_BY_REPORT_CODE_MAPPING = "/report/{reportTypeCode}";
    
    
    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "CommonAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "CommonAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
	public static final String ENDPOINT_UNGRAD_REASON_BY_CODE_URL = "${endpoint.code-api.ungrad_reason.ungrad_reasons-by-ungrad-code.url}";
	public static final String ENDPOINT_CAREER_PROGRAM_BY_CODE_URL = "${endpoint.code-api.career_program.career_program-by-career-code.url}";
	public static final String ENDPOINT_CERTIFICATE_BY_CODE_URL = "${endpoint.code-api.certificate_type.certificate-by-code.url}";
	
}
