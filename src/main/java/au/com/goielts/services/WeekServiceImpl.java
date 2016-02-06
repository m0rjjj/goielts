package au.com.goielts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.WeekDao;
import au.com.goielts.model.Week;

@Service("weekService")
@Transactional
public class WeekServiceImpl implements WeekService{

	@Autowired WeekDao dao;
	
	@Override
	public Week findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void save(Week week) {
		dao.save(week);
	}
	
	@Override
	public void update(Week week) {
		Week entity = dao.findById(week.getId());
		if(entity!=null){
			entity.setNumber(week.getNumber());
			entity.setDescription(week.getDescription());
//			if(week.getMaterials()!=null){
//				entity.setMaterials(week.getMaterials());
//			}
		}
	}
	
	@Override
	public void merge(Week week){
		dao.merge(week);
	}

	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

}
