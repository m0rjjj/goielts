package au.com.goielts.services;

import java.util.List;

import au.com.goielts.model.Assessment;

public interface AssessmentService {

	Assessment findById(int id);

	void save(Assessment assessment);

	void delete(String id);

	List<Assessment> findAll();

	Assessment findByTaskIdAndStudentId(int taskId, int studentId);
	
	public void update(Assessment assessment);
}