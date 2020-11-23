package ca.bc.gov.educ.api.studentcourse.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "COURSE_REQUIREMENT")
public class CourseRequirementEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "ID", unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID courseRequirementId;

    @Column(name = "CRSE_CODE", nullable = true)
    private String courseCode;

    @Column(name = "CRSE_LVL", nullable = true)
    private String courseLevel;

    @Column(name = "RULE_CODE", nullable = true)
    private String ruleCode;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_timestamp", nullable = false)
    private java.util.Date createdTimestamp;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "updated_timestamp", nullable = false)
    private Date updatedTimestamp;
}
