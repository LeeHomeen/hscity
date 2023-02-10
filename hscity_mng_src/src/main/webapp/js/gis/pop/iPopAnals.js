/**
*
* 인구/매출 분석 관련 js (유입인구)
*
**/

/**
 * 페이지 초기화
 */
function initIPopPageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;
	searchJspPage("/gis/area.do", {flag:flag}, false, "divIPopArea"); //공통 영역지정 페이지 호출
	
	setBasicMonth('txtMonthStart');  //기본 기준년월일 세팅
	setBasicMonth('txtMonthEnd');    //기본 기준년월일 세팅
	setBasicDay('txtDayStart');     //시작 기준일자 세팅
	setBasicDay('txtDayEnd');       //종표 기준일자 세팅
	
	setCalendarMonth('txtMonthStart'); //common.js
	setCalendarMonth('txtMonthEnd'); //common.js
	setCalendarDay('txtDayStart'); //common.js
	setCalendarDay('txtDayEnd'); //common.js
	
	//grid 세팅	
	var options = {};
	options.height = $('.wp70').height() - 35;
	options.num = 10000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "1-3-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	//chart 세팅 divChart1-3-1
	options.chartId = "divChart"+chkFlag;
	options.chartData = [[null]];
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
	
	/**
	 * 시군구
	 */
	chkFlag = "1-3-2";	
	initGridInfo(chkFlag, options, flag);
	//차트 초기화	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
	
	/**
	 * 성연령별
	 */
	chkFlag = "1-3-3";	
	initGridInfo(chkFlag, options, flag);
	//차트 초기화	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initLineChartResult(options);
	
	//처음탭으로 이동
	$('#li1-3-1').trigger('click');
	showMapResult(false);
	
	getTablePeriod("1-3-1"); //기간설정
}

/////////////////////////////////////////////////////////////////////////////
////////////////////////////////시도별 & 시군구별 분석///////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function setIPopAnals(url, id, mapType){
	//시걔열분석, 읍면동 선택 수 체크, 기간설정
	if(chkMultiAreaCnt(1) && chkNumValid("1-3")){
		clearLegendInfo(); //범례 초기화 common.js
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
			 var datas = getAnalsInPopParams(url, mapObj, id);			 
			 var iPopParams = {
				atype:datas.params.atype,
				area: datas.params.area,
				dongcd: datas.params.dongcd,
				smonth:datas.params.smonth,
				emonth:datas.params.emonth,
				sday: datas.params.sday,
				eday: datas.params.eday,
				std: datas.params.std,				
				range: datas.params.range,
				coord: datas.params.coord,
				gubun: datas.params.gubun
			};
			 
			 $.ajax({
				type:"POST",
				url:datas.params.url,
				dataType:'json',
				async:true,
				data: iPopParams,
				async: true,
				success:function(data){
					if(data.cnt != null && data.cnt.length > 1){
						//결과 화면 보기
						$('#t'+id).show();
						$('#li'+id).trigger('click');						
						settingGridData(gridResultId, data.cnt); //grid data
						//차트
						setInPopChartInfo(data.cnt, id);
						//엑셀 조회용 params저장
						setGridParams(gridResultId, iPopParams, null);
						//wfs layer 생성
						mapObj.addAnalsVtLayer(datas, data.geom);
					}else{
						clearResultTab(id, "1-3");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						clearResultTab(id, "1-3");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}
				}
			});
		}else{
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}

/////////////////////////////////////////////////////////////////////////////
////////////////////////////////성연령별 분석 ////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function setIPopAgeAnals(url, id, mapType){
	//시걔열분석, 읍면동 선택 수 체크, 기간설정
	if( chkMultiAreaCnt(1) && chkNumValid("1-3")){
		clearLegendInfo(); //범례 초기화 common.js
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
			var age = getChkItems('chkIPopAge');
			 var datas = getAnalsInPopParams(url, mapObj, id);			 
			 var iPopParams = {
				atype:datas.params.atype,
				area: datas.params.area,
				dongcd: datas.params.dongcd,
				smonth:datas.params.smonth,
				emonth:datas.params.emonth,
				sday: datas.params.sday,
				eday: datas.params.eday,
				std: datas.params.std,				
				range: datas.params.range,
				coord: datas.params.coord,
				gubun: datas.params.gubun,
				age: age
			};
			 
			 $.ajax({
				type:"POST",
				url:datas.params.url,
				dataType:'json',
				async:true,
				data: iPopParams,
				async: true,
				success:function(data){
					if(data.cnt != null && data.cnt.length > 1){
						//결과 화면 보기
						$('#t'+id).show();
						$('#li'+id).trigger('click');						
						settingGridData(gridResultId, data.cnt); //grid data						
						//차트
						setInPopGAChartInfo(data.cnt, id);
						//엑셀 조회용 params저장
						setGridParams(gridResultId, iPopParams, null);
						//wfs layer 생성
						mapObj.addAnalsVtLayer(datas, data.geom);						
					}else{
						clearResultTab(id, "1-3");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						clearResultTab(id, "1-3");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}
				}
			});
		}else{
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}


/////////////////////////////////////////////////////////////////////////////
////////////////////////////////공통 //////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

//params구성
function getAnalsInPopParams(url, mapObj, id){
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
			 sday : $('#txtDayStart').val(),
			 range  : $('#selRange'+id).val(),
			 eday : $('#txtDayEnd').val(),
			 std  : getStdInfo("1-3"), //기준년월 또는 기준일자 정보 가져오기
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

//chart표시 

//차트 정보 제공
function setInPopChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();	
	var chartData =[];
	var tmpChart = [];
	var labels = [];
	if(datas.length > 0){
		for(var i=0; i<datas.length; i++){
			if(i == 30)
				break;
			var item = datas[i].div;
			var tot = datas[i].tot;
			if(item == '전체' || item == '합계' || item == null)
				continue;
			var arrItem = item.split(" ");
			if(arrItem.length > 0){
				item = arrItem[arrItem.length-1];
			}
			if(id == '1-3-2')
				item = replaceSigName(item); //시군구명칭 간략명칭으로 변경
			labels.push(item);
			tmpChart.push([item, tot]);
			//item value 명칭 줄이기
		}
		if(tmpChart.length > 0)
			chartData.push(tmpChart);
		else
			chartData = [[null]];
		
		if(id == '1-3-1')
			settingBarChartData(chartId, chartData, labels, '시도', '유입인구(명)');
		else if(id == '1-3-2')
			settingBarChartData(chartId, chartData, labels, '시군구', '유입인구(명)');
	}
}

/**
 * 차트정보 제공 (성연령별)
 *  
*/
function setInPopGAChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();
	
	var chartData =[];	
	var tmpMChart = []; //남자 결과
	var tmpWChart = []; //여자 결과
	var labels = [];
	if(datas.length > 0){
		var prev = datas[0].item;
		labels.push(datas[0].div + " 남자");
		labels.push(datas[0].div + " 여자");
		for(var i=0; i<datas.length; i++){
			var data = datas[i];
			
			if(prev != data.item){		
				chartData.push(tmpMChart);
				chartData.push(tmpWChart);
				tmpMChart = [];
				tmpWChart = [];
				
				if(data.item != '합계'){
					prev = data.item;
					labels.push(data.div + " 남자");
					labels.push(data.div + " 여자");
					
					if(data.m10 == -999) tmpMChart.push(['10대', 0]);
					else tmpMChart.push(['10대', data.m10]);					
					if(data.m20 == -999) tmpMChart.push(['20대', 0]);
					else tmpMChart.push(['20대', data.m20]);					
					if(data.m30 == -999) tmpMChart.push(['30대', 0]);
					else tmpMChart.push(['30대', data.m30]);					
					if(data.m40 == -999) tmpMChart.push(['40대', 0]);
					else tmpMChart.push(['40대', data.m40]);
					if(data.m50 == -999) tmpMChart.push(['50대', 0]);
					else tmpMChart.push(['50대', data.m40]);
					if(data.m60 == -999) tmpMChart.push(['60대', 0]);
					else tmpMChart.push(['60대', data.m60]);
					if(data.m70 == -999) tmpMChart.push(['70대', 0]);
					else tmpMChart.push(['70대이상', data.m70]);
					
					if(data.w10 == -999) tmpWChart.push(['10대', 0]);
					else tmpMChart.push(['10대', data.w10]);					
					if(data.w20 == -999) tmpWChart.push(['20대', 0]);
					else tmpWChart.push(['20대', data.w20]);					
					if(data.w30 == -999) tmpWChart.push(['30대', 0]);
					else tmpWChart.push(['30대', data.w30]);					
					if(data.w40 == -999) tmpWChart.push(['40대', 0]);
					else tmpWChart.push(['40대', data.w40]);
					if(data.w50 == -999) tmpWChart.push(['50대', 0]);
					else tmpWChart.push(['50대', data.w40]);
					if(data.w60 == -999) tmpWChart.push(['60대', 0]);
					else tmpWChart.push(['60대', data.w60]);
					if(data.w70 == -999) tmpWChart.push(['70대', 0]);
					else tmpWChart.push(['70대이상', data.w70]);
				}else
					break;				
			}else{
				if(data.m10 == -999) tmpMChart.push(['10대', 0]);
				else tmpMChart.push(['10대', data.m10]);					
				if(data.m20 == -999) tmpMChart.push(['20대', 0]);
				else tmpMChart.push(['20대', data.m20]);					
				if(data.m30 == -999) tmpMChart.push(['30대', 0]);
				else tmpMChart.push(['30대', data.m30]);					
				if(data.m40 == -999) tmpMChart.push(['40대', 0]);
				else tmpMChart.push(['40대', data.m40]);
				if(data.m50 == -999) tmpMChart.push(['50대', 0]);
				else tmpMChart.push(['50대', data.m40]);
				if(data.m60 == -999) tmpMChart.push(['60대', 0]);
				else tmpMChart.push(['60대', data.m60]);
				if(data.m70 == -999) tmpMChart.push(['70대', 0]);
				else tmpMChart.push(['70대이상', data.m70]);
				
				if(data.w10 == -999) tmpWChart.push(['10대', 0]);
				else tmpWChart.push(['10대', data.w10]);					
				if(data.w20 == -999) tmpWChart.push(['20대', 0]);
				else tmpWChart.push(['20대', data.w20]);					
				if(data.w30 == -999) tmpWChart.push(['30대', 0]);
				else tmpWChart.push(['30대', data.w30]);					
				if(data.w40 == -999) tmpWChart.push(['40대', 0]);
				else tmpWChart.push(['40대', data.w40]);
				if(data.w50 == -999) tmpWChart.push(['50대', 0]);
				else tmpWChart.push(['50대', data.w40]);
				if(data.w60 == -999) tmpWChart.push(['60대', 0]);
				else tmpWChart.push(['60대', data.w60]);
				if(data.w70 == -999) tmpWChart.push(['70대', 0]);
				else tmpWChart.push(['70대이상', data.w70]);
			}
		}
		settingLineChartData(chartId, chartData, labels, '연령', '인구수(명)');
	}
}
