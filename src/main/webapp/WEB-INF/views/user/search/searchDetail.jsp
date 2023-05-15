<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>

<ul class="lv0">
<li>
<div>
	<a href="javascript:fn_detail(${MAIN.QATV_ID }, ${MAIN.CHAR_LVL });"> ${MAIN.CHAR_VNF} <c:if test="${!empty MAIN.CHAR_HNF}"> [${MAIN.CHAR_HNF}]</c:if></a>
	<c:if test="${!empty MAIN.PRT_ID}"> (Xem thêm <a href="javascript:fn_detail(${MAIN.PRT_ID }, 0);"> ${MAIN.PRT_CHAR_VIET} <c:if test="${!empty MAIN.PRT_CHAR_HNF}"> [${MAIN.PRT_CHAR_HNF}]</c:if></a>)</c:if>
</div>
<p>
${MAIN.CHAR_DEF }
</p>		
</li></ul>

<ul class="lv1">
<c:forEach items="${SUB}" var="item" varStatus="state">
	<li>
		<div>
			<a href="javascript:fn_detail(${item.QATV_ID }, ${item.CHAR_LVL });"> ${item.CHAR_VNF} <c:if test="${!empty item.CHAR_HNF}"> [${item.CHAR_HNF}]</c:if></a>
		</div>
		<p>
		${item.CHAR_DEF }
		</p>		
	</li>	
</c:forEach>
</ul>		
