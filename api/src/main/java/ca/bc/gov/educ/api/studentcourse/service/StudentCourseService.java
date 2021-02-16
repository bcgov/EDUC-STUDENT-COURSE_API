package ca.bc.gov.educ.api.studentcourse.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ca.bc.gov.educ.api.studentcourse.model.dto.Course;
import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.studentcourse.model.transformer.StudentCourseTransformer;
import ca.bc.gov.educ.api.studentcourse.repository.StudentCourseRepository;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiConstants;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiUtils;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepo;

    Iterable<StudentCourseEntity> studentCourseEntities;

    @Autowired
    private StudentCourseTransformer studentCourseTransformer;

    @Value(StudentCourseApiConstants.ENDPOINT_COURSE_BY_CRSE_CODE_URL)
    private String getCourseByCrseCodeURL;
    
    @Value(StudentCourseApiConstants.ENDPOINT_COURSE_BY_CRSE_CODE_ONLY)
    private String getCourseByCrseCodeOnlyURL;
    
    @Autowired
    RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(StudentCourseService.class);

     /**
     * Get all student courses by PEN populated in Student Course DTO
     * @param accessToken 
     *
     * @return Student Course 
     * @throws java.lang.Exception
     */
    public List<StudentCourse> getStudentCourseList(String pen, String accessToken) {
        List<StudentCourse> studentCourses  = new ArrayList<StudentCourse>();
        HttpHeaders httpHeaders = StudentCourseApiUtils.getHeaders(accessToken);
        try {
        	studentCourses = studentCourseTransformer.transformToDTO(studentCourseRepo.findByPen(pen));
        	studentCourses.forEach(sC -> {
        		if(StringUtils.isNotBlank(sC.getRelatedCourse()) || StringUtils.isNotBlank(sC.getRelatedLevel()) || StringUtils.isNotBlank(sC.getCourseDescription())) {
        			sC.setHasRelatedCourse("Y");
        		}else {
        			sC.setHasRelatedCourse("N");
        		}
        		if(sC.getCourseLevel() != null) {
	        		if(sC.getCourseLevel().trim().equalsIgnoreCase("")) {
	        			Course course = restTemplate.exchange(String.format(getCourseByCrseCodeOnlyURL,sC.getCourseCode()), HttpMethod.GET,
	            				new HttpEntity<>(httpHeaders), Course.class).getBody();
	            		if(course != null) {
	            			sC.setCourseName(course.getCourseName());
		        			sC.setGenericCourseType(course.getGenericCourseType());
	            		}
	        		}else {
		        		Course course = restTemplate.exchange(String.format(getCourseByCrseCodeURL,sC.getCourseCode(),sC.getCourseLevel()), HttpMethod.GET,
		        				new HttpEntity<>(httpHeaders), Course.class).getBody();
		        		if(course != null) {
		        			sC.setCourseName(course.getCourseName());
		        			sC.setGenericCourseType(course.getGenericCourseType());
		        		}
	        		}
        		}
        	});
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }
        Collections.sort(studentCourses, Comparator.comparing(StudentCourse::getPen)
                .thenComparing(StudentCourse::getCompletedCoursePercentage)
                .thenComparing(StudentCourse::getCredits)
                .thenComparing(StudentCourse::getCourseLevel)
                .reversed());
        return studentCourses;
    }
}
