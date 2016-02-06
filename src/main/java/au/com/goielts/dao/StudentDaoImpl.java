package au.com.goielts.dao;

import org.hibernate.Query;
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

}
