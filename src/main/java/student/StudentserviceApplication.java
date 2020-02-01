package student;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import student.entity.Course;
import student.entity.Student;
import student.repository.CourseRepository;
import student.repository.StudentRepository;

@EnableDiscoveryClient
@SpringBootApplication
// interfering with the spring boot auto configuration of the resources, i.e. the one that serves up swagger-ui. 
//@EnableWebMvc // extention content negotiation will not work if want then uncomment bcos swagger ui will not work in boot and webmvc combination
public class StudentserviceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(StudentserviceApplication.class, args);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		/*
		 * Spring will look for path extension first, if that is not present
		 * then will look for path parameter. And if both of these are not
		 * available in the input request, then default content type will be
		 * returned back
		 */
		configurer.favorPathExtension(true).ignoreUnknownPathExtensions(true).favorParameter(true)
				.parameterName("mediaType").ignoreAcceptHeader(false).defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Bean
	public CommandLineRunner mappingDemo(StudentRepository studentRepository, CourseRepository courseRepository) {
		return args -> {

			// create a student

			Student student = new Student("John Doe", 15, "syed@gmail.com");

			// save the student
			studentRepository.save(student);

			// create three courses
			Course course1 = new Course("Machine Learning", "ML", 1500.0, true);
			Course course2 = new Course("Database Systems", "DS", 800.0, true);
			Course course3 = new Course("Web Basics", "WB", 130.0, true);

			// save courses
			courseRepository.saveAll(Arrays.asList(course1, course2, course3));

			// add courses to the student
			student.getCourse().addAll(Arrays.asList(course1, course2, course3));

			// update the student
			studentRepository.save(student);

		};
	}

}
