package student.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

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

import student.entity.Course;
import student.entity.Student;
import student.exception.RecordNotFoundException;
import student.repository.CourseRepository;
import student.repository.StudentRepository;

@RestController
@CrossOrigin
@RequestMapping("/student")
@RefreshScope
//Path Variables/ Request Param Validation
@Validated
public class StudentController {

	protected static Logger logger = LoggerFactory.getLogger(StudentController.class.getName());

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CourseRepository courseRepository;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	List<Student> getStudents(@RequestParam(value = "courseID") String courseID) {

		return studentRepository.findByCourseId(Long.parseLong(courseID));
	}

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
