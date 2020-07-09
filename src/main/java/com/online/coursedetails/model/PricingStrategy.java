package com.online.coursedetails.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingStrategy {

    private String free;
    private String oneTimePayment;
    private String subscription;
}
