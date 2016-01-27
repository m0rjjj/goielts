package au.com.goielts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="student_profile")
public class StudentProfile {
	
	@Id
    private int id;
	
    @Column(name="student_id", nullable=false)
    private String studentId;
	
	@Type(type = "text")
	private String address;
	
	private String phone;
	
	@Type(type = "text")
	private String about;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "StudentProfile [id=" + id + ", studentId=" + studentId + ", address=" + address + ", phone=" + phone
				+ ", about=" + about + "]";
	}
	
	
}
