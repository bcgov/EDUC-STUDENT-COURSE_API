package ca.bc.gov.educ.api.studentcourse.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Date;
import java.util.UUID;

@Data
@Component
public class CourseRequirement {

    private UUID courseRequirementId;
    private String courseCode;
    private String courseLevel;
    private String ruleCode;
    private String createdBy;
    private java.util.Date createdTimestamp;
    private String updatedBy;
    private Date updatedTimestamp;

    @Override
    public String toString() {
        return "\nCourseRequirement {" +
                "courseRequirementId='" + courseRequirementId + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseLevel='" + courseLevel + '\'' +
                ", ruleCode=" + ruleCode +
                "}";
    }
}
