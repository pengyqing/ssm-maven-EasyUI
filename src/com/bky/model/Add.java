package com.bky.model;

import java.math.BigDecimal;

/**
 * 接受参数的DTO
 * <p>
 */
public class Add {
	
	private String stuNumber;
	
	private String name;
	
	private String sex;
	
	private String department;
	
	private String grade;
	
	private String classesCard;
	
	private String build;
	
	private String dormNum;
	
	private String subject;
	
	private BigDecimal mark;
	
	private Integer stuId;
	
	private Integer id;

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassesCard() {
		return classesCard;
	}

	public void setClassesCard(String classesCard) {
		this.classesCard = classesCard;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getDormNum() {
		return dormNum;
	}

	public void setDormNum(String dormNum) {
		this.dormNum = dormNum;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public BigDecimal getMark() {
		return mark;
	}

	public void setMark(BigDecimal mark) {
		this.mark = mark;
	}

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}