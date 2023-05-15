<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<html>
<head>
	<title>공통 코드 관리</title>
	<meta name="decorator" content="null" />
	<style type="text/css">
	/* 에러메시지를 input 하단에 띄워 주는 스타일 [START] */
	input.error, textarea.error {
		border:1px solid red;
	}
	label.error {
		display:block;
		color:red;
	}
	/* 에러메시지를 input 하단에 띄워 주는 스타일 [END] */
	</style>
	
	<!-- include summernote css/js-->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.js"></script>
	
	<script src="/resources/js/jquery.validate.min.js"></script>
	<script src="/resources/js/jquery.validate.bootstrap.popover.min.js"></script>
	<script>
		$(function(){
			refCdNoCheck();
			
			$('.summernote').summernote({
				  height: 100,                 // set editor height
				  minHeight: null,             // set minimum height of editor
				  maxHeight: null,             // set maximum height of editor
				  focus: true,                  // set focus to editable area after initializing summernote
				  disableDragAndDrop: true,			  
				  toolbar: [
				            // [groupName, [list of button]]
				            ['style', ['bold', 'italic', 'underline', 'clear']],
				            ['font', ['strikethrough', 'superscript', 'subscript']],
				            ['fontsize', ['fontsize']],
							['color', ['color']],
				            ['para', ['ul', 'ol', 'paragraph']],
				            ['insert',['picture','link']]
				          ] 
				});
			$('.summernote').summernote('justifyFull');
			$("#codeForm").validate({
				rules : { 
					cd_nm:{
						required : true
					},
					cd_sort_ordr:{
						required : true,
						digits : true
					}
				},
				messages: {
					cd_nm:{
						required : "코드명은 반드시 입력해야 합니다."
					},
					cd_sort_ordr:{
						required : "순서는 반드시 입력해야 합니다.",
						digits : "순서는 숫자로 입력해 주세요."
					}
				}
			});
		
			/*************************************
			 * 취소 버튼 클릭 이벤트
			 ************************************/
			$("#modal-close").click( function () {
				// Manually hides a modal.
				$('div#code_form').hide();
			});
		});
		
		function refCdNoCheck(){
			var level = $("#cd_clsf_levl").val();
			if(level == 0){
				//alert($("label[for=ref_cd_no]").html());
				$("#ref_cd_no_comment").show();
				$("input#ref_cd_no").val("");
				$("input#ref_cd_no").attr("disabled", "disabled");
				$("label[for=ref_cd_no]").html(" 참조 코드 번호");
			} else {
				$("input#ref_cd_no").removeAttr("disabled");
				$("#ref_cd_no_comment").hide();
			}
		}
		
		function goInsert(){
			
			$("#writeform").submit();
			
		}
		
		function addRow(obj) {
			$tr = $(obj).closest("tr");
			$f = '<tr valign="top">'
				+	'<td class="left">1</td>'
				+	'<td class="text-center"><input class="form-control" type="text" name="CHAR_HANNOM" value=""/></td>'
				+	'<td class="text-center"><input class="form-control" type="text" name="CHAR_VIET" value=""/></td>'
				+	'<td class="text-center"><div class="summernote" name="CHAR_DEF"></div></td>'
				+	'<td class="text-center">'
				+	'<a href="javascript:void(0)" onclick="addRow(this);" data-toggle="tooltip" data-placement="top" title="Thêm hàng"><span class="glyphicon glyphicon-plus-sign btn-xs"></span></a>'
				+	'<a href="javascript:void(0)" onclick="deleteRow(this);" data-toggle="tooltip" data-placement="top" title="Xóa"><span class="glyphicon glyphicon-trash btn-xs"></span>'
				+	'</a></td></tr>';
			$newtr = $($f);
			$tr.after($newtr);	
			
			$newtr.find(".summernote").summernote({
				  height: 100,                 // set editor height
				  minHeight: null,             // set minimum height of editor
				  maxHeight: null,             // set maximum height of editor
				  focus: true,                  // set focus to editable area after initializing summernote
				  disableDragAndDrop: true,
				  toolbar: [
				            // [groupName, [list of button]]
				            ['style', ['bold', 'italic', 'underline', 'clear']],
				            ['font', ['strikethrough', 'superscript', 'subscript']],
				            ['fontsize', ['fontsize']],
							['color', ['color']],
				            ['para', ['ul', 'ol', 'paragraph']],
				            ['insert',['picture','link']]
				          ] 
				});
			$newtr.find(".summernote").summernote('justifyFull');
			$("#sub_table tr").each(function(idx){
				$(this).find("td:first").html(idx);
			});
		}
		
		function deleteRow(obj) {
			$(obj).closest("tr").remove();
			$("#sub_table tr").each(function(idx){
				$(this).find("td:first").html(idx);
			});
		}
	</script>
</head>

<body>
	<form:form id="writeform" method="post" action="/staff/qatv/new">
	<fieldset>
		<h3 style="margin-top: 1em;">Thêm mục từ</h3>
		<div class="table-responsive" style="clear: both;"> 	
		<div align="right"> <button type="button" class="btn btn-success right"  onclick="goInsert();">Submit</button>
  		</div>		
			
			<table class="table table-bordered text-center"> 
		        <colgroup>
			        <col width="20%" />
			        <col width="30%" />
			        <col width="20%" />
			        <col width="30" />
		        </colgroup>
		        <tbody>	
		       		<tr>
		       			<input type="hidden" name="MAIN.QATV_ID" value="${MAIN.QATV_ID }"/>
		       			<th>Loại từ</th>
		       			<td>
		       			<select class="form-control" style="width:100px;" name="MAIN.CHAR_TYPE">
		       				<option value="0" <c:if test="${empty MAIN.CHAR_TYPE or MAIN.CHAR_TYPE eq 0 }">selected="selected"</c:if>>::::</option>
		       				<option value="1" <c:if test="${MAIN.CHAR_TYPE eq 1 }">selected="selected"</c:if>>c</option>
		       				<option value="2" <c:if test="${MAIN.CHAR_TYPE eq 2 }">selected="selected"</c:if>>n</option>
		       				<option value="3" <c:if test="${MAIN.CHAR_TYPE eq 3 }">selected="selected"</c:if>>cn</option>
		       			</select>
		       			</td>
		       			<th rowspan="3">Định nghĩa</th>
		       			<td rowspan="3">
		       			<%-- <textarea class="form-control" style="width:100%; height:100%; " name="MAIN.CHAR_DEF" >${MAIN.CHAR_DEF }</textarea> --%>
		       			<%-- <input type="hidden" name="" value="${MAIN.CHAR_DEF }" /> --%>
		       			<div class="summernote" name="MAIN.CHAR_DEF">${MAIN.CHAR_DEF }</div>
		       			</td>
		       		</tr>
		       		<tr>
		       			<th>Hán Nôm</th>
		       			<td><input class="form-control" type="text" name="MAIN.CHAR_HANNOM" value="${MAIN.CHAR_HANNOM }"/></td>
		       		</tr>
					<tr>
						<th>Từ Việt</th>
		       			<td><input class="form-control" type="text" name="MAIN.CHAR_VIET" value="${MAIN.CHAR_VIET }"/></td>
		       		</tr>
		        </tbody>
			</table>
		</div>
		<div class="adv-table">
				<table id="sub_table" class="table table-bordered text-center"> 
			        <colgroup>
				        <col width="5%" />
				        <col width="10%" />
				        <col width="20%" />
				        <col width="*" />			        
						<col width="15%" />
			        </colgroup>
			        <thead>
			        	<tr>
			            	<th>No.</th>
			            	<th>Hán Nôm</th>
			                <th>Từ Việt</th>
			                <th>Định nghĩa</th>		            	
			            	<th>
			            	</th>
			            </tr>
			        </thead>
			        <tbody>
			        <c:choose>
			        	<c:when test="${!empty SUB}">
			        		<c:forEach items="${SUB}" var="item" varStatus="state">
			        			<tr valign="top">
			        				<input type="hidden" name="MAIN.QATV_ID" value="${item.QATV_ID }"/>
									<td class="left">${state.index }</td>
									<td class="text-center"><input class="form-control" type="text" name="CHAR_HANNOM" value="${item.CHAR_HANNOM }"/></td>
									<td class="text-center"><input class="form-control" type="text" name="CHAR_VIET" value="${item.CHAR_VIET }"/></td>
									<td class="text-center"><textarea class="form-control" rows="3" name="CHAR_DEF" >${item.CHAR_DEF }</textarea></td>
									<td class="text-center">
										<a href="javascript:void(0)" onclick="addRow(this);" data-toggle="tooltip" data-placement="top" title="Thêm hàng">
											<span class="glyphicon glyphicon-plus-sign btn-xs"></span>
										</a>
										<a href="javascript:void(0)" onclick="deleteRow(this);" data-toggle="tooltip" data-placement="top" title="Xóa">
											<span class="glyphicon glyphicon-trash btn-xs"></span>
										</a>
									</td>
								</tr>
			        		</c:forEach>
			        	</c:when>
			        	<c:otherwise>
			        		<tr valign="top">
								<td class="left">1</td>
								<td class="text-center"><input class="form-control" type="text" name="CHAR_HANNOM" value=""/></td>
								<td class="text-center"><input class="form-control" type="text" name="CHAR_VIET" value=""/></td>
								<td class="text-center">
								<!-- <textarea class="form-control" rows="3" name="CHAR_DEF" ></textarea> -->
								<div class="summernote" name="CHAR_DEF">${CHAR_DEF }</div>
								</td>
								<td class="text-center">
									<a href="javascript:void(0)" onclick="addRow(this);" data-toggle="tooltip" data-placement="top" title="Thêm hàng">
										<span class="glyphicon glyphicon-plus-sign btn-xs"></span>
									</a>
									<a href="javascript:void(0)" onclick="deleteRow(this);" data-toggle="tooltip" data-placement="top" title="Xóa">
										<span class="glyphicon glyphicon-trash btn-xs"></span>
									</a>
								</td>
							</tr>
			        	</c:otherwise>
			        </c:choose>			        
			        </tbody>
				</table>
		</div>
		</fieldset>
		</form:form>
</body>
</html>