package com.medicine.model.entity;

public class MenuBean {

	private String menuId; // 本菜单的实际代号,应该由用户输入,或程序生成
	private String menuName;// 菜单的名称,如:学生管理
	private String menuMemo;// 菜单备注
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
