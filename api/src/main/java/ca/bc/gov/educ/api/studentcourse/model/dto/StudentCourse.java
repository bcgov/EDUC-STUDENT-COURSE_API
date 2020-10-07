package ca.bc.gov.educ.api.studentcourse.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class StudentCourse {

	private UUID studentCourseId;
	private String pen;
	private String sessionDate;
	private String courseCode;
	private String courseType;
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
		return "StudentCourse [studentCourseId=" + studentCourseId + ", pen=" + pen + ", sessionDate=" + sessionDate
				+ ", courseCode=" + courseCode + ", courseType=" + courseType + ", courseDescription="
				+ courseDescription + ", courseLevel=" + courseLevel + ", gradReqMet=" + gradReqMet
				+ ", completedCoursePercentage=" + completedCoursePercentage + ", completedCourseLetterGrade="
				+ completedCourseLetterGrade + ", interimPercent=" + interimPercent + ", interimLetterGrade="
				+ interimLetterGrade + ", credits=" + credits + ", creditsUsedForGrad=" + creditsUsedForGrad
				+ ", relatedCourse=" + relatedCourse + ", relatedLevel=" + relatedLevel + "]";
	}
}
