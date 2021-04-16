package ca.bc.gov.educ.api.studentcourse.model.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Course {

	private String courseCode;
    private String courseLevel;
    private String courseName;
    private String language;    
    private Date startDate;
    private Date endDate;
    private String workExpFlag;    
    private String genericCourseType;
    
    public String getCourseName() {
		return courseName != null ? courseName.trim(): null; 
	}
    
    public String getGenericCourseType() {
		return genericCourseType != null ?  genericCourseType.trim(): null;
	}
	@Override
	public String toString() {
		return "Course [courseCode=" + courseCode + ", courseLevel=" + courseLevel + ", courseName=" + courseName
				+ ", language=" + language + ", startDate=" + startDate + ", endDate=" + endDate + ", workExpFlag="
				+ workExpFlag + ", genericCourseType=" + genericCourseType + "]";
	}		
}
