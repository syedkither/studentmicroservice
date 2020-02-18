package student.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import student.controller.AbstractTest;
import student.entity.Course;
public class CourseComponentServiceTest extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void add() {
		Course course = Course.builder().abbreviation("Machine Learning")
				.active(true)
				.fee(1500.00)
				.title("ML").build();				
		doReturn(course).when(courseRepository).save(course);
		// when
		courseComponent.addCourse(course);
		// then
		assertThat(course).isEqualTo(course);
	}
	
	@Test
	public void remove() {
		Course course =  Course.builder().abbreviation("Machine Learning")
				.active(true)
				.fee(1500.00)
				.title("ML").build();
		doReturn(Optional.of(course)).when(courseRepository).findById(1L);		
		course.setActive(false);
		// when
		courseComponent.removeCourse("1");
		// then
		assertFalse(course.getActive());		
	}

}
