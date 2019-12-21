package student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import student.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("select s from Student s join fetch s.course c where c.id = :param and c.active = true ORDER BY s.name ASC ")
	List<Student> findByCourseId(@Param("param") Long courseID);

	Student findByName(String name);
}