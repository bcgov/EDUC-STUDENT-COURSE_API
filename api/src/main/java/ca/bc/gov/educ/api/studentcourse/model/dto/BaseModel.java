package ca.bc.gov.educ.api.studentcourse.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BaseModel {
	private String createdBy;	
	private Date createdTimestamp;	
	private String updatedBy;	
	private Date updatedTimestamp;
}
