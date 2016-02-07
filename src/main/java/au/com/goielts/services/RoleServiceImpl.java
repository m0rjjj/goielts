package au.com.goielts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.RoleDao;
import au.com.goielts.model.Role;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired RoleDao dao;
	
	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public Role findById(int id) {
		return dao.findById(id);
	}

}
