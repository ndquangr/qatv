<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false"%>
<sec:authorize access="authenticated">
	<sec:authentication property="principal.userID" var="userID"/>
	<sec:authentication property="principal.password" var="userPW"/>
	<sec:authentication property="principal.username" var="userNM" />
	<%-- <sec:authentication property="principal.userSeq" var="userSeq" /> --%>
	<sec:authentication property="principal.authorities" var="authorities"/>
	<c:set var="hasUser" value="Y"/>
</sec:authorize>
<html>
<head>
<title> 관리자 관리</title>
<script src="/resources/js/jquery.validate.min.js"></script>
<script src="/resources/js/jquery.validate.bootstrap.popover.min.js"></script>
<script src="/resources/js/validateCommon.js"></script>
<script>
$(function(){
	
	$("#member_write").validate_popover({
		popoverPosition: 'top',
		rules : { 
			user_id:{
				required:true
				/* , remote: {
					type: "GET",
		            url: "/web-commons/checkUserId",
		            data:{"user_id":function(){
		            		return $("#member_write #user_id").val();
		            	}
		            }
				} */
			},
			eml:{
				required:true,
				email:true
			},
			user_nm:{
				required:true
			},
			pwd:{
 				required:true
 			},
 			pwd_confirm:{
 				equalTo:"#pwd"
 			}
		},
		messages: {
			user_id:{
				required:"아이디는 반드시 입력해주셔야합니다.",
				remote:"이미 가입되어 있는 아이디입니다."
			},
			eml:{
				required:"이메일은 반드시 입력해주셔야합니다.",
				email:"올바른 이메일 형식이 아닙니다."
			},
			user_nm:{
				required:"회원명은 반드시 입력해야 합니다."
			},
 			pwd:{
 				required:"비밀번호를 입력해주세요."
 			},
 			pwd_confirm:{
 				equalTo:"비밀번호가 일치하지 않습니다."
 			}
		}
	});
	
	$("#modal-close").click( function () {
		$('div#validate-popover').hide();
	});
	
});

function goInsert(){
	
	var user_id = $('#user_id').val();
	var user_nm = $('#user_nm').val();
	var pwd = $('#pwd').val();
	var pwd_confirm = $('#pwd_confirm').val();
	
	if(isNotEmpty(user_id)) {
		if(fn_specialCharacterValidation(user_id)) {
			alert("허용하지 않는 문자가 존재합니다.");
			$('#user_id').focus();
			return false;
		}
		
		if(user_id == "anonymous") {
			alert("허용되지 않는 ID입니다.");
			$('#user_id').focus();
			return false;
		}
	}
	
	if(isNotEmpty(pwd)) {
		/* if(!fn_pwdValidation(pwd)) {
			alert("비밀번호는 8~15자리의 대문자, 소문자, 특수문자, 숫자를 포함하고 있어야 합니다.");
			$('#pwd').focus();
			return false;
		} */
	}
	
	if(isNotEmpty(pwd_confirm)) {
		/* if(!fn_pwdValidation(pwd_confirm)) {
			alert("비밀번호는 8~15자리의 대문자, 소문자, 특수문자, 숫자를 포함하고 있어야 합니다.");
			$('#pwd_confirm').focus();
			return false;
		} */
	}
	
	if(isNotEmpty(user_nm)) {
		if(fn_specialCharacterValidation(user_nm)) {
			alert("허용하지 않는 문자가 존재합니다.");
			$('#user_nm').focus();
			return false;
		}
	}

	if ( confirm("등록하시겠습니까?") ) {
		$("#member_write").attr('action','/sys/memberManage/member/new').submit();
	}
}
</script>
</head>
<body>
<div>
	<section class="panel">
		<header class="panel-heading">관리자 추가</header>
		<div class="panel-body " >
			<form name="member_write" id="member_write" class="form-horizontal" method="post" action="/sys/memberManage/member/new">
				<div class="form-group">
					<label for="user_id" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 회원ID</label>
					<div class="col-sm-10 col-lg-10">
						<input type="text" class="form-control" id="user_id" name="user_id" placeholder="아이디" value="" />
					</div>
				</div>
				<div class="form-group">
					<label for="eml" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> E-mail</label>
					<div class="col-sm-10 col-lg-10">
						<input type="text" class="form-control" id="eml" name="eml" placeholder="Email" value="" />
					</div>
				</div>
				<div class="form-group">
					<label for="pwd" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 비밀번호</label>
					<div class="col-sm-10 col-lg-10">
						<input type="password" class="form-control" id="pwd" name="pwd" placeholder="비밀번호" value="" maxlength="15" />
					</div>
				</div>
				<div class="form-group">
					<label for="pwd_confirm" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 비밀번호 재확인</label>
					<div class="col-sm-10 col-lg-10">
						<input type="password" class="form-control" id="pwd_confirm" name="pwd_confirm" placeholder="비밀번호 재입력" value="" maxlength="15" />
					</div>
					
					<!-- <label class="col-sm-2 col-lg-2"> </label> -->
					<div class="col-lg-offset-2 col-xs-8 col-sm-8 help-block">
						<span class="fa  fa-warning"></span> 패스워드 수정시에만 입력하세요.
					</div>
				</div>
				<div class="form-group">
					<label for="user_nm" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 회원명</label>
					<div class="col-sm-10 col-lg-10">
						<input type="text" class="form-control" id="user_nm" name="user_nm" placeholder="사용자 이름" value=""/>
					</div>
				</div>
				<div class="form-group">
					<label for="clpn1" class="col-sm-2 col-lg-2 control-label">휴대전화</label>
					<div class="col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="clpn1" name="clpn1" placeholder="국번" maxlength="3" value="" />
					</div>
					<div class="col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="clpn2" name="clpn2" placeholder="휴대폰 번호" maxlength="4" value=""/>
					</div>
					<div class="col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="clpn3" name="clpn3" placeholder="휴대폰 번호" maxlength="4" value=""/>
					</div>
				</div>
				<div class="form-group">
					<label for="tel_no1" class="col-sm-2 col-lg-2 control-label">직장전화</label>
					<div class="col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="tel_no1" name="tel_no1" placeholder="지역번호" maxlength="3" value="" />
					</div>
					<div class="col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="tel_no2" name="tel_no2" placeholder="전화 번호" maxlength="4" value=""/>
					</div>
					<div class="col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="tel_no3" name="tel_no3" placeholder="전화 번호" maxlength="4" value=""/>
					</div>
				</div>
				<div class="form-group">
					<label for="tel_no1" class="col-sm-2 col-lg-2 control-label">팩스번호</label>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="fax_no1" name="fax_no1" placeholder="지역번호" maxlength="3" value=""/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="fax_no2" name="fax_no2" placeholder="팩스 번호" maxlength="4" value=""/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="fax_no3" name="fax_no3" placeholder="팩스 번호" maxlength="4" value=""/>
					</div>
				</div>
				<c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')>-1 }">
				<div class="form-group">
					<label for="user_nm" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 접속권한</label>
					<div class="col-sm-10 col-lg-10">						
						<c:forEach items="${roleList}" var="auth" varStatus="state">
							<c:if test="${auth.ROL_MNG_ID != 'ROLE_ANONYMOUS'}">
								<label class="checkbox-inline_o2"> 
								  <input type="checkbox" name="rol_mng_id" id="${auth.ROL_MNG_ID}" value="${auth.ROL_MNG_ID}">${auth.ROL_MNG_NM}
								</label>
							</c:if>
						</c:forEach>
					</div>
				</div>
				</c:if>
				<div class="form-group text-center">
					<button type="button" class="btn btn-success"  onclick="goInsert();">등 록</button>
					<a class="btn btn-warning" href="/sys/memberManage/members" role="button" >취 소</a>
					<a class="btn btn-info" href="/sys/memberManage/members" role="button" >목 록</a>
				</div>
			</form>
		</div>
	</section>
</div>
<div id="page-modal"></div>
</body>
</html>
