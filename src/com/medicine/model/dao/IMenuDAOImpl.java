package com.medicine.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.medicine.model.entity.MenuBean;
import com.medicine.model.util.DBUtil;

public class IMenuDAOImpl implements IMenuDAO {

	@Override
	public List<MenuBean> queryBySql(String sql, String[] parse) {
		List<MenuBean> list = new LinkedList<MenuBean>();
		DBUtil dbUtil = new DBUtil();
		ResultSet res = dbUtil.queryExe(sql, parse);
		try {
			while (res.next()) {
				MenuBean temp = new MenuBean();
				temp.setMenuId(res.getString(1));
				temp.setMenuName(res.getString(2));
				temp.setMenuMemo(res.getString(3));
				temp.setFunctionClass(res.getString(4));
				
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection();
		}
		return list;
	}

	@Override
	public MenuBean findByMenuId(String menuId) {
		MenuBean menuBean = new MenuBean();
		DBUtil dbUtil = new DBUtil();
		String sql = "SELECT * FROM menu WHERE menuId = ?";
		String[] parse = new String[]{
			menuId
		};
		ResultSet res = dbUtil.queryExe(sql, parse);
		try {
			if (res.next()) {
				menuBean.setMenuId(res.getString(1));
				menuBean.setMenuName(res.getString(2));
				menuBean.setMenuMemo(res.getString(3));
				menuBean.setFunctionClass(res.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection();
		}
		return menuBean;
	}

}
