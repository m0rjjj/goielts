package au.com.goielts.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Assessment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	
	@OneToOne
	@JoinColumn(name="assessment_file_id")
	private UploadedFile assessmentFile;
	
	@OneToOne
	@JoinColumn(name="marked_file_id")
	private UploadedFile markedFile;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private User student;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private User teacher;
	
	private double mark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public UploadedFile getAssessmentFile() {
		return assessmentFile;
	}

	public void setAssessmentFile(UploadedFile assessmentFile) {
		this.assessmentFile = assessmentFile;
	}

	public UploadedFile getMarkedFile() {
		return markedFile;
	}

	public void setMarkedFile(UploadedFile markedFile) {
		this.markedFile = markedFile;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
}
