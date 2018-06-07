package com.medicine.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.medicine.control.listener.MenuListener;
import com.medicine.model.dao.IMenuDAOImpl;
import com.medicine.model.entity.MenuBean;
import com.medicine.model.entity.MisUser;

public class MainFrame extends JFrame implements ActionListener {

	private MisUser misUser = null;
	private JFrame reLoginFrame = null;

	private JPanel bodyPanel = null;
	private JMenuBar menuBar = null;
	private JMenu helpMenu = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem contentMenuItem = null;
	private JToolBar toolBar = null;
	private JLabel showNameLabel = null;
	private JButton reLoginButton = null;
	private JButton exitButton = null;
	private JPanel welcomePanel = null;
	private JLabel stateLabel = null;

	public MainFrame(MisUser misUser, JFrame reLoginFrame) {
		this.misUser = misUser;
		this.reLoginFrame = reLoginFrame;
		this.init();
	}

	// ��̬�����˵�.
	private void createMenu() {
		IMenuDAOImpl menuDAOImpl = new IMenuDAOImpl();
		String sql = "SELECT * FROM menu WHERE menuId LIKE ?";
		String parse[] = new String[] { "__" };
		List<MenuBean> list = menuDAOImpl.queryBySql(sql, parse);
		for (MenuBean menuBean : list) {
			// �ݹ鷽ʽ��ȡ�˵�������Ϣ
			JMenuItem menu = this.travelMenu(menuBean);

			this.menuBar.add(menu);
		}
	}

	/**
	 * �ݹ鷽ʽ���� menuBean �Ӳ˵�����,������ JMenuItem/JMenu.
	 * 
	 * @param menuBean
	 * @return
	 */
	private JMenuItem travelMenu(MenuBean menuBean) {
		IMenuDAOImpl menuDAOImpl = new IMenuDAOImpl();
		String sql = "SELECT * FROM menu WHERE menuId LIKE ?";
		String parse[] = new String[] { menuBean.getMenuId() + "__" };

		List<MenuBean> list = menuDAOImpl.queryBySql(sql, parse);

		if (list.isEmpty()) {
			JMenuItem menuItem = new JMenuItem();
			menuItem.setText(menuBean.getMenuName());
			menuItem.setActionCommand(menuBean.getMenuId());
			menuItem.setToolTipText(menuBean.getMenuMemo());
			// Ϊ menuItem ����¼�����.
			menuItem.addActionListener(new MenuListener(this, this.misUser));

			return menuItem;
		} else {
			JMenu menu = new JMenu();
			menu.setText(menuBean.getMenuName());
			menu.setActionCommand(menuBean.getMenuId());
			menu.setToolTipText(menuBean.getMenuMemo());

			for (MenuBean menuBean1 : list) {
				JMenuItem item = travelMenu(menuBean1);
				menu.add(item);
			}

			return menu;
		}
	}

	private void init() {

		this.bodyPanel = (JPanel) this.getContentPane();
		this.bodyPanel.setLayout(new BorderLayout());

		this.menuBar = new JMenuBar();

		this.createMenu();

		this.helpMenu = new JMenu("����");
		this.aboutMenuItem = new JMenuItem("����");
		this.contentMenuItem = new JMenuItem("����");
		this.helpMenu.add(this.aboutMenuItem);
		this.helpMenu.add(this.contentMenuItem);
		this.menuBar.add(this.helpMenu);
		this.setJMenuBar(this.menuBar);

		this.toolBar = new JToolBar();
		this.toolBar.setFloatable(false);
		this.toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.showNameLabel = new JLabel("�û� ��" + this.misUser.getUserName());
		this.reLoginButton = new JButton("���µ�½");
		this.exitButton = new JButton("�˳�");
		this.toolBar.add(this.showNameLabel);
		this.toolBar.add(this.reLoginButton);
		this.toolBar.add(this.exitButton);
		this.bodyPanel.add(toolBar, BorderLayout.NORTH);

		this.welcomePanel = new JPanel();
		this.welcomePanel.setBackground(Color.GRAY);
		this.welcomePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0,
				Color.RED));
		this.bodyPanel.add(this.welcomePanel, BorderLayout.CENTER);

		this.stateLabel = new JLabel("�汾: V1.0", JLabel.CENTER);
		this.bodyPanel.add(this.stateLabel, BorderLayout.SOUTH);

		// �¼�ע��.
		this.reLoginButton.addActionListener(this);
		this.exitButton.addActionListener(this);
		this.aboutMenuItem.addActionListener(this);

		this.setTitle("ҩ������ϵͳ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(50, 50, 1050, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		// ���µ�½.
		if (s == this.reLoginButton) {
			this.dispose();
			this.reLoginFrame.setVisible(true);
		}// �˳�ϵͳ.
		else if (s == this.exitButton) {
			System.exit(0);
		}// ����.
		else if (s == this.aboutMenuItem) {
			JOptionPane.showMessageDialog(this, "ҩ������ϵͳ���԰汾Version1.0",
					"About", JOptionPane.DEFAULT_OPTION, new ImageIcon(
							"./imgs/2.png"));
		}
	}
}
