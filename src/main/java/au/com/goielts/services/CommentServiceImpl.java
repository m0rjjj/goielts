package au.com.goielts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.CommentDao;
import au.com.goielts.model.Comment;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService{

	@Autowired CommentDao dao;
	
	@Override
	public Comment findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void save(Comment comment) {
		dao.save(comment);
	}

}
