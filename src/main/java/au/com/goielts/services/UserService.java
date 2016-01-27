package au.com.goielts.services;

import java.util.List;

import au.com.goielts.model.User;

public interface UserService {
	 
    User findById(int id);
     
    User findByEmail(String email);
    
    void save(User user);
    
    void update(User user);
    
    void updateInfo(User user);

	List<User> findAll();
	
	int deleteById(int id);
     
}