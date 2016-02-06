package au.com.goielts.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.CourseDao;
import au.com.goielts.model.Course;

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao dao;
	
	private boolean withWeeks = false;

	@Override
	public Course findById(int id) {
		return buildEntity(dao.findById(id));
	}

	private Course buildEntity(Course course) {
		if(withWeeks){
			Hibernate.initialize(course.getWeeks());
		}
		return course;
	}

	@Override
	public void save(Course course) {
		dao.save(course);
	}

	@Override
	public void update(Course course) {
		Course entity = dao.findById(course.getId());
		if (entity != null) {
			entity.setName(course.getName());
			entity.setDescription(course.getDescription());
		}
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
	}

	@Override
	public List<Course> findAll() {
		return dao.findAll();
	}

	@Override
	public int countByStudentId(int id) {
		return dao.countByStudentId(id);
	}
	
	@Override
	public CourseService with(String name){
		switch (name) {
		case "weeks":
			withWeeks = true;
			break;

		default:
			break;
		}
		return this;
	}

}
