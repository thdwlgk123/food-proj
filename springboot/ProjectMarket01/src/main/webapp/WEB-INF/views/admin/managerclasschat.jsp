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
    		height:70vh;
    	}
    	.hi{
		font-family: 'Nanum Gothic', sans-serif;
		font-size:30px;
		  /* 마우스 오버(마우스 올렸을때) */
		 }
	
		.img-chat{
		width:40px;
		height:40px;
		}
		li,ul {
		  margin: 0;
		  padding: 0;
		  list-style: none;
		}

</style>

<script>
//엔터키가 눌렸을 때
	function enterkey() { 
		if (window.event.keyCode == 13) {  
			writeUserData();
			
			} 
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
    	<button type="button" class="btn btn-outline-warning" onclick="movehome()">로그인</button>&nbsp;
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
	<div class="page-header col font">
			<header>
				<p class="hi Secondary">${userName }님 채팅방</p>&nbsp;<p class="hi" style="font-size:15px">${type }</p>
			</header>
		</div>
		<hr>
		<div class="container">
	 
             <div class="row">
             	<div class="col border">
                <div id="chat" class="">      
                    <div id="messages" style="overflow-y: auto; width: auto; height: 500px;">
		                <ul id="list">
		
						</ul> 
                    <hr>
                    </div>
                    
           
                        <form role="form">
                        	<input type="hidden" id="classid" value='${type}'/>	
                            <div class="form-group" style="float:left;">
            
                               <input type="hidden" id="msgsender" value="<sec:authentication property="principal.Username"/>"/>
                            </div>
                            <div class="row">
                            	<div class="col" style="margin:10px">
                               	<textarea style="height: 80px;" id="messageinput"
                                    class="col" placeholder="메시지를 입력하세요." onkeyup="enterkey()" ></textarea>
                                   <button type="button" class="btn btn-outline-warning " onclick="writeUserData();">Send</button>
                               </div>
                            </div>
                         
                        </form>
                   
                </div>
            </div>
            </div>
        </div>
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
  
  var chatroomname='${type}';
  var inputmsg= document.getElementById('messages');
  var testref=firebase.database().ref();
  var ulList=document.getElementById('list');
  var userName='${userName}';
  console.log("채팅방이름"+chatroomname);
  firebase.database().ref().child('4thteam').child(chatroomname).on('child_added', snap => {
	  console.log(snap.val());
	  var manId='manager';
	  var chatId=snap.val().userName;
	  console.log("멤버 : "+userName+", 매니저 : "+manId);
  		if(snap.exists()){
  			//페이지 관리자일 경우도 추가해야함
	  		if(chatId==userName || chatId==manId){
	  			//timestamp 값 date 로 변환
		  		var date=new Date(snap.val().timestamp);
		  		var year = date.getFullYear();
		  		var month = date.getMonth()+1;
		  		var day = date.getDate();
		  		var hour = date.getHours();
		  		var minute = date.getMinutes();
		  		
			  const li = document.createElement('li');
			  if(chatId==manId){
				  li.innerHTML ="<div class=\"row\"><div class=\"col-lg-12\"><a class=\"float-right\" href=\"#\"><img class=\" img-circle img-chat\" src=\"https://w7.pngwing.com/pngs/442/17/png-transparent-computer-icons-user-profile-male-user-heroes-head-silhouette-thumbnail.png\" alt=\"\"></a><div class=\"float-right\"><h4 class=\"\">"+snap.val().userName+"<span class=\"small float-left\"></span></h4><p>"+snap.val().message+"<span>&nbsp;&nbsp;"+year+"."+month+"."+day+" "+hour+":"+minute+"</span></p></div></div></div>";				  
			  }else{
/* 			  "<span>"+snap.val().userId+":"+snap.val().message+"</span><span>&nbsp;&nbsp;"+year+"."+month+"."+day+" "+hour+":"+minute+"</span>"; */
			  li.innerHTML ="<div class=\"row\"><div class=\"col-lg-12\"><a class=\"float-left\" href=\"#\"><img class=\" img-circle img-chat\" src=\"https://w7.pngwing.com/pngs/442/17/png-transparent-computer-icons-user-profile-male-user-heroes-head-silhouette-thumbnail.png\" alt=\"\"></a><h4 class=\"media-heading\">"+snap.val().userName+"<span class=\"small float-right\"></span></h4><p>"+snap.val().message+"<span>&nbsp;&nbsp;"+year+"."+month+"."+day+" "+hour+":"+minute+"</span></p></div></div>";
			  }// 삭제, 수정시 변동사항 체크를 위하여
			  li.id = snap.key;
			  ulList.appendChild(li);
	  		}  			
	  }
  });
</script>
<script>
//realtime database에 채팅 등록
function writeUserData() {
	var chatroomname='${type}';
	var userName=$('#msgsender').val();
	var message=$('#messageinput').val();
	var timestamp=firebase.database.ServerValue.TIMESTAMP;
	console.log(userName+","+message+", "+timestamp);
	var newPostKey = firebase.database().ref().child('4thteam').child(chatroomname).push().key;
	firebase.database().ref('4thteam/' +chatroomname+'/'+newPostKey).set({
	    userName : userName,
		message : message,
		timestamp: timestamp
	  });
	console.log("writeUserData success");
	$('#messageinput').val('');

	}
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