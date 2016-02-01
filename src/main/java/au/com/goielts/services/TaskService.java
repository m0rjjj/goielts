package au.com.goielts.services;

import java.util.List;

import au.com.goielts.model.Task;

public interface TaskService {

	Task findById(int id);

	void save(Task task);

	void update(Task task);

	void delete(String id);

	List<Task> findAll();
	
	List<Task> findAllByCourseId(int id);

}
