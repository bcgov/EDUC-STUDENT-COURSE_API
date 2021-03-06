package ca.bc.gov.educ.api.studentcourse.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
public class StudentCourseApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String STUDENT_COURSE_API_ROOT_MAPPING = "/api/" + API_VERSION + "/studentcourse";
    public static final String GET_STUDENT_COURSE_BY_ID_MAPPING = "/{studentCourseId}";
    public static final String GET_STUDENT_COURSE_BY_PEN_MAPPING = "/pen/{pen}";

    //Attribute Constants
    public static final String STUDENT_COURSE_ID_ATTRIBUTE = "studentCourseID";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "StudentCourseAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "StudentCourseAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
    
    //Endpoints
    @Value("${endpoint.course-api.course_by_crse_code.url}")
    private String courseByCourseCodeUrl;

    @Value("${endpoint.course-api.course_by_crse_code_only.url}")
    private String courseByCourseCodeOnlyUrl;
}
