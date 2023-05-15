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

/*function downLocation(aftNm, befNm){		
	var link = document.createElement("a");
	link.download = befNm;
	link.href = '/fatt/'+aftNm;
	link.click();
}*/

function contentsDown(aftNm, befNm, folder_nm){		
	var downForm = document.createElement("form");
	var input1 = document.createElement("input");
	var input2 = document.createElement("input");
	var input3 = document.createElement("input");
	input1.setAttribute("type","hidden");
	input2.setAttribute("type","hidden");
	input3.setAttribute("type","hidden");
	input1.setAttribute("name","file1");
	input2.setAttribute("name","file2");
	input3.setAttribute("name","path");
	input1.setAttribute("value",befNm);
	input2.setAttribute("value",aftNm);
	if(folder_nm != "" && folder_nm != null){
		input3.setAttribute("value", folder_nm);
	}

	downForm.appendChild(input1);
	downForm.appendChild(input2);
	downForm.appendChild(input3);

	downForm.setAttribute("method", "post");
	downForm.setAttribute("action", "/web-front/comm/fileDownload");
	document.body.appendChild(downForm);
	downForm.submit();
}

function downLocation(seq){		

	var downForm = document.createElement("form");
	var input = document.createElement("input");
	input.setAttribute("type","hidden");
	input.setAttribute("name","seq");
	input.setAttribute("value",seq);

	downForm.appendChild(input);
	
	downForm.setAttribute("method", "post");
	downForm.setAttribute("action", "/web-front/comm/fileDownload");
	document.body.appendChild(downForm);
	downForm.submit();

}
/*****************************************************************
 * 게시판 상단의 검색처리
 * @param form 폼 오브젝트
 ****************************************************************/
function fn_bbs_search(form) {
    var searchClsf = form.clsf_cd.value;
	var searchType = form.searchType.value;
	var searchTxt = encodeURIComponent(form.searchTxt.value);
    var searchUrl = form.action + "?searchType=" + searchType + "&searchTxt=" + searchTxt;
    if(searchClsf != ''){
    	searchUrl = form.action + "?clsf_cd=" + searchClsf + "&searchType=" + searchType + "&searchTxt=" + searchTxt, "_self";
    } 

	window.open(searchUrl, "_self");
	return false;

}
