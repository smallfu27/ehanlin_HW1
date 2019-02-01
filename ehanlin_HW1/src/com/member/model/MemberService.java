package com.member.model;

public class MemberService {
	private MemberDAO_interface dao;
	
	public MemberService() {
		dao = new MemberDAO();
	}
	
	public MemberVO addMember(String memId) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemId(memId);
		memberVO.setVisitTimes(1);
		
		dao.insert(memberVO);
		
		return memberVO;
	}
	
	public MemberVO updateVisitTimes(String memId, Integer visitTimes) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemId(memId);
		memberVO.setVisitTimes(visitTimes);
		
		dao.updateVisitTimesByMemId(memberVO);
		
		return memberVO;
	}
	
	public MemberVO getOneMemberByMemId(String memId) {
		return dao.findByMemId(memId);
	}
}
