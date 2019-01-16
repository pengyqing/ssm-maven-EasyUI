package com.bky.model;

/**
 * 封装返回结果
 * <p>
 */
public class Result {
	
	// 数据
	private Object rows;
	
	// 总条数
	private Integer total;
	
	// 信息
	private String msg;
	
	public Result(Object rows,Integer total) {
		this.rows=rows;
		this.total=total;
	}
	
	public Result(String msg) {
		this.msg=msg;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
