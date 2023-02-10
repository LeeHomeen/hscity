/**
*
* 교통 분석 관련 js (저상버스 도입요구분석)
*
**/

/**
 * 페이지 초기화
 */
function initBusPageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;
	searchJspPage("/gis/bus.do", {flag:flag}, false, "divBusInfo"); //공통 영역지정 페이지 호출
	
	//grid 세팅	
	var options = {};
	options.height = $('.wp100').height() - 35;
	
	options.num = 10000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "2-2-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	mapObj.mapTools.basicLocation(); //기본 위치로 이동
	var lnd = setOldGridInfo(chkFlag, flag); //노인격자
	
	//legend params임시 저장
	$("#tblGrid" + chkFlag)[0].lndparams = {};
	$("#tblGrid" + chkFlag)[0].lndparams =  lnd;
}


/////////////////////////////////////////////////////////////////////////////
////////////////////////////////// 노선 조회 분석  /////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

//노인격자
function setOldGridInfo(id, mapType){
	var mapObj = getMapObj(mapType);
	openTreePopup(false, mapType); //분석결과 트리 hide		
	$('#divMapLoading').show();
	
	//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
	var datas = getOldWMSParams(mapObj, "oldGrid");
	datas.coord = mapObj.map.getProjection().replace("EPSG:", '');
	oldParams = {coord:datas.coord};
	//범례정보 표시
	var lndParams = {};
	var legendInfo = getLegendInfo("/gis/traffic/busLegend.do", oldParams); //범례표시 정보 (max, min)
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
	return lndParams;
}

function busAnals(id, mapType){
	//버스노선 선택 validation
	if(chkMultiBusCnt(3)){
		openTreePopup(false, mapType); //분석결과 트리 hide
		var gridId = "tblGrid" + id;		
		$('#'+gridId).jqGrid('clearGridData');	//grid 클리어		
		
		$('#divMapLoading').show();		 
		 var busParams = { bus : multiAreaValue("multibus") };
		 $.ajax({
			type:"POST",
			url:"/gis/traffic/busList.do",
			dataType:'json',
			async:true,
			data: busParams,
			async: true,
			success:function(data){
				//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
				if(data.result != null && data.result.length > 1){
					//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
					//하이라이트 레이어 생성					
					var datas = getAnalsBusParams(id, mapType);
					setHighlightLayer(datas, true, true);
					
					$('#t'+id).show();
					$('#li'+id).trigger('click');
					settingGridData(gridId, data.result); //grid data							
					//엑셀 조회용 params저장
					setGridParams(gridId, busParams); //grid_chart.js											
				}else{					
					clearResultTab(id, "2-2");
					openInfoPopup(true,"검색결과정보가 없습니다.");
				}				
			},
			error:function(err){				
				if(chkSession(err.status)){
					clearResultTab(id, "2-2");
					openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
				}
			}
		});
	}
}

//param설정
function getAnalsBusParams(id, mapType){	 
	 var datas = {
		mapType : mapType,
	    params  : {
	    	 aid    : id,
			 opacity : 1,
			 visible : true
	 	},
	 	layerName : "popLayer"+id,
	 	analsName : getMenuName(id)
	 };
	 return datas;
}

//노인격자params
function getOldWMSParams(mapObj, id){
	var url = "/gis/traffic/busAnals.do";
	var datas = {
		mapType : mapObj.mapType,
	    params  : {
	    	 aid    : id,
	    	 vcolor : 'green'
	 	},
	 	layerName : "oldGrid",
	 	analsName : "노인인구_격자",
	 	analsUrl  : url
	 };
	 return datas;
}