package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.Teacher;

public interface TeacherDao {

	void save(Teacher teacher);

	int deleteById(int id);

	Teacher findById(int id);

	void createProfile(int id);

	List<Teacher> findAllByTerm(String term);
}
