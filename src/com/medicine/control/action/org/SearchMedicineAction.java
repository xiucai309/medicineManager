package com.medicine.control.action.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.medicine.model.dao.IMedicineDAO;
import com.medicine.model.dao.IMedicineDAOImpl;
import com.medicine.model.entity.Medicine;
import com.medicine.view.UniversalTableModel;
import com.medicine.view.ShowMedicineInfoDialog;

public class SearchMedicineAction implements FunctionAction, ActionListener,
		MouseListener {

	private IMedicineDAO medicineDAO = null;
	private JPanel workPanel = null;

	// ���
	private JScrollPane tableScrollPane = null;
	private UniversalTableModel tableModel = null;
	private JTable myTable = null;
	private JPanel southPanel;

	private JLabel searchLabel;

	private JTextField textField;

	private JButton searchButton;

	@Override
	public void execute(JPanel workPanel) {
		this.workPanel = workPanel;
		this.workPanel.removeAll();
		this.workPanel.repaint();
		medicineDAO = new IMedicineDAOImpl();
		this.init();
	}

	private void init() {

		this.workPanel.setLayout(new BorderLayout());

		// �����в����
		this.tableModel = new UniversalTableModel();
		this.myTable = new JTable(this.tableModel);
		this.myTable.setRowHeight(25);
		this.myTable.setGridColor(Color.RED);
		this.myTable.setToolTipText("˫���鿴��¼��ϸ��Ϣ");
		// this.myTable.setFont(this.myFont);
		this.tableScrollPane = new JScrollPane();
		this.tableScrollPane.getViewport().add(this.myTable);
		this.workPanel.add(this.tableScrollPane, BorderLayout.CENTER);

		// �����ײ����
		this.southPanel = new JPanel(new FlowLayout());
		this.searchLabel = new JLabel("������ҩƷ����");
		this.textField = new JTextField(15);
		this.searchButton = new JButton("ģ����ѯ");
		this.southPanel.add(this.searchLabel);
		this.southPanel.add(this.textField);
		this.southPanel.add(this.searchButton);
		this.workPanel.add(this.southPanel, BorderLayout.SOUTH);

		// �¼�ע��
		this.searchButton.addActionListener(this);
		this.myTable.addMouseListener(this);
		
		this.flushTable();
	}

	private void flushTable() {
		String sql = "SELECT medId as 'ҩƷID', "
				+ "medName as 'ҩƷ����', "
				+ "ShelfLife as 'ҩƷ������/��', "
				+ "isOTC as '�Ƿ�ΪOTCҩƷ',"
				+ "approvalNumber as 'ҩƷ����', "
				+ "introduce as 'ҩƷ����', "
				+ "storageWay as '���淽ʽ', "
				+ "price as '���ۼ۸�/Ԫ'"
				+ "FROM medicine WHERE medName LIKE ?";
		String[] parse = new String[] { "%" + this.textField.getText() + "%" };
		this.tableModel = new UniversalTableModel(sql, parse);
		this.myTable.setModel(this.tableModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object soucre = e.getSource();
		if (soucre == this.searchButton) {
			this.flushTable();
		}
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if (e.getClickCount() == 2) {
			int rowIndex = this.myTable.getSelectedRow();
			String selectName = this.myTable.getValueAt(rowIndex, 1).toString();
			try {
				Medicine medicine = this.medicineDAO
						.findByMedicineName(selectName);
				new ShowMedicineInfoDialog(null, true, medicine)
						.setVisible(true);
				this.flushTable();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
