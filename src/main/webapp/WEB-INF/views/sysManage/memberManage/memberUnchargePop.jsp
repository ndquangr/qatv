<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false" %>
<html>
<head>
	<title>담당자 삭제</title>
	<meta name="decorator" content="null" />
	<script src="/resources/js/jquery.validate.min.js"></script>
	<script src="/resources/js/jquery.validate.bootstrap.popover.min.js"></script>
	<script type="text/javascript">
	function checkGroup(type, mode){
		if ( type == 'all' ) {
			var allcheck = $("input:checkbox[name='"+mode+"all']").is(':checked');
			$("input:checkbox[name='"+mode+"seq']").each(function () {
				$(this).prop("checked", allcheck);
			});
		}
	}

	function setMemberUncharge(userIdForm, userNameForm) {
		var checked_user_ids = "";
		var checked_user_names = "";

		if ( $("input:checkbox[name='confirm_seq']:checked").length < 1 ) {
			alert("삭제할 담당자를 선택하세요.");
			return false;
		}

		if ( ! confirm("선택한 담당자를 삭제하시겠습니까?") ) {
			return false;
		}

		$("input:checkbox[name='confirm_seq']:checked").each(function () {
			var seq = $(this).val();

			if ( checked_user_ids != "" ) {
				checked_user_ids = checked_user_ids + "," + (seq.split('|'))[0];
				checked_user_names = checked_user_names + "," + (seq.split('|'))[1];
			} else {
				checked_user_ids = (seq.split('|'))[0];
				checked_user_names = (seq.split('|'))[1];
			}
		});

		//--console.log("checked_user_ids : " + checked_user_ids);
		//--console.log("checked_user_names : " + checked_user_names);

		var checked_user_ids_arr = checked_user_ids.split(',');
		var checked_user_names_arr = checked_user_names.split(',');

		/******************************************************
		 * 선택한 삭제대상 담당자를
		 * 바닥화면으로부터 제거한다. 
		 *****************************************************/
		var caller_chrg_user_id_multi = $('#'+userIdForm).val();
		var caller_chrg_user_id = caller_chrg_user_id_multi.split(',');

		for (var i=0; i < checked_user_ids_arr.length; i++ ) {
			//--console.log("splice user_id i : " + i + " value : " + caller_chrg_user_id[i]);

			if ( caller_chrg_user_id_multi.indexOf( checked_user_ids_arr[i] ) > -1 ) {
				for (var j=0; j < caller_chrg_user_id.length; j++ ) {
					if ( checked_user_ids_arr[i] == caller_chrg_user_id[j] ) {
						//--console.log("splice 제거 성공 : " + caller_chrg_user_id[j]);
						caller_chrg_user_id.splice(j, 1);
						break;
					}
				}
			}
		}

		//--console.log("61 caller_chrg_user_id : " + caller_chrg_user_id);
		//--console.log("62 caller_chrg_user_id.length : " + caller_chrg_user_id.length);

		var caller_chrg_user_nm_multi = $('#'+userNameForm).val();
		var caller_chrg_user_nm = caller_chrg_user_nm_multi.split(',');

		for (var i=0; i < checked_user_names_arr.length; i++ ) {
			//--console.log("splice user_nm i : " + i + " value : " + caller_chrg_user_nm[i]);

			if ( caller_chrg_user_nm_multi.indexOf( checked_user_names_arr[i] ) > -1 ) {
				for (var j=0; j < caller_chrg_user_nm.length; j++ ) {
					if ( checked_user_names_arr[i] == caller_chrg_user_nm[j] ) {
						//--console.log("splice 제거 성공 : " + caller_chrg_user_nm[j]);
						caller_chrg_user_nm.splice(j, 1);
						break;
					}
				}
			}
		}

		//--console.log("72 caller_chrg_user_nm : " + caller_chrg_user_nm);
		//--console.log("73 caller_chrg_user_nm.length : " + caller_chrg_user_nm.length);

		// [1] hidden 담당자ID 제거
		$('#'+userIdForm).val(caller_chrg_user_id);

		// [2] text 담당자명 제거
		$('#'+userNameForm).val(caller_chrg_user_nm);

		// 팝업 닫기.
		$('.close').click();
	}
	</script>
</head>
<body>
<div id="member_form" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<header class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title">삭제할 담당자 선택</h4></header>
			<div class="panel-body">

			<c:if test="${fn:length(userList) <= 0 }">
				검색된 담당자가 없습니다.
			</c:if>

			<c:if test="${fn:length(userList) > 0 }">

	        <div class="adv-table">
				<table class="display table table-bordered table-striped">
			        <colgroup>
				        <col width="*" />
			        </colgroup>
			        <thead>
			        	<tr>
							<th class="text-center">
								<label class="label_check " for="confirm_all">
									<input type="checkbox" name="confirm_all" id="confirm_all" value="all" onclick="checkGroup('all', 'confirm_')"/>&nbsp;
								</label>
							</th>
			            	<th>이름</th>
			            </tr>
			        </thead>
			        <tbody>
			
			       	<c:forEach items="${userList}" var="user" varStatus="state">
						<tr>
							<td class="text-center">
								<label class="label_check " for="confirm_${user.USER_ID}">
									<input type="checkbox" name="confirm_seq" id="confirm_${user.USER_ID}" value="${user.USER_ID}|${user.USER_NM}"/>&nbsp;
								</label>
							</td>
							<td class="left">${user.USER_NM}</td>
						</tr>
					</c:forEach>
			        </tbody>
				</table>
			</div>
			<div class="form-group text-center noline">
				<button style="cursor:pointer" onclick="javascript:setMemberUncharge('${userIdForm}', '${userNameForm}');" class="bdel btn btn-danger" type="button">적용</button>
		    </div>

			</c:if>

			</div>
		</div>
	</div>
</div>
</body>
</html>
