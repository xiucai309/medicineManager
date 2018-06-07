package com.medicine.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.medicine.model.entity.Medicine;
import com.medicine.model.util.DBUtil;

public class IMedicineDAOImpl implements IMedicineDAO {

	@Override
	public boolean addMedicine(Medicine medicine) {
		boolean flage = false;

		String sql = "INSERT INTO medicine values(?,?,?,?,?,?,?)";
		String[] parse = new String[] { medicine.getMedName(),
				medicine.getShelfLife(), medicine.isOTC() ? "1" : "0",
				medicine.getApprovalNumber(),		
				medicine.getIntroduce(),
				medicine.getStroageWay(),
				medicine.getPrice() + ""
		};
		
		DBUtil dbUtil = new DBUtil();
		if(1 == dbUtil.updateExe(sql, parse)) {
			flage = true;
		}
		return flage;
	}

	@Override
	public Medicine findByMedicineName(String name) {
		Medicine medicine = null;
		String sql = "SELECT * FROM medicine WHERE medName = ?";
		String[] parse = new String[] { name };

		DBUtil dbUtil = new DBUtil();
		ResultSet res = dbUtil.queryExe(sql, parse);
		try {
			if(res.next()) {
				medicine = new Medicine();
				medicine.setMedId(res.getString(1));
				medicine.setMedName(res.getString(2));
				medicine.setShelfLife(res.getInt(3) + "");
				medicine.setOTC(res.getBoolean(4));
				medicine.setApprovalNumber(res.getString(5));
				medicine.setIntroduce(res.getString(6));
				medicine.setStroageWay(res.getString(7));
				medicine.setPrice(res.getDouble(8) + "");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection();
		}
		return medicine;
	}

	@Override
	public boolean modifyMedicine(Medicine medicine) {
		boolean flage = false;
		String sql = "UPDATE medicine SET " + "ShelfLife = ?, "
				+ "isOTC = ?, "
				+ "introduce = ?,  price = ?, storageWay = ? "
				+ " WHERE medId = ?";
		String[] parse = new String[]{
			medicine.getShelfLife(), medicine.isOTC()? "1" : "0",
		    medicine.getIntroduce(),medicine.getPrice(), medicine.getStroageWay(),
			medicine.getMedId()
		};
		
		System.out.println(medicine.getPrice());
		DBUtil dbUtil = new DBUtil();
		if(1 == dbUtil.updateExe(sql, parse)) {
			flage = true;
		}
		
		return flage;
	}

	@Override
	public Medicine findByMedicineApprovalNumber(String approvalNumber) {
		Medicine medicine = null;
		String sql = "SELECT * FROM medicine WHERE approvalNumber = ?";
		String[] parse = new String[] { approvalNumber };

		DBUtil dbUtil = new DBUtil();
		ResultSet res = dbUtil.queryExe(sql, parse);
		try {
			if(res.next()) {
				medicine = new Medicine();
				medicine.setMedId(res.getString(1));
				medicine.setMedName(res.getString(2));
				medicine.setShelfLife(res.getInt(3) + "");
				medicine.setOTC(res.getBoolean(4));
				medicine.setApprovalNumber(res.getString(5));
				medicine.setIntroduce(res.getString(6));
				medicine.setStroageWay(res.getString(7));
				medicine.setPrice(res.getDouble(8) + "");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection();
		}
		return medicine;
	}

}
