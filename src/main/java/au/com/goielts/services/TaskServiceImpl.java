package au.com.goielts.services;

import java.util.List;

import org.hibernate.Hibernate;
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
	private boolean withAssessments;
	
	@Override
	public Task findById(int id) {
		return buildEntity(dao.findById(id));
	}

	private Task buildEntity(Task task) {
		if(withAssessments){
			Hibernate.initialize(task.getAssessments());
		}
		return task;
	}

	@Override
	public void save(Task task) {
		dao.save(task);
	}

	@Override
	public void update(Task task) {
		Task entity = dao.findById(task.getId());
		if(entity!=null){
			entity.setName(task.getName());
			entity.setDescription(task.getDescription());
			entity.setDueDate(task.getDueDate());
		}
		
	}

	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public List<Task> findAll() {
		return dao.findAll();
	}
	
	@Override
	public List<Task> findAllByCourseId(int id) {
		return dao.findAllByCourseId(id);
	}
	
	@Override
	public TaskService with(String name){
		switch (name) {
		case "assessments":
			withAssessments = true;
			break;
		default:
			break;
		}
		return this;
	}

}
