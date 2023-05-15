<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">

//var code =  ${not empty code.json ? code.json :'""'};
</script>
<script> var CTX = "<%=request.getContextPath()%>"; </script> 
<script type="text/javascript">


$(document).ready(function(){
	
});

function fnSearch(){
 	document.getElementById("searchForm").submit();
}
function fnReset(){
	//$("#searchTxt").val('');
	//fnSearch();
	window.close();
	window.open("/staff/nom/list", "_blank");
}
</script> 
<div  id="contents">
<form class="form_search_box validated fmt" id="searchForm" action="/staff/nom/popup" method="get">
<fieldset>
	<legend>입력 및 선택한 조건으로 검색합니다.</legend>
	<div class="group" style="margin-top: 40px;">
		<div class="group_content search">
			<table class="search_tbl" style="width: 100%;">
				<tr>
					<td>
						<div class="thl" style="text-align: left;">
							<label class="th_label" for="searchTxt">Âm Nôm</label>
							<input type="hidden" id="searchType" name="searchType" value="text"/>
							<input type="text" id="searchTxt" name="searchTxt" value="${params.searchTxt }"/>
							<!-- <span class="btn btm_search btg_black ac_click" data-func="fnSearch">Tìm</span> -->
							<button type="button" class="btn btn-success right"  onclick="fnSearch();">Search</button>
							<button type="button" class="btn btn-info right"  onclick="fnReset();">Add</button>
							<!-- <span class="btn btm_refresh ac_click" data-func="fnReset">Reset</span> -->
						</div>
					</td>
					</tr>
				</table>				
			</div>	
		</div>
	</fieldset>
</form>
<div id="grid2" style="margin-top: 40px;" class="group">
	<div class="group_content group_scroll" >		
		<table class="display table table-striped">
		        <colgroup>
			        <col width="10%" />
			        <col width="10%" />
			        <col width="10%" />
			        <col width="30%" />
			        <col width="10%" />
			        <col width="*" />					
		        </colgroup>
		        <thead>
		        	<tr>
		            	<th>Âm nôm</th>
		            	<th>Mã nôm</th>
		                <th>Mã unicode</th>
		                <th>Định nghĩa</th>
		            	<th>Hình ảnh</th>
		            </tr>
		        </thead>
		        <tbody>
				<c:choose >
				<c:when test="${!empty DATA}">
			       	<c:forEach items="${DATA}" var="item" varStatus="state">
						<tr data-code-id="${item.NOM_ID }">
							<td class="text-center">${item.CHAR_VIET}</td>
							<td class="text-center">{${item.NOM_CD}}</td>
							<td class="text-center">${item.CHAR_UNI}</td>
							<td class="text-center">${item.CHAR_DEF}</td> 
							<td class="text-center"><img src="/qatv/${item.FILE_NAME}" alt="${item.CHAR_VIET}" height="30" width="30" /></td>						
						</tr>
					</c:forEach>
				</c:when>
					<c:otherwise>
						<tr>
							<td colspan="5">Không có kết quả.</td>
						</tr>
					</c:otherwise>
				</c:choose>
		        </tbody>
			</table>
	</div>
</div>
<nav class="bbs_paging clearfix">
	${pageNavigator}	
</nav>
</div>