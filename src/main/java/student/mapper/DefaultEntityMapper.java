package student.mapper;

import org.springframework.stereotype.Component;

import student.entity.Student;
import student.entity.StudentVO;

@Component
public class DefaultEntityMapper implements StudentEntityMapper {

    public void from(final Student student, final StudentVO studentvo) {
    	student.setAge(studentvo.getAge());
    	student.setEmail(studentvo.getEmail());
    	student.setName(studentvo.getName());
    	student.setCourses(studentvo.getCourse());    	       
    }


    public Student from(final StudentVO studentvo) {
        final Student student = new Student();
        from(student, studentvo);
        return student;
    }
}
