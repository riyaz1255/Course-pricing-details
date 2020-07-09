package com.online.coursedetails.dal;


import com.online.coursedetails.model.Course;
import com.online.coursedetails.model.response.CoursePrice;
import com.online.coursedetails.model.request.Request;

import java.util.List;

public interface CourseDAL {

    CoursePrice getCoursePriceById(Request request);

    CoursePrice getCheckOutDetails(Request request);

    CoursePrice getCourseDescription(Request request);

    Course createCourse(Course course);

    List<Course> getAllCourses();

}