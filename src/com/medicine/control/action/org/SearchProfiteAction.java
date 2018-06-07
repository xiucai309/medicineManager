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

	// ���
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

		this.medNameLabel = new JLabel("ҩƷ���ƣ�");
		this.medNameTField = new JTextField(15);
		this.medNameTField.setToolTipText("ҩƷ��Ϊ�����ѯ����ҩƷ");
		this.typeComboBox = new JComboBox<String>(new String[] { "����ͳ��",
		 "����ͳ��", "����ͳ��" });
		this.dateLabel = new JLabel("���ڣ�");
		this.dateTField = new JTextField(12);
		this.dateTField.setToolTipText("yyyy");
		this.searchButton = new JButton("�����ѯ");

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

		// �¼�ע��
		this.searchButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object souce = e.getSource();
		if (souce == this.searchButton) {

			Date searchDate = null;
			SimpleDateFormat dateFormat = null;
			// ҩƷ�����ѯ
			String sql = "SELECT medicine.medId as 'ҩƷID', "
					+ " medicine.medName as 'ҩƷ����', "
					+ " medicine.approvalNumber as 'ҩƷ��׼�ĺ�', "
					+ " medicine.introduce as 'ҩƷ����', "
					+ " medicine.isOTC as '�Ƿ�ΪOTC��ҩ', "
					+ " temp.profite as '����ͳ��(Ԫ)' "
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

			// ��Ҫ���ѯ.
			try {
				// ���ݲ�ѯ��ʽȷ�����ڸ�ʽҪ��
				if (0 == this.typeComboBox.getSelectedIndex()) {
					dateFormat = new SimpleDateFormat("yyyy");
				} else if (1 == this.typeComboBox.getSelectedIndex()) {
					dateFormat = new SimpleDateFormat("yyyy-mm");
				} else if (2 == this.typeComboBox.getSelectedIndex()) {
					dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				}

				// �ж����ڸ�ʽ�Ƿ�ƥ��(��ƥ�佫�׳�ParseException�쳣)
				searchDate = dateFormat.parse(this.dateTField.getText());
				parse = new String[] { dateFormat.format(searchDate) + "%",
						this.medNameTField.getText() + "%" };

				// ����ѯ��ʽ����TableModel���������ʾ�ڱ����
				this.myTable.setModel(new ShowProfiteTableModel(sql, parse));

			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.workPanel, "���ڸ�ʽ������ȷ��ʽ:"
						+ dateFormat.toPattern(), "��ʾ",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
