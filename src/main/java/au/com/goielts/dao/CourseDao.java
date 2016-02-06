package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.Course;

public interface CourseDao {
	Course findById(int id);
	 
    void save(Course course);
     
    void delete(String id);
     
    List<Course> findAll();

	int countByStudentId(int id);
}
