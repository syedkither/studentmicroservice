package student.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import student.entity.Student;
import student.entity.StudentResponse;
import student.entity.StudentResponse.StudentResponseBuilder;

@Component
public class DefaultResponseMapper implements StudentResponseMapper {

    public List<StudentResponse> from(final List<Student> student) {
        return student.stream().map(this::from).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public StudentResponse from(Student student){
    	final StudentResponseBuilder studentResponseBuilder = StudentResponse.builder();
    	studentResponseBuilder.withAge(student.getAge())
        .withId(student.getId())
        .withCourse(student.getCourse())
        .withEmail(student.getEmail())
        .withName(student.getName());                
    	return studentResponseBuilder.build();
    }
}
