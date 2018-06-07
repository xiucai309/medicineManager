package com.medicine.control.action.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.medicine.model.util.DBUtil;
import com.medicine.view.ShowProfiteTableModel;

public class SellMedicineAction implements FunctionAction, ActionListener {

	private JPanel workPanel = null;

	private JPanel northPanel = null;
	private JLabel medNameLabel = null;
	private JTextField medTField = null;
	private JButton searchButton = null;

	private JPanel southPanel = null;
	private JLabel sellNumberLabel = null;
	private JTextField sellNumberTField = null;
	private JButton confirmButton = null;

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

		this.workPanel.setLayout(new BorderLayout());

		this.northPanel = new JPanel();
		this.medNameLabel = new JLabel("������ҩƷ���ƣ�");
		this.medTField = new JTextField(15);
		this.searchButton = new JButton("�鿴");
		this.northPanel.add(this.medNameLabel);
		this.northPanel.add(this.medTField);
		this.northPanel.add(this.searchButton);

		this.tableModel = new ShowProfiteTableModel();
		this.myTable = new JTable(this.tableModel);
		this.myTable.setRowHeight(25);
		this.myTable.setGridColor(Color.RED);
		this.tableScrollPane = new JScrollPane(this.myTable);

		this.southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.sellNumberLabel = new JLabel("ҩƷ������");
		this.sellNumberTField = new JTextField(10);
		this.confirmButton = new JButton("ȷ�ϳ���");
		this.southPanel.add(this.sellNumberLabel);
		this.southPanel.add(this.sellNumberTField);
		this.southPanel.add(this.confirmButton);

		this.workPanel.add(this.northPanel, BorderLayout.NORTH);
		this.workPanel.add(this.tableScrollPane, BorderLayout.CENTER);
		this.workPanel.add(this.southPanel, BorderLayout.SOUTH);

		// �¼�ע��
		this.searchButton.addActionListener(this);
		this.confirmButton.addActionListener(this);

		this.refreshTable();
	}

	private boolean sellHandle(String medId, int number) {
		// �������ݿ�洢����
		boolean flage = false;
		DBUtil dbUtil = new DBUtil();
		String sql = "{call sell_procedure(?, ?)}";
		String[] parse = new String[] { medId, number + "" };
		if (0 != dbUtil.updateExe(sql, parse)) {
			flage = true;
		}
		return flage;
	}

	private void refreshTable() {
		String sql = "SELECT medId as 'ҩƷID', "
				+ "medName as 'ҩƷ����', approvalNumber as 'ҩƷ��׼�ĺ�', "
				+ " isOTC as '�Ƿ�ΪOTC��ҩ', introduce as 'ҩƷ����', price as 'ҩƷ����(Ԫ)', "
				+ "leastNumber as '��������' "
				+ " FROM sellTable WHERE medName LIKE ?";
		String parse[] = new String[] { "%" + this.medTField.getText() + "%" };

		this.myTable.setModel(new ShowProfiteTableModel(sql, parse));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object souce = e.getSource();
		if (souce == this.searchButton) {
			this.refreshTable();
		} else if (souce == this.confirmButton) {
			// �ж��Ƿ�ѡ��ҩƷ
			if (1 != this.myTable.getSelectedRowCount()) {
				JOptionPane
						.showMessageDialog(this.workPanel, "���ڱ����ѡ������۵�һ��ҩƷ",
								"δ��ȷѡ��ҩƷ", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String medId = this.myTable.getValueAt(
					this.myTable.getSelectedRow(), 0).toString();
			int canSellNumber = Integer.parseInt(this.myTable.getValueAt(
					this.myTable.getSelectedRow(), 6).toString());

			// �ж������ҩƷ�����Ƿ�Ϸ�
			int number = 0;
			try {
				number = Integer.parseInt(this.sellNumberTField.getText());
				if (number <= 0) {
					JOptionPane.showMessageDialog(this.workPanel, "������������Ƿ�",
							"��ʾ", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (number > canSellNumber) {
					JOptionPane.showMessageDialog(this.workPanel,
							"������������ҩƷ�����", "��ʾ", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this.workPanel, "������������Ƿ�", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (this.sellHandle(medId, number)) {
				JOptionPane.showMessageDialog(this.workPanel, "�ɹ��۳�ҩƷ", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ����ʧ��", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
			}
			this.refreshTable();
		}
	}
}
