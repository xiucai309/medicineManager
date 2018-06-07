package com.medicine.control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.medicine.control.action.org.FunctionAction;
import com.medicine.model.dao.IMenuDAO;
import com.medicine.model.dao.IMenuDAOImpl;
import com.medicine.model.entity.MenuBean;
import com.medicine.model.entity.MisUser;

public class MenuListener implements ActionListener {

	private JFrame mainFrame = null;
	private MisUser misUser = null;
	
	public MenuListener(JFrame mainFrame, MisUser misUser) {
		this.mainFrame = mainFrame;
		this.misUser = misUser;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		IMenuDAO menuDAO = new IMenuDAOImpl();
		String className = menuDAO.findByMenuId(e.getActionCommand()).getFunctionClass();
		try {
			
			Class myClass = Class.forName(className);
			FunctionAction functionAction = (FunctionAction) myClass.newInstance();
			functionAction.execute((JPanel)mainFrame.getContentPane().getComponent(1));
			this.mainFrame.setVisible(true);
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
