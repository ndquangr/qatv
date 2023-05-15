<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/logout" var="logoutUrl" htmlEscape="true" />

<div id="error-page">
	<p class="error" style="display: inline-block; font-size: 15px"><spring:message code='common.error'/></p>
	<span style="display: block; margin-top: 1em">
		<input type="button" value="<spring:message code='login.submit.value'/>">
	</span>
</div>

<script>
	$('input[type="button"]').on('click',function(){
		window.location.href = '${logoutUrl}';
	});
</script>