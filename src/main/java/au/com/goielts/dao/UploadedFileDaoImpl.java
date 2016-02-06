package au.com.goielts.dao;

import org.hibernate.Query;
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
	public int deleteById(int id) {
		Query query = getSession().createSQLQuery("delete from materials where file_id = :id");
        query.setInteger("id", id);
        return query.executeUpdate();
	}

}
