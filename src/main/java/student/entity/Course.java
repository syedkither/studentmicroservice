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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String abbreviation;
	private Double fee;
	private Boolean active;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	private Set<Student> student = new HashSet<>();

	public Course() {
	}

	public Course(String title, String abbreviation, Double fee, Boolean active) {
		this.title = title;
		this.abbreviation = abbreviation;
		this.fee = fee;
		this.active = active;

	}

	private Course(Builder builder) {
		this.title = builder.title;
		this.abbreviation = builder.abbreviation;
		this.fee = builder.fee;
		this.active = builder.active;

	}

	public Long getId() {
		return id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	@JsonIgnore
	public Set<Student> getStudent() {
		return student;
	}
	@JsonIgnore
	public void setStudent(Set<Student> student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "Course{" + "id=" + id + ", title='" + title + '\'' + ", abbreviation='" + abbreviation + '\''
				+ ", active='" + active + '\'' + ", fee=" + fee + '}';
	}

	public static class Builder {
		private String title;
		private String abbreviation;
		private Double fee;
		private Boolean active;

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder abbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
			return this;
		}

		public Builder active(Boolean active) {
			this.active = active;
			return this;
		}

		public Builder fee(Double fee) {
			this.fee = fee;
			return this;
		}

		public Course build() {
			return new Course(this);
		}

	}
}