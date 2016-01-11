package au.com.goielts.services;

import au.com.goielts.model.User;

public interface UserService {
	 
    User findById(int id);
     
    User findBySso(String sso);
     
}