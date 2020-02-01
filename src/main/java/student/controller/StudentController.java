package student.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import student.entity.Course;
import student.entity.Student;
import student.exception.RecordNotFoundException;
import student.repository.CourseRepository;
import student.repository.StudentRepository;


//https://medium.com/swlh/documenting-spring-boot-api-using-swagger2-14926e8e20a4

@RestController
@CrossOrigin
@RequestMapping("/student")
@RefreshScope
//Path Variables and Request Param Validation
@Validated
@Api(tags = "Student")
public class StudentController {

	protected static Logger logger = LoggerFactory.getLogger(StudentController.class.getName());

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CourseRepository courseRepository;
	@ApiOperation(value = "This endpoint is used to get the all student details")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = " Successfully retrieved list of students"),
            @ApiResponse(code = 201, message = " New Student was successfully created"),
            @ApiResponse(code = 401, message = " You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = " Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = " The resource you were trying to reach is not found"),
		    @ApiResponse(code = 400, message = " Bad request is received", response = Error.class),
		    @ApiResponse(code = 500, message = " Server error", response = Error.class)
		})
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	List<Student> getStudents(@ApiParam(value = "The Id of the course to register", required = true) @RequestParam(value = "courseID") String courseID) {
		List<Student> aoStudent = studentRepository.findByCourseId(Long.parseLong(courseID));
		if(aoStudent.isEmpty()){
			throw new RecordNotFoundException(String.format(
					"Student list cann't be retrived,  because Course ID : %s doesn't exists", courseID));
		}
		return studentRepository.findByCourseId(Long.parseLong(courseID));
	}
	@ApiOperation(value = "This endpoint is used to add student and register course")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void add(@Valid @RequestBody Student student, @RequestParam @Min(1)  String courseID) {		

		Course course = courseRepository.findById(Long.parseLong(courseID))
				.orElseThrow(() -> new RecordNotFoundException(String.format(
						"Student Cann't be registered the course,  because Course ID : %s doesn't exists", courseID)));

		// add courses to the student
		student.getCourse().add(course);
		// update the student
		studentRepository.save(student);
		logger.info(String.format("Student is registered to course : %s ", courseID));

	}
	@ApiOperation(value = "This endpoint is used to remove the student details")
	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	void remove(@RequestParam(value = "studentID") @Min(1)  String studentID) {
		if (Objects.nonNull(studentID)) {
			studentRepository.deleteById(Long.parseLong(studentID));
			logger.info(String.format("Student ID : %s removed", studentID));
		}else {
			// logger.info(String.format("Student ID : %s doesn't exists",
			// courseID));
			throw new RecordNotFoundException(String.format("Student ID : %s doesn't exists", studentID));
		}
		
	}
}
