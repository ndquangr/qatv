/*-----------------------------------------------------------------------------------*/
/*	Document Ready
/*-----------------------------------------------------------------------------------*/
jQuery(document).ready(function($){
'use strict';

	commonUI.boardView();
	commonUI.boardView({
		container : '.poll-item',
		content : '.content',
		button : '.button-participation, .button-result',
		upIcon : 'ico-up01',
		downIcon : 'ico-down01'
	});
	
	commonUI.boardView({
		container : '.work-page',
		content : '.work-detail',
		button : '.detail-button',
		close : '.ico-close',
		upIcon : 'ico-up01',
		downIcon : 'ico-down01'
	});

});

/*-----------------------------------------------------------------------------------*/
/*	
/*-----------------------------------------------------------------------------------*/
var commonUI = {};
commonUI.boardView = function(options)
{
	var opts =
	{
		container : '.board-item',
		content : '.bbs_view_block_open',
		button : '.detail-button',
		close : '.ico-close',
		upIcon : 'glyphicon-chevron-up',
		downIcon : 'glyphicon-chevron-down',
		addText : ' 닫기 ',
	};
	
	var options = $.extend( opts , options );
	
	$(options.container).each(function()
	{
		var _this = $(this);
		var buttonText = _this.find(options.button).text();
		_this.find(options.button).on('click', function()
		{
			if (_this.find(options.content).css('display') == 'block')
			{
				var txt = buttonText;
				txt = txt + '<span class="glyphicon ' + options.downIcon + '"></span>';
				$(this).html(txt); 
			}
			else
			{
				var txt = buttonText + options.addText;
				
				if(document.location.href.indexOf("/eng/") > -1){
					txt = buttonText + ' Close ';
				} 
				
				txt = txt + '<span class="glyphicon ' + options.upIcon + '"></span>';
				$(this).html(txt); 
			}
			toggleView();
		});

		function toggleView() {
			_this.find(options.content).slideToggle('');
			//_this.find('.bbs_pretext').toggle();
		}
	});
}


