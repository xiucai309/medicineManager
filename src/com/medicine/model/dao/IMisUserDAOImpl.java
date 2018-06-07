package com.medicine.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.medicine.model.entity.MisUser;
import com.medicine.model.util.DBUtil;

public class IMisUserDAOImpl implements IMisUserDAO {

	@Override
	public boolean add(MisUser misUser) {
		boolean flag = false;
		DBUtil dbUtil = new DBUtil();
		String sql = "INSERT INTO misUser VALUES(?,?,?,?,?,?)";
		String parse[] = new String[] { misUser.getUserId(),
				misUser.getUserName(), misUser.getUserPwd(),
				misUser.getUserMemo(), misUser.getRoleId() };
		if (1 == dbUtil.updateExe(sql, parse)) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean remove(String Id) {
		boolean flag = false;
		DBUtil dbUtil = new DBUtil();
		String sql = "DELETE FROM misUser WHERE userId = ?";
		String parse[] = new String[] { Id };
		if (1 == dbUtil.updateExe(sql, parse)) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean modify(MisUser misUser) {
		boolean flage = false;
		DBUtil dbUtil = new DBUtil();
		String sql = "UPDATE misUser SET userId = ?, userName = ?,"
				+ "userPwd = ?, userMemo = ?, roleId = ?," + "addressId = ?";
		String parse[] = new String[] { misUser.getUserId(),
				misUser.getUserName(), misUser.getUserPwd(),
				misUser.getUserMemo(), misUser.getRoleId() };
		if (1 == dbUtil.updateExe(sql, parse)) {
			flage = true;
		}
		return flage;
	}

	@Override
	public MisUser findById(String Id) {
		MisUser misUser = null;
		DBUtil dbUtil = new DBUtil();
		String sql = "SELECT * FROM misUser WHERE userId = ?";
		String parse[] = new String[] { Id };
		ResultSet res = null;
		res = dbUtil.queryExe(sql, parse);
		try {
			if (res.next()) {
				misUser = new MisUser();
				misUser.setUserId(res.getString(1));
				misUser.setUserName(res.getString(2));
				misUser.setUserPwd(res.getString(3));
				misUser.setUserMemo(res.getString(4));
				misUser.setRoleId(res.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection();
		}
		return misUser;
	}

	@Override
	public List<MisUser> findByLike(MisUser misUser) {
		List<MisUser> list = new LinkedList<MisUser>();
		String sql = "SELECT * FROM misUser WHERE 1=1 ";
		String where = "";
		if (misUser.getUserId() != null)
			where += "AND userId " + "LIKE ? ";
		if (misUser.getUserName() != null)
			where += "AND userName " + "LIKE ? ";
		if (misUser.getUserPwd() != null)
			where += "AND userPwd " + "LIKE ? ";
		if (misUser.getUserMemo() != null)
			where += "AND userMemo " + "LIKE ? ";
		if (misUser.getRoleId() != null)
			where += "AND roleId " + "LIKE ? ";

		sql += where;

		String[] parse = new String[] { "%" + misUser.getUserId() + "%",
				"%" + misUser.getUserName() + "%",
				"%" + misUser.getUserPwd() + "%",
				"%" + misUser.getUserMemo() + "%",
				"%" + misUser.getRoleId() + "%" };

		DBUtil dbUtil = new DBUtil();
		ResultSet res = dbUtil.queryExe(sql, parse);

		try {
			while (res.next()) {
				MisUser temp = new MisUser();
				temp.setUserId(res.getString(1));
				temp.setUserName(res.getString(2));
				temp.setUserPwd(res.getString(3));
				temp.setUserMemo(res.getString(4));
				temp.setRoleId(res.getString(5));

				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection();
		}
		return list;
	}
}
