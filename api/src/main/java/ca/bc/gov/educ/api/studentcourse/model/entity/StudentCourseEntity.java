package ca.bc.gov.educ.api.studentcourse.model.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "TRAX_STUDENT_COURSES")
public class StudentCourseEntity {
   
	@EmbeddedId
    private StudentCourseId courseKey;
    
    @Column(name = "FINAL_PCT", nullable = true)
    private Double completedCoursePercentage;

    @Column(name = "FINAL_LG", nullable = true)
    private String completedCourseLetterGrade;
    
    @Column(name = "PRED_PCT", nullable = true)
    private Double interimPercent;

    @Column(name = "PRED_LG", nullable = true)
    private String interimLetterGrade;

    @Column(name = "NUM_CREDITS", nullable = true)
    private Integer credits;
    
    @Column(name = "EQUIV_OR_CHALLENGE", nullable = true)
    private String courseType;
    
    @Column(name = "RELATED_CRSE", nullable = true)
    private String relatedCourse; 
    
    @Column(name = "RELATED_LEVEL", nullable = true)
    private String relatedLevel;  
    
    @Column(name = "CRSE_DESCRIPTION", nullable = true)
    private String courseDescription; 
}
