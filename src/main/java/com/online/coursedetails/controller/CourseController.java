package com.online.coursedetails.controller;

import com.online.coursedetails.api.CourseApi;
import com.online.coursedetails.dal.CourseDAL;
import com.online.coursedetails.repository.CourseRepository;
import com.online.coursedetails.model.Course;
import com.online.coursedetails.model.response.CoursePrice;
import com.online.coursedetails.model.request.Request;
import com.online.coursedetails.transformation.ResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController implements CourseApi {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseDAL courseDAL;

    @Autowired
    private ResponseTransformer transformer;

    public CoursePrice getCoursePriceById(@RequestBody Request request) {
        return courseDAL.getCoursePriceById(request);
    }

    public List<Course> getAllCourses() {
        return courseDAL.getAllCourses();
    }


    public CoursePrice checkOutDetails(@RequestBody Request request) {
        return courseDAL.getCheckOutDetails(request);
    }


    public CoursePrice courseDescription(@RequestBody Request request) {
        return courseDAL.getCourseDescription(request);
    }


    public Course createCourse(@RequestBody Course course) {
        return courseDAL.createCourse(course);
    }

}