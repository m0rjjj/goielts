package au.com.goielts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.TaskDao;
import au.com.goielts.model.Task;

@Service("taskService")
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao dao;
	
	@Override
	public Task findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void save(Task task) {
		dao.save(task);
		
	}

	@Override
	public void update(Task task) {
		Task entity = dao.findById(task.getId());
		if(entity!=null){
			//set fields
		}
		
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
		
	}

	@Override
	public List<Task> findAll() {
		return dao.findAll();
	}
	
	@Override
	public List<Task> findAllByCourseId(int id) {
		return dao.findAllByCourseId(id);
	}

}
