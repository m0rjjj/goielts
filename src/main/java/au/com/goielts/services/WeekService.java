package au.com.goielts.services;

import au.com.goielts.model.Week;

public interface WeekService {

	Week findById(int id);

	void save(Week week);

	int delete(int id);

	void update(Week week);

	void merge(Week week);

}