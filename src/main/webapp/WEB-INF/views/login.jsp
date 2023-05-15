<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String lang = (new RequestContext(request)).getLocale().getLanguage();%>

<div class="login">
	<c:if test="${not empty error}">
		<p class="error">${error}</p>
	</c:if>
	<c:if test="${not empty logoutSucess}">
		<p class="msg">${logoutSucess}</p>
	</c:if>

	<form id="login" action="<c:url value='/login' />" method="post">
		<input type="text" placeholder="<spring:message code='login.placeholder.userName'/>" id="username" name="username" autocomplete="off" maxlength="100">
		<input type="password" placeholder="<spring:message code='login.placeholder.password'/>" id="password" name="password" maxlength="100">
		<!-- 		<a href="#" class="forgot">forgot password?</a>  -->
		<input type="submit" value="<spring:message code='login.submit.value'/>" name="submit">
	</form>
</div>

<script type="text/javascript">
	$('#login').parsley({
		errorsWrapper : '<span class="validate login-page"></span>',
		errorTemplate : "<p></p>"
	});

	$('#username').attr('data-parsley-maxlength', '100');
	$('#username').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');
	
	$('#username').attr('data-parsley-required', 'true');
	$('#username').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');
	
	$('#password').attr('data-parsley-maxlength', '100');
	$('#password').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');
	
	$('#password').attr('data-parsley-required', 'true');
	$('#password').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');
	
	$('#language').on('change',function(){
		var locale = $(this).val();
		var url = window.location.pathname;
		url = url + '?lang='+ locale;
		window.location.href = url;
	});
	
	$(document).ready(function(){
		$('#language').val("<%=lang%>");
	});
	
	function getURLParameter(name) {
		  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
	}
</script>