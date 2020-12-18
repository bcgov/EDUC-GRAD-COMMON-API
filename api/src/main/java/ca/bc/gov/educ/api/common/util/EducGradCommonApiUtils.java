package ca.bc.gov.educ.api.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpHeaders;

public class EducGradCommonApiUtils {

    public static String formatDate (Date date) {
        if (date == null)
            return null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EducGradCommonApiConstants.DEFAULT_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String formatDate (Date date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    public static Date parseDate (String dateString) {
        if (dateString == null || "".compareTo(dateString) == 0)
            return null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EducGradCommonApiConstants.DEFAULT_DATE_FORMAT);
        Date date = new Date();

        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseDate (String dateString, String dateFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Date date = new Date();

        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
    
    public static Date parseTraxDate (String sessionDate) {
        if (sessionDate == null)
            return null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EducGradCommonApiConstants.TRAX_DATE_FORMAT);
        Date date = new Date();

        try {
            date = simpleDateFormat.parse(sessionDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
    
    public static HttpHeaders getHeaders (String accessToken)
    {
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.setBearerAuth(accessToken);
        return httpHeaders;
    }
}
