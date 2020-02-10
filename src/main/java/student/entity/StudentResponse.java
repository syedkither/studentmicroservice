package student.entity;

import java.util.Set;

import org.springframework.util.StringUtils;

import student.component.ErrorMessageConstants;
public class StudentResponse {
	private Long id;	
	private String name;	
	private Integer age;	
	private String email;	
	private Set<Course> course;
	
	private StudentResponse(final StudentResponseBuilder studentResponseBuilder){
		 this.id = studentResponseBuilder.id;
		 this.name = studentResponseBuilder.name;
		 this.age = studentResponseBuilder.age;
		 this.email = studentResponseBuilder.email;
		 this.course = studentResponseBuilder.course;
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
	public Set<Course> getCourse() {
		return course;
	}
	public void setCourse(Set<Course> course) {
		this.course = course;
	}
	public static StudentResponseBuilder builder(){
        return new StudentResponseBuilder();
    }
	public static class StudentResponseBuilder {
		private Long id;	
		private String name;	
		private Integer age;	
		private String email;	
		private Set<Course> course;
		
		 private StudentResponseBuilder(){}
		 
		 public StudentResponseBuilder withId(final Long id) {
	            this.id = id;
	            return this;
	     }
		 public StudentResponseBuilder withName(final String name) {
	            this.name = name;
	            return this;
	     }
		 public StudentResponseBuilder withAge(final Integer age) {
	            this.age = age;
	            return this;
	     }
		 public StudentResponseBuilder withEmail(final String email) {
	            this.email = email;
	            return this;
	     }
		 public StudentResponseBuilder withCourse(final Set<Course> course) {
	            this.course = course;
	            return this;
	     }
		 public StudentResponse build(){
	            if(StringUtils.isEmpty(name) || StringUtils.isEmpty(age) || StringUtils.isEmpty(email)  || StringUtils.isEmpty(course) ){
	                throw new IllegalArgumentException(ErrorMessageConstants.ADDRESS_RESPONSE_EMPTY);
	            }
	            return new StudentResponse(this);
	     }
	}
	
}
