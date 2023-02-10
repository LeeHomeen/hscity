//키워드 찾기 
var mapType = "";

function searchPlaces(type){
	mapType = type;
	var keyword = $('#keyword').val();
	if (!keyword.replace(/^\s+|\s+$/g, '')) {		
		openInfoPopup(true,'키워드를 입력해주세요!');
		closeSearchList();
	    return false;
	}
	daumKeyword.keywordSearch(keyword, placesSearchCB); 
}

//키워드 찾기 결과
function placesSearchCB(data, status, pagination) {
    if (status === daum.maps.services.Status.OK) {
        displayPlaces(data);        
        //displayPagination(pagination);
    } else if (status === daum.maps.services.Status.ZERO_RESULT) {
    	openInfoPopup(true,'검색 결과가 존재하지 않습니다.');
        closeSearchList();
        return;
    } else if (status === daum.maps.services.Status.ERROR) {
    	openInfoPopup(true,'검색 결과 중 오류가 발생했습니다.');
        closeSearchList();
        return;
    }
}

//검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {
	//http://apis.map.daum.net/web/sample/keywordList/#
	var html = "";
    for ( var i=0; i<places.length; i++ ) {
    	//마커는 여기서 표시
    	html += "<li><a href='#' onclick='itemLocation(\""+places[i].x+"\",\""+places[i].y+"\")'><img src='/images/cmm/bul1.gif'>&nbsp;<strong>"+places[i].place_name + "</strong>";
    	if(places[i].road_address_name != null && places[i].road_address_name != '')
    		html += "<br/>&nbsp;&nbsp" + places[i].road_address_name;
    	if(places[i].address_name != null && places[i].address_name != '')
    		html += "<br/>&nbsp;&nbsp" +places[i].address_name;
    	html += "</a>";
		html += "</li>";    	
    }
    if(html != ""){
    	$('#ulSearchList').html(html);
    	$('#divSearchList').show();
    }
}

//클릭 지점으로 이동
function itemLocation(x, y){
	var mapObj = getMapObj(mapType);	
	var lonlat = mapObj.mapTools.changePointCoord({lon:x ,lat:y}, mapObj.epsg4326, mapObj.map.getProjection());
	var zoomLevel = 11;
	if(mapObj.chkNMap)
		mapObj.map.setCenter(lonlat, zoomLevel);
	else
		mapObj.map.setCenter(lonlat, zoomLevel+6);
	//marker 추가
	var highlightLayer = mapObj.map.getLayersByName(mapObj.highlightName)[0];
	if(highlightLayer == undefined || highlightLayer == null){
		highlightLayer = new OpenLayers.Layer.Vector(mapObj.highlightName, {
			 styleMap: new OpenLayers.StyleMap({'default':{
				 externalGraphic: '/images/cmm/map-pin.png',
	               graphicWidth: 22, 
	               graphicHeight: 32, 
	               graphicXOffset: -(22/2), 
	               graphicYOffset: -(32/2),
	               title: 'point'
		        }}),
	       isBaseLayer: false,
	       renderers: mapObj.getRenderer()	       	
	    });
		mapObj.map.addLayer(highlightLayer);            
	}else{
		//기존 features 삭제
		mapObj.clearAllVectorFeatures(mapObj.highlightName);
	}
	var pointMarker = new OpenLayers.Geometry.Point(lonlat.lon,lonlat.lat);			 
    var pointFeature = new OpenLayers.Feature.Vector(pointMarker);	            
    highlightLayer.addFeatures([pointFeature]);
    highlightLayer.redraw();
}

//키워드 검색창 종료
function closeSearchList(){
	$('#keyword').val('');
	$('#divSearchList').hide();
	$('#ulSearchList').html('');
}
