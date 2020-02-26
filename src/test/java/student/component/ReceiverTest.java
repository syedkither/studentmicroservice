package student.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import student.controller.AbstractTest;
import student.entity.Course;

public class ReceiverTest extends AbstractTest {

	private static final String EXCHANGE_NAME = "spring-boot-exchange";
	private static final String QUEUE_ROUTINGKEY_ADD = "routingKeyadd-boot";
	private static final String QUEUE_ROUTINGKEY_REMOVE = "routingKeyremove-boot";
	@Mock
	CourseComponentService courseComponentService;
	@Autowired
	protected RabbitTemplate rabbitTemplate;
	
	@ClassRule
	public static final EmbeddedInMemoryQpidBrokerRule qpidBrokerRule = new EmbeddedInMemoryQpidBrokerRule();

	@Test
	public void testWithReceiverRoutingKey() {

		Map<String, Object> courseDetails = new HashMap<>();
		courseDetails.put("id", 1);
		courseDetails.put("courseId", 4);
		courseDetails.put("description", "Data Analyst");
		courseDetails.put("active", true);
		courseDetails.put("title", "Data Analyst Training");
		courseDetails.put("fee", 150.00);

		doNothing().when(courseComponentService).addCourse(any(Course.class));

		rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_ROUTINGKEY_ADD, courseDetails);
		rabbitTemplate.convertAndSend(EXCHANGE_NAME,QUEUE_ROUTINGKEY_REMOVE, "4");		

	}	
}
