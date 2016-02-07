package au.com.goielts.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("type"));
        return (List<Role>) criteria.list();
	}

	@Override
	public Role findById(int id) {
		return getByKey(id);
	}

}
