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
		this.nameLabel = new JLabel("药品名及生产商：");
		this.centerPanel.add(this.nameLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.nameField = new JTextField(16);
		this.centerPanel.add(this.nameField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		this.shelfLifeLabel = new JLabel("药品保质期(天)：");
		this.centerPanel.add(this.shelfLifeLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		this.shelfLifeField = new JTextField(16);
		this.centerPanel.add(this.shelfLifeField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		this.approvalLabel = new JLabel("药品准字号：");
		this.centerPanel.add(this.approvalLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.approvalField = new JTextField(16);
		this.approvalField.setToolTipText("国药[准/试]字 +1字母+8数字");
		this.centerPanel.add(this.approvalField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		this.isOTCLabel = new JLabel("是否为OTC：");
		this.centerPanel.add(this.isOTCLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.fill = 1;
		JPanel otcPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
		otcPanel.setBackground(Color.WHITE);
		this.isOTCheckBox = new JCheckBox("是", true);
		this.isNotOTCCheckBox = new JCheckBox("否", false);
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.isOTCheckBox);
		bg.add(this.isNotOTCCheckBox);
		otcPanel.add(this.isOTCheckBox);
		otcPanel.add(this.isNotOTCCheckBox);
		this.centerPanel.add(otcPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		this.introduceLabel = new JLabel("药品介绍：");
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
		this.storageLabel = new JLabel("贮存方式：");
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
		this.priceLabel = new JLabel("药品售价(元)：");
		this.centerPanel.add(this.priceLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.fill = 1;
		this.priceTextField = new JTextField(16);
		this.centerPanel.add(this.priceTextField, gbc);

		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.fill = 1;
		this.confirmButton = new JButton("确定");
		this.resetButton = new JButton("重置");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(this.confirmButton);
		buttonPanel.add(this.resetButton);
		buttonPanel.setOpaque(false);
		this.centerPanel.add(buttonPanel, gbc);
		
		this.workPanel.add(this.centerPanel, BorderLayout.CENTER);
		this.titleLabel = new JLabel("药品信息注册表", JLabel.CENTER);
		this.titleLabel.setFont(new Font("华文行楷", Font.BOLD, 19));
		this.titleLabel.setPreferredSize(new Dimension(50, 50));
		this.workPanel.add(this.titleLabel, BorderLayout.NORTH);
		
		this.confirmButton.addActionListener(this);
		this.resetButton.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == this.confirmButton) {
			// 判断表格信息是否完整
			if (this.nameField.getText().length() <= 0
					|| this.shelfLifeField.getText().length() <= 0
					|| this.storageTextArea.getText().length() <= 0
					|| this.approvalField.getText().length() <= 0
					|| this.introduceTextArea.getText().length() <= 0
					|| this.priceTextField.getText().length() <= 0) {
				JOptionPane.showMessageDialog(this.workPanel, "表中信息必须完整", "提示",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 判断数字输入是否合法
			try {
				int shelfLife = Integer.parseInt(this.shelfLifeField.getText());
				if (shelfLife <= 0) {
					JOptionPane.showMessageDialog(this.workPanel, "保质期时间输入非法",
							"提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.workPanel, "保质期时间输入非法",
						"提示", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				double price = Double
						.parseDouble(this.priceTextField.getText());
				if (price <= 0) {
					JOptionPane.showMessageDialog(this.workPanel, "出售价格输入非法",
							"提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.workPanel, "出售价格输入非法", "提示",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 判断药品准字号输入是否合法
			String regex = "^国药[准|试]字[HZSBTJF][0-9]{8}$";
			Pattern pattern = Pattern.compile(regex);
			if (!pattern.matcher(this.approvalField.getText()).find()) {
				JOptionPane.showMessageDialog(this.workPanel, "药品准字号输入不合法",
						"提示", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 判断药品是否已经注册
			IMedicineDAO iMedicineDAO = new IMedicineDAOImpl();
			if (iMedicineDAO.findByMedicineName(this.nameField.getText()) != null
					|| iMedicineDAO
							.findByMedicineApprovalNumber(this.approvalField
									.getText()) != null) {
				JOptionPane.showMessageDialog(this.workPanel, "该药品名或药品准字号已被注册",
						"注册失败", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 将药品信息加入数据库.
			Medicine medicine = new Medicine();
			medicine.setMedName(this.nameField.getText());
			medicine.setShelfLife(this.shelfLifeField.getText());
			medicine.setOTC(this.isOTCheckBox.isSelected());
			medicine.setStroageWay(this.storageTextArea.getText());
			medicine.setApprovalNumber(this.approvalField.getText());
			medicine.setIntroduce(this.introduceTextArea.getText());
			medicine.setPrice(this.priceTextField.getText());

			if (iMedicineDAO.addMedicine(medicine)) {
				JOptionPane.showMessageDialog(this.workPanel, "药品："
						+ this.nameField.getText() + " 注册成功", "注册成功",
						JOptionPane.INFORMATION_MESSAGE);
				this.reset();
			} else {
				JOptionPane.showMessageDialog(this.workPanel, "药品信息注册失败",
						"注册失败", JOptionPane.ERROR_MESSAGE);
			}

		} else if (s == this.resetButton) {
			this.reset();
		}
	}

}
