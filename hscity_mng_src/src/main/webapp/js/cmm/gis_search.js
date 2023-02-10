$(document).ready(function (){

	// lnb 아코디언 메뉴
	$.sidebarMenu($('.sidebar-menu'));

	// lnb 창 펼치고 닫기
	/*
	$(".lnb-fold .btn-fold").click(function(){
		if ($("#gis-lnb").css("marginLeft")=="0px"){
			$("#gis-lnb").animate({marginLeft:'-269px'},300);
			$("#gis-container").addClass("slide");
			$(".lnb-fold").addClass("off");
		}else {			
			$("#gis-lnb").animate({marginLeft:'0px'},10);
			$("#gis-container").removeClass("slide");
			$(".lnb-fold").removeClass("off");
		}
	});
	*/
	

	// tab2메뉴
	$(function(){
		var tab = $('.tab2');
		var tab_i = tab.find('>ul>li');
		tab_i.find('>.sec-tab').hide();
		tab.find('>ul>li.active').find('>.sec-tab').show();
		tab.css('height', tab.find('>ul>li.active>.sec-tab').height()+ tab.find('>ul>li').height());
		function tabMenuToggle(event){
			var t = $(this);
			var p = t.parent().parent().find('>li');
			p.find('>.sec-tab').hide();
			t.next('.sec-tab').show();
			p.removeClass('active');
			t.parent('li').addClass('active');
			tab.css('height', t.next('.sec-tab').height()+tab.find('>ul>li').height());
			return false;
		}
		tab_i.find('>a[href=#]').click(tabMenuToggle).focus(tabMenuToggle);
	});

	// tab3메뉴
	$(function(){
		var tab = $('.tab3');
		var tab_i = tab.find('>ul>li');
		tab_i.find('>.sec-tab').hide();
		tab.find('>ul>li.active').find('>.sec-tab').show();
		tab.css('height', tab.find('>ul>li.active>.sec-tab').height()+ tab.find('>ul>li').height());
		function tabMenuToggle(event){
			var t = $(this);
			var p = t.parent().parent().find('>li');
			p.find('>.sec-tab').hide();
			t.next('.sec-tab').show();
			p.removeClass('active');
			t.parent('li').addClass('active');
			tab.css('height', t.next('.sec-tab').height()+tab.find('>ul>li').height());
			return false;
		}
		tab_i.find('>a[href=#]').click(tabMenuToggle).focus(tabMenuToggle);
	});

	//drop slide
	$(".btn-drop").click(function(){ 
		var t = $(this);
		var dropSlide = t.parent().children(".drop-slide");		
		if (dropSlide.css("display")=="none"){
			dropSlide.slideDown(200);
			t.addClass("on");
		}else {			
			dropSlide.slideUp(100);
			t.removeClass("on");
		}
	});	
});

function toggleLeftMenu(mapType, chkType, pid){
	if ($("#gis-lnb").css("marginLeft")=="0px"){
		$("#gis-lnb").animate({marginLeft:'-269px'},300);
		$("#gis-container").addClass("slide");
		$(".lnb-fold").addClass("off");
	}else {			
		$("#gis-lnb").animate({marginLeft:'0px'},10);
		$("#gis-container").removeClass("slide");
		$(".lnb-fold").removeClass("off");
	}
	updateMap(mapType, chkType, pid);
}
