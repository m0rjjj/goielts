package au.com.goielts.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@Column(nullable = false)
	private String name;
	
	@NotEmpty
	@Type(type = "text")
	@Column(nullable = false)
	private String description;
	
	@NotEmpty
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name="due_date")
	private Date dueDate;

	@OneToMany(mappedBy="task",cascade=CascadeType.ALL)
	private Set<Assessment> assessments;
	
	@OneToOne
	@JoinColumn(name="course_id")
	private Course course;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Set<Assessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
