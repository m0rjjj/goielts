package au.com.goielts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Material {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotEmpty
	@Column(nullable = false)
	private String filename;
	
	@NotEmpty
	@Column(nullable = false)
	private String path;
	
	@Column(nullable = false)
	private String extention;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}
	
}
