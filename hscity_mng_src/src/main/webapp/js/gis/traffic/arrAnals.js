/**
*
* 교통 분석 관련 js (탄력배차 분석)
*
**/

/**
 * 페이지 초기화
 */
function initArrPageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;
	searchJspPage("/gis/bus.do", {flag:flag}, false, "divBusInfo"); //공통 영역지정 페이지 호출
	
	//grid 세팅	
	var options = {};
	options.height = $('.wp100').height() - 35;
	
	options.num = 10000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "2-5-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);	
	
	/**
	 * 연령대별 승차정보
	 */
	chkFlag = "2-5-2";
	initGridInfo(chkFlag, options, flag);
	
	//맨 앞 탭 선택
	$('#li2-5-1').trigger('click');
	showMapResult(false);
	
	mapObj.mapTools.basicLocation(); //기본 위치로 이동
}

/////////////////////////////////////////////////////////////////////////////
//////////////////////////////////요일별 조회 분석 /////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function weekAnals(id, mapType){
	//버스노선 선택 validation
	if(chkMultiBusCnt(3)){
		var mapObj =  getMapObj(mapType);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var gridId = "tblGrid" + id;
		$('#'+gridId).jqGrid('clearGridData');	//grid 클리어
		
		$('#divMapLoading').show();
		
		var weekParams = { 
			bus : multiAreaValue("multibus"),
			coord: mapObj.map.getProjection().replace("EPSG:", '')
		};
		
		$.ajax({
			type:"POST",
			url:"/gis/traffic/arrWkList.do",
			dataType:'json',
			async:true,
			data: weekParams,
			async: true,
			success:function(data){
				//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
				if(data.result != null && data.result.length > 0){
					//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
					var datas = getAnalsArrParams(id, mapType);
					setHighlightLayer(datas, true, true);
					$('#t'+id).show();
					$('#li'+id).trigger('click');
					settingGridData(gridId, data.result); //grid data							
					//엑셀 조회용 params저장
					setGridParams(gridId, weekParams); //grid_chart.js		
				}else{					
					clearResultTab(id, "2-5");
					openInfoPopup(true,"검색결과정보가 없습니다.");
				}				
			},
			error:function(err){
				if(chkSession(err.status)){
					clearResultTab(id, "2-5");
					openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
				}				
			}
		});
	}
}


/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////청두/미청두 조회 분석 //////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function timeAnals(id, mapType){
	//버스노선 선택 validation
	if(chkMultiBusCnt(3)){
		var mapObj =  getMapObj(mapType);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var gridId = "tblGrid" + id;
		$('#'+gridId).jqGrid('clearGridData');	//grid 클리어
		
		$('#divMapLoading').show();
		
		var timeParams = { 
			bus : multiAreaValue("multibus"),
			coord: mapObj.map.getProjection().replace("EPSG:", '')
		};
		
		$.ajax({
			type:"POST",
			url:"/gis/traffic/arrTmList.do",
			dataType:'json',
			async:true,
			data: timeParams,
			async: true,
			success:function(data){
				//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
				if(data.result != null && data.result.length > 0){
					//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
					var datas = getAnalsArrParams(id, mapType);
					setHighlightLayer(datas, true, true);
					$('#t'+id).show();
					$('#li'+id).trigger('click');
					settingGridData(gridId, data.result); //grid data							
					//엑셀 조회용 params저장
					setGridParams(gridId, timeParams); //grid_chart.js		
				}else{					
					clearResultTab(id, "2-5");
					openInfoPopup(true,"검색결과정보가 없습니다.");
				}				
			},
			error:function(err){
				if(chkSession(err.status)){
					clearResultTab(id, "2-5");
					openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
				}
			}
		});
	}
}

//param설정
function getAnalsArrParams(id, mapType){	 
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