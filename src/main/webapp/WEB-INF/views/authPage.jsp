<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta name="keyword" content="">
<link href="/resources/css/font.css" rel="stylesheet">
<link href="/resources/css/cms_layout1502.css" rel="stylesheet"> 

<!-- <link href="/resources/css/font-awesome.min.css" rel="stylesheet"> -->

<!--[if IE 7]>
<link href="/resources/css/page-ie7.css" rel="stylesheet">
 <![endif]-->

<title>Login</title>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/jquery.validate.bootstrap.popover.min.js"></script>
<script>
$(function(){
	var flag = "${error}";
	if(flag == 'true'){
		alert("Information not found.");
	}
	else if(flag == 'pwd_error'){
		alert("Password incorrect.")
	}
	else if(flag == 'pwd_locked'){
		alert("Account locked.")
	}
	// 로그인 validation
	$("#loginForm").validate_popover({
		popoverPosition: 'top',
		rules : { 
			j_username:{
				required:true
			},
			j_password:{
				required:true
			}
		},
		messages: {
			j_username:{
				required:"Input your ID."
			},
			j_password:{
				required:"Input password."
			}
		}
	});
});

// 패스워드 복사 및 붙여넣기 금지 처리 [2015-10-28, 차동환K]
$(document).on('keydown', 'input[type="password"]', function(e) {
	var inputKey = String.fromCharCode(e.keyCode).toUpperCase();

	// Ctrl 키를 누른 상태에서, C 또는 V 키를 입력한 경우, 처리 종료.
	if ( e.ctrlKey==true && (inputKey == 'C' || inputKey == 'V') ) {
    	e.preventDefault();
    }
});
</script>
</head>
<body style="background:#2a3542;">

<div class="login_wrap">

<div class="col-xs-12 col-sm-12 col-md-7 login_join_box">
	<div class="log_tit"><img src="/resources/images/cms/login.png" alt="iljimage"></div>
	<form id="loginForm" class="form-inline" role="form" action="/login" method="post">
		<div class="form-group login_box">
		 	<div style="border-bottom: 1px solid #5fa1b0; width: 92%; margin: 0px auto;">
		 	  <img src="/resources/images/cms/id.png" alt="ID">
			  <label class="sr-only" for="id">ID</label>
			  <input type="text" class="form-control input-lg" id="id" placeholder="ID" name="id"  value="" >
		 	</div>
		 	<div style="width: 92%; margin: 0px auto;">
		 	  <img src="/resources/images/cms/ps.png" alt="Password">
			  <label class="sr-only" for="password">Password</label>
			  <input type="password" autocomplete="off" class="form-control input-lg" id="password" placeholder="Password" name="password" value="" oncontextmenu="return false;" />
			</div>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-primary">LOGIN</button>
		</div>
	</form>
</div>

</div>
</body>
</html>
