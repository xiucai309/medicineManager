package com.medicine.model.dao;

import com.medicine.model.entity.*;

import java.util.List;

public interface IMenuDAO {
	public List<MenuBean> queryBySql(String sql, String[] parse);
	public MenuBean findByMenuId(String menuId);
}
