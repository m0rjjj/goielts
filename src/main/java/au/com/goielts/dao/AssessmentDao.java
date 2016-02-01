package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.Assessment;

public interface AssessmentDao {
	Assessment findById(int id);

	void save(Assessment assessment);

	void delete(String id);

	List<Assessment> findAll();

	Assessment findByTaskIdAndStudentId(int taskId, int studentId);
}