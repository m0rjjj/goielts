package au.com.goielts.dao;

import au.com.goielts.model.UploadedFile;

public interface UploadedFileDao {
	
	UploadedFile findById(int id);

	void save(UploadedFile uploadedFile);

	int deleteById(int id);

}
