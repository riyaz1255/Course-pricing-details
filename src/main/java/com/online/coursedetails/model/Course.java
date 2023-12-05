package com.online.coursedetails.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

	@Id
	private String courseId;
	private String CourseName;
	private float price;
	private PricingStrategy pricingStrategy;
	private String courseType;
}
