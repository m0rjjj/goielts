package au.com.goielts.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.TeacherDao;
import au.com.goielts.model.Teacher;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService{

	private boolean withCourses = false;
	
	@Autowired
	TeacherDao dao;
	
	@Override
	public Teacher findById(int id) {
		return buildEntity(dao.findById(id));
	}
	
	private Teacher buildEntity(Teacher teacher) {
		if(withCourses){
			Hibernate.initialize(teacher.getCourses());
		}
		return teacher;
	}

	@Override
	public void save(Teacher teacher) {
		dao.save(teacher);
	}

	@Override
	public int deleteById(int id) {
		return dao.deleteById(id);
	}
	
	@Override
	public TeacherService with(String name){
		switch (name) {
		case "courses":
			withCourses = true;
			break;

		default:
			break;
		}
		
		return this;
	}
	
	@Override
	public void createProfile(int id) {
		dao.createProfile(id);
	}

	@Override
	public List<Teacher> findAllByTerm(String term) {
		return dao.findAllByTerm(term);
	}

	@Override
	public void updateProfile(int id, Teacher teacher) {
		Teacher entity = dao.findById(id);
		if(entity!=null){
			entity.setFirstName(teacher.getFirstName());
			entity.setLastName(teacher.getLastName());
			entity.setEmail(teacher.getEmail());
			entity.setPhone(teacher.getPhone());
			entity.setAbout(teacher.getAbout());
			entity.setAddress(teacher.getAddress());
			entity.setQualifications(teacher.getQualifications());
		}
		
	}

}
