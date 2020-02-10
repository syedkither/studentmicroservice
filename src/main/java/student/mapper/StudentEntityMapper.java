package student.mapper;

import student.entity.Student;
import student.entity.StudentVO;

public interface StudentEntityMapper {
	 void from(final Student student, final StudentVO studentvo);
	 Student from(final StudentVO studentvo);
}
