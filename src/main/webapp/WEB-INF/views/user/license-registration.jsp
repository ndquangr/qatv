<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="/license-manage" var="licenseManage" htmlEscape="true" />
<spring:url value="/error" var="errorUrl" htmlEscape="true" />
<spring:message code="common.dateFormat" var="dateFormat"/>
<br>
<form:form id="add-license" action="add-license" method="post" commandName="licenseForm">

	<div id="content-add-license">
		<div class="add-license">
			<div class="row">
				<div class="left">
					<label for="customerName"><spring:message code="addLicense.customerName"/></label>
				</div>
				<div class="right">
					<input type="hidden" name="id" value="${licenseForm.id}" id="id">
					<input type="text" tabindex="1" maxlength="100" name="customerName" id="customerName" value="${licenseForm.customerName}">
				</div>
			</div>

			<div class="row">
				<div class="left">
					<label for="address"><spring:message code="addLicense.address"/></label>
				</div>
				<div class="right">
					<form:textarea path="address" rows="2" id="address" tabindex="2" value="${licenseForm.address}" maxlength="200" />
				</div>
			</div>

			<div class="row">
				<div class="left">
					<label for="person"><spring:message code="addLicense.person"/></label>
				</div>
				<div class="right">
					<input type="text" tabindex="3" maxlength="100" name="personIncharge" id="person" value="${licenseForm.personIncharge}">
				</div>
			</div>
			<div class="row">
				<div class="left">
					<label for="phone"><spring:message code="addLicense.phone"/></label>
				</div>
				<div class="right">
					<input type="text" tabindex="4" maxlength="13" name="phone" id="phone" value="${licenseForm.phone}">
				</div>
			</div>

			<div class="row">
				<div class="left">
					<label for="email"><spring:message code="addLicense.email"/></label>
				</div>
				<div class="right">
					<input type="text" tabindex="5" maxlength="100" name="email" id="email" value="${licenseForm.email}">
				</div>
			</div>

			<div class="row">
				<div class="left">
					<label for="purchaseDate"><spring:message code="addLicense.purchaseDate"/></label>
					<p class="format"><spring:message code="common.dateFormat"/></p>
				</div>
				<div class="right">
					<fmt:formatDate var="purchaseDate" value="${licenseForm.purchaseDate}" pattern="${dateFormat }"/>
					<input type="text" tabindex="6" maxlength="10" name="purchaseDate" id="purchaseDate" class="datepicker" value="${purchaseDate}">
				</div>
			</div>
			<div class="row">
				<div class="left">
					<label for="paymentDate"><spring:message code="addLicense.paymentDate"/></label>
					<p class="format"><spring:message code="common.dateFormat"/></p>
				</div>
				<div class="right">
					<fmt:formatDate var="paymentDate" value="${licenseForm.paymentDate}" pattern="${dateFormat }"/>
					<input type="text" tabindex="7" maxlength="10" name="paymentDate" id="paymentDate" class="datepicker" value="${paymentDate}">
				</div>
			</div>
		</div>

		<div class="add-license inright" >
			<div class="row">
				<div class="left">
					<label for="domain"><spring:message code="addLicense.domain"/></label>
				</div>
				<div class="right">
					<input type="text" tabindex="8" maxlength="63" name="domain" id="domain" value="${licenseForm.domain}">
				</div>
			</div>
			<div class="row">
				<div class="left">
					<label for="version"><spring:message code="addLicense.version"/></label>
				</div>
				<div class="right">
					<input type="text" tabindex="9" maxlength="10" name="version" id="version" value="${licenseForm.version}">
				</div>
			</div>
			<div class="row">
				<div class="left" style="vertical-align: inherit;">
					<label for="trial" tabindex="10"><spring:message code="addLicense.licenseType"/></label>
				</div>
				<div class="right radio">
					<form:radiobutton path="licenseType" id="trial"  value="0"/><label for="trial" style="margin-right: 2em"><spring:message code="addLicense.trial"/></label>
					<form:radiobutton path="licenseType" id="paid" value="1" /><label for="paid"><spring:message code="addLicense.paid"/></label>
				</div>
			</div>
			<div class="row">
				<div class="left">
					<label for="license-date"><spring:message code="addLicense.licenseDate"/></label>
					<p class="format"><spring:message code="common.dateFormat"/></p>
				</div>
				<div class="right">
					<fmt:formatDate var="licenseDate" value="${licenseForm.licenseDate}" pattern="${dateFormat}"/>
					<input type="text" tabindex="11" maxlength="10" name="licenseDate" id="license-date" value="${licenseDate}" class="datepicker">
				</div>
			</div>
			<div class="row">
				<div class="left">
					<label for="licenseNo"><spring:message code="addLicense.licenseNo"/></label>
				</div>
				<div class="right">
					<form:textarea path="licenseNumber" rows="4" id="licenseNo" class="disabled" readonly="true" value="${licenseForm.licenseNumber}"/>
				</div>
			</div>
			<br>

			<div class="row">
				<div class="left"></div>
				<div class="right submit">
					<p class="error" style="display: none;"></p>
					<p class="msg" style="display: none;"></p>
					<input type="submit" value="${submit}" tabindex="12">
					<input type="reset" value="<spring:message code="common.reset"/>" tabindex="13">
				</div>
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript">
//set default radio button
	$(document).ready(function() {
		setDefaultTrial();
	});
	//alert when change paid to trial
	$("input[name=licenseType]:radio").change(function () {
		var type = $(this).val();
		//Edit license and change license type from [paid] to [trial]
		if(type == "0" && $('#id').val() != "") {
			var r = confirm('<spring:message code="addLicense.confirm.changeLicenseType"/>');
			if (r == false){ //set val back to paid
				$('#paid').prop("checked", true);
				return;
			}
			//reset purchase date
			$('#purchaseDate').val('');
		}
	});

	//custom parsley validator
	window.ParsleyValidator.addValidator('paymentdate',
		function (value, requirement) {
		    return value >= $(requirement).val();
		},32).addMessage('en', 'paymentdate', '<spring:message code="validate.custom.paymentdate"/>');

	window.ParsleyValidator.addValidator('purchasedate',
		function (value, requirement) {
		    return value <= $(requirement).val();
		},32).addMessage('en', 'purchasedate', '<spring:message code="validate.custom.purchasedate"/>');

 	window.ParsleyValidator.addValidator('domain',
		function (value, requirement) {
			var regex1 = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
			var regex2 = /^[a-zA-Z0-9]+([\-\.]{1}[a-zA-Z0-9]+)*\.[a-zA-Z]{2,}$/;
		    return (regex1.test(value.toLowerCase()) || regex2.test(value.toLowerCase()));
		},32).addMessage('en', 'domain', '<spring:message code="validate.type.url"/>');

	$('#add-license').parsley({
		errorsWrapper: '<span class="validate"></span>',
		errorTemplate: "<p></p>",
	});

	$( ".datepicker" ).datepicker({
		dateFormat: '<spring:message code="datepicker.format"/>'
	});
	
	$('#customerName').attr('data-parsley-maxlength', '100');
	$('#customerName').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');

	$('#customerName').attr('data-parsley-required', 'true');
	$('#customerName').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');

	$('#domain').attr('data-parsley-maxlength', '100');
	$('#domain').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');

	$('#domain').attr('data-parsley-required', 'true');
	$('#domain').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');
	
	$('#domain').attr('data-parsley-domain', '');
	
	$('#version').attr('data-parsley-maxlength', '10');
	$('#version').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');

	$('#version').attr('data-parsley-required', 'true');
	$('#version').attr('data-parsley-required-message', '<spring:message code="validate.required"/>');

	$('#person').attr('data-parsley-maxlength', '100');
	$('#person').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');

	$('#phone').attr('data-parsley-pattern', '^[0-9+]*$');
	$('#phone').attr('data-parsley-pattern-message', '<spring:message code="validate.pattern"/>');

	$('#phone').attr('data-parsley-length', '[8,13]');
	$('#phone').attr('data-parsley-length-message', '<spring:message code="validate.length"/>');

	$('#email').attr('data-parsley-maxlength', '100');
	$('#email').attr('data-parsley-maxlength-message', '<spring:message code="validate.maxlength"/>');

	$('#email').attr('data-parsley-type', 'email');
	$('#email').attr('data-parsley-type-message', '<spring:message code="validate.type.email"/>');

	$('#purchaseDate').attr('data-parsley-purchasedate', '#paymentDate');

	$('#paymentDate').attr('data-parsley-paymentdate', '#purchaseDate');

	$("#add-license").on('click',function(){
		clearErrorMsg();
	});

	//callback handler for form submit
	$("#add-license").submit(function(e)
	{
		clearErrorMsg();
	 	startSpinning();
	    var postData = $(this).serializeArray();
	    var formURL = $(this).attr("action");

	    //pass disable input data
// 	    $('#add-license input[disabled]').each( function() {
// 	    	postData.push({name: $(this).attr('name'), value: $(this).val()});
//         });

	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	        dataType: "json",
	        success:function(data) {
	      	  if(data.msg.toLowerCase().indexOf('<spring:message code="common.successfully"/>') < 0){
	           	$("p.error").html(data.msg);
	            	$("p.error").removeAttr('style');
	      	  } else {	//success
	              	if($('#id').val() == '') { //add new license
	              		$("p.msg").html(data.msg);
		              	$("p.msg").removeAttr('style');
		              	$("#licenseNo").val(data.licenseNumber);
		       	  	} else { //update user
		       	  		localStorage.setItem('editLicenseMessage', data.msg);
		       	  		//redirect to license management page
		       	  		window.location.href = '${licenseManage}';
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
	    e.unbind(); 		//unbind. to stop multiple form submit.
	});

    //Process clear all items.
	$('#add-license input[type="reset"]').click(function () {
	    $('#add-license')[0].reset();
	    $('#add-license').parsley().reset();
	    clearErrorMsg();
	    setDefaultTrial();
	});

	//Clear error message
	function clearErrorMsg(){
		$("p.error").html('');
     	$("p.error").css('display','none');
      	$("p.msg").html('');
     	$("p.msg").css('display','none');
	}

	//Start spinning
	function startSpinning() {
		$.blockUI({
			message : $('#spining')
		});
	}

	//Stop spinning
	function stopSpinning() {
		$.unblockUI();
	}

	//Set default trial license.
	function setDefaultTrial() {
		if(typeof $('input:radio[name=licenseType]:checked').val() === 'undefined'){
			$('#trial').prop('checked',true);
		}
	}
</script>