package com.medicine.control.action.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.FlowView;

import java.util.regex.*;

import com.medicine.model.dao.IMedicineDAO;
import com.medicine.model.dao.IMedicineDAOImpl;
import com.medicine.model.entity.Medicine;

public class AddMedicineAction implements FunctionAction, ActionListener {

	private JPanel workPanel = null;
	private JPanel centerPanel = null;
	private JLabel titleLabel = null;

	private JLabel nameLabel = null;
	private JTextField nameField = null;
	private JLabel shelfLifeLabel = null;
	private JTextField shelfLifeField = null;
	private JLabel isOTCLabel = null;
	private JCheckBox isOTCheckBox = null;
	private JCheckBox isNotOTCCheckBox = null;
	private JLabel approvalLabel = null;
	private JTextField approvalField = null;
	private JLabel storageLabel = null;
	private JTextArea storageTextArea = null;
	private JLabel introduceLabel = null;
	private JTextArea introduceTextArea = null;
	private JLabel priceLabel = null;
	private JTextField priceTextField = null;
	private JButton confirmButton = null;
	private JButton resetButton = null;

	@Override
	public void execute(JPanel workPanel) {
		this.workPanel = workPanel;
		this.workPanel.removeAll();
		this.init();
		this.workPanel.repaint();
	}

	private void reset() {
		this.nameField.setText("");
		this.approvalField.setText("");
		this.introduceTextArea.setText("");
		this.shelfLifeField.setText("");
		this.isOTCheckBox.setSelected(true);
		this.priceTextField.setText("");
		this.storageTextArea.setText("");
	}

	private void init() {
		this.workPanel.setBackground(Color.WHITE);
		this.workPanel.setLayout(new BorderLayout());
		this.centerPanel = new JPanel(new GridBagLayout());
		this.centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.nameLabel = new JLabel("ҩƷ���������̣�");
		this.centerPanel.add(this.nameLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.nameField = new JTextField(16);
		this.centerPanel.add(this.nameField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		this.shelfLifeLabel = new JLabel("ҩƷ������(��)��");
		this.centerPanel.add(this.shelfLifeLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		this.shelfLifeField = new JTextField(16);
		this.centerPanel.add(this.shelfLifeField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		this.approvalLabel = new JLabel("ҩƷ׼�ֺţ�");
		this.centerPanel.add(this.approvalLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.approvalField = new JTextField(16);
		this.approvalField.setToolTipText("��ҩ[׼/��]�� +1��ĸ+8����");
		this.centerPanel.add(this.approvalField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		this.isOTCLabel = new JLabel("�Ƿ�ΪOTC��");
		this.centerPanel.add(this.isOTCLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.fill = 1;
		JPanel otcPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
		otcPanel.setBackground(Color.WHITE);
		this.isOTCheckBox = new JCheckBox("��", true);
		this.isNotOTCCheckBox = new JCheckBox("��", false);
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.isOTCheckBox);
		bg.add(this.isNotOTCCheckBox);
		otcPanel.add(this.isOTCheckBox);
		otcPanel.add(this.isNotOTCCheckBox);
		this.centerPanel.add(otcPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		this.introduceLabel = new JLabel("ҩƷ���ܣ�");
		this.centerPanel.add(this.introduceLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.fill = 1;
		this.introduceTextArea = new JTextArea(10, 15);
		JScrollPane scrollPane1 = new JScrollPane(this.introduceTextArea);
		this.centerPanel.add(scrollPane1, gbc);

		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.fill = 0;
		this.storageLabel = new JLabel("���淽ʽ��");
		this.centerPanel.add(this.storageLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.fill = 1;
		this.storageTextArea = new JTextArea(10, 16);
		JScrollPane scrollPane2 = new JScrollPane(this.storageTextArea);
		this.centerPanel.add(scrollPane2, gbc);

		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.fill = 0;
		this.priceLabel = new JLabel("ҩƷ�ۼ�(Ԫ)��");
		this.centerPanel.add(this.priceLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.fill = 1;
		this.priceTextField = new JTextField(16);
		this.centerPanel.add(this.priceTextField, gbc);

		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.fill = 1;
		this.confirmButton = new JButton("ȷ��");
		this.resetButton = new JButton("����");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(this.confirmButton);
		buttonPanel.add(this.resetButton);
		buttonPanel.setOpaque(false);
		this.centerPanel.add(buttonPanel, gbc);
		
		this.workPanel.add(this.centerPanel, BorderLayout.CENTER);
		this.titleLabel = new JLabel("ҩƷ��Ϣע���", JLabel.CENTER);
		this.titleLabel.setFont(new Font("�����п�", Font.BOLD, 19));
		this.titleLabel.setPreferredSize(new Dimension(50, 50));
		this.workPanel.add(this.titleLabel, BorderLayout.NORTH);
		
		this.confirmButton.addActionListener(this);
		this.resetButton.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == this.confirmButton) {
			// �жϱ����Ϣ�Ƿ�����
			if (this.nameField.getText().length() <= 0
					|| this.shelfLifeField.getText().length() <= 0
					|| this.storageTextArea.getText().length() <= 0
					|| this.approvalField.getText().length() <= 0
					|| this.introduceTextArea.getText().length() <= 0
					|| this.priceTextField.getText().length() <= 0) {
				JOptionPane.showMessageDialog(this.workPanel, "������Ϣ��������", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// �ж����������Ƿ�Ϸ�
			try {
				int shelfLife = Integer.parseInt(this.shelfLifeField.getText());
				if (shelfLife <= 0) {
					JOptionPane.showMessageDialog(this.workPanel, "������ʱ������Ƿ�",
							"��ʾ", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.workPanel, "������ʱ������Ƿ�",
						"��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				double price = Double
						.parseDouble(this.priceTextField.getText());
				if (price <= 0) {
					JOptionPane.showMessageDialog(this.workPanel, "���ۼ۸�����Ƿ�",
							"��ʾ", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.workPanel, "���ۼ۸�����Ƿ�", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// �ж�ҩƷ׼�ֺ������Ƿ�Ϸ�
			String regex = "^��ҩ[׼|��]��[HZSBTJF][0-9]{8}$";
			Pattern pattern = Pattern.compile(regex);
			if (!pattern.matcher(this.approvalField.getText()).find()) {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ׼�ֺ����벻�Ϸ�",
						"��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// �ж�ҩƷ�Ƿ��Ѿ�ע��
			IMedicineDAO iMedicineDAO = new IMedicineDAOImpl();
			if (iMedicineDAO.findByMedicineName(this.nameField.getText()) != null
					|| iMedicineDAO
							.findByMedicineApprovalNumber(this.approvalField
									.getText()) != null) {
				JOptionPane.showMessageDialog(this.workPanel, "��ҩƷ����ҩƷ׼�ֺ��ѱ�ע��",
						"ע��ʧ��", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// ��ҩƷ��Ϣ�������ݿ�.
			Medicine medicine = new Medicine();
			medicine.setMedName(this.nameField.getText());
			medicine.setShelfLife(this.shelfLifeField.getText());
			medicine.setOTC(this.isOTCheckBox.isSelected());
			medicine.setStroageWay(this.storageTextArea.getText());
			medicine.setApprovalNumber(this.approvalField.getText());
			medicine.setIntroduce(this.introduceTextArea.getText());
			medicine.setPrice(this.priceTextField.getText());

			if (iMedicineDAO.addMedicine(medicine)) {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ��"
						+ this.nameField.getText() + " ע��ɹ�", "ע��ɹ�",
						JOptionPane.INFORMATION_MESSAGE);
				this.reset();
			} else {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ��Ϣע��ʧ��",
						"ע��ʧ��", JOptionPane.ERROR_MESSAGE);
			}

		} else if (s == this.resetButton) {
			this.reset();
		}
	}

}
