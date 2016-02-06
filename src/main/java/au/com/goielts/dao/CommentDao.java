package au.com.goielts.dao;

import au.com.goielts.model.Comment;

public interface CommentDao {

	Comment findById(int id);

	void save(Comment comment);

}
