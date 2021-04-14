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
	private String alternateCourseName;
	private String gradReqMet;
	private Double completedCoursePercentage;
	private String completedCourseLetterGrade;
	private Double interimPercent;
	private String interimLetterGrade;
	private Double bestSchoolPercent; 
    private Double bestExamPercent;  
    private String metLitNumRequirement; 
	private Integer credits;
	private Integer creditsUsedForGrad;	
	private String relatedCourse;
	private String relatedCourseName;
	private String relatedLevel;
	private String hasRelatedCourse;
	private String genericCourseType;
	private String language;
	private String workExpFlag;
	private boolean isNotCompleted;
	private boolean isFailed;
	private boolean isDuplicate;
	
	public String getPen() {
    	return pen != null ? pen.trim():null;
    }
	
	public String getCourseCode() {
		return courseCode != null ? courseCode.trim(): null;
	}
	public String getCourseName() {
		return courseName != null ? courseName.trim(): null; 
	}	

	public String getCourseLevel() {
		return courseLevel != null ? courseLevel.trim(): null;
	}
	
	public String getAlternateCourseName() {
		return alternateCourseName != null ? alternateCourseName.trim(): null;
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
				+ ", courseLevel=" + courseLevel + ", sessionDate=" + sessionDate + ", alternateCourseName="
				+ alternateCourseName + ", gradReqMet=" + gradReqMet + ", completedCoursePercentage="
				+ completedCoursePercentage + ", completedCourseLetterGrade=" + completedCourseLetterGrade
				+ ", interimPercent=" + interimPercent + ", interimLetterGrade=" + interimLetterGrade + ", credits="
				+ credits + ", creditsUsedForGrad=" + creditsUsedForGrad + ", relatedCourse=" + relatedCourse
				+ ", relatedLevel=" + relatedLevel + ", hasRelatedCourse=" + hasRelatedCourse + ", genericCourseType="+genericCourseType + "]";
	}
	
	
		
}
