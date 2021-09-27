
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
    <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery.js"></script>
    <title>Hello, world!</title>
    <style>
		@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@800&display=swap');

    	.foot{
    	 bottom:0;
    	
    	} 
    	.body2{
    		height:70vh;
    		margin-top:20px;
    	}
    	.hi{
		font-family: 'Nanum Gothic', sans-serif;
		font-size:30px;
		  /* 마우스 오버(마우스 올렸을때) */
		 }
		.mypage{
			height:300px;
		}

    </style>
     
	<script>
	
	function form_check() {
		
		if($('#nC_pw').val().length == 0) {
			alert("비밀번호를 입력해주세요.");
			$('#c_pw').focus();
			return;
		}
		
		if($('#c_pw').val().length < 8) {
			alert("비밀번호는 8자리 이상이어야 합니다.");
			$('#c_pw').focus();
			return;
		}
		if($('#c_pw').val() != $('#password_check').val()) {
			alert("비밀번호가 일치하지 않습니다.");
			$('#c_pw').focus();
			return;
		}
		
		
		submit_ajax();
	}
	
	
	

	function submit_ajax() {
		<%
			String conPath = request.getContextPath();
		%>
		var queryString = $("#reg_frm").serialize();
		$.ajax({
			url: '<%=conPath%>/myPage/changePwd',
			type: 'POST',
			data: queryString,
			dataType: 'text',
			success: function(json) {
				var result = JSON.parse(json);
				if(result.code=="success") {
					alert(result.desc)
					window.location.replace("/");
				} else {
					alert(result.desc);
				}
			}
		});
	}
	
	
	</script>
  </head>
  <body>
    <nav class="navbar navbar-expand-lg">
 		<a class="navbar-brand" href="/"></a>
  		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
   		 <span class="navbar-toggler-icon"></span>
  	</button>
  <div class="collapse navbar-collapse " id="navbarText">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link " href="/"><img src="/img/mainicon.png" style="width:90px"><span class="sr-only">(current)</span></a>
      </li>
       <li class="nav-item">
        <a class="nav-link text-secondary" href="/notice">&nbsp;&nbsp;예약현황</a>
      </li>
       <li class="nav-item">
        <a class="nav-link text-secondary" href="/noticelist">&nbsp;&nbsp;공지사항</a>
      </li>
      <li class="nav-item">
      <a class="nav-link text-secondary" href="/SendMessage">푸시알림</a>
	  </li>

    </ul>
	<sec:authorize access="!isAuthenticated()">
    	<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/loginForm'">로그인</button>&nbsp;
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
		 <span class="navbar-text">
   			<a class="text-secondary"  href="/myPageMain?c_id=<sec:authentication property="principal.Username"/>">마이페이지</a>&nbsp;&nbsp;
   		 </span>
   	 		<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/logout'">로그아웃</button>&nbsp;
		</sec:authorize>
  </div>
</nav>

 
	<div class="container col-8 body2 ">
		<div class="row">
					<p class="col-10 hi Secondary">마이페이지</p>
		</div>
			<form id="reg_frm" name="reg_frm" >
			<input type="hidden" class="form-control" id="c_id" name="c_id" value=<sec:authentication property="principal.Username"/>>
			 <div class="row justify-content-center"style="margin-top:180px;">
			 	 <div class="input-group col-8" >
	  					<span class="input-group-text col-3.5" id="basic-addon1">현재 비밀번호</span>
	  					<input type="password" class="form-control" id="nC_pw" name="nC_pw" placeholder="현재 비밀번호" aria-label="비밀번호" aria-describedby="basic-addon1">
				</div>
			</div>
			 <div class="row justify-content-center" style="margin-top:10px;">
				 <div class="input-group col-8">
	  					<span class="input-group-text col-3.5" id="basic-addon1">비밀번호</span>
	  					<input type="password" class="form-control" id="c_pw" name="c_pw" placeholder="* 8자리 이상" aria-label="비밀번호" aria-describedby="basic-addon1">
				</div>
			</div>
			 <div class="row justify-content-center"style="margin-top:10px;">
				<div class="input-group col-8">
  					<span class="input-group-text col-3.5" id="basic-addon1">비밀번호 확인</span>
  					<input type="password" class="form-control" id="password_check" name="password_check" placeholder="* 8자리 이상"  aria-label="비밀번호 확인" aria-describedby="basic-addon1">
				</div>
			</div>
  			
  			<div class="d-flex justify-content-center"style="margin-top:10px;">
					<input type="button" class="btn btn-outline-warning d-flex p-2" value="취소" onclick="javascript:window.location='/myPageMain?c_id=<sec:authentication property="principal.Username"/>'">&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-outline-warning d-flex p-2" value="변경하기" onclick="form_check()">
			</div>
			</form>
		</div>
		
	

	

	<div class="p-3 mb-2  text-black foot">
	<hr>
	<br>
		<div class="container">
			<footer>
			<div class="row">
			<div class="col-10">
				이메일 : engudengud@gmail.com <br> 전화번호 : 010-5918-3963 <br> 주소 : 서울시 은평구 신사동 <br> 고객센터 : 010-9273-9992 
			</div>
			<div class="col-1">
				<img src="/img/mainicon.png" style="width:90px; margin:20px">
			</div>
			</div>
			</footer>
		</div>
	</div>
    
  </body>
</html>