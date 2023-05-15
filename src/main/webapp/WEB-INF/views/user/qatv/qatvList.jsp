<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>

<html>
	<head>
	<title>시스템 공통코드 관리</title>

	<script type="text/javascript">
		$(function(){
			initSubTr();
		});
	
		function initSubTr(){
			parent_tr = $("#code_name");
			if(parent_tr.hasClass("glyphicon-folder-close")){
				parent_tr.removeClass("glyphicon-folder-close");
				parent_tr.addClass("glyphicon-folder-open");
		
				$("tr").each(function(){
					$(this).find(".glyphicon-folder-close").addClass("glyphicon-folder-open");
					$(this).find(".glyphicon-folder-close").removeClass("glyphicon-folder-close");
					if($(this).data("cd-levl") > 0){
						$(this).show();
					}
				});
			} else {
				parent_tr.addClass("glyphicon-folder-close");
				parent_tr.removeClass("glyphicon-folder-open");
		
				$("tr").each(function(){
					$(this).find(".glyphicon-folder-open").addClass("glyphicon-folder-close");
					$(this).find(".glyphicon-folder-open").removeClass("glyphicon-folder-open");
					if($(this).data("cd-levl") > 0){
						$(this).hide();
					}
				});
			}
		}
		
		function showSubTr(ref_cd, level){
			level = eval(level);
			parent_tr = $("#" + ref_cd);
			if(parent_tr.hasClass("glyphicon-folder-close")){
				parent_tr.removeClass("glyphicon-folder-close");
				parent_tr.addClass("glyphicon-folder-open");
				$("." + ref_cd).each(function(){
					//console.log(level);
					//console.log(eval(level + 1));
					//console.log($(this).data("cd-levl"));
					if($(this).data("cd-levl") == level + 1){
						$(this).slideDown();
					}
				});
			} else {
				closeSubTr(parent_tr, ref_cd, level);
			}
		}
		
		function closeSubTr(parent_item, ref_cd, level){
			parent_item.addClass("glyphicon-folder-close");
			parent_item.removeClass("glyphicon-folder-open");
			
			child_item = $("." + ref_cd);
			if(child_item.length > 0){
				$("." + ref_cd).each(function(){
					if($(this).data("cd-levl") >= (level + 1)){
						var id = $(this).data("code-id");
						closeSubTr($("#" + id), id, level + 1);
						$(this).hide();
					}
				});
			}
		}

		//코드 추가.
		function openCodeForm(code_id, params){
			$("#code-modal").html("");
			ajaxReplaceHtmlWithModal("#code-modal", params, "/staff/qatv/" + code_id + "/edit", "#code_form");
		}
	
		function goDelete(cd_no, cd_nm){
			if(confirm("Xóa mục từ?")){
				$("#deleteForm").attr("action", "/staff/qatv/delete?id=" + cd_no);
				$("#deleteForm #cd_no").val(cd_no);
				$("#deleteForm").submit();
			}
		}
		
		function goInsert(){
			
			// similar behavior as an HTTP redirect
			//window.location.replace("http://stackoverflow.com");

			// similar behavior as clicking on a link
			window.location.href = "/staff/qatv/write";
			
		}
		
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
	</script>
</head>

<body>
<form name="deleteForm" id="deleteForm" method="post"></form>
<div>
	<section class="panel">		
		<div class="panel-body">
		<!-- 검색영역 -->
		<form id="formSearch" name="formSearch" action="/staff/qatv/list" > <!-- style="display: inline-block;" -->
			<div class="row cc" >
				<div class="col-sm-12 col-lg-4">
					<div class="botbox botser stats_input"> 
						<ul >  			
							<li>							
								<!-- <span class="input-group-addon">회원명</span> -->
								<select id="searchType" name="searchType" class="form-control6" onchange="fn_search(this.form);">
									<option value="n_text" <c:if test="${params.searchType == 'n_text' }">selected="selected"</c:if>>Quốc ngữ</option>
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
		
		<div align="right"> <button type="button" class="btn btn-success right"  onclick="goInsert();">Thêm</button>
        <div class="adv-table">
			<table class="display table table-striped">
		        <colgroup>
			        <%-- <col width="*" /> --%>
			        <col width="10%" />
			        <col width="10%" />
			        <col width="20%" />
			        <col width="20%" />
					<col width="15%" />
		        </colgroup>
		        <thead>
		        	<tr>
		            	<!-- <th>
		            		<a href="javascript:initSubTr();">
								<span id="code_name" class="glyphicon glyphicon-folder-open"></span>&nbsp;&nbsp;코드명
							</a>
						</th> -->
		            	<th>Quốc ngữ</th>
		                <th>Hán Nôm</th>
		                <th>Định nghĩa</th>
		            	<th>Từ dẫn xuất</th>
		            	<th><!-- <a href="javascript:openCodeForm('', {'CD_CLSF_LEVL':'0'});" data-toggle="tooltip" data-placement="top" title="코드 추가">Thêm từ<span class="glyphicon glyphicon-plus-sign btn-xs"></span></a> --></th>
		            </tr>
		        </thead>
		        <tbody>
		
		       	<c:forEach items="${DATA}" var="item" varStatus="state">
					<tr data-code-id="${item.QATV_ID }" data-cd-levl="${item.CHAR_LVL }">
						<%-- <td class="left">
							<c:forEach var="i" begin="1" end="${sysCodeList.CD_CLSF_LEVL}" step="1">&nbsp;&nbsp;</c:forEach>
							<a href="javascript:showSubTr('${sysCodeList.CD_NO }', '${sysCodeList.CD_CLSF_LEVL }');">
								<c:if test="${sysCodeList.CD_CLSF_LEVL > 0 }"></c:if><span id="${sysCodeList.CD_NO }" class="glyphicon glyphicon-folder-close"></span>&nbsp;&nbsp;${sysCodeList.CD_NM}
							</a>
						</td> --%>
						<td class="text-center">${item.CHAR_VIET}</td>
						<td class="text-center">${item.CHAR_HANNOM}</td>
						<td class="text-center">${item.CHAR_DEF}</td>
						<td class="text-center">${item.CHILDREN}</td>
						<td class="text-center">
							<%-- <a href="javascript:openCodeForm('', {'REF_CD_NO':'${sysCodeList.CD_NO }', 'REF_CD_NM':'${sysCodeList.CD_NM }', 'CD_CLSF_LEVL':'${sysCodeList.CD_CLSF_LEVL + 1 }'});" data-toggle="tooltip" data-placement="top" title="하위 코드 추가">
								<span class="glyphicon glyphicon-plus-sign btn-xs"></span>
							</a> --%>
							<%-- <a href="javascript:openCodeForm('${item.QATV_ID }');" data-toggle="tooltip" data-placement="top" title="Sửa"><span class="glyphicon glyphicon-edit btn-xs"></span></a> --%>
							<a href="/staff/qatv/write?id=${item.QATV_ID }" data-toggle="tooltip" data-placement="top" title="Sửa"><span class="glyphicon glyphicon-edit btn-xs"></span></a>
							<a href="javascript:goDelete('${item.QATV_ID }');" data-toggle="tooltip" data-placement="top" title="Xóa"><span class="glyphicon glyphicon-trash btn-xs"></span></a>
						</td>
					</tr>
				</c:forEach>
		        </tbody>
			</table>
		</div>
		</div>
		<nav class="bbs_paging clearfix">
			${pageNavigator}	
		</nav>
	</section>
</div>
<div id="code-modal">
</div>
</body>
</html>
