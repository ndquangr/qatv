<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false" %>
<html>
<head>
<title>관리자 관리</title>
<script type="text/javascript" src="/resources/js/bbsCommon.js"></script>
<script>
$(function(){
	// 검색어 입력 필드에서 엔터키 감지
	$("#searchTxt").keypress(function(e) {
		if ((e.keyCode == 13)) {
			return fn_search(document.clientSearchForm_memberSearch);
	    }
	});
});

$(document).on('change', 'input[type="radio"]', function(e) {
	return fn_search(document.clientSearchForm_memberSearch);
});

function fn_search(form) {
	var searchType = form.searchType.value;
	var searchTxt = encodeURIComponent(form.searchTxt.value);
	var searchRol = form.searchRol.value;
	window.open(form.action + "?searchType=" + searchType + "&searchTxt=" + searchTxt + "&searchRol=" + searchRol, "_self");
	return false;
}

function goDelete(user_id){
	if(confirm("Submit this information?")){
		$("#deleteForm").attr("action", "/sys/memberManage/member/" + user_id + "/delete");
		$("#deleteForm #user_id").val(user_id);
		$("#deleteForm").submit();
	}
}
</script>
</head>
<body>
<form name="deleteForm" id="deleteForm" method="post" >
	<input type="hidden" name="user_id" id="user_id" />
</form>
<!-- 페이지 타이틀 -->
<div>
	<section class="panel">
		<header class="panel-heading">Administration Page</header>
		<div class="panel-body">
		
		<!-- 검색영역 -->
		<form id="clientSearchForm_memberSearch" name="clientSearchForm_memberSearch" action="/sys/memberManage/members" > <!-- style="display: inline-block;" -->
			<div class="row cc" >
				<div class="col-sm-12 col-lg-offset-4 col-lg-4">
				<div class="search_area"> 	
					<div class="label_t">
		                  <label class="label_radio radio-inline"><input type="radio" name="searchRol" id="searchRol_0" value="" <c:if test="${searchRol == null || searchRol == ''}">checked</c:if>> All</label>
		                  
							<c:forEach items="${roleList}" var="auth" varStatus="state">
									<c:if test="${auth.ROL_MNG_ID != 'ROLE_ANONYMOUS'}">
										<label class="label_radio radio-inline"><input type="radio" name="searchRol" id="searchRol_${state.index + 1 }" value="${auth.ROL_MNG_ID}" <c:if test="${searchRol == auth.ROL_MNG_ID}">checked</c:if>> ${auth.ROL_MNG_NM}</label>
									</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="botbox botser stats_input"> 
						<ul >  
							<li>							
								<!-- <span class="input-group-addon">회원명</span> -->
								<select id="searchType" name="searchType" class="form-control6" onchange="fn_search(this.form);">
									<option value="user_nm" <c:if test="${searchType == 'user_nm' }">selected="selected"</c:if>>Name</option>
								</select>
							</li>							
							<li><input type="text" class="form-control" id="searchTxt" name="searchTxt" value="${searchTxt }" /></li>
							<li><span class="input-group-btn">
							<!--  
							<button class="btn btn-default" type="submit">SEARCH</button>
							-->
							<button class="btn btn-default btn-sm" type="button" onclick="fn_search(this.form);"><i class="glyphicon glyphicon-search"></i></button>
							</span></li>
						</ul>
					</div><!-- /input-group --> 
				</div><!-- /.col-lg-6 -->
			</div><!-- /.row -->
		</form> 
		
        <div class="adv-table">
		<table class="display table table-striped">
			<thead>
				<tr>
					<th class="text-center">No.</th>					
					<th class="text-center">ID</th>
					<th class="text-center">Email</th>
					<th class="text-center">Role</th>
					<th class="text-center">Phone</th>
					<th class="text-center">Reg. Date</th>
					<th>
					<!-- <a href="/sys/memberManage/member/write" data-placement="top" title="회원 추가">회원 추가<span class="glyphicon glyphicon-plus-sign btn-xs"></span></a> -->
					</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${clientList}" var="user" varStatus="ps">
	        	<tr>
	        		<td class="text-center">${clientCnt - ps.index - ((cPage-1) * (intListCnt))}</td>
					<td class="text-center">
						<a href="/sys/memberManage/member/${user.USER_ID}/edit" data-placement="top" title="수정">${user.USER_NM}</a>
						<c:if test="${user.PWD_ERR_CNT >= 5}">
							<span class="glyphicon glyphicon-lock btn-xs"></span>
						</c:if>
					</td>
					<td><a href="/sys/memberManage/member/${user.USER_ID}/edit" data-placement="top" title="수정">${user.EML}</a></td>
					<td class="text-center">${fn:substring(user.USER_ROL,0,fn:length(user.USER_ROL)-1) }</td> 
					<td class="text-center">${user.CLPN1} - ${user.CLPN2} - ${user.CLPN3}</td>
					<td class="text-center">${fn:substring(user.MEM_JOIN_DTTM, 0, 10) }</td>
					<td class="text-center">
						<a href="/sys/memberManage/member/${user.USER_ID}/edit" data-placement="top" title="수정"><span class="glyphicon glyphicon-edit btn-xs"></span></a>
						<a href="javascript:goDelete('${user.USER_ID}');" data-placement="top" title="삭제"><span class="glyphicon glyphicon-trash btn-xs"></span></a>
					</td> 
	        	</tr>
	        	</c:forEach>
			</tbody>
		</table>
		</div>

		<div class="text-center">
			${pageNavigator }
		</div>
		<p class="text-right">
			<a role="button" href="/sys/memberManage/member/write" class="btn btn-primary">Add</a>
		</p>
		</div>
		
	</section>
</div>
</body>
</html>
