package student;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import student.entity.Course;
import student.entity.Student;
import student.repository.CourseRepository;
import student.repository.StudentRepository;
@EnableDiscoveryClient
@SpringBootApplication
public class StudentserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(StudentRepository studentRepository, CourseRepository courseRepository) {
		return args -> {

			// create a student

			Student student = new Student("John Doe", 15);

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
