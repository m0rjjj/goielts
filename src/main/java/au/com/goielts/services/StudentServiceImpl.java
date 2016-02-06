package au.com.goielts.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.StudentDao;
import au.com.goielts.model.Student;

@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService{

	private boolean withCourses = false;
	
	@Autowired
	StudentDao dao;
	
	@Override
	public Student findById(int id) {
		return buildEntity(dao.findById(id));
	}
	
	private Student buildEntity(Student student) {
		if(withCourses){
			Hibernate.initialize(student.getCourses());
		}
		return student;
	}

	@Override
	public void save(Student profile) {
		dao.save(profile);
	}

	@Override
	public int deleteById(int id) {
		return dao.deleteById(id);
	}
	
	@Override
	public StudentService with(String name){
		switch (name) {
		case "courses":
			withCourses = true;
			break;

		default:
			break;
		}
		
		return this;
	}

}
