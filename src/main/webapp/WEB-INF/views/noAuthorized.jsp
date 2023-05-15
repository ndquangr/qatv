<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %> --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page session="false" %>

<c:set var="url" value='${requestScope["javax.servlet.forward.request_uri"]}' />

<sec:authorize access="authenticated">
	<sec:authentication property="principal.userID" var="userID"/>
	<sec:authentication property="principal.username" var="userNM" />
	<sec:authentication property="principal.userSeq" var="userSeq" />
	<sec:authentication property="principal.authorities" var="authorities"/>
	<c:set var="hasUser" value="Y"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>
		<c:choose>
			<c:when test="${fn:contains(url, '/eng/')}">No access</c:when>
			<c:otherwise>접근권한 없음</c:otherwise>
		</c:choose>
	</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="decorator" content="null" />
</head>

<body class="lock-screen" onload="startTime()">
		<c:choose>
			<c:when test="${fn:contains(url, '/eng/')}">
				<div class="lock-box text-center">
		            <h1><strong>You do not have permission.</strong></h1>
		            <div class="form-group col-lg-12">
		            	<span class="locked"><a class="btn btn-lock" href="/eng/">Go to main page</a></span>
		            </div>
		        </div>
			</c:when>
			<c:otherwise>
				<div class="lock-box text-center">
		            <h1><strong>접근 권한이 없습니다.</strong></h1>
		            <div class="form-group col-lg-12">
		            	<span class="locked"><a class="btn btn-lock" href="/">메인페이지로 이동하기</a></span>
		            </div>
		        </div>
			</c:otherwise>
		</c:choose>
    <script>
        function startTime()
        {
            var today=new Date();
            var h=today.getHours();
            var m=today.getMinutes();
            var s=today.getSeconds();
            // add a zero in front of numbers<10
            m=checkTime(m);
            s=checkTime(s);
            document.getElementById('time').innerHTML=h+":"+m+":"+s;
            t=setTimeout(function(){startTime()},500);
        }

        function checkTime(i)
        {
            if (i<10)
            {
                i="0" + i;
            }
            return i;
        }
    </script>
</body>

</html>