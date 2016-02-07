package au.com.goielts.services;

import java.util.List;

import au.com.goielts.model.Role;

public interface RoleService {
	
	public List<Role> findAll();
	
	public Role findById(int id);
}
