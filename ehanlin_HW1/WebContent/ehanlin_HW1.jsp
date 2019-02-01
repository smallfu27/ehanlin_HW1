<%@page import="com.member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>  
<!DOCTYPE html>
<html>
<head>
<title>依照使用者輸入名稱，計數點擊加總</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('#submit').click(function(){
    	console.log($('#memId').val());
		var data = {
				action : 'updateVisitTimesByAjax',
				memId : $('#memId').val(),
			};
       $.ajax({
             url:'<%=request.getContextPath()%>/member/member.do',
             type:'post',
             data : data,
             dataType: 'json',
             success : function(response) {
            	 	if(response.errorMsgs != null){
            	 		var errorMsgs = response.errorMsgs;
            	 		$('#errorMsgs').text(response.errorMsgs.typeNothing);
            	 		$('#visitTimes').text("");
            	 		return;
            	 	}
//                  swal('點擊次數+1').then(() =>{
                	 $('#visitTimes').text(response.memId + "您好，您的點擊加總次數為：" +  response.visitTimes + "次");
                	 $('#errorMsgs').text("");
//              	});
             }
         });
    });
 });
</script>
<style>
#errorMsgs{
	color: red;
}
</style>
</head>

<body>
<!-- -----------------------------ajax寫法------------------------------------------->
<b>請輸入名稱:</b>
<input id="memId" type="text" value="${memberVO.memId}" maxlength="6">
<button id="submit" type="button" class="btn btn-primary" >送出</button>
<span id="errorMsgs"></span>
<br>		
<!-- -----------------------------forward協同寫法-------------------------------->
<%-- <form method="post" action="<%=request.getContextPath()%>/member/member.do"> --%>
<!-- 	<b>請輸入名稱:</b> -->
<%-- 	<input type="text" name="memId" value="${memberVO.memId}" maxlength="6">  --%>
<!-- 	<input type="hidden" name="action" value="getOneMember"> -->
<!-- 	<input type="submit" value="送出"> -->
<!-- </form>	 -->
<%-- <span style="color:red;">${errorMsgs.typeNothing}</span> --%>
<% 
// 	if(memberVO != null){
%>
<%-- 		<p>${memberVO.memId}您好，您的點擊加總次數為：${memberVO.visitTimes}次</p> --%>
<% 
// 	}
%>		
<!-- -----------------------------forward協同寫法--------------------------------->	

<p id="visitTimes"></p>


</body>
</html>