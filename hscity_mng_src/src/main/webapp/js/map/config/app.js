var gis;

$(document).ready(function(){
	gis = new Map();

	// 지도 elementId 설정
	gis.setElementId("gis-map");

	// 지도 중심점
	gis.setDefaultCenter([14126669.41589247, 4493404.190498611]);

	// 기본 zoom 레벨
	gis.setDefaultZoomLevel(10);
	// 지도 생성
	gis.init();

	gis.updateInteractionEvent = function(evt) {
		if( evt.name == "roadview" && !evt.status ) {
			$('#gis-main').css({width:'100%', height:'100%'});
			$('#roadmap').css({width:'0%', height:'100%'});
			gis.mapRefresh();
		}
	}

	// 로드뷰
	$("#btnRaod").click(function() {
		$(this).toggleClass("on");
		gis.toggleRoadViewInteraction($(this).hasClass("on"));
	});

	// 줌인
	$("#btnZoomIn").click(function() {
		gis.zoomIn();
	});

	// 줌아웃
	$("#btnZoomOut").click(function() {
		gis.zoomOut();
	});

	// 새로고침
	$("#btnMapClear").click(function() {
		gis.removeAll();
	});

	// 측정하기
	$("#btnMeasure").click(function() {
		$("#liMeasure").toggleClass("active", !$("#liMeasure").hasClass("active"));
	})

	// 면적계산하기
	$("#btnArea").click(function() {
		$(this).parent().toggleClass("on");
		gis.toggleAreaInteraction($(this).parent().hasClass("on"));
	});

	// 거리계산하기
	$("#btnLine").click(function() {
		$(this).parent().toggleClass("on");
		gis.toggleLengthInteraction($(this).parent().hasClass("on"));
	});

	// 화면저장
	$("#btnSave").click(function() {
		gis.exportMapImage();
	});

	if(flag == 'pop'){
		popMap = gis;
	}else if(flag == 'traffic'){
		trafficMap = gis;
	}
});