<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="/" var="baseUrl" htmlEscape="true" />
<div id="home-menu">
	<ul class="left-col">
		<li id="license-manage">
			<spring:url value="/license-manage" var="licenseManageUrl" htmlEscape="true" />
			<a href="${licenseManageUrl}"><img src="${baseUrl}resources/img/license-management-1.png"><br><spring:message code="title.licenseManage"/></a>
		</li>
		
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<li id="user-manage">
				<spring:url value="/user-manage" var="userManageUrl" htmlEscape="true" />
				<a href="${userManageUrl}"><img src="${baseUrl}resources/img/user-management-1.png"><br><spring:message code="title.userManage"/></a>
			</li>
		</sec:authorize>
	</ul>
	
	<ul class="right-col">
		<sec:authorize access="hasRole('ROLE_MOD')">
			<li id="add-license">
				<spring:url value="/modify/add-license" var="addLicenseUrl" htmlEscape="true" />
				<a href="${addLicenseUrl}"><img src="${baseUrl}resources/img/license-registration-1.png"><br><spring:message code="title.addLicense"/></a>
			</li>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<li id="add-user">
				<spring:url value="/add-user" var="addUserUrl" htmlEscape="true" />
				<a href="${addUserUrl}"><img src="${baseUrl}resources/img/user-registration-1.png"><br><spring:message code="title.addUser"/></a>
			</li>
		</sec:authorize>
	</ul>
</div>

<script>
	$('#license-manage').mouseover(function() {
		var src = $('#license-manage img').attr("src").replace('license-management-1.png','license-management.png');
		$('#license-manage img').attr("src", src);
	});
	
	$('#license-manage').mouseleave(function() {
		var src = $('#license-manage img').attr("src").replace('license-management.png','license-management-1.png');
		$('#license-manage img').attr("src", src);
	});
	
	$('#user-manage').mouseover(function() {
		var src = $('#user-manage img').attr("src").replace('user-management-1.png','user-management.png');
		$('#user-manage img').attr("src", src);
	});
	
	$('#user-manage').mouseleave(function() {
		var src = $('#user-manage img').attr("src").replace('user-management.png','user-management-1.png');
		$('#user-manage img').attr("src", src);
	});
	
	$('#add-license').mouseover(function() {
		var src = $('#add-license img').attr("src").replace('license-registration-1.png','license-registration.png');
		$('#add-license img').attr("src", src);
	});
	
	$('#add-license').mouseleave(function() {
		var src = $('#add-license img').attr("src").replace('license-registration.png','license-registration-1.png');
		$('#add-license img').attr("src", src);
	});
	
	$('#add-user').mouseover(function() {
		var src = $('#add-user img').attr("src").replace('user-registration-1.png','user-registration.png');
		$('#add-user img').attr("src", src);
	});
	
	$('#add-user').mouseleave(function() {
		var src = $('#add-user img').attr("src").replace('user-registration.png','user-registration-1.png');
		$('#add-user img').attr("src", src);
	});
</script>