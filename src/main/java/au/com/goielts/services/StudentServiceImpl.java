package au.com.goielts.services;

import java.util.List;

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
	public List<Student> findAllByTerm(String term) {
		return dao.findAllByTerm(term);
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

	@Override
	public void createProfile(int id) {
		dao.createProfile(id);
	}
	
	@Override
	public void updateProfile(int id, Student student){
		Student entity = dao.findById(id);
		if(entity!=null){
			entity.setFirstName(student.getFirstName());
			entity.setLastName(student.getLastName());
			entity.setEmail(student.getEmail());
			entity.setPhone(student.getPhone());
			entity.setAbout(student.getAbout());
			entity.setAddress(student.getAddress());
		}
	}

}
