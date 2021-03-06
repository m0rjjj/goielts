package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.Task;

public interface TaskDao {
	Task findById(int id);

	void save(Task task);
	 
	int delete(int id);
	 
	List<Task> findAll();
	
	List<Task> findAllByCourseId(int id);
}