package com.online.coursedetails.model.response;

import com.online.coursedetails.model.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CoursePrice {

    private String courseId;
    private String courseName;
    private String citizenType;
    private Price price;
    private String pricingStrategy;
}
