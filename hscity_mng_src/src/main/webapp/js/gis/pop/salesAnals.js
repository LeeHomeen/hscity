/**
*
* 인구/매출 분석 관련 js (매출분석)
*
**/

/**
 * 페이지 초기화
 */
function initSalePageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;		
	searchJspPage("/gis/area.do", {flag:flag}, false, "divSalesArea"); //공통 영역지정 페이지 호출
	//외국인분석시 사용
	selectCstDongList("selCstDong", flag); //읍면동 리스트 가져오기	common.js (법정동)
	
	selectLclasList("SelClas1-4-4", true); //업종대분류 가져오기
	//중분류 선택시
	$('#lSelClas1-4-4').change(function(){
		selectMclasList("SelClas1-4-4");
	});
	//소분류 선택시
	$('#mSelClas1-4-4').change(function(){
		selectSclasList("SelClas1-4-4");
	});
	selectLclasList("SelClas1-4-5", false); //업종대분류 가져오기
	
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
	var chkFlag = "1-4-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	//chart 세팅 divChart1-1-1
	options.chartId = "divChart"+chkFlag;
	options.chartData = [[null]];
	$('#li'+chkFlag).trigger('click');
	initLineChartResult(options);
	
	/**
	 * 그리드 성/연령별
	 */
	chkFlag = "1-4-2";	
	initGridInfo(chkFlag, options, flag);
	//차트 초기화	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initLineChartResult(options);
	
	/**
	 * 그리드 시간대별
	 */
	chkFlag = "1-4-3";
	initGridInfo(chkFlag, options, flag);
	//차트 초기화
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initLineChartResult(options);
	
	/**
	 * 그리드 업종별
	 */
	chkFlag = "1-4-4";
	initGridInfo(chkFlag, options, flag);
	//차트 초기화
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
	
	/**
	 * 그리드 외국인
	 */
	chkFlag = "1-4-5";
	initGridInfo(chkFlag, options, flag);
	//차트 초기화
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
	
	//처음탭으로 이동
	$('#li1-4-1').trigger('click');
	showMapResult(false);
	
	getTablePeriod("1-4-1"); //기간설정
}

//그리드 생성
function initGridInfo(chkFlag, options, flag){	
	options.paging = "gridPaging"+chkFlag;
	options.gridId = "tblGrid"+chkFlag;	
	options.cols = gridCols[0]["cols"+chkFlag];
	options.mapType = flag;
	initGridResult(options);
}

/////////////////////////////////////////////////////////////////////////
///////////////////////////////매출분석_기간별분석//////////////////////////////
/////////////////////////////////////////////////////////////////////////

/**
* 매출분석_기간별 분석
*/
function salesAnalsPeriod(id, mapType){
	//common.js 시계열 분석 체크, 읍면동 수 체크, 기간설정
	//2019.03.29 bskim 수정 행정동 2개이상으로 처리
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26) && chkNumValid("1-4")){ 
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
			
			//분석 wms
			 var datas = getAnalsWMSParams(mapObj, id);		 
			 var saleParams = {
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
				url:"/gis/sale/salesYearList.do",
				dataType:'json',
				async:true,
				data: saleParams,
				async: true,
				success:function(data){
					if(data.result != null && data.result.length > 1){
						//합계만 나올수 있음
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data
						
						datas.params.gyear = data.result[0].item;
						saleParams.gyear = datas.params.gyear;
						var lndParams = {};
						var legendInfo = getLegendInfo(legendUrl.get(id), saleParams); //범례표시 정보 (max, min)
						if(legendInfo != undefined && legendInfo != null){
							datas.params.max = legendInfo[0].max;
							datas.params.min = legendInfo[0].min;
							addAnalsWmsLayer(datas);
							//범례정보 표시
							lndParams.max = datas.params.max;
							lndParams.min = datas.params.min;
							lndParams.vtype = 'area';
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
							
						}		
						//엑셀 조회용 params저장
						setGridParams(gridId, saleParams, lndParams); //grid_chart.js
						var year =  datas.params.gyear.substring(0,4);
						var month = datas.params.gyear.substring(4,6);
						var day = '';
						if(saleParams.std == 'day'){
							day = datas.params.gyear.substring(6,8);
							$('#plbl'+id).html("<b>* 데이터 : "+year+"년 "+month+"월 "+day+"일</b>&nbsp;&nbsp;");
						}else
							$('#plbl'+id).html("<b>* 데이터 : "+year+"년 "+month+"월</b>&nbsp;&nbsp;");
						//차트
						setSalesChartInfo(data.result, id);						
					}else{
						$('#plbl'+id).html("");
						clearResultTab(id, "1-4");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html("");
						clearResultTab(id, "1-4");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}
				}
			});
		}else{
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}

//분석 wms layer추가
function addAnalsWmsLayer(datas){	
	var mapObj = getMapObj(datas.mapType);
	mapObj.addAnalsWMSLayer(datas);
}

//차트정보 제공
function setSalesChartInfo(datas, id){
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
			var item = data.item;
			if(id == '1-4-4')
				item = data.lnm;
			if(item == '합계'){
				if(data.div != '전체') 
					chartData.push(tmpChart);	
				continue;
			}
			if(prev != data.div){
				//chartData.push(tmpChart);				
				tmpChart = [];
				prev = data.div;
				if(id == '1-4-4'){
					labels.push(data.snm);
				}else{
					labels.push(prev);
				}
				tmpChart.push([(data.item+''), data.tot]);
			}else{
				tmpChart.push([(data.item+''), data.tot]);				
			}
		}
		if(id == '1-4-1')
			settingLineChartData(chartId, chartData, labels, '기준년월', '금액(원)');
		else if(id == '1-4-3')
			settingLineChartData(chartId, chartData, labels, '시간대별', '금액(원)');
		else if(id == '1-4-4')
			settingLineChartData(chartId, chartData, labels, '업종별', '금액(원)');
	}
}

//param 가져오기 (기간별/성연령별)
function getAnalsWMSParams(mapObj, id){
	 var url = '';
	 if(id == '1-4-1')
		 url = "/gis/sale/salesYearAnals.do"; //매출분석_기간별
	 else if(id == '1-4-2')
		 url = "/gis/sale/salesGAAnals.do";   //매출분석_성연령별
	 else if(id == '1-4-3')
		 url = "/gis/sale/salesTimeAnals.do"; //매출분석_시간대별
	 else if(id == '1-4-4')
		 url = "/gis/sale/salesCtgAnals.do";  //매출분석_업종별
	 else if(id == '1-4-5')
		 url = "/gis/sale/salesFgnAnals.do";  //매출분석_외국인
	 var analsName = getMenuName(id); //메뉴 분석한글명 가져오기
	 var atype = cmmAreaType(); //분석영역 : area, user
	 var area = mapObj.mapCustom.getUserArea();
	 //area 좌표변환
	 if(area != null){
		 var geom = new OpenLayers.Format.WKT().read(area);
		 geom.geometry.transform(mapObj.map.getProjection(), mapObj.epsg4326);
		 area = new OpenLayers.Format.WKT().write(geom);
	 }	 
	 var dongcd = "";
	 if(id == "1-4-5")
		 dongcd = $('#selCstDong').val(); //외국인예외
	 else
		 dongcd = multiAreaValue();	
	//params생성 ( 추가시 : mergeParams : map.js 수정 )
	 var datas = {
		mapType : mapObj.mapType,
	    params  : {
	    	 aid    : id,
			 dongcd : dongcd, //$('#selDong').val(), //'4159056000,4159026200',
			 atype  : atype, //분석영역 설정 (area, user)
			 area   : area,    //사용자지정영역 geometry
			 vtype  : $('#selType'+id).val(), //분석/시각화 (heat, grid, beehive)
			 vcolor : $('#selColor'+id).val(),  //빨 녹 파			 
			 std : getStdInfo("1-4"), //기준년월 또는 기준일자 정보 가져오기
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

/////////////////////////////////////////////////////////////////////////
///////////////////////////////매출분석_성연령별분석////////////////////////////
/////////////////////////////////////////////////////////////////////////

function salesAnalsAge(id, mapType){
	//common.js 시계열 분석 체크, 읍면동 수 체크, 기간설정
	//2019.03.29 bskim 수정 행정동 2개이상으로 처리
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26) && chkNumValid("1-4")){ 
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
			//time정보 가져오기
			var age = getChkItems('chkSaleAge');
			//분석 wms
			var datas = getAnalsWMSParams(mapObj, id);	
			var saleParams = {
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
				url:"/gis/sale/salesGAList.do",
				dataType:'json',
				async:true,
				data: saleParams,
				async: true,
				success:function(data){
					if(data.result != null && data.result.length > 1){
						//합계만 나올수 있음
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data			
						//남여 추출
						var mcnt = data.result[0].mcnt;
						var wcnt = data.result[0].wcnt;
						var gender = "all";
						if(mcnt > 0 && wcnt == 0)
							gender = "M";
						else if(wcnt > 0 && mcnt == 0)
							gender = "F";
						datas.params.gender = gender;            //성별
						datas.params.gyear = data.result[0].item; //연령대
						saleParams.gyear = datas.params.gyear;
						saleParams.gender = datas.params.gender;
						var lndParams = {};
						var legendInfo = getLegendInfo(legendUrl.get(id), saleParams); //범례표시 정보 (max, min)
						if(legendInfo != undefined && legendInfo != null){
							datas.params.max = legendInfo[0].max;
							datas.params.min = legendInfo[0].min;
							addAnalsWmsLayer(datas);
							//범례정보 표시
							lndParams.max = datas.params.max;
							lndParams.min = datas.params.min;
							lndParams.vtype = 'area';
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
						}
						//엑셀 조회용 params저장
						setGridParams(gridId, saleParams, lndParams); //grid_chart.js
						$('#plbl'+id).html("<b>* 연령 : "+data.result[0].gb+"</b>&nbsp;&nbsp;");
						//차트
						setPopGAChartInfo(data.result, id);						
					}else{
						$('#plbl'+id).html("");
						clearResultTab(id, "1-4");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html("");
						clearResultTab(id, "1-4");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}
				}
			});
		}else{
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}

//차트 정보 제공_성연령별
function setPopGAChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();
	
	var chartData =[];	
	var tmpMChart = []; //남자 결과
	var tmpWChart = []; //여자 결과
	var labels = [];
	if(datas.length > 0){
		var prev = datas[0].div;
		labels.push(prev + " 남자");
		labels.push(prev + " 여자");
		for(var i=0; i<datas.length; i++){
			var data = datas[i];
			if(data.gb == '합계'){
				if(data.div != '전체'){ //최종합계가 아니면 저장
					chartData.push(tmpMChart);
					chartData.push(tmpWChart);
					tmpMChart = [];
					tmpWChart = [];
				}
				continue;				
			}
			if(prev != data.div){
				//chartData.push(tmpMChart);
				//chartData.push(tmpWChart);				
				prev = data.div;
				labels.push(data.div + " 남자");	
				tmpMChart.push([(data.gb+''), data.mcnt]);
				labels.push(data.div + " 여자");
				tmpWChart.push([(data.gb+''), data.wcnt]);
			}else{
				tmpMChart.push([(data.gb+''), data.mcnt]);	
				tmpWChart.push([(data.gb+''), data.wcnt]);
			}
		}
		settingLineChartData(chartId, chartData, labels, '연령', '인구수');
	}
}

/////////////////////////////////////////////////////////////////////////
///////////////////////////////매출분석_시간대별분석////////////////////////////
/////////////////////////////////////////////////////////////////////////

/**
* 매출분석_기간별 분석
*/
function salesAnalsTime(id, mapType){
	//common.js 시계열 분석 체크, 읍면동 수 체크, 기간설정
	//2019.03.29 bskim 수정 행정동 2개이상으로 처리 
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26) && chkNumValid("1-4")){ 
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
			//time정보 가져오기
			var time = getChkItems('chkSaleTime');
			//분석 wms
			 var datas = getAnalsWMSParams(mapObj, id);	
			 var saleParams = {
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
				url:"/gis/sale/salesTimeList.do",
				dataType:'json',
				async:true,
				data: saleParams,
				async: true,
				success:function(data){
					if(data.result != null && data.result.length > 1){
						//합계만 나올수 있음
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data
						
						datas.params.gyear = data.result[0].item;
						saleParams.gyear = datas.params.gyear;
						var lndParams = {};
						var legendInfo = getLegendInfo(legendUrl.get(id), saleParams); //범례표시 정보 (max, min)
						if(legendInfo != undefined && legendInfo != null){
							datas.params.max = legendInfo[0].max;
							datas.params.min = legendInfo[0].min;
							addAnalsWmsLayer(datas);
							//범례정보 표시
							lndParams.max = datas.params.max;
							lndParams.min = datas.params.min;
							lndParams.vtype = 'area';
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
						}
						//엑셀 조회용 params저장
						setGridParams(gridId, saleParams, lndParams); //grid_chart.js
						$('#plbl'+id).html("<b>* 시간 : "+data.result[0].item+"시</b>&nbsp;&nbsp;");
						//차트
						setSalesChartInfo(data.result, id);						
					}else{
						$('#plbl'+id).html('');
						clearResultTab(id, "1-4");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html('');
						clearResultTab(id, "1-4");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}
				}
			});
		}else{
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}

/////////////////////////////////////////////////////////////////////////
///////////////////////////////매출분석_업종별분석//////////////////////////////
/////////////////////////////////////////////////////////////////////////


/**
* 매출분석_업종별 분석
*/
function salesAnalsClas(id, mapType){
	//common.js 시계열 분석 체크, 읍면동 수 체크, 기간설정
	if(chkTsAnals(mapType) && chkMultiAreaCnt(1) && chkNumValid("1-4")){ 	
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
			
			var ctg = getCtgInfo('SelClas'+id);  //common.js
			//분석 wms
			 var datas = getAnalsWMSParams(mapObj, id);	
			 var saleParams = {
						atype : datas.params.atype,
						area  : datas.params.area,
						dongcd: datas.params.dongcd,
						std   : datas.params.std,
						smonth: datas.params.smonth,
						emonth: datas.params.emonth,
						sday  : datas.params.sday,
						eday  : datas.params.eday,
						gubun : datas.params.gubun,
						ctg   : ctg			
					};
			 $.ajax({
				type:"POST",
				url:"/gis/sale/salesCtgList.do",
				dataType:'json',
				async:true,
				data:saleParams,
				async: true,
				success:function(data){
					if(data.result != null && data.result.length > 1){
						//합계만 나올수 있음
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data
						
						datas.params.gyear = data.result[0].item;
						saleParams.gyear = datas.params.gyear;
						var lndParams = {};
						var legendInfo = getLegendInfo(legendUrl.get(id), saleParams); //범례표시 정보 (max, min)
						if(legendInfo != undefined && legendInfo != null){
							datas.params.max = legendInfo[0].max;
							datas.params.min = legendInfo[0].min;
							addAnalsWmsLayer(datas);
							//범례정보 표시
							lndParams.max = datas.params.max;
							lndParams.min = datas.params.min;
							lndParams.vtype = 'area';
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
						}
						//엑셀 조회용 params저장
						setGridParams(gridId, saleParams, lndParams); //grid_chart.js
						$('#plbl'+id).html("<b>* 업종 : "+data.result[0].snm+"</b>&nbsp;&nbsp;");
						//차트
						setSalesBarChartInfo(data.result, id);
					}else{
						$('#plbl'+id).html('');
						clearResultTab(id, "1-4");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html('');
						clearResultTab(id, "1-4");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}
				}
			});
		}else{
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}

/////////////////////////////////////////////////////////////////////////
///////////////////////////////매출분석_외국인분석//////////////////////////////
/////////////////////////////////////////////////////////////////////////

/**
* 매출분석_외국인분석
*/
function salesAnalsFgn(id, mapType){
	//common.js 시계열 분석 체크, 읍면동 수 체크, 기간설정
	if(chkTsAnals(mapType) && chkNumValid("1-4")){
		$('#plbl'+id).html('');	
		openTsBtn(false, false, id);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var mapObj = getMapObj(mapType);	
		mapObj.clearRemoveLayer("popLayer"+id); //wms layer 초기화	
		var datas = getAnalsWMSParams(mapObj, id);
		
		var chkSearch = true;
		if(datas.params.std != undefined && datas.params.std != null && datas.params.std == 'day') chkSearch = false;
	
		if(chkSearch){
			var chk = mapObj.mapCustom.mapCstAnalsLocation(mapType, $('#selCstDong').val(), datas);
			if(chk){
				var gridId = "tblGrid" + id;				
				$('#'+gridId).jqGrid('clearGridData');	//grid 클리어	
				$('#'+"divChart" + id).html(""); //차트 클리어
				
				$('#divMapLoading').show();
				
				var ctg = getCtgInfo('SelClas'+id);  //common.js
				//분석 wms
				var saleParams = {
						//atype : datas.params.atype,
						//area  : datas.params.area,
						atype : 'area',					
						area : null,
						dongcd: datas.params.dongcd,
						std   : datas.params.std,
						smonth: datas.params.smonth,
						emonth: datas.params.emonth,
						sday  : datas.params.sday,
						eday  : datas.params.eday,
						gubun : datas.params.gubun,
						ctg   : ctg			
				};			
				$.ajax({
					type:"POST",
					url:"/gis/sale/salesFgnList.do",
					dataType:'json',
					async:true,
					data:saleParams,
					async: true,
					success:function(data){
						if(data.result != null && data.result.length > 1){
							//합계만 나올수 있음
							$('#t'+id).show();
							$('#li'+id).trigger('click');
							settingGridData(gridId, data.result); //grid data						
							datas.params.gyear = data.result[0].item;
							saleParams.gyear = datas.params.gyear;
							//차트
							setSalesFgnBarChartInfo(data.result, id);
							setGridParams(gridId, saleParams, null); //grid_chart.js
							clearLegendInfo(); //범례 초기화 common.js
						}else{
							$('#plbl'+id).html('');
							clearResultTab(id, "1-4");
							openInfoPopup(true,"검색결과정보가 없습니다.");
						}
					},
					error:function(err){
						if(chkSession(err.status)){
							$('#plbl'+id).html('');
							clearResultTab(id, "1-4");
							openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
						}
					}
				});
			}else{
				openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
			}	
		}else{
			openInfoPopup(true,"기준일자 분석을 지원하지 않습니다.");
		}
	}
}

//////////////////////////////////////////////////////////////////////////
///////////////////////////////// 공통 /////////////////////////////////////
//////////////////////////////////////////////////////////////////////////

function setSalesBarChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();	
	var chartData =[];
	var tmpChart = [];
	var labels = [];
	if(datas.length > 0){
		for(var i=0; i<datas.length; i++){			
			var div = datas[i].div;
			var item = datas[i].lnm;
			var cnt = datas[i].tot;
			if(div == '전체' || div == '합계' || item == '합계' )
				continue;
			tmpChart.push([datas[i].snm,cnt]);
			labels.push(item);
		}
		if(tmpChart.length > 0)
			chartData.push(tmpChart);
		else
			chartData = [[null]];
		if(id == '1-4-4')
			settingBarChartData(chartId, chartData, labels, '업종별', '금액(원)');
	}
}


//외국인 분석 차트
function setSalesFgnBarChartInfo(datas, id){
	var chartId = "divChart" + id;
	$('#'+chartId).empty();	
	var chartData =[];
	var tmpChart = [];
	var labels = [];
	if(datas.length > 0){
		//중복 x축 라벨명 체크
		var chkNameCnt = 0;
		var nmMap = new Map(); //라벨명 같을 경우 차트표시 에러
		
		for(var i=0; i<datas.length; i++){
			var item = datas[i].item;
			var cnt = datas[i].tot;
			if(item == '전체' || item == '합계' || item == '합계' )
				continue;
			//x라벨 체크			
			if(item.length > 6){
				item = item.substring(0,6)+"...";
				chkSplit = true;
			}				
			var chkName = nmMap.get(item);
			if(chkName != undefined && chkName != null){				
				item += "_"+ ++chkNameCnt; //중복라벨명이 있을경우 명칭변경
			}
			nmMap.put(item, item);
			
			tmpChart.push([item, cnt]);
			labels.push(item);
		}
		if(tmpChart.length > 0)
			chartData.push(tmpChart);
		else
			chartData = [[null]];
		settingBarChartData(chartId, chartData, labels, '국가별', '금액(원)');			
	}
}
