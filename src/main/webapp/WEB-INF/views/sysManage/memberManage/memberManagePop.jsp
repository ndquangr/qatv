<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<html>
<head>
	<meta name="decorator" content="null" />
	<title> 발주기관 찾기</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/common.css" media="all">
	<link rel="stylesheet" type="text/css" href="/resources/css/jquery-ui.css" media="all">
	<script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="/resources/js/base_query.js"></script>
</head>
<body>
<!-- <section>
<h3>
	<span class="menu_1"><a href="#" id="page_zoom"><span>화면조정</span></a>
		발주기관 찾기</span> <span class="menu_2" style="display: none;"><a
		href="#" id="page_zoom_002"><span>화면조정</span></a> 발주기관 찾기</span>
</h3>
<span class="page_navi"> > Project > <strong>발주기관 찾기</strong></span>
 --><article>
<!-- 	<h4><span></span>발주기관 List</h4> -->
	<table cellpadding="0" cellspacing="0" class="table_list">
		<thead>
			<tr>
				<th>No.</th>
				<th>고객명</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${clientList}" var="client" varStatus="ps">
			<tr>
				<td>${clientCnt - ps.index - clientCntSv}</td>
				<td><a href="javascript:find_inst('${client.INST_NM}')">${client.INST_NM}</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="table_foot">
		<!-- pase nav-->
		<p class="page_nav">
		${pageNavigator }
		</p>
	</div>
</article>
<!-- </section> -->
<script>
function find_inst(inst_name)
{
    opener.document.pform.prjt_ordr_inst.value = inst_name;
    window.close();
    return false;
}	
</script>
</body>
</html>