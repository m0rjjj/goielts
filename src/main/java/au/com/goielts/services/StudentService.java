package au.com.goielts.services;

import java.util.List;

import au.com.goielts.model.Student;

public interface StudentService {

	void save(Student profile);
    
	int deleteById(int id);

	StudentService with(String name);

	Student findById(int id);

	void createProfile(int id);

	List<Student> findAllByTerm(String term);

	void updateProfile(int id, Student student);
}
