package com.medicine.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowMonthProfiteDialog extends JDialog {

//	public static void main(String[] args) {
//		new ShowMonthProfiteDialog(null, "1", "2017").setVisible(true);
//	}
	private JPanel bodyPanel = null;
	private ShowProfiteTableModel tableModel = null;
	private JTable myTable = null;
	private JScrollPane tableScrollPane = null;

	private String medId = null;
	private String year = null;

	public ShowMonthProfiteDialog(Frame parent, String medId, String year) {
		super(parent, false);
		this.medId = medId;
		this.year = year;
		this.init();
		this.pack();
	}

	private void init() {
		this.bodyPanel = (JPanel) this.getContentPane();

		this.bodyPanel.setBackground(Color.WHITE);
		this.bodyPanel.setLayout(new BorderLayout());

		String sql = "SELECT '1' as '月份',medicine.medId as '药品ID', "
				+ "medicine.medName as '药品名称', "
				+ "medicine.approvalNumber as '药品批准文号', "
				+ "temp.profite as '月份利润' "
				+ "FROM medicine LEFT JOIN "
				+ "(SELECT"
				+ " medicine.medId as 'medId', "
				+ " sum((sellRecord.sellPrice - storageRecord.price) * sellRecord.sellNumber) as 'profite' "
				+ " FROM medicine, storageRecord, sellRecord "
				+ " WHERE medicine.medId = storageRecord.medId AND "
				+ " storageRecord.storageId = sellRecord.storageId AND "
				+ " convert(CHAR(10),sellRecord.sellDate, 120) LIKE '"
				+ this.year + "-01%'" + " GROUP BY medicine.medId) as temp "
				+ " ON " + " medicine.medId = temp.medId " + "WHERE "
				+ " medicine.medId = '" + this.medId + "'";
		for (int i = 2; i < 13; i++) {
			String temp = "UNION SELECT '" + i + "' as '月份', medicine.medId as '药品ID', "
					+ "medicine.medName as '药品名称', "
					+ "medicine.approvalNumber as '药品批准文号', "
					+ "temp.profite as '月份利润' "
					+ "FROM medicine LEFT JOIN "
					+ "(SELECT"
					+ " medicine.medId as 'medId', "
					+ " SUM((sellRecord.sellPrice - storageRecord.price) * sellRecord.sellNumber) as 'profite' "
					+ " FROM medicine, storageRecord, sellRecord "
					+ " WHERE medicine.medId = storageRecord.medId AND "
					+ " storageRecord.storageId = sellRecord.storageId AND "
					+ " CONVERT(CHAR(10),sellRecord.sellDate, 120) LIKE '"
					+ this.year + "-" + i + "%'"
					+ " GROUP BY medicine.medId) as temp " + " ON "
					+ " medicine.medId = temp.medId " + "WHERE "
					+ " medicine.medId = '" + this.medId + "'";
			sql += temp;
		}
		this.tableModel = new ShowProfiteTableModel(sql, null);
		this.myTable = new JTable(this.tableModel);
		this.myTable.setRowHeight(25);
		this.myTable.setGridColor(Color.RED);
		this.tableScrollPane = new JScrollPane(this.myTable);

		this.bodyPanel.add(this.tableScrollPane);
	}
}
