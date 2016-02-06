package au.com.goielts.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import au.com.goielts.model.Week;
@Repository("weekDao")
public class WeekDaoImpl extends AbstractDao<Integer, Week> implements WeekDao {
	
	@Override
	public Week findById(int id) {
		return getByKey(id);
	}

	@Override
	public void save(Week week) {
		persist(week);
	}

	@Override
	public int delete(int id) {
		Query query = getSession().createSQLQuery("delete from Week where id = :id");
        query.setInteger("id", id);
        return query.executeUpdate();
	}
	
	@Override
	public void merge(Week week){
		getSession().merge(week);
	}
}
