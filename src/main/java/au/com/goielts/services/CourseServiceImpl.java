package au.com.goielts.services;

import java.util.List;

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

	@Override
	public Course findById(int id) {
		return dao.findById(id);
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

}
