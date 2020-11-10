package ca.bc.gov.educ.api.studentcourse.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ca.bc.gov.educ.api.studentcourse.model.dto.Course;
import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.studentcourse.model.transformer.StudentCourseTransformer;
import ca.bc.gov.educ.api.studentcourse.repository.StudentCourseRepository;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiConstants;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepo;

    Iterable<StudentCourseEntity> studentCourseEntities;

    @Autowired
    private StudentCourseTransformer studentCourseTransformer;
    
    @Value(StudentCourseApiConstants.ENDPOINT_COURSE_BY_CRSE_CODE_URL)
    private String getCourseByCrseCodeURL;
    
    @Autowired
    RestTemplate restTemplate;

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
        	studentCourse.forEach(sC -> {
        		if(StringUtils.isNotBlank(sC.getRelatedCourse()) || StringUtils.isNotBlank(sC.getRelatedLevel()) || StringUtils.isNotBlank(sC.getCourseDescription())) {
        			sC.setHasRelatedCourse("Y");
        		}else {
        			sC.setHasRelatedCourse("N");
        		}
        		String url = String.format(getCourseByCrseCodeURL,sC.getCourseCode(),sC.getCourseLevel());
        		Course course = restTemplate.getForObject(url, Course.class);
        		if(course != null) {
        			sC.setCourseName(course.getCourseName());
        		}
        	});
            logger.debug(studentCourse.toString());
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return studentCourse;
    }
}
