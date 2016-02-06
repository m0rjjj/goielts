package au.com.goielts.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.goielts.dao.UploadedFileDao;
import au.com.goielts.model.UploadedFile;

@Service("uploadedFileService")
@Transactional
public class UploadedFileServiceImpl implements UploadedFileService{

	@Autowired UploadedFileDao dao;
	
	@Override
	public UploadedFile findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void save(UploadedFile uploadedFile) {
		dao.save(uploadedFile);
	}

	@Override
	public int deleteById(int id) {
		return dao.deleteById(id);
		
	}
	
	@Override
	public void update(UploadedFile uploadedFile) {
		UploadedFile entity = dao.findById(uploadedFile.getId());
		if(entity!=null){
			BeanUtils.copyProperties(uploadedFile, entity);
		}
	}

}
