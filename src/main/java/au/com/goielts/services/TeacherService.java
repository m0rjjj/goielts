package au.com.goielts.services;

import java.util.List;

import au.com.goielts.model.Teacher;

public interface TeacherService {

	void save(Teacher teacher);
    
	int deleteById(int id);

	TeacherService with(String name);

	Teacher findById(int id);

	void createProfile(int id);

	List<Teacher> findAllByTerm(String term);

	void updateProfile(int id, Teacher teacher);
}
