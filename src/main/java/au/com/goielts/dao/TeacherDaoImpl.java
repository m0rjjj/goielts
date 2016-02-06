package au.com.goielts.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

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

}
