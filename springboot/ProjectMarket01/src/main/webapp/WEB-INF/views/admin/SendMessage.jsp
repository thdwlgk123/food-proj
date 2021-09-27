<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset= UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>회원가입</title>
<style>
	 @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@800&display=swap');
    	.foot{
    	 bottom:0;
    	
    	} 
    	.body2{
    		height:60vh;
    	}
    	.hi{
		font-family: 'Nanum Gothic', sans-serif;
		font-size:30px;
		 }
</style>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	
<script src="http://code.jquery.com/jquery.js"></script>


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

	<div class="container text-center body2">
		<div class="page-header col aling-self-center font" style="margin-top:100px">
			<header>
				<p class="col-10 hi Secondary">전체 푸쉬 알림 보내기</p>
			</header>
		</div>
	
		<hr>
		
		<div class="container col-10">
			<form action="/sendMessageOk" method="post" >
				<div class="row gy-5 justify-content-center">
				    <div class="col-8 input-group mb-3">
				      <span class="input-group-text col-3 " id="basic-addon1">공지 제목</span>
				      <input type="text" class="form-control" id="j_username" name="notiTitle" aria-label="타이틀" aria-describedby="basic-addon1" placeholder="알림 타이틀을 입력하세요">
				    </div>
				    <div class="input-group mb-3 col-8">
	  					<span class="input-group-text col-3" id="basic-addon1">공지 내용</span>
	  					<input type="text" class="form-control" id="j_password" name="notiBody" aria-label="내용" aria-describedby="basic-addon1" placeholder="알림 내용을 입력하세요">
					</div>
					<div class="input-group mb-3 col-8">
	  					<span class="input-group-text col-3" id="basic-addon1">메세지</span>
	  					<input type="text" class="form-control" id="j_password" name="message"aria-label="메세지" aria-describedby="basic-addon1" placeholder="메세지를 입력하세요">
					</div>
					<br>
					<div  class="row gy-5 col-8 ">
						<input type="button" class="btn btn-outline-warning d-flex p-2" value="취소" onclick="javascript:window.location='/'">&nbsp;&nbsp;&nbsp;
						<input type="submit" name="submit" class="btn btn-outline-warning col" value="푸시 알림 전송">
					</div>
					<br>		
					<div class="container overflow-hidden">			    
				</div> 
			</div>
			</form>			
		</div>
		
	</div>

	<hr>

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