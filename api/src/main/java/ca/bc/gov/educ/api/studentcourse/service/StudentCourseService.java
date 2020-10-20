package ca.bc.gov.educ.api.studentcourse.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.studentcourse.model.transformer.StudentCourseTransformer;
import ca.bc.gov.educ.api.studentcourse.repository.StudentCourseRepository;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepo;

    Iterable<StudentCourseEntity> studentCourseEntities;

    @Autowired
    private StudentCourseTransformer studentCourseTransformer;

    private static Logger logger = LoggerFactory.getLogger(StudentCourseService.class);

     /**
     * Get all student courses by PEN populated in Student Course DTO
     *
     * @return Student Course 
     * @throws java.lang.Exception
     */
    public List<StudentCourse> getStudentCourseList(String pen) {
        List<StudentCourse> studentCourse  = new ArrayList<StudentCourse>();

        try {
        	studentCourse = studentCourseTransformer.transformToDTO(studentCourseRepo.findByPen(pen));
            logger.debug(studentCourse.toString());
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return studentCourse;
    }
}
