package ca.bc.gov.educ.api.studentcourse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.service.StudentCourseService;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiConstants;

@CrossOrigin
@RestController
@RequestMapping(StudentCourseApiConstants.COURSE_ACHIEVEMENT_API_ROOT_MAPPING)
public class StudentCourseController {

    private static Logger logger = LoggerFactory.getLogger(StudentCourseController.class);

    @Autowired
    StudentCourseService studentCourseService;

    @GetMapping(StudentCourseApiConstants.GET_COURSE_ACHIEVEMENT_BY_PEN_MAPPING)
    public List<StudentCourse> getStudentCourseByPEN(@PathVariable String pen) {
        logger.debug("#Get All Course Achievements by PEN: " + pen);
        return studentCourseService.getStudentCourseList(pen);
    }
}
