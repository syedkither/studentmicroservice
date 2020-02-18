package student.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import student.controller.AbstractTest;
import student.entity.Course;

public class ReceiverTest extends AbstractTest {

	private final static String EXCHANGE_NAME = "spring-boot-exchange";
	private final static String QUEUE_ROUTINGKEY_ADD = "routingKeyadd-boot";
	private final static String QUEUE_ROUTINGKEY_REMOVE = "routingKeyremove-boot";
	
	@Autowired
	protected RabbitTemplate rabbitTemplate;
	
	@ClassRule
	public static final EmbeddedInMemoryQpidBrokerRule qpidBrokerRule = new EmbeddedInMemoryQpidBrokerRule();

	@Test
	public void testWithReceiverRoutingKey() throws Exception {

		Map<String, Object> courseDetails = new HashMap<String, Object>();
		courseDetails.put("id", 1);
		courseDetails.put("courseId", 4);
		courseDetails.put("description", "Data Analyst");
		courseDetails.put("active", true);
		courseDetails.put("title", "Data Analyst Training");
		courseDetails.put("fee", 150.00);

		doNothing().when(courseComponent).addCourse(any(Course.class));

		rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_ROUTINGKEY_ADD, courseDetails);
		rabbitTemplate.convertAndSend(EXCHANGE_NAME,QUEUE_ROUTINGKEY_REMOVE, "4");
		
		// assertThat(firstReceiver.getCounter()).isEqualTo(3);

	}

	/*@Test
	public void testNoRoutingkey() throws Exception {
		doNothing().when(courseComponent).removeCourse(any(String.class));
	//	rabbitTemplate.convertAndSend(EXCHANGE_NAME,"routing_not_found", "Hello from RabbitMQ Sent 1!");
		//Thread.sleep(5000);
	}*/
}
