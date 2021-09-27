<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
<!-- Required meta tags -->
    <meta http-equiv="Content-Type" content="text/html; charset= UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"	crossorigin="anonymous"></script>
	<script src="http://code.jquery.com/jquery.js"></script>
    <title>Hello, world!</title>
     
  </head>
  <body>
    <nav class="navbar navbar-expand" style="background-color:#D7E5E5;">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	 
	    <a class="navbar-brand" href="/">맛집식당</a>
	    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		  <span class="navbar-toggler-icon"></span>
		</button>
	 

	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	        <li class="nav-item active"><a class="nav-link" href="/">Home
					<span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link" href="/admin/notice">예약현황</a>
			</li>
			<li class="nav-item active"><a class="nav-link" href="#">포장예약현황</a>
			</li>
			<li class="nav-item active"><a class="nav-link" href="#">리뷰조회</a>
			</li>
	      </ul>
	      <form class="form-inline">
				<input class="form-control mr-sm-2" type="search"
					placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>&nbsp;
		  </form>	    
	    </div><!-- /.navbar-collapse -->
	    <div class="visible font3">
			<sec:authentication property="name"/>&nbsp;
			
			<a href="/logout" type="button" class="btn btn-info">로그아웃</a>&nbsp;
		</div>
		<div class="invisible">
			<tr>
				<td class="button">
					<button type="button" class="btn btn-info" onclick="javascript:window.location='/loginForm'">로그인</button>&nbsp;
				</td>
			</tr>
		</div>
	  
	  </div><!-- /.container-fluid -->
	</nav>
	</p>
	<div class="text-center">
	  <img src="../img/sponge.gif" class="rounded"/>
	</div>
	</p>
	<div class="p-3 mb-2 font2" style="background-color:#D7E5E5">
		<div class="container">
			<footer>
				이메일 : kmdadoo@naver.com <br> 전화번호 : 010-9273-9992 <br> 주소 : 서울 성북구 종암로 24가길 53,<br> <a href="">고객센터 : 010-9273-9992 </a>
			</footer>
		</div>
	</div>
    
  </body>
</html>