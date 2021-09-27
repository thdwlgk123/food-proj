<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery.js"></script>
<title>Insert title here</title>

<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@800&display=swap');


<style>

	ul{
	
		list-style:none;	
	}
	li{
		float:left;
	
		padding-right:40px;
	}
	
	.hi{
		font-family: 'Nanum Gothic', sans-serif;
		font-size:30px;
		
	}
	.foot{
    	 bottom:0;
    	
    	} 
    	.body2{
    		height:80vh;
    	}
	
</style>
<script>
function fun_delete(bId)
{
	var bId = "bId="+bId;
	$.ajax({
		url: '/admin/deleteNotice',
		type: 'POST',
		data: bId,
		dataType: 'text',
		success: function(json) {
			var result = JSON.parse(json);
			if(result.code=="success") {
				alert(result.desc);
				window.location.href="/noticelist";
			} else {
				alert(result.desc);
			}
		}
	});
		
}

 
function fun_modify(bId)
{
		window.location.href='/admin/modifyNoticeForm?bId='+bId;

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
      <span class="navbar-text">
    	<sec:authorize access="!isAuthenticated()">
    	<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/loginForm'">로그인</button>&nbsp;
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
		 <span class="navbar-text">
   			<a class="text-secondary"  href="/myPageMain?c_id=<sec:authentication property="principal.Username"/>">마이페이지</a>&nbsp;&nbsp;
   		 </span>
   	 		<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/logout'">로그아웃</button>&nbsp;
		</sec:authorize>
    </span>
  </div>
</nav>

<hr>

<div class="container">
<div class="row"></div>
</div>

	<div class="container">
		<c:forEach var="noticeList" items="${noticeList}">
		<p class="hi Secondary">공지사항</p>
			<table class="table table-bordered table-striped shadow">
			
	 			<tr>
	 				<td width="100px">제목</td>
	 				<td colspan="5" ><p>${noticeList.btitle}</p></td>
	 			</tr>
	 			
	 			<tr>
	 				<td>등록일</td>
	 				<td>
	 					${noticeList.bdate}&nbsp;&nbsp;
	 				</td>
	 				<td>작성자</td>
	 				<td>${noticeList.bname}</td>
	 				<td width="100px">조회수</td>
	 				<td>${noticeList.bhit}</td>
	 			</tr>
	 			<tr>
	 				<td>첨부파일</td>
	 				<td><a href="/notice/download?fName=${noticeList.fileName}">${noticeList.fileName}</a></td>
	 			</tr>
	 			
	 			<tr>
	 				<td>내용</td>
	 				<td colspan="5" style="height:300px" >${noticeList.bcontent}</td>
	 			</tr>
	 		
			</table>
			<div class="row">
				<button type="button" class="btn btn-outline-warning" onclick="javascript:window.location='/noticelist'">목록보기</button>
				<c:set var="name" value="${noticeList.bname}" />
			    <c:if test="${name == '관리자'}">
					<button class="btn btn-primary" type="button" onclick="fun_modify(${noticeList.bid});" style="margin-left:10px">수정</button>
					<button class="btn btn-primary" type="button" onclick="fun_delete(${noticeList.bid});" style="margin-left:10px">삭제</button>
				</c:if>
			</div>
			</c:forEach>
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
	  <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
	
    
</body>
</html>