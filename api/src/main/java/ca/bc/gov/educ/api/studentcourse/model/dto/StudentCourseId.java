package ca.bc.gov.educ.api.studentcourse.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class StudentCourseId {

    private String pen;
    private String courseCode;
    private String courseLevel;
    private String sessionDate;

    public StudentCourseId() {
    }
    
    public StudentCourseId(String studNo, String crseCode, String crseLevel, String crseSession) {
        this.pen = studNo;
        this.courseCode = crseCode;
        this.courseLevel = crseLevel;
        this.sessionDate = crseSession;
    }
}
