package com.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MemberDAO implements MemberDAO_interface{

	private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			}catch(NamingException e) {
				e.printStackTrace();
			}
		}
		
	
	private static final String INSERT_STMT = "INSERT INTO MEMBER (MEMNO, MEMID, VISITTIMES) "
			+ "VALUES ('M'||LPAD(to_char(member_seq.NEXTVAL), 4, '0'), ?, ?)";
	private static final String UPDATE_VISITTIMES = "UPDATE MEMBER SET VISITTIMES=? WHERE MEMID=?"; 
	private static final String GET_ONE_MEMBER = "SELECT * FROM MEMBER WHERE MEMID = ?"; 
	
	@Override 
	public void insert(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memberVO.getMemId());
			pstmt.setInt(2, memberVO.getVisitTimes());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void updateVisitTimesByMemId(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_VISITTIMES);
			
			pstmt.setInt(1, memberVO.getVisitTimes());
			pstmt.setString(2, memberVO.getMemId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public MemberVO findByMemId(String memId) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEMBER);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			pstmt.clearParameters();
			while(rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemNo(rs.getString(1));
				memberVO.setMemId(rs.getString(2));
				memberVO.setVisitTimes(rs.getInt(3));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return memberVO;
	}
	
}
