<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>

<html>
	<head>
	<title>시스템 공통코드 관리</title>
	<style type="text/css">
		/* 에러메시지를 input 하단에 띄워 주는 스타일 [START] */
		input.error, textarea.error {
			border:1px solid red !important;
		}
		label.error {
			display:block;
			color:red;
		}
		/* 에러메시지를 input 하단에 띄워 주는 스타일 [END] */
	</style>
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
		function goEdit(code_id){
			
			var _url = "/staff/nom/edit";
			var _data = "id=" + code_id;
			//ajaxReplaceHtmlWithModal("#code-modal", params, "/staff/nom/" + code_id + "/edit", "#code_form");
			$.ajax({
				type: "GET",
				url: _url,
				cache: false,
				data:_data,
				success: function(data) {
					$("input[name='MAIN.NOM_ID']").val(data.NOM_ID);
					$("input[name='MAIN.FILE_ID']").val(data.FILE_ID);
					$("input[name='MAIN.CHAR_VIET']").val(data.CHAR_VIET);
					$("input[name='MAIN.CHAR_UNI']").val(data.CHAR_UNI);
					$("textarea[name='MAIN.CHAR_DEF']").val(data.CHAR_DEF);
					//$("input[name='MAIN.FILE']").val(data.FILE_NAME);
					
					$("#NOM_IMG").attr("src","/qatv/" + data.FILE_NAME).attr('alt', data.NOM_CD).attr('title', data.NOM_CD);
					//debugger; <img id="NOM_IMG" src="" alt="" height="30" width="30"  />
					//alert(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) { 
			        alert("Status: " + textStatus); alert("Error: " + errorThrown); 
			    }  
			});
		}
	
		function goDelete(cd_no){
			if(confirm("Xóa mục từ?")){
				$("#deleteForm").attr("action", "/staff/nom/delete?id=" + cd_no);
				$("#deleteForm #cd_no").val(cd_no);
				$("#deleteForm").submit();
			}
		}
		
		function goInsert(){
			var submitable = true;
			$('.vn_txt').each(function() {
			    var val = $(this).val();
			    var newval= val.trim();
			    $(this).removeClass("error");
			    if (newval == "") {
			    	submitable = false;
			    	$(this).addClass("error");
			    } 
			    $(this).val(newval);
			});
			if(submitable) {
				$("#writeform").submit();
			}		
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
		<!-- <header class="panel-heading">Danh sách</header> -->
		<div class="panel-body">
		<div class="table-responsive" style="clear: both;"> 	
		<div align="right"> <button type="button" class="btn btn-success right"  onclick="goInsert();">Lưu</button>
  		</div>		<!--   -->
			<form:form id="writeform" method="post" action="/staff/nom/new" enctype="multipart/form-data">
			
			<!-- <div align="right"> <input type="submit" class="btn btn-success right" value="Thêm" /> -->
			<table class="table table-bordered text-center"> 
		        <colgroup>
			        <col width="20%" />
			        <col width="30%" />
			        <col width="20%" />
			        <col width="30" />
		        </colgroup>
		        <tbody>	
		       		<tr>
		       			<input type="hidden" name="MAIN.NOM_ID" value="${MAIN.NOM_ID }"/>
		       			<input type="hidden" name="MAIN.FILE_ID" value="${MAIN.FILE_ID }"/>
		       			<th>Âm nôm</th>
		       			<td>
		       			<input class="form-control vn_txt" type="text" name="MAIN.CHAR_VIET" value="${MAIN.CHAR_VIET }"/>
		       			</td>
		       			
		       			<th>Mã Unicode</th>
		       			<td><input class="form-control" type="text" name="MAIN.CHAR_UNI" value="${MAIN.CHAR_UNI }"/></td>		       			
		       		</tr>
		       		<tr>
		       			<th>Hình ảnh hiện tại</th>
		       			<td ><img id="NOM_IMG" src="" alt="" height="30" width="30" /></td>
		       			
		       			<th rowspan="2">Định nghĩa</th>
		       			<td rowspan="2">
		       			<textarea class="form-control" style="width:97%; height:100%; " name="MAIN.CHAR_DEF" >${MAIN.CHAR_DEF }</textarea>
		       			
		       			</td>
		       		</tr>
					<tr>
						<th>Hình ảnh</th>
		       			<td><input class="form-control"  type="file" name="MAIN.FILE"></td>						
		       		</tr>
		        </tbody>
			</table>
			</form:form>
		</div>
		
		<!-- 검색영역 -->
		<form id="formSearch" name="formSearch" action="/staff/nom/list" > <!-- style="display: inline-block;" -->
			<div class="row cc" >
				<div class="col-sm-12 col-lg-4">
					<div class="botbox botser stats_input"> 
						<ul >  			
							
							<li>
							<input type="text" class="form-control" id="searchTxt" name="searchTxt" value="${params.searchTxt }" />
							<input type="hidden" id="searchType" name="searchType" value="text"/></li>
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
		
        <div class="adv-table">
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
		            	<!-- <th>
		            		<a href="javascript:initSubTr();">
								<span id="code_name" class="glyphicon glyphicon-folder-open"></span>&nbsp;&nbsp;코드명
							</a>
						</th> -->
		            	<th>Âm nôm</th>
		            	<th>Mã nôm</th>
		                <th>Mã unicode</th>
		                <th>Định nghĩa</th>
		            	<th>Hình ảnh</th>
		            	<th></th>
		            </tr>
		        </thead>
		        <tbody>
		
		       	<c:forEach items="${DATA}" var="item" varStatus="state">
					<tr data-code-id="${item.NOM_ID }">
						<%-- <td class="left">
							<c:forEach var="i" begin="1" end="${sysCodeList.CD_CLSF_LEVL}" step="1">&nbsp;&nbsp;</c:forEach>
							<a href="javascript:showSubTr('${sysCodeList.CD_NO }', '${sysCodeList.CD_CLSF_LEVL }');">
								<c:if test="${sysCodeList.CD_CLSF_LEVL > 0 }"></c:if><span id="${sysCodeList.CD_NO }" class="glyphicon glyphicon-folder-close"></span>&nbsp;&nbsp;${sysCodeList.CD_NM}
							</a>
						</td> --%>
						<td class="text-center">${item.CHAR_VIET}</td>
						<td class="text-center">${item.NOM_CD}</td>
						<td class="text-center">${item.CHAR_UNI}</td>
						<td class="text-center">${item.CHAR_DEF}</td> 
						<td class="text-center"><img src="/qatv/${item.FILE_NAME}" alt="${item.CHAR_VIET}" height="30" width="30" /></td>
						<td class="text-center">
							<%-- <a href="javascript:openCodeForm('', {'REF_CD_NO':'${sysCodeList.CD_NO }', 'REF_CD_NM':'${sysCodeList.CD_NM }', 'CD_CLSF_LEVL':'${sysCodeList.CD_CLSF_LEVL + 1 }'});" data-toggle="tooltip" data-placement="top" title="하위 코드 추가">
								<span class="glyphicon glyphicon-plus-sign btn-xs"></span>
							</a> --%>
							<%-- <a href="javascript:openCodeForm('${item.NOM_ID }');" data-toggle="tooltip" data-placement="top" title="Sửa"><span class="glyphicon glyphicon-edit btn-xs"></span></a> --%>
							<a href="javascript:goEdit(${item.NOM_ID });" data-toggle="tooltip" data-placement="top" title="Sửa"><span class="glyphicon glyphicon-edit btn-xs"></span></a>
							<a href="javascript:goDelete('${item.NOM_ID }');" data-toggle="tooltip" data-placement="top" title="Xóa"><span class="glyphicon glyphicon-trash btn-xs"></span></a>
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
