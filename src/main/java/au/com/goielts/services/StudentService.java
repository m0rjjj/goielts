package au.com.goielts.services;

import au.com.goielts.model.Student;

public interface StudentService {

	void save(Student profile);
    
	int deleteById(int id);

	StudentService with(String name);

	Student findById(int id);
}
