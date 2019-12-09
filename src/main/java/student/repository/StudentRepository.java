package student.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import student.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
	List<Student> findByCourseId(Long courseID);

	Student findByName(String name);
}