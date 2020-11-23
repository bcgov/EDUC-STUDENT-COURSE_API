package ca.bc.gov.educ.api.studentcourse.repository;

import ca.bc.gov.educ.api.studentcourse.model.entity.CourseRequirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRequirementRepository extends JpaRepository<CourseRequirementEntity, UUID> {

    List<CourseRequirementEntity> findAll();
    
    Optional<CourseRequirementEntity> findByCourseRequirementId(UUID courseRequirementId);

    List<CourseRequirementEntity> findByCourseCodeAndCourseLevel(String courseCode, String courseLevel);

}
