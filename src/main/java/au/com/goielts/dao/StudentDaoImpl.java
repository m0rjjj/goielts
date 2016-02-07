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

@Repository("studentDao")
public class StudentDaoImpl extends AbstractDao<Integer, Student>  implements StudentDao{

	@Override
	public Student findById(int id) {
		return getByKey(id);
	}
	
	@Override
	public void save(Student student) {
		persist(student);
		
	}

	@Override
	public int deleteById(int id) {
		Query query = getSession().createSQLQuery("delete from Student where id = :id");
        query.setInteger("id", id);
        return query.executeUpdate();
	}

	@Override
	public void createProfile(int id) {
		System.out.println("Creating student with id "+id);
		Query query = getSession().createSQLQuery("insert into student (user_id, student_id) values (:puser_id, :pstudent_id) ");
		query.setInteger("puser_id", id);
		String studentId = String.format("S%07d", id);
		query.setString("pstudent_id", studentId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> findAllByTerm(String term) {
		Criteria criteria = createEntityCriteria();
		Criterion studentId = Restrictions.like("studentId", term, MatchMode.ANYWHERE);
		Criterion firstName = Restrictions.like("firstName", term, MatchMode.ANYWHERE);
		Criterion lastName = Restrictions.like("lastName", term, MatchMode.ANYWHERE);
		Criterion email = Restrictions.like("email", term, MatchMode.ANYWHERE);
		criteria.add(Restrictions.or(studentId,firstName,lastName,email));
		criteria.addOrder(Order.asc("firstName"));
    	
        return (List<Student>) criteria.list();
	}

}
