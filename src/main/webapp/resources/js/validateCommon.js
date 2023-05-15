
function isEmpty(param) {
	var rtnVal;
	if(param == null || param == "" || param == undefined) {
		rtnVal = true;
	} else {
		rtnVal = false;
	}
	return rtnVal;
}

function isNotEmpty(param) {
	var rtnVal;
	if(!isEmpty(param)) {
		rtnVal = true;
	} else {
		rtnVal = false;
	}
	return rtnVal;
}

function fn_onlyNumber(event){
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else
		return false;
}

function fn_removeChar(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else {
		(event.srcElement || event.target).value = (event.srcElement || event.target).value.replace(/[^0-9]/g, "");
	}
}

// 비밀번호 정규식
function fn_pwdValidation(param) {
	
	var str = param;
	var patt = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*_+=]).*$/);
	var res = patt.test(str);
	
	return res;
}

// 특수문자 여부 확인
function fn_specialCharacterValidation(param) {
	
	var str = param;
	var patt = new RegExp(/[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi);
	var res = patt.test(str);
	
	return res;
}
