<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
	<style>
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
	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"	crossorigin="anonymous"></script>
	<script src="http://code.jquery.com/jquery.js"></script>
    <title>로그인</title>

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
    </ul>
	<sec:authorize access="!isAuthenticated()">
    	<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/loginForm'">로그인</button>&nbsp;
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
		 <span class="navbar-text">
   			<a class="text-secondary"  href="javascript:window.location='myPage.jsp'">마이페이지</a>&nbsp;&nbsp;
   		 </span>
   	 		<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/logout'">로그아웃</button>&nbsp;
		</sec:authorize>
  </div>
</nav>

	
	<hr>

	<div class="container text-center body2">
		<div class="page-header col aling-self-center font">
			<header>
				<img src="/img/mainicon.png" style="width:90px; margin:20px">
			</header>
		</div>
	
		<hr>
	
		<div class="container col-10">
			<c:url value="j_spring_security_check" var="loginUrl"/>
			<form action="${loginUrl}" method="post" >
				<c:if test="${param.error != null}">
					<p>
						${error_message}
					</p>
				</c:if>
				<div class="row gy-5 justify-content-center">
				    <div class="col-8 input-group mb-3">
				      <span class="input-group-text col-3 " id="basic-addon1">아이디</span>
				      <input type="text" class="form-control" id="j_username" name="j_username" aria-label="아이디" aria-describedby="basic-addon1" value="${username}">
				    </div>
				    <div class="input-group mb-3 col-8">
	  					<span class="input-group-text col-3" id="basic-addon1">비밀번호</span>
	  					<input type="password" class="form-control" id="j_password" name="j_password" aria-label="비밀번호" aria-describedby="basic-addon1">
					</div>
					<br>
					<div  class="row gy-5 col-8 ">
						<input type="submit" class="btn btn-warning col" value="로그인">
					</div>
					<br>
					<div class="row gy-5 col-8 justify-content-center">
						
						<a href="/security/joinForm" class="font3">회원등록</a>
						
					</div>				
					<div class="container overflow-hidden">			    
				</div> 
			</div>
			</form>
			
		</div>
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
	  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
	

</body>
</html>