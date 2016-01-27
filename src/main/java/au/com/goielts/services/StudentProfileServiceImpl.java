package au.com.goielts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.StudentProfileDao;
import au.com.goielts.model.StudentProfile;

@Service("studentProfileService")
@Transactional
public class StudentProfileServiceImpl implements StudentProfileService{

	@Autowired
	StudentProfileDao dao;
	
	@Override
	public void save(StudentProfile profile) {
		dao.save(profile);
		
	}

	@Override
	public int deleteById(int id) {
		return dao.deleteById(id);
	}

}
