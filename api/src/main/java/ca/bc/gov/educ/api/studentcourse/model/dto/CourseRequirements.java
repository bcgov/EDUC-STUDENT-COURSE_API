package ca.bc.gov.educ.api.studentcourse.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CourseRequirements {
    List<CourseRequirement> courseRequirements;
}
