package au.com.goielts.dao;

import au.com.goielts.model.Student;

public interface StudentDao {

	void save(Student profile);

	int deleteById(int id);

	Student findById(int id);
}
