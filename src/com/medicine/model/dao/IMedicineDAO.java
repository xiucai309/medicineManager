package com.medicine.model.dao;

import com.medicine.model.entity.Medicine;

public interface IMedicineDAO {
	public boolean addMedicine(Medicine medicine);
	
	public Medicine findByMedicineName(String name);
	
	public Medicine findByMedicineApprovalNumber(String approvalNumber);
	
	public boolean modifyMedicine(Medicine medicine);
}
