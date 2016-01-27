package au.com.goielts.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.StudentProfile;

@Repository("studentProfileDao")
public class StudentProfileDaoImpl extends AbstractDao<Integer, StudentProfile>  implements StudentProfileDao{

	@Override
	public void save(StudentProfile profile) {
		persist(profile);
		
	}

	@Override
	public int deleteById(int id) {
		Query query = getSession().createSQLQuery("delete from StudentProfile where id = :id");
        query.setInteger("id", id);
        return query.executeUpdate();
	}

}
