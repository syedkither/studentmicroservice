package student.controller;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import student.component.CourseComponent;
import student.repository.CourseRepository;
import student.repository.StudentRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class)
public abstract class AbstractTest {
	
   protected MockMvc mvc;
   
    @InjectMocks
	protected StudentController studentController;
   
    @MockBean
	protected CourseRepository courseRepository;
	
    @MockBean
	protected StudentRepository studentRepository;
	
    @MockBean
	protected CourseComponent courseComponent;
    
   protected void setUp() {
      mvc = MockMvcBuilders.standaloneSetup(studentController)
              .build();
   }
   protected String mapToJson(Object obj) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
   }
   protected <T> T mapFromJson(String json, Class<T> clazz)
      throws IOException {
      
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
   }
}