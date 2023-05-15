function sendTwitter(title, url) {
	title = title.replace("#39;", "&#39;");
	url = "http://nnfcweb.a2m.co.kr"+url;
	//url = "http://www.nnfc.re.kr"+url;
	var wp = window.open("http://twitter.com/home?status="+encodeURIComponent(title)+" "+encodeURIComponent(url),'twitter','');

	if(wp) {
		wp.focus();
	}
}
	
function sendFaceBook(title, url) {
	title = title.replace("#39;", "&#39;");
	url = encodeURIComponent("http://nnfcweb.a2m.co.kr"+url);
	//url = encodeURIComponent("http://www.nnfc.re.kr"+url);
	var wp = window.open("http://www.facebook.com/sharer.php?u=" + url + "&t="+encodeURIComponent(title),'facebook', '');
	if(wp) {
		wp.focus();
	}
}

//네이버 블로그
function sendNaverBlog(paramTitle, paramUrl) {
    var title = paramTitle.replace("#39;", "&#39;");
    var url = "http://nnfcweb.a2m.co.kr" + paramUrl;
    var wp = window.open("http://share.naver.com/web/shareView.nhn?url=" + encodeURIComponent(url) + "&title=" + encodeURIComponent(title));
 
    if(wp) {
       wp.focus();
    }
}

function printContents(){
	var lang = document.location.href;
	var initBody = null;
	var printArea = document.getElementById('print').innerHTML; 
	var beforePrint = function() {	
		initBody = document.body.innerHTML;
		document.body.innerHTML =  document.getElementById('print').innerHTML;
		console.log('이 Function은 프린트 이전에 호출됩니다.');
	};
	var afterPrint = function() {
		document.body.innerHTML = initBody;
		console.log('이 Function은 프린트 이후에 호출됩니다.');
	};

	try{
		if (window.matchMedia) {
			var mediaQueryList = window.matchMedia('print');
			mediaQueryList.addListener(function(mql) {
				if (mql.matches) {
					console.log("print before");
					beforePrint();
				} else {
					console.log("print after");
					afterPrint();
				}
			});
		}
	}
	catch(err)
	{
		//alert(err.description);
	}
	
	window.onbeforeprint = beforePrint;
	window.onafterprint = afterPrint;
	
	// 새창으로 인쇄 - IE
	var win = window.open();
	self.focus();
	//win.document.open();
	win.document.write('<!DOCTYPE html>');
	win.document.write('<html>');
	win.document.write('<head>');
	win.document.write('<meta http-equiv="X-UA-Compatible" content="IE=Edge">');
	win.document.write('<link href="/resources/css/bootstrap.css" rel="stylesheet">');
	if(lang.indexOf('/eng/') == -1){
		win.document.write('<link href="/resources/css/style.css" rel="stylesheet">');
		win.document.write('<link href="/resources/css/contents-board.css" rel="stylesheet">');
		win.document.write('<link href="/resources/css/contents-type3.css" rel="stylesheet">');
	} else {
		win.document.write('<link href="/resources/css/en-style.css" rel="stylesheet">');
		win.document.write('<link href="/resources/css/en-contents-board.css" rel="stylesheet">');
		win.document.write('<link href="/resources/css/en-contents-type2.css" rel="stylesheet">');
		win.document.write('<link href="/resources/css/en-contents-type3.css" rel="stylesheet">');
	}
	win.document.write('<link href="/resources/css/print.css" rel="stylesheet">');
	win.document.write('<title>AtwoM 인쇄 미리보기</title>');
	win.document.write('</head>');
	win.document.write('<body style="width:1030px; margin:0 auto;">');
	win.document.write('<section class="wrapper site-min-height" id="printPreview" style="width:1030px; margin:0 auto;">');
	win.document.write('<button type="button" class="btn btn-default pull-right sns_box" onclick="javascript:window.print();">print <i class="glyphicon glyphicon-print"></i></button>');
	win.document.write(printArea);
	win.document.write('<button type="button" class="btn btn-default pull-right sns_box" onclick="javascript:window.print();">print <i class="glyphicon glyphicon-print"></i></button>');
	win.document.write('</section>');
	win.document.write('</body>');
	win.document.write('</html>');
	win.document.close();
	//win.print();
	//win.close();
	
	//window.print();
	return false;
}