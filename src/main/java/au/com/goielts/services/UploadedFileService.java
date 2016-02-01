package au.com.goielts.services;

import au.com.goielts.model.UploadedFile;

public interface UploadedFileService {

	UploadedFile findById(int id);

	void save(UploadedFile uploadedFile);

	void delete(int id);
	
	public void update(UploadedFile uploadedFile);
	
}
