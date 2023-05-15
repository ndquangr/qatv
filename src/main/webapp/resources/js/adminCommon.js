function ajaxReplaceHtmlJsonData(_id, _data, _url, _key){
	$.ajax({
		type: "GET",
		url: _url,
		cache: false,
		data:_data,
		success: function(response) {
			var length = _key.length;
			for(var i =0; i < length; i++){
				var val = eval(response[_key[i]]);
				$("#" + _id[i]).html(val);
				if(val == 0){
					$("#" + _id[i]).remove();
				}
			}
		}
	});
}

function ajaxReplaceHtml(_id, _data, _url){
	$.ajax({
		type: "GET",
		url: _url,
		cache: false,
		data:_data,
		success: function(response) {
			$(_id).html( response );
		}
	});
}

function ajaxReplaceHtmlWithReload(_id, _data, _url){
	$.ajax({
		type: "GET",
		url: _url,
		cache: false,
		data:_data,
		success: function(response) {
			$(_id).html( response );
			timeReload();
		}
	});
}

function ajaxReplaceHtmlWithModal(_id, _data, _url, modal_id){
	$.ajax({
		type: "GET",
		url: _url,
		cache: false,
		data:_data,
		success: function(response) {
			$(_id).html("");

			$(_id).html( response );
			$(modal_id).modal("show");

			// modal 팝업 열 때마다, div 을 하나씩 추가로 생성하는 버그가 발생하여, remove 로직 적용함.
			// 2015-07-27, 차동환K
			$(modal_id).each(function () {
				if ( $(this).attr("style") == 'display: none;' ) {
					$(this).remove();
				}
			});
		}
	});
}

// 리스트형 modal 팝업 열때 여러개가 열리는 현상이 있어 수정
// 2015-08-25
function ajaxReplaceHtmlWithModalList(_id, _data, _url, modal_id){
	$.ajax({
		type: "GET",
		url: _url,
		cache: false,
		data:_data,
		success: function(response) {
			
			$(_id).html("");
			$(_id).html(response);
			
			$(modal_id).each(function () {
				if ( $(this).attr("style") == 'display: none;' ) {
					$(this).remove();
				}
			});
		}
	});
}

function openDaumPostcode() {
	var modal_body = "<div id=\"address_form\" class=\"modal fade\">"
		+ "<div class=\"modal-dialog modal-lg\">"
		+ "<div class=\"modal-content\">"
		+ "<div class=\"modal-header\">"
		+ "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>"
		+ "<h4 class=\"modal-title\">주소검색</h4>"
		+ "</div>"
		+ "<div id=\"addressBody\" class=\"modal-body\">"
		+ "</div>"
		+ "<div class=\"modal-footer\">"
		+ "<div id=\"address_table\"></div>"
		+ "<div><p class=\"text-center find_id\" ></p></div>"
		+ "<div class=\"dis-b-n\" style=\"display:none;\"><p class=\"text-center\"><a onclick=\"javascript:setUniqid();\">확인</a></div>"
		+ "<p class=\"text-center btn-use\" style=\"display:none;\">"
		+ "<button class=\"btn btn-success\" onclick=\"setUniqid();\">사용하기</button>"
		+ "</p>"
		+ "</div>"
		+ "</div><!-- /.modal-content -->"
		+ "</div><!-- /.modal-dialog -->"
		+ "</div><!-- /.modal -->";
	if($("#address-modal").length < 1){
		$("body").append($("<div>").attr("id", "address-modal"));
	}
	$("#address-modal").html(modal_body);
	var element = document.getElementById('addressBody');//$("#address-modal #addressBody");

    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 우편번호와 주소 정보를 해당 필드에 넣고, 커서를 상세주소 필드로 이동한다.
            document.getElementById('zipcode').value = data.postcode1 + "-" + data.postcode2;
            //document.getElementById('post2').value = data.postcode2;
            document.getElementById('addr1').value = data.address;

            //전체 주소에서 연결 번지 및 ()로 묶여 있는 부가정보를 제거하고자 할 경우,
            //아래와 같은 정규식을 사용해도 된다. 정규식은 개발자의 목적에 맞게 수정해서 사용 가능하다.
            //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
            //document.getElementById('addr').value = addr;

            document.getElementById('addr2').focus();
        	$("#address_form").modal("hide");
        }
    }).embed(element);
	$("#address_form").modal("show");
}

function openDaumPostcodeSeperate() {
	var modal_body = "<div id=\"address_form\" class=\"modal fade\">"
		+ "<div class=\"modal-dialog modal-lg\">"
		+ "<div class=\"modal-content\">"
		+ "<div class=\"modal-header\">"
		+ "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>"
		+ "<h4 class=\"modal-title\">주소검색</h4>"
		+ "</div>"
		+ "<div id=\"addressBody\" class=\"modal-body\">"
		+ "</div>"
		+ "<div class=\"modal-footer\">"
		+ "<div id=\"address_table\"></div>"
		+ "<div><p class=\"text-center find_id\" ></p></div>"
		+ "<div class=\"dis-b-n\" style=\"display:none;\"><p class=\"text-center\"><a onclick=\"javascript:setUniqid();\">확인</a></div>"
		+ "<p class=\"text-center btn-use\" style=\"display:none;\">"
		+ "<button class=\"btn btn-success\" onclick=\"setUniqid();\">사용하기</button>"
		+ "</p>"
		+ "</div>"
		+ "</div><!-- /.modal-content -->"
		+ "</div><!-- /.modal-dialog -->"
		+ "</div><!-- /.modal -->";
	if($("#address-modal").length < 1){
		$("body").append($("<div>").attr("id", "address-modal"));
	}
	$("#address-modal").html(modal_body);
	var element = document.getElementById('addressBody');//$("#address-modal #addressBody");

    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 우편번호와 주소 정보를 해당 필드에 넣고, 커서를 상세주소 필드로 이동한다.
            document.getElementById('post_no1').value = data.postcode1;
            document.getElementById('post_no2').value = data.postcode2;
            document.getElementById('addr').value = data.address;

            //전체 주소에서 연결 번지 및 ()로 묶여 있는 부가정보를 제거하고자 할 경우,
            //아래와 같은 정규식을 사용해도 된다. 정규식은 개발자의 목적에 맞게 수정해서 사용 가능하다.
            //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
            //document.getElementById('addr').value = addr;

            document.getElementById('detl_addr').focus();
        	$("#address_form").modal("hide");
        }
    }).embed(element);
	$("#address_form").modal("show");
}

function isNull(str){
	if(str == null || str.length < 1){
		return true;
	}
	
	return false;
}

/*function loginWithNaver(){
	window.open("/naver/loginForm", 'pop_9', 'width=600, height=600, scrollbars=no');
}

function loginWithTwitter(){
	window.open("/twitter/loginForm", 'pop_9', 'width=600, height=600, scrollbars=no');
}

function loginWithFacebook(){
	window.open("/facebook/loginForm", 'pop_9', 'width=600, height=600, scrollbars=no');
}

function loginWithGoogle(){
	window.open("/google/loginForm", 'pop_9', 'width=600, height=600, scrollbars=no');
}

function openLoginForm(){
	window.open("/login", 'pop_9', 'width=600, height=600, scrollbars=no');
}

function shareWithSNS(snsType, post_seq){
	var _url = "";
	var msg = $("#bbs_view_" + post_seq + " .fb-user-status").html();
	
	if(snsType == 'facebook'){
		_url = "/facebook/post";
	} else if(snsType == 'twitter'){
		_url = "/twitter/tweet";
	}
	$.ajax({
		type: "POST",
		url: _url,
		cache: false,
		data:{"message":msg},
		success: function(jsonData){
			var flag = eval(jsonData.success);
			if(flag == 1){
				alert("공유되었습니다.");
			} else if(flag == 2) {
				alert("연동된 SNS 계정이 없습니다. 먼저 SNS 계정을 연동해주세요.");
			} else {
				alert("전송 중 오류가 발생했습니다. 잠시 후 다시 시도하시거나, 관리자에게 문의해주세요.");
			}
		}
	});
	
	return false;
}*/

function restoreHtml(content){
	if(content == "" || $.trim(content) == ""){
		return content;
	}
	
	content = content.replace(/\&lt;/gi,"<");
	content = content.replace(/\&gt;/gi,">");
	content = content.replace(/\&amp;lt;/gi,"&lt;");
	content = content.replace(/\&amp;gt;/gi,"&gt");
	content = content.replace(/\[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']/gi, "\"\"");
	content = content.replace(/\script/gi, "");
	content = content.replace(/\&#40;/gi,"(");
	content = content.replace(/\&#41;/gi,")");
	content = content.replace(/\&#39;/gi, "'"); 
	return content;
}

/*****************************************************************
 * 세부연구과제 첨부파일 다운로드 (bbsCommon.js 참조)
 * @param aftNm 첨부 변경후 파일명
 * @param befNm 첨부 변경전 파일명
 * @param sub_folder_nm 서브폴더명 (optional)
 ****************************************************************/
function downLocation_RFP(aftNm, befNm, sub_folder_nm) {
	var downForm = document.createElement("form");
	downForm.setAttribute("name", "downForm");

	var input1 = document.createElement("input");
	var input2 = document.createElement("input");
	var input3 = document.createElement("input");

	input1.setAttribute("type", "hidden");
	input2.setAttribute("type", "hidden");
	input3.setAttribute("type", "hidden");

	input1.setAttribute("name", "file1");
	input2.setAttribute("name", "file2");
	input3.setAttribute("name", "path");

	input1.setAttribute("value", befNm);
	input2.setAttribute("value", aftNm);

	if ( sub_folder_nm != null && sub_folder_nm != "" ) {
		input3.setAttribute("value", "/fatt" + "/" + sub_folder_nm);
	} else {
		input3.setAttribute("value", "/fatt");
	}

	downForm.appendChild(input1);
	downForm.appendChild(input2);
	downForm.appendChild(input3);

	downForm.setAttribute("method", "post");
	downForm.setAttribute("action", "/web-front/comm/fileDownload");
	document.body.appendChild(downForm);
	// console.log( $("form[name='downForm']").html() );
	downForm.submit();
}
