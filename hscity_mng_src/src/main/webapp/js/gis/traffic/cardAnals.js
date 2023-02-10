/**
*
* 교통 분석 관련 js (교통카드현황 분석)
*
**/

/**
 * 페이지 초기화
 */
function initCardPageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;
	searchJspPage("/gis/bus.do", {flag:flag}, false, "divBusInfo"); //공통 영역지정 페이지 호출
	
	//grid 세팅	
	var options = {};
	options.height = $('.wp60').height() - 35;
	
	options.num = 100000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "2-3-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	//chart 세팅 divChart1-1-1
	options.chartId = "divChart"+chkFlag;
	options.chartData = [[null]];	
	initLineChartResult(options);
	
	mapObj.mapTools.basicLocation(); //기본 위치로 이동
}


/////////////////////////////////////////////////////////////////////////////
//////////////////////////////////정류장별 승하차 분석 //////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function cardAnals(id, mapType){
	//버스노선 선택 validation
	if(chkMultiBusCnt(1)){
		var mapObj =  getMapObj(mapType);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var gridId = "tblGrid" + id;
		$('#'+gridId).jqGrid('clearGridData');	//grid 클리어	
		$('#'+"divChart" + id).html(""); //차트 클리어
		$('#divMapLoading').show();
		
		var cardParams = { 
			bus : multiAreaValue("multibus"),
			coord: mapObj.map.getProjection().replace("EPSG:", '')
		};
		$.ajax({
			type:"POST",
			url:"/gis/traffic/cardList.do",
			dataType:'json',
			async:true,
			data: cardParams,
			async: true,
			success:function(data){
				//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
				if(data.result != null && data.result.length > 0){
					var datas = getAnalsCardParams(id, mapType);
					setHighlightLayer(datas, true, true);
					//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
					$('#t'+id).show();
					$('#li'+id).trigger('click');
					settingGridData(gridId, data.result); //grid data							
					//엑셀 조회용 params저장
					setGridParams(gridId, cardParams); //grid_chart.js
					//차트
					setCardChartInfo(data.result, id);	
				}else{					
					clearResultTab(id, "2-3");
					openInfoPopup(true,"검색결과정보가 없습니다.");
				}				
			},
			error:function(err){
				if(chkSession(err.status)){
					clearResultTab(id, "2-3");
					openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
				}
			}
		});
	}
}

/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// 공통 /// //////////////////////////////
/////////////////////////////////////////////////////////////////////////////

//차트구성
function setCardChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();
	
	var chartData =[];	
	var tmpInChart = []; //승차 결과
	var tmpOutChart = []; //하차 결과
	var tmpTrsChart = []; //환승 결과
	var labels = [];
	if(datas.length > 0){
		var prev = datas[0].lid; //노선 id
		var bnum = datas[0].bnum; //우선 사용안함
		labels.push("승차자수");
		labels.push("하차자수");
		labels.push("환승자수");
		//중복 x축 라벨명 체크
		var chkNameCnt = 0;
		var nmMap = new Map(); //라벨명 같을 경우 차트표시 에러
		
		for(var i=0; i<datas.length; i++){
			var data = datas[i];			
			if(prev != data.lid){
				prev = data.lid;
				labels.push("승차자수");
				tmpInChart.push([data.div, data.spop]);
				labels.push("하차자수");
				tmpOutChart.push([data.div, data.fpop]);
				labels.push("환승자수");
				tmpTrsChart.push([data.div, data.tpop]);
			}else{
				/**
				 * 1. 동일한 x라벨명이 있을경우 차트가 중복으로 그려지는 문제발생 (라벨명을 체크하여 같은경우 (1)..을 붙여줘 중복 문제해결
				 * 추후 라벨 중복가능한 옵션있을경우 하단의 로직 변경가능
				 */
				var name = data.div;
				var chkSplit = false;
				if(name.length > 4){
					name = name.substring(0,4);
					chkSplit = true;
				}				
				var chkName = nmMap.get(name);
				if(chkName != undefined && chkName != null){
					if(chkSplit)
						name += "~"; //말줄임표
					name += "_"+ ++chkNameCnt; //중복라벨명이 있을경우 명칭변경
				}
				nmMap.put(name, name);
				tmpInChart.push([name, data.spop]);	
				tmpOutChart.push([name, data.fpop]);
				tmpTrsChart.push([name, data.tpop]);
			}
			if(i == (datas.length - 1) || data.div == '합계'){
				chartData.push(tmpInChart);
				chartData.push(tmpOutChart);
				chartData.push(tmpTrsChart);
				tmpInChart = [];
				tmpOutChart = [];
				tmpTrsChart = [];
				continue;				
			}
		}
		settingLineChartData(chartId, chartData, labels, '정류장명', '인구수(명)', 60);
	}
}


//param설정
function getAnalsCardParams(id, mapType){	 
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
