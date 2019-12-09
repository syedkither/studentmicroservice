package student.component;

import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import student.entity.Course;

@Component
public class Receiver {

	CourseComponent courseComponent;

	@Autowired
	public Receiver(CourseComponent courseComponent) {
		this.courseComponent = courseComponent;
	}

	@Bean
	Queue queueadd() {
		return new Queue("StudentAQ", false);
	}

	@Bean
	Queue queuerem() {
		return new Queue("StudentRQ", false);
	}

	@RabbitListener(queues = "StudentAQ")
	public void processAMessage(Map<String, Object> course) {

		Course c = new Course.Builder().title((String) course.get("title"))
				.abbreviation((String) course.get("description")).active(new Boolean(course.get("active").toString()))
				.fee(new Double(course.get("fee").toString())).build();

		courseComponent.addCourse(c);

	}

	@RabbitListener(queues = "StudentRQ")
	public void processRMessage(String courseID) {
		courseComponent.removeCourse(courseID);

	}
}
