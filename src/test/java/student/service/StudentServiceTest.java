package student.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import student.component.ErrorMessageConstants;
import student.controller.AbstractTest;
import student.entity.Course;
import student.entity.Student;
import student.entity.StudentVO;
import student.mapper.StudentEntityMapper;

public class StudentServiceTest extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Autowired
	private StudentService studentService;
	
	@Mock
	StudentEntityMapper studentEntityMapper;
	
	@Test
	public void findStudentByCourse() {
		
		List<Student> aoStudent = new ArrayList();
		aoStudent.add(getStudentEntity());
		doReturn(aoStudent).when(studentRepository).findByCourseId(any(Long.class));
		studentService.findStudentByCourse("1");

	}
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testMissingFields() {
		Student student = getStudentEntity();
		student.setEmail(null);
		List<Student> aoStudent = new ArrayList();
		aoStudent.add(student);
		doReturn(aoStudent).when(studentRepository).findByCourseId(any(Long.class));
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(ErrorMessageConstants.ADDRESS_RESPONSE_EMPTY);
        studentService.findStudentByCourse("1");
	}
	
	@Test
	public void save(){
		StudentVO studentvo = new StudentVO();
		Course course = new Course("Web Basics", "WB", 130.0, true);
	    Set<Course> courseSet = new HashSet<>(); 
	    courseSet.add(course);
	    studentvo.setName("ijaz");
	    studentvo.setEmail("syed@gmail.com");
	    studentvo.setAge(20);
	    studentvo.setId(1L);
	    studentvo.setCourse(courseSet);
	    Student student = getStudentEntity();
		doReturn(student).when(studentEntityMapper).from(studentvo);
		doReturn(student).when(studentRepository).save(student);
		studentService.saveStudent(studentvo);
		assertThat(student).isEqualTo(student);
		
	}
	
	public Student getStudentEntity(){
		Course course = new Course("Web Basics", "WB", 130.0, true);
	    Set<Course> courseSet = new HashSet<>(); 
	    courseSet.add(course);
		Student student = new Student();
		student.setName("ijaz");
		student.setEmail("syed@gmail.com");
		student.setAge(20);
		student.setId(1L);
		student.setCourses(courseSet);
		return student;
	}
	
}
