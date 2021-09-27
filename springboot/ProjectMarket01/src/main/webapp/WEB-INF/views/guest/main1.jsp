
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	
    <title>Hello, world!</title>
    <style>
		@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@800&display=swap');

    	.foot{
    	 bottom:0;
    	
    	} 
    	.body2{
    		height:80vh;
    	}
    	.hi{
		font-family: 'Nanum Gothic', sans-serif;
		font-size:30px;
		  /* 마우스 오버(마우스 올렸을때) */
		 }
        .inform:hover{
                background-color: #c5c5c5;
                cursor:pointer;
              
        }
        .slide1{
		  width: 100%;
		  height: 400px;
		  object-fit: cover;
        }
        
        a {
		  color: inherit;
		  text-decoration: none;
		}
        

    </style>
     
	<script>
	window.addEventListener('load', function () {
		naverLogin.getLoginStatus(function (status) {
			if (status) {
				/* (6) 로그인 상태가 "true" 인 경우 로그인 버튼을 없애고
				   사용자 정보를 출력합니다. */
				setLoginStatus();
			}
		});
	});
	
	var IMP = window.IMP;
	IMP.init('imp22149701');
	</script>
	<script>
	var index = 0; //이미지에 접근
	window.onload=function(){
		slideShow();
	}
	function slideShow(){
		var i;
		var x = $('.slide1');
		for(i = 0; i<x.length; i++){
			x[i].style.display = "none";
			
		}
		index++;
		if(index > x.length){
			index = 1;
		}
		x[index-1].style.display = "block";
		setTimeout(slideShow, 3000);
	}
	
	</script>
  </head>
  <body class="body2">
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

	<div class ="row"> 
				<a href="/content_view?bId=13"><img class = "slide1" src="/img/main1.png"></a>
			<img class = "slide1" src="/img/main2.png" >

		</div>
 
	<div class=" bg-light " style="margin-top:20px">
		<div class="row">
			<div class="container">
				<div class="row">
					<div class="col">
						<p class="hi Secondary">공지사항</p>
					</div>
					<div class="col">
						<a class="text-secondary float-right" href="/noticelist">더보기 ></a>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="container">
				<div class="row">
					<c:forEach items="${noticeTitle}" var="NoticeDto">
						<div class="col-4  inform" onclick="javascript:window.location='/admin/content_view?bId=${NoticeDto.bid}'">
						<div class="" style="font-size:30px; height:200px">${NoticeDto.btitle}</div>
						<div class =" text-secondary"style="font-size:15px">${fn:split(NoticeDto.bdate,' ')[0]}</div>
						</div>
					</c:forEach>
				</div>
			</div>
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