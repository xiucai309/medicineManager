package com.medicine.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.medicine.model.dao.IMisUserDAOImpl;
import com.medicine.model.entity.MisUser;

public class LoginFrame extends JFrame implements ActionListener {

	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new LoginFrame().setVisible(true);
	}

	private JLabel titleLabel = null;
	private JLabel nameLabel = null;
	private JTextField nameField = null;
	private JLabel passwordLabel = null;
	private JPasswordField passwordField = null;
	private JButton loginButton = null;
	private JButton resetButton = null;

	private MisUser misUser = null;

	public LoginFrame() {
		this.init();
	}

	public void init() {
		JPanel body = (JPanel) this.getContentPane();
		body.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		body.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		this.titleLabel = new JLabel("药房管理系统登陆");
		this.titleLabel.setFont(new Font("华文行楷", Font.BOLD, 20));
		// this.titleLabel.setForeground(Color.WHITE);
		gbc.gridx = 1;
		gbc.gridy = 0;
		body.add(this.titleLabel, gbc);

		gbc.anchor = GridBagConstraints.EAST;
		this.nameLabel = new JLabel("用户名：");
		gbc.gridx = 0;
		gbc.gridy = 1;
		body.add(this.nameLabel, gbc);

		this.nameField = new JTextField(16);
		gbc.gridx = 1;
		gbc.gridy = 1;
		body.add(this.nameField, gbc);

		this.passwordLabel = new JLabel("密码：");
		gbc.gridx = 0;
		gbc.gridy = 2;
		body.add(this.passwordLabel, gbc);

		this.passwordField = new JPasswordField(16);
		gbc.gridx = 1;
		gbc.gridy = 2;
		body.add(this.passwordField, gbc);

		JPanel buttonPanel = new JPanel();
		this.loginButton = new JButton("登陆");
		this.resetButton = new JButton("重置");
		buttonPanel.add(this.loginButton);
		buttonPanel.add(this.resetButton);
		gbc.gridx = 1;
		gbc.gridy = 3;
		body.add(buttonPanel, gbc);
		buttonPanel.setOpaque(false);

		body.setBackground(Color.PINK);

		// 事件注册
		this.loginButton.addActionListener(this);
		this.resetButton.addActionListener(this);
		
		this.setResizable(false);
		this.setTitle("药房管理系统登陆");
		this.setBounds(300, 100, 330, 220);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private boolean checkUser(String Id, String pwd) {
		boolean flag = false;
		IMisUserDAOImpl misUserDAO = new IMisUserDAOImpl();
		this.misUser = misUserDAO.findById(Id);
		if (misUser != null && misUser.getUserPwd().equals(pwd)) {
			flag = true;
		}
		return flag;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == this.loginButton) {
			if (this.nameField.getText().length() <= 0
					|| this.passwordField.getPassword().length <= 0) {
				JOptionPane.showMessageDialog(this, "用户名或密码能不为空");
				return;
			}
			if (this.checkUser(this.nameField.getText(), new String(
					this.passwordField.getPassword()))) {
				MainFrame main = new MainFrame(this.misUser, this);
				JOptionPane.showMessageDialog(this, "登陆成功");
				main.setVisible(true);

				this.dispose();

			} else {
				JOptionPane.showMessageDialog(this, "用户名或密码不正确");
			}
		} else if (s == this.resetButton) {
			this.nameField.setText("");
			this.passwordField.setText("");
		}
	}
}
