package ca.bc.gov.educ.api.studentcourse.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity, UUID> {

     @Query("select c from StudentCourseEntity c where c.courseKey.pen=:pen")
    Iterable<StudentCourseEntity> findByPen(String pen);

}
