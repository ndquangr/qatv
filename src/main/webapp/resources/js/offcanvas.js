/*
$(document).ready(function () {
  $('[data-toggle="offcanvas"]').click(function () {
    $('.row-offcanvas').toggleClass('active')
  });
});
	*/
$(document).ready(function () {
	  $('.mt_menu_close a').click(function () {
	    $('.row-offcanvas').toggleClass('active')
	  });
	});

	
	
$(document).on("click", "[data-toggle='offcanvas']", function(){
	$('.row-offcanvas').toggleClass('active');
});

$(document).on("click", ".mt_menu_close a", function(){
	 $('.row-offcanvas').toggleClass('active');
});