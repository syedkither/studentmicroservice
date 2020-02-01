package student.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "students")
public class Student {

	@Id
	@ApiModelProperty(notes = "The database generated member ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
	@NotEmpty(message = "First name must not be empty")
	@ApiModelProperty(notes = "The name of the student")
	private String name;
	@Min(value = 10, message = "Age should not be less than 10")
    @Max(value = 60, message = "Age should not be greater than 60")
	@NotNull(message = "Age must not be empty")
	@ApiModelProperty(notes = "The age of the student")
	private Integer age;
	@NotEmpty(message = "Email must not be empty")
	@Email(message = "Email should be a valid email")
	@ApiModelProperty(notes = "The email of the student")
	private String email;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "students_courses", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
			@JoinColumn(name = "course_id") })
	private Set<Course> course = new HashSet<Course>();

	public Student() {
	}

	public Student(String name, int age, String email) {
		this.name = name;
		this.age = age;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Course> getCourse() {
		return course;
	}

	public void setCourses(Set<Course> course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Student{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + ", email='" + email + '}';
	}
}