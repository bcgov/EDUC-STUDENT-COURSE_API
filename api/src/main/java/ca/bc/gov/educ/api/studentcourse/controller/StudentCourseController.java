package ca.bc.gov.educ.api.studentcourse.controller;

import java.util.List;

import ca.bc.gov.educ.api.studentcourse.model.dto.CourseRequirements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.service.StudentCourseService;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiConstants;

@CrossOrigin
@RestController
@RequestMapping(StudentCourseApiConstants.STUDENT_COURSE_API_ROOT_MAPPING)
public class StudentCourseController {

    private static Logger logger = LoggerFactory.getLogger(StudentCourseController.class);

    @Autowired
    StudentCourseService studentCourseService;

    @GetMapping(StudentCourseApiConstants.GET_STUDENT_COURSE_BY_PEN_MAPPING)
    public List<StudentCourse> getStudentCourseByPEN(@PathVariable String pen) {
        logger.debug("#Get All Course Achievements by PEN: " + pen);
        return studentCourseService.getStudentCourseList(pen);
    }

    @GetMapping("/course-requirement")
    public CourseRequirements getCourseRequirements(
            @RequestParam(value = "courseCode", required = false) String courseCode,
            @RequestParam(value = "courseLevel", required = false) String courseLevel) {

        CourseRequirements courseRequirements = new CourseRequirements();

        if ((courseCode == null || courseCode.isEmpty()) && (courseLevel == null || courseLevel.isEmpty())) {
            logger.debug("**** CourseCode and CourseLevel Not Specified. Retreiving all CourseRequirements.");
            courseRequirements = studentCourseService.getCourseRequirements();
        } else {
            logger.debug("**** Retreiving CourseRequirements for CourseCode= " + courseCode + " and CourseLevel= " + courseLevel + ".");
            courseRequirements = studentCourseService.getCourseRequirements(courseCode, courseLevel);
        }

        return courseRequirements;
    }
}
