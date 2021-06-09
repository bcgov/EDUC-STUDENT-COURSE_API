package ca.bc.gov.educ.api.studentcourse.service;

import ca.bc.gov.educ.api.studentcourse.model.dto.Course;
import ca.bc.gov.educ.api.studentcourse.model.dto.StudentCourse;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseEntity;
import ca.bc.gov.educ.api.studentcourse.model.entity.StudentCourseId;
import ca.bc.gov.educ.api.studentcourse.repository.StudentCourseRepository;
import ca.bc.gov.educ.api.studentcourse.util.StudentCourseApiConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StudentCourseServiceTest {

    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    private StudentCourseApiConstants constants;

    @MockBean
    private StudentCourseRepository studentCourseRepo;

    @MockBean
    WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    @Test
    public void testGetStudentCourseList_whenGivenCourseCodeAndLevelWithRelatedCourse_thenReturnSuccess() {
        // ID
        StudentCourseId studentCourseId = new StudentCourseId();
        studentCourseId.setPen("123456789");
        studentCourseId.setCourseCode("main");
        studentCourseId.setCourseLevel("12");
        studentCourseId.setSessionDate("2020-05");

        StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
        studentCourseEntity.setCourseKey(studentCourseId);
        studentCourseEntity.setRelatedCourse("rest");
        studentCourseEntity.setRelatedLevel("12");

        Course course = new Course();
        course.setCourseCode("main");
        course.setCourseLevel("12");
        course.setCourseName("test main course");
        course.setGenericCourseType("gct");
        course.setLanguage("en");
        course.setWorkExpFlag("Y");

        when(studentCourseRepo.findByPen(studentCourseId.getPen())).thenReturn(Arrays.asList(studentCourseEntity));

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getCourseByCourseCodeUrl(), course.getCourseCode(), course.getCourseLevel()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(Course.class)).thenReturn(Mono.just(course));

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getCourseByCourseCodeUrl(), studentCourseEntity.getRelatedCourse(), studentCourseEntity.getRelatedLevel()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(Course.class)).thenReturn(Mono.just(course));

        var result = studentCourseService.getStudentCourseList(studentCourseId.getPen(), "accessToken", false);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        StudentCourse responseStudentCourse = result.get(0);
        assertThat(responseStudentCourse.getCourseCode()).isEqualTo(course.getCourseCode());
        assertThat(responseStudentCourse.getGenericCourseType()).isEqualTo(course.getGenericCourseType());
    }

    @Test
    public void testGetStudentCourseList_whenGivenCourseCodeWithoutRelatedCourse_thenReturnSuccess() {
        // ID
        StudentCourseId studentCourseId = new StudentCourseId();
        studentCourseId.setPen("123456789");
        studentCourseId.setCourseCode("main");
        studentCourseId.setCourseLevel("");
        studentCourseId.setSessionDate("2020-05");

        StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
        studentCourseEntity.setCourseKey(studentCourseId);

        Course course = new Course();
        course.setCourseCode("main");
        course.setCourseLevel("12");
        course.setCourseName("test main course");
        course.setGenericCourseType("gct");
        course.setLanguage("en");
        course.setWorkExpFlag("Y");

        when(studentCourseRepo.findByPen(studentCourseId.getPen())).thenReturn(Arrays.asList(studentCourseEntity));

        when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
        when(this.requestHeadersUriMock.uri(String.format(constants.getCourseByCourseCodeOnlyUrl(), course.getCourseCode()))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(Course.class)).thenReturn(Mono.just(course));

        var result = studentCourseService.getStudentCourseList(studentCourseId.getPen(), "accessToken", false);
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        StudentCourse responseStudentCourse = result.get(0);
        assertThat(responseStudentCourse.getCourseCode()).isEqualTo(course.getCourseCode());
        assertThat(responseStudentCourse.getGenericCourseType()).isEqualTo(course.getGenericCourseType());
    }

}
