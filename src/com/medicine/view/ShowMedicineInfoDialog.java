package com.medicine.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.medicine.model.dao.IMedicineDAO;
import com.medicine.model.dao.IMedicineDAOImpl;
import com.medicine.model.entity.Medicine;

public class ShowMedicineInfoDialog extends JDialog implements ActionListener {

	private Medicine medicine = null;
	private IMedicineDAO medicineDAO = null;
	private JPanel bodyPanel = null;

	private JLabel nameLabel = null;
	private JTextField nameField = null;
	private JLabel shelfLifeLabel = null;
	private JTextField shelfLifeField = null;
	private JLabel isOTCLabel = null;
	private JCheckBox isOTCCheckBox = null;
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

	public ShowMedicineInfoDialog(Medicine medicine) {
		this.medicine = medicine;
		this.init();
		this.setBounds(50, 50, 700, 550);
	}

	public ShowMedicineInfoDialog(Frame parent, boolean isModal,
			Medicine medicine) {
		super(parent, isModal);
		this.medicine = medicine;
		this.init();
		this.setBounds(50, 50, 700, 550);
	}

	private void init() {

		medicineDAO = new IMedicineDAOImpl();

		this.bodyPanel = (JPanel) this.getContentPane();

		this.bodyPanel.setBackground(Color.WHITE);
		this.bodyPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.nameLabel = new JLabel("药品名及生产商：");
		this.bodyPanel.add(this.nameLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.nameField = new JTextField(16);
		this.nameField.setEnabled(false);
		this.bodyPanel.add(this.nameField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		this.shelfLifeLabel = new JLabel("药品保质期(天)：");
		this.bodyPanel.add(this.shelfLifeLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		this.shelfLifeField = new JTextField(10);
		this.bodyPanel.add(this.shelfLifeField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		this.approvalLabel = new JLabel("药品准字号：");
		this.bodyPanel.add(this.approvalLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.approvalField = new JTextField(16);
		this.approvalField.setEnabled(false);
		this.bodyPanel.add(this.approvalField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		this.isOTCLabel = new JLabel("是否为OTC：");
		this.bodyPanel.add(this.isOTCLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		JPanel otcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
		otcPanel.setOpaque(false);
		this.isOTCCheckBox = new JCheckBox("是", true);
		this.isNotOTCCheckBox = new JCheckBox("否", false);
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.isOTCCheckBox);
		bg.add(this.isNotOTCCheckBox);
		otcPanel.add(this.isOTCCheckBox);
		otcPanel.add(this.isNotOTCCheckBox);
		this.bodyPanel.add(otcPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		this.introduceLabel = new JLabel("药品介绍：");
		this.bodyPanel.add(this.introduceLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.fill = 1;
		this.introduceTextArea = new JTextArea(10, 15);
		JScrollPane scrollPane1 = new JScrollPane(this.introduceTextArea);
		this.bodyPanel.add(scrollPane1, gbc);

		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.fill = 0;
		this.storageLabel = new JLabel("贮存方式：");
		this.bodyPanel.add(this.storageLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.fill = 1;
		this.storageTextArea = new JTextArea(10, 9);
		JScrollPane scrollPane2 = new JScrollPane(this.storageTextArea);
		this.bodyPanel.add(scrollPane2, gbc);

		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.fill = 0;
		this.priceLabel = new JLabel("药品售价(元)：");
		this.bodyPanel.add(this.priceLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.fill = 1;
		this.priceTextField = new JTextField(10);
		this.bodyPanel.add(this.priceTextField, gbc);

		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.fill = 1;
		this.confirmButton = new JButton("确定");
		this.resetButton = new JButton("重置");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.confirmButton);
		buttonPanel.add(this.resetButton);
		buttonPanel.setOpaque(false);
		this.bodyPanel.add(buttonPanel, gbc);

		// 填写药品信息
		if (this.medicine != null) {
			this.nameField.setText(this.medicine.getMedName());
			this.approvalField.setText(this.medicine.getApprovalNumber());
			this.isOTCCheckBox.setSelected(this.medicine.isOTC());
			this.isNotOTCCheckBox.setSelected(!this.medicine.isOTC());
			this.introduceTextArea.setText(this.medicine.getIntroduce());
			this.storageTextArea.setText(this.medicine.getStroageWay());
			this.shelfLifeField.setText(this.medicine.getShelfLife());
			this.priceTextField.setText(this.medicine.getPrice());
		}

		// 事件注册
		this.confirmButton.addActionListener(this);
		this.resetButton.addActionListener(this);

		this.setTitle(this.medicine.getMedName() + "-"
				+ this.medicine.getApprovalNumber());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.confirmButton) {
			if (this.nameField.getText().length() <= 0
					|| this.shelfLifeField.getText().length() <= 0
					|| this.storageTextArea.getText().length() <= 0
					|| this.introduceTextArea.getText().length() <= 0
					|| this.priceTextField.getText().length() <= 0) {
				JOptionPane.showMessageDialog(this.bodyPanel, "表中信息必须完整", "提示",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 判断数字输入是否合法
			try {
				int shelfLife = Integer.parseInt(this.shelfLifeField.getText());
				if (shelfLife <= 0) {
					JOptionPane.showMessageDialog(this.bodyPanel, "药品保质期时间输入非法",
							"提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.bodyPanel, "药品保质期时间输入非法",
						"提示", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				double price = Double
						.parseDouble(this.priceTextField.getText());
				if (price <= 0) {
					JOptionPane.showMessageDialog(this.bodyPanel, "药品出售价格输入非法",
							"提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.bodyPanel, "药品出售价格输入非法", "提示",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			int result = JOptionPane.showConfirmDialog(this, "确认修改当前药品信息",
					"提示", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				// 将药品信息存入Medicine对象.
				this.medicine.setShelfLife(this.shelfLifeField.getText());
				this.medicine.setIntroduce(this.introduceTextArea.getText());
				this.medicine.setPrice(this.priceTextField.getText());
				this.medicine.setOTC(this.isOTCCheckBox.isSelected());
				this.medicine.setStroageWay(this.storageTextArea.getText());

				// 将药品对象信息存入数据库.
				try {
					if (this.medicineDAO.modifyMedicine(this.medicine)) {
						JOptionPane.showMessageDialog(this,
								"药品 : " + medicine.getMedName() + " 信息修改成功",
								"提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "修改失败", "提示",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
