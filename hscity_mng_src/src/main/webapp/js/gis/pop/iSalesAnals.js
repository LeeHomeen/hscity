/**
*
* 인구/매출 분석 관련 js (유입매출)
* 
*
**/

/**
 * 페이지 초기화
 */
function initISalePageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;
	searchJspPage("/gis/area.do", {flag:flag}, false, "divPopArea"); //공통 영역지정 페이지 호출
	
	setBasicMonth('txtMonthStart');  //기본 기준년월일 세팅
	setBasicMonth('txtMonthEnd');    //기본 기준년월일 세팅
	setCalendarMonth('txtMonthStart'); //common.js
	setCalendarMonth('txtMonthEnd'); //common.js
	
	//1-5-1 그리드 , 차트 적용
	var options = {};
	options.height = $('.wp30').height() - 35;
	options.num = 10000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "1-5-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	//chart 세팅 divChart1-1-1
	options.chartId = "divChart"+chkFlag;
	options.chartData = [[null]];
	$('#li'+chkFlag).trigger('click'); //chart기본 세팅하기 위해 클릭이벤트 효과 (임시처리)
	initBarChartResult(options);
	
	//1-5-2 그리드 , 차트 적용
	chkFlag = "1-5-2";	
	initGridInfo(chkFlag, options, flag);
	//차트 초기화	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
	
	//1-5-3 그리드 , 차트 적용
	chkFlag = "1-5-3";	
	initGridInfo(chkFlag, options, flag);
	//차트 초기화	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
		
	$('#li1-5-1').trigger('click');
	showMapResult(false);
	
	getTablePeriod("1-5-1"); //기간설정
}

//그리드 생성
function initGridInfo(chkFlag, options, flag){	
	options.paging = "gridPaging"+chkFlag;
	options.gridId = "tblGrid"+chkFlag;	
	options.cols = gridCols[0]["cols"+chkFlag];
	options.mapType = flag;
	initGridResult(options);
}

////////////////////////////////////////////////////////////////////////
/////////////////////////////////시도별 유입매출//////////////////////////////
////////////////////////////////////////////////////////////////////////

function salesInfluxAnals(url, id, mapType){
	var start = $('#txtMonthStart').val();			 
	var end = $('#txtMonthEnd').val();
	 //common.js 시계열 분석 체크 및 읍면동 수 체크, 기간설정
	if(chkTsAnals(mapType) && chkMultiAreaCnt(1) && chkNumSeq(start, end, 0)){
		clearLegendInfo(); //범례 초기화 common.js
		$('#plbl'+id).html('');
		openTsBtn(false, false, id);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var mapObj = getMapObj(mapType);	
		//추가 필요 layer 클리어
		mapObj.clearRemoveLayer("popLayer"+id); 
		var chk = mapObj.mapCustom.mapAnalsLocation(mapType); //분석위치로 이동	
		if(chk){
			var gridResultId = "tblGrid" + id;	//지역분석 그리드 아이디		
			$('#'+gridResultId).jqGrid('clearGridData');	//grid 클리어
			$('#'+"divChart" + id).html(""); //차트 클리어
			
			$('#divMapLoading').show();
			
			 //분석 vt
			 var datas = getAnalsInfluxParams(url, mapObj, id);
			 var isaleParams = {
					atype:datas.params.atype,
					area: datas.params.area,
					dongcd: datas.params.dongcd,
					smonth:datas.params.smonth,
					emonth:datas.params.emonth,				
					range: datas.params.range,
					coord: datas.params.coord,
					gubun: datas.params.gubun
				};
			 $.ajax({
				type:"POST",
				url:datas.params.url,
				dataType:'json',
				async:true,
				data: isaleParams,
				async: true,
				success:function(data){
					if(data.cnt != null && data.cnt.length > 1){
						//결과 화면 보기
						$('#t'+id).show();
						$('#li'+id).trigger('click');						
						settingGridData(gridResultId, data.cnt); //grid data
						//차트
						setInfluxChartInfo(data.cnt, id);
						//엑셀 조회용 params저장
						setGridParams(gridResultId, isaleParams, null);
						//wfs layer 생성
						mapObj.addAnalsVtLayer(datas, data.geom);						
					}else{
						clearResultTab(id, "1-5");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						clearResultTab(id, "1-5");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}					
				}
			});
		}else{
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}

////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////

//차트 정보 제공
function setInfluxChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();	
	var chartData =[];
	var tmpChart = [];
	var labels = [];
	if(datas.length > 0){
		for(var i=0; i<datas.length; i++){
			if(i == 30)
				break;
			var item = datas[i].item;
			var cnt = datas[i].cnt;
			if(item == '전체' || item == '합계' || item == null)
				continue;
			var arrItem = item.split(" ");
			if(arrItem.length > 0){
				item = arrItem[arrItem.length-1];
			}
			if(id == '1-5-2') //시군구조회
				item = replaceSigName(item); //시군구명칭 간략명칭으로 교체
			labels.push(item);
			tmpChart.push([item,cnt]);
			//item value 명칭 줄이기
		}
		if(tmpChart.length > 0)
			chartData.push(tmpChart);
		else
			chartData = [[null]];
		
		if(id == '1-5-1') //시도
			settingBarChartData(chartId, chartData, labels, '시도', '유입매출(원)');
		else if(id == '1-5-2') //시군구
			settingBarChartData(chartId, chartData, labels, '시군구', '유입매출(원)');
		else if(id == '1-5-3') //화성시내
			settingBarChartData(chartId, chartData, labels, '읍면동', '유입매출(원)');
	}
}

//전입, 전출  param 생성
function getAnalsInfluxParams(url, mapObj, id){	 
	 var analsName = getMenuName(id); //메뉴 분석한글명 가져오기
	 var atype = cmmAreaType(); //분석영역 : area, user
	 var area = mapObj.mapCustom.getUserArea();
	 //area 좌표변환
	 if(area != null){
		 var geom = new OpenLayers.Format.WKT().read(area);
		 geom.geometry.transform(mapObj.map.getProjection(), mapObj.epsg4326);
		 area = new OpenLayers.Format.WKT().write(geom);
	 }
	 var colpck = rgb2hex($('#colpck'+id+' div').css('backgroundColor'));
	 var datas = {
		mapType : mapObj.mapType,
	    params  : {	 
	    	 url :  url,
	    	 aid :  id, 
	    	 coord  : mapObj.map.getProjection().replace("EPSG:", ''),
			 dongcd : multiAreaValue(),//$('#selDong').val(), //'4159056000,4159026200',
			 atype  : atype, //분석영역 설정 (area, user)
			 area   : area,    //사용자지정영역 geometry
			 smonth : $('#txtMonthStart').val(), //기준년월			 
			 emonth : $('#txtMonthEnd').val(),
			 visible : true, //reload시 필요
			 opacity : 1,     //reload시 필요
			 gubun : $("#selStd").val(),//$("#selStd option:selected").text()
			 colpck : colpck  //표현될 색상 지정
	 	},
	 	layerName : "popLayer"+id,
	 	analsName : analsName,
	 	analsUrl  : url
	 };
	 return datas;
}
