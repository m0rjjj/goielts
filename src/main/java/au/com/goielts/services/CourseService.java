package au.com.goielts.services;

import java.util.List;

import au.com.goielts.model.Course;

public interface CourseService {

	Course findById(int id);

	void save(Course course);

	void update(Course course);

	List<Course> findAll();

	int countByStudentId(int id);

	CourseService with(String name);

	Course findByTaskId(int id);

	void merge(Course course);

	int deleteById(int id);

}
