package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.User;

public interface UserDao {
	 
    void save(User user);
     
    User findById(int id);
     
    User findByEmail(String email);
    
    List<User> findAll();
    
    int deleteById(int id);
     
}