package com.framework.daobasetest;

public class BeanTest extends PropertyChangeSupportTest {

	private String name = "";

	public void setName(String name) {
		if (this.name.equals(name)) {
			System.out.println("BeanTest 的 name 属性没有变化！");
			return;
		}
		this.name = name;
		firePropertyChange("name", null, name);
	}

	public String getName() {
		return this.name;
	}

}