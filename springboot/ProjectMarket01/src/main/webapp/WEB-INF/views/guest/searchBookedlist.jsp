<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
 	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	
	<script src="http://code.jquery.com/jquery.js"></script>
	
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

		 }
		
		ul{
	
		list-style:none;	
		}
		li{
		float:left;
	
		padding-right:5px;
		ling-height:10px;
		}
		
	
	</style>
	<script>
		function checkReservation(rid){
			var r_rsvnumber = "r_rsvnumber="+$('#r_rsvnumber'+rid).val();
			$.ajax({
				url: '/checkReservation',
				type: 'POST',
				data: r_rsvnumber,
				dataType: 'text',
				success: function(json) {
					var result = JSON.parse(json);
					if(result.code=="success") {
						alert(result.desc);
						location.reload();
					} else {
						alert(result.desc);
					}
				}
			});
			
		}
		
		function form_check() {

			if ($('#lF').val().length == 0) {
				alert("검색어를 입력해주세요.");
				$('#lF').focus();
				return;
			}
			var select = ($("#slF").val());
			var  content = ($("#lF").val());

			
			
			window.location.href="/searchNotice?category="+select+"&content="+content;
			
			
		}
			
	</script>
    <title>Hello, world!</title>
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
      <span class="navbar-text">
    	<sec:authorize access="!isAuthenticated()">
    	<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/loginForm'">로그인</button>&nbsp;
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
		 <span class="navbar-text">
   			<a class="text-secondary" href="/myPageMain?c_id=<sec:authentication property="principal.Username"/>">마이페이지</a>&nbsp;&nbsp;
   		 </span>
   	 		<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/logout'">로그아웃</button>&nbsp;
		</sec:authorize>
    </span>
  </div>
</nav>
	<hr>

	<div class="container">
		<div class="page-header col font">
			<header>
				<p class="col-10 hi Secondary">예약현황</p>
			</header>
		</div>
	
		<hr>
		<div class="row float-right">
  			 <form class="#reg_frm">
		  	 	<ul>
					<li><select id="slF" class="form-select">
						<option selected value="bName">예약번호</option>
						<option value="r_name">음식점 이름</option>
						<option value="nickname">예약자 이름</option>
					</select></li>
					<li><input type="text" class="form-control" name="lF" id="lF" placeholder="검색"></li>
					<li><input type="button" class="btn btn-outline-warning"  onclick="form_check();" value="찾기"></li>
					
				</ul>	 
		</form>
  	 </div>		
		<table class="table table-hover">
			<thead class="thead-light">
				<tr>
					<td>예약번호</td>
					<td>음식점이름</td>
					<td>예약자아이디</td>
					<td>예약자이름</td>
					<td>휴대폰 번호</td>
					<td>예약인원</td>
					<td>예약승인</td>
					<td>요청사항</td>
					<td>예약날짜</td>
					<td>예약시간</td>
				</tr>
				<c:forEach items="${noticeList}" var="NoticeDto">
				<tbody>
				<tr>
					<td>
						${NoticeDto.r_rsvnumber}
						<input type="hidden" value="${NoticeDto.r_rsvnumber}" id ="r_rsvnumber${NoticeDto.r_rsvnumber}" >
					</td>
					<td>${NoticeDto.r_name}</td>
					<td>${NoticeDto.c_id}</td>
					<td>${NoticeDto.nickname}</td>
					<td>${NoticeDto.c_phone}</td>
					<td>${NoticeDto.b_party}</td>
					<td>${NoticeDto.reject}</td>
					<td>${NoticeDto.request}</td>
					<td>${NoticeDto.tdate}</td>
					<td>${NoticeDto.time}</td>
					<c:if test="${NoticeDto.condition_check == 1}">
					<td><a class="btn btn-outline-warning text-warning" onclick="checkReservation('${NoticeDto.r_rsvnumber}')">예약확인</a></td>
					</c:if>
				</tr>
				</tbody>
				</c:forEach>
<!-- 				<tr>
					<td colspan="5">
					<button type="button" class="btn btn-outline-primary">
					<a class="nav-link" href="write_view.doo">글작성</a>
					</button>
					</td> -->
				</tr>
				<tr>
			  <c:forEach var="page" items="${page }">
			<td colspan="5">
			<ul class="pagination justify-content-center">
			    <!-- 처음 -->
			    <li class="page-item, page-link" href="/notice?page=1">
			    <c:choose>
					<c:when test="${(page.curPage - 1) < 1}">
						First
					</c:when>
					<c:otherwise>
						<a href="/searchNotice?page=1">First</a>
					</c:otherwise>
				</c:choose>
				</li>
				<!-- 이전 -->
			    <li class="page-item, page-link">
			    <c:choose>
					<c:when test="${(page.curPage - 1) < 1}">
						Prev
					</c:when>
					<c:otherwise>
						<a href="/searchNotice?page=${page.curPage - 1}">Prev</a>
					</c:otherwise>
				</c:choose>
				</li>
			    <!-- 개별 페이지 -->
			    <li class="page-item, page-link" >
			    <c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
					<c:choose>
					<c:when test="${page.curPage == fEach}">
						 &nbsp; ${fEach}  &nbsp;
					</c:when>
					
					<c:otherwise>
						&nbsp;<a href="/searchNotice?page=${fEach}"> ${fEach} </a> &nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>
			    </li>
			    <!-- 다음 -->			   
			    <li class="page-item, page-link" >
			    <c:choose>
					<c:when test="${(page.curPage + 1) > page.totalPage}">
						Next
					</c:when>
					<c:otherwise>
						<a href="/searchNotice?page=${page.curPage + 1}">Next</a>
					</c:otherwise>
				</c:choose>
			    </li>
			    <!-- 끝 -->
			    <li class="page-item, page-link" >
			    <c:choose>
					<c:when test="${page.curPage == page.totalPage}">
						End
					</c:when>
					<c:otherwise>
						<a href="/searchNotice?page=${page.totalPage}">End</a>
					</c:otherwise>
				</c:choose>
			    </li>
			  </ul>
			</td>
			</c:forEach>
		</tr>
			</thead>
		</table>
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