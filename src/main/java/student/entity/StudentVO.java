package student.entity;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class StudentVO implements Serializable {
	
	
	private static final long serialVersionUID = 8577155903278060953L;
	
	
	@ApiModelProperty(notes = "The database generated member ID")	
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
	
	private Set<Course> course;
	
	public Set<Course> getCourse() {
		return course;
	}
	public void setCourse(Set<Course> course) {
		this.course = course;
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
