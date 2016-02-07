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
	private boolean withStudents = false;
	private boolean withTeachers = false;

	@Override
	public Course findById(int id) {
		return buildEntity(dao.findById(id));
	}
	
	@Override
	public Course findByTaskId(int id){
		return dao.findByTaskId(id);
	}

	private Course buildEntity(Course course) {
		if(withWeeks){
			Hibernate.initialize(course.getWeeks());
		}
		if(withStudents){
			Hibernate.initialize(course.getStudents());
		}
		if(withTeachers){
			Hibernate.initialize(course.getTeachers());
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
	public int deleteById(int id) {
		return dao.deleteById(id);
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
	public void merge(Course course){
		dao.merge(course);
	}
	
	@Override
	public CourseService with(String name){
		switch (name) {
		case "weeks":
			withWeeks = true;
			break;
		case "students":
			withStudents = true;
			break;
		case "teachers":
			withTeachers = true;
			break;

		default:
			break;
		}
		return this;
	}

}
