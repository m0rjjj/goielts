package au.com.goielts.services;

import au.com.goielts.model.StudentProfile;

public interface StudentProfileService {

	void save(StudentProfile profile);
    
	 int deleteById(int id);
}
