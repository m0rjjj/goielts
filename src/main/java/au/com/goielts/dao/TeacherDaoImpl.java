package au.com.goielts.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.Student;
import au.com.goielts.model.Teacher;

@Repository("teacherDao")
public class TeacherDaoImpl extends AbstractDao<Integer, Teacher>  implements TeacherDao{

	@Override
	public Teacher findById(int id) {
		return getByKey(id);
	}
	
	@Override
	public void save(Teacher student) {
		persist(student);
		
	}

	@Override
	public int deleteById(int id) {
		Query query = getSession().createSQLQuery("delete from Teacher where id = :id");
        query.setInteger("id", id);
        return query.executeUpdate();
	}
	
	@Override
	public void createProfile(int id) {
		System.out.println("Creating teacher with id "+id);
		Query query = getSession().createSQLQuery("insert into teacher (user_id) values (:puser_id) ");
		query.setInteger("puser_id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> findAllByTerm(String term) {
		Criteria criteria = createEntityCriteria();
		Criterion firstName = Restrictions.like("firstName", term, MatchMode.ANYWHERE);
		Criterion lastName = Restrictions.like("lastName", term, MatchMode.ANYWHERE);
		Criterion email = Restrictions.like("email", term, MatchMode.ANYWHERE);
		criteria.add(Restrictions.or(firstName,lastName,email));
		criteria.addOrder(Order.asc("firstName"));
    	
        return (List<Teacher>) criteria.list();
	}

}
