<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
	<style>
	   @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@800&display=swap');
    	.hi{
			font-family: 'Nanum Gothic', sans-serif;
			font-size:30px;
		 }
		 .foot{
    		bottom:0;
    	
    	} 
    	.body2{
    		height:70vh;
    	}
	</style>
	
<!-- Required meta tags -->
   
	 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	
	<script src="http://code.jquery.com/jquery.js"></script>
    <title>회원정보 변경</title>
    <script>
    var check = 0;
	function form_check() {
		
		
		
		if($('#c_name').val().length == 0) {
			alert("이름을 입력해주세요.");
			$('#c_name').focus();
			return;
		}
		if(check == 0) {
			alert("닉네임 중복체크를 해주세요.");
			return;
		}
		
		if($('#c_email').val().length == 0) {
			alert("이메일 아이디를 입력해주세요.");
			$('#c_email_id').focus();
			return;
		}
	
		
		if($('#c_phone1').val().length == 0) {
			alert("전화번호를 입력해주세요.");
			$('#c_phone').focus();
			return;
		}
		if($('#c_phone2').val().length == 0) {
			alert("전화번호를 입력해주세요.");
			$('#c_phone').focus();
			return;
		}if($('#c_phone3').val().length == 0) {
			alert("전화번호를 입력해주세요.");
			$('#c_phone').focus();
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
			url: '<%=conPath%>/security/modify',
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
	
	function moveCursor(){
		if($('#c_phone1').val().length ==  3) {
			$('#c_phone2').focus();
		}
	}
	function moveCursor2(){
		if($('#c_phone2').val().length ==  4) {
			$('#c_phone3').focus();
		}
	}
	function moveCursor3(){
		if($('#c_phone3').val().length ==  4) {
			$('#nickname').focus();
		}
	}
	function check_nick() {
		
		if($('#nickname').val() == $('#ori_nickname').val()){
			alert("기존 닉네임과 같습니다");
			check = 1;
		}else if($('#nickname').val().lenth < 6){
			alert("닉네임을 3자리 이상으로 만들어주세요.");
		} else{
		
		{
			var nickname = "nickname="+$('#nickname').val();
			$.ajax({
				url: '/security/checkNick',
				type: 'POST',
				data: nickname,
				dataType: 'text',
				success: function(json) {
					var result = JSON.parse(json);
					if(result.code=="success") {
						alert(result.desc);
						check = 1;
					} else {
						alert(result.desc);
					}
				}
			});
		}
	}
	}
		
	
	</script>

</head>
<body>
	 <nav class="navbar navbar-expand-lg">
 		<a class="navbar-brand" href="list.do"></a>
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
   			<a class="text-secondary"   href="/myPageMain?c_id=<sec:authentication property="principal.Username"/>">마이페이지</a>&nbsp;&nbsp;
   		 </span>
   	 		<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/logout'">로그아웃</button>&nbsp;
		</sec:authorize>
  </div>
</nav>

	
	<hr>

	<div class="container col-8">
			<div class="page-header col font">
				<header>
					<p class="col-10 hi Secondary">회원정보 변경</p>
				</header>
			</div>
			<form id="reg_frm" name="reg_frm" >
				<c:forEach items="${myPageInfo}" var="myPageInfo">	
				<div class="input-group mb-3">
  					<span class="input-group-text col-3.5" id="basic-addon1">아이디</span>
					<input type="text" class="form-control" id="c_id" name="c_id" readonly="true"  placeholder="* 6자리 이상" aria-label="아이디" aria-describedby="basic-addon1" value="${myPageInfo.c_id}">&nbsp;
				</div>
	
				<div class="input-group mb-3">
  					<span class="input-group-text col-3.5" id="basic-addon1">이름</span>
  					<input type="text" class="form-control" id="c_name" name="c_name" readonly="true"  aria-label="이름" aria-describedby="basic-addon1" value="${myPageInfo.c_name}">
				</div>
				<div class="input-group mb-3">
			
  					<span class="input-group-text col-3.5" id="basic-addon1">휴대폰</span>
  					<input type="text" class="form-control" id="c_phone1" name="c_phone1" aria-label="휴대폰" aria-describedby="basic-addon1" onkeyup = "moveCursor()" value="${fn:split(myPageInfo.c_phone,'-')[0]}">&nbsp;-&nbsp;
  					<input type="text" class="form-control" id="c_phone2" name="c_phone2" aria-label="휴대폰" aria-describedby="basic-addon1" onkeyup = "moveCursor2()" value="${fn:split(myPageInfo.c_phone,'-')[1]}">&nbsp;-&nbsp;
  					<input type="text" class="form-control" id="c_phone3" name="c_phone3" aria-label="휴대폰" aria-describedby="basic-addon1" onkeyup = "moveCursor3()" value="${fn:split(myPageInfo.c_phone,'-')[2]}"> 
  		
				</div>
				
				<div class="input-group mb-3">
  					<span class="input-group-text col-3.5" id="basic-addon1">닉네임</span>
  					<input type="text" class="form-control" id="nickname" name="nickname"  placeholder="* 3자리 이상" aria-label="닉네임" aria-describedby="basic-addon1" value="${myPageInfo.nickname}">&nbsp;
  					<input type="hidden" class="form-control" id="ori_nickname" name="ori_nickname"  placeholder="* 3자리 이상" aria-label="닉네임" aria-describedby="basic-addon1" value="${myPageInfo.nickname}">&nbsp;
  					<button type="button" class="btn btn-outline-warning" onclick="check_nick()">닉네임 중복확인</button>
				</div>
				<div class="input-group mb-3">
					<span class="input-group-text col-3.5" id="basic-addon1">이메일</span>
  					<input type="text" class="form-control" id="c_email" name="c_email" aria-label="c_email_id" value="${myPageInfo.c_email}">
  					
				</div>
				</c:forEach>
				
				<br><p>
				<div class="d-flex justify-content-center">
					<input type="button" class="btn btn-outline-warning d-flex p-2" value="취소" onclick="javascript:window.location='/'">&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-outline-warning d-flex p-2" value="수정하기" onclick="form_check()">
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