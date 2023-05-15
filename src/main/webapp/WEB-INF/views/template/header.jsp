<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="header">
	<!-- <h1> -->
	<spring:url value="/" var="homeUrl" htmlEscape="true" />
	
	<a class="btn btn-info" type="button" href="/"><i class="glyphicon glyphicon-home"></i></a>	
	
	<!-- </h1> -->
	
	<sec:authorize access="isAuthenticated()">
		<a class="btn btn-info" href="/staff/qatv/list">Quốc Âm Tự Vị</a>
		<a class="btn btn-info" href="/staff/nom/list">Chữ Nôm</a> 
		<div class="user_info">
			<ul>
				<li style="font-size: medium; display: inline">
					<%-- <spring:message code='header.loginAs'/>
					<spring:url value="/logout" var="logoutUrl" htmlEscape="true" />&nbsp; --%>
					<span style="max-width: 300px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;font-size: medium;">
						Hello <sec:authentication property="principal.username" />
					</span> 
					<a class="btn btn-warning" href="/logout"><spring:message code='header.logout'/></a>
				</li>
			</ul>
		</div>
	</sec:authorize>
	<sec:authorize access="!isAuthenticated()">
		<div class="user_info">
			<ul>
				<li style="font-size: medium; display: inline">
					<a class="btn btn-info" href="/pageAuth">Login</a>
				</li>
			</ul>
		</div>
	</sec:authorize>
</div>