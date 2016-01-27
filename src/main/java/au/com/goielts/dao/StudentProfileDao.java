package au.com.goielts.dao;

import au.com.goielts.model.StudentProfile;

public interface StudentProfileDao {
	
	 void save(StudentProfile profile);
     
	 int deleteById(int id);
}
