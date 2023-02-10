/**
*
* 통행패턴 분석 관련 js
*
**/

/**
 * 페이지 초기화
 */
function initPassPageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;
	
	//grid 세팅	
	var options = {};
	options.height = $('.wp70').height() - 35;
	
	options.num = 100000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "2-7-1";
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

function passAnals(id, mapType){
	//버스노선 선택 validation
	var mapObj =  getMapObj(mapType);
	openTreePopup(false, mapType); //분석결과 트리 hide
	var gridId = "tblGrid" + id;
	$('#'+gridId).jqGrid('clearGridData');	//grid 클리어
	
	$('#divMapLoading').show();
	
	var passParams = { 
		sid: $('#selPassSta').val(),
		ptype: decodeURIComponent($('#selPassGubun').val()),
		coord: mapObj.map.getProjection().replace("EPSG:", '')
	};
	
	$.ajax({
		type:"POST",
		url:"/gis/traffic/passList.do",
		dataType:'json',
		async:true,
		data: passParams,
		success:function(data){
			//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
			if(data.result != null && data.result.length > 0){
				//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
				$('#t'+id).show();
				$('#li'+id).trigger('click');				
				settingGridData(gridId, data.result); //grid data
				//차트
				setPassChartInfo(data.result, id);				
				//화면표시
				var lndParams = setPassGeom(id, mapType, data.result);
				//엑셀 조회용 params저장
				setGridParams(gridId, passParams, lndParams); //grid_chart.js
			}else{					
				clearResultTab(id, "2-7");
				$('#spn'+id).html("통행패턴 분석");
				openInfoPopup(true,"검색결과정보가 없습니다.");
			}				
		},
		error:function(err){
			if(chkSession(err.status)){
				clearResultTab(id, "2-7");
				$('#spn'+id).html("통행패턴 분석");
				openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
			}
		}
	});
}

/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// 공통 /// //////////////////////////////
/////////////////////////////////////////////////////////////////////////////

//geometry 구성
function setPassGeom(id, mapType, obj){	
	var mapObj = getMapObj(mapType);
	var datas = getAnalsPassParams(id, mapType);
	var layer = setHighlightLayer(datas, true, false);
	var coord = mapObj.map.getProjection().replace("EPSG:", '');
	var stopMap = new Map(); //정류장 중복 구분 map	
	var stkColor = 'blue';
	var lndParams = {};
	if(obj.length > 0){		
		var max = obj[0].max;
		var min = obj[0].min;		
		//범례정보 표시
		lndParams.max = max;
		lndParams.min = min;
		lndParams.vtype = 'pass';
		lndParams.vcolor = stkColor;		
		showLegendInfo(lndParams); //common.js
		$('#spn'+id).html(obj[0].snm + " 통행패턴 분석"); //타이틀 설정
		
		for(var i=0; i<obj.length; i++){
			var geom = new OpenLayers.Format.WKT().read(obj[i].bline);
			var pop = obj[i].pop;
			var size = 10;
			var div = ( (max - min) / size);
			var grade = 1;
			for(var j=1; j<=size; j++){
				if(Number(pop) < (min+(div*j))){
					grade = j;
					break;
				}else if(Number(pop) >= max){
					grade = size;
					break;
				}	
			}
			geom.attributes = {coord: coord, 
					name: '', 
					label : '', 
					stkStyle: "solid", 
					radius : 0, stkColor: stkColor, stkWidth:grade, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
			layer.addFeatures([geom]);	
			
			//2. 정류장 그리기
			var stop = stopMap.get(obj[i].sid);
			if(stop == undefined || stop != null){								
				stopMap.put(obj[i].sid, obj[i].sid);
				geom = new OpenLayers.Format.WKT().read(obj[i].sp);
				geom.attributes = {coord: coord, fontSize:10, 
						name: obj[i].snm, label:obj[i].snm,
						xOffset:6, yOffset:-05,align:'lm',						
						stkStyle:"solid", radius : 6, 
						stkColor: 'red', stkWidth: 1, 
						fllColor:'#FFDD73', fllOpacity:0.5, fllWidth:2};
				layer.addFeatures([geom]);
			}
			stop = stopMap.get(obj[i].fid);
			if(stop == undefined || stop != null){								
				stopMap.put(obj[i].fid, obj[i].fid);
				geom = new OpenLayers.Format.WKT().read(obj[i].fp);
				//var fstop = obj[i].fnm + " ("+obj[i].pop+")";
				geom.attributes = {coord: coord, fontSize:9, name: '', label:obj[i].fnm, stkStyle:"solid", radius : 5, stkColor: stkColor, stkWidth: 1, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
				layer.addFeatures([geom]);
			}
		}	
		mapObj.map.zoomToExtent(layer.getDataExtent());
	}	
	return lndParams;
}

//차트구성
function setPassChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();
	
	var chartData =[];	
	var tmpStaChart = []; //승차 결과
	var labels = [];
	if(datas.length > 0){
		//중복 x축 라벨명 체크		
		var chkNameCnt = 0;
		var nmMap = new Map(); //라벨명 같을 경우 차트표시 에러
		
		var prev = datas[0].fid; //정류장명
		labels.push("정류장");
		for(var i=0; i<datas.length; i++){
			var data = datas[i];			
			if(prev != data.fid){
				prev = data.fid;
				labels.push("정류장");
				var name = resizeName(data.fnm, nmMap, chkNameCnt);
				var arrName = name.split("@");
				chkNameCnt = Number(arrName[1]);
				tmpStaChart.push([arrName[0], data.pop]);
			}else{				
				var name = resizeName(data.fnm, nmMap, chkNameCnt);
				var arrName = name.split("@");
				chkNameCnt = Number(arrName[1]);
				tmpStaChart.push([arrName[0], data.pop]);
			}
			if(i == (datas.length - 1)){
				chartData.push(tmpStaChart);
				tmpInChart = [];
				continue;				
			}
		}
		settingLineChartData(chartId, chartData, labels, '정류장명', '승객수(명)', 60);
	}
}

/**
 * 명칭 사이즈 조절
 * @param name
 * @returns {String}
 */
function resizeName(name, nmMap, chkNameCnt){
	/**
	 * 1. 동일한 x라벨명이 있을경우 차트가 중복으로 그려지는 문제발생 (라벨명을 체크하여 같은경우 (1)..을 붙여줘 중복 문제해결
	 * 추후 라벨 중복가능한 옵션있을경우 하단의 로직 변경가능
	 */	
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
	return name + "@" + chkNameCnt;
}

//param설정
function getAnalsPassParams(id, mapType){	 
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