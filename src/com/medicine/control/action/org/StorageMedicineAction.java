package com.medicine.control.action.org;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.medicine.model.dao.IMedicineDAO;
import com.medicine.model.dao.IMedicineDAOImpl;
import com.medicine.model.dao.IStorageRecordDAO;
import com.medicine.model.dao.IStorageRecordDAOImpl;
import com.medicine.model.entity.Medicine;
import com.medicine.model.entity.StorageRecord;

public class StorageMedicineAction implements FunctionAction, ActionListener {

	private JPanel workPanel = null;

	private JLabel titleLabel = null;
	private JLabel medNameLable = null;
	private JTextField medNameTField = null;
	private JLabel medNumber = null;
	private JTextField medNumberField = null;
	private JLabel productDateLabel = null;
	private JTextField productDateField = null;
	private JLabel priceLabel = null;
	private JTextField priceTField = null;
	private JLabel noteLabel = null;
	private JTextArea noteTextArea = null;
	private JButton confirmButton = null;
	private JButton resetButton = null;

	@Override
	public void execute(JPanel workPanel) {
		this.workPanel = workPanel;
		this.workPanel.removeAll();
		this.init();
		this.workPanel.repaint();
	}

	private void init() {
		this.workPanel.setBackground(Color.WHITE);
		this.workPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 1;
		gbc.gridy = 0;
		this.titleLabel = new JLabel("ҩƷ���ǼǱ�");
		this.titleLabel.setFont(new Font("�����п�", Font.BOLD, 17));
		this.workPanel.add(this.titleLabel, gbc);

		gbc.fill = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.medNameLable = new JLabel("ҩƷ���ƣ�");
		this.workPanel.add(this.medNameLable, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.medNameTField = new JTextField(16);
		this.medNameTField.setToolTipText("ҩƷ��������ע��");
		this.workPanel.add(this.medNameTField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		this.medNumber = new JLabel("ҩƷ������");
		this.workPanel.add(this.medNumber, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.medNumberField = new JTextField(16);
		this.workPanel.add(this.medNumberField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		this.productDateLabel = new JLabel("�������ڣ�");
		this.workPanel.add(this.productDateLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.productDateField = new JTextField(16);
		this.productDateField.setToolTipText("yyyy-mm-dd");
		this.workPanel.add(this.productDateField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		this.priceLabel = new JLabel("��ⵥ�ۣ�");
		this.workPanel.add(this.priceLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		this.priceTField = new JTextField(16);
		this.workPanel.add(this.priceTField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		this.noteLabel = new JLabel("ҩƷ��ע��");
		this.workPanel.add(this.noteLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		this.noteTextArea = new JTextArea(10, 16);
		JScrollPane scrollPane = new JScrollPane(this.noteTextArea);
		this.workPanel.add(scrollPane, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		this.confirmButton = new JButton("ȷ�����");
		this.resetButton = new JButton("����");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setOpaque(false);
		buttonPanel.add(this.confirmButton);
		buttonPanel.add(this.resetButton);
		this.workPanel.add(buttonPanel, gbc);

		this.confirmButton.addActionListener(this);
		this.resetButton.addActionListener(this);
	}

	private int getDayOff(Date d1, Date d2) {
		// Calendar c1 = Calendar.getInstance();
		// c1.setTime(d1);
		// Calendar c2 = Calendar.getInstance();
		// c2.setTime(d2);
		// long n1 = c1.getTimeInMillis();
		// long n2 = c2.getTimeInMillis();
		// int dayoff = (int)Math.abs((n2 - n1)/24*3600000);
		// if(n2 > n1) {
		// return dayoff;
		// }else {
		// return -1 * dayoff;
		// }
		//System.out.println(d1.toLocaleString());
		//System.out.println(d2.toLocaleString());
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object souce = e.getSource();
		if (souce == this.confirmButton) {
			// �ж�ҩƷ�Ƿ�ע��
			IMedicineDAO medicineDAO = new IMedicineDAOImpl();
			Medicine medicine = medicineDAO
					.findByMedicineName(this.medNameTField.getText());
			if (medicine == null) {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ:"
						+ this.medNameTField.getText() + " ��δע�ᣬ��ע���ҩƷ��Ϣ",
						"��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// �ж����������Ƿ�Ϸ�
			try {
				int number = Integer.parseInt(this.medNumberField.getText());
				if (number <= 0) {
					JOptionPane.showMessageDialog(this.workPanel, "ҩƷ��������Ƿ�",
							"��ʾ", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ��������Ƿ�", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				double price = Double.parseDouble(this.priceTField.getText());
				if (price <= 0) {
					JOptionPane.showMessageDialog(this.workPanel, "ҩƷ�۸�����Ƿ�",
							"��ʾ", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ�۸�����Ƿ�", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// �ж������Ƿ�Ϸ�
			Date productDate = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(false);
			try {
				productDate = format.parse(this.productDateField.getText());
				// ҩƷ�������ڲ��ܴ���ϵͳ����
				int dayoff = getDayOff(productDate, new Date());// ϵͳ��ǰ�������������ڵ�ʱ���
				if (dayoff < 0) {
					JOptionPane.showMessageDialog(this.workPanel,
							"�������ڲ��ô��ڵ�ǰϵͳ����", "��ʾ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// �жϸ���ҩƷ�Ƿ����
				if (dayoff >= Integer.parseInt(medicine.getShelfLife())) {
					int res = JOptionPane.showConfirmDialog(
							this.workPanel,
							"����ҩƷ�ѹ��� "
									+ (dayoff - Integer.parseInt(medicine
											.getShelfLife())) + " ���Ƿ����������?",
							"��ʾ", JOptionPane.YES_NO_OPTION);
					
					if (res != JOptionPane.OK_OPTION) {
						return;
					}
				}
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(this.workPanel,
						"���ڸ�ʽ�Ƿ�����ȷ��ʽ:yyyy-mm-dd", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			StorageRecord record = new StorageRecord();
			record.setMedId(medicine.getMedId());
			record.setPrice(Double.parseDouble(this.priceTField.getText()));
			record.setProductDate(productDate);
			record.setStorageDate(new Date());
			record.setStorageNumber(Integer.parseInt(this.medNumberField
					.getText()));
			record.setLeastNumber(record.getStorageNumber() + "");
			record.setNote(this.noteTextArea.getText());

			IStorageRecordDAO recordDAO = new IStorageRecordDAOImpl();
			if (recordDAO.addStorageRecord(record)) {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ���ɹ�", "��ʾ",
						JOptionPane.DEFAULT_OPTION);

			} else {
				JOptionPane.showMessageDialog(this.workPanel, "ҩƷ���ʧ��", "��ʾ",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (souce == this.resetButton) {

		}
	}

}
