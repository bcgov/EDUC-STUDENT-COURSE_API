package ca.bc.gov.educ.api.studentcourse.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class StudentCourse {

	private String pen;
    private String courseCode;
    private String courseName;
    private String courseLevel;
    private String sessionDate;
	private String courseDescription;
	private String gradReqMet;
	private Double completedCoursePercentage;
	private String completedCourseLetterGrade;
	private Double interimPercent;
	private String interimLetterGrade;
	private Integer credits;
	private String creditsUsedForGrad;	
	private String relatedCourse;
	private String relatedLevel;
	private String hasRelatedCourse;
	private String genericCourseType;
	private boolean isNotCompleted;
	private boolean isFailed;
	private boolean isDuplicate;
	
	
	public String getCourseCode() {
		return courseCode != null ? courseCode.trim(): null;
	}
	public String getCourseName() {
		return courseName != null ? courseName.trim(): null; 
	}	

	public String getCourseLevel() {
		return courseLevel != null ? courseLevel.trim(): null;
	}
	
	public String getCourseDescription() {
		return courseDescription != null ? courseDescription.trim(): null;
	}

	public String getCompletedCourseLetterGrade() {
		return completedCourseLetterGrade != null ? completedCourseLetterGrade.trim(): null;
	}

	public String getInterimLetterGrade() {
		return interimLetterGrade != null ? interimLetterGrade.trim(): null;
	}

	public String getRelatedCourse() {
		return relatedCourse != null ? relatedCourse.trim(): null;
	}

	public String getRelatedLevel() {
		return  relatedLevel != null ?  relatedLevel.trim(): null;
	}
	
	public String getGenericCourseType() {
		return genericCourseType != null ?  genericCourseType.trim(): null;
	}

	public Double getCompletedCoursePercentage() {
		if(completedCoursePercentage == null) {
			return Double.valueOf("0");
		}
		return completedCoursePercentage; 
	}
	
	@Override
	public String toString() {
		return "StudentCourse [pen=" + pen + ", courseCode=" + courseCode + ", courseName=" + courseName
				+ ", courseLevel=" + courseLevel + ", sessionDate=" + sessionDate + ", courseDescription="
				+ courseDescription + ", gradReqMet=" + gradReqMet + ", completedCoursePercentage="
				+ completedCoursePercentage + ", completedCourseLetterGrade=" + completedCourseLetterGrade
				+ ", interimPercent=" + interimPercent + ", interimLetterGrade=" + interimLetterGrade + ", credits="
				+ credits + ", creditsUsedForGrad=" + creditsUsedForGrad + ", relatedCourse=" + relatedCourse
				+ ", relatedLevel=" + relatedLevel + ", hasRelatedCourse=" + hasRelatedCourse + ", genericCourseType="+genericCourseType + "]";
	}
	
	
		
}
