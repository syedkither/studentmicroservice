package student.controller;

import java.text.MessageFormat;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import student.entity.Course;
import student.entity.Student;
import student.entity.StudentResponse;
import student.entity.StudentVO;
import student.exception.RecordNotFoundException;
import student.mapper.StudentEntityMapper;
import student.mapper.StudentResponseMapper;
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

	private static Logger logger = LoggerFactory.getLogger(StudentController.class.getName());

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;	
	@Autowired
    private StudentEntityMapper studentEntityMapper;
	@Autowired
    private StudentResponseMapper studentResponseMapper;
	
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
	@GetMapping(path = "/get")
	List<StudentResponse> getStudents(@ApiParam(value = "The Id of the course to register", required = true) @RequestParam(value = "courseID") final String courseID) {
		final List<Student> aoStudent = studentRepository.findByCourseId(Long.parseLong(courseID));
		if(aoStudent.isEmpty()){
			throw new RecordNotFoundException(String.format(
					"Student list cann't be retrived,  because Course ID : %s doesn't exists", courseID));
		}
		return studentResponseMapper.from(aoStudent);
	}
	@ApiOperation(value = "This endpoint is used to add student and register course")
	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	void add(@Valid @RequestBody final StudentVO studentvo, @RequestParam @Min(1)  final String courseID) {		

		final Course course = courseRepository.findById(Long.parseLong(courseID))
				.orElseThrow(() -> new RecordNotFoundException(String.format(
						"Student Cann't be registered the course,  because Course ID : %s doesn't exists", courseID)));

		// add courses to the student
		studentvo.getCourse().add(course);
		// update the student
		final Student student = studentEntityMapper.from(studentvo);
		studentRepository.save(student);
		if (logger.isInfoEnabled()){
			logger.info(MessageFormat.format("Student is registered to course {0}", courseID));
		}

	}
	@ApiOperation(value = "This endpoint is used to remove the student details")
	@DeleteMapping(path = "/remove")
	void remove(@RequestParam(value = "studentID") @Min(1)  final String studentID) {
		if (Objects.nonNull(studentID)) {
			studentRepository.deleteById(Long.parseLong(studentID));
			if (logger.isInfoEnabled()){
				logger.info(MessageFormat.format("Student ID {0} removed", studentID));
			}
		}else {			
			throw new RecordNotFoundException(String.format("Student ID : %s doesn't exists", studentID));
		}
		
	}
}
