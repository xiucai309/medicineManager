package com.medicine.model.entity;

public class MenuBean {

	private String menuId; // ���˵���ʵ�ʴ���,Ӧ�����û�����,���������
	private String menuName;// �˵�������,��:ѧ������
	private String menuMemo;// �˵���ע
	private String functionClass;
	
	public String getFunctionClass() {
		return functionClass;
	}

	public void setFunctionClass(String functionClass) {
		this.functionClass = functionClass;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuMemo() {
		return menuMemo;
	}

	public void setMenuMemo(String menuMemo) {
		this.menuMemo = menuMemo;
	}

}
