package au.com.goielts.services;

import au.com.goielts.model.Comment;

public interface CommentService {
	
	Comment findById(int id);

	void save(Comment comment);
	
	
}
