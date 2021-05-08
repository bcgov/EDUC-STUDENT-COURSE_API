package ca.bc.gov.educ.api.studentcourse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.service.StudentCourseService;
import ca.bc.gov.educ.api.studentcourse.util.GradValidation;
import ca.bc.gov.educ.api.studentcourse.util.ResponseHelper;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping(StudentCourseApiConstants.STUDENT_COURSE_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Student Course Data.", description = "This API is for Reading Student Course data.", version = "1"),
        security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_COURSE_DATA"})})
public class StudentCourseController {

    private static Logger logger = LoggerFactory.getLogger(StudentCourseController.class);

    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    GradValidation validation;

    @Autowired
    ResponseHelper response;

    @GetMapping(StudentCourseApiConstants.GET_STUDENT_COURSE_BY_PEN_MAPPING)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_STUDENT_COURSE_DATA')")
    @Operation(summary = "Find All Student Courses by PEN", description = "Get All Student Courses by PEN", tags = {"Student Courses"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<StudentCourse>> getStudentCourseByPEN(
            @PathVariable String pen, @RequestParam(value = "sortForUI", required = false, defaultValue = "false") boolean sortForUI) {
        logger.debug("#Get All Student Course by PEN: *****" + pen.substring(5));
        validation.requiredField(pen, "Pen");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String accessToken = auth.getTokenValue();
            List<StudentCourse> studentCourseList = studentCourseService.getStudentCourseList(pen, accessToken, sortForUI);
            if (studentCourseList.isEmpty()) {
                return response.NO_CONTENT();
            }
            return response.GET(studentCourseList);
        }
    }
}
