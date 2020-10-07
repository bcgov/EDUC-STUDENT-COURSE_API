package ca.bc.gov.educ.api.studentcourse.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "STUD_CRSE")
public class StudentCourseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "stud_course_id", unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID studentCourseId;

    @Column(name = "STUD_NO", nullable = true)
    private String pen;

    @Column(name = "CRSE_SESSION", nullable = true)
    private Date sessionDate;

    @Column(name = "CRSE_CODE", nullable = true)
    private String courseCode;
    
    @Column(name = "CRSE_LEVEL", nullable = true)
    private String courseLevel;
    
    @Column(name = "STUDY_TYPE", nullable = true)
    private String gradReqMet;    
    
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
    
    @Column(name = "USED_FOR_GRAD", nullable = true)
    private Integer creditsUsedForGrad;    

    @Column(name = "CRSE_TYPE", nullable = true)
    private String courseType;
    
    @Column(name = "RELATED_CRSE", nullable = true)
    private String relatedCourse; 
    
    @Column(name = "RELATED_LEVEL", nullable = true)
    private String relatedLevel; 
    
    @Column(name = "COURSE_DESC", nullable = true)
    private String courseDescription;     

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_timestamp", nullable = false)
    private Date createdTimestamp;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "updated_timestamp", nullable = false)
    private Date updatedTimestamp;    
}
