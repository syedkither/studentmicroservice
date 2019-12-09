package student.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import student.entity.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
	List<Course> findByTitleContaining(String title);

	List<Course> findByFeeLessThan(double fee);
}