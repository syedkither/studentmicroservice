package student.component;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import student.entity.Course;

@Component
public class Receiver {

	CourseComponentService courseComponent;
	
	public final static String QUEUE_ROUTINGKEY_ADD = "routingKeyadd-boot";
	public final static String QUEUE_ROUTINGKEY_REMOVE = "routingKeyremove-boot";
	public final static String EXCHANGE_NAME = "spring-boot-exchange";
	private final static String QUEUE_NAME_1 = "StudentAQ";
	private final static String QUEUE_NAME_2 = "StudentRQ";


	@Autowired
	public Receiver(CourseComponentService courseComponent) {
		this.courseComponent = courseComponent;
	}

	
	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = QUEUE_NAME_1, durable = "true"),
    exchange = @Exchange(value = EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
    key = QUEUE_ROUTINGKEY_ADD))
	public void processAMessage(Map<String, Object> course) {

		Course c = new Course.Builder().title((String) course.get("title"))
				.abbreviation((String) course.get("description")).active(new Boolean(course.get("active").toString()))
				.fee(new Double(course.get("fee").toString())).build();

		courseComponent.addCourse(c);

	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = QUEUE_NAME_2, durable = "true"),
    exchange = @Exchange(value = EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
    key = QUEUE_ROUTINGKEY_REMOVE))
	public void processRMessage(String courseID) {
		courseComponent.removeCourse(courseID);

	}
}
