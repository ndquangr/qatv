<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ page import="org.springframework.web.servlet.support.RequestContext"%> 
<% String lang = (new RequestContext(request)).getLocale().getLanguage();%>
<spring:url value="/user-manage/list" var="listUser" htmlEscape="true" />
<spring:url value="/user-manage/delete" var="deleteUser" htmlEscape="true" />
<spring:url value="/user-manage/edit-user" var="editUser" htmlEscape="true" />
<spring:url value="/" var="baseUrl" htmlEscape="true" />
<spring:url value="/error" var="errorUrl" htmlEscape="true" />
<div class="update-message">
	<p class="msg ontop" style="display: none;"></p>
</div>
<br>
<div id="search-user">
	<table class="search-bar">
			<tr>
				<td class="label"><label for="keyWord"><spring:message code="userManage.userName"/></label></td>
				<td><input type="text" id="keyWord" name="keyWord" maxlength="100"/></td>
			</tr>
	</table>
	<input type="button" value="<spring:message code="common.search"/>" />
</div>

<table id="user-manage" class="display">
	<thead>
		<tr>
			<th>ID</th>
			<th><spring:message code="userManage.userName"/></th>
			<th><spring:message code="userManage.email"/></th>
			<th><spring:message code="userManage.regDate"/><p class="format"><spring:message code="common.dateTimeFormat"/></p></th>
			<th><spring:message code="userManage.role"/></th>
			<th></th>
		</tr>
	</thead>

</table>

<script>
	var table;
	$(document).ready(function() {
		
		//check edit user information
		var msg = localStorage.editUserMessage;
		if (typeof msg !== "undefined") {
		    localStorage.removeItem('editUserMessage');
			//display message
			$("p.msg").html(msg);
           	$("p.msg").removeAttr('style');
           	
          //set time out update message
    		setTimeout(function() {
    			$("p.msg").fadeOut("slow");
    		}, 5000);
          
		} else if(escape(document.referrer).indexOf('edit-user') < 0){
			localStorage.removeItem( 'DataTables_user-manage_'+window.location.pathname); //remove state of datatable
		}
		
		table = $('#user-manage').DataTable({
			"bFooter" : false,
			"sDom" : '<"top"lp>rt<"bottom"ip><"clear">',
			"bPaginate" : true,
			"sPaginationType" : "full_numbers",
			'aLengthMenu': [[20, 50, 100, -1], [20, 50, 100, '<spring:message code="common.all"/>']],
			
			"aoColumnDefs": [ 
				{
		            "aTargets": [0],
		            "bSortable": false,
		            "bVisible": false,
	        	},
				{
		            "aTargets": [-1],
		            "mData": null,
		            "bSortable": false,
		            "mRender": function (data, type, full) {
		                var edit = '<a class="edit" index="'+ full[0] +'"><spring:message code="common.edit"/></a>  	&nbsp; 	&nbsp;';
		                var del  = '<a class="delete" index="'+ full[0] +'"><spring:message code="common.delete"/></a> ';
		                if(full[4]=='<spring:message code="controller.userManager.roleAdmin"/>'){
		                	return edit;
		                }
		                return edit + del;
		            },
		            "sClass": "action",
		            "sWidth": "8em",
	        	},
				
			   ],
			"aaSorting": [[ 3, "desc" ]],
			"iDisplayLength" : 20,
// 			"bProcessing": true,
	        "bServerSide": true,
	        "sServerMethod": "GET",
	        "sAjaxSource": "${listUser}",
	        "autoWidth": false,
	        "oLanguage": {
	        	  "sUrl": "${baseUrl}resources/js/dataTable_<%=lang%>.json"
	        },
	        "bStateSave": true,
	        "stateLoadParams": function (settings, data) {
	            //set back param to search form
	            var keyWord = data.columns[0].search.search;
	            $('#keyWord').val(keyWord);
	        },
	        
		});
		$('#user-manage').on('processing.dt',function(e, settings, processing){
			if (processing){
				startSpinning();
			}else {
				stopSpinning();
			}
		});
	    
		$(document).on('click', '#search-user input[type ="button"]', function(e) {
		    var keyword =   $('#keyWord').val().trim();
			//search by username and order by registration date
		    table.columns(0).search(keyword);
			table.order([ 3,'desc']).draw();
		});
		
	});
	
	$.fn.dataTable.ext.errMode = function ( settings, helpPage, message ) { 
 		window.location.href = '${errorUrl}';
	};
	
	//delete user
	$(document).on('click', 'a.delete', function(e) {
		var r = confirm('<spring:message code="userManage.performDelete"/>');
		if (r == false) {
			return false;
		}
		startSpinning();
		var id = $(this).attr("index");
		//delete user by id
		$.ajax({
			url : "${deleteUser}",
			type : "post",
			data : {
				'id': id
			},
			success:function(data){
				if(data.toLowerCase().indexOf('<spring:message code="common.successfully"/>') < 0){
					alert(data);
				}
				table.draw();
				stopSpinning();
			},
			error : function(respone) {
				stopSpinning();
			},
			
			complete: function(xhr) {
                if (xhr.status == 601) {
                	window.location.href = '${errorUrl}';
                }
            }
		});
	});
	
	//edit user
	$(document).on('click', 'a.edit', function(e) {
		table.state.save();
		window.location.href = '${editUser}' + '?id='+$(this).attr("index");
	});
	
	function startSpinning() {
		$.blockUI({
			message : $('#spining')
		});
	}
	function stopSpinning() {
		$.unblockUI();
	}
	
</script>