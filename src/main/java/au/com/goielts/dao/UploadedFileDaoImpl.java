package au.com.goielts.dao;

import org.springframework.stereotype.Repository;

import au.com.goielts.model.UploadedFile;

@Repository("uploadedFileDao")
public class UploadedFileDaoImpl extends AbstractDao<Integer, UploadedFile> implements UploadedFileDao{

	@Override
	public UploadedFile findById(int id) {
		return getByKey(id);
	}

	@Override
	public void save(UploadedFile uploadedFile) {
		persist(uploadedFile);
		
	}

	@Override
	public void delete(int id) {
		delete(id);
	}

}
