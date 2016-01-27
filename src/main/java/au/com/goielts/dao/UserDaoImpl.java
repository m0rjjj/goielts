package au.com.goielts.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.User;
 
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
	 
    public void save(User user) {
        persist(user);
    }
     
    public User findById(int id) {
        return getByKey(id);
    }
 
    public User findByEmail(String email) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("email", email));
        return (User) crit.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<User> findAll(){
    	Criteria criteria = createEntityCriteria();
        return (List<User>) criteria.list();
    }
    
    public int deleteById(int id) {
        Query query = getSession().createSQLQuery("delete from User where id = :id");
        query.setInteger("id", id);
        return query.executeUpdate();
    }
}