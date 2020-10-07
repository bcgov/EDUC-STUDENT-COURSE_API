package ca.bc.gov.educ.api.studentcourse.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;

import java.util.UUID;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity, UUID> {

    Iterable<StudentCourseEntity> findByPen(String pen);

    Iterable<StudentCourseEntity> findByPen(String pen, Sort sort);

}
