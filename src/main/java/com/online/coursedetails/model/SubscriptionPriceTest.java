package com.online.coursedetails.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionPriceTest {

    private float oneYearPrice;
    private float twoYearPrice;
    private float threeYearPrice;
}
