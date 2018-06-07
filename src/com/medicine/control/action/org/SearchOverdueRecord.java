package com.medicine.control.action.org;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.medicine.view.ShowProfiteTableModel;

public class SearchOverdueRecord implements FunctionAction {

	private JPanel workPanel = null;
	
	// ±í¸ñ
	private JScrollPane tableScrollPane = null;
	private ShowProfiteTableModel tableModel = null;
	private JTable myTable = null;
	
	@Override
	public void execute(JPanel workPanel) {
		// TODO Auto-generated method stub
		this.workPanel = workPanel;
		this.workPanel.removeAll();
		this.init();
		this.workPanel.repaint();
	}
	
	private void init() {
		this.workPanel.setLayout(new BorderLayout());;
		
		String sql = "SELECT * FROM overdueRecord";
		
		this.tableModel = new ShowProfiteTableModel(sql, null);
		this.myTable = new JTable(this.tableModel);
		this.myTable.setRowHeight(25);
		this.myTable.setGridColor(Color.RED);
		this.tableScrollPane = new JScrollPane(this.myTable);
		
		this.workPanel.add(this.tableScrollPane);
	}
}
