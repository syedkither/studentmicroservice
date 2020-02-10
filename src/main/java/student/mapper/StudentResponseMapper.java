package student.mapper;

import java.util.List;

import student.entity.Student;
import student.entity.StudentResponse;

public interface StudentResponseMapper {
	List<StudentResponse> from(final List<Student> student);
}
