package au.com.goielts.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="teacher")
@PrimaryKeyJoinColumn(name="user_id")
public class Teacher extends User{
	
	@Type(type = "text")
	private String address;
	
	private String phone;
	
	@Type(type = "text")
	private String about;

	@Type(type = "text")
	private String qualifications;
	
	@ManyToMany(mappedBy="teachers")
	@OrderBy("name")
    private Set<Course> courses = new LinkedHashSet<>();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	
	

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Teacher [address=" + address + ", phone=" + phone + ", about=" + about + ", qualifications="
				+ qualifications + "]";
	}

}
