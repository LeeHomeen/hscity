$(function(){
  
  //$.sidebarMenu($('.sidebar-menu'));
  
  //scroll
	//$(".scrollwrap").mCustomScrollbar({
		//theme:"dark-3"
	//});
	
	// 모달 팝업
	$(function(){
		$(".modal-pop").css({
			"position":"fixed",
			"top":"0%",
			"left":"0%",
			"width":"100%",
			"height":"100%",
			"background":"url(/images/cmm/bg-layer-pop.png)", //경로변경시 수정.
			"overflow-y":"auto"
		});
		
		$(".layer-pop").css({
			"margin": "100px auto 0"
		});

		$(".btn-popup").on("click",
			function(e){
				e.preventDefault();
				$("body").css(	{
					"overflow":"hidden",
					"margin-right":"18px" 
				});
				$(".pop-box1").show();			
		});
		$(".btn-popup2").on("click",
			function(e){
				e.preventDefault();
				$("body").css(	{
					"overflow":"hidden",
					"margin-right":"18px" 
				});
				$(".pop-box2").show();			
		});
		/*
		$(".modal-pop").on( "click",
			function(e){
				if(e.target == this){
					$(".pop-close").trigger("click");
				}
		}); 
		*/
		$(".pop-close").on("click",
			function(e){
				e.preventDefault();
				$(".modal-pop").hide();
				$("body").css({
					"overflow":"visible",
					"margin-right":"0px"
				});
		});
	});

	//drop slide
	$(".btn-drop").click(function(){ 
		var t = $(this);
		var dropSlide = t.parent().next(".drop-slide");		
		if (dropSlide.css("display")=="none"){
			dropSlide.slideDown(300);
			t.addClass("on");
		}else {			
			dropSlide.slideUp(100);
			t.removeClass("on");
		}
	});
	
});