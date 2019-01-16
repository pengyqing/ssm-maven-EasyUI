package com.bky.model;

/**
 * 学生实体类
 * <p>
 */
public class Student {
	
	private int id;
	
	private String stuNumber;
	
	private String name;
	
	private String sex;
	
	private int classesId;
	
	private int dormitoryId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStuNumber() {
		return stuNumber;
	}

	public void setStuNumber(String stuNumber) {
		this.stuNumber = stuNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getClassesId() {
		return classesId;
	}

	public void setClassesId(int classesId) {
		this.classesId = classesId;
	}

	public int getDormitoryId() {
		return dormitoryId;
	}

	public void setDormitoryId(int dormitoryId) {
		this.dormitoryId = dormitoryId;
	}
	
	
	
}
