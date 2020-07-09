package com.online.coursedetails.transformation;

import com.online.coursedetails.model.Price;
import com.online.coursedetails.model.Course;
import com.online.coursedetails.model.response.CoursePrice;
import com.online.coursedetails.model.PricingStrategy;
import com.online.coursedetails.utils.CountryCode;
import com.online.coursedetails.utils.CountryPrice;
import com.online.coursedetails.utils.PriceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResponseTransformer {

    CoursePrice coursePrice;

    public CoursePrice getCoursePrice(Course response, String citizenType) {

        coursePrice = pricingStrategy(response, citizenType);
        ruppeeConversion(coursePrice);
        return coursePrice;
    }

    /*
       This Method calculates the price and other component charges based on PricingStrategy
       PricingStartegy possible values Free=0 , OneTimePayment=1 , Subscription=2

     */

    private static CoursePrice pricingStrategy(Course response, String citizenType) {

        CoursePrice coursePrice = null;
        if (null != response.getPricingStrategy()) {
            PricingStrategy priceType = response.getPricingStrategy();
            if (StringUtils.isNotBlank(priceType.getFree()) &&
                    StringUtils.equalsIgnoreCase(priceType.getFree(), PriceType.FREE.getValue())) {
                coursePrice = calculateFreePrice(response, citizenType);
            }

            if (StringUtils.isNotBlank(priceType.getOneTimePayment()) &&
                    StringUtils.equalsIgnoreCase(priceType.getOneTimePayment(), PriceType.ONE_TIME_PAYMENT.getValue())) {
                coursePrice = calculateOneTimePayment(response, citizenType);
            }
            if (StringUtils.isNotBlank(priceType.getSubscription()) &&
                    StringUtils.equalsIgnoreCase(priceType.getSubscription(), PriceType.SUBSCRIPTION.getValue())) {
                coursePrice = calculateSubscription(response, citizenType);
            }
        }
        return coursePrice;
    }

    private static CoursePrice calculateFreePrice(Course response, String citizenType) {
        Price price = calculateComponentPrice(response.getPrice(), PriceType.FREE.getValue());
        return buildCoursePrice(response, response.getPricingStrategy().getFree(), price, citizenType);
    }

    private static CoursePrice calculateOneTimePayment(Course response, String citizenType) {

        Price price = calculateComponentPrice(response.getPrice(), PriceType.ONE_TIME_PAYMENT.getValue());
        return buildCoursePrice(response, response.getPricingStrategy().getOneTimePayment(), price, citizenType);

    }

    private static CoursePrice calculateSubscription(Course response, String citizenType) {
        Price price = calculateComponentPrice(response.getPrice(), PriceType.SUBSCRIPTION.getValue());
        return buildCoursePrice(response, response.getPricingStrategy().getSubscription(), price, citizenType);
    }

    private static Price calculateComponentPrice(float price, String priceType) {
        float subscriptionPrice = 0;
        float gst = calculateGst(price);
        float shippingCharges = shippingCharges(price);
        float otherTaxes = otherTaxes(price);
        if (StringUtils.equalsIgnoreCase(priceType, PriceType.SUBSCRIPTION.getValue())) {
            subscriptionPrice = subscriptionPrice(price);
        }

        return Price.builder()
                .basePrice(price)
                .gst(gst)
                .shippingCharges(shippingCharges)
                .otherTax(otherTaxes)
                .subscriptionPrice(subscriptionPrice)
                .totalCost(price + gst + shippingCharges + otherTaxes)
                .build();
    }

    private static CoursePrice buildCoursePrice(Course response, String priceType, Price price, String citizenType) {

        return CoursePrice.builder()
                .courseId(response.getCourseId())
                .courseName(response.getCourseName())
                .citizenType(identifyCitizenType(CountryCode.getCountryCodes(), citizenType))
                .pricingStrategy(PriceType.getName(priceType))
                .price(price)
                .build();
    }

    /*
         This method converts the calculated coursePrice cost to Respective country currency
         if citizenType is USA it converts to dollars, if germany it converts to Euros

     */

    private static CoursePrice ruppeeConversion(CoursePrice coursePrice) {

        String citizenType = coursePrice.getCitizenType();
        Price price = coursePrice.getPrice();

        if (StringUtils.equalsIgnoreCase(citizenType, CountryCode.USA.getValue()))
            price = convertRuppeeToDollar(price, coursePrice.getCitizenType(), coursePrice.getPricingStrategy());

        if (StringUtils.equalsIgnoreCase(citizenType, CountryCode.GERMANY.getValue()))
            price = convertRuppeeToEuro(price, coursePrice.getCitizenType(), coursePrice.getPricingStrategy());

        if (StringUtils.equalsIgnoreCase(citizenType, CountryCode.INDIA.getValue()))
            price = convertRuppee(price, coursePrice.getCitizenType(), coursePrice.getPricingStrategy());


        coursePrice.setPrice(price);

        return coursePrice;

    }


    private static Price convertRuppeeToEuro(Price price, String citizenType, String pricingType) {

        Price conversionPrice = covertPriceTo(price, CountryPrice.GERMANY.getValue());
        if (StringUtils.equalsIgnoreCase(pricingType, PriceType.getName("2")))
            return covertSubscriptionPriceTo(conversionPrice, citizenType, CountryPrice.GERMANY.getValue());
        if (StringUtils.equalsIgnoreCase(pricingType, PriceType.getName("0")))
            return setTotalCostToZero(conversionPrice);

        return conversionPrice;
    }

    private static Price convertRuppeeToDollar(Price price, String citizenType, String pricingType) {

        Price conversionPrice = covertPriceTo(price, CountryPrice.USA.getValue());
        if (StringUtils.equalsIgnoreCase(pricingType, PriceType.getName("2")))
            covertSubscriptionPriceTo(conversionPrice, citizenType, CountryPrice.USA.getValue());
        if (StringUtils.equalsIgnoreCase(pricingType, PriceType.getName("0")))
            return setTotalCostToZero(conversionPrice);

        return conversionPrice;
    }

    private static Price convertRuppee(Price price, String citizenType, String pricingType) {

        if (StringUtils.equalsIgnoreCase(pricingType, PriceType.getName("2")))
            return covertSubscriptionPriceTo(price, citizenType, CountryPrice.INDIA.getValue());
        if (StringUtils.equalsIgnoreCase(pricingType, PriceType.getName("0")))
            return setTotalCostToZero(price);

        return price;
    }

    private static Price covertSubscriptionPriceTo(Price conversionPrice, String citizenType, double countryPrice) {
        Price price = conversionPrice;
        if (!StringUtils.equalsIgnoreCase(citizenType, CountryCode.INDIA.getValue()))
            price.setSubscriptionPrice((float) (conversionPrice.getSubscriptionPrice() / countryPrice));

        price.setTotalCost(((conversionPrice.getSubscriptionPrice() + conversionPrice.getTotalCost()) - conversionPrice.getBasePrice()));

        return price;
    }

    private static Price setTotalCostToZero(Price price) {
        Price freePrice = price;
        freePrice.setTotalCost(price.getTotalCost() - price.getBasePrice());
        return freePrice;
    }

   /*
        This Method take input of country Price and Convert to respective country currency.
    */

    private static Price covertPriceTo(Price price, double countryPrice) {
        return Price.builder()
                .basePrice((float) (price.getBasePrice() / countryPrice))
                .gst((float) (price.getGst() / countryPrice))
                .otherTax((float) (price.getOtherTax() / countryPrice))
                .shippingCharges((float) (price.getShippingCharges() / countryPrice))
                .subscriptionPrice(price.getSubscriptionPrice())
                .totalCost((float) (price.getTotalCost() / countryPrice))
                .build();

    }

    /*
      This Method used to Identity the citizenType of user Based on the given Request

     */

    private static String identifyCitizenType(Map<String, String> countryCodes, String citizenType) {
        return countryCodes.entrySet().stream()
                .filter(code -> StringUtils.equalsIgnoreCase(citizenType, code.getKey()))
                .map(code -> code.getKey())
                .collect(Collectors.joining());

    }

    private static float calculateGst(double baseprice) {
        return (float) (baseprice / 100) * 7;

    }

    private static float shippingCharges(double baseprice) {
        return (float) (baseprice / 100) * 3;
    }

    private static float subscriptionPrice(double baseprice) {
        return (float) (baseprice / 100) * 40;
    }

    private static float otherTaxes(double baseprice) {
        return (float) (baseprice / 100) * 2;
    }

}
