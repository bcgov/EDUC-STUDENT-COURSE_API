package ca.bc.gov.educ.api.studentcourse.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class StudentCourse {

	private CourseId courseKey;
	private String courseDescription;
	private String courseLevel;
	private String gradReqMet;
	private Double completedCoursePercentage;
	private String completedCourseLetterGrade;
	private Double interimPercent;
	private String interimLetterGrade;
	private Integer credits;
	private Integer creditsUsedForGrad;	
	private String relatedCourse;
	private String relatedLevel;
	
	@Override
	public String toString() {
		return "StudentCourse [courseKey=" + courseKey + ", courseDescription=" + courseDescription
				+ ", courseLevel=" + courseLevel + ", gradReqMet=" + gradReqMet + ", completedCoursePercentage="
				+ completedCoursePercentage + ", completedCourseLetterGrade=" + completedCourseLetterGrade
				+ ", interimPercent=" + interimPercent + ", interimLetterGrade=" + interimLetterGrade + ", credits="
				+ credits + ", creditsUsedForGrad=" + creditsUsedForGrad + ", relatedCourse=" + relatedCourse
				+ ", relatedLevel=" + relatedLevel + "]";
	}	
}
