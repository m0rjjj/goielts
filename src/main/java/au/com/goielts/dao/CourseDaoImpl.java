package au.com.goielts.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.Course;

@Repository("courseDao")
public class CourseDaoImpl extends AbstractDao<Integer, Course> implements CourseDao{

	@Override
	public Course findById(int id) {
		return getByKey(id);
	}

	@Override
	public void save(Course course) {
		persist(course);
		
	}

	@Override
	public void delete(String id) {
		Query query = getSession().createSQLQuery("delete from Course where id = :id");
        query.setString("id", id);
        query.executeUpdate();
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Course> findAll() {
		Criteria criteria = createEntityCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  
        return (List<Course>) criteria.list();
	}
	
	@Override
	public int countByStudentId(int id) {
		int count = ((BigInteger)getSession().
				createSQLQuery("select count(*) from course_student where student_id = :id").setInteger("id", id).uniqueResult()).intValue();
		return count;
	}
	
	@Override 
	public Course findByTaskId(int id){
		Query query = getSession().createQuery("select t.course from Task t where t.id=:id");
		query.setInteger("id", id);
		return (Course)query.uniqueResult();
	}

}
