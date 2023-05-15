//이메일 인증 확인
function AntiSpam(id){ 
	window.open('/popup/anticheck?UniqId='+id,'AntiSpam','width=440,height=290,left=50,top=50'); 
} 

//이메일 인증 확인 - 영문
function AntiSpamEng(id){ 
	window.open('/popup/anticheck?type=eng&UniqId='+id,'AntiSpam','width=440,height=290,left=50,top=50'); 
} 
