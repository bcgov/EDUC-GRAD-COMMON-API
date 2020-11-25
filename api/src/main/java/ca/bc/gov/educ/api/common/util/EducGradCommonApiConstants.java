package ca.bc.gov.educ.api.common.util;

import java.util.Date;

public class EducGradCommonApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_COMMON_API_ROOT_MAPPING = "/api/" + API_VERSION + "/common";
    public static final String GET_ALL_STUDENT_CAREER_MAPPING = "/studentcareerprogram/pen/{pen}";
        
       
    public static final String GET_ALL_STUDENT_UNGRAD_MAPPING = "/studentungradreason/pen/{pen}";
    
    
    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "CommonAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "CommonAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
}
