<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지사항</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript"
	src="/naver-editor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<style>
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

.right {
	float: right;
	text-align: right;
}
</style>
<script>
	function form_check() {
		alert("111")

		oEditors.getById["content_text"].exec("UPDATE_CONTENTS_FIELD", []);
		document.reg_frm.submit();

		if ($('#title').val().length == 0) {
			alert("제목을 입력해주세요.");
			$('#title').focus();
			return;
		}

		if ($('#content_text').val().length == 0) {
			alert("내용을 입력해주세요.");
			$('#content_text').focus();
			return;
		}

		submit_ajax();
	}

	function submit_ajax() { alert("11111");
		var queryString = $("#reg_frm").serialize();
		$.ajax({
			url : '/admin/noticeWrite',
			type : 'POST',
			data : queryString,
			dataType : 'text',
			success : function(json) {
				var result = JSON.parse(json);
				if (result.code == "success") {
					alert(result.desc)
					window.location.replace("/admin/notice");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>



</head>
<body>
	<c:forEach items="${nickname}" var="nickname">
		<nav class="navbar navbar-expand-lg navbar navbar-dark bg-dark">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarTogglerDemo03"
				aria-controls="navbarTogglerDemo03" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<a class="navbar-brand" href="#">YT</a>

			<div class="collapse navbar-collapse" id="navbarTogglerDemo03">
				<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
					<li class="nav-item active"><a class="nav-link"
						href="main.jsp">Home <span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item active"><a class="nav-link" href="/notice">공지사항</a>
					</li>
					<li class="nav-item active"><a class="nav-link" href="#">인플루언서</a>
					</li>
					<li class="nav-item active"><a class="nav-link" href="#">구인구직</a>
					</li>
					<li class="nav-item active"><a class="nav-link" href="#">굿즈</a>
					</li>
					<li class="nav-item active"><a class="nav-link" href="#">고객센터</a>
					</li>
				</ul>
				<form class="form-inline my-2 my-lg-0">
					<input class="form-control mr-sm-2" type="search"
						placeholder="Search" aria-label="Search">
					<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
					&nbsp;
				</form>
			</div>
			<div class="visible font3">
				<sec:authentication property="name" />
				&nbsp;
				<c:url value="/logout" var="logoutUrl" />
				<a href="${logoutUrl}" type="button" class="btn btn-secondary">로그아웃</a>&nbsp;
			</div>
			<div class="invisible">
				<tr>
					<td class="button">
						<button type="button" class="btn btn-secondary"
							onclick="javascript:window.location='/loginForm'">로그인</button>&nbsp;
					</td>
				</tr>
			</div>
			<div id="navbarNavDarkDropdown">
				<ul class="navbar-nav">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDarkDropdownMenuLink" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> 메뉴 </a>
						<ul class="dropdown-menu dropdown-menu-dark"
							aria-labelledby="navbarDarkDropdownMenuLink">
							<li><a class="dropdown-item" href="#">Action</a></li>
							<li><a class="dropdown-item" href="#">Another action</a></li>
							<li><a class="dropdown-item" href="#">Something else
									here</a></li>
						</ul></li>
				</ul>
			</div>
		</nav>

		<hr>

		<div class="container">
			<div class="page-header col aling-self-center font">
				<header>
					<h1>
						<a href="">YT</a>
					</h1>
				</header>
			</div>

			<hr>

			<div class="container col-12">
				<form id="reg_frm" name="reg_frm">
					<div class="input-group mb-3">
						<span class="input-group-text col-1" id="basic-addon1">작성자</span>
						<input class="form-control" id="writer" name="writer"
							readonly="true" value="${nickname}" aria-label="이름"
							aria-describedby="basic-addon1">
					</div>
					<div class="input-group mb-3">
						<span class="input-group-text col-1" id="basic-addon1">제목</span> <input
							type="text" class="form-control" id="title" name="title"
							aria-label="제목" aria-describedby="basic-addon1">
					</div>
					<div class="input-group mb-3">
						<span class="input-group-text col-12" id="basic-addon1">내용</span>
						<textarea name="content_text" id="content_text" rows="30"
							class="col"></textarea>
						<script type="text/javascript">
							var oEditors = [];
							nhn.husky.EZCreator.createInIFrame({
    							oAppRef: oEditors,
    							elPlaceHolder: "content_text",
    							sSkinURI: "/naver-editor/SmartEditor2Skin.html",
    							fCreator: "createSEditor2"
							});
						</script>
						<script
							src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
							integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
							crossorigin="anonymous"></script>
						<script
							src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
							integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
							crossorigin="anonymous"></script>
					</div>
					<div class="input-group mb-3 col-6">
						<span class="input-group-text col-3" id="basic-addon1">유튜브 아이디</span>
						<input type="text" class="form-control" id="youtube" name="youtube" aria-label="youtubeId" aria-describedby="basic-addon1">&nbsp;
						<a href="/youtube_info" target="_blank"><img src="/img/question_mark_t1.png" alt="도움말"></a>
					</div>
					<br>
					<p>
					<div class="d-flex justify-content-center">
						<input type="button" class="btn btn-dark d-flex p-2" value="취소"
							onclick="javascript:window.location='/admin/notice'">&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn btn-dark d-flex p-2" value="작성완료"
							onclick="form_check()">
					</div>

				</form>
			</div>
		</div>
	</c:forEach>
	<hr>

	<div class="p-3 mb-2 bg-dark text-white font2">
		<div class="container">
			<footer>
				이메일<br> 전화번호<br> 주소<br> <a href="">고객센터</a>
			</footer>
		</div>
	</div>

</body>
</html>