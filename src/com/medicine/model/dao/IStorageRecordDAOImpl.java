package com.medicine.model.dao;

import com.medicine.model.entity.StorageRecord;
import com.medicine.model.util.DBUtil;

public class IStorageRecordDAOImpl implements IStorageRecordDAO {

	@Override
	public boolean addStorageRecord(StorageRecord record) {
		boolean flage = false;
		
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		//String productDate = dateFormat.format(record.getProductDate()).toUpperCase();
	   
	    String sql = "INSERT INTO storageRecord values(?, ?, ?, ?, getDate(), ?, ?)";
		String parse[] = new String[]{
			record.getMedId(), 
			record.getPrice() + "",
			record.getStorageNumber() + "",
			record.getProductDate().toLocaleString(),
			record.getStorageNumber() + "",
			record.getNote()
		};
		//System.out.println("---" + record.getProductDate().toLocaleString());
		DBUtil dbUtil = new DBUtil();
		if(1 == dbUtil.updateExe(sql, parse)) {
			flage = true;
		}
		
		return flage;
	}

}
