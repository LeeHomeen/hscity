/*********************************************
 * 
 * 지도상에성의 각종 기능 동작
 * 
 ********************************************/

//menu.jsp
function clkMnItem(num, flag, menu){
	console.log("clkMnItem", num, flag, menu)
	//현재 시계열 분석 중인지 체크
	if(chkTsAnals(flag)){
		clearLegendInfo(); //범례 초기화
		clearAnalsInfo(flag);
		//clearControlInfo(flag);
		clearPopupInfo(flag);
		topParent = null; //storage.js 멀티셀렉트 초기화
		//결과창 내리기
		showMapResult(false);	
		$('#gis-lnb').html('');
		$('.gnb li').removeClass('active');
		$('#liMn'+num).addClass("active");	
		
		searchJspPage("/gis/search_v2.do", {flag:flag, menu:menu}, false, "gis-lnb"); //gisPopup side search 호출
		searchJspPage("/gis/result_v2.do", {flag:flag, menu:menu}, false, "gis-result");
		
		if(menu != 'blind' && menu != 'line'){ //사각지대, 버스노선중복 분석 제외
			if ($("#gis-lnb").css("marginLeft") != "0px"){
				$(".lnb-fold .btn-fold").trigger('click');
			}	
		}	
	}	
}

//분석정보  초기화 (동 세팅 체크, 동 geom정보)
function clearAnalsInfo(flag){
	var mapObj = getMapObj(flag);
	mapObj.popAreaSearch = false; 
	mapObj.popAreaPoly = null;
	//분석결과 레이어 tree map 클리어
	mapObj.wmsLayerInfos = new Map();
	//레이어 결과 분석 tools hide
	openTreePopup(false, flag);
	//시계열 체크
	mapObj.chkLyrLoading = false;
	//분석 wms layer 제거
	mapObj.clearRemoveLayer();
}

//컨크롤 제거
function clearControlInfo(flag){
	var mapObj = getMapObj(flag);
	mapObj.clearRemoveControl("SELFEATURE");
}

//팝업 제거
function clearPopupInfo(flag){
	var mapObj = getMapObj(flag);
	// mapObj.clearPopupsById("busInfo");
}

//지도 zoombar 클릭
function clickZoombar(evt, flag){
	var sliderTop = $('#toolSliser')[0].style.top.replace('px','');
	var clickY = evt.offsetY;
	if(Number(sliderTop) > Number(clickY)){
		clickMapTool('zoomout', flag);
	}else{
		clickMapTool('zoomin', flag);
	}
}


//현재 분석영역 저장 팝업 open
function openRsPopup(view, mapType){
	if(view){
		var mapObj = getMapObj(mapType);
		var chkFtCnt = false;
		//사용자 지정영역 레이어에서 피쳐정보 확인
		var layer = mapObj.getLayer("analysisLayer");
		if( layer.getSource().getFeatures().length > 0 && layer.getSource().getFeatures()[0].get("type") == "user" )
			chkFtCnt = true;
		//사용자 지정영역 설정 체크
		if(chkFtCnt)
			$('#divSavePop').show();
		else
			openInfoPopup(true,"사용자 지정영역이 설정되어 있지 않습니다.");
	}else
		$('#divSavePop').hide();
}

//지도 조작도구 클릭
function clickMapTool(key, mapType){
	var mapObj = getMapObj(mapType);
	if(key == 'zoomin'){
		mapObj.mapTools.zoomMap(false);
	}else if(key == 'zoomout'){
		mapObj.mapTools.zoomMap(true);		
	}else if(key == 'clear'){
		mapObj.clearMap();
	}else if(key == 'line'){
		$('#liArea').removeClass("active");
		if($('#liDist').hasClass("active")){
			$('#liDist').removeClass("active");
			clearMeasureInfo(mapObj, "ms", "line");
		}else{
			$('#liDist').addClass("active");
			mapObj.activeControl('ms', key);			
		}
	}else if(key == 'area'){
		$('#liDist').removeClass("active");
		if($('#liArea').hasClass("active")){
			$('#liArea').removeClass("active");
			clearMeasureInfo(mapObj, "ds", "area");
		}else{
			$('#liArea').addClass("active");
			mapObj.activeControl('ms', key);			
		}
	}else if(key == 'save'){
		saveImageMap("gis-area");
	}else if(key == 'road'){
		roadView(mapType);
	}else if(key == 'measure'){
		if($("#liMeasure").hasClass("active")){			
			clearMeasureImg();			
			clearMeasureInfo(mapObj, "ms", "line");
			clearMeasureInfo(mapObj, "ds", "area");
		}else{
			$("#liMeasure").addClass("active");
		}
	}else if(key == 'layer'){
		//분석결과 레이어
		if($('#liLayer').hasClass("active")){			
			openTreePopup(false, mapType);
			$('#liLayer').removeClass("active");
		}else{
			openTreePopup(true, mapType);
			$('#liLayer').addClass("active");
		}
	}
}

//측정도구 이미지 초기화
function clearMeasureImg(){
	$('.area').removeClass("active");
	$('.distance').removeClass("active");
	$("#liMeasure").removeClass("active");
}

//측정도구 초기화
function clearMeasureInfo(mapObj, control, name){
	mapObj.clearControl(control);
	mapObj.clearVectorFeaturesById(mapObj.measureVtName, name);
	// mapObj.clearPopupsById(name);
}

//현재분석영역 저장
function clickRadiusSave(mapType){
	var mapObj = getMapObj(mapType);
	//var menu = mapObj.mapMenu;
	var name = $('#txtAnalsNm').val();
	var layer = mapObj.getLayer("analysisLayer");
	if(layer.getSource().getFeatures().length > 0 ){
		if(name != ''){
			var wkt = mapObj.getWktFromGeometry( layer.getSource().getFeatures()[0].getGeometry() );
			console.log("wkt", wkt);
			$.ajax({
				type:"POST",
				url:"/gis/comm/addBmk.do",		
				dataType:'json',
				data:{geom:wkt,
					coord:mapObj.getProjection().replace("EPSG:", ''),
					name:name
				},
				success:function(data){
					var chk =data.result;
					if(chk == 'ok'){
						openRsPopup(false, mapType);
						selectBookmarkList("selBookmark");
						openInfoPopup(true,"저장하였습니다.");						
					}else{
						openInfoPopup(true,"사용자 지정영역 저장 중 에러가 발생하였습니다.");
					}
				},
				error:function(err){
					openInfoPopup(true,"사용자 지정영역 저장 중 에러가 발생하였습니다.");
				}
			});	
		}else
			openInfoPopup(true,"영역명을 입력하여 주시기 바랍니다.");
		
	}else{
		openInfoPopup(true,"사용자 지정영역이 설정되어 있지 않습니다.");
	}	
}

//사용자영역지정
function clickUsrArea(mapType, flag){
	var mapObj = getMapObj(mapType);
	console.log("mapObj", mapObj)
	if(flag == 'create'){
		mapObj.clearRemoveLayer();
		var gbn = $(":input:radio[name=rdUsrRadius]:checked").val();
		if( gbn == "polygon" ) {
			mapObj.togglePolygonInteraction(true);
		}
		else if( gbn == "point" ) {
			mapObj.togglePointInteraction(true);
		}
		else if( gbn == "layer" && $('#selBookmark').val() != "" ) {
			var seq = $('#selBookmark').val();
			
			var feature = mapObj.getWktToGeometry($('#'+"hid"+$('#selBookmark').val()).val(), "EPSG:4326");
			console.log("feature", feature.getGeometry());
			var layer = mapObj.getLayer("analysisLayer");
			layer.getSource().addFeature(feature);
			mapObj.zoomToLayer("analysisLayer");
		}
	}else if(flag == 'clear'){
		mapObj.clearRemoveLayer();
		//mapObj.clearAllVectorFeatures(mapObj.vectorRdName);
	}else if(flag == 'del'){		
		var seq = $('#selBookmark').val();
		if(seq != undefined && seq != null && seq != ''){
			if(confirm("선택한 지정영역을 삭제하시겠습니까?")){
				$.ajax({
					type:"POST",
					url:"/gis/comm/delBmk.do",		
					dataType:'json',
					data:{seq:seq},
					success:function(data){
						var chk =data.result;
						if(chk == 'ok'){					
							selectBookmarkList("selBookmark");
							openInfoPopup(true,"삭제하였습니다.");
							mapObj.clearRemoveLayer();
						}else{
							openInfoPopup(true,"사용자 지정영역 삭제 중 에러가 발생하였습니다.");
						}
					},
					error:function(err){
						openInfoPopup(true,"사용자 지정영역 삭제 중 에러가 발생하였습니다.");
					}
				});	
			}			
		}else{
			openInfoPopup(true,"선택된 사용자지정영역이 없습니다.");
		}
	}
}

//로드뷰 실행
function roadView(mapType){	
	if($('.btn-roadmap').hasClass('active')){		
		closeRoadView();
	}else{		
		DaumRoadView.init(getMapObj(mapType));
		DaumRoadView.activeOpenRoadView();
		$('.btn-roadmap').addClass('active');		
	}
}

//로드뷰 종료
function closeRoadView(){
	DaumRoadView.exitRoadView();
	$('.btn-roadmap').removeClass('active');
}

//지도 이미지 저장
function saveImageMap(mapId){
	if (confirm('지도를 저장 하시겠습니까?')) {
		try{		
			html2canvas($('#'+mapId)[0], {
				//allowTaint: true,
				//async: false,
				//logging: true,
				proxy : '/gis/comm/proxy.do',
	            useCORS: true	            
	        }).then(function (canvas) {
	            console.log(canvas);	            
	            if (navigator.msSaveBlob) {                
	                var BlobBuilder = window.MSBlobBuilder;
	                navigator.saveBlob=navigator.msSaveBlob;
	                var imgBlob = canvas.msToBlob();
	                if (BlobBuilder && navigator.saveBlob) {
	                    var showSave =  function (data, name, mimetype) {
	                        var builder = new BlobBuilder();
	                        builder.append(data);
	                        var blob = builder.getBlob(mimetype||"application/octet-stream");
	                        if (!name)
	                            name = "Download.bin";
	                        navigator.saveBlob(blob, name);
	                    };
	                    showSave(imgBlob, 'map.png',"image/png");
	                }
	            } else {
	                var canvasDataURL = canvas.toDataURL('image/png', 1.0);
	                var aTag = document.createElement('a');
	                aTag.crossorigin="anonymous";
	                aTag.download = 'map.png';
	                aTag.href = canvasDataURL;
	                aTag.click();
	            }
	        });
		}catch(err){
			openInfoPopup(true, err.message);
		}        
        /*
		html2canvas($('#'+mapId)[0], 
	    {
            onrendered: function (canvas) {
                var img = canvas.toDataURL("image/png");
                alert('This will currently open image in a new window called "data:". Instead I want to save to users local computer. Ideally as a jpg instead of png.');
                window.open(img);
            }
         });  
         */ 
    }
}

//알림창 팝업 open
function openInfoPopup(chk, lbl){
	$('#lblInfo').html('');
	if(chk){
		$('#lblInfo').html(lbl);
		$('#divInfoPop').show();
	}else
		$('#divInfoPop').hide();		
}

//범례창 닫기
function openLegendPopup(chk){	
	$('#divMapLegend').hide();
	$('#divLegendCont').html("");
}

//분석결과 레이어 opacity 처리 팝업
function openTreePopup(chk, mapType){
	if(chk){
		//레이어 목록 create
		var html = "";
		var mapObj = getMapObj(mapType);	
		var layersInfo = mapObj.wmsLayerInfos;
		for(var i=0; i<layersInfo.size(); i++){			
			var layerId = layersInfo.keys()[i];
			var layerName = layersInfo.get(layersInfo.keys()[i]);
			var layer = mapObj.map.getLayersByName(layerId)[0];			
			html += "<li>";
			html += "<span class='head2'>"; 						
			html += "<img src='/images/gis/ico-heatmap.gif' alt='히트맵 표시' class='img-lgd1'/>";
			if(layer.getVisibility())
				html += "<input onchange='changeShow(this.checked,\""+layerId+"\",\""+mapType+"\")' type='checkbox' name='chk"+layerId+"' id='chk"+layerId+"' class='chckbox' checked>";
			else
				html += "<input onchange='changeShow(this.checked,\""+layerId+"\",\""+mapType+"\")' type='checkbox' name='chk"+layerId+"' id='chk"+layerId+"' class='chckbox'>";
			html += "<label for='chk"+layerId+"'>"+layerName+"</label>";
			html += "</span>";
			html += "<input type='range' onchange='changeOpacity(this.value,\""+layerId+"\",\""+mapType+"\")' value='"+(layer.opacity * 100)+"' class='range-slider'>";
			html += "<a href='#' onclick='removeAnalsLayer(\""+layerId+"\",\""+mapType+"\")'><img src='/images/cmm/ico-bin.jpg'></a>";
			html += "</li>";
		}		
		$('#ulPopupLayer').html(html);
		$('#liLayer').addClass("active");
		$('#divAnalsResult').show();
	}else{
		$('#liLayer').removeClass("active");
		$('#divAnalsResult').hide();
	}
}

//엑셀 팝업 show
function openExcelPopup(chk){
	if(chk){
		var id = getMenuId();
		//해당 id의 show, hide여부
		if(id != undefined && id != null && id != ''){
			if($('#t'+id).css('display') != 'none'){
				$('#rdExcelOne')[0].checked = true;
				$('#txtExcelEtc').val("");
				$('#rdExcelAgree')[0].checked = false;
				$('#divPopExcel').show();	
			}else{
				openInfoPopup(true,"검색결과가 없습니다.");
			}
		}else
			openInfoPopup(true,"검색결과가 없습니다.");	
	}else{
		$('#divPopExcel').hide();
	}
}

//레이어 opacity 변경
function changeOpacity(val, layerId, mapType){
	getMapObj(mapType).mapCustom.changeLayerOpacity(val, layerId);
}
//레이어 view
function changeShow(view, layerId, mapType){
	getMapObj(mapType).mapCustom.changeLayerShow(view, layerId);
}

//범례 레이어 제거
function removeAnalsLayer(layerId, mapType){
	if(chkTsAnals(mapType)){ //common.js 시계열 분석 체크	
		if(layerId != undefined && layerId != null && layerId != ''){
			var mapObj = getMapObj(mapType);
			mapObj.clearRemoveLayer(layerId);
			mapObj.wmsLayerInfos.remove(layerId);
			//데이터 결과 초기화
			var id = layerId.replace("popLayer", "");
			$('#li'+id).trigger('click'); //해당 tab활성화
			var gridId = "tblGrid" + id;
			if(id == "1-1-3" || id == "1-1-4"){
				//그리드가 2개씩 존재
				$('#'+gridId+"-1").jqGrid('clearGridData');	//grid 클리어
				$('#'+gridId+"-2").jqGrid('clearGridData');	//grid 클리어
			}else
				$('#'+gridId).jqGrid('clearGridData');	//grid 클리어
			
			var chartId = "divChart" + id;		
			$('#'+chartId).html(""); //차트 클리어
			//bar와 line구분		
			if(id == '1-1-3' 
				|| id == '1-1-4' 
				|| id == "1-4-4" 
				|| id == "1-4-5" 
				|| (id.indexOf("1-5") > -1))
				settingBarChartData(chartId, [[null]], [], '', '');
			else{
				//chart없는경우
				var chkDelChart = true;
				if(id == '2-1-1' 
					|| id == '2-2-1'
					|| id == '2-4-1'
					|| id == '2-5-1'
					|| id == '2-5-2'
					|| id == '2-6-1'
					|| id == 'StationLayer'
					|| id == 'oldGrid'					
				){
					chkDelChart = false;
				}				
				if(chkDelChart)
					settingLineChartData(chartId, [[null]], [], '', '');
			}
			//탭 숨김
			var chkTabShow = true;
			if(id == 'StationLayer' || id == 'oldGrid')
				chkTabShow = false;
			
			if(chkTabShow){
				showMapResult(false);
				$('#t'+id).hide();
			}
			clearLegendInfo(); //범례창 제거
			
			var chkMap = mapObj.wmsLayerInfos.size();
			if(chkMap == 0){				
				openTreePopup(false, mapType);
			}else{
				openTreePopup(true, mapType);
			}
		}
	}
}