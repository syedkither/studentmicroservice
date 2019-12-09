package student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import student.entity.Course;
import student.entity.Student;
import student.repository.CourseRepository;
import student.repository.StudentRepository;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CourseRepository courseRepository;

	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
	List<Student> getStudents(@RequestParam(value = "courseID") String courseID) {

		return studentRepository.findByCourseId(Long.parseLong(courseID));
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void add(@RequestBody Student student, @RequestParam String courseID) {

		studentRepository.save(student);

		Optional<Course> course = courseRepository.findById(Long.parseLong(courseID));

		// add courses to the student
		if (course.isPresent())
			student.getCourse().add(course.get());

		// update the student
		studentRepository.save(student);

	}

	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	void remove(@RequestParam(value = "studentID") String studentID) {
		studentRepository.deleteById(Long.parseLong(studentID));
	}
}
