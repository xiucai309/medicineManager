package com.medicine.control.action.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.medicine.model.dao.IMedicineDAO;
import com.medicine.model.dao.IMedicineDAOImpl;
import com.medicine.model.entity.Medicine;
import com.medicine.view.ShowProfiteTableModel;
import com.medicine.view.UniversalTableModel;

public class SearchProfiteAction implements FunctionAction, ActionListener {

	private JPanel workPanel = null;

	private JPanel northPanel = null;
	private JLabel medNameLabel = null;
	private JTextField medNameTField = null;
	private JComboBox typeComboBox = null;
	private JLabel dateLabel = null;
	private JTextField dateTField = null;

	private JButton searchButton = null;

	// 表格
	private JScrollPane tableScrollPane = null;
	private ShowProfiteTableModel tableModel = null;
	private JTable myTable = null;

	@Override
	public void execute(JPanel workPanel) {
		this.workPanel = workPanel;
		this.workPanel.removeAll();
		this.init();
		this.workPanel.repaint();
	}

	private void init() {
		this.workPanel.setBackground(Color.WHITE);
		this.workPanel.setLayout(new BorderLayout());

		this.medNameLabel = new JLabel("药品名称：");
		this.medNameTField = new JTextField(15);
		this.medNameTField.setToolTipText("药品名为空则查询所有药品");
		this.typeComboBox = new JComboBox<String>(new String[] { "按年统计",
		 "按月统计", "按日统计" });
		this.dateLabel = new JLabel("日期：");
		this.dateTField = new JTextField(12);
		this.dateTField.setToolTipText("yyyy");
		this.searchButton = new JButton("利润查询");

		this.northPanel = new JPanel();
		this.northPanel.add(this.medNameLabel);
		this.northPanel.add(this.medNameTField);
		this.northPanel.add(this.dateLabel);
		this.northPanel.add(this.dateTField);
		this.northPanel.add(this.typeComboBox);
		this.northPanel.add(this.searchButton);

		this.tableModel = new ShowProfiteTableModel();
		this.myTable = new JTable(this.tableModel);
		this.myTable.setRowHeight(25);
		this.myTable.setGridColor(Color.RED);
		this.tableScrollPane = new JScrollPane(this.myTable);

		this.workPanel.add(this.tableScrollPane, BorderLayout.CENTER);
		this.workPanel.add(this.northPanel, BorderLayout.NORTH);

		// 事件注册
		this.searchButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object souce = e.getSource();
		if (souce == this.searchButton) {

			Date searchDate = null;
			SimpleDateFormat dateFormat = null;
			// 药品利润查询
			String sql = "SELECT medicine.medId as '药品ID', "
					+ " medicine.medName as '药品名称', "
					+ " medicine.approvalNumber as '药品批准文号', "
					+ " medicine.introduce as '药品介绍', "
					+ " medicine.isOTC as '是否为OTC用药', "
					+ " temp.profite as '利润统计(元)' "
					+ " FROM medicine LEFT JOIN "
					+ " (SELECT"
					+ " medicine.medId as 'medId', "
					+ " SUM((sellRecord.sellPrice - storageRecord.price) * sellRecord.sellNumber) as 'profite' "
					+ " FROM medicine, storageRecord, sellRecord "
					+ " WHERE medicine.medId = storageRecord.medId AND "
					+ " storageRecord.storageId = sellRecord.storageId AND "
					+ " CONVERT(CHAR(10),sellRecord.sellDate, 120) LIKE ? "
					+ " GROUP BY medicine.medId) as temp " + " ON "
					+ " medicine.medId = temp.medId " + "WHERE "
					+ " medicine.medName LIKE ?";
			String[] parse = null;

			// 按要求查询.
			try {
				// 根据查询方式确定日期格式要求
				if (0 == this.typeComboBox.getSelectedIndex()) {
					dateFormat = new SimpleDateFormat("yyyy");
				} else if (1 == this.typeComboBox.getSelectedIndex()) {
					dateFormat = new SimpleDateFormat("yyyy-mm");
				} else if (2 == this.typeComboBox.getSelectedIndex()) {
					dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				}

				// 判断日期格式是否匹配(不匹配将抛出ParseException异常)
				searchDate = dateFormat.parse(this.dateTField.getText());
				parse = new String[] { dateFormat.format(searchDate) + "%",
						this.medNameTField.getText() + "%" };

				// 将查询方式传入TableModel并将结果显示在表格中
				this.myTable.setModel(new ShowProfiteTableModel(sql, parse));

			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.workPanel, "日期格式错误，正确格式:"
						+ dateFormat.toPattern(), "提示",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
