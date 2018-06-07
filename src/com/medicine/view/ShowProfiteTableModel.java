package com.medicine.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import com.medicine.model.util.DBUtil;


public class ShowProfiteTableModel extends AbstractTableModel {

	private LinkedList<String> columNames = null;
	private LinkedList<LinkedList<String>> rowData = null;
	
	public ShowProfiteTableModel() {
		this.columNames = new LinkedList<String>();
		this.rowData = new LinkedList<LinkedList<String>>();
	}
	
	public ShowProfiteTableModel(String sql, String[] parse) {
		this.columNames = new LinkedList<String>();
		this.rowData = new LinkedList<LinkedList<String>>();

		DBUtil dbUtil = new DBUtil();
		ResultSet res = dbUtil.queryExe(sql, parse);
		this.fillData(res);
		dbUtil.closeConnection();
	}
	
	private void fillData(ResultSet res) {
		ResultSetMetaData rsmd;
		try {
			rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			// 设置列名
			for (int i = 0; i < colNum; i++) {
				this.columNames.add(rsmd.getColumnName(i + 1));
			}
			// 读出行数据
			while (res.next()) {
				LinkedList<String> row = new LinkedList<String>();
				for (int i = 0; i < colNum; i++) {
					if (res.getObject(i + 1) != null) {
						row.add(res.getObject(i + 1).toString());
					} else {
						row.add("0");
					}
				}
				this.rowData.add(row);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getRowCount() {
		return this.rowData.size();
	}

	@Override
	public int getColumnCount() {
		return this.columNames.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return this.columNames.get(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return this.rowData.get(rowIndex).get(columnIndex);
	}

}
