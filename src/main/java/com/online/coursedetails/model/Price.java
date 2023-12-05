package com.online.coursedetails.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Price {

    private float basePrice;
    private float gst;
    private float otherTax;
    private float shippingCharges;
    private float subscriptionPrice;
    private float totalCost;
    private float taxprice;

}
