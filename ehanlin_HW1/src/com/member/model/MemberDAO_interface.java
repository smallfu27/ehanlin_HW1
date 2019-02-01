package com.member.model;

public interface MemberDAO_interface {
	public void insert(MemberVO memberVO);
    public void updateVisitTimesByMemId(MemberVO memberVO);
    public MemberVO findByMemId(String memId);
}
