package com.online.coursedetails.api;

import com.online.coursedetails.model.Course;
import com.online.coursedetails.model.request.Request;
import com.online.coursedetails.model.response.CoursePrice;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiOperation(value = "Pricing Details of Courses ", tags = "Course Controller")
@SwaggerDefinition(
        consumes = {"application/json"},
        produces = {"application/json"}
)
@RequestMapping(value = "/course")
public interface CourseApi {

    @PostMapping(value = "/Price")
    @ApiOperation(value = "Gets Course Price ", notes = "Based on Input  citizenType It returns Price." +
            " Possible values of citizen Type IND, USA ,DEU")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "not found!!!")})
    public CoursePrice getCoursePriceById(@RequestBody Request request);

    @GetMapping(value = "/getAllCourses")
    @ApiOperation(value = "Gets All Courses ", notes = "Returns All courses from the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "not found!!!")})
    public List<Course> getAllCourses();

    @PostMapping(value = "/checkout")
    @ApiOperation(value = "Gets checkOut Details and Course Price ", notes = "Returns course Price based on citizenType")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "not found!!!")})
    public CoursePrice checkOutDetails(@RequestBody Request request);

    @PostMapping(value = "/description")
    @ApiOperation(value = "Gets courseDescription Details and Course Price ",
            notes = " Returns course Price based on citizenType")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "not found!!!")})
    public CoursePrice courseDescription(@RequestBody Request request);


    @PostMapping(value = "/create")
    @ApiOperation(value = "create Course ",
            notes = "Course Can be offered in 3 strategies  (Free, oneTimeSubscription, subscription)," +
                    " default values assigned to 3 strategies," +
                    " Free= 0, oneTimeSubscription=1, subscription=2")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "not found!!!")})
    public Course createCourse(@RequestBody Course course);

}
