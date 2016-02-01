package au.com.goielts.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.Task;

@Repository("taskDao")
public class TaskDaoImpl extends AbstractDao<Integer, Task> implements TaskDao{

	@Override
	public Task findById(int id) {
		return getByKey(id);
	}

	@Override
	public void save(Task task) {
		persist(task);
		
	}

	@Override
	public void delete(String id) {
		Query query = getSession().createSQLQuery("delete from Task where id = :id");
        query.setString("id", id);
        query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> findAll() {
		Criteria criteria = createEntityCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  
        return (List<Task>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> findAllByCourseId(int id) {
		Criteria criteria = createEntityCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  
        criteria.add(Restrictions.sqlRestriction("{alias}.course_id = ?",id, IntegerType.INSTANCE));
		return (List<Task>) criteria.list();
	}

}
