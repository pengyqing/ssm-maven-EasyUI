package com.bky.model;

/**
 * 成绩实体类
 * <p>
 */
import java.math.BigDecimal;

public class Score {
	private int id;
	
	private String subject;
	
	private BigDecimal mark;
	
	private int stuId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	
	
}
