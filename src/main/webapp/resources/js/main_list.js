try{
	document.createElement("header");						document.createElement("section");
	document.createElement("article");							document.createElement("aside");
	document.createElement("footer");							document.createElement("nav");
	document.execCommand('BackgroundImageCache',false,true);
}catch(e){}

var creamUtil = {
	trace : function(msg,type){ //creamUtil.trace(1);
		if($("#trace").length==0) $("body").append("<div id='trace' style='position:fixed; right:0; bottom:17px; padding:3px; width:90%; background:#fff; border:1px solid red;z-index:100;'><a href='#del' style='position:absolute;right:3px;top:3px;' onclick='$(this).next().html(\"\"); return false;'>del</a><div class='inner'></div></div>");
		$box = $("#trace .inner");
		msg = (type) ? $box.html()+msg : msg;
		$box.html(msg);
	},
	inputDesign : function(){
		$('input[type="radio"], input[type="checkbox"], input[type="file"]').each(function(i){
			if(this.type=="file"){
				this.baseW = this.offsetWidth;
				$(this).wrap('<span class="fileForm" />');
				$(this).parent().prepend('<input type="text" id="designFileInput'+i+'" class="text" readonly="readonly" title="첨부파일 결과값 출력" value="'+($(this).attr("placeholder")?$(this).attr("placeholder"):"")+'" style="width:'+(this.baseW-84)+'px;text-shadow:0;" />');
				$("#designFileInput"+i)[0].matchFile = this;
				this.matchInput = $("#designFileInput"+i).attr({"readonly":"true"}); //,"disabled":"true"    .click(function(){ this.matchFile.click(); }); --> 개발 붙이면 충돌일어나서 삭제
				if(this.placeholder) this.matchInput[0].value = this.placeholder;
				this.onchange = function(){ this.matchInput[0].value = this.value; }
			}else{
				var cn = (this.type =="radio") ? "radio" : "check";
				$(this).wrap('<span class="'+cn+'Design" />').focus(function(){ $(this).parent().addClass(cn+"Focus") }).blur(function(){ $(this).parent().removeClass(cn+"Focus") }).change(creamUtil.inputChange);
				creamUtil.inputChange.call(this);
			}
		});
	},
	inputChange : function(){
		var cn = (this.type =="radio") ? "radio" : "check";
		if(cn=="radio") $('input[name="'+this.name+'"]').parent().removeClass(cn+"Checked");
		if(this.checked) $(this).parent().addClass(cn+"Checked");
		else $(this).parent().removeClass(cn+"Checked");
	},
	resizeObj : {
		$obj : null,
			set : function(){ this.$obj.each(function(){ this.isunBind(); }); }
	},
	slider : function(v){
		if(!v) return;
		if(!v.$obj) return;
		if(!v.childName) v.childName = "li"; // 롤링 개체
		if(!v.h) v.w = v.$obj.width(); // 보여지는 가로 사이즈
		if(!v.h) v.h = v.$obj.height(); // 보여지는 세로 사이즈
		if(!v.view) v.view = 1; // 보여지는 개수
		if(!v.dir) v.dir = "left"; // 슬라이딩 방향 : left | top
		if(!v.itemW) v.itemW = (v.dir=="left") ? v.w/v.view : v.w; //아이템 가로 사이즈
		if(!v.itemH) v.itemH = (v.dir=="top") ? v.h/v.view : v.h; //아이템 세로 사이즈
		if(!v.flow) v.flow = "default"; // 슬라이딩 방식 : default | alpha
		if(!v.auto) v.auto = false; //자동롤링 여부
		if(!v.naviNum) v.naviNum = false; //번호 네비 여부
		if(!v.naviStep) v.naviStep = false; //이전,다음 버튼 여부
		if(!v.time) v.time = 3000; //자동롤링 시간
		v.onePos = (100/v.view); // 개체 하나당 위치값
		v.$obj.css({"position":"relative","width":v.w+"px","height":v.h+"px","overflow":"hidden"});

		v.$obj.each(function(){
			$contain = $(this), contain = this, contain.add="";
			if(v.dir=="left") contain.$item = $contain.find(v.childName).css({"position":"absolute","left":"100%","top":"0","z-index":1,"display":"none","overflow":"hidden","width":v.w/v.view+"px","height":v.h+"px"});
			else if(v.dir=="top") contain.$item = $contain.find(v.childName).css({"position":"absolute","top":"100%","left":"0","z-index":1,"display":"none","overflow":"hidden","width":v.w+"px","height":v.h/v.view+"px"});
			contain.aflag = false;
			contain.mflag = false;
			contain.curNum = 1, contain.oldNum = null;
			contain.repeat = null;
			contain.time = v.time;
			contain.$page = null;

			for(var i=0; i<v.view; i++){
				if(v.dir=="left") contain.$item.eq(i).css({"left":(i*v.onePos)+"%","display":"block"});
				else if(v.dir=="top") contain.$item.eq(i).css({"top":(i*v.onePos)+"%","display":"block"});
			} //처음 보여지는 개체
			if((contain.$item.length > 1) && v.view>=contain.$item.length) return;
			if(v.naviNum || v.auto || v.naviStep){
				contain.add += '<span class="controlArea">'
				if(v.naviNum){
					 contain.add += '<span class="num">';
					for(var i=1; i<=Math.ceil(contain.$item.length/v.view); i++) contain.add += '<a class="num'+(i)+'" href="#'+i+'번째 배너">'+ i+'</a>';
					 contain.add += '</span>';
				}
				if(v.auto) contain.add += '<span class="auto"><a href="#stop" class="stop"><span>슬라이드 수동</span></a></span>';
				if(v.naviStep) contain.add += '<span class="step"><a href="#prev" class="prev">Previous</a><a href="#next" class="next">Next</a></span>';
				contain.add += '</span>';
				if($contain.find(".controlArea").length>0) $contain.find(".controlArea").remove();
				$contain.prepend(contain.add);
			}
			if(v.view*2 > contain.$item.length){
				contain.oldCount = contain.$item.length; //원데이터 길이
				contain.$item.parent()[0].innerHTML += contain.$item.parent()[0].innerHTML;
				contain.$item = $contain.find("li"); //변경데이터
				for(var i=0; i<contain.oldCount ; i++){
					if(v.dir=="left") contain.$item.eq(i+contain.oldCount).addClass("clone").css({"display":"none","left":"100%","top":"0"});
					else if(v.dir=="top") contain.$item.eq(i+contain.oldCount).addClass("clone").css({"display":"none","left":"0","top":"100%"});
				}
			}
			contain.btnSet = function(obj){ if(obj.$page){ obj.$page.removeClass("active").eq(obj.curNum-1).addClass("active"); } }
			contain.prev = function(obj){ obj.mov(obj, obj.curNum-v.view) }
			contain.next = function(obj){ obj.mov(obj, obj.curNum+v.view) }
			contain.toggle = function(obj,btn){ obj.clear(obj); obj.aflag = !obj.aflag; if(!obj.aflag){ obj.auto(obj); } $(btn).toggleClass("play").html(function(){ return (this.innerHTML.indexOf("수동")>-1)?"슬라이드 자동":"슬라이드 수동"}); };
			contain.stop = function(obj){ obj.clear(obj); }
			contain.play = function(obj){ obj.clear(obj); box.auto(obj); }
			contain.auto = function(obj){ obj.clear(obj); if(!obj.aflag){ obj.autoRepeat(obj); }}
			contain.clear = function(obj){ clearTimeout(obj.repeat); }
			contain.autoRepeat = function(obj){ obj.repeat = setTimeout(function(){ obj.next(obj); obj.auto(obj); }, obj.time); }
			if(v.naviNum){
				contain.$page = $contain.find(".controlArea .num a");
				contain.$page.each(function(j){ this.obj = contain; $(this).click(function(){ this.obj.mov(this.obj, (j*v.view)+1); return false; }); });
			}
			if(v.naviStep){
				$contain.find(".controlArea a.prev")[0].contain = contain;
				$contain.find(".controlArea a.next")[0].contain = contain;
				$contain.find(".controlArea a.prev").click(function(){  this.contain.prev(this.contain); return false; });
				$contain.find(".controlArea a.next").click(function(){ this.contain.next(this.contain); return false; }).addClass("active");
			}
			if(v.auto){
				$contain.find(".stop")[0].contain = contain;
				$contain.find(".stop").click(function(){ this.contain.toggle(this.contain,this); return false; });
				contain.auto(contain);
			}
			contain.btnSet(contain);

			contain.mov = function(obj, pos){
				if(obj.mflag) return;
				var curType = (obj.curNum > pos) ? "prev" : "next";
				if(pos<0)  pos = obj.$item.length + pos;
				else if(pos>obj.$item.length) pos = pos - obj.$item.length;
				if(obj.curNum==pos) return;
				obj.oldNum = obj.curNum;
				obj.curNum = pos;
				obj.mflag = true;

				for(var i=pos, plus=0,cn, on, cbpos, cpos, opos; i< pos+v.view; i++, plus++){
					if(obj.oldNum+plus<0)  on = obj.$item.length + (obj.oldNum+plus+1);
					else if(obj.oldNum+plus>obj.$item.length) on = obj.oldNum+plus - obj.$item.length;
					else on = obj.oldNum+plus;
					if(i<0)  cn = obj.$item.length + (i+1);
					else if(i>obj.$item.length) cn  = i - obj.$item.length;
					else cn  = i;
					cbpos = (curType=="prev") ? ((100/v.view)*plus-100) : (100+((100/v.view)*plus));
					cpos = ((100/v.view)*plus);
					opos = (curType=="prev") ? (100+((100/v.view)*plus)) : ((100/v.view)*plus-100);
					if(v.flow == "alpha"){
						obj.$item.eq(on-1).css({"z-index":1,"left":((v.dir=="left")?cpos+"%":0),"top":((v.dir=="top")?cpos+"%":0)+"%","display":"block"}).stop().animate({"opacity" : 0},600, function(){ $(this).css({"display":"none"}); });
						obj.$item.eq(cn-1).css({"z-index":2,"left":((v.dir=="left")?cpos+"%":0),"top":((v.dir=="top")?cpos+"%":0)+"%","display":"block","opacity":0}).stop().animate({"opacity" : 1},600,  function(){ obj.mflag = false; });
					}else{
						if(v.dir=="left"){
							obj.$item.eq(on-1).css({"z-index":1}).stop().animate({"left" : opos+"%"},600, function(){ $(this).css({"display":"none","left":"100%"}); });
							obj.$item.eq(cn-1).css({"z-index":2, "display":"block", "left":cbpos+"%"}).stop().animate({"left" : cpos+"%"},600, function(){ obj.mflag = false; });
						}else if(v.dir=="top"){
							obj.$item.eq(on-1).css({"z-index":1}).stop().animate({"top" : opos+"%"},600, function(){ $(this).css({"display":"none","top":"100%"}); });
							obj.$item.eq(cn-1).css({"z-index":2, "display":"block", "top":cbpos+"%"}).stop().animate({"top" : cpos+"%"},600, function(){ obj.mflag = false; });
						}
					} //v.flow
				}
				obj.btnSet(obj);
			}
		});
	}
}

var creamUI = {
	gnb : {
		$obj : null, $item : [], subName : ["sub"],
		init : function (obj) {
			var gnb = creamUI.gnb;
			this.$obj = obj;
			this.$item = this.$obj.children();
			this.$item.each(function(){
				var $this = $(this);
				if(!this.className.match("active")) $this.find("."+gnb.subName[0]).hide();
				/* $this.find("a").first().click(function(){
					if($(this).parent().find("."+gnb.subName[0]).length==0){
						return;
					}else{
						creamUI.gnb.$item.removeClass("show").filter(this.parentNode).addClass("show");
						creamUI.gnb.$item.not(this.parentNode).find("."+gnb.subName[0]).slideUp(600,"easeInOutCubic").end().end().filter(this.parentNode).find("."+gnb.subName[0]).slideDown(600,"easeInOutCubic");
						return false;
					}
				}); */
			});
		} /*
		$obj : null, $active : null,
		init : function(obj){
			this.$obj = obj;
			this.$active = this.$obj.find(".active");
			this.$active.filter("li").find(".sub").show();
		} */
	},
	fixedSet : function(first){
		if(creamUI.effect.flag==true) creamUI.effect.firstPos = Math.max($("html").scrollTop(),$("body").scrollTop());
		var obj = $("header.layout, .fixedList");
		var ml = Math.max($("html").scrollLeft(), $("body").scrollLeft());
		obj.css({"margin-left":"-"+ml+"px"});
		if($("#wrap.works").length>0){
			if($("#wrap.works")[0].offsetWidth<1088) $("aside.layout").css({"left":"1088px", "margin-left":"-"+ml+"px"});
			else $("aside.layout").css({"left":"100%", "margin-left":"-"+ml+"px"});
		}
		creamUI.main.sc();
		if(first!=true) creamUI.effect.init();
	},
	effect : {
		$obj : null, repeat : null, flag : true, flag2 : false, objname : "span.deco", firstPos :0,
		set : function(){
			if(this.$obj.length < 1) return;
			this.$obj.stop().animate({"marginTop":0},600,'easeInOutCubic');
			this.flag = true;
		},
		init : function(){
			this.$obj = $(this.objname);
			clearTimeout(this.repeat);
			if(this.flag){ this.flag2 = true;}
			else{
				if(this.flag2) this.$obj.each(function(i){$(this).stop().animate({"marginTop":(  creamUI.effect.firstPos > Math.max($("html").scrollTop(),$("body").scrollTop()) ? "5px" : "-5px" )},600,'easeInOutCubic')});
				this.flag2 = false;
			}
			this.flag = false;
			this.repeat = setTimeout(function(){ creamUI.effect.set(); },300);
		}
	},
	main : {
		$obj : null, $menu : null,
		sc : function(){
			if(creamUI.main.$menu.length<1) return;
			var curTop = Math.max($("html").scrollTop(),$("body").scrollTop());
			creamUI.main.$obj.each(function(i){
				if($(this).offset().top<= curTop && $(this).offset().top+this.offsetHeight > curTop) creamUI.main.$menu.find("li").eq(i).addClass("active");
				else creamUI.main.$menu.find("li").eq(i).removeClass("active");
			});
		},
		init : function(){
			creamUI.main.$obj = $(".secIntro, .secWork, .secCream");
			creamUI.main.$menu = $(".indexWrap .navmenu");
			if(creamUI.main.$menu.length<1) return;
			creamUI.main.$menu.each(function(){
				$(this).find("a").each(function(i){
					this.num = i;
					$(this).click(function(){
						$("html, body").stop().animate({"scrollTop":creamUI.main.$obj.eq(this.num).offset().top},800,'easeInOutCubic');
						return false;
					});
				});
			});
		}
	},
	common : function(){
		creamUtil.inputDesign();
	},
	init : function(){
		$("header.layout").before("<div class='banner10'></div>");
		this.common();
		if($("header.layout .gnb").length>0){
			this.gnb.init($("header.layout .gnb"));
			this.main.init();
			$(window).resize(this.fixedSet).scroll(this.fixedSet);
			this.fixedSet(true);
		}
		loading.view();
	}
}
var loading = {
	$obj :{ text: "<div class='isloading'></div>" },
	timer : null, curPos : 0, max : 7,
	view : function(){
		$.isLoading(loading.$obj);
		this.timer = setInterval( function(){ loading.curPos = ( (loading.curPos+1 > loading.max) ? 0 : loading.curPos+1 ); $(".isloading").css({"background-position":"0 -"+(loading.curPos*59)+"px"}) }, 300 );
	},
	hidden : function(){
		clearInterval(this.timer);
		$.isLoading("hide");
	}
}
$(document).ready(function($) {
	loading.hidden();
});

var fixedWheel = {
	$obj : null, $mObj : null,
	overAni : function(){
		fixedWheel.$obj.find(".item").not($(this).parents(".item")).stop().animate({"opacity":0.7},300);
		$(this).find(".view").stop().animate({"opacity":1},100).parents(".item").stop().animate({"opacity":1},300);
	},
	outAni : function(){
		fixedWheel.$obj.find(".item").stop().animate({"opacity":1},300);
		$(this).find(".view").stop().animate({"opacity":0},100);
	},
	set : function($obj){
		this.$obj = $obj;
		this.$obj[0].flag = false;
		this.$obj.children().wrapAll("<div style='position:relative'>");
		this.$mObj = this.$obj.children().first();
		this.$mObj.stop().animate({"top":-( this.$obj.find('.active').offset().top-Math.max($("html").scrollTop(),$("body").scrollTop()) )},'easeInOutCubic');
		if(this.$mObj.find(".item a .view").length>0) this.$mObj.find(".item a").mouseover(fixedWheel.overAni).focus(fixedWheel.overAni).mouseout(fixedWheel.outAni).blur(fixedWheel.outAni).find(".view").css({"opacity":"0","display":"block"});
		this.$obj.bind({
			mouseover : function(){ this.flag = true; },
			mouseout : function(){ this.flag = false; },
			mousewheel : function(event, delta){
				if(fixedWheel.$obj[0].flag){
					var cur = Math.floor(parseFloat(fixedWheel.$mObj.css("top"))/160)*-1;
					var step = Math.floor(fixedWheel.$obj[0].offsetHeight/160);
					var max = fixedWheel.$obj[0].scrollHeight;
					var nextPos = (delta > 0) ? ((cur-step)*160) : ((cur+step)*160);
					if(nextPos < 0) nextPos = 0;
					else if(nextPos >= max) return false;
					fixedWheel.$mObj.stop().animate({"top":-nextPos},150*step,'easeInOutCubic');
					return false;
				}else{
					return;
				}
			}
		});
		if( (navigator.userAgent.match(/iPhone|iPad|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|Mobile|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || navigator.userAgent.match(/LG|SAMSUNG|Samsung/) != null) ){
			touch.$obj = this.$obj;
			touch.$target = this.$mObj;
			touch.evType = document.hasOwnProperty && document.hasOwnProperty('ontouchstart') && ((/ip(ad|hone|od)/i).test(navigator.userAgent) || (/android/i).test(navigator.userAgent));
			touch.event.add(touch.$obj[0], 'mousedown', touch.down, "scLIst");
		}
	}
}

var touch = {
	$obj : null, $target : null, sPos : 0, ePos : 0, cPos : { t : 0, h : 160, firstT : 0, maxH : 0 }, evType : false, movTime : { start : null, end : null },
	getpoint : function(e){
		return (touch.evType)?
			[
				(e.touches[0])? e.touches[0].pageX : e.changedTouches[0].pageX,
				(e.touches[0])? e.touches[0].pageY : e.changedTouches[0].pageY
			] :
			[e.clientX, e.clientY];
	},
	down : function(e){
		var targetPos = touch.getpoint(e);
		touch.event.add(document, 'mousemove', touch.mov, "scLIst");
		touch.event.add(document, 'mouseup', touch.up, "scLIst");
		touch.sPos = { x : targetPos[0], y : targetPos[1] };
		touch.movTime.start =new Date().getTime();
		touch.cPos.firstT = touch.cPos.t;
	},
	mov : function(e){
		e.preventDefault();
		var targetPos = touch.getpoint(e);
		touch.cPos.t = touch.cPos.t+(targetPos[1]-touch.sPos.y);
		touch.sPos.y = targetPos[1];
		touch.$target.css({"top":(touch.cPos.t)+"px"});
	},
	up : function(e){
		touch.movTime.end =new Date().getTime();
		var swipetime = touch.movTime.end-touch.movTime.start; //움직인 시간
		var swipegap = touch.cPos.firstT - touch.cPos.t; //움직인 정도
		var viewCount = Math.floor(touch.$obj.height()/touch.cPos.h); //보이는 개수
		touch.event.remove(document, 'mousemove', touch.mov, "scLIst");
		touch.event.remove(document, 'mouseup', touch.up, "scLIst");
		if(swipegap==0) return;
		var curCount = Math.abs(touch.cPos.firstT/touch.cPos.h); //현재 위치 번호
		if(300>swipetime && (swipegap > 0 && swipegap >= 80)) touch.cPos.t = -(curCount+viewCount)*touch.cPos.h;
		else if(300>swipetime && (swipegap < 0 && swipegap < -80)) touch.cPos.t = -(curCount-viewCount)*touch.cPos.h;

		if(touch.cPos.t>0) touch.cPos.t = 0;
		else if(touch.cPos.t < -(touch.$target.height()-touch.$obj.height()) ) touch.cPos.t = -(touch.$target.height()-touch.$obj.height());
		else touch.cPos.t = Math.ceil(touch.cPos.t/touch.cPos.h)*touch.cPos.h;
		touch.$target.stop().animate({"top":touch.cPos.t},600,'easeOutCubic');
	},
	event : {
		typeinmobile : {  mouseover : 'touchstart',  mousedown : 'touchstart',  mousemove : 'touchmove',  mouseout : 'touchend',  mouseup : 'touchend'  },
		type : function(type){
			var typeinmobile=touch.event.typeinmobile;
			if(touch.evType && typeinmobile[type]) return typeinmobile[type];
			else if((/firefox/i).test(navigator.userAgent) && type=='mousewheel') return 'DOMMouseScroll';
			return type;
		},
		add : function(target, type, callback, id){
			type=touch.event.type(type);
			if(target.addEventListener){
				target.addEventListener(type, callback, false);
			}else{
				target[type+id+callback]=function(){ callback.call(target, window.event); }
				target.attachEvent('on'+type, target[type+id+callback]);
			}
		},
		remove : function(target, type, callback, id){
			type=touch.event.type(type);
			if(target.removeEventListener){
				target.removeEventListener(type, callback, false);
			}else{
				if(target[type+id+callback]){
					target.detachEvent('on'+type, target[type+id+callback]);
					target[type+id+callback]=null;
				}
			}
		}
	}
}

/* Main Set */
function mainIntroSet($obj1, $obj2){
	if($obj1[0].timer) clearTimeout($obj1[0].timer);
	$obj1[0].timer = 0, $obj1[0].count = 0;
	var $row = $obj1.find(".visual .row");
	$row.find("img").pngFix();
	$row.each(function(ii){
		$(this).find("span").filter(".back").stop().delay((300*(ii+1))+0).animate({"opacity":1}, 100, 'easeInQuad')
		$(this).find("span").not(".back").stop().delay((300*(ii+1))+200).animate({"left":0,"opacity":1}, 600, 'easeInQuad', function(){
			$(this).next().stop().animate({"top":"-102px"}, 300, 'easeInQuad', function(){
				$obj1[0].count += 1;
				if($obj1[0].count == $row.length) $row.find(".back").eq(Math.floor(Math.random()*4)).stop().delay(400).animate({"top":"0"}, 300, 'easeInQuad');
			});
		});
	});

	$obj1[0].timer = setTimeout(function(){
		$obj1.animate({"left":"-100%"}, 600, 'easeInOutCubic');
		$obj2.animate({"left":"0"}, 600, 'easeInOutCubic');
		creamUtil.slider({$obj:$obj2, childName:".item", w:"100%", h:887, view:1, dir:"left", flow:"default", auto:true, naviNum:false, naviStep:true, time : 5000});
	},10000);
	//this.isBindtimer = setTimeout(function(){ creamUtil.resizeObj.set(); },300); }
}

/* Works List Set */
jQuery.fn.extend({
	reset : function(){ this.each(function(){ try{ return this.reset(true); }catch(e){} });  },
	addDeco :function(option){
		this.each(function(i){ if(i >= option.start){
			for(var z=0; z<option.pos.length; z++){
				if( i == Math.floor((i-option.start)/option.gap)*option.gap+option.pos[z]+option.start && !($(this).prev()[0].className.match(/decoblank/))) $('<div class="item decoblank loop'+Math.floor((i-option.start)/option.gap)+' decobg'+z+'"></div>').insertBefore(this);
			}
		} });
	},
	dynamic : function(option){
		creamUtil.resizeObj.$obj = this;
		this.each(function(){
			this.style.position = "relative";
			var objWrap = this;

			this.returnPos = function(val, sizeW, sizeH){
				var clonePos = eval("["+val+"]"), pos;
				for(var i=0; i<clonePos.length; i++)	 pos = (pos) ? Math.min(pos, clonePos[i]) : clonePos[i];
				for(var i=0; i<objWrap.lastTop.length; i++){ if(objWrap.lastTop[i]==pos){ if(i+sizeW <= objWrap.lastTop.length){ for(var j=0, falg=true; j<sizeW; j++){ if(objWrap.lastTop[i+j] > pos){ falg = false; } if(j == sizeW-1 && falg){ for(var z=0; z<sizeW; z++){ objWrap.lastTop[i+z] += sizeH; } return i; } } } } }
				if(i==0) return objWrap.returnPos(val.replace(pos+",",""), sizeW, sizeH);
				else return objWrap.returnPos(val.replace(","+pos,""), sizeW, sizeH);
			} //this.returnPos

			this.reset = function(type){
				var obj = this;
				obj.loadCount=0, obj.loadImg = 0;
				var count = (obj.loadMax)? $(this).find("img").length-obj.loadMax : $(this).find("img").length;
				obj.loadMax = $(this).find("img").length;
				if(option.decopos) $(objWrap).find(option.itemName).not(".decoblank").addDeco({start: option.decostart, gap :option.decogap, pos : option.decopos}); //Deco Blank
				obj.loadImg = $(this).find("img");
				obj.loadImg.each(function(){
					if(this.complete){
						obj.loadCount += 1;
						if(obj.loadCount == obj.loadImg.length) obj.rePos();
					}else{
						$(this).load(function(){ if((obj.loadCount += 1) == obj.loadImg.length) obj.rePos(); }).error(function(){ if((obj.loadCount += 1) == obj.loadImg.length) obj.rePos(); });
					}
				});
			} //this.reset

			this.rePos = function(){
				var itemName = option.itemName, w = option.width, gap = option.gap, isAni = option.isAni, $obj = $(this);
				var maxX = Math.floor( (this.offsetWidth+gap)/(w+gap) );
				var item = $obj.find(itemName);
				this.lastTop = new Array(maxX);
				for(var i=0; i<maxX; i++){ this.lastTop[i] = 0; }
				item.removeClass("top").filter(":even").addClass("top").end().each(function(i){
					var pos = objWrap.returnPos( objWrap.lastTop.join(","), (this.offsetWidth+gap)/(w+gap), (this.offsetHeight+gap) );
					$(this).stop().delay(i*10).animate({"left":(pos)*(w+gap)+"px", "top":objWrap.lastTop[pos]-(this.offsetHeight+gap)+"px",  "margin-left":0, opacity : 1 }, 600, 'easeInOutCubic');
				});
				var outerH;
				for(var i=0; i<objWrap.lastTop.length; i++)	 outerH = (outerH) ? Math.max(outerH, objWrap.lastTop[i]) : objWrap.lastTop[i];
				$obj.css({"height":outerH+"px"});
			} //this.rePos

			this.isBindtimer = null;
			this.isunBind = function(){ clearTimeout(this.isBindtimer); this.rePos(); }
			this.reLayout = function(){ clearTimeout(this.isBindtimer); this.isBindtimer = setTimeout(function(){ creamUtil.resizeObj.set(); },300); }
			$(window).resize(this.reLayout);
		});
		this.reset();
	},
	pngFix : function(){
		if(navigator.userAgent.toLowerCase().indexOf("msie") < 0) return;
		var version = parseFloat(navigator.userAgent.toLowerCase().substr(navigator.userAgent.toLowerCase().indexOf("msie"), 7).replace("msie ",""));
		if( version > 8 ) return;
		return $(this).each(function() {
			var img = $(this);
			var src = img.attr('src');
			if(!src.match(/\.png/)) return;
			img.css('filter', "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src='" + src + "')");
		});
	 }
});
