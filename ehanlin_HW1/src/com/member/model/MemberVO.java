package com.member.model;

public class MemberVO  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String memNo;
	private String memId;
	private Integer visitTimes;
	
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public Integer getVisitTimes() {
		return visitTimes;
	}
	public void setVisitTimes(Integer visitTimes) {
		this.visitTimes = visitTimes;
	}
	
}
