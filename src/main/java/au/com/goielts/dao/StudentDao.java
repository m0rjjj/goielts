package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.Student;

public interface StudentDao {

	void save(Student profile);

	int deleteById(int id);

	Student findById(int id);

	void createProfile(int id);

	List<Student> findAllByTerm(String term);
}
