package au.com.goielts.dao;

import au.com.goielts.model.Teacher;

public interface TeacherDao {

	void save(Teacher teacher);

	int deleteById(int id);

	Teacher findById(int id);
}
