<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false" %>

<html>
<head>
	<title> 담당자 찾기</title>

	<meta name="decorator" content="null" />
	<style>
		#member_form .adv-table
		{
		   overflow-y:auto; 
		   height:500px;
		}
	</style>
	<script src="/resources/js/jquery.validate.min.js"></script>
	<script src="/resources/js/jquery.validate.bootstrap.popover.min.js"></script>
	<script type="text/javascript">
		$(function(){

			// modal open
			$("body").css("overflow-y", "hidden");

			// modal close
			$('#member_form').on('hidden.bs.modal', function () {
				$("body").css("overflow-y", "visible");
			});

		});

		function setCharge(id, nm, en_nm, tel, userIdForm, userNameForm, userTelForm) {

			var caller_chrg_user_id = ($('#'+userIdForm).val());

			if ( (caller_chrg_user_id).indexOf(id) > -1 ) {
				alert("이미 담당자로 지정되어 있습니다.");
			} else {
				//if ( caller_chrg_user_id != null && caller_chrg_user_id != '' ) {
				//	$('#'+userIdForm).val(caller_chrg_user_id + "," + id);
				//	$('#'+userNameForm).val(caller_chrg_user_nm + "," + nm);
				//} else {
					$('#'+userIdForm).val(id);
					$('#chrg_user_nm_ko').val(nm);
					$('#'+userNameForm).val(nm+'|'+en_nm);
					$('#'+userTelForm).val(tel);
				//}

				// 팝업 닫기.
				$('.close').click();
			}
		}
	</script>
</head>

<body>
	<div id="member_form" class="modal fade">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<header class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">담당자 선택</h4></header>
				<div class="panel-body">
					<c:if test="${fn:length(userList) <= 0}">
						검색된 담당자가 없습니다.
					</c:if>
					<c:if test="${fn:length(userList) > 0}">
				        <table class="display table table-bordered table-striped" style="margin-top:0 !important; margin-bottom: 0 !important;">
							<colgroup>
						        <col width="250" />
				        		<col width="250" />
						        <col width="250" />
						        <col width="*" />
					        </colgroup>
					        <thead>
					        	<tr>
					            	<th>이름</th>
									<th>소속</th>
									<th>전화번호</th>
									<th>&nbsp;</th>
					            </tr>
					        </thead>
						</table>
				        <div class="adv-table">
							<table class="display table table-bordered table-striped" style="border-top-style:none !important; margin-top:0 !important; margin-bottom: 0 !important;">
								<colgroup>
							        <col width="250" />
					        		<col width="250" />
							        <col width="250" />
							        <col width="*" />
						        </colgroup>
						        <tbody>
						       	<c:forEach items="${userList}" var="user" varStatus="state">
									<tr>
										<td class="left">${user.USER_NM_KO}</td>
										<td>${user.DEPT_NM}</td>
										<td>${user.DEPT_TEL}</td>
										<td calss="text-center"><a class="btn btn-success btn-xs" href="javascript:setCharge('${user.USER_ID}', '${user.USER_NM_KO}', '${user.USER_NM_EN}', '${user.DEPT_TEL}', '${userIdForm}', '${userNameForm}', '${userTelForm}');" data-user-id="${user.USER_ID}">선택</a></td>
									</tr>
								</c:forEach>
						        </tbody>
							</table>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
