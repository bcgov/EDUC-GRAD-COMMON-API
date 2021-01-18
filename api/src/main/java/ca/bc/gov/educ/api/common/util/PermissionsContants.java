package ca.bc.gov.educ.api.common.util;

public interface PermissionsContants {
	String _PREFIX = "#oauth2.hasAnyScope('";
	String _SUFFIX = "')";

	String READ_GRAD_STUDENT_CAREER_DATA = _PREFIX + "READ_GRAD_STUDENT_CAREER_DATA" + _SUFFIX;
	String READ_GRAD_STUDENT_UNGRAD_REASONS_DATA = _PREFIX + "READ_GRAD_STUDENT_UNGRAD_REASONS_DATA" + _SUFFIX;
	String UPDATE_GRADUATION_STUDENT_REPORTS = _PREFIX + "UPDATE_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
	String READ_GRADUATION_STUDENT_REPORTS = _PREFIX + "READ_GRAD_STUDENT_REPORT_DATA" + _SUFFIX;
}
