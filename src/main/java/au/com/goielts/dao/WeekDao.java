package au.com.goielts.dao;

import au.com.goielts.model.Week;

public interface WeekDao {

	Week findById(int id);

	void save(Week week);

	int delete(int id);

	void merge(Week week);

}
