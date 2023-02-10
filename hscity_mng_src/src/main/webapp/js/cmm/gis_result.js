$(document).ready(function (){
	$(".result-btn-wrap .btn-fold").click(function(){
		if ($("#gis-main").hasClass("active")){
			showMapResult(true);
		}else{
			showMapResult(false);
		}
	});
	
	// tab메뉴
	$(function(){		
		var tab = $('.tab');
		var tab_i = tab.find('>ul>li');
		tab_i.find('>.sec-tab').hide();
		tab.find('>ul>li.active').find('>.sec-tab').show();
		tab.css('height', tab.find('>ul>li.active>.sec-tab').height()+ tab.find('>ul').height());
		function tabMenuToggle(event){
			var t = $(this);
			var p = t.parent().parent().find('>li');
			p.find('>.sec-tab').hide();
			t.next('.sec-tab').show();
			p.removeClass('active');
			t.parent('li').addClass('active');
			tab.css('height', t.next('.sec-tab').height()+tab.find('>ul').height());
			return false;
		}
		tab_i.find('>a[href=#]').click(tabMenuToggle).focus(tabMenuToggle);
	});	
	
	//custom
	$('.tab .btn-tab').click(function(){
		clearLegendInfo(); //범례 초기화 common.js
		//legend 다시 읽어들이기
		var id = getMenuId();
		if(id == '1-1-3')
			id = "1-1-3-1"; 
		else if(id == '1-1-4')
			id = "1-1-4-1";
		var lndparams = $('#tblGrid' + id)[0].lndparams;
		if(lndparams != undefined && lndparams != null){
			showLegendInfo(lndparams);	
		}
		//활성화여부
		if ($("#gis-main").hasClass("active")){
			$(".result-btn-wrap .btn-fold").trigger("click");			
		}
	});
});

//결과창 상태 적용
function showMapResult(chk){
	if(chk){
		$("#gis-main").removeClass("active");
		$(".result-btn-wrap .btn-fold").removeClass("on");	
		$(".result-btn-wrap .btn-fold").text('접기');
	}else{
		$("#gis-main").addClass("active");
		$(".result-btn-wrap .btn-fold").addClass("on");
		$(".result-btn-wrap .btn-fold").text('펼치기');
	}
}
