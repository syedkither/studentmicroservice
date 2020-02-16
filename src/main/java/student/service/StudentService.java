package student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import student.entity.Course;
import student.entity.Student;
import student.entity.StudentResponse;
import student.entity.StudentVO;
import student.mapper.StudentEntityMapper;
import student.mapper.StudentResponseMapper;
import student.repository.CourseRepository;
import student.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;	
	
	@Autowired
    private StudentEntityMapper studentEntityMapper;
	
	@Autowired
    private StudentResponseMapper studentResponseMapper;
	
	public List<StudentResponse> findStudentByCourse(final String courseID){
		 final List<Student> aoStudent = studentRepository.findByCourseId(Long.parseLong(courseID));
		 return studentResponseMapper.from(aoStudent);
	}
	
	public Optional<Course> findCourseByID(final String courseID){
		return courseRepository.findById(Long.parseLong(courseID));
	}
	
	public void saveStudent(final StudentVO studentvo){
		final Student student = studentEntityMapper.from(studentvo);
		studentRepository.save(student);
	}
	
	public void deleteById(final String studentID){
		studentRepository.deleteById(Long.parseLong(studentID));
	}

}
