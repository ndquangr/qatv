<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>

<html>
	<head>
	<title>시스템 공통코드 관리</title>
	<style>
		.tab_index{height:60px;border:3px solid #ebebeb;}
		.tab_index li{float:left;margin-top:5px;padding:0 4px 0 3px;background:url(/resources/images/sp_bullet.gif) 0 -302px no-repeat;}
		.tab_index li.fst{background:none}
	</style>
	<script type="text/javascript">
				
		function fn_search(form) {
			var searchType = form.searchType.value;
			var searchTxt = encodeURIComponent(form.searchTxt.value);			
			window.open(form.action + "?searchType=" + searchType + "&searchTxt=" + searchTxt, "_self");
			return false;
		}
		
		function fn_refresh(form) {
			window.open(form.action, "_self");
			return false;
		}
		
		function fn_detail(id, lv) {
			var _url = "/guest/detail";
			var _data = "id=" + id + "&lv=" + lv;
			//ajaxReplaceHtmlWithModal("#code-modal", params, "/staff/nom/" + code_id + "/edit", "#code_form");
			$.ajax({
				type: "GET",
				url: _url,
				cache: false,
				data:_data,
				success: function(data) {
					$("#search_result").html("");					
					$("#word_detail").html(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) { 
			        alert("Status: " + textStatus); alert("Error: " + errorThrown); 
			    }  
			});
		}
	</script>
</head>

<body>
<form name="deleteForm" id="deleteForm" method="post"></form>
<div>
	<section class="panel">		
		<div class="panel-body">
		<!-- 검색영역 -->
		<form id="formSearch" name="formSearch" action="/guest/search" > <!-- style="display: inline-block;" -->
			<div class="row cc" >
				<div class="col-sm-12 col-lg-4">
					<div class="botbox botser stats_input"> 
						<ul >  			
							<li>							
								<!-- <span class="input-group-addon">회원명</span> -->
								<select id="searchType" name="searchType" class="form-control6" onchange="fn_search(this.form);">
									<option value="n_start" <c:if test="${params.searchType == 'n_start' }">selected="selected"</c:if>>Quốc ngữ</option>
									<option value="h_text" <c:if test="${params.searchType == 'h_text' }">selected="selected"</c:if>>Hán Nôm</option>
								</select>
							</li>		
							<li><input type="text" class="form-control" id="searchTxt" name="searchTxt" value="${params.searchTxt }" /></li>
							<li><span class="input-group-btn">
							<!--  
							<button class="btn btn-default" type="submit">SEARCH</button>
							-->
							<button class="btn btn-default btn-sm" type="button" onclick="fn_search(this.form);"><i class="glyphicon glyphicon-search"></i></button>
							<button class="btn btn-default btn-sm" type="button" onclick="fn_refresh(this.form);" style="margin-left: 5px;"><i class="glyphicon glyphicon-refresh"></i></button>
							</span></li>
						</ul>
					</div><!-- /input-group --> 
				</div><!-- /.col-lg-6 -->
			</div><!-- /.row -->
		</form> 
		<div id="search_result">
		<c:choose>
			<c:when test="${!empty DATA}"> 
				<h4 >Kết quả <em>(${totalCnt })</em></h4>
			</c:when>
			<c:otherwise>
				<ul class="tab_index">
				<c:forEach items="${start_chars}" var="item" varStatus="state">
					<li <c:if test="${state.index == 0}"> class="fst"</c:if>>
					<a href="/guest/search?searchType=n_start&searchTxt=${item}"> ${item}</a>
					</li>
				</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
		
		<ul>
		<c:forEach items="${DATA}" var="item" varStatus="state">
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
		<nav class="bbs_paging clearfix">
			${pageNavigator}	
		</nav>
		</div>
		<div id="word_detail" class="text-left">
		</div>
	</section>
</div>
<div id="code-modal">
</div>
</body>
</html>
