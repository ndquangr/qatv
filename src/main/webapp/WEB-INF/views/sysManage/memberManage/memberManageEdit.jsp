<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false"%>
<sec:authorize access="authenticated">
	<sec:authentication property="principal.userID" var="userID"/>
	<sec:authentication property="principal.password" var="userPW"/>
	<sec:authentication property="principal.username" var="userNM" />
	<%-- <sec:authentication property="principal.userSeq" var="userSeq" /> --%>
	<sec:authentication property="principal.authorities" var="authorities"/>
	<c:set var="hasUser" value="Y"/>
</sec:authorize>
<html>
<head>
<title> 관리자 관리</title>
<script src="/resources/js/jquery.validate.min.js"></script>
<script src="/resources/js/jquery.validate.bootstrap.popover.min.js"></script>
<script src="/resources/js/validateCommon.js"></script>
<script>
$(function(){
		
	$("#member_edit").validate_popover({
		popoverPosition: 'top',
		rules : { 
			user_nm:{
				required:true
			}
		},
		messages: {
			user_nm:{
				required:"회원명은 반드시 입력해야 합니다."
			}
		}
	});
	
	$("#modal-close").click( function () {
		$('div#validate-popover').hide();
	});
	
});

function goDelete(){
	if(confirm("정말 삭제하시겠습니까?")){
		$("#member_edit").attr('action','/a2mcms/memberManage/member/${user_id}/delete').submit();
	}
}

function goUpdate(){
	
	var user_id = $('#user_id').val();
	var user_nm = $('#user_nm').val();
	var pwd = $('#pwd').val();
	var pwd_confirm = $('#pwd_confirm').val();
	
	if(isNotEmpty(user_id)) {
		if(fn_specialCharacterValidation(user_id)) {
			alert("허용하지 않는 문자가 존재합니다.");
			$('#user_id').focus();
			return false;
		}
	}
	
	if(isNotEmpty(pwd)) {
		/* if(!fn_pwdValidation(pwd)) {
			alert("비밀번호는 8~15자리의 대문자, 소문자, 특수문자, 숫자를 포함하고 있어야 합니다.");
			$('#pwd').focus();
			return false;
		} */
	}
	
	if(isNotEmpty(pwd_confirm)) {
		/* if(!fn_pwdValidation(pwd_confirm)) {
			alert("비밀번호는 8~15자리의 대문자, 소문자, 특수문자, 숫자를 포함하고 있어야 합니다.");
			$('#pwd_confirm').focus();
			return false;
		} */
	}
	
	if(isNotEmpty(user_nm)) {
		if(fn_specialCharacterValidation(user_nm)) {
			alert("허용하지 않는 문자가 존재합니다.");
			$('#user_nm').focus();
			return false;
		}
	}

	if ( confirm("수정하시겠습니까?") ) {
		$("#member_edit").attr('action','/sys/memberManage/member/${user_id }').submit();
	}
}
</script>
</head>
<body>
<div>
	<section class="panel">
		<c:choose>
			<c:when test="${user.USER_ID == userID}">
				<c:choose>
					<c:when test="${fn:indexOf(authorities, 'ROLE_ADMIN') > -1 }">
						<header class="panel-heading">관리자 프로필 변경</header>
					</c:when>
					<c:otherwise>
						<header class="panel-heading">개인 프로필 변경</header>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise><header class="panel-heading">회원 프로필 변경</header></c:otherwise>
		</c:choose>
		<div class="panel-body " >
			<form name="member_edit" id="member_edit" class="form-horizontal" method="post" action="/a2mcms/memberManage/member/${user_id }">
				<div class="form-group">
					<label for="user_id" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 회원ID</label>
					<div class="col-sm-10 col-lg-10">
						<input type="text" class="form-control" id="user_id" name="user_id" placeholder="Email" value="${user.USER_ID}" readonly />
					</div>
				</div>
				<div class="form-group">
					<label for="eml" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> E-mail</label>
					<div class="col-sm-10 col-lg-10">
						<input type="text" class="form-control" id="eml" name="eml" placeholder="Email" value="${user.EML}" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
				</div>
				<div class="form-group">
					<label for="pwd" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 비밀번호</label>
					<div class="col-sm-10 col-lg-10">
						<input type="password" class="form-control" id="pwd" name="pwd" placeholder="비밀번호" value="" maxlength="15" />
					</div>
				</div>
				<div class="form-group">
					<label for="pwd_confirm" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 비밀번호 재확인</label>
					<div class="col-sm-10 col-lg-10">
						<input type="password" class="form-control" id="pwd_confirm" id=""name="pwd_confirm" placeholder="비밀번호 재입력" value="" maxlength="15" />
					</div> 
					 <label class="col-sm-2 col-lg-2 "> </label> 
					<div class="col-lg-offset-2 col-xs-10 col-sm-10 help-block">
						<span class="glyphicon glyphicon-exclamation-sign"></span> 패스워드 수정시에만 입력하세요.
					</div>
				</div>
				<div class="form-group">
					<label for="user_nm" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 회원명</label>
					<div class="col-sm-10 col-lg-10">
						<input type="text" class="form-control" id="user_nm" name="user_nm" placeholder="사용자 이름" value="${user.USER_NM }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
				</div>
				<div class="form-group">
					<label for="clpn1" class="col-sm-2 col-lg-2 control-label">휴대전화</label>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="clpn1" name="clpn1" placeholder="국번" maxlength="3" value="${user.CLPN1 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="clpn2" name="clpn2" placeholder="휴대폰 번호" maxlength="4" value="${user.CLPN2 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="clpn3" name="clpn3" placeholder="휴대폰 번호" maxlength="4" value="${user.CLPN3 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
				</div>
				<div class="form-group">
					<label for="tel_no1" class="col-sm-2 col-lg-2 control-label">직장전화</label>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="tel_no1" name="tel_no1" placeholder="지역번호" maxlength="3" value="${user.TEL_NO1 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="tel_no2" name="tel_no2" placeholder="전화 번호" maxlength="4" value="${user.TEL_NO2 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="tel_no3" name="tel_no3" placeholder="전화 번호" maxlength="4" value="${user.TEL_NO3 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
				</div>
				<div class="form-group">
					<label for="tel_no1" class="col-sm-2 col-lg-2 control-label">팩스번호</label>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="fax_no1" name="fax_no1" placeholder="지역번호" maxlength="3" value="${user.FAX_NO1 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="fax_no2" name="fax_no2" placeholder="팩스 번호" maxlength="4" value="${user.FAX_NO2 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
					<div class="col-xs-11 col-sm-2 col-lg-2 margin50">
						<input type="tel" class="form-control" id="fax_no3" name="fax_no3" placeholder="팩스 번호" maxlength="4" value="${user.FAX_NO3 }" <c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')<=-1 }">readonly</c:if>/>
					</div>
				</div>
				<c:if test="${!empty bbsList}">
					<div class="form-group">
						<label for="user_nm" class="col-sm-2 col-lg-2 control-label">관리 게시판</label>
						<div class="col-sm-10 col-lg-10">
							<div class="col-sm-12"> 
								<table class="display tables">
							        <colgroup>
								        <col width="5%" />
								        <col width="*" />
								        <col width="6%" />
								        <col width="30%" />
								        <col width="10%" />
								        <col width="13%" />
								        <col width="7%" />
							        </colgroup>
							        <thead> 
							        	<tr>
							        		<th class="text-center">No.</th>
							        		<th class="text-center" colspan="2">게시판명</th>
							            	<th class="text-center">메뉴위치</th>
							            	<th class="text-center">디자인</th>
							            	<th class="text-center">등록일</th>
							            	<th class="text-center">사용여부</th>
							            </tr>
							        </thead>
							        <tbody id="orgSet_tbody"> 
							        	<c:forEach items="${bbsList}" var="bbs" varStatus="state">
								        	<tr>
									        	<td class="text-center">${state.index+1}</td>
									        	<td class="text-left">${bbs.BBS_NM}</td>
									        	<td class="text-center">
									        		<c:if test="${bbs.USE_YN eq 'Y'}">
														<a class="btn btn-default" href="/${lang_type}/post/${bbs.BBS_UNIQ_ID}" target="_blank"
															onclick="window.open(this.href,'popupReView'); return false;">
															<span class="glyphicon glyphicon-link"></span> 바로가기
														</a>
			        								</c:if>
									        	</td>
									        	<td class="text-left">${bbs.PATH_NM}</td>
									        	<td class="text-center">${bbs.BBS_SKIN_NM}</td>
									        	<td class="text-center">${fn:substring(bbs.RGST_DTTM, 0, 19)}</td>
									        	<td class="text-center">${bbs.USE_YN}</td>
								        	</tr>
							        	</c:forEach>
							        </tbody>
								</table>
							</div>
						</div>	
					</div>
				</c:if>
				
				<c:if test="${!empty pageList}">
					<div class="form-group">
						<label for="user_nm" class="col-sm-2 col-lg-2 control-label">관리 페이지</label>
						<div class="col-sm-10 col-lg-10">
							<div class="col-sm-12"> 
								<table class="display tables">
							        <colgroup>
								        <col width="5%" />
								        <col width="*" />
								        <col width="6%" />
								        <col width="35%" />
								        <col width="12%" />
								        <col width="6%" />
								        <col width="6%" />
								        <col width="7%" />
								        <col width="6%" />
							        </colgroup>
							        <thead> 
							        	<tr>
							        		<th class="text-center">No.</th>
							        		<th class="text-center" colspan="2">페이지명</th>
							            	<th class="text-center">메뉴위치</th>
							            	<th class="text-center">최근수정일시</th>
							            	<th class="text-center">노출여부</th>
							            	<th class="text-center">노출횟수</th>
							            	<th class="text-center">요청상태</th>
							            </tr>
							        </thead>
							        <tbody id="orgSet_tbody"> 
							        	<c:forEach items="${pageList}" var="page" varStatus="state">
								        	<tr>
									        	<td class="text-center">${state.index+1}</td>
									        	<td class="text-left">${page.PAGE_TITLE}</td>
									        	<td class="text-center">
									        		<c:if test="${page.DEL_YN != 'Y' && page.VIEW_YN == 'Y' }">
			        									<a class="btn btn-default btn-sm" href="/${lang_type}/pageView/${page.PAGE_SEQ}" target="_blank"
															onclick="window.open(this.href,'popupReView'); return false;">
																<span class="glyphicon glyphicon-link"></span>바로가기
														</a>
			        								</c:if>
									        	</td>
									        	<td class="text-left">${page.PATH_NM}</td>
									        	<td class="text-center">${fn:substring(page.MDFY_DTTM, 0, 19)}</td>
									        	<td class="text-center">${page.VIEW_YN}</td>
									        	<td class="text-center">${page.PAGE_INQR_CUNT}</td>
									        	<td class="text-center">${page.PAGE_PROC_NM}</td>
								        	</tr>
							        	</c:forEach>
							        </tbody>
								</table>
							</div>
						</div>	
					</div>
				</c:if>
				
				<c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')>-1 }">
				<div class="form-group">
					<label for="user_nm" class="col-sm-2 col-lg-2 control-label"><span class="glyphicon glyphicon-ok-sign"></span> 접속권한</label>
					<div class="col-sm-10 col-lg-10">
						<c:choose>
							<c:when test="${fn:indexOf(authorities, 'ROLE_ADMIN')>-1 }">
								<c:forEach items="${roleList}" var="auth" varStatus="state">
										<c:if test="${auth.ROL_MNG_ID != 'ROLE_ANONYMOUS'}">
											<c:set var="pref" value="${auth.ROL_MNG_ID}" /> 
											<label class="checkbox-inline_o2"> 
											  <input type="checkbox" name="rol_mng_id" id="${auth.ROL_MNG_ID}" value="${auth.ROL_MNG_ID}" <c:if test="${fn:indexOf(rol_val, pref)>-1 }">checked</c:if>> ${auth.ROL_MNG_NM}
											</label>
										</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								${rol_nm}
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				</c:if>
				<div class="form-group text-center noline">
					<button type="button" class="btn btn-success"  onclick="goUpdate();">수 정</button>
					<c:if test="${fn:indexOf(authorities, 'ROLE_ADMIN')>-1 }">
						<button type="submit" class="btn btn-danger" onclick="goDelete();">삭 제</button>
						<a class="btn btn-warning" href="/a2mcms/memberManage/members" role="button" >취 소</a>
					</c:if>
					<a class="btn btn-info" href="/a2mcms/memberManage/members" role="button" >목 록</a>
				</div>
			</form>
		</div>
	</section>
</div>
<div id="page-modal"></div>
</body>
</html>
