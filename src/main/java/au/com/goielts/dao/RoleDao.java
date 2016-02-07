package au.com.goielts.dao;

import java.util.List;

import au.com.goielts.model.Role;

public interface RoleDao {

	public List<Role> findAll();
	
	public Role findById(int id);
}
