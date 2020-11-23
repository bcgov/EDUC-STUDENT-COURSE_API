package ca.bc.gov.educ.api.studentcourse;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;

@SpringBootApplication
public class EducStudentCourseApiApplication {

	private static Logger logger = LoggerFactory.getLogger(EducStudentCourseApiApplication.class);

	@Value("${spring.security.user.name}")
	private String uName;
	    
	@Value("${spring.security.user.password}")
	private String pass;
	
	public static void main(String[] args) {
		logger.debug("########Starting API");
		SpringApplication.run(EducStudentCourseApiApplication.class, args);
		logger.debug("########Started API");
	}

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(StudentCourseEntity.class, StudentCourse.class)
				.addMappings(sc -> { sc.skip(StudentCourse::setNotCompleted); });
		modelMapper.typeMap(StudentCourse.class, StudentCourseEntity.class);

		return modelMapper;
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.basicAuthentication(uName, pass).build();
	}
}