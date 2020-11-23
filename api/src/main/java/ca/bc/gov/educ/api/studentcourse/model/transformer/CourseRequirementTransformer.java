package ca.bc.gov.educ.api.studentcourse.model.transformer;

import ca.bc.gov.educ.api.studentcourse.model.dto.CourseRequirement;
import ca.bc.gov.educ.api.studentcourse.model.entity.CourseRequirementEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CourseRequirementTransformer {

    @Autowired
    ModelMapper modelMapper;

    public CourseRequirement transformToDTO (CourseRequirementEntity courseRequirementEntity) {
        return modelMapper.map(courseRequirementEntity, CourseRequirement.class);
    }

    public CourseRequirement transformToDTO (Optional<CourseRequirementEntity> courseRequirementEntity) {
        CourseRequirementEntity cre = new CourseRequirementEntity();

        if (courseRequirementEntity.isPresent())
            cre = courseRequirementEntity.get();

        return modelMapper.map(cre, CourseRequirement.class);
    }

    public List<CourseRequirement> transformToDTO (Iterable<CourseRequirementEntity> courseRequirementEntities) {
        List<CourseRequirement> courseRequirements = new ArrayList<CourseRequirement>();

        for (CourseRequirementEntity courseRequirementEntity : courseRequirementEntities) {
            CourseRequirement courseRequirement = new CourseRequirement();
            courseRequirement = modelMapper.map(courseRequirementEntity, CourseRequirement.class);

            courseRequirements.add(courseRequirement);
        }
        return courseRequirements;
    }

    public CourseRequirementEntity transformToEntity(CourseRequirement courseRequirement) {
        return modelMapper.map(courseRequirement, CourseRequirementEntity.class);
    }
}