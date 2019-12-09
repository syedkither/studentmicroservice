package student.component;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import student.entity.Course;
import student.repository.CourseRepository;

@Component
public class CourseComponent {
	private static final Logger logger = LoggerFactory.getLogger(CourseComponent.class);
	@Autowired
	private CourseRepository courseRepository;

	public void addCourse(Course course) {
		logger.info("Adding new course to student service  from course service ");
		courseRepository.save(course);
	}

	public void removeCourse(String courseID) {
		logger.info("Removing course to student service from course service");
		// courseRepository.deleteById(Long.parseLong(courseID));
		Optional<Course> entity = courseRepository.findById(Long.parseLong(courseID));
		if (entity.isPresent()) {
			entity.get().setActive(false);
			courseRepository.save(entity.get());
		}
	}
}
