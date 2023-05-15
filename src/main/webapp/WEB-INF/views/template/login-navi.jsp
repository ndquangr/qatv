<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="navi">
	<p class="screenName"><spring:message code='title.login' /></p>
	<div class="language">
		<label for="language"><spring:message code='login.label.language' /></label> <select id="language">
			<option value="ko">한국어</option>
			<option value="en">English</option>
		</select>
	</div>
</div>
