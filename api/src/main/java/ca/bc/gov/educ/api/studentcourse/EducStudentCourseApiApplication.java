package ca.bc.gov.educ.api.studentcourse;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;

@SpringBootApplication
public class EducStudentCourseApiApplication {

	private static Logger logger = LoggerFactory.getLogger(EducStudentCourseApiApplication.class);

	public static void main(String[] args) {
		logger.debug("########Starting API");
		SpringApplication.run(EducStudentCourseApiApplication.class, args);
		logger.debug("########Started API");
	}

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(StudentCourseEntity.class, StudentCourse.class);
		modelMapper.typeMap(StudentCourse.class, StudentCourseEntity.class);
		return modelMapper;
	}
}