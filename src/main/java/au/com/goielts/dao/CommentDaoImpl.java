package au.com.goielts.dao;

import org.springframework.stereotype.Repository;

import au.com.goielts.model.Comment;

@Repository("commentDao")
public class CommentDaoImpl extends AbstractDao<Integer, Comment> implements CommentDao {
	
	@Override
	public Comment findById(int id) {
		return getByKey(id);
	}

	@Override
	public void save(Comment comment) {
		persist(comment);
	}
}
