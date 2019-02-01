package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.member.model.MemberService;
import com.member.model.MemberVO;

public class MemberServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");
		
		if ("getOneMember".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String memId = req.getParameter("memId");
			if (memId == null || memId.trim().length() == 0) {
				errorMsgs.put("typeNothing", "名稱請勿空白");
				String url = "/ehanlin_HW1.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			Integer visitTimes = null;
			MemberService ms = new MemberService();
			MemberVO memberVO = ms.getOneMemberByMemId(memId);
			if (memberVO == null) {
				memberVO = ms.addMember(memId); // 新增member的sql指令會直接指定visitTimes欄位為1(次)
				visitTimes = memberVO.getVisitTimes();
			} else {
				visitTimes = memberVO.getVisitTimes() + 1;
				ms.updateVisitTimes(memId, visitTimes);
			}

			req.setAttribute("memberVO", memberVO);
			String url = "/ehanlin_HW1.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		if ("updateVisitTimesByAjax".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			String memId = req.getParameter("memId");
			if (memId == null || memId.trim().length() == 0) {
				errorMsgs.put("typeNothing", "請輸入名稱");
				JSONObject obj = new JSONObject();
				try {
					obj.put("errorMsgs", errorMsgs);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				out.print(obj);
				out.flush();
				out.close();
				return;
			} 
			Integer visitTimes = null;
			MemberService ms = new MemberService();
			MemberVO memberVO = ms.getOneMemberByMemId(memId);
			if (memberVO == null) {
				memberVO = ms.addMember(memId); // 新增member的sql指令會直接指定visitTimes欄位為1(次)
				visitTimes = memberVO.getVisitTimes();
			} else {
				visitTimes = memberVO.getVisitTimes() + 1;
				ms.updateVisitTimes(memId, visitTimes);
			}
			System.out.println("memId = " + memId);
			System.out.println("visitTimes = " + visitTimes);

			try {
				JSONObject obj = new JSONObject();
				obj.put("memId", memId);
				obj.put("visitTimes", visitTimes);
				out.print(obj);
				out.flush();
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
