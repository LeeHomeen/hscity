/**
*
* 교통 분석 관련 js (사각지대 분석)
*
**/

/**
 * 페이지 초기화
 */
function initBlindPageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;
	/*
	setBasicMonth('txtBasicDate'); //기본 기준년월일 세팅
	setCalendarMonth('txtBasicDate'); //common.js
	
	$('.numbersOnly').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	*/
	
	//grid 세팅	
	var options = {};
	options.height = $('.wp80').height() - 35;
	
	//options.num = 30000; //속도문제
	//options.loadonce = true,
	options.num = 10000; //-1 
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "2-1-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	mapObj.mapTools.basicLocation(); //기본 위치로 이동
	//지도상에 정류장 정보 표시
	setStaResult(flag);	
	//결과 표출	
	setBlindResult(chkFlag, flag);
}

///////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////사각지대 결과//////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////

function setBlindResult(id, mapType){
	var mapObj = getMapObj(mapType);
	openTreePopup(false, mapType); //분석결과 트리 hide
	
	var gridId = "tblGrid" + id;
	$('#'+gridId).jqGrid('clearGridData');	//grid 클리어	
	$('#divMapLoading').show();
	
	var coord = mapObj.map.getProjection().replace("EPSG:", '');
	var blindParams = {coord:coord};
	
	$.ajax({
		type:"POST",
		url:"/gis/traffic/blindList.do",
		dataType:'json',
		async:true,
		data: blindParams,
		async: true,
		success:function(data){
			//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
			if(data.result != null && data.result.length > 1){
				//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
				var datas = getBlindWMSParams(mapObj, id);
				datas.coord = coord;				
				$('#t'+id).show();
				$('#li'+id).trigger('click');
				settingGridData(gridId, data.result); //grid data
				//범례정보 표시
				var lndParams = {};
				var legendInfo = getLegendInfo("/gis/traffic/blindLegend.do", blindParams); //범례표시 정보 (max, min)
				if(legendInfo != undefined && legendInfo != null){
					datas.params.max = legendInfo[0].max;
					datas.params.min = legendInfo[0].min;
					mapObj.addAnalsWMSLayer(datas);
					//범례정보 표시
					lndParams.max = datas.params.max;
					lndParams.min = datas.params.min;
					lndParams.vtype = 'area';
					lndParams.vcolor = datas.params.vcolor;
					showLegendInfo(lndParams); //common.js
				}
				//엑셀 조회용 params저장
				setGridParams(gridId, blindParams, lndParams); //grid_chart.js										
			}else{				
				clearResultTab(id, "2-1");
				openInfoPopup(true,"검색결과정보가 없습니다.");
			}				
		},
		error:function(err){
			if(chkSession(err.status)){
				clearResultTab(id, "2-1");
				openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
			}
		}
	});
}


//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////공통/////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

function getBlindWMSParams(mapObj, id){
	var url = "/gis/traffic/blindAnals.do";
	var analsName = getMenuName(id); //메뉴 분석한글명 가져오기
	
	var datas = {
		mapType : mapObj.mapType,
	    params  : {
	    	 aid    : id,
	    	 vcolor : 'blue'
	 	},
	 	layerName : "popLayer"+id,
	 	analsName : analsName,
	 	analsUrl  : url
	 };
	 return datas;
}
