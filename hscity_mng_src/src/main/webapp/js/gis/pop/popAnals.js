/**
*
* 인구/매출 분석 관련 js (인구분석)
*
**/

/**
 * 페이지 초기화
 */
function initPopPageInfo(flag, menu){
	var mapObj = getMapObj(flag);
	console.log("mapObj", mapObj);
	mapObj.mapMenu = menu;
	searchJspPage("/gis/area.do", {flag:flag}, false, "divPopArea"); //공통 영역지정 페이지 호출
	setBasicMonth('txtBasicDate'); //기본 기준년월일 세팅
	setCalendarMonth('txtBasicDate'); //common.js	
	$('.numbersOnly').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	
	//grid 세팅	
	var options = {};
	options.height = $('.wp60').height() - 35;
	//options.width = $('.wp60').width() - 10;
	options.num = 10000;
	options.legendx = '';
	options.legendy = '';
	var chkFlag = "1-1-1";
	options.autowidth = true;
	initGridInfo(chkFlag, options, flag);
	
	//chart 세팅 divChart1-1-1
	options.chartId = "divChart"+chkFlag;
	options.chartData = [[null]];
	$('#li'+chkFlag).trigger('click'); //임시처리 (차트 초기세팅위해서)
	initLineChartResult(options);
	
	/**
	 * 성/연령별 분석 초기화
	 */
	chkFlag = "1-1-2";
	initGridInfo(chkFlag, options, flag);
	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click'); 
	initLineChartResult(options);	
	
	/**
	 * 전입분석 (지역별, 요인별)
	 */	
	chkFlag = "1-1-3-1";	
	initGridInfo(chkFlag, options, flag);	
	//전입분석 요인별
	chkFlag = "1-1-3-2";		
	initGridInfo(chkFlag, options, flag);
	
	chkFlag = "1-1-3";	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
	
	/**
	 * 전출분석 (지역별, 요인별)
	 */	
	chkFlag = "1-1-4-1";	
	initGridInfo(chkFlag, options, flag);	
	//전입분석 요인별
	chkFlag = "1-1-4-2";		
	initGridInfo(chkFlag, options, flag);
	
	chkFlag = "1-1-4";	
	options.chartId = "divChart"+chkFlag;
	$('#li'+chkFlag).trigger('click');
	initBarChartResult(options);
	
	$('#li1-1-1').trigger('click');
	showMapResult(false);	
	
	getTablePeriod("1-1-1"); //기간설정
}

/**
 * 인구분석_기간별 분석
 */
function popAnalsPeriod(id, mapType){
	console.log( "popAnalsPeriod", id, mapType)
	//2019.03.29 bskim 수정 행정동 2개이상으로 처리
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26)){ //common.js 시계열 분석 체크 및 선택 동 수 체크
		clearLegendInfo(); //범례 초기화 common.js
		$('#plbl'+id).html('');
		//버튼 선택
		openTsBtn(true, false, id);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var mapObj = getMapObj(mapType);	
		mapObj.clearRemoveLayer("popLayer"+id); //wms layer 초기화
		
		var chk = mapObj.mapCustom.mapAnalsLocation(mapType); //분석위치로 이동
		//분석하려는 영역이 지정되어 있으면 다음 단계로..
		var gridId = "tblGrid" + id;
		
		if(chk){		
			$('#'+gridId).jqGrid('clearGridData');	//grid 클리어	
			$('#'+"divChart" + id).html(""); //차트 클리어
			
			$('#divMapLoading').show();
			 //분석 wms
			 var datas = getAnalsWMSParams(mapObj, id);
			 var popParams = {
						atype:datas.params.atype,
						vtype:datas.params.vtype,
						area: datas.params.area,
						syear:datas.params.syear,
						dongcd: datas.params.dongcd,
						period : datas.params.period,
						std : datas.params.std,
						smonth : datas.params.smonth
					};			 
			 $.ajax({
				type:"POST",
				url:"/gis/pop/popYearList.do",
				dataType:'json',
				async:true,
				data: popParams,
				async: true,
				success:function(data){
					console.log("data", data);
					//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
					if(data.result != null && data.result.length > 1){
						//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
						//결과 화면 보기
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data
												
						datas.params.gyear = data.result[0].item;
						popParams.gyear = datas.params.gyear;
						var lndParams = {};
						if(popParams.vtype != 'heat'){
							var legendInfo = getLegendInfo(legendUrl.get(id), popParams); //범례표시 정보 (max, min)							
							if(legendInfo != undefined && legendInfo != null){								
								//GRID, BEEHIVE
								datas.params.max = legendInfo[0].max;
								datas.params.min = legendInfo[0].min;
								addAnalsWmsLayer(datas);						
								//범례정보 표시
								lndParams.max = datas.params.max;
								lndParams.min = datas.params.min;
								lndParams.vtype = popParams.vtype;
								lndParams.vcolor = datas.params.vcolor;
								showLegendInfo(lndParams); //common.js
							}
						}else{
							//HEAT MAP
							addAnalsWmsLayer(datas);
							//범례정보 표시							
							lndParams.max = 0;
							lndParams.min = 0;
							lndParams.vtype = popParams.vtype;
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams);
						}	
						//엑셀 조회용 params저장
						setGridParams(gridId, popParams, lndParams); //grid_chart.js
						var year =  datas.params.gyear.substring(0,4);
						var month = datas.params.gyear.substring(4,6);
						$('#plbl'+id).html("<b>* 데이터 : "+year+"년 "+month+"월</b>&nbsp;&nbsp;");
						//차트						
						setPopChartInfo(data.result, id);						
					}else{
						$('#plbl'+id).html("");
						clearResultTab(id, "1-1");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}				
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html("");
						clearResultTab(id, "1-1");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}
				}
			});
		}else{			
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}
	}
}


/**
 * 차트 정보 제공
 */
function setPopChartInfo(datas, id){
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

//분석 wms layer추가
function addAnalsWmsLayer(datas){
	console.log("addAnalsWmsLayer",datas);
	var mapObj = getMapObj(datas.mapType);
	mapObj.addAnalsWMSLayer(datas);
}

//param 가져오기 (기간별/성연령별)
function getAnalsWMSParams(mapObj, id){
	 var url = '';
	 if(id == '1-1-1')
		 url = "/gis/pop/popYearAnals.do";
	 else if(id == '1-1-2')
		 url = "/gis/pop/popGAAnals.do";
	 var analsName = getMenuName(id); //메뉴 분석한글명 가져오기
	 var atype = cmmAreaType(); //분석영역 : area, user
	 var area = mapObj.mapCustom.getUserArea();
	 //area 좌표변환
	 if(area != null){
		 var geom = new OpenLayers.Format.WKT().read(area);
		 geom.geometry.transform(mapObj.map.getProjection(), mapObj.epsg4326);
		 area = new OpenLayers.Format.WKT().write(geom);
	 }
	//연령계산의 기준년 가져오기
	 var chkYear = $(":input:radio[name=rdStd"+id+"]:checked").val();
	 var syear = new Date().getFullYear();
	 var smonth =  syear + "-" + (new Date().getMonth() + 1);
	 if(chkYear == 'past'){
		 var arrDate = $('#txtBasicDate').val().split('-');
		 syear = arrDate[0];		  
		 smonth =  $('#txtBasicDate').val();
	 }	 
	 //성별 체크
	 var mgender = ''; //성연령별 성별 남
	 var wgender = ''; //성연령별 성별 여
	 if($('#chkMGender'+id)[0] != undefined && $('#chkMGender'+id)[0].checked)
		 mgender = 'mm';
	 if($('#chkWGender'+id)[0] != undefined && $('#chkWGender'+id)[0].checked)
		 wgender = 'wm';
	 var gender = 'gender'; 
	 if(mgender == 'mm' && wgender == 'wm')
		 gender = 'all';
	 else if(mgender != 'mm' && wgender != 'wm')
		 gender = 'no';
	 //params생성 ( 추가시 : mergeParams : map.js 수정 )
	 var datas = {
		mapType : mapObj.mapType,
	    params  : {
	    	 aid    : id,
			 dongcd : multiAreaValue(),//$('#selDong').val(), //'4159056000,4159026200',
			 atype  : atype, //분석영역 설정 (area, user)
			 area   : area,    //사용자지정영역 geometry
			 smonth : smonth, //기준년월
			 vtype  : $('#selType'+id).val(), //분석/시각화 (heat, grid, beehive)
			 vcolor : $('#selColor'+id).val(),  //빨 녹 파			 
			 std    : $(":input:radio[name=rdStd"+id+"]:checked").val(), //연령계산
			 syear  : syear, //기준년
			 //기간별
			 period : $(":input:radio[name=rdPeriod"+id+"]:checked").val(), //month(지난 6개월), year(지난해)			 
			 //성/연령별 
			 gender : gender,
			 mgender : mgender,
			 wgender : wgender,
			 sage   : $("#sage"+id).val(),
		     eage   : $("#eage"+id).val(),
		     smember : $("#smember"+id).val(),
		     emember : $("#emember"+id).val()
	 	},
	 	layerName : "popLayer"+id,
	 	analsName : analsName,
	 	analsUrl  : url
	 };
	 return datas;
}


/////////////////////////////////////////////////////////////////////////////
////////////////////////////////성연령별 분석 ////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function popAnalsAge(id, mapType){
	//common.js 시계열 및 읍면동 수 체크(읍면동수 변경)
	if(chkTsAnals(mapType) && chkMultiAreaCnt(26)){ //2019.03.29 bskim 수정 행정동 2개이상으로 처리	
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
			 var popParams = {
						atype:datas.params.atype,
						vtype:datas.params.vtype,
						area: datas.params.area,
						syear:datas.params.syear,
						dongcd: datas.params.dongcd,				
						std : datas.params.std,
						smonth : datas.params.smonth,
						gender : datas.params.gender,
						mgender : datas.params.mgender,
						wgender : datas.params.wgender,
						sage : datas.params.sage,
						eage : datas.params.eage,
						smember : datas.params.smember,
						emember : datas.params.emember
					};
			 $.ajax({
				type:"POST",
				url:"/gis/pop/popGAList.do",
				dataType:'json',
				async:true,
				data: popParams,
				async: true,
				success:function(data){
					//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
					if(data.result != null && data.result.length > 1){
						//값이 없더라도 합계만 나올수 있으므로 1개이상보다 커야 함
						//결과 화면 보기
						$('#t'+id).show();
						$('#li'+id).trigger('click');
						settingGridData(gridId, data.result); //grid data						
												
						datas.params.gyear = data.result[0].ym;
						datas.params.age = data.result[0].item;//선택 연령	
						popParams.gyear = datas.params.gyear;
						popParams.age = datas.params.age;
						var lndParams = {};
						if(popParams.vtype != 'heat'){
							//GRID, BEEHIVE
							var legendInfo = getLegendInfo(legendUrl.get(id), popParams); //범례표시 정보 (max, min)
							if(legendInfo != undefined && legendInfo != null){
								datas.params.max = legendInfo[0].max;
								datas.params.min = legendInfo[0].min;
//								addAnalsWmsLayer(datas);
								//범례정보 표시
								lndParams.max = datas.params.max;
								lndParams.min = datas.params.min;
								lndParams.vtype = popParams.vtype;
								lndParams.vcolor = datas.params.vcolor;
								showLegendInfo(lndParams); //common.js
							}								
						}else{
							//HEAT MAP
//							addAnalsWmsLayer(datas);
							//범례정보 표시							
							lndParams.max = 0;
							lndParams.min = 0;
							lndParams.vtype = popParams.vtype;
							lndParams.vcolor = datas.params.vcolor;
							showLegendInfo(lndParams); //common.js
						}
						//엑셀 조회용 params저장
						setGridParams(gridId, popParams, lndParams); //grid_chart.js
						$('#plbl'+id).html("<b>* 연령 : "+data.result[0].age+"</b>&nbsp;&nbsp;");
						//차트
						setPopGAChartInfo(data.result, id);						
					}else{
						$('#plbl'+id).html("");
						clearResultTab(id, "1-1");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}			
				},
				error:function(err){
					if(chkSession(err.status)){
						$('#plbl'+id).html("");
						clearResultTab(id, "1-1");
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
			if(data.age == '합계'){
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
				tmpMChart.push([(data.item+''), data.mcnt]);
				labels.push(data.div + " 여자");
				tmpWChart.push([(data.item+''), data.wcnt]);
			}else{
				tmpMChart.push([(data.item+''), data.mcnt]);	
				tmpWChart.push([(data.item+''), data.wcnt]);
			}
		}
		settingLineChartData(chartId, chartData, labels, '연령', '인구수(명)');		
	}
}

/////////////////////////////////////////////////////////////////////////////
////////////////////////////////  전입/전출 분석  /////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

function inOutPopAnals(url, id, mapType){
	//시걔열분석, 읍면동 선택 수 체크
	if(chkTsAnals(mapType) && chkMultiAreaCnt(1)){
		clearLegendInfo(); //범례 초기화 common.js
		$('#spanTsLabel1-1').html('');
		openTsBtn(false, false, id);
		openTreePopup(false, mapType); //분석결과 트리 hide
		var mapObj = getMapObj(mapType);	
		//추가 필요 layer 클리어
		mapObj.clearRemoveLayer("popLayer"+id); 
		var chk = mapObj.mapCustom.mapAnalsLocation(mapType); //분석위치로 이동	
		if(chk){
			var gridCntId = "tblGrid" + id + "-1";	//지역분석 그리드 아이디
			var gridResnId = "tblGrid" + id + "-2";	//요인분석 그리드 아이디		
			$('#'+gridCntId).jqGrid('clearGridData');	//grid 클리어
			$('#'+gridResnId).jqGrid('clearGridData');	//grid 클리어
			$('#'+"divChart" + id).html(""); //차트 클리어
			
			$('#divMapLoading').show();			
			 //분석 vt
			 var datas = getAnalsInOutParams(url,mapObj, id);
			 var popParams =  {
						atype:datas.params.atype,
						area: datas.params.area,
						smonth:datas.params.smonth,
						dongcd: datas.params.dongcd,
						range: datas.params.range,
						coord: datas.params.coord,
						gubun: datas.params.gubun
			 };
			 $.ajax({
				type:"POST",
				url:datas.params.url,
				dataType:'json',
				async:true,
				data: popParams,
				async: true,
				success:function(data){				
					if(data.cnt != null && data.cnt.length > 1){
						//결과 화면 보기
						$('#t'+id).show();
						$('#li'+id).trigger('click');				
						settingGridData(gridCntId, data.cnt); //grid data
						settingGridData(gridResnId, data.resn); //grid data
						//차트
						setInOutChartInfo(data.cnt, id);
						//엑셀 조회용 params저장
						setGridParams(gridCntId, popParams, null); //grid_chart.js (결과가 1개이상일경우 1번째 grid에 저장)
						//wfs layer 생성
						mapObj.addAnalsVtLayer(datas, data.geom); //layer + 범례구성
					}else{
						clearResultTab(id, "1-1");
						openInfoPopup(true,"검색결과정보가 없습니다.");
					}
				},
				error:function(err){
					if(chkSession(err.status)){
						clearResultTab(id, "1-1");
						openInfoPopup(true,"조회 중  에러가 발생하였습니다.");	
					}					
				}
			});
		}else{			
			openInfoPopup(true,"선택된 읍면동정보가 없습니다.");
		}	
	}
}

//전입, 전출  param 생성
function getAnalsInOutParams(url, mapObj, id){	 
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
			 smonth : $('#txtBasicDate').val().replace("-",""), //기준년월			 			
			 range  : $('#selRange'+id).val(),
			 visible : true, //reload시 필요
			 opacity : 1,     //reload시 필요
			 gubun : $("#selStd").val(),//$("#selStd option:selected").text()
			 colpck : colpck  //표현될 색상 지정
	 	},
	 	layerName : "popLayer"+id,
	 	analsName : analsName,
	 	analsUrl  : "/gis/pop/inPopAnals.do"
	 };
	 return datas;
}

//차트 정보 제공
function setInOutChartInfo(datas, id){
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
			if(item == '전체' || item == '합계')
				continue;
			tmpChart.push([(item.split(" ")[1]+''),cnt]);
			item = replaceSigName(item); //시군구명칭 간략명칭으로 교체
			labels.push(item);
		}
		if(tmpChart.length > 0)
			chartData.push(tmpChart);
		else
			chartData = [[null]];
		if(id == '1-1-3')
			settingBarChartData(chartId, chartData, labels, '전입지역', '전입자수(명)');
		else if(id == '1-1-4')
			settingBarChartData(chartId, chartData, labels, '전출지역', '전출자수(명)');
	}
}