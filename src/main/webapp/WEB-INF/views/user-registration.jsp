<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<spring:url value="/user-manage" var="userManage" htmlEscape="true" />
<spring:url value="/error" var="errorUrl" htmlEscape="true" />
<br>
<form:form class="add-user" action="add-user" method="post" commandName="userForm">
	<div>
		<div class="row">
			<div class="left">
				<label for="username"><spring:message code="addUser.userName"/></label>
			</div>
			<div class="right">
				<input type="hidden"  name="id" value="${userForm.id}" id="id">	
				<input type="text" tabindex="1" maxlength="100" name="userName" id="username" value="${userForm.userName}">
<%-- 				<p class="description"><spring:message code="addUser.desc.userName"/></p> --%>
			</div>
		</div>
		<div class="row">
			<div class="left">
				<label for="email"><spring:message code="addUser.email"/></label>
			</div>
			<div class="right">
				<input type="text" tabindex="2" maxlength="100" name="emailAddress" id="email" value="${userForm.emailAddress}">
<%-- 				<p class="description"><spring:message code="addUser.desc.email"/></p> --%>
			</div>
		</div>
		
		<div class="row">
			<div class="left">
				<label for="password"><spring:message code="addUser.password"/></label>
			</div>
			<div class="right">
				<input type="password" tabindex="3" maxlength="100" name="password" id="password" value="${userForm.password}">
			</div>
		</div>
		<div class="row">
			<div class="left">
				<label for="re-password"><spring:message code="addUser.re-password"/></label>
			</div>
			<div class="right">
				<input type="password" tabindex="4" maxlength="100" name="re-password" id="re-password" value="${userForm.password}">
<%-- 				<p class="description"><spring:message code="addUser.desc.password"/></p> --%>
<%-- 				<form:checkbox path="adminFlag" tabindex="5" id="adminFlag" value="${userForm.adminFlag}"/><label for="adminFlag"><spring:message code="addUser.isAdmin"/></label> --%>
			</div>
		</div>
		
		<div class="row">
			<div class="left" style="vertical-align: inherit;">
				<label for="role-user"><spring:message code="addUser.role"/></label>
			</div>
			<div class="right radio">
				<form:radiobutton path="roles" id="role-user" value="0" /><label for="role-user"><spring:message code="controller.userManager.roleUser"/></label>
				<form:radiobutton path="roles" id="role-mod" value="1"/><label for="role-mod"><spring:message code="controller.userManager.roleMod"/></label>
				<form:radiobutton path="roles" id="role-admin" value="2"/><label for="role-admin"><spring:message code="controller.userManager.roleAdmin"/></label>
			</div>
		</div>
		
		<div class="row">
			<div class="left"></div>
			<div class="right">
				<p class="error" style="display: none;"></p>
				<p class="msg" style="display: none;"></p>
				<input type="submit" value="${submit}" tabindex="6"> 
				<input type="reset" value="<spring:message code="common.reset"/>" tabindex="7">
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript">

	//set default radio button
	$(document).ready(function() {
		setDefaultUserRole();
		if($('#id').val() != "") {
			 $('#password').attr('data-parsley-required', 'false');
		}
	});
  $('.add-user').parsley({
	  errorsWrapper: '<span class="validate"></span>',
	  errorTemplate: "<p></p>"
  });
  
  $('#username').attr('data-parsley-length', '[4,100]');
  $('#username').attr('data-parsley-length-message', '<spring:message code="validate.length"/>');
  
  $('#username').attr('data-parsley-required', 'true');
  $('#username').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');
  
  $('#username').attr('data-parsley-pattern', '^[a-zA-Z0-9]*$');
  $('#username').attr('data-parsley-pattern-message', '<spring:message code="validate.pattern"/>');
  
  $('#password').attr('data-parsley-required', 'true');
  $('#password').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');
  
  $('#password').attr('data-parsley-length', '[6,100]');
  $('#password').attr('data-parsley-length-message', '<spring:message code="validate.length"/>');
//   $('#password').attr('data-parsley-pattern', '^.*(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%&? "]).*$');
//   $('#password').attr('data-parsley-pattern-message', 'Password should consist of letters, numbers, and special characters');

  $('#re-password').attr('data-parsley-equalto', '#password');
  $('#re-password').attr('data-parsley-equalto-message', '<spring:message code="validate.equalto"/>');
	
  $('#email').attr('data-parsley-required', 'true');
  $('#email').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');
  
  $('#email').attr('data-parsley-maxlength', '100');
  $('#email').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');
  
  $('#email').attr('data-parsley-type', 'email');
  $('#email').attr('data-parsley-type-message', '<spring:message code="validate.type.email"/>');

	$(".add-user").on('click',function(){
		clearErrorMsg();
	});
	//callback handler for form submit
  $("form.add-user").submit(function(e)
  {
	  clearErrorMsg();
	  startSpinning();
      var postData = $(this).serializeArray();
      var formURL = $(this).attr("action");
      $.ajax(
      {
          url : formURL,
          type: "POST",
          data : postData,
          success:function(data) {
        	  if(data.toLowerCase().indexOf('<spring:message code="common.successfully"/>') < 0){
             	$("p.error").html(data);
              	$("p.error").removeAttr('style');
        	  } else { // success
		       		if($('#id').val() == '') { //add new user
		       			$("p.msg").html(data);
		               	$("p.msg").removeAttr('style');
		       	  	} else { //update user
		       	  		localStorage.setItem('editUserMessage', data);
		       	  		//redirect to user management page
		       	  		window.location.href = '${userManage}';
		       	  	}
  	  
        	  }
        	  stopSpinning();
          },
          error: function(jqXHR, textStatus, errorThrown) {
              //if fails  
        	  stopSpinning();
          },
          complete: function(xhr) {
              if (xhr.status == 601) {
            	  window.location.href = '${errorUrl}';
              }
          }
      });
      e.preventDefault(); //STOP default action
      e.unbind(); //unbind. to stop multiple form submit.
  	});
  
	$('form.add-user input[type="reset"]').click(function () {
      $('form.add-user')[0].reset();
      $('form.add-user').parsley().reset();
      clearErrorMsg();
      setDefaultUserRole();
  	});
	
	function clearErrorMsg(){
		$("p.error").html('');
     	$("p.error").css('display','none');
      	$("p.msg").html('');
     	$("p.msg").css('display','none');
	}
	
	function startSpinning() {
		$.blockUI({
			message : $('#spining')
		});
	}
	function stopSpinning() {
		$.unblockUI();
	}
	function setDefaultUserRole() {
		if(typeof $('input:radio[name=roles]:checked').val() === 'undefined'){
			$('#role-user').attr('checked',true);
		}
	}
</script>