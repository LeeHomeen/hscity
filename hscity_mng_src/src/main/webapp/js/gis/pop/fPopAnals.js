/**
*
* 인구/매출 분석 관련 js (유동인구)
*
**/

/**
 * 페이지 초기화
 */
function initFPopPageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;		
	searchJspPage("/gis/area.do", {flag:flag}, false, "divFPopArea"); //공통 영역지정 페이지 호출
	
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
	options.height = $('.wp50').height() - 35;
	options.num = 10000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "1-2-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	//chart 세팅 divChart1-2-1
	options.chartId = "divChart"+chkFlag;
	options.chartData = [[null]];
	$('#li'+chkFlag).trigger('click');
	initLineChartResult(options);
	
	/**
	 * 그리드 성/연령별
	 */
	chkFlag = "1-2-2";	
	initGridInfo(chkFlag, options, flag);
	//차트 초기화	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initLineChartResult(options);
	
	/**
	 * 그리드 시간대별
	 */
	chkFlag = "1-2-3";
	initGridInfo(chkFlag, options, flag);
	//차트 초기화
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initLineChartResult(options);
	
	$('#li1-2-1').trigger('click');
	showMapResult(false);
	
	getTablePeriod("1-2-1"); //기간설정
}

/////////////////////////////////////////////////////////////////////////////
////////////////////////////////기간별 분석 //////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function fPopAnalsPeriod(id, mapType){
	//common.js 시계열 및 읍면동 수 체크, 기간설정
	//2019.03.29 bskim 수정 행정동 2개이상으로 처리
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26) && chkNumValid("1-2")){
		clearLegendInfo(); //범례 초기화 common.js
		$('#plbl'+id).html('');	
		
		openTsBtn(true, false, id);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var mapObj = getMapObj(mapType);	
		mapObj.clearRemoveLayer("popLayer"+id); //wms layer 초기화
		var chk = mapObj.mapCustom.mapAnalsLocation(mapType); //분석위치로 이동	
		if(chk){
			var gridId = "tblGrid" + id;				
			$('#'+gridId).jqGrid('clearGridData');	//grid 클리어	
			$('#'+"divChart" + id).html(""); //차트 클리어
			
			$('#divMapLoading').show();			
			var datas = getAnalsWMSParams(mapObj, id);
			var fPopParams = {
				atype : datas.params.atype,
				area  : datas.params.area,
				dongcd: datas.params.dongcd,
				std   : datas.params.std,
				smonth: datas.params.smonth,
				emonth: datas.params.emonth,
				sday  : datas.params.sday,
				eday  : datas.params.eday,
				gubun: datas.params.gubun
			};
			
			$.ajax({
				type:"POST",
				url:"/gis/fpop/fPopYearList.do",
				dataType:'json',
				async:true,
				data: fPopParams,
				async: true,
				success:function(data){					
					if(data.result != null && data.result.length > 1){
						//합계만 나올수 있음
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data						
																			
						datas.params.gyear = data.result[0].item;
						fPopParams.gyear = datas.params.gyear;
						fPopParams.vtype = datas.params.vtype;
						
						var lndParams = {};
						if(fPopParams.vtype != 'heat'){
							//GRID, BEEHIVE
							var legendInfo = getLegendInfo(legendUrl.get(id), fPopParams); //범례표시 정보 (max, min)
							if(legendInfo != undefined && legendInfo != null){
								datas.params.max = legendInfo[0].max;
								datas.params.min = legendInfo[0].min;
								addAnalsWmsLayer(datas);
								//범례정보 표시
								lndParams.max = datas.params.max;
								lndParams.min = datas.params.min;
								lndParams.vtype = fPopParams.vtype;
								lndParams.vcolor = datas.params.vcolor;
								showLegendInfo(lndParams); //common.js
							}
						}else{
							//heat map
							addAnalsWmsLayer(datas);
							//범례정보 표시							
							lndParams.max = 0;
							lndParams.min = 0;
							lndParams.vtype = fPopParams.vtype;
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
						}
						//엑셀 조회용 params저장
						setGridParams(gridId, fPopParams, lndParams); //grid_chart.js
						var year =  datas.params.gyear.substring(0,4);
						var month = datas.params.gyear.substring(4,6);	
						var day = '';
						if(fPopParams.std == 'day'){
							day = datas.params.gyear.substring(6,8);
							$('#plbl'+id).html("<b>* 데이터 : "+year+"년 "+month+"월 "+day+"일</b>&nbsp;&nbsp;");
						}else
							$('#plbl'+id).html("<b>* 데이터 : "+year+"년 "+month+"월</b>&nbsp;&nbsp;");
						//차트			
						setFPopChartInfo(data.result, id);						
					}else{
						$('#plbl'+id).html("");
						clearResultTab(id, "1-2");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html("");
						clearResultTab(id, "1-2");
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

function fPopAnalsAge(id, mapType){
	//common.js 시계열 및 읍면동 수 체크, 기간설정
	//2019.03.29 bskim 수정 행정동 2개이상으로 처리
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26) && chkNumValid("1-2")){
		clearLegendInfo(); //범례 초기화 common.js
		$('#plbl'+id).html('');	
		
		openTsBtn(false, false, id);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var mapObj = getMapObj(mapType);	
		mapObj.clearRemoveLayer("popLayer"+id); //wms layer 초기화
		var chk = mapObj.mapCustom.mapAnalsLocation(mapType); //분석위치로 이동	
		if(chk){
			var gridId = "tblGrid" + id;				
			$('#'+gridId).jqGrid('clearGridData');	//grid 클리어	
			$('#'+"divChart" + id).html(""); //차트 클리어
			
			$('#divMapLoading').show();
			//time정보 가져오기
			var age = getChkItems('chkFPopAge');
			var datas = getAnalsWMSParams(mapObj, id);
			var fPopParams = {
				atype : datas.params.atype,
				area  : datas.params.area,
				dongcd: datas.params.dongcd,
				std   : datas.params.std,
				smonth: datas.params.smonth,
				emonth: datas.params.emonth,
				sday  : datas.params.sday,
				eday  : datas.params.eday,
				gubun: datas.params.gubun,
				age : age
			};
			
			$.ajax({
				type:"POST",
				url:"/gis/fpop/fPopGAList.do",
				dataType:'json',
				async:true,
				data: fPopParams,
				async: true,
				success:function(data){
					if(data.result != null && data.result.length > 1){
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data
						//합계만 나올수 있음						
						datas.params.age = age;
						fPopParams.vtype = datas.params.vtype;
						var lndParams = {};
						if(fPopParams.vtype != 'heat'){
							//GRID, BEEHIVE
							var legendInfo = getLegendInfo("/gis/fpop/fPopGALegend.do", fPopParams); //범례표시 정보 (max, min)
							if(legendInfo != undefined && legendInfo != null){
								datas.params.max = legendInfo[0].max;
								datas.params.min = legendInfo[0].min;
								addAnalsWmsLayer(datas);
								//범례정보 표시
								lndParams.max = datas.params.max;
								lndParams.min = datas.params.min;
								lndParams.vtype = fPopParams.vtype;
								lndParams.vcolor = datas.params.vcolor;
								showLegendInfo(lndParams); //common.js
							}
						}else{
							//heat map
							addAnalsWmsLayer(datas);
							//범례정보 표시							
							lndParams.max = 0;
							lndParams.min = 0;
							lndParams.vtype = fPopParams.vtype;
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
						}
						//엑셀 조회용 params저장
						setGridParams(gridId, fPopParams, lndParams); //grid_chart.js							
						//차트						
						setFPopGAChartInfo(data.result, id);						
					}else{
						$('#plbl'+id).html("");
						clearResultTab(id, "1-2");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html("");
						clearResultTab(id, "1-2");
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
////////////////////////////////시간대별 분석 ////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function fPopAnalsTime(id, mapType){
	//common.js 시계열 및 읍면동 수 체크, 기간설정
	//2019.03.29 bskim 수정 행정동 2개이상으로 처리
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26) && chkNumValid("1-2")){
		clearLegendInfo(); //범례 초기화 common.js
		$('#plbl'+id).html('');	
		
		openTsBtn(false, false, id);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var mapObj = getMapObj(mapType);	
		mapObj.clearRemoveLayer("popLayer"+id); //wms layer 초기화
		var chk = mapObj.mapCustom.mapAnalsLocation(mapType); //분석위치로 이동	
		if(chk){
			var gridId = "tblGrid" + id;				
			$('#'+gridId).jqGrid('clearGridData');	//grid 클리어	
			$('#'+"divChart" + id).html(""); //차트 클리어
			
			$('#divMapLoading').show();
			//time정보 가져오기
			var time = getChkItems('chkFPopTime');
			var datas = getAnalsWMSParams(mapObj, id);
			var fPopParams = {
				atype : datas.params.atype,
				area  : datas.params.area,
				dongcd: datas.params.dongcd,
				std   : datas.params.std,
				smonth: datas.params.smonth,
				emonth: datas.params.emonth,
				sday  : datas.params.sday,
				eday  : datas.params.eday,
				gubun: datas.params.gubun,
				time : time
			};
			
			$.ajax({
				type:"POST",
				url:"/gis/fpop/fPopTimeList.do",
				dataType:'json',
				async:true,
				data: fPopParams,
				async: true,
				success:function(data){
					if(data.result != null && data.result.length > 1){
						//합계만 나올수 있음
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data						
						
						datas.params.time = time;
						fPopParams.vtype = datas.params.vtype;
						var lndParams = {};
						if(fPopParams.vtype != 'heat'){
							//GRID, BEEHIVE
							var legendInfo = getLegendInfo("/gis/fpop/fPopTimeLegend.do", fPopParams); //범례표시 정보 (max, min)
							if(legendInfo != undefined && legendInfo != null){
								datas.params.max = legendInfo[0].max;
								datas.params.min = legendInfo[0].min;
								addAnalsWmsLayer(datas);
								//범례정보 표시
								lndParams.max = datas.params.max;
								lndParams.min = datas.params.min;
								lndParams.vtype = fPopParams.vtype;
								lndParams.vcolor = datas.params.vcolor;
								showLegendInfo(lndParams); //common.js
							}
						}else{
							//heat map
							addAnalsWmsLayer(datas);
							//범례정보 표시							
							lndParams.max = 0;
							lndParams.min = 0;
							lndParams.vtype = fPopParams.vtype;
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
						}
						//엑셀 조회용 params저장
						setGridParams(gridId, fPopParams, lndParams); //grid_chart.js							
						//차트					
						setFPopTimeChartInfo(data.result, id);
					}else{
						$('#plbl'+id).html("");
						clearResultTab(id, "1-2");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html("");
						clearResultTab(id, "1-2");
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
////////////////////////////////공통 ///// ////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

//param 가져오기 (기간별/성연령별)
function getAnalsWMSParams(mapObj, id){
	 var url = '';
	 if(id == '1-2-1')
		 url = "/gis/fpop/fPopYearAnals.do"; //유동인구분석_기간별
	 else if(id == '1-2-2')
		 url = "/gis/fpop/fPopGAAnals.do";   //유동인구분석_성연령별
	 else if(id == '1-2-3')
		 url = "/gis/fpop/fPopTimeAnals.do"; //유동인구분석_시간대별
	 var analsName = getMenuName(id); //메뉴 분석한글명 가져오기
	 var atype = cmmAreaType(); //분석영역 : area, user
	 var area = mapObj.mapCustom.getUserArea();
	 //area 좌표변환
	 if(area != null){
		 var geom = new OpenLayers.Format.WKT().read(area);
		 geom.geometry.transform(mapObj.map.getProjection(), mapObj.epsg4326);
		 area = new OpenLayers.Format.WKT().write(geom);
	 }	  
	//params생성 ( 추가시 : mergeParams : map.js 수정 )
	 var datas = {
		mapType : mapObj.mapType,
	    params  : {
	    	 aid    : id,
			 dongcd : multiAreaValue(), //$('#selDong').val(), //'4159056000,4159026200',
			 atype  : atype, //분석영역 설정 (area, user)
			 area   : area,    //사용자지정영역 geometry
			 vtype  : $('#selType'+id).val(), //분석/시각화 (heat, grid, beehive)
			 vcolor : $('#selColor'+id).val(),  //빨 녹 파			 
			 std : getStdInfo("1-2"), //기준년월 또는 기준일자 정보 가져오기
			 smonth : $('#txtMonthStart').val(), //기준년월			 
			 emonth : $('#txtMonthEnd').val(),
			 sday : $('#txtDayStart').val(),
			 eday : $('#txtDayEnd').val(),
			 gubun : $("#selStd").val()
	 	},
	 	layerName : "popLayer"+id,
	 	analsName : analsName,
	 	analsUrl  : url
	 };
	 return datas;
}


/**
 * 차트 정보 제공 (기간별)
 */
function setFPopChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();
	
	var chartData =[];
	var tmpChart = [];
	var labels = [];
	if(datas.length > 0){
		var prev = datas[0].div;
		labels.push(prev);
		for(var i=0; i<datas.length; i++){
			var data = datas[i];
			if(data.item == '합계'){
				if(data.div != '전체') //최종합계가 아니면 저장
					chartData.push(tmpChart);
				continue;				
			}
			if(prev != data.div){
				//chartData.push(tmpChart);				
				tmpChart = [];
				prev = data.div;
				labels.push(prev);
				tmpChart.push([(data.item+''), data.tot]);
			}else{
				tmpChart.push([(data.item+''), data.tot]);				
			}
		}
		settingLineChartData(chartId, chartData, labels, '기준년월', '인구수(명)');
	}
}

/**
 * 차트정보 제공 (성연령별)
 *  
*/
function setFPopGAChartInfo(datas, id){
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
					else tmpMChart.push(['50대', data.m50]);
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
					else tmpWChart.push(['50대', data.w50]);
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
				else tmpMChart.push(['50대', data.m50]);
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
				else tmpWChart.push(['50대', data.w50]);
				if(data.w60 == -999) tmpWChart.push(['60대', 0]);
				else tmpWChart.push(['60대', data.w60]);
				if(data.w70 == -999) tmpWChart.push(['70대', 0]);
				else tmpWChart.push(['70대이상', data.w70]);
			}
		}
		settingLineChartData(chartId, chartData, labels, '연령', '인구수(명)');
	}
}


/**
 * 차트정보 제공 (시간대별)
 *  
*/
function setFPopTimeChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();
	
	var chartData =[];	
	var tmpTChart = []; // 시간대
	var labels = [];
	if(datas.length > 0){
		var prev = datas[0].item;
		labels.push(datas[0].div);
		for(var i=0; i<datas.length; i++){
			var data = datas[i];
			
			if(prev != data.item){		
				chartData.push(tmpTChart);
				tmpTChart = [];
				
				if(data.item != '합계'){
					prev = data.item;
					labels.push(data.div);
					
					if(data.t00 == -999) tmpTChart.push(['00시', 0]);
					else tmpTChart.push(['00시', data.t00]);					
					if(data.t01 == -999) tmpTChart.push(['01시', 0]);
					else tmpTChart.push(['01시', data.t01]);					
					if(data.t02 == -999) tmpTChart.push(['02시', 0]);
					else tmpTChart.push(['02시', data.t02]);					
					if(data.t03 == -999) tmpTChart.push(['03시', 0]);
					else tmpTChart.push(['03시', data.t03]);
					if(data.t04 == -999) tmpTChart.push(['04시', 0]);
					else tmpTChart.push(['04시', data.t04]);
					if(data.t05 == -999) tmpTChart.push(['05시', 0]);
					else tmpTChart.push(['05시', data.t05]);
					if(data.t06 == -999) tmpTChart.push(['06시', 0]);
					else tmpTChart.push(['06시', data.t06]);					
					if(data.t07 == -999) tmpTChart.push(['07시', 0]);
					else tmpTChart.push(['07시', data.t07]);
					if(data.t08 == -999) tmpTChart.push(['08시', 0]);
					else tmpTChart.push(['08시', data.t08]);
					if(data.t09 == -999) tmpTChart.push(['09시', 0]);
					else tmpTChart.push(['09시', data.t09]);
					if(data.t10 == -999) tmpTChart.push(['10시', 0]);
					else tmpTChart.push(['10시', data.t10]);
					if(data.t11 == -999) tmpTChart.push(['11시', 0]);
					else tmpTChart.push(['11시', data.t11]);
					if(data.t12 == -999) tmpTChart.push(['12시', 0]);
					else tmpTChart.push(['12시', data.t12]);
					if(data.t13 == -999) tmpTChart.push(['13시', 0]);
					else tmpTChart.push(['13시', data.t13]);
					if(data.t14 == -999) tmpTChart.push(['14시', 0]);
					else tmpTChart.push(['14시', data.t14]);
					if(data.t15 == -999) tmpTChart.push(['15시', 0]);
					else tmpTChart.push(['15시', data.t15]);
					if(data.t16 == -999) tmpTChart.push(['16시', 0]);
					else tmpTChart.push(['16시', data.t16]);
					if(data.t17 == -999) tmpTChart.push(['17시', 0]);
					else tmpTChart.push(['17시', data.t17]);
					if(data.t18 == -999) tmpTChart.push(['18시', 0]);
					else tmpTChart.push(['18시', data.t18]);
					if(data.t19 == -999) tmpTChart.push(['19시', 0]);
					else tmpTChart.push(['19시', data.t19]);
					if(data.t20 == -999) tmpTChart.push(['20시', 0]);
					else tmpTChart.push(['20시', data.t20]);
					if(data.t21 == -999) tmpTChart.push(['21시', 0]);
					else tmpTChart.push(['21시', data.t21]);
					if(data.t22 == -999) tmpTChart.push(['22시', 0]);
					else tmpTChart.push(['22시', data.t22]);
					if(data.t23 == -999) tmpTChart.push(['23시', 0]);
					else tmpTChart.push(['23시', data.t23]);					
				}else
					break;				
			}else{
				if(data.t00 == -999) tmpTChart.push(['00시', 0]);
				else tmpTChart.push(['00시', data.t00]);					
				if(data.t01 == -999) tmpTChart.push(['01시', 0]);
				else tmpTChart.push(['01시', data.t01]);					
				if(data.t02 == -999) tmpTChart.push(['02시', 0]);
				else tmpTChart.push(['02시', data.t02]);					
				if(data.t03 == -999) tmpTChart.push(['03시', 0]);
				else tmpTChart.push(['03시', data.t03]);
				if(data.t04 == -999) tmpTChart.push(['04시', 0]);
				else tmpTChart.push(['04시', data.t04]);
				if(data.t05 == -999) tmpTChart.push(['05시', 0]);
				else tmpTChart.push(['05시', data.t05]);
				if(data.t06 == -999) tmpTChart.push(['06시', 0]);
				else tmpTChart.push(['06시', data.t06]);					
				if(data.t07 == -999) tmpTChart.push(['07시', 0]);
				else tmpTChart.push(['07시', data.t07]);
				if(data.t08 == -999) tmpTChart.push(['08시', 0]);
				else tmpTChart.push(['08시', data.t08]);
				if(data.t09 == -999) tmpTChart.push(['09시', 0]);
				else tmpTChart.push(['09시', data.t09]);
				if(data.t10 == -999) tmpTChart.push(['10시', 0]);
				else tmpTChart.push(['10시', data.t10]);
				if(data.t11 == -999) tmpTChart.push(['11시', 0]);
				else tmpTChart.push(['11시', data.t11]);
				if(data.t12 == -999) tmpTChart.push(['12시', 0]);
				else tmpTChart.push(['12시', data.t12]);
				if(data.t13 == -999) tmpTChart.push(['13시', 0]);
				else tmpTChart.push(['13시', data.t13]);
				if(data.t14 == -999) tmpTChart.push(['14시', 0]);
				else tmpTChart.push(['14시', data.t14]);
				if(data.t15 == -999) tmpTChart.push(['15시', 0]);
				else tmpTChart.push(['15시', data.t15]);
				if(data.t16 == -999) tmpTChart.push(['16시', 0]);
				else tmpTChart.push(['16시', data.t16]);
				if(data.t17 == -999) tmpTChart.push(['17시', 0]);
				else tmpTChart.push(['17시', data.t17]);
				if(data.t18 == -999) tmpTChart.push(['18시', 0]);
				else tmpTChart.push(['18시', data.t18]);
				if(data.t19 == -999) tmpTChart.push(['19시', 0]);
				else tmpTChart.push(['19시', data.t19]);
				if(data.t20 == -999) tmpTChart.push(['20시', 0]);
				else tmpTChart.push(['20시', data.t20]);
				if(data.t21 == -999) tmpTChart.push(['21시', 0]);
				else tmpTChart.push(['21시', data.t21]);
				if(data.t22 == -999) tmpTChart.push(['22시', 0]);
				else tmpTChart.push(['22시', data.t22]);
				if(data.t23 == -999) tmpTChart.push(['23시', 0]);
				else tmpTChart.push(['23시', data.t23]);
			}
		}
		settingLineChartData(chartId, chartData, labels, '시간대', '인구수(명)');
	}
}

