package au.com.goielts.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.UserDao;
import au.com.goielts.model.User;

 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserDao dao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private boolean withCourses = false;
 
     
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }
 
    public User findById(int id) {
    	return buildUser(dao.findById(id));
    }
 
    public User findByEmail(String email) {
    	//return dao.findByEmail(email);
        return buildUser(dao.findByEmail(email));
    }
    
    private User buildUser(User user){
    	if(withCourses){
    		Hibernate.initialize(user.getCourses());
    	}
    	return user;
    }
    
    public List<User> findAll(){
    	return dao.findAll();
    }
    
    public int deleteById(int id){
    	return dao.deleteById(id);
    }
    
    public void update(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setEmail(user.getEmail());
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setPassword(user.getPassword());
            entity.setState(user.getState());
        }
    }
    
    public void updateInfo(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setEmail(user.getEmail());
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
        }
    }
    
    public UserService with(String name){
    	switch(name){
    		case "courses":
    			this.withCourses = true;
    		break;
    	}
    	return this;
    }
}