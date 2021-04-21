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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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
    
    @Value(StudentCourseApiConstants.ENDPOINT_COURSE_BY_CRSE_CODE_ONLY)
    private String getCourseByCrseCodeOnlyURL;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    WebClient webClient;

    private static Logger logger = LoggerFactory.getLogger(StudentCourseService.class);

     /**
     * Get all student courses by PEN populated in Student Course DTO
     * @param accessToken 
     * @param sortForUI 
     *
     * @return Student Course 
     * @throws java.lang.Exception
     */
    public List<StudentCourse> getStudentCourseList(String pen, String accessToken, boolean sortForUI) {
        List<StudentCourse> studentCourses  = new ArrayList<StudentCourse>();
        try {
        	studentCourses = studentCourseTransformer.transformToDTO(studentCourseRepo.findByPen(pen));
        	studentCourses.forEach(sC -> {
        		if(StringUtils.isNotBlank(sC.getRelatedCourse()) || StringUtils.isNotBlank(sC.getRelatedLevel()) || StringUtils.isNotBlank(sC.getAlternateCourseName()) 
        				|| StringUtils.isNotBlank(sC.getBestSchoolPercent() != null ?sC.getBestSchoolPercent().toString():null) || StringUtils.isNotBlank(sC.getBestExamPercent() != null ?sC.getBestExamPercent().toString():null) || StringUtils.isNotBlank(sC.getMetLitNumRequirement())) {
        			sC.setHasRelatedCourse("Y");
        		}else {
        			sC.setHasRelatedCourse("N");
        		}
        		if(sC.getCourseLevel() != null) {
	        		if(sC.getCourseLevel().trim().equalsIgnoreCase("")) {
	        			Course course = webClient.get().uri(String.format(getCourseByCrseCodeOnlyURL,sC.getCourseCode())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
	            		if(course != null) {
	            			sC.setCourseName(course.getCourseName());
		        			sC.setGenericCourseType(course.getGenericCourseType());
		        			sC.setLanguage(course.getLanguage());
		        			sC.setWorkExpFlag(course.getWorkExpFlag());
		        			sC.setCourseDetails(course);
	            		}
	        		}else {
	        			Course course = webClient.get().uri(String.format(getCourseByCrseCodeURL,sC.getCourseCode(),sC.getCourseLevel())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
		        		if(course != null) {
		        			sC.setCourseName(course.getCourseName());
		        			sC.setGenericCourseType(course.getGenericCourseType());
		        			sC.setLanguage(course.getLanguage());
		        			sC.setWorkExpFlag(course.getWorkExpFlag());
		        			sC.setCourseDetails(course);
		        		}
	        		}
        		}
        		if(StringUtils.isNotBlank(sC.getRelatedCourse()) || StringUtils.isNotBlank(sC.getRelatedLevel())) {
	        		if(sC.getRelatedLevel() != null) {
		        		if(sC.getRelatedLevel().trim().equalsIgnoreCase("")) {
		        			Course course = webClient.get().uri(String.format(getCourseByCrseCodeOnlyURL,sC.getRelatedCourse())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
		            		if(course != null) {
		            			sC.setRelatedCourseName(course.getCourseName());
		            		}
		        		}else {
		        			Course course = webClient.get().uri(String.format(getCourseByCrseCodeURL,sC.getRelatedCourse(),sC.getRelatedLevel())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
			        		if(course != null) {
			        			sC.setRelatedCourseName(course.getCourseName());
			        		}
		        		}
	        		}
        		}
        	});
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }
        if(sortForUI) {
        	Collections.sort(studentCourses, Comparator.comparing(StudentCourse::getPen)
                .thenComparing(StudentCourse::getCourseCode)
                .thenComparing(StudentCourse::getCourseLevel)
                .thenComparing(StudentCourse::getSessionDate));
        }else {
        	Collections.sort(studentCourses, Comparator.comparing(StudentCourse::getPen)
                .thenComparing(StudentCourse::getCompletedCoursePercentage)
                .thenComparing(StudentCourse::getCredits)
                .thenComparing(StudentCourse::getCourseLevel));
        }
        return studentCourses;
    }
}
