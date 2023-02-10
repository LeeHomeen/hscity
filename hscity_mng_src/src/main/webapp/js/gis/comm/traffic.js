/***
 * 교통 관련 공통  js
 */

//정류장 정보
function setStaResult(mapType){
	var mapObj = getMapObj(mapType);
	var params = {};
	var coord = mapObj.map.getProjection().replace("EPSG:", '');
	$.ajax({
		type:"POST",
		url:"/gis/traffic/staList.do",
		dataType:'json',
		async:true,
		data: {coord: mapObj.map.getProjection().replace("EPSG:", '')},
		async: true,
		success:function(data){
			if(data.result != null && data.result.length > 0){
				//sta vector layer에 표시				
				params.layerName = mapObj.stationName; //공통레이어
				params.analsName = "정류장(점형)";
				var layer = mapObj.addCommVtLayer(params);
				layer.attributes = {params:{params:{aid:"station"}}};				
				var arrGeom = [];
				for(var i=0; i<data.result.length; i++){
					var obj = data.result[i];
					var geom = new OpenLayers.Format.WKT().read(obj.geom);
					geom.attributes = {coord: coord, name:'', label:obj.cnm, radius: 4, fllColor: 'red', fllOpacity: 0.5};
					arrGeom.push(geom);
				}
				if(arrGeom.length > 0)
					layer.addFeatures(arrGeom);
			}				
		}
	});
}
/**
 * custom 위치확인
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns {String}
 */
function cstLoc(cellValue, option, rowObject){
	var item = rowObject.item;
	var mid = rowObject.mid; //메뉴id
	//사각지대 이동 : blindAnals.js
	return "<a href='#' onclick='moveBlindLocation(\""+item+"\", \""+mid+"\")'>위치확인</a>";
}


/**
 * 사각지대 위치 이동
 * @param item
 */
function moveBlindLocation(item, mid){
	var mapType = 'traffic';
	var mapObj = getMapObj(mapType); //traffic전용
	mapObj.clearAllVectorFeatures(mapObj.highlightName); //clear highlight layer
	
	var geom = new OpenLayers.Format.WKT().read(item);
	mapObj.map.zoomToExtent(geom.geometry.getBounds());
	var datas = {layerName:mapObj.highlightName, mapType:mapType, analsName:''};
	var layer = setHighlightLayer(datas, false, false);
	geom.attributes = {name:'', label: '', stkColor: '#CC3D3D', stkWidth: 3, fllColor:'white', fllOpacity:0};
	layer.addFeatures([geom]);	
}


/**
 * 버스정류장 확인 (checkbox type)
 */
//param으로 rowObject 못넘김
function cstBusStop(cellValue, option, rowObject){
	var geom = rowObject.geom;
	var sid = rowObject.sid; //정류장 id
	var mid = rowObject.mid; //메뉴 id
	var coord = rowObject.coord; //좌표체계
	var div = rowObject.div;
	var html = "";
	if(sid != undefined && sid != null && sid != '')
		html = "<input onchange='drawBusStop(this,\""+geom+"\", \""+sid+"\", \""+mid+"\", \""+coord+"\", \""+div+"\")' type='checkbox' style='width:15px; height:15px;'/>"; 
	return html;
}

function drawBusStop(obj, geom, sid, mid, coord, name){
	if(obj.checked){
		moveStopLocation(geom, false, sid, mid, coord, name);
	}else{
		var mapObj = getMapObj('traffic'); //traffic전용
		mapObj.clearVectorFeaturesById("popLayer"+mid, sid);
	}
}

/**
 * 버스정류장 확인 (link type)
 * @param cellvalue
 * @param options
 * @param rowObject
 */
function cstStop(cellValue, option, rowObject){
	var item = rowObject.item;
	var div = rowObject.div; 
	var sid = rowObject.sid; //정류장id
	var mid = rowObject.mid; //메뉴id
	var coord = rowObject.coord; //좌표체계	
	return "<a href='#' onclick='moveStopLocation(\""+item+"\", true, \""+sid+"\", \""+mid+"\", \""+coord+"\", \""+div+"\")'>"+div+"</a>";
}

/**
 * 버스정류장 확인 (통행패턴용)
 */
function cstPassStop(cellValue, option, rowObject){
	var fnm =  rowObject.fnm;
	var geom = rowObject.fp;
	var pop = rowObject.pop;
	return "<a href='#' onclick='movePassStopLocation(\""+geom+"\",\""+fnm+"\",\""+pop+"\")'>"+fnm+"</a>";	
}

/**
 * 하이라이트 레이어를 사용하여 정류장으로 이동
 * @param geom
 * @param fnm
 * @param pop
 */
function movePassStopLocation(geom, fnm, pop){
	var mapType = 'traffic';
	var mapObj = getMapObj(mapType); //traffic전용
	mapObj.clearAllVectorFeatures(mapObj.highlightName); //clear highlight layer	
	var item = new OpenLayers.Format.WKT().read(geom);	
	mapObj.map.zoomToExtent(item.geometry.getBounds());
	
	var datas = {layerName:mapObj.highlightName, mapType:mapType, analsName:''};
	var layer = setHighlightLayer(datas, false, false);
	var fname = fnm + "\n(하차승객수:"+pop+")";
	item.attributes = {fontSize: 12, name:fname, label: '',
			xOffset:7, yOffset:-05,align:'lm', radius : 6,
			stkColor: 'red', stkWidth: 3, fllColor:'white', fllOpacity:0};
	layer.addFeatures([item]);	
}

/**
 * 정류장 위치 이동
 * @param geometry정보, 삭제여부, pk id
*/
function moveStopLocation(item, chkClear, sid, mid, oriCoord, name){
	var mapType = 'traffic';
	var mapObj = getMapObj(mapType); //traffic전용
	var coord = mapObj.map.getProjection().replace("EPSG:", '');
	if(chkClear)
		mapObj.clearAllVectorFeatures("popLayer"+mid); //clear highlight layer
	
	var geom = new OpenLayers.Format.WKT().read(item);
	geom.geometry.transform(new OpenLayers.Projection("EPSG:"+oriCoord), mapObj.map.getProjection());
	
	mapObj.map.zoomToExtent(geom.geometry.getBounds());
	if(mid == '2-3-1')
		datas = getAnalsCardParams(mid, mapType);
	else if(mid == '2-4-1')
		datas = getAnalsFacParams(mid, mapType);
	var layer = setHighlightLayer(datas, false, true);	 
	geom.attributes = {name: name, coord: coord, type: sid, 
			radius : 10, stkColor: 'red', stkWidth: 1, 
			fllColor:'#FFDD73', fllOpacity:0.5,
			align: "cm", xOffset: 25, yOffset: -05};
	layer.addFeatures([geom]);	
}

/**
 * 버스라인 확인
 * @param cellvalue
 * @param options
 * @param rowObject
*/
function cstBusLine(cellValue, option, rowObject){
	var lid = rowObject.lid;
	var mid = rowObject.mid; //메뉴id
	var html = "";
	if(lid != undefined && lid != null && lid != '')
		html = "<input onchange='drawBusLine(this,\""+lid+"\",\""+mid+"\")' type='checkbox' style='width:15px; height:15px;'/>";	
	return html;
}

/**
 * 버스노선 및 관련 정류장 그리기
 * @param obj
 * @param lid
 */
function drawBusLine(obj, lid, mid){	
	if(obj.checked){
		var mapType = 'traffic';
		var mapObj = getMapObj(mapType); //traffic전용
		var coord = mapObj.map.getProjection().replace("EPSG:", '');		
		$.ajax({
			type:"POST",
			url:"/gis/traffic/lineList.do",
			data:{lid:lid,  coord: coord},
			dataType:'json',		
			success:function(data){
				if(data != undefined && data != null && data != ''){
					if(data.result.length > 0){
						var datas = null;
						if(mid == '2-2-1')
							datas = getAnalsBusParams(mid, mapType);
						else if(mid == '2-5-1' || mid == '2-5-2')
							datas = getAnalsArrParams(mid, mapType);
						var layer = setHighlightLayer(datas, false, true);
						var obj = data.result;
						var stopMap = new Map(); //정류장 중복 구분 map						
						for(var i=0; i<obj.length; i++){
							//1. 라인그리기
							var geom = new OpenLayers.Format.WKT().read(obj[i].line);
							var rid = obj[i].rid;
							var stkColor = 'blue';
							if(rid != '1')
								stkColor = 'red';
							var label = obj[i].div + "("+obj[i].otype+")";
							geom.attributes = {name: '', label:label, coord: coord, type: lid, radius : 0, stkColor: stkColor, stkWidth: 2, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
							layer.addFeatures([geom]);	
							
							//2. 정류장 그리기
							var stop = stopMap.get(obj[i].bsid);
							if(stop == undefined || stop != null){								
								stopMap.put(obj[i].bsid, obj[i].bsid);
								geom = new OpenLayers.Format.WKT().read(obj[i].bp);
								geom.attributes = {name: '', label:obj[i].bsnm, coord: coord, type: lid, radius : 3, stkColor: stkColor, stkWidth: 1, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
								layer.addFeatures([geom]);
							}
							stop = stopMap.get(obj[i].nsid);
							if(stop == undefined || stop != null){								
								stopMap.put(obj[i].nsid, obj[i].nsid);
								geom = new OpenLayers.Format.WKT().read(obj[i].np);
								geom.attributes = {name: '', label:obj[i].nsnm, coord: coord, type: lid, radius : 3, stkColor: stkColor, stkWidth: 1, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
								layer.addFeatures([geom]);
							}
						}					
						//위치이동
						mapObj.map.zoomToExtent(layer.getDataExtent());
					}
				}
			},
			error:function(err){
				openInfoPopup(true,"업종정보(중분류) 조회 중 에러가 발생하였습니다.");
			}
		});
	}else{
		var mapObj = getMapObj('traffic'); //traffic전용
		mapObj.clearVectorFeaturesById("popLayer"+mid, lid);
	}
}

/**
*  피처 정보 도움말
*/
function setFeatureLabel(obj){
	var label = obj.feature.attributes.label;
	if(label != undefined && label != null && label != ''){
		var popId = "busInfo";
		var mapType = 'traffic';
		var mapObj = getMapObj(mapType);
		popupClose(popId, mapType);
		var arrLabel = label.split(',');
		var html = '';
		var max = 0;
		var height = 0;
		if(arrLabel.length > 0){
			html += "<div class='cont'>";			
			for(var i=0; i<arrLabel.length; i++){
				height++;
				var label = arrLabel[i];
				html += label;
				if(max < label.length)
					max = label.length;
				if(i<(arrLabel.length-1))
					html += "<br/>";
			}	
			html += "</div>";
		}		
		var width = ((max * 12)+12) + 20;
		height = (height * 15) + 50; 
		if(html != ''){			
			var header = '<p class="tit"><img class="infopop" src="/images/gis/ico-info.png">&nbsp;상세정보</p>';
			header += "<button href='#' onclick='popupClose(\""+popId+"\",\""+mapType+"\")' class='btn-close'>닫기</button>";			
			if(width < 100)
				width = 120;
			if(height > 280)
				height = 280;
			html = "<div class='pop sr1 st1' style='width:"+(width)+";height:"+(height)+"px;'>"+ header + html + "</div>";
		}
		
		var centerXY = obj.feature.geometry.getCentroid();
		var popup = new OpenLayers.Popup(
				popId,
                {lon:centerXY.x, lat:centerXY.y},
                new OpenLayers.Size((width+3), (height + 3)),
                html,
                false,
                false
        );
		mapObj.map.addPopup(popup);
	}
}

/**
 * 팝업 닫기
 */
function popupClose(popId, mapType){
	var mapObj = getMapObj(mapType);
	mapObj.clearPopupsById(popId);
}