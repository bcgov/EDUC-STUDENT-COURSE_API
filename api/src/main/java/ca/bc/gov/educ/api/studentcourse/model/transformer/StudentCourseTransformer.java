package ca.bc.gov.educ.api.studentcourse.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiUtils;

@Component
public class StudentCourseTransformer {

    @Autowired
    ModelMapper modelMapper;

    public StudentCourse transformToDTO (StudentCourseEntity studentCourseEntity) {
        StudentCourse studentCourse = modelMapper.map(studentCourseEntity, StudentCourse.class);
        return studentCourse;
    }

    public StudentCourse transformToDTO ( Optional<StudentCourseEntity> courseAchievementEntity ) {
        StudentCourseEntity cae = new StudentCourseEntity();

        if (courseAchievementEntity.isPresent())
            cae = courseAchievementEntity.get();

        StudentCourse courseAchievement = modelMapper.map(cae, StudentCourse.class);
        return courseAchievement;
    }

    @SuppressWarnings("deprecation")
	public List<StudentCourse> transformToDTO (Iterable<StudentCourseEntity> courseAchievementEntities ) {

        List<StudentCourse> courseAchievementList = new ArrayList<StudentCourse>();

        for (StudentCourseEntity courseAchievementEntity : courseAchievementEntities) {
            StudentCourse courseAchievement = new StudentCourse();
            courseAchievement = modelMapper.map(courseAchievementEntity, StudentCourse.class);
           
            courseAchievement.setPen(courseAchievementEntity.getCourseKey().getPen());
            courseAchievement.setCourseCode(courseAchievementEntity.getCourseKey().getCourseCode());
            courseAchievement.setCourseLevel(courseAchievementEntity.getCourseKey().getCourseLevel());
            courseAchievement.setSessionDate(StudentCourseApiUtils.parseTraxDate(courseAchievementEntity.getCourseKey().getSessionDate()).toLocaleString());
            
            courseAchievementList.add(courseAchievement);
        }

        return courseAchievementList;
    }

    public StudentCourseEntity transformToEntity(StudentCourse studentCourse) {
        StudentCourseEntity courseAchievementEntity = modelMapper.map(studentCourse, StudentCourseEntity.class);
        return courseAchievementEntity;
    }
}
