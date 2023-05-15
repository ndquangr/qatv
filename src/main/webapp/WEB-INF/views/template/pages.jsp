<%@page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/" var="baseUrl" htmlEscape="true" />

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${pageTitle}"/></title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico"/>

<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css" media="screen" />
<%-- <link href="${pageContext.request.contextPath}/resources/css/jquery.spin.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath}/resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath}/resources/js/jquery-ui-1.11.2.custom/jquery-ui.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/parsley.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.spin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js"></script> --%>


<link href="/resources/css/bootstrap.min150225.css" rel="stylesheet">
<link href="/resources/css/bootstrap20151019.css" rel="stylesheet">
<link href="/resources/css/font.css" rel="stylesheet">
<link href="/resources/css/cms_layout1502.css" rel="stylesheet">
<!-- <link href="/resources/css/krdic.css" rel="stylesheet"> -->

<!--[if lte IE 8]>
<script src="/resources/js/html5shiv.js"></script>
<link href="/resources/css/kisti-ie7.css" rel="stylesheet">
<![endif]-->

<!--[if lt IE 8]>
<link href="/resources/css/bootstrap-ie7.css" rel="stylesheet">
<link href="/resources/css/page-ie7.css" rel="stylesheet">
<![endif]-->

<script type="text/javascript" src="/resources/js/jquery-latest.js"></script>
<script type="text/javascript" src="/resources/js/jquery-1.12.4.min.js"></script>
<script src="/resources/js/adminCommon.js"></script>
<script type="text/javascript" src="/resources/js/validateCommon.js"></script>
<script src="/resources/js/bootstrap-datepicker.js"></script>

</head>
<body>
	<div class="wrap">
		<!-- Header -->
		<tiles:insertAttribute name="header" />
	
		<!-- Navi -->
		<%-- <tiles:insertAttribute name="navi" /> --%>
		<!-- Body Page -->
		<div id="contents">
			<tiles:insertAttribute name="body" />
		</div>
		<div class="push"></div>
	</div>
	<!-- Footer Page -->
	<tiles:insertAttribute name="footer" />
	
	<div id="spining">
		<div class="spin" data-spin style="border-radius: 15%"></div>
	</div>
</body>
</html>