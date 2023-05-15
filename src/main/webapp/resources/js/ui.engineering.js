$(function(){
	 
	$bodyWidth = $(window).width() + 17;
	$(window).resize(function(){
		$bodyWidth=$(window).width() + 17;
		//reset();
		popProject();
		//sitemap();
		//imgPop();
	});
	locatoin();
	mainPage(); 
	mainVisual(); //메인상단 비주얼 영역제어
	globalOffice(); //about us -> global offices 지도영역
	faqMotion(); // careers -> join us -> FAQ 슬라이딩 모션
	videoPop(); // 모든 공통 팝업 영역 제어 (Project Lists 팝업 제외)
	leadership(); // about us -> leadership  탭기능 제어
	majorPopup(); //Project Lists 팝업 영역
	majorPopSlide(); //Project Lists 팝업영역 안에 이미지 슬라이딩 기능
	majorProjects();
	growthTab();
	recruitTab();
	irMore(); //IR Archive 페이지에서 하단 영역 more버튼 클릭시 숨김페이지 노출
	Compliance(); //Sustainability 모든 컨텐츠 페이지 탭 메뉴 설정
	popProject();
	//meetOurPeople(); // meet our people 영상 wmode 제어
	videoWmode(); // iframe video wmode 
	//ComTop(); // 모든페이지 상단 top버튼 제어
	//linkMotion(); // media center -> Publications, video 탭 이동 스크립트
	sitemap(); //사이트맵 팝업
	imgPop(); // Quality Management 이미지 확대 레이어팝업
	legel();
	emailRefuse();
	personal();

	/* 
	// gloval office 메뉴 오류 
	if($('.globalLocation').size() != 0){
		menuLoad();
	}
	*/
});


function menuLoad(a,b,c){
	var $subBg = 200; //Math.abs(parseInt($('.subBg').css('left')));
	var $footer = Math.abs(parseInt($('#footer').css('left')));
	
	//alert('${active_menu}');
	
	//$('.subBg').addClass('on');
	//$('#container').css({'left':$subBg});
	
	/*
	if(a){
		if($bodyWidth >= 1281){
			$('#header').next().css({'left':$subBg});
			$('.location').css({'left':($subBg*2)});
			$('#gnb>ul>li').eq(a-1).addClass('on').find('.gnbSub>ul>li>h3').eq(b-1).addClass('on').next().show().find('li').eq(c-1).addClass('on');
			$('#header').next().delay(1000).animate({'left':'0px'},function(){;
				//$('#gnb>ul>li.on').removeClass('on');
			});
			$('.location').stop().delay(1000).animate({'left':($subBg)});
		};
	};
*/
	$('#gnb h2 a').click(function(ev){
		ev.preventDefault();		
		
		var $this = $(this);
		if($bodyWidth >= 1281){																		
			if($(this).parent().parent().hasClass('on')){ // submenu li - opend
				/*
				$('#header').next().stop().animate({'left':'0px'},function(){
					$this.parent().parent().removeClass('on').siblings().removeClass('on');
				});
				$('.location').stop().animate({'left':$subBg},function(){
					$('.subBg').removeClass('on');
				});
				*/
			}else{ // submenu li - closed -> Do open
				/*
				$('#header').next().stop().animate({'left':$subBg});
				$('.location').stop().animate({'left':($subBg*2)});
				$('.subBg').addClass('on');
				*/
				$this.parent().parent().addClass('on').siblings().removeClass('on');				
			}	
		}else{
			$(this).parent().next('div').slideDown().parent().siblings().children('div').slideUp();
			$this.parent().parent().addClass('on').siblings().removeClass('on');
		}
	});
	
//	$('.gnbSub h3 a').click(function(){
//		var $size = $(this).parent().next().size();
//		if($size != 0){
//			$(this).parent().toggleClass('on').next().slideToggle();
//			$('.gnbSub h3').not($(this).parent()).removeClass('on');
//			$('.gnbSub>ul>li>ul').not($(this).parent().next()).slideUp();
//		}else{
//			var $href = $(this).attr('href');
//			location.href = $href;
//		};
//		return false;
//	});
	
	
	$('.mtMenu>a').click(function(){
		if($(this).parent().hasClass('on')){
			$('#wrap').stop().animate({'left':'0px'});
		}else{
			$('#wrap').stop().animate({'left':'200px'});
		}
		$(this).parent().toggleClass('on');
	});

	/*
	var menu_timer;
	$('#header').hover(function(){
		// mouse on...
		alert("mouse on...");
		clearTimeout(menu_timer);
	},function(){
		// mouse off...
		alert("mouse off...");
		clearTimeout(menu_timer);
		menu_timer = setTimeout(MenuTime,1500);
	});
	*/

	function MenuTime(){
		$('#header').next().animate({'left':'0px'},function(){
			if($bodyWidth >= 1281){
				$('#gnb>ul>li.on').removeClass('on');
			}
		});
		if($('.location').css('left') == '460px'){
			$('.location').stop().animate({'left':($subBg)});
		}
	}

	$(window).scroll(function(){
		var $scrollLeft = $(window).scrollLeft();
		var $container  = Math.abs(parseInt($('.container').css('left')));
		if($bodyWidth >= 1281){	
			$('.subBg').css({'left' : $subBg - $scrollLeft});			
			if($container == 180 ){
				$('.location').css({'left' : ($subBg*2) - $scrollLeft});
			}			
			$('#footer').css({'left': $footer - $scrollLeft});
			$('#header').css({'left': -$scrollLeft});
		}	
	});
};

/* GNB reset*/
function reset(){
	$('#wrap').removeAttr('style');
	$('#header').next().removeAttr('style');
	$('.subBg').removeAttr('style');
	$('.gnbSub').removeAttr('style');
	$('.location').removeAttr('style');
	$('.gnbSub ul li ul').removeAttr('style');
	// $('#gnb li').removeClass('on');
	$('.mtMenu').removeClass('on');
	$('.gnbSub h3').removeClass('on');
};

/* locatoin */
function locatoin(){
	$('.location>.last>a, .location>.togglable>a').click(function(ev){
		ev.preventDefault();
		//if($('.location>.last>ul').css('display')=='block'){
		//if($(this).next('ul').css('display')=='block'){
		if($(this).next('ul').is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$('.location ul:visible').slideUp()
			$(this).next('ul').slideDown();
		}
	});
	$('.location').mouseleave(function(){
		$('.location ul:visible').slideUp()
		//if($('.location>.last>ul').css('display')=='block'){
		//	$('.location>.last>ul').slideUp();
		//}
	});
};

/* mainPageBox */
function mainPage(){
	var initProject = Math.floor(Math.random()*4 );
	$('.box2 .choice a').click(function(){
		$('.box2 li.on').removeClass('on');
		$(this).parents('li').addClass('on');
		return false;
	}).eq( initProject ).trigger('click');
};

function mainVisual(){
	
	var delay = 8000;
	var speed = 1000;
	var motion = 'easeInOutCirc';
	var check = true;
	var $index = Math.floor(Math.random()*4 );
	var prevBtn = $('.menu li > a').eq($index).parent();
	var $timer = setInterval(timerHandler,delay);
	var focus = false;
	
	$('.visual li').hide(); // Initial - $index pos item show
	$('.menu li').removeClass('on'); // Initial - menu active
	
	$(window).load(function(){
		var height = $('#visual .visual li:visible').height();
		$('#visual .visual').css('height',height);
	});
	$(window).resize(function(){
		height = $('#visual .visual li:visible').height();
		$('#visual .visual').css('height',height);
	});
	
	clickHandler(prevBtn);
	
	$('.slideNext').click(function(e){
		e.preventDefault();
		if(prevBtn.index()<$('.menu li').length-1)
		{
			clickHandler($('.menu li').eq(prevBtn.index()+1));
		}else{
			clickHandler($('.menu li').eq(0));
		}
	});
	
	$('.slidePrev').click(function(e){ 
		e.preventDefault();
		if(prevBtn.index()>0)
		{
			clickHandler($('.menu li').eq(prevBtn.index()-1));
		}else{
			clickHandler($('.menu li').eq($('.menu li').length-1));
		}
	});
	
	$('#visual').hover(function(){
		clearInterval($timer);
	},function(){
		if(!focus)
		{
			$timer = setInterval(timerHandler,delay);
		}
	});
	
	$('#visual a').focus(function(){
		clearInterval($timer);
	});

	/*
	$('#visual a').focusin(function(){
		clearInterval($timer);
		focus = true;
	});
	
	$('#visual a').focusout(function(){
		$timer = setInterval(timerHandler,delay);
		focus = false;
	});
	
	$('#visual .menu li > a').focusin(function(){
		$('#visual .menu li:eq('+$(this).parent().index()+')').addClass('on');
	});
	*/
	
	$('#visual .menu li > a').focusout(function(){		
		$('#visual .menu li').removeClass('on');
		$('#visual .menu li:eq('+prevBtn.index()+')').addClass('on');
	});
	
	/*
	$('#visual a:last, #visual a:first').blur(function(){
		$timer = setInterval(timerHandler,delay);
	});
	*/
	$('#visual .menu li > a').click(function(e){ 
		e.preventDefault();
		$(this).blur();
		clickHandler($(this).parent());
	});
	
	// Media OPEN
	$('#visual a.media-show').click(function(ev){
		ev.preventDefault()
		clearInterval($timer);
		$(this).parent().find('.media').show();// Media frm
	});
	
	// Media CLOSE
	$('#visual button.mediaClose').click(function(ev){
		ev.preventDefault();
		$(this).parent().hide(); // Media frm
		$timer = setInterval(timerHandler,delay);
	});
	
	$('.popOpen').click(function(){
		clearInterval($timer);
	});
	
	function clickHandler(tg) 
	{
		$('#visual .menu li').removeClass('on')
		if(prevBtn) {
			if(prevBtn.index()<tg.index())
			{
				$('.visual li').eq(tg.index()).css('left','100%');
				$('.visual li').eq(prevBtn.index()).stop(true,false).animate({'left':'-100%'},speed,motion);
			}else if(prevBtn.index()>tg.index()){
				$('.visual li').eq(tg.index()).css('left','-100%');
				$('.visual li').eq(prevBtn.index()).stop(true,false).animate({'left':'100%'},speed,motion);
			}

		}
		$('.visual li').eq(tg.index()).show().animate({'left':'0%'},speed,motion);

		$('#visual .menu li:eq('+tg.index()+')').addClass('on');
		prevBtn=tg;
	}
	
	function timerHandler()
	{
		if(prevBtn.index()<$('.menu li').length-1)
		{
			clickHandler($('.menu li').eq(prevBtn.index()+1));
		}else{
			clickHandler($('.menu li').eq(0));
		}
	}
}

/*
function mainVisual(){
	$(window).load(function(){
		var height = $('#visual .visual li:visible').height();
		$('#visual .visual').css('height',height);
	});
	$(window).resize(function(){
		height = $('#visual .visual li:visible').height();
		$('#visual .visual').css('height',height);
	 });

	var $index = Math.floor(Math.random()*4 );
	//var $index = 0;
	$('.visual li').hide().eq( $index ).show(); // Initial - $index pos item show
	$('.menu li').removeClass('on').eq( $index ).addClass('on'); // Initial - menu active
		

	$('.slideNext').click(function(ev){
		ev.preventDefault();
		plus($index,true);
	});
	
	$('.slidePrev').click(function(ev){
		ev.preventDefault();
		minus($index,true);
	});
	
	$('.menu li > a').click(function(ev){
		$('.menu li').removeClass('on');
		ev.preventDefault();		
		var toShow = $index;
		$idx_list = $(this).parent().index(); // li index		
		if(toShow != $idx_list){
			if(toShow < $idx_list){
				toShow = $idx_list;
				plus(toShow,false);
			}else{
				toShow = $idx_list;
				minus(toShow,false);
			};
		};
		return false;
	});
	
	var delay = 5000;
	var speed = 1000;
	var motion = 'easeInOutCirc';
	var check = true;
	
	var $timer = setInterval(function(){
		plus($index,true);
	},delay);
	
	$('#visual').hover(function(){
		clearInterval($timer);
	},function(){
		clearInterval($timer);
		if(check == true){ 
			$timer = setInterval(function(){
				plus($index,true);
			},delay);
		};
	});
	
	$('#visual a').focus(function(){
		clearInterval($timer);
	});
	
	$('#visual a:last').blur(function(){
		$timer = setInterval(function(){
			plus($index,true);
		},delay);
	});
	
	// Media OPEN
	$('#visual a.media-show').click(function(ev){
		ev.preventDefault()
		clearInterval($timer);
		$(this).parent().find('.media').show();// Media frm
		check = false;
	});
	
	// Media CLOSE
	$('#visual button.mediaClose').click(function(ev){
		ev.preventDefault();
		$(this).parent().hide(); // Media frm
		$timer = setInterval(function(){
			plus($index,true);
		},delay);
		check = true;
		return false;
	});
	
	$('.popOpen').click(function(){
		clearInterval($timer);
		check = false;
	});
	
	function plus(a,b){
		$('.visual li').eq($index).stop(true,false).animate({'left':'-100%'},speed,motion);
		if(b == true){
			a+1 == $('.visual li').size() ? a = 0 : a++;
		}
		$('.visual li').eq(a).show().css('left','100%').animate({'left':'0%'},speed,motion);
		$('.menu li').removeClass('on');
		$('.menu li').eq(a).addClass('on');
		$index = a;
	};
	function minus(a,b){
		$('.visual li').eq($index).stop(true,false).animate({'left':'100%'},speed,motion);
		if(b == true){
			a-1 < 0? a = $('.visual li').size()-1 : a--;
		}
		$('.visual li').eq(a).show().css('left','-100%').animate({'left':'0%'},speed,motion);
		$('.menu li').removeClass('on');
		$('.menu li').eq(a).addClass('on');
		//$('.menu li').eq(a).addClass('on').siblings().removeClass('on');
		$index = a;
	};
};
*/

function globalOffice(){
	if($('.globalLocation').size() != 0){
		
		$('.globalLocation .btn a').click(function(){			
			var putLink = $(this).parent();
			var viewLayer = $(this).parents('.text').next();			
			
			if (viewLayer.css('display') == 'none') {
				$(viewLayer).css({'display' : 'block'});
				$(putLink).addClass('on');
			} else {
				$(viewLayer).css({'display' : 'none'});
				$(putLink).removeClass('on');
			}
			return false;
		});

		/*
		$('.globalLocation .btn a').click(function(){
			$(this).parent().toggleClass('on').parent().next().slideToggle('fast');
			$('.globalLocation .btn').parent().next().not($(this).parent().parent().next()).slideUp('fast');
			return false;
		});
		
		$('.detail p a').click(function(){
			var _idx = $(this).index();
			$(this).parent().siblings().slideUp().eq(_idx).slideDown();
			return false;
		});
		*/
		
		var _index = 1;
		$('.globalLocation').hide().eq(0).show();
		$('.map .link li').click(function(){
			var clickIndex = $(this).index() + 1;
			$(this).addClass('on').siblings().removeClass('on');
			//$('img.map').attr('src',$('img.map').attr('src').replace(_index + '.jpg', clickIndex + '.jpg'));
			$('.globalLocation').hide().eq(clickIndex -1).show();
			_index = clickIndex;
			return false;
		});
		/*
		$('.map .link li').hover(function(){
			$(this).find('a').stop().animate({ marginTop : '8px'});
		},function(){
			$(this).find('a').animate({ marginTop : '0px'});
			
		});
		*/
		
		$('.subMenu li').click(function(){
			var $txt = $(this).text();
			if($txt.indexOf(' ') != -1){
				$txt = $txt.substr($txt.indexOf(' '));
			}
			if($txt == 'All'){
				$(this).parents('.globalLocation').find('.box').show();
			}else{
				$(this).parents('.globalLocation').find('.box').hide().each(function(){
					$(this).find('p[title*=' + $txt + ']').parents('.box').show();
				});
			}
			$(this).addClass('on').siblings().removeClass('on');
			return false;
		});
	};
};

/*faq*/
function faqMotion(){	
	$(".faq>dl>dt>a").click(function(){
		$('#base_seq').val($(this).parent('dt').next("dd").find('input').val());
		var frm = $('#frm');
		var options = 
		{
			url : "updateReadCntAjaxProc",
			type : "post",
			dataType : "text",
			success : function(data){
				
			}
		};
		frm.ajaxSubmit(options);
		
		$(this).parent('dt').toggleClass('on').siblings().removeClass('on');
		$(this).parent('dt').next("dd").slideToggle('fast').siblings("dd").slideUp('fast');       	
		return false;
	});
}

/*video - popup*/
function videoPop(){
	var $popupWidth = 0;
	var $popupHeight = 0;
	var $document = 0;
	var x, y;
	
	$(".popOpen").click(function(e){ 
		if($('.youtube').size() != 0){
			$(this).after('<div class="videoPop layerPopup">' +
			'<h5></h5>' +
			'<p id="youtube"></p>' + 
			'<div class="videoCaption" tabindex="0"><div class="videoCaptionInner"></div></div>'+
			'<p class="comment"></p>' + 
			'<a href="#" class="popClose"><img src="/static/images/common/btn/btnPopClose.gif" alt="popup close" /></a>').next().show();
			$(this).after("<div class='popBg'></div>");
			youtube($(this).siblings('.youtube'));
		}else if($('.googleMapText').size() != 0){			
			$(this).after('<div class="mapPop  layerPopup">' +
			'<p class="kind"><span></span></p>' +
			'<h5></h5>' +
			'<p></p>' + 
			'<ul class="contact"></ul>'+
			'<div class="siteCon"></div>' +
			'<div class="googleMap"></div>'+
			'<a href="#" class="popClose"><img src="/static/images/common/btn/btnPopClose.gif" alt="popup close" /></a>').next().show();
			$(this).after("<div class='popBg'></div>");
			googleMap($(this).siblings('.googleMapText'));
		}else{
			$(this).after("<div class='popBg'></div>");
			$(this).siblings('div').show();
		}
		if($('#visual').size() != 0){
			$('#youtube').css('height','90%');
		}else{
			popupMotion(e);
			popupCenter();
		}
		return false;
	});
	
	$(document).on('click','.popClose',function(){
		$('.popBg').detach();
		$(this).parents('.layerPopup').prev().focus();
		$('.layerPopup').animate({'left':x,'top':y,'width':'0px','height':'0px','margin-left':'0px','margin-top':'0px'},function(){
			if($('.youtube').size() != 0 || $('.googleMapText').size() != 0){
				$(this).remove();
			}else{
				$(this).hide();			
			};
		});
		return false;
	});
	
	function popupCenter(){
		//$popupHeight = $('.layerPopup').outerHeight();
		$document = $(document).height();
		if($popupHeight > $document){
			$('.layerPopup').animate({'top':'0px','left':'0px','margin-left':'0px','height':$popupHeight});
		}else{
			$('.layerPopup').animate({'top':'50%','left':'50%','margin-left':-($popupWidth/2) - 25 ,'margin-top':-($popupHeight/2),'width':$popupWidth,'height':$popupHeight});
		};
	};
	
	function popupMotion(e){
		$popupWidth = $('.layerPopup').width();
		$popupHeight = $('.layerPopup').height();
		x = e.clientX;
		y = e.clientY;
		$('.layerPopup').css({'left':x,'top':y,'width':'0px','height':'0px','margin-left':'0px'});
	};
}

function googleMap(link){
	
	var $pKind = link.find('p.kind').html();
	var $h5 = link.find('h5').text();
	var $p = link.find('h5').next().text();
	var $iframe = link.find('textarea').val();
	var $ul = link.find('ul.contact').html();
	var $psite = link.find('div.siteCon').html();

	$('.googleMap').html($iframe);
	$('.mapPop h5').text($h5);
	$('.mapPop p.kind').html($pKind);
	$('.mapPop h5').next().text($p);
	$('.mapPop ul.contact').html($ul);
	$('.mapPop div.siteCon').html($psite);
}

function youtube(link){
	var $h5 = link.find('h5').text();
	var $file = link.find('.file').text();
	var $text = link.find('.text').html();
	var $text2 = link.find('.comment').text();
	
	$('#youtube').append($file);
	$('.videoPop h5').text($h5);
	$('.videoCaption .videoCaptionInner').html($text);
	$('.videoPop .comment').text($text2);

	$('#base_seq').val(link.find('input').val());
	var frm = $('#frm');
	var options = 
	{
		url : "updateReadCntAjaxProc",
		type : "post",
		dataType : "text",
		success : function(data){
			console.log( data )
			
		}
	};
	frm.ajaxSubmit(options);
}

/* Leadership */
function leadership(){
	if($('.Leadership').size() != 0){
		$('.Leadership .con').hide();
		$('.Leadership .con').eq(0).show();
		
		$('.Leadership .tab li a').click(function(){
			$('.Leadership .tab li').removeClass('on');
			$(this).parent().addClass('on');
			var $index = $(this).parent().index();
			$('.Leadership .con').hide();
			$('.Leadership .con').eq($index).show();
		});
	}
};

function majorPopup(){
	var $majorPopHeight = 0;
	var $WinHeight = 0, $majorPopWidth = 0;
	
	if($('.majorProject').size() != 0){
		$('.unit').on('click','td>a',function(ev){
			ev.preventDefault();
			
			//var $memory = '';
			
			$('#base_seq').val($(this).find('input').val());
			var frm = $('#frm');
			var options = 
			{
				url : "updateReadCntAjaxProc",
				type : "post",
				dataType : "text",
				success : function(data){
					
				}
			};
			frm.ajaxSubmit(options);
			$(this).after("<div class='popBg'></div>");
			$(this).siblings('.layerPopup').show();
			$memory = $(this).siblings('.layerPopup');
			majorPopSize(ev,$memory);
			
			$(".popClose").click(function(){
				$('.popBg').detach();
				$(this).parents('.layerPopup').prev().focus();
				/*
				$(this).parents('.layerPopup').stop(true, true).animate({'left':_x,'top':_y,'width':'0','height':'0','margin-left':'0px','margin-top':'0px'},function(){
					//$(this).hide().css({'width':'auto','height':'auto'});
				});
				*/
				$(this).parents('.layerPopup').animate({'left':_x,'top':_y,'width':'0','height':'0','margin-left':'0px','margin-top':'0px'},function(){
					$(this).hide().stop(true, true).css({'width':'327px','height':'auto'});
				});				
				return false;
			});			
		});
		$(window).resize(function(){
			if( typeof $memory != 'undefined' && $memory != '' )
			{
				$WinHeight = $(window).outerHeight();
				$majorPopHeight = $memory.outerHeight();
				if($WinHeight < $majorPopHeight){
					$memory.css({'height':$WinHeight-20,'top':0,'margin-top':'0px'});
				}
			}			
		});
	};
	function majorPopSize(e,memory){
		var $WinHeight = $(window).outerHeight();
		var $majorPopWidth = memory.outerWidth();
		var $majorPopHeight = memory.outerHeight();

		_x = e.clientX;
		_y = e.clientY;
		memory.css({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px'});
		if($WinHeight > $majorPopHeight){
			memory.css({'overflow-y':'auto'}).stop(true, true).animate({'top':'50%','left':'50%','width':$majorPopWidth, 'height':$majorPopHeight,'margin-top':- ($majorPopHeight/2),'margin-left':- ($majorPopWidth/2)});
		}else{
			memory.css({'overflow-y':'auto'}).stop(true, true).animate({'top':'0%','left':'50%','width':$majorPopWidth, 'height':$WinHeight,'margin-top':'0px','margin-left':- ($majorPopWidth/2)});
		}
		
	}
};

function majorProjects(){
	if($('.ProjectStory').size() != 0){
		$('.listMenu a').click(function(){
			var listIndex = $(this).index();
			$(this).addClass('on').siblings().removeClass('on');
			if(listIndex == 0){
			$('div.gallery').show().next().hide();
			$('#pageType').val('gallery');
		}else{
			$('div.list').show().prev().hide();
			$('#pageType').val('list');
		};
		return false;
		});
	};
};
	
function majorPopSlide(){
	var aa = $('.majorProject .layerPopup').size();
	/*if($('.majorProject .layerPopup').size() != 0){
		var li_length = $('.layerPopup .img ul li').length;
		console.log(li_length);
	};*/
}

function growthTab(){
	if($('.growth').size() != 0){
		$('.growth .con').eq(0).show();
		$('.growth .tab li').eq(0).addClass('on');
		
		$('.growth .tab li').click(function(){
			var $index = $(this).index();
			$('.growth .con').hide().eq($index).show();
			$(this).addClass('on').siblings().removeClass('on');
			return false;
		});
	}
}

function recruitTab(){
	if($('.recruit').size() != 0){
		$('.recruit .con').eq(0).show().siblings('.con').hide();
		$('.recruit .tab li').eq(0).addClass('on');
		
		$('.recruit .tab li').click(function(){
			var $index = $(this).index();
			$('.recruit .con').hide().eq($index).show();
			$(this).addClass('on').siblings().removeClass('on');
			return false;
		});
	}
}

function irMore(){
	if($('.IRArchive').size() != 0){
		var $IRidx = 1;
		$('.box').hide();
		$('.box:lt('+ ($IRidx+2) +')').show();
		$('.btnMore a').click(function(){
			$IRidx+=3;
			$('.box:lt('+ ($IRidx+2) +')').slideDown();
			if($('.box').length-1 <= $IRidx ) {
				$('.more').hide();
				
			}
			
			return false;
		});
	}
}

function Compliance(){
	if($('.compliance, .quality').size() != 0 ){
		$('div.tabLayout>div').hide();
		$('.tabLayout .tab li > a').click(function(ev){
			ev.preventDefault();
			$(this).focus();
			var _val = $(this).attr('href');
			$(this).parent().toggleClass('on').siblings().removeClass('on');
			$(this).parents('.tabLayout').find('div.' + _val).slideToggle('fast').siblings('div').hide();			
		});
		$('.tabLayout').on('click', '.close a, .innerClose a, .closeBtn a', function(ev){
			ev.preventDefault();
			var _val = $(this).attr('href');
			$(this).parents('.tabLayout').find('.tab li').removeClass('on');
			//$(this).parents('.tabLayout').find('div.' + _val).slideToggle('fast').siblings('div').hide();
			$(this).parents('.tabLayout > div:visible').slideToggle('fast').siblings('div').hide();
		});
		/*
		$(document).on('click','.compliance .close a, .quality .close a, .closeBtn a, .innerClose a', function(ev){
			ev.preventDefault();
			$(this).parent().parent().parent().slideToggle('fast');
			$(this).parents('.tabLayout').find('li.on').removeClass('on');
		});
		*/
		
		if($('.galleryList').size() != 0){
			$('.tabBox1').galleryList3();
			$('.tabBox2').galleryList3();
		}
	};
};

function popProject(){
	if ($('.majorProject').size() != 0){
		var $popWidth = $('.layerPopup').outerWidth()/2 ;
		$('.majorProject .layerPopup').css('margin-left',-$popWidth);	
	}
};

function videoWmode() {
	if($('.MOP').size() != 0 || $('.video').size() > 0 ){
		$('iframe').each(function(){
			var src = $(this).attr('src');
			if( src.indexOf('?') == -1 )
				$(this).attr('src', src + '?wmode=transparent');
			else
				$(this).attr('src', src + '&wmode=transparent');
		});
	};
};

function ComTop(){
	var $winWidth = $(window).outerWidth(); 
	$(document).scroll(function(){
		var $scrollTop = $(document).scrollTop();
		if($scrollTop > 100){
			$('.ComTop').fadeIn();
		}else{
			$('.ComTop').fadeOut();
		}
	});
	$('.ComTop a').smoothScroll({
		speed: 500,
		easing: 'easeInOutCubic'
	});
	ComTopLeft();
	$(window).resize(function(){
		ComTopLeft();
	});
	
	function ComTopLeft(){
		 $winWidth = $(window).outerWidth(); 
		 $('.ComTop').css('left',$winWidth <= 1424 ? $winWidth-48 : 1424);
	};
};

function linkMotion(){
	$('.video .line a, .publication>ul a').smoothScroll({
		speed: 500,
		offset: -50,
		easing: 'easeInOutCubic'
	});
};

function sitemap(){	
	var windowHeight = 0, popWidth = 0, popHeight = 0;
	var $layerPop = '';
	
	$('body').on('click', '.btnSite', function(ev){   

		ev.preventDefault();
		$layerPop = $(this).parents("#footers").children('.sitemap');
		if( $layerPop.is(':hidden') )
		{
			$layerPop.show();
			$('#footers .sitemap').prev().after("<div class='sitemapBg'></div>");
			$('.sitemapBg').click(function(){
				close( $layerPop );
			});
			
			if( $('.sitemapBg').length == 1 ) {
				popSize(ev,$layerPop);
				$('.sitemapBg').show();
			}
		}		
	});
	
	$('body').on('click', '.sitemap .close', function(ev){
		ev.preventDefault();		
		close( $layerPop );		
	});
	function close(obj)
	{
		$layerPop = obj;
		$('.sitemapBg').hide().detach();
		$layerPop.prev().focus();
		$layerPop.stop(true,true).animate({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px','margin-top':'0px'},function(){
			$(this).hide().css({'width':'auto','height':'auto'});
		});
	}
	
	$(window).resize(function(){
		if( $layerPop != '' ) {
			windowHeight = $(window).height();			
			popHeight = $layerPop.height();
			if(windowHeight < popHeight){
				$layerPop.css({'height':windowHeight-20,'top':0,'margin-top':'0px'});
			}
		}		
	});
	function popSize(e,memory){
		windowHeight = $(window).height();
		popWidth = memory.width();
		log( memory.width() );
		popHeight = memory.height();
		_x = e.clientX;
		_y = e.clientY;
		memory.css({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px'});
		if(windowHeight > popHeight){
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'50%','left':'50%','width':popWidth, 'height':popHeight,'margin-top':- (popHeight/2),'margin-left':- (popWidth/2)});
		}else{
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'0%','left':'50%','width':popWidth, 'height':windowHeight,'margin-top':'0px','margin-left':- (popWidth/2)});
		}
	}
}

function legel(){	
	var windowHeight = 0, popWidth = 0, popHeight = 0;
	var $layerPop = '';
	
	$('body').on('click', '.btnNotice', function(ev){   

		ev.preventDefault();
		$layerPop = $(this).parents("#footers").children('.legel');
		if( $layerPop.is(':hidden') )
		{
			$layerPop.show();
			$('#footers .legel').prev().after("<div class='legelBg'></div>");
			$('.legelBg').click(function(){
				close( $layerPop );
			});
			
			if( $('.legelBg').length == 1 ) {
				popSize(ev,$layerPop);
				$('.legelBg').show();
			}
		}		
	});
	
	$('body').on('click', '.legel .close', function(ev){
		ev.preventDefault();		
		close( $layerPop );		
	});
	function close(obj)
	{
		$layerPop = obj;
		$('.legelBg').hide().detach();
		$layerPop.prev().focus();
		$layerPop.stop(true,true).animate({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px','margin-top':'0px'},function(){
			$(this).hide().css({'width':'auto','height':'auto'});
		});
	}
	
	$(window).resize(function(){
		if( $layerPop != '' ) {
			windowHeight = $(window).height();			
			popHeight = $layerPop.height();
			if(windowHeight < popHeight){
				$layerPop.css({'height':windowHeight-20,'top':0,'margin-top':'0px'});
			}
		}		
	});
	function popSize(e,memory){
		windowHeight = $(window).height();
		popWidth = memory.width();
		log( memory.width() );
		popHeight = memory.height();
		_x = e.clientX;
		_y = e.clientY;
		memory.css({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px'});
		if(windowHeight > popHeight){
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'50%','left':'50%','width':popWidth, 'height':popHeight,'margin-top':- (popHeight/2),'margin-left':- (popWidth/2)});
		}else{
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'0%','left':'50%','width':popWidth, 'height':windowHeight,'margin-top':'0px','margin-left':- (popWidth/2)});
		}
	}
}

function emailRefuse(){	
	var windowHeight = 0, popWidth = 0, popHeight = 0;
	var $layerPop = '';
	
	$('body').on('click', '.btnemailRefuse', function(ev){   

		ev.preventDefault();
		$layerPop = $(this).parents("#footers").children('.emailRefuse');
		if( $layerPop.is(':hidden') )
		{
			$layerPop.show();
			$('#footers .emailRefuse').prev().after("<div class='emailRefuseBg'></div>");
			$('.emailRefuseBg').click(function(){
				close( $layerPop );
			});
			
			if( $('.emailRefuseBg').length == 1 ) {
				popSize(ev,$layerPop);
				$('.emailRefuseBg').show();
			}
		}		
	});
	
	$('body').on('click', '.emailRefuse .close', function(ev){
		ev.preventDefault();		
		close( $layerPop );		
	});
	function close(obj)
	{
		$layerPop = obj;
		$('.emailRefuseBg').hide().detach();
		$layerPop.prev().focus();
		$layerPop.stop(true,true).animate({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px','margin-top':'0px'},function(){
			$(this).hide().css({'width':'auto','height':'auto'});
		});
	}
	
	$(window).resize(function(){
		if( $layerPop != '' ) {
			windowHeight = $(window).height();			
			popHeight = $layerPop.height();
			if(windowHeight < popHeight){
				$layerPop.css({'height':windowHeight-20,'top':0,'margin-top':'0px'});
			}
		}		
	});
	function popSize(e,memory){
		windowHeight = $(window).height();
		popWidth = memory.width();
		log( memory.width() );
		popHeight = memory.height();
		_x = e.clientX;
		_y = e.clientY;
		memory.css({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px'});
		if(windowHeight > popHeight){
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'50%','left':'50%','width':popWidth, 'height':popHeight,'margin-top':- (popHeight/2),'margin-left':- (popWidth/2)});
		}else{
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'0%','left':'50%','width':popWidth, 'height':windowHeight,'margin-top':'0px','margin-left':- (popWidth/2)});
		}
	}
}

function personal(){	
	var windowHeight = 0, popWidth = 0, popHeight = 0;
	var $layerPop = '';
	
	$('body').on('click', '.btnpersonal', function(ev){   

		ev.preventDefault();
		$layerPop = $(this).parents("#footers").children('.personal');
		if( $layerPop.is(':hidden') )
		{
			$layerPop.show();
			$('#footers .personal').prev().after("<div class='personalBg'></div>");
			$('.personalBg').click(function(){
				close( $layerPop );
			});
			
			if( $('.personalBg').length == 1 ) {
				popSize(ev,$layerPop);
				$('.personalBg').show();
			}
		}		
	});
	
	$('body').on('click', '.personal .close', function(ev){
		ev.preventDefault();		
		close( $layerPop );		
	});
	function close(obj)
	{
		$layerPop = obj;
		$('.personalBg').hide().detach();
		$layerPop.prev().focus();
		$layerPop.stop(true,true).animate({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px','margin-top':'0px'},function(){
			$(this).hide().css({'width':'auto','height':'auto'});
		});
	}
	
	$(window).resize(function(){
		if( $layerPop != '' ) {
			windowHeight = $(window).height();			
			popHeight = $layerPop.height();
			if(windowHeight < popHeight){
				$layerPop.css({'height':windowHeight-20,'top':0,'margin-top':'0px'});
			}
		}		
	});
	function popSize(e,memory){
		windowHeight = $(window).height();
		popWidth = memory.width();
		log( memory.width() );
		popHeight = memory.height();
		_x = e.clientX;
		_y = e.clientY;
		memory.css({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px'});
		if(windowHeight > popHeight){
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'50%','left':'50%','width':popWidth, 'height':popHeight,'margin-top':- (popHeight/2),'margin-left':- (popWidth/2)});
		}else{
			memory.css({'overflow-y':'auto'}).stop(true,true).animate({'top':'0%','left':'50%','width':popWidth, 'height':windowHeight,'margin-top':'0px','margin-left':- (popWidth/2)});
		}
	}
}

/*
function sitemap(){
	if ($('.sitemap').size() != 0){
		var $mapWidth = $('.sitemap').outerWidth()/2 ;
		var $mapHeight = $('.sitemap').outerHeight()/2 ;
		$('.sitemap').css({'margin-left':-$mapWidth,'margin-top':-$mapHeight});			
		$('.btnSite').click(function(ev){
			ev.preventDefault();
			$('.sitemapBg').show();
			$('.sitemap').show();
			$('#sitemapAnchor').focus();
		});	
		$('.close').click(function(ev){
			ev.preventDefault();
			$('.sitemapBg').hide();
			$('.sitemap').hide();
			$('.btnSite').focus();
		});
	}
}
*/

function imgPop(){	
	var windowHeight = 0, popWidth = 0, popHeight = 0;
	var $layerPop = '';
	
	$('body').on('click', '.smallImg', function(ev){
		ev.preventDefault();
		$layerPop = $(this).parent().siblings('.layerPop');
		if( $layerPop.is(':hidden') )
		{
			$layerPop.show();
			$(this).parent().after("<div class='popBg'></div>");
			$('.popBg').click(function(){
				close( $layerPop );
			});
			
			if( $('.popBg').length == 1 ) {
				popSize(ev,$layerPop);
				$('.popBg').show();
			}
		}		
	});
	
	$('body').on('click', '.layerPop .close', function(ev){
		ev.preventDefault();		
		close( $layerPop );		
	});
	function close(obj)
	{
		$layerPop = obj;
		$('.popBg').hide().detach();
		$layerPop.prev().focus();
		$layerPop.stop(true,true).animate({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px','margin-top':'0px'},function(){
			$(this).hide().css({'width':'auto','height':'auto'});
		});
	}
	
	$(window).resize(function(){
		if( $layerPop != '' ) {
			windowHeight = $(window).height();			
			popHeight = $layerPop.height();
			if(windowHeight < popHeight){
				$layerPop.css({'height':windowHeight-20,'top':0,'margin-top':'0px'});
			}
		}		
	});
	function popSize(e,memory){
		windowHeight = $(window).height();
		popWidth = memory.width();
		log( memory.width() );
		popHeight = memory.height();
		_x = e.clientX;
		_y = e.clientY;
		memory.css({'left':_x,'top':_y,'width':'0px','height':'0px','margin-left':'0px'});
		if(windowHeight > popHeight){
			memory/*.css({'overflow-y':'auto'})*/.stop(true,true).animate({'top':'50%','left':'50%','width':popWidth, 'height':popHeight,'margin-top':- (popHeight/2),'margin-left':- (popWidth/2)});
		}else{
			memory/*.css({'overflow-y':'auto'})*/.stop(true,true).animate({'top':'0%','left':'50%','width':popWidth, 'height':windowHeight,'margin-top':'0px','margin-left':- (popWidth/2)});
		}
	}
}

$(function(){
	$(".infoTooltip").hide();
	var viewLayer = $(".infoTooltip");
	
	$(".info li a").click(function(){ 
		$(viewLayer).show();
	});
	$(".infoTooltip .close").click(function(){ 
		$(viewLayer).hide();
	});	


	$(".compliance .video iframe").css({"width" : "478px", "height" : "301px"});
	$(".detailMovie iframe").css({"width" : "478px", "height" : "301px"});
	
	var MOPText = $(".MOP .view .info p.title").text();
	$(".MOP .view .movie > iframe").attr('title',  MOPText + ' 동영상');
	
	
	$(".galleryLeft div").hide(); 
	$(".galleryLeft div:first").show(); 
	
	$(".galleryRight a").click(function() {
		var activeLink = $(this).attr("href"); 
		$(".galleryRight a").removeClass("active"); 
		$(this).addClass("active");				
		$(this).parents('.activitiesGallery').find('.galleryLeft div.' + activeLink).show().siblings('div').hide();
		return false;
	});
	
	/* SKIP NAVIGATION */
	// Skipnav Anchor focus	
	$('.skip-navigation a').focus(function(){
		$('body').addClass('skip');
	}).blur(function(){
		removeSkipState()	
	}).click(function(ev){
		var target = $($(this).attr('href'));
		target.attr('tabindex', '0').focus();
	});

	function removeSkipState()
	{
		setTimeout(function(){			
			var $focused = $(':focus');
			if( !$('.skip-navigation a').is(':focus') ) {
				$('body').removeClass('skip');
			}
		},10);	
	}



	/* MAIN YOUTUBE TITLE */
	if( $('#visual').length > 0 ) 
	{	
		var videoTitle = $('#visual .mediaScript h2').text();
		$('.mediaSource > iframe').attr('title', videoTitle );
	}	


	/* 
	* EVENT BANNER
	*/	
	var newsList = function()
	{
		var $event = $('#eventBanner ');
		var lengths = $event.find('li').length;		
		var $indicator = $event.find('.indicator');
		
		if(lengths <= 1){
			$indicator.css("display","none");	
		}else{
			$indicator.css("display","block");	
		}		
		
		for( var i=0; i < lengths; i++ )
		{
			$('<button />').text(i+1).appendTo($indicator)			
		} 
		$indicator.find('button:first-child').addClass('active');
		$indicator.find('button').click(function(ev){
			ev.preventDefault();
			$(this).addClass('active').siblings().removeClass('active')
			seeMore( parseInt( $(this).text() -1 ) );
		});

		function seeMore(idx) {
			//log( idx + ' / ' + lengths );
			$event.find('li').removeClass('active').eq(idx).addClass('active')
		}
	}.call(this);
});

/* Log */
window.log = function(){
  log.history = log.history || [];
  log.history.push(arguments);
  arguments.callee = arguments.callee.caller;  
  if(this.console) console.log( Array.prototype.slice.call(arguments) );
};
(function(b){function c(){}for(var d="assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,markTimeline,profile,profileEnd,time,timeEnd,trace,warn".split(","),a;a=d.pop();)b[a]=b[a]||c})(window.console=window.console||{});










