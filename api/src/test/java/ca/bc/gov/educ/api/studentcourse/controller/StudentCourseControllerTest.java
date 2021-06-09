package ca.bc.gov.educ.api.studentcourse.controller;

import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.service.StudentCourseService;
import ca.bc.gov.educ.api.studentcourse.util.GradValidation;
import ca.bc.gov.educ.api.studentcourse.util.ResponseHelper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class StudentCourseControllerTest {

    @Mock
    private StudentCourseService studentCourseService;

    @InjectMocks
    StudentCourseController studentCourseController;

    @Mock
    ResponseHelper responseHelper;

    @Mock
    GradValidation validation;

    @Test
    public void testGetStudentCoursesByPEN() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setPen("123456789");
        studentCourse.setCourseCode("main");
        studentCourse.setCourseLevel("12");
        studentCourse.setCourseName("main test course");
        studentCourse.setLanguage("en");

        Authentication authentication = Mockito.mock(Authentication.class);
        OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(studentCourseService.getStudentCourseList(studentCourse.getPen(), null, true)).thenReturn(Arrays.asList(studentCourse));
        var result = studentCourseController.getStudentCourseByPEN(studentCourse.getPen(), true);
        Mockito.verify(studentCourseService).getStudentCourseList(studentCourse.getPen(), null, true);
    }

    @Test
    public void testValidationError() {
        Mockito.when(validation.hasErrors()).thenReturn(true);
        var result = studentCourseController.getStudentCourseByPEN("", true);
        Mockito.verify(validation).hasErrors();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
