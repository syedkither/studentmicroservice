package student.controller;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import student.component.CourseComponentService;
import student.exception.CustomExceptionHandler;
import student.repository.CourseRepository;
import student.repository.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractTest {

	protected MockMvc mvc;

	@InjectMocks
	protected StudentController studentController;

	@MockBean
	protected CourseRepository courseRepository;

	@MockBean
	protected StudentRepository studentRepository;

	@Autowired
	protected CourseComponentService courseComponent;

	protected void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(studentController)
				.setControllerAdvice(new CustomExceptionHandler())
				.build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}