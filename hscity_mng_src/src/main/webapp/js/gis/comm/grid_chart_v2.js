/**
 * data grid 세팅
 * @param id
 */
//그리드 생성
function initGridInfo(chkFlag, options, flag){	
	options.paging = "gridPaging"+chkFlag;
	options.gridId = "tblGrid"+chkFlag;	
	options.cols = gridCols[0]["cols"+chkFlag];
	options.mapType = flag;
	initGridResult(options);
}

function initGridResult(options){
	 $("#"+options.gridId).jqGrid({                
       datatype: "local",       
       data: [],       
       colModel:options.cols,
       autowidth:options.autowidth,
       loadonce: true,
       shrinkToFit: false,
       //width : options.width,
       height: options.height,
        //pager: "#"+options.paging,
       rowNum: options.num,
       onSelectRow: function(rowid, status, e){
    	   if(e != undefined && e.currentTarget != undefined && e.currentTarget != null){
    		   selectRowGrid(rowid, e.currentTarget.id, options.mapType); //common.js
    	   }
       }
    });
	 //options.columns;
	 
}

/**
 * data chart 세팅
 * @param id
 */
function initLineChartResult(options){
	 //var plot =	
	 $.jqplot(options.chartId, options.chartData,{
		 height: 205,
		 //animate: true,
		 seriesDefaults: { 
	        showMarker:true,
	        pointLabels: { show:false },
	        rendererOptions: {fillToZero: true}
	      },	     
	      axesDefaults: {
            labelRenderer: $.jqplot.CanvasAxisLabelRenderer
         },
		 axes: {
		        xaxis: {
		          renderer: $.jqplot.CategoryAxisRenderer,
		          label: options.legendx,
		          labelOptions: {		            
		              fontSize: '9pt'
		          },		    
		          tickRenderer:  $.jqplot.CanvasAxisTickRenderer,
		          tickOptions: {
		        	  fontSize: '9pt',
		              labelPosition: 'middle',
		              angle: options.angle //label의 기울어진 각도
		          }
		        },
		        yaxis: {
		          label: options.legendy,
		          labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
		          labelOptions: {		            
		              fontSize: '9pt'
		          }
		        }
		 },
		 series: [
		             {
                 renderer:$.jqplot.OHLCRenderer, 
                 rendererOptions:{ 
                     candleStick:true
                 }
             }
         ],         
		 legend: {
			 show: true,
	            renderer: $.jqplot.EnhancedLegendRenderer,
	            placement: "outsideGrid",
	            labels: options.labels,
	            location: "ne",
	            rowSpacing: "0px",
	            rendererOptions: {
	                seriesToggle: 'normal',
	                seriesToggleReplot: {resetAxes: true}
	            }
		 }		
    }); 
	/*
	$('#'+options.chartId).bind('resize', function(event, ui) {		
		plot.replot( { resetAxes: true } );
    });
    */
}

//바차트 RESULT
function initBarChartResult(options){
	var plot = $.jqplot(options.chartId, options.chartData,{
		height: 205,
		seriesDefaults:{
            renderer:$.jqplot.BarRenderer
        },       	     
	    axesDefaults: {
            labelRenderer: $.jqplot.CanvasAxisLabelRenderer
         },
        axes: {
	        xaxis: {
	          renderer: $.jqplot.CategoryAxisRenderer,
	          label: options.xlabel,
	          labelOptions: {		            
	              fontSize: '8pt'
	          },	
	          tickRenderer:  $.jqplot.CanvasAxisTickRenderer,
	          tickOptions: {
	        	  fontSize: '8pt',
	              labelPosition: 'middle',
	              angle: 50 //label의 기울어진 각도
	          }
	        },
	        yaxis: {
	          label: options.ylabel,
	          labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
	          labelOptions: {		            
	              fontSize: '8pt'
	          }
	        }
        }
    });
}


/**
 * grid dataset (data 값 채우기)
 * @param id
 * @param dataset
 */
function settingGridData(id, dataset){
	 setExtGrid(id, dataset); //그리드값 세팅전 예외로 처리할 항목처리
	 $("#"+id).jqGrid('setGridParam', {data:dataset, page:1});	 
	 $("#"+id).trigger('reloadGrid');
	 resizeGrid('win','0');
}

/**
 * 그리드에 검색params정보 저장
 * @param gridId
 * @param params
 */
function setGridParams(id, params, lnd){
	//엑셀 조회용 params저장
	$('#'+id)[0].gisparams = {};
	$('#'+id)[0].gisparams =  params;	
	if(lnd != null)
		setLndGridParams(id, lnd);
}

function setLndGridParams(id, lnd){
	$('#'+id)[0].lndparams = {};
	$('#'+id)[0].lndparams =  lnd;
}


/**
* chart dataset 
* @param id
* @param dataset
*/
function settingLineChartData(id, dataset, labels, legendx, legendy, angle){
	var options = [];
	options.chartId = id;
	options.chartData = dataset;
	//$.jqplot(id, [dataset]);	
	options.labels = labels;
	options.show = true;	
	options.legendx = legendx;
	options.legendy = legendy;
	options.type = 'line';
	$('#'+id)[0].options = {};
	$('#'+id)[0].options = options;
	options.angle = 25;
	if(angle != undefined && angle != null && angle != '')
		options.angle = angle;
	initLineChartResult(options);
}

/**
* chart dataset 
* @param id
* @param dataset
*/
function settingBarChartData(id, dataset, labels, xlabel, ylabel){
	var options = [];
	options.chartId = id;
	options.chartData = dataset;
	//$.jqplot(id, [dataset]);	
	options.labels = labels;
	options.show = true;
	options.xlabel = xlabel;
	options.ylabel = ylabel;
	options.type = 'bar';
	$('#'+id)[0].options = {};
	$('#'+id)[0].options = options;	
	initBarChartResult(options);
}

/**
 * 그리드 리사이즈
 * type : window resize, left menu event
 */
function resizeGrid(type, pid){
	var id = getMenuId();	
	if(id != undefined && id != null && id != ''){
		var divId = id.split('-');
		if(divId.length > 1)
			divId = divId[0] + "-" + divId[1];
		//클릭 이벤트 당시의 id와pid가 일치할때 이벤트 수행
		//일치하지 않은 경우는 메뉴이동시 이므로 이벤트 수행 안함
		if(type == 'win' || divId == pid){
			if(id == "1-1-3" || id == "1-1-4"){
				//예외 2개 grid적용
				var colId = id.replace(/-/gi, "");		
				var divWidth = $('#divGrid'+id + "-1").width() - 15;
				var gridWidth = gridWidthMap.get("cols" + colId + "1");
				applyGridSize(divWidth, gridWidth, id+"-1");
				
				divWidth = $('#divGrid'+id + "-2").width() - 15;
				gridWidth = gridWidthMap.get("cols" + colId + "2");
				applyGridSize(divWidth, gridWidth, id+"-2");
			}else{				
				var divWidth = $('#divGrid'+id).width() - 15;
				var gridWidth = gridWidthMap.get("cols" + id.replace(/-/gi, ""));		
				applyGridSize(divWidth, gridWidth, id);
			}		
		}else{
			var divWidth = $('#divGrid'+pid+"-1").width() - 15;			
			var gridWidth = gridWidthMap.get("cols" + id.replace(/-/gi, ""));		
			applyGridSize(divWidth, gridWidth, id);
		}
	}
}

//그리드 기본정보
function gridInfo(mapType){	
	var id = getMenuId();
	var pid = id.split('-');
	if(pid.length == 3)
		pid = pid[0] + "-" + pid[1];
	var gridId = "tblGrid" + id;
	var options = {};	
	options.id = id;
	options.pid = pid;
	options.gridId = gridId; 
	options.ids = $('#'+gridId).jqGrid("getDataIDs");
	tsPopStop(mapType, pid); //시계열 중지
	return options;
}

/*
 * grid선택시 레이어 정보 변경
 */
function selectRowGrid(rowId, gridId, mapType){
	var mapObj = getMapObj(mapType); //map.js
	var id = gridId.replace("tblGrid","");
	var pid = id.split('-');
	if(pid.length == 3)	pid = pid[0] + "-" + pid[1];
	var data = $("#"+gridId).jqGrid('getRowData', rowId);
	var layer = mapObj.getLayer("geoserverLayer");
	var label = "";
	if(layer != undefined && layer != null){
		
		var chkItem = data.item;
		if(id == '1-1-2')
			chkItem = data.age;
		if(chkItem != undefined && chkItem != '' && chkItem != '합계'){
			$('#'+gridId).setSelection(rowId, true);
			if(id == '1-1-1' || id == '1-2-1' || id == '1-4-1'){
				layer.attributes.params.gyear = data.item;
				var year =  chkItem.substring(0,4);
				var month = chkItem.substring(4,6);
				if(chkItem.length == 8){
					var day = chkItem.substring(6,8);
					label = "<b>* 데이터 : "+year+"년 "+month+"월 "+day+"일</b>&nbsp;&nbsp;";
				}else
					label = "<b>* 데이터 : "+year+"년 "+month+"월</b>&nbsp;&nbsp;";
			}else if(id == '1-1-2'){
				layer.attributes.params.gyear = data.ym;
				layer.attributes.params.age = data.item;//선택 연령
				label = "<b>* 연령 : "+chkItem+"</b>&nbsp;&nbsp;";
			}else if(id == '1-4-2'){
				var mcnt = data.mcnt;
				var wcnt = data.wcnt;
				var gender = "all";
				if(mcnt > 0 && wcnt == 0)
					gender = "M";
				else if(wcnt > 0 && mcnt == 0)
					gender = "F";
				layer.attributes.params.gender = gender;            //성별
				layer.attributes.params.gyear = data.item; //연령대
				label = "<b>* 연령 : "+data.gb+"</b>&nbsp;&nbsp;";
			}else if(id == '1-4-3'){
				layer.attributes.params.gyear = data.item;
				label = "<b>* 시간 : "+data.item+"시</b>&nbsp;&nbsp;";
			}else if(id == '1-4-4'){
				layer.attributes.params.gyear = data.item;
				label = "<b>* 업종 : "+data.snm+"</b>&nbsp;&nbsp;";
			}else if(id == '1-4-5'){
				
			}			
			var url = legendUrl.get(id);
			if(url != undefined && url != null && url != ''){
				//legend 변경
				var legendInfo = getLegendInfo(url, layer.attributes.params); //범례표시 정보 (max, min)
				var lndParams = {};
				if(legendInfo != undefined && legendInfo != null){
					layer.attributes.params.max = legendInfo[0].max;
					layer.attributes.params.min = legendInfo[0].min;					
					lndParams.max = layer.attributes.params.max;
					lndParams.min = layer.attributes.params.min;
					if(id.indexOf("1-4-") > -1)
						lndParams.vtype = "area";
					else
						lndParams.vtype = layer.attributes.params.vtype;
					lndParams.vcolor =layer.attributes.params.vcolor;
					showLegendInfo(lndParams); //common.js
				}
				setGridParams("tblGrid" + id, layer.attributes.params, lndParams); 
			}
			$('#plbl'+id).html(label);
			addAnalsWmsLayer(layer.attributes);
			//var params = mapObj.mergeParams(mapType, layer.attributes.params);			
			//layer.mergeNewParams(params);	
		}	
	}
}

/**
 * 그리드 사이즈 적용
 * @param divWidth
 * @param gridWidth
 */
function applyGridSize(divWidth, gridWidth, id){
	if($("#tblGrid"+id)[0].p != undefined ){		
		if(divWidth > gridWidth){		
			$("#tblGrid"+id)[0].p.shrinkToFit = true;		
		}else{
			$("#tblGrid"+id)[0].p.shrinkToFit = false;
		}	
	}
	$("#tblGrid"+id).setGridWidth(divWidth);
    $("#tblGrid"+id).trigger("reloadGrid");
}


/**
 * chart resize
 * 차트는 이미 생성된 이미지라 resize시 재이미지 생성해야함
 * type : window resize, left menu event
 */
function resizeChart(type, pid){
	var id = getMenuId();
	if(id != undefined && id != null && id != ''){
		var divId = id.split('-');
		if(divId.length > 1)
			divId = divId[0] + "-" + divId[1];
		//클릭 이벤트 당시의 id와pid가 일치할때 이벤트 수행
		//일치하지 않은 경우는 메뉴이동시 이므로 이벤트 수행 안함		
		if(type == 'win' || divId == pid){
			var divWidth = $('#frmChart'+id).width() - 5;			
			var plotChart = $('#divChart'+id)[0];
			var options = null;
			if(plotChart != undefined && plotChart != null){
				options = plotChart.options;
				$('#divChart'+id).empty("");
			};	
			if(options != undefined){
				$('#divChart'+id).width(divWidth);	
				if(options.type == 'line')
					initLineChartResult(options);
				else
					initBarChartResult(options);	
			}	
		}			
	}	
}


////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////기타 사항////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

/**
 * 그리드 기타처리 (유동인구 분석) 
 */

function setExtGrid(id, dataset){
	if(id == "tblGrid1-2-2" || id == "tblGrid1-3-3"){
		var tblGrid = $('#'+id);
		if(dataset.length > 0){
			var data = dataset[0];
			if(data.m10 == -999)
				tblGrid.jqGrid('hideCol', "m10");
			else
				tblGrid.jqGrid('showCol', "m10");
			
			if(data.m20 == -999)
				tblGrid.jqGrid('hideCol', "m20");
			else
				tblGrid.jqGrid('showCol', "m20");
			
			if(data.m30 == -999)
				tblGrid.jqGrid('hideCol', "m30");
			else
				tblGrid.jqGrid('showCol', "m30");
			
			if(data.m40 == -999)
				tblGrid.jqGrid('hideCol', "m40");
			else
				tblGrid.jqGrid('showCol', "m40");
			
			if(data.m50 == -999)
				tblGrid.jqGrid('hideCol', "m50");
			else
				tblGrid.jqGrid('showCol', "m50");
			
			if(data.m60 == -999)
				tblGrid.jqGrid('hideCol', "m60");
			else
				tblGrid.jqGrid('showCol', "m60");
			
			if(data.m70 == -999)
				tblGrid.jqGrid('hideCol', "m70");
			else
				tblGrid.jqGrid('showCol', "m70");
			
			if(data.w10 == -999)
				tblGrid.jqGrid('hideCol', "w10");
			else
				tblGrid.jqGrid('showCol', "w10");
			
			if(data.w20 == -999)
				tblGrid.jqGrid('hideCol', "w20");
			else
				tblGrid.jqGrid('showCol', "w20");
			
			if(data.w30 == -999)
				tblGrid.jqGrid('hideCol', "w30");
			else
				tblGrid.jqGrid('showCol', "w30");
			
			if(data.w40 == -999)
				tblGrid.jqGrid('hideCol', "w40");
			else
				tblGrid.jqGrid('showCol', "w40");
			
			if(data.w50 == -999)
				tblGrid.jqGrid('hideCol', "w50");
			else
				tblGrid.jqGrid('showCol', "w50");
			
			if(data.w60 == -999)
				tblGrid.jqGrid('hideCol', "w60");
			else
				tblGrid.jqGrid('showCol', "w60");
			
			if(data.w70 == -999)
				tblGrid.jqGrid('hideCol', "w70");
			else
				tblGrid.jqGrid('showCol', "w70");			
		}
	}else if(id == "tblGrid1-2-3"){
		//유동인구 _ 시간대별
		var tblGrid = $('#'+id);
		if(dataset.length > 0){
			var data = dataset[0];
			if(data.t00 == -999)
				tblGrid.jqGrid('hideCol', "t00");
			else
				tblGrid.jqGrid('showCol', "t00");
			
			if(data.t01 == -999)
				tblGrid.jqGrid('hideCol', "t01");
			else
				tblGrid.jqGrid('showCol', "t01");
			
			if(data.t02 == -999)
				tblGrid.jqGrid('hideCol', "t02");
			else
				tblGrid.jqGrid('showCol', "t02");
			
			if(data.t03 == -999)
				tblGrid.jqGrid('hideCol', "t03");
			else
				tblGrid.jqGrid('showCol', "t03");
			
			if(data.t04 == -999)
				tblGrid.jqGrid('hideCol', "t04");
			else
				tblGrid.jqGrid('showCol', "t04");
			
			if(data.t05 == -999)
				tblGrid.jqGrid('hideCol', "t05");
			else
				tblGrid.jqGrid('showCol', "t05");
			
			if(data.t06 == -999)
				tblGrid.jqGrid('hideCol', "t06");
			else
				tblGrid.jqGrid('showCol', "t06");
			
			if(data.t07 == -999)
				tblGrid.jqGrid('hideCol', "t07");
			else
				tblGrid.jqGrid('showCol', "t07");
			
			if(data.t08 == -999)
				tblGrid.jqGrid('hideCol', "t08");
			else
				tblGrid.jqGrid('showCol', "t08");
			
			if(data.t09 == -999)
				tblGrid.jqGrid('hideCol', "t09");
			else
				tblGrid.jqGrid('showCol', "t09");
			
			if(data.t10 == -999)
				tblGrid.jqGrid('hideCol', "t10");
			else
				tblGrid.jqGrid('showCol', "t10");
			
			if(data.t11 == -999)
				tblGrid.jqGrid('hideCol', "t11");
			else
				tblGrid.jqGrid('showCol', "t11");
			
			if(data.t12 == -999)
				tblGrid.jqGrid('hideCol', "t12");
			else
				tblGrid.jqGrid('showCol', "t12");
			
			if(data.t13 == -999)
				tblGrid.jqGrid('hideCol', "t13");
			else
				tblGrid.jqGrid('showCol', "t13");
			
			if(data.t14 == -999)
				tblGrid.jqGrid('hideCol', "t14");
			else
				tblGrid.jqGrid('showCol', "t14");
			
			if(data.t15 == -999)
				tblGrid.jqGrid('hideCol', "t15");
			else
				tblGrid.jqGrid('showCol', "t15");
			
			if(data.t16 == -999)
				tblGrid.jqGrid('hideCol', "t16");
			else
				tblGrid.jqGrid('showCol', "t16");
			
			if(data.t17 == -999)
				tblGrid.jqGrid('hideCol', "t17");
			else
				tblGrid.jqGrid('showCol', "t17");
			
			if(data.t18 == -999)
				tblGrid.jqGrid('hideCol', "t18");
			else
				tblGrid.jqGrid('showCol', "t18");
			
			if(data.t19 == -999)
				tblGrid.jqGrid('hideCol', "t19");
			else
				tblGrid.jqGrid('showCol', "t19");
			
			if(data.t20 == -999)
				tblGrid.jqGrid('hideCol', "t20");
			else
				tblGrid.jqGrid('showCol', "t20");
			
			if(data.t21 == -999)
				tblGrid.jqGrid('hideCol', "t21");
			else
				tblGrid.jqGrid('showCol', "t21");
			
			if(data.t22 == -999)
				tblGrid.jqGrid('hideCol', "t22");
			else
				tblGrid.jqGrid('showCol', "t22");
			
			if(data.t23 == -999)
				tblGrid.jqGrid('hideCol', "t23");
			else
				tblGrid.jqGrid('showCol', "t23");
			
		}
	}
}