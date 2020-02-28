package student.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import student.controller.AbstractTest;
import student.entity.Course;
import student.entity.Student;

public class StudentServiceTest extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Autowired
	private StudentService studentService;
	
	@Test
	public void findStudentByCourse() {
		Course course = new Course("Web Basics", "WB", 130.0, true);
	    Set<Course> courseSet = new HashSet<>(); 
	    courseSet.add(course);
		Student student = new Student();
		student.setName("ijaz");
		student.setEmail("syed@gmail.com");
		student.setAge(20);
		student.setId(1L);
		student.setCourses(courseSet);
		List<Student> aoStudent = new ArrayList();
		aoStudent.add(student);
		doReturn(aoStudent).when(studentRepository).findByCourseId(any(Long.class));
		studentService.findStudentByCourse("1");

	}
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testMissingFields() {
		Student student = new Student();
		student.setName("ijaz");
		student.setEmail(null);
		student.setAge(20);
		student.setId(1L);
		student.setCourses(null);
		List<Student> aoStudent = new ArrayList();
		aoStudent.add(student);
		doReturn(aoStudent).when(studentRepository).findByCourseId(any(Long.class));
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Student response cannot be empty");
        studentService.findStudentByCourse("1");
	}
	
}
