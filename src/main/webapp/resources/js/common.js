function restoreHtml(content){
	if(content == "" ){
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