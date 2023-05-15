<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/" var="baseUrl" htmlEscape="true" />

<div id="navi">
	<p class="screenName"></p>
	<c:if test="${empty menu}">
		<ul>
			<li>
				<spring:url value="/index" var="homeUrl" htmlEscape="true" /> 
				<a href="${homeUrl}" class="link"><spring:message code='navi.home'/></a>
				<span class="arrow"><img src="${pageContext.request.contextPath}/resources/img/arrow.png"></span>
			</li>
			<li id="parentPage" class="link" style="display: none;">
				<a></a>
				<span class="arrow"><img src="${pageContext.request.contextPath}/resources/img/arrow.png"></span>
			</li>
			<li><a id="currentPage"></a></li>
		</ul>
	</c:if>
</div>
<script>
	$('#currentPage').text($('title').text());
	$('.screenName').text($('title').text());
	var pathArray = window.location.pathname.split('/').filter(function(el) {return el.length != 0 && el != 'modify' });
	var parentPath = pathArray.length >=2 ? pathArray[pathArray.length - 2] : '';
	var newPath = '${baseUrl}' + parentPath;
	
	
	switch (parentPath) {
		case 'user-manage':
			parentPath = '<spring:message code="title.userManage"/>';
			$('#parentPage a').attr('href',newPath);
			$('#parentPage a').text(parentPath);
			$('#parentPage').css('display', 'inline');
			break;
		case 'license-manage':
			parentPath = '<spring:message code="title.licenseManage"/>';
			$('#parentPage a').attr('href',newPath);
			$('#parentPage a').text(parentPath);
			$('#parentPage').css('display', 'inline');
		default:
			parentPath = null;
	}
	
</script>