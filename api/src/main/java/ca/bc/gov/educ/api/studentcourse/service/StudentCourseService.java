package ca.bc.gov.educ.api.studentcourse.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.studentcourse.model.dto.Course;
import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.transformer.StudentCourseTransformer;
import ca.bc.gov.educ.api.studentcourse.repository.StudentCourseRepository;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiConstants;

@Service
public class StudentCourseService {
    private static final Logger logger = LoggerFactory.getLogger(StudentCourseService.class);

    private final StudentCourseRepository studentCourseRepo;
    private final StudentCourseTransformer studentCourseTransformer;
    private final StudentCourseApiConstants constants;
    private final WebClient webClient;

    public StudentCourseService(StudentCourseRepository studentCourseRepo, StudentCourseTransformer studentCourseTransformer, StudentCourseApiConstants constants, WebClient webClient) {
        this.studentCourseRepo = studentCourseRepo;
        this.studentCourseTransformer = studentCourseTransformer;
        this.constants = constants;
        this.webClient = webClient;
    }

    /**
     * Get all student courses by PEN populated in Student Course DTO
     *
     * @param pen           PEN #
     * @param accessToken   Access Token
     * @param sortForUI     Sort For UI
     * @return Student Course
     */
    public List<StudentCourse> getStudentCourseList(String pen, String accessToken, boolean sortForUI) {
        List<StudentCourse> studentCourses  = new ArrayList<>();
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
	        			getCourseDetails(sC.getCourseCode(),accessToken,sC);	            		
	        		}else {
	        			getCourseDetailsByLevel(sC.getCourseCode(), sC.getCourseLevel(), accessToken,sC);
	        		}
        		}
        		if((StringUtils.isNotBlank(sC.getRelatedCourse()) || StringUtils.isNotBlank(sC.getRelatedLevel())) && sC.getRelatedLevel() != null) {
        			checkForMoreOptions(sC,accessToken);
        		}
        	});
        } catch (Exception e) {
            logger.debug(String.format("Exception: %s",e));
        }
        getDataSorted(studentCourses,sortForUI);
        return studentCourses;
    }
    
    private void checkForMoreOptions(StudentCourse sC, String accessToken) {
		if(sC.getRelatedLevel().trim().equalsIgnoreCase("")) {
			Course course = webClient.get().uri(String.format(constants.getCourseByCourseCodeOnlyUrl(),sC.getRelatedCourse())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
			if(course != null) {
				sC.setRelatedCourseName(course.getCourseName());
			}
		} else {
			Course course = webClient.get().uri(String.format(constants.getCourseByCourseCodeUrl(),sC.getRelatedCourse(),sC.getRelatedLevel())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
			if(course != null) {
				sC.setRelatedCourseName(course.getCourseName());
			}
		}
	 }
    
    private void getDataSorted(List<StudentCourse> studentCourses, boolean sortForUI) {
    	if(sortForUI) {
        	Collections.sort(studentCourses, Comparator.comparing(StudentCourse::getPen)
                .thenComparing(StudentCourse::getCourseCode)
                .thenComparing(StudentCourse::getCourseLevel)
                .thenComparing(StudentCourse::getSessionDate));
        }else {
        	Collections.sort(studentCourses, Comparator.comparing(StudentCourse::getPen)
                .thenComparing(StudentCourse::getCompletedCoursePercentage).reversed()
                .thenComparing(StudentCourse::getCredits).reversed()
                .thenComparing(StudentCourse::getCourseLevel).reversed());        
        }
    }

	private void getCourseDetails(String courseCode,String accessToken, StudentCourse sC) {
    	Course course = webClient.get().uri(String.format(constants.getCourseByCourseCodeOnlyUrl(),courseCode)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
    	if(course != null) {
			sC.setCourseName(course.getCourseName());
			sC.setGenericCourseType(course.getGenericCourseType());
			sC.setLanguage(course.getLanguage());
			sC.setWorkExpFlag(course.getWorkExpFlag());
			sC.setCourseDetails(course);
		  }
    }
    private void getCourseDetailsByLevel(String courseCode,String courseLevel,String accessToken, StudentCourse sC) {
    	Course course = webClient.get().uri(String.format(constants.getCourseByCourseCodeUrl(),courseCode,courseLevel)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(Course.class).block();
    	if(course != null) {
			sC.setCourseName(course.getCourseName());
			sC.setGenericCourseType(course.getGenericCourseType());
			sC.setLanguage(course.getLanguage());
			sC.setWorkExpFlag(course.getWorkExpFlag());
			sC.setCourseDetails(course);
		  }
    }
}
