package au.com.goielts.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Size(min = 3, max = 50)
	@Column(nullable = false)
	private String name;
	
	@NotEmpty
	@Type(type = "text")
	@Column(nullable = false)
	private String description;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id")
	@OrderBy("number")
	private Set<Week> weeks;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_student", 
        joinColumns = { @JoinColumn(name = "course_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "student_id") })
	private Set<Student> students;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_teacher", 
        joinColumns = { @JoinColumn(name = "course_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "teacher_id") })
	private Set<Teacher> teachers;
	
	public Set<Week> getWeeks() {
		return weeks;
	}

	public void setWeeks(Set<Week> weeks) {
		this.weeks = weeks;
	}

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

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
}
