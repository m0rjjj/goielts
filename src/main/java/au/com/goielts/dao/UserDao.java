package au.com.goielts.dao;

import au.com.goielts.model.User;

public interface UserDao {
	 
    void save(User user);
     
    User findById(int id);
     
    User findBySSO(String sso);
     
}