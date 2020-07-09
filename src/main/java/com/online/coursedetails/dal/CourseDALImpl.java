package com.online.coursedetails.dal;


import com.online.coursedetails.model.Course;
import com.online.coursedetails.model.Price;
import com.online.coursedetails.model.response.CoursePrice;
import com.online.coursedetails.model.request.Request;
import com.online.coursedetails.transformation.ResponseTransformer;
import com.online.coursedetails.utils.Constants;
import com.online.coursedetails.utils.CountryCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CourseDALImpl implements CourseDAL {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ResponseTransformer responseTransformer;


    @Override
    public List<Course> getAllCourses() {
        return mongoTemplate.findAll(Course.class);
    }


    @Override
    public CoursePrice getCoursePriceById(Request request) {
        CoursePrice coursePrice = null;
        log.info(Constants.COURSE_ID, request.getCourseId());
        Query query = new Query();
        query.addCriteria(Criteria.where("courseId").is(request.getCourseId()));
        Course course = mongoTemplate.findOne(query, Course.class);
        if (null != course && null != course.getPricingStrategy())
            log.info(Constants.SUCCESS);
        return responseTransformer.getCoursePrice(course, request.getCitizenType());

    }

    @Override
    public CoursePrice getCheckOutDetails(Request request) {
        CoursePrice coursePrice = null;
        log.info(Constants.COURSE_ID, request.getCourseId());
        Query query = new Query();
        query.addCriteria(Criteria.where("courseId").is(request.getCourseId()));
        Course course = mongoTemplate.findOne(query, Course.class);
        if (null != course && null != course.getPricingStrategy())
            log.info(Constants.SUCCESS);
        return responseTransformer.getCoursePrice(course, request.getCitizenType());

    }

    @Override
    public CoursePrice getCourseDescription(Request request) {
        CoursePrice coursePrice = null;
        log.info(Constants.COURSE_ID, request.getCourseId());
        Query query = new Query();
        query.addCriteria(Criteria.where("courseId").is(request.getCourseId()));
        Course course = mongoTemplate.findOne(query, Course.class);
        if (null != course && null != course.getPricingStrategy())
            log.info(Constants.SUCCESS);
        coursePrice = responseTransformer.getCoursePrice(course, request.getCitizenType());

        if (StringUtils.equalsIgnoreCase(coursePrice.getCitizenType(), CountryCode.USA.getValue())) {
            coursePrice.setPrice(Price.builder()
                    .basePrice(coursePrice.getPrice().getBasePrice())
                    .build());
            return coursePrice;
        }
        if (StringUtils.equalsIgnoreCase(coursePrice.getCitizenType(), CountryCode.INDIA.getValue())) {
            coursePrice.setPrice(Price.builder()
                    .totalCost(coursePrice.getPrice().getTotalCost())
                    .build());
            return coursePrice;
        }

        return coursePrice;
    }

    @Override
    public Course createCourse(Course course) {
        mongoTemplate.save(course);
        return course;
    }

}
