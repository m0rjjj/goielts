package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.Course;

public interface CourseDao {
	Course findById(int id);
	 
    void save(Course course);
     
    int deleteById(int id);
     
    List<Course> findAll();

	int countByStudentId(int id);

	Course findByTaskId(int id);

	void merge(Course course);
}
