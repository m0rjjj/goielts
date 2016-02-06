package au.com.goielts.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Week {
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	private Integer number;
	
	@NotEmpty
	@Type(type = "text")
	@Column(nullable = false)
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="materials", 
	    joinColumns={@JoinColumn(name="week_id")},
	    inverseJoinColumns={@JoinColumn(name="file_id")}
	)
	@OrderBy("id")
	private Set<UploadedFile> materials;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UploadedFile> getMaterials() {
		return materials;
	}

	public void setMaterials(Set<UploadedFile> materials) {
		this.materials = materials;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
