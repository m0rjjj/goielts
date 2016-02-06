package au.com.goielts.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="student")
@PrimaryKeyJoinColumn(name="user_id")
public class Student extends User{
	
	@Column(name="student_id")
    private String studentId;
	
	@Type(type = "text")
	private String address;
	
	private String phone;
	
	@Type(type = "text")
	private String about;
	
	@ManyToMany(mappedBy="students")
	@OrderBy("name")
    private Set<Course> courses = new LinkedHashSet<>();

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

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

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", address=" + address + ", phone=" + phone + ", about=" + about
				+ "]";
	}
}
