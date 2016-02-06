package au.com.goielts.services;

import au.com.goielts.model.Teacher;

public interface TeacherService {

	void save(Teacher teacher);
    
	int deleteById(int id);

	TeacherService with(String name);

	Teacher findById(int id);
}
