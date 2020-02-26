package student.controller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import student.entity.Course;
import student.entity.StudentResponse;
import student.entity.StudentResponse.StudentResponseBuilder;
import student.entity.StudentVO;
import student.service.StudentService;
public class StudentControllerTest extends AbstractTest {
	   
	   @Override
	   @Before
	   public void setUp() {
	      super.setUp();
	   }
	   
	   @Mock
	   private StudentService studentService;
	   
	   @Test
	   public void get() throws Exception {
	      String uri = "/student/get?courseID=1";
	      List<StudentResponse> aoStudent = new ArrayList<>();
	      
	      aoStudent.add(studentResponse());
	      
	      when(studentService.findStudentByCourse(any(String.class))).thenReturn(aoStudent);
	      
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      StudentVO[] studentvo = mapFromJson(content, StudentVO[].class);
	      assertTrue(studentvo[0].getName().equalsIgnoreCase("Syed Kither"));
	   }
	   @Test
	   public void getNoRecord() throws Exception {
	      String uri = "/student/get?courseID=1";
	      List<StudentResponse> aoStudent = new ArrayList<>();
	      
	      when(studentService.findStudentByCourse(any(String.class))).thenReturn(aoStudent);
	      
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(404, status);
	   }
	   @Test
	   public void create() throws Exception {
	      String uri = "/student/add?courseID=4";
	      Course course = new Course("Web Basics", "WB", 130.0, true);
	      when(studentService.findCourseByID(any(String.class))).thenReturn(Optional.of(course));
	      
	      String inputJson = super.mapToJson(studentResponse());
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      
	   } 
	   
	   private StudentResponse studentResponse(){
		   	Course course = new Course("Web Basics", "WB", 130.0, true);
		    Set<Course> courseSet = new HashSet<>(); 
		    courseSet.add(course);
		    final StudentResponseBuilder studentResponseBuilder = StudentResponse.builder();
		    	studentResponseBuilder.withAge(15)
		        .withId(4L)
		        .withCourse(courseSet)
		        .withEmail("syed@gmail.com")
		        .withName("Syed Kither"); 
		    	
		    	return studentResponseBuilder.build();
	   }
	  
	   @Test
	   public void delete() throws Exception {
	      String uri = "/student/remove?studentID=1";
	      doNothing().when(studentService).deleteById(any(String.class));
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	   }
	   @Test
	   public void deleteOnNoRecord() throws Exception {
	      String uri = "/student/remove/";
	      doNothing().when(studentService).deleteById(any(String.class));
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	}