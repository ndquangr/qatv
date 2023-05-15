// resize 
var webCnt = 0;
var mobileCnt = 0;
var scrollFalg = 0;
var tabletSize = 1153;
var mobileSize = 733;
var agt = navigator.userAgent.toLowerCase();
var trident = navigator.userAgent.match(/\/(\d)/i);
//alert(agt);
//alert(trident);

if(agt.indexOf("msie 8.0") != -1){
	//alert('8');
}

$(document).ready(function(){
	/* 정리 필요한 영역 */
	var delta = 300;
	var timer = null;
	
	$(window).on('resize', function( ) {

		// 마우스휠 이벤트 발생후 resize 이벤트가 발생하는 경우에 Cnt 값 초기화 필요 
		if(scrollFlag){
			webCnt = 0;
			mobileCnt = 0;			
		}

	    clearTimeout(timer);
	    timer = setTimeout(resizeDone, delta);
	});

	resizeDone();
	
	// resize function 대체
	function resizeDone() {
		var str = "";
		var currentWidth = $(window).width();
		//alert(currentWidth);
    	var cleft = $("#container").css("left");
    	//alert(cleft);
    	cleft = cleft.replace('px', ''); 
    	if(parseInt(cleft) < 0){
    		$("#container").css("left", 200);
    	}

    	// setTimeout 초기화
    	clearInterval(play_s); clearInterval(play_p); clearInterval(play_w); clearInterval(play_t); clearInterval(play_m);
    	clearInterval(play_n);

    	// .popup_block [START]
    	if(currentWidth >= tabletSize && currentWidth > 1870){
    		//alert(currentWidth);
    		rotateSwitch_n();
    		$('#wrap').unbind('mousewheel');
			slideArrow(0);
    	} else if(currentWidth >= tabletSize || agt.indexOf("msie 8.0") != -1){ // web
			if(webCnt=='0'){
				$("#container").css("left", 200);
				
				$("a[rel='1']").click();
				var div_area = $(".slide-visual-area:visible").attr('id'); 
				$("#p_control a").attr("class", "icon-play");
				$("#p_control a").find("span").text("자동재생 시작");
				$("#p_control a").click();
				
				$("#s_control a").attr("class", "icon-play");
				$("#s_control a").find("span").text("자동재생 시작");
				$("#s_control a").click();
				
				rotateSwitch_n();
			}
			webCnt++;
			mobileCnt=0;
			
			slideArrow(1);	//슬라더 화살표
			
			// mouse wheel eventListener
			$("#wrap").mousewheel(function(event, delta, deltaX, deltaY){
				if (delta > 0) {
					handle(1);
				}
				if (deltaY < 0) {
					handle(-1);
				}
				event.stopPropagation();
				event.preventDefault();
			});
			
		} else if(currentWidth < tabletSize && agt.indexOf("msie 8.0") == -1){ // tablet, mobile
			if(mobileCnt=='0'){
				$("a[rel='1']").click();
				var div_area = $(".slide-visual-area:visible").attr('id'); 
				$("#p_control a").attr("class", "icon-play");
				$("#p_control a").find("span").text("자동재생 시작");
				$("#p_control a").click();
				
				$("#s_control a").attr("class", "icon-play");
				$("#s_control a").find("span").text("자동재생 시작");
				$("#s_control a").click();
			}
			mobileCnt++;
			webCnt=0;
			
			$('#wrap').unbind('mousewheel');
			slideArrow(0);	//슬라더 화살표
		}
		// .popup_block [END]

		// .main_new_visual [START]
		$("a[rel='1']").click();
		var div_area = $(".slide-visual-area:visible").attr('id'); 
		$("#"+div_area+"_control a").attr("class", "icon-play");
		$("#"+div_area+"_control a").find("span").text("자동재생 시작");
		$("#"+div_area+"_control a").click();
		$(".address").css("color","#000"); 
		$(".copyright").css("color","#000");
		// main_new_visual [END]		
		
	}

	heightChange();
	/* 정리 필요한 영역 */
	
	
	/* 최근소식 */
	$(".main_tab > li > a").click(function(){
		$(".main_tab > li > a").removeClass('on');
		$(".main_tab_cont:not('.main_tab_cont3')").hide();
		$(this).next().show();
		$(this).addClass('on');
	});		
});

/* .main_new_visual .popup_block [START]*/
var play_w; // WEB
var play_t; // TABLET
var play_m; // MOBILE
var play_p; // 팝업
var play_s; // 원스톱 핫라인 서비스, SOS 1379
var play_n; // 상단 팝업

rotateSwitch_s = function(){
	play_s = setInterval(function(){
		var total = $("#sa .png24").length;
		//alert(total);
		$("#sa > a").each(function(index){
			if($(this).hasClass("active")) {
				if(index+1 == total) {
					$("#sa > a").removeClass("active");
					$("#sa > a:first").addClass("active");
					$("#sa .visual-img").hide();
					$("#sa > a:first").next("div").show();
					return false;
				}

				$("#sa > a").eq(index).removeClass("active");
				$("#sa > a").eq(index+1).addClass("active");
				$("#sa .visual-img").hide();
				$("#sa > a").eq(index+1).next("div").show();
				return false;
			}
		});

	}, 7000);
};

rotateSwitch_p = function(){
	play_p = setInterval(function(){
		var total = $("#vs > a").length;
		$("#vs > a").each(function(index){
			if($(this).hasClass("active")) {
				if(index+1 == total) {
					$("#vs > a").removeClass("active");
					$("#vs > a:first").addClass("active");
					$("#vs .visual-img").hide();
					$("#vs > a:first").next("div").show();
					return false;
				}

				$("#vs > a").eq(index).removeClass("active");
				$("#vs > a").eq(index+1).addClass("active");
				$("#vs .visual-img").hide();
				$("#vs > a").eq(index+1).next("div").show();
				return false;
			}
		});

	}, 7000);
};

rotateSwitch_w = function(){
	play_w = setInterval(function(){
		var total = $("#w > a").length;
		$("#w > a").each(function(index){
			$(".address").css("color","#fff"); 
			$(".copyright").css("color","#fff");

			if($(this).hasClass("active")) {
				if(index+1 == total) {
					$("#w > a").removeClass("active");
					$("#w > a:first").addClass("active");
					$("#w .visual-img").hide();
					$("#w > a:first").next("div").show();

					$(".address").css("color","#000"); 
					$(".copyright").css("color","#000");

					return false;
				}

				$("#w > a").eq(index).removeClass("active");
				$("#w > a").eq(index+1).addClass("active");
				$("#w .visual-img").hide();
				$("#w > a").eq(index+1).next(" div").show();
				return false;

			}
		});

	}, 5000);
};

rotateSwitch_t = function(){
	play_t = setInterval(function(){
		var total = $("#t > a").length;
		$("#t > a").each(function(index){
			if($(this).hasClass("active")) {
				if(index+1 == total) {
					$("#t > a").removeClass("active");
					$("#t > a:first").addClass("active");
					$("#t .visual-img").hide();
					$("#t > a:first").next("div").show();
					return false;
				}

				$("#t > a").eq(index).removeClass("active");
				$("#t > a").eq(index+1).addClass("active");
				$("#t .visual-img").hide();
				$("#t > a").eq(index+1).next("div").show();
				return false;
			}
		});

	}, 5000);
};

rotateSwitch_m = function(){
	play_m = setInterval(function(){
		var total = $("#m > a").length;
		$("#m > a").each(function(index){
			if($(this).hasClass("active")) {
				if(index+1 == total) {
					$("#m > a").removeClass("active");
					$("#m > a:first").addClass("active");
					$("#m .visual-img").hide();
					$("#m > a:first").next("div").show();
					return false;
				}

				$("#m > a").eq(index).removeClass("active");
				$("#m > a").eq(index+1).addClass("active");
				$("#m .visual-img").hide();
				$("#m > a").eq(index+1).next("div").show();
				return false;
			}
		});

	}, 5000);
};

rotateSwitch_n = function(){
	play_n = setInterval(function(){
		var total = $("#ul_popNoticeList > li").length;
		$("#ul_popNoticeList > li").each(function(index){
			if($(this).is(":visible") == true) {
				if(index+1 == total) {
					$("#ul_popNoticeList > li").css("display", "none");
					$("#ul_popNoticeList > li").eq(0).css("display", "block");
					$(".slider-controller a").removeClass("active");
					$(".slider-controller a").blur();
					$(".slider-controller a").eq(0).addClass("active");
					return false;
				}

				$("#ul_popNoticeList > li").eq(index).css("display", "none");
				$(".slider-controller a").removeClass("active");
				$(".slider-controller a").blur();
				$("#ul_popNoticeList > li").eq(index+1).css("display", "block");
				$(".slider-controller a").eq(index+1).addClass("active");
				return false;
			}
		});

	}, 5000);
};

$(document).on("click", ".png24", function(){
	var div_area = $(this).attr('id').substr(0,1);
	$("#" + div_area + " .png24").removeClass("active");
	$(this).addClass("active");
	$("#" + div_area + " .visual-img").hide();
	$(this).next("div").show();
	
	if(div_area == 'w'){
		if($(this).attr('id') == 'w_1'){
			$(".address").css("color","#000"); 
			$(".footer .copyright").css("color","#000");
		} else {
			$(".address").css("color","#fff"); 
			$(".footer .copyright").css("color","#fff");
		}
	}
});

$(document).on("click", "[id$='_control'] a", function(){
	var div_area = $(this).parents().attr('id').substr(0,1);
	
	if($(this).attr("class").indexOf("icon-play") > -1) {
		switch(div_area){
			case 'w' : rotateSwitch_w(); break;
			case 't' : rotateSwitch_t(); break;
			case 'm' : rotateSwitch_m(); break;
			case 'p' : rotateSwitch_p(); break;
			case 's' : rotateSwitch_s(); break;
		}

		$(this).attr("class", "icon-pause");
		$(this).find("span").text("자동재생 중지");
	} else if($(this).attr("class").indexOf("icon-pause") > -1){
		
		switch(div_area){
			case 'w' : clearInterval(play_w); break;
			case 't' : clearInterval(play_t); break;
			case 'm' : clearInterval(play_m); break;
			case 'p' : clearInterval(play_p); break;
			case 's' : clearInterval(play_s); break;
		}
		
		$(this).attr("class", "icon-play");
		$(this).find("span").text("자동재생 시작");
	}
});

/* .main_new_visual .popup_block [END]*/

/* 상단 팝업 [START]*/
$(document).on("click", ".slider-controller a", function(){
	var item = $(this).find('span').text() - 1;
	$("#ul_popNoticeList > li").css("display", "none");
	$("#ul_popNoticeList > li").eq(item).css("display", "block");
	$(".slider-controller a").removeClass("active");
	$(".slider-controller a").eq(item).addClass("active");
});

$(document).on("click", "[class^='slider-btn']", function(){
	var mode = $(this).prop('class').replace("slider-btn-", "");
	var total = $("#ul_popNoticeList > li").length;
	var active = parseInt($(".inbox > a:visible").attr('data-page'));

	if(mode == 'prev') {
		if(active == 0){
			return false;
		}
		$("#ul_popNoticeList > li").css("display", "none");
		$("#ul_popNoticeList > li").eq(active-1).css("display", "block");
		$(".slider-controller a").removeClass("active");
		$(".slider-controller a").eq(active-1).addClass("active");
	} else {
		if(active+1 == total){
			return false;
		}
		$("#ul_popNoticeList > li").css("display", "none");
		$("#ul_popNoticeList > li").eq(active+1).css("display", "block");
		$("#ul_popNoticeList > li").eq(active).css("display", "none");
		$(".slider-controller a").removeClass("active");
		$(".slider-controller a").eq(active+1).addClass("active");
	}
});

$(document).on("click", "#spn_mainPopBannerClose", function(){
	if($("#pushbanner").is(":visible") == true) {
		//alert('1');
		$("#pushbanner").slideUp();
		$(this).find("> img").prop("src", $(this).find("> img").prop('src').replace("close","open"));
	} else {
		//alert('3');
		$("#pushbanner").slideDown();
		$(this).find("> img").prop("src", $(this).find("> img").prop('src').replace("open","close"));
		
		$(".slider-controller a:first").click();
	}
});

$(window).ready(function(){
	if(unescape(getCookie('top_popup')) == ""){
        $("#pushbanner").delay(800).slideDown(700);
		$("#spn_mainPopBannerClose").css("display", "block");
	} else {
		$("#spn_mainPopBannerClose").css("display", "block");
		$("#spn_mainPopBannerClose").find("> img").prop("src", $("#spn_mainPopBannerClose").find("> img").prop('src').replace("close","open"));
	}
	$(".copy").hover(
		function () {  $(".checkbox").addClass("focus");  }, 
		function () {  $(".checkbox").removeClass("focus");  }
	);
});

$(document).on("click", ".copy", function(){
	setCookie("top_popup", "done", 7);

	$("#pushbanner").css("display", "none");
	$("#spn_mainPopBannerClose").find("> img").prop("src", $("#spn_mainPopBannerClose").find("> img").prop('src').replace("close","open"));
	//$("#spn_mainPopBannerClose").css("display", "none");
});

function setCookie(name, value, expiredays){
	var todayDate = new Date(); 
 	todayDate.setDate( todayDate.getDate() + expiredays ); 
 	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"; 
}

function getCookie(name){    
	var wcname = name + '=';
	var wcstart, wcend, end;
	var i = 0;    

	while(i <= document.cookie.length) {            
		wcstart = i;  
	 	wcend   = (i + wcname.length);            
	 	if(document.cookie.substring(wcstart, wcend) == wcname) {                    
	  		if((end = document.cookie.indexOf(';', wcend)) == -1)                           
	   			end = document.cookie.length;                    
	  		return document.cookie.substring(wcend, end);            
	   	}            

	 	i = document.cookie.indexOf('', i) + 1;            
	  
	   	if(i == 0)
	  		break;    
	}

  	return '';
}
/* 상단 팝업 [START]*/

/* 마우스 이벤트 관련 */
function slideArrow(flag){
	var currentWidth = $(window).width();
	var currentHeight = $(window).height();
	//alert(flag);
	if(flag == 1 && currentWidth <= 1870){
		scrollFlag = 1;
		$(".slide_side_bar").show();
		$('#spn_mainPopBannerClose').css("right", "40px");
		//스크롤 했을경우 화살표 움직여준다
		 $(window).scroll(function()
		{
	            var position = $(window).scrollTop();
	            $(".slide_side_bar").stop().animate({"top":position+"px"},1000);
        });
		 
		$('.slide_side_bar').css("height",(currentHeight) + "px");
		$('.slide_left_right').css("height",(currentHeight) + "px");
		//$('.slide_left_left').css("height", (currentHeight / 2 ) + "px");
		
// 		$('.slide_side_bar').hover(function(){
			$('.slide_left_right').show();//우로가는 화살표 표시
		//	$('.slide_left_left').show();//우로가는 화살표 표시
// 		},function(){
// 			$('.slide_left_right').hide();//우로가는 화살표 숨김
// 			$('.slide_left_left').hide();//우로가는 화살표 숨김
// 		});
		
		$('.slide_left_right, .slide_left_left').hover(function(){
			$(this).css('background-color','#4b5357');
		}, function(){
			$(this).css('background-color','#31373a');
		})
		//좌우 화살표 클릭시 이동 
		$('.slide_left_right').on("click",function(){
			handle(-1);
		});

		$('.slide_left_left').on("click",function(){
			handle(1);
		});
	}
	else{
		scrollFlag = 0;
		$(".slide_side_bar").hide();
		$('#spn_mainPopBannerClose').css("right", "0px");
	}
}

var moveWidth = 0;
var containID = "#container";
function handle(delta){
	//var containID = "#container";
	//var s = delta + ": ";
	if(delta == 0) {
		$(containID).stop().animate({left: '200px'},{queue: false});
		alert('0');
	}
	
	var winWidth = $(window).width(); 
	if(winWidth < 1870){
		if(delta < 0 ){
			$(containID).stop().animate({left: leftChange(delta)},{queue: false});
		}
		else if(delta > 0){
			$(containID).stop().animate({left: leftChange(delta)},{queue: false});
		}
	}
}

function showSideDiv(leftDiv, rightDiv){
	var currentHeight = $(window).height();
// 	console.log("leftDiv:" + leftDiv);
// 	console.log("rightDiv:" + rightDiv);
	if(leftDiv && rightDiv){
		$('.slide_side_bar').css("height",(currentHeight) + "px");
		$('.slide_left_right').css("height",(currentHeight / 2) + "px");
		$('.slide_left_left').css("height", (currentHeight / 2 ) + "px");
		$('.slide_left_right').show();
		$('.slide_left_left').show();
	}
	else if(leftDiv){
		$('.slide_side_bar').css("height",(currentHeight) + "px");
		$('.slide_left_left').css("height", (currentHeight) + "px");
		$('.slide_left_left').show();
		$('.slide_left_right').hide();
	}
	else if(rightDiv){
		$('.slide_side_bar').css("height",(currentHeight) + "px");
		$('.slide_left_right').css("height", (currentHeight) + "px");
		$('.slide_left_left').hide();
		$('.slide_left_right').show();
	}
}

function leftChange(delta)
{
	if(delta >= 0){
		$position = $(containID).position().left + 200;
		//console.log("up : "+ delta + "position : " + $position);
		
		if($position > 200){
			showSideDiv(false, true);
			return '+=' + (200 - Math.abs($(containID).position().left)) + 'px';
		}
		else{
			showSideDiv(true, true);
			return '+=200px';
		}
	}
	else if(delta < 0){
		$position = $(containID).position().left - 200;
		$gap = $("#wrap").width() - $(containID).width();
		
		if($position < $gap){
			showSideDiv(true, false);
			return  '-=' + (Math.abs($gap - $(containID).position().left)) + 'px';
		}
		else
		{
			showSideDiv(true, true);
			return '-=200px';
		}
	}
		 
}

function wheel(event){
	var delta = 0;
    if(!event) event = window.event;
    
    if(event.delta){
        delta = event.delta/120;
        if (window.opera) delta = -delta;
    }
    else if(event.detail) delta = -event.detail/3;
    
    if (delta) 
    	handle(delta);
}

function heightChange()
{
	var winheight = $(window).height();
	var MINHEIGHT = 960;
	
	$("#header, #container,.scroll_wrap > div").css({height : function(){
		if(winheight > MINHEIGHT)
		{
			return winheight;	
		}
		else if(winheight <= MINHEIGHT)
		{
			return MINHEIGHT;
		}		
	}});
}
/* 마우스 이벤트 관련 */