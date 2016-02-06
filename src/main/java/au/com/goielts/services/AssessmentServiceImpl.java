package au.com.goielts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.AssessmentDao;
import au.com.goielts.model.Assessment;

@Service("assessmentService")
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

	@Autowired
	AssessmentDao dao;

	@Override
	public Assessment findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void save(Assessment assessment) {

		dao.save(assessment);
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
	}

	@Override
	public List<Assessment> findAll() {
		return dao.findAll();
	}

	@Override
	public Assessment findByTaskIdAndStudentId(int taskId, int studentId) {
		return dao.findByTaskIdAndStudentId(taskId, studentId);
	}

	@Override
	public void update(Assessment assessment) {
		Assessment entity = dao.findById(assessment.getId());
        if (entity!=null){
        	entity.setMark(assessment.getMark());
        	entity.setMarkedFile(assessment.getMarkedFile());
        	entity.setStudent(assessment.getStudent());
        	entity.setTask(assessment.getTask());
        	entity.setTeacher(assessment.getTeacher());
        	entity.setAssessmentFile(assessment.getAssessmentFile());
        }
    }
	
	

}
