<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<c:set var="user"><sec:authentication property="principal"/></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>ContentView</title>

    <!-- Bootstrap -->
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<!-- Custom styles for this template -->
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
		


li {
  margin: 0;
  padding: 0;
  list-style: none;
}


</style>
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
				<p class="col-10 hi Secondary">${type } 채팅 목록</p>
			</header>
		</div>
	
		<hr>
	<div class="container">
    <div class="row">
        <div class="col border">
      
                <div id="chat" class="list-group list-group-flush" style="overflow-y: auto;  height: 500px;">
		                <ul id="list" class="">
		
						</ul> 
                    <hr>
                    </div>
                    </div>

                </div>
           
        </div>
        <!-- /.col-md-4 -->
    </div>


  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

<!-- The core Firebase JS SDK is always required and must be listed first -->
 <script src="https://www.gstatic.com/firebasejs/8.6.8/firebase-app.js"></script>

<!-- TODO: Add SDKs for Firebase products that you want to use
     https://firebase.google.com/docs/web/setup#available-libraries -->
<script src="https://www.gstatic.com/firebasejs/8.6.8/firebase-analytics.js"></script>
<!-- Add Firebase products that you want to use -->
<script src="https://www.gstatic.com/firebasejs/8.6.5/firebase-auth.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.6.5/firebase-database.js"></script>



<script>
//firebase 초기화 과정
 var firebaseConfig = {
    apiKey: "AIzaSyCFKEB_NKzpL3Gjg7918sHJk3ksvEFjG6o",
    authDomain: "fcm-test-c09b0.firebaseapp.com",
    databaseURL: "https://fcm-test-c09b0-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "fcm-test-c09b0",
    storageBucket: "fcm-test-c09b0.appspot.com",
    messagingSenderId: "597270423179",
    appId: "1:597270423179:web:10beb6f19dffd00ef3d68e",
    measurementId: "G-BV1QQBWQSD"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();
  
  var chatroomname=${type};
  var inputmsg= document.getElementById('messages');
  var testref=firebase.database().ref();
  var ulList=document.getElementById('list');
  console.log("채팅방이름: "+chatroomname);
  firebase.database().ref().child('4thteam').child(chatroomname).on('child_added', snap => {
	  console.log(snap.val());
  		if(snap.exists()){
  		//timestamp 값 date 로 변환
  		if((snap.val().userName)=="manager"){
  		}else{
	  		var date=new Date(snap.val().timestamp);
	  		var year = date.getFullYear();
	  		var month = date.getMonth()+1;
	  		var day = date.getDate();
	  		var hour = date.getHours();
	  		var minute = date.getMinutes();
	  		
		  const li = document.createElement('li');
		  <%-- var content='<span><a href="/chat/managerclasschat?classid='+<%=classid%>+'&memid='+snap.val().userId+'">message from   :   '+snap.val().userId+'</a></span><span>&nbsp;&nbsp;'
		  				+year+"."+month+"."+day+"&nbsp; "+hour+":"+minute+"</span>";
		  li.innerHTML =content;
		   --%>
		  li.innerHTML ="<div class=\"row\"><a class=\"list-group-item list-group-item-action border-0 \" href=\"/admin/managerclasschat?type="+${type}+"&userName="+snap.val().userName+"\"><h4 class=\"media-heading\">'"+snap.val().userName+"'님 문의<span class=\"small pull-right\"></span></h4><p>"+snap.val().message+"<span>&nbsp;&nbsp;"+year+"."+month+"."+day+" "+hour+":"+minute+"</span></p></a></div>";

		  // 삭제, 수정시 변동사항 체크를 위하여
		  li.id = snap.key;
		  ulList.appendChild(li);
  		}
	  }
  });
</script>
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