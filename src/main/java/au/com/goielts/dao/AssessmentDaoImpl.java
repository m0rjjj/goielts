package au.com.goielts.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.Assessment;

@Repository("assessmentDao")
public class AssessmentDaoImpl extends AbstractDao<Integer, Assessment> implements AssessmentDao{

	@Override
	public Assessment findById(int id) {
		return getByKey(id);
	}

	@Override
	public void save(Assessment assessment) {
		persist(assessment);
		
	}

	@Override
	public void delete(String id) {
		Query query = getSession().createSQLQuery("delete from Assessment where id = :id");
        query.setString("id", id);
        query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assessment> findAll() {
		Criteria criteria = createEntityCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  
        return (List<Assessment>) criteria.list();
	}

	@Override
	public Assessment findByTaskIdAndStudentId(int taskId, int studentId) {
		Criteria crit = createEntityCriteria();
        crit.add(Restrictions.sqlRestriction("{alias}.task_id = ?", taskId, IntegerType.INSTANCE));
        crit.add(Restrictions.sqlRestriction("{alias}.student_id = ?", studentId, IntegerType.INSTANCE));
        return (Assessment) crit.uniqueResult();
	}

}
