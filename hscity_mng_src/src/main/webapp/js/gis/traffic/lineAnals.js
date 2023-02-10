/**
*
* 교통 분석 관련 js (버스노선 중복 분석)
*
**/

/**
 * 페이지 초기화
 */
function initLinePageInfo(flag, menu){	
	var mapObj = getMapObj(flag);
	mapObj.mapMenu = menu;	
	var chkFlag = '2-6-1';		
	//결과 표출
	mapObj.mapTools.basicLocation(); //기본 위치로 이동
	setLineResult(chkFlag, flag);
}

///////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////중복노선 결과//////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////

function setLineResult(id, mapType){	
	var mapObj = getMapObj(mapType);
	openTreePopup(false, mapType); //분석결과 트리 hide	
	//지도상에 중복도 표시
	var coord = mapObj.map.getProjection().replace("EPSG:", '');
	var dupParams = {coord:coord};	
	$('#divMapLoading').show();
	
	$.ajax({
		type:"POST",
		url:"/gis/traffic/dupList.do",
		dataType:'json',
		async:true,
		data: dupParams,
		async: true,
		success:function(data){
			//검색된 결과의 제일 최상단 결과에 해당하는 정보를 시각화
			if(data.result != null && data.result.length > 0){
				var datas = getAnalsLineParams(id, mapType);
				var layer = setHighlightLayer(datas, true, true);
				var obj = data.result;
				var stopMap = new Map(); //정류장 중복 구분 map
				for(var i=0; i<obj.length; i++){
					var geom = new OpenLayers.Format.WKT().read(obj[i].bline);
					var stkColor = 'blue';
					geom.attributes = {coord: coord, name: '', label : obj[i].bus, stkStyle:"solid", radius : 0, stkColor: stkColor, stkWidth: obj[i].cnt, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
					layer.addFeatures([geom]);	
					
					//2. 정류장 그리기
					var stop = stopMap.get(obj[i].bsid);
					if(stop == undefined || stop != null){								
						stopMap.put(obj[i].bsid, obj[i].bsid);
						geom = new OpenLayers.Format.WKT().read(obj[i].bp);
						geom.attributes = {coord: coord, name: '', label:obj[i].bsnm, stkStyle:"solid", radius : 3, stkColor: stkColor, stkWidth: 1, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
						layer.addFeatures([geom]);
					}
					stop = stopMap.get(obj[i].nsid);
					if(stop == undefined || stop != null){								
						stopMap.put(obj[i].nsid, obj[i].nsid);
						geom = new OpenLayers.Format.WKT().read(obj[i].np);
						geom.attributes = {coord: coord,name: '', label:obj[i].nsnm, stkStyle:"solid", radius : 3, stkColor: stkColor, stkWidth: 1, fllColor:'#FFDD73', fllOpacity:0.5,fllWidth:2};
						layer.addFeatures([geom]);
					}
				}
				//mapObj.map.zoomToExtent(layer.getDataExtent());
			}else{
				openInfoPopup(true,"검색결과정보가 없습니다.");
			}				
		},
		error:function(err){
			if(chkSession(err.status)){
				openInfoPopup(true,"조회 중  에러가 발생하였습니다.");
			}
		}
	});
}

//param설정
function getAnalsLineParams(id, mapType){	 
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
