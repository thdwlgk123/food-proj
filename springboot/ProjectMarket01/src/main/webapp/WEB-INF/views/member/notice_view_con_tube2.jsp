<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset= UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>공지사항</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery.js"></script>
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
		if ($('#reply_content').val().length == 0) {
			alert("댓글에 내용을 입력해주세요.");
			$('#reply_content').focus();
			return;
		}

		submit_ajax();
	}

	function submit_ajax() {
		var queryString = $("#reg_frm").serialize();
		$.ajax({
			url : '/admin/writereply',
			type : 'POST',
			data : queryString,
			dataType : 'text',
			success : function(json) {
				var result = JSON.parse(json);
				if (result.code == "success") {
					window.location
							.replace("/admin/noticeView?id=${noticeView.id}");
				} else {
					alert(result.desc);
				}
			}
		});
	}
</script>
</head>
<body>
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
				<li class="nav-item active"><a class="nav-link" href="main.jsp">Home
						<span class="sr-only">(current)</span>
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
		<div hidden="true">
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
						<li><a class="dropdown-item" href="#">Something else here</a></li>
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

		<div class="d-flex justify-content-end">
			<button type="button" class="btn btn-dark btn-sm d-flex p-2"
				onclick="javascript:window.location='/admin/notice'">목록보기</button>
		</div>

		<table class="table table-hover table-dark">
			<tr>
				<td>번호</td>
				<td>${noticeView.id}</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${noticeView.writer}</td>
			</tr>
			<tr>
				<td>조회수</td>
				<td>${noticeView.hit}</td>
			</tr>
			<tr>
				<td><h3>제목</h3></td>
				<td><h3>${noticeView.title}</h3></td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>${noticeView.createDate}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<div id="player"></div> <script type="text/javascript">
						// 2. This code loads the IFrame Player API code asynchronously.
						var tag = document.createElement('script');

						tag.src = "https://www.youtube.com/iframe_api";
						var firstScriptTag = document
								.getElementsByTagName('script')[0];
						firstScriptTag.parentNode.insertBefore(tag,
								firstScriptTag);

						// 3. This function creates an <iframe> (and YouTube player)
						//    after the API code downloads.
						var player;
						function onYouTubeIframeAPIReady() {
							player = new YT.Player('player', {
								height : '576',
								width : '1024',
								videoId : '${noticeView.youtube}',
								events : {
									'onReady' : onPlayerReady,
									'onStateChange' : onPlayerStateChange
								}
							});
						}

						// 4. The API will call this function when the video player is ready.
						function onPlayerReady(event) {
							event.target.playVideo();
						}

						// 5. The API calls this function when the player's state changes.
						//    The function indicates that when playing a video (state=1),
						//    the player should play for six seconds and then stop.
						var done = false;
						function onPlayerStateChange(event) {
							if (event.data == YT.PlayerState.PLAYING && !done) {
								setTimeout(stopVideo, 6000);
								done = true;
							}
						}
						function stopVideo() {
							player.stopVideo();
						}
					</script><br> ${noticeView.content_text}
				</td>
			</tr>
		</table>
		<table class="table table-dark">
			<form id="reg_frm" name="reg_frm">
				<div class="input-group mb-3" hidden="true">
					<span class="input-group-text col-1" id="basic-addon1">작성자ID</span>
					<input class="form-control" id="userId" name="userId"
						readonly="true" value="${uDto.id}" aria-label="id"
						aria-describedby="basic-addon1">
				</div>
				<div class="input-group mb-3" hidden="true">
					<span class="input-group-text col-1" id="basic-addon1">작성자</span> <input
						class="form-control" id="writer" name="writer" readonly="true"
						value="${uDto.nickName}" aria-label="닉네임"
						aria-describedby="basic-addon1">
				</div>
				<div class="input-group mb-3" hidden="true">
					<span class="input-group-text col-1" id="basic-addon1">save_place</span>
					<input class="form-control" id="viewPart_id" name="viewPart_id"
						readonly="true" value="${noticeView.id}" aria-label="save_place"
						aria-describedby="basic-addon1">
				</div>
				<div class="input-group mb-3">
					<textarea name="reply_content" id="reply_content" rows="2"
						class="col"></textarea>
					<button type="button" class="btn btn-secondary btn-lg col-2"
						onclick="form_check()">댓글 달기</button>
				</div>
			</form>

			<c:forEach items="${rDto}" var="rDto">
				<tr>
					<td class="col-1">${rDto.writer}</td>
					<td class="col">${rDto.reply_content}</td>
				</tr>
			</c:forEach>

		</table>
	</div>

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