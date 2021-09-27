<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<c:set var="user"><sec:authentication property="principal"/></c:set>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
	 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="http://code.jquery.com/jquery.js"></script>
	<script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
<title>공지사항</title>


<style>
	    @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@800&display=swap');
 .hi{
	font-family: 'Nanum Gothic', sans-serif;
	font-size:30px;
		 }
.font {
	font-color: white;
	font-weight: bold;
	font-size: 1.5em;
	text-align: center;
}

.font2 {
	font-color: white;
	font-size: 0.8em;
	text-align: center;
}

.button {
	float: right;
}

</style>
<script>
	function form_check() {
		

		var text = new CKEDITOR.dom.text( 'editor1' );

		if ($('#title').val().length == 0) {
			console.log("1th if");
			alert("제목을 입력해주세요.");
			$('#title').focus();
			return;
		}

		if ($('#bcontents').val().length == 0) {
			console.log("2nd if");
			alert("내용을 입력해주세요.");
			$('#content_text').focus();
			return;
		}

		window.location.replace("/");
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
	<hr>

		<div class="container">
			<div class="page-header col aling-self-center font">
				<header>
					<p class="col-10 hi Secondary">공지사항</p>
				</header>
			</div>

			<hr>

			<div class="container col-12">
				<form action="/admin/noticeWrite" method= "post" enctype= "multipart/form-data">
					<div class="input-group mb-3">
						<span class="input-group-text col-1" id="basic-addon1">작성자</span>
						<input class="form-control" id="bname" name="bname"
							readonly="true" value="${nickname}" aria-label="이름"
							aria-describedby="basic-addon1">
					</div>
					<div class="input-group mb-3">
						<span class="input-group-text col-1" id="basic-addon1">제목</span> <input
							type="text" class="form-control" id="btitle" name="btitle"
							aria-label="제목" aria-describedby="basic-addon1">
					</div>
					<div class="input-group mb-3">
						  <span class="input-group-text col-1" id="basic-addon1">첨부파일</span> 
						  <input class="form-control" name="file" type="file" id="formFile">
					</div>
				
					<span class="input-group-text col-1" id="basic-addon1">내용</span>
					<textarea class="form-control" name= "bcontent" id="editor1" rows="3"></textarea>
					<script>
			                        CKEDITOR.replace( 'editor1' );
	                </script>
			
					 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	    
					<br>
					<p>
					<div class="d-flex justify-content-center">
						<input type="button" class="btn btn-warning d-flex p-2" value="취소"
							onclick="javascript:window.location='/noticelist'">&nbsp;&nbsp;&nbsp;
						<input type="submit" class="btn btn-warning d-flex p-2" value="등록">
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
