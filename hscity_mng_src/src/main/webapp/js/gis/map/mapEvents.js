/*********************************************
 * 
 * 지도조작 이벤트 객체 생성
 * 
 ********************************************/
MapEvents = OpenLayers.Class({
	hsctMap  :   null,  //지도 객체
	//생성자
	initialize : function(obj){		
		this.hsctMap = obj;
	},
	//사용자 지정 반경 영역
	radiusPointFinish : function(e){
		var mapObj = getMapObj(this.map.mapType);		
		var params = {lon:e.y, lat:e.x, radius: $('#selUsrRadius').val()};
		$.ajax({type: 'POST',url: "/gis/raiusPolygon.do", data:params, async: false, dataType: "json",
	        success: function(data) {	        	
	        	if(data != undefined && data != null && data != ''){
					if(data.result.length > 0){
						var radiusGeom = new OpenLayers.Format.WKT().read(data.result[0].wkt);
						radiusGeom.attributes.name = "";
						var layer = mapObj.map.getLayersByName(mapObj.vectorRdName)[0];
						if(layer != undefined && layer != null){
							layer.addFeatures([radiusGeom]);
							mapObj.map.zoomToExtent(radiusGeom.geometry.getBounds());
						}
						mapObj.clearControl('ds');
					}
	        	}
	        }
		});
	},
	//사용자 지정 다각형 폴리곤 생성
	radiusPolygonFinish : function(e){
		var mapObj = getMapObj(this.map.mapType);
		mapObj.drawRadiusPoints = [];	
		mapObj.clearAllVectorFeatures(mapObj.vectorRdName); //라벨피쳐삭제		
		mapObj.clearControl("ds");
		var polygon = new OpenLayers.Feature.Vector(e);
		//var area = polygon.geometry.getArea(); //면적 제한		
		var layer = mapObj.map.getLayersByName(mapObj.vectorRdName)[0];		
		polygon.attributes.name = "" ;
		polygon.attributes.type = "area";				
		layer.addFeatures([polygon]);
	},
	//사용자 지정 다각형 생성 중 이벤트
	handleUsrAreaDraw : function(event){
		var mapObj = getMapObj(this.map.mapType);
		mapObj.drawRadiusPoints.push(new OpenLayers.Geometry.Point(event.x, event.y));
		if(mapObj.drawRadiusPoints.length > 1){
			//최소3개 포인트가 이상이 그려져야 면적이 계산됨
			var ring = new OpenLayers.Geometry.LinearRing(mapObj.drawRadiusPoints);			
			var polygon = new OpenLayers.Geometry.Polygon([ring]);
			var area = polygon.getArea(); //면적  
			var center = polygon.getCentroid(); //중심점
			mapObj.clearVectorFeaturesById(mapObj.vectorRdName, "area"); //라벨피쳐삭제
			var calcArea = area.toFixed(1);
			calcArea = Number(calcArea).toLocaleString('en') + "㎡";
			mapObj.pointAreaLabel({lon : center.x, lat : center.y }, "면적 : "+ calcArea, mapObj.vectorRdName);
		}
	},
	handleAreaMeasurements : function(event){
		var mapObj = getMapObj(this.map.mapType);
		mapObj.drawRadiusPoints.push(new OpenLayers.Geometry.Point(event.x, event.y));
		if(mapObj.drawRadiusPoints.length > 1){
			//최소3개 포인트가 이상이 그려져야 면적이 계산됨
			var ring = new OpenLayers.Geometry.LinearRing(mapObj.drawRadiusPoints);			
			var polygon = new OpenLayers.Geometry.Polygon([ring]);
			var area = polygon.getArea(); //면적  
			var center = polygon.getCentroid(); //중심점
			mapObj.clearVectorFeaturesById(mapObj.measureVtName, "area"); //라벨피쳐삭제
			var calcArea = area.toFixed(1);
			calcArea = Number(calcArea).toLocaleString('en') + "㎡";
			mapObj.pointAreaLabel({lon : center.x, lat : center.y }, "면적 : "+ calcArea, mapObj.measureVtName);
		}
	},
	//면적 측정
	distPolygonFinish : function(e){
		var mapObj = getMapObj(this.map.mapType);
		mapObj.clearVectorFeaturesById(mapObj.measureVtName, "area"); //라벨피쳐삭제
		mapObj.drawRadiusPoints = [];
		mapObj.clearControl("ms");
		var polygon = new OpenLayers.Feature.Vector(e);
		var area = polygon.geometry.getArea(); //면적 제한 
		var center = polygon.geometry.getCentroid();
		
		var layer = mapObj.map.getLayersByName(mapObj.measureVtName)[0];
		polygon.attributes.name = '';
		polygon.attributes.type = "area";
		polygon.attributes.strokeColor = "#003399";		
		layer.addFeatures([polygon]);
		
		var calcArea = area.toFixed(1);
		calcArea = Number(calcArea).toLocaleString('en');
		var width = (calcArea.length * 11); //zoom level포함해서 계산해야 함
		width += (5 * 10); //기타 글자수
		var html = "<div style='text-align:center; valign:middle; height:17; border: 1px; opacity:1.0; text-align=\"center\"; border-style: solid; border-color: #003399;'>";
		html += "<span style='text-align:center; align:center;'><font size='2' color='#003399'><b>총면적 : "+calcArea+"</b>㎡</font><span></div>";
		
		var popup = new OpenLayers.Popup("areaPopup",  new OpenLayers.LonLat(center.x, center.y), 
				new OpenLayers.Size(width,19), html, false);				
		popup.opacity = 0.8;
		popup.contentDiv.style.padding='0px';
		popup.contentDiv.style.margin='0';	
		mapObj.map.addPopup(popup);	
		//close BTN				
		var popup = new OpenLayers.Popup("measureareaPopup",  new OpenLayers.LonLat(center.x, center.y), 
		new OpenLayers.Size(width,20), null, true, callBackCloseArea);
		mapObj.map.addPopup(popup);		
		clearMeasureImg();	
	},
	//측정도구 측정값
	handleLineMeasurements : function(event) {
    	var mapObj = getMapObj(this.map.mapType);
    	var geometry = event.geometry;
        var units = event.units;        
        var measure = parseFloat(event.measure).toFixed(1);
        var label = measure+units;
        if(measure == 0)
        	label = "시작"; //0m
        if(event.geometry.components.length > 0)
        	geometry = event.geometry.components[event.geometry.components.length-1];        
        var pos = { lon:geometry.x, lat:geometry.y };        
        mapObj.pointLineLabel(pos, label);
    },
	//거리 라인 종료
    distLineFinish : function(e){
    	var mapObj = getMapObj(this.map.mapType);
    	mapObj.clearControl("ms");
    	//기존 feature정보 제거
    	//mapObj.clearAllVectorFeatures(mapObj.measureVtName);
    	mapObj.clearVectorFeaturesById(mapObj.measureVtName, "line"); //라벨피쳐삭제
    	//라인 그리기
    	var layer = mapObj.map.getLayersByName(mapObj.measureVtName)[0];
    	var line = new OpenLayers.Feature.Vector(e);
    	line.attributes.name = ''; //undefined
    	line.attributes.type = "line";
    	line.attributes.strokeColor = "red";
		layer.addFeatures([line]);
		//거리 라벨 추가
		if(line.geometry.components.length > 0){
			var prevX = 0;
			var prevY = 0;
			var total = 0;
			var measureCnt = 0;
			for(var i=0; i<line.geometry.components.length; i++){
				var geom = line.geometry.components[i];
				var label = "시작"; //"0m"
				var unit = "";
				if(i == 0){					
					prevX = geom.x;
					prevY = geom.y;
				}else{
					var point1 = new OpenLayers.Geometry.Point(prevX, prevY);
					var point2 = new OpenLayers.Geometry.Point(geom.x, geom.y);
					total += point1.distanceTo(point2);					
					prevX = geom.x;
					prevY = geom.y;
					if( (total/1000) < 1){
						label = total.toFixed(1);
						unit = "m";
						measureCnt = 1;
					}else{
						label = (total/1000).toFixed(1);
						unit = "km";
						measureCnt = 2;
					}
				}				
				mapObj.pointLineLabel({lon : geom.x, lat : geom.y }, ''); //라벨만 처리
				
				var width = (label.length * 17);
				if(i > 0){					
					if(measureCnt > 1){
						//km
						width = ((label.length + measureCnt) * 17);
						width -= ((measureCnt+1) * 12);
					}else{
						width = ((label.length + measureCnt) * 16);
						width -= (measureCnt * 29);
					}
				}
				var height = 19;			
				var html = "<div style='text-align:center; valign:middle; height:17; border: 1px; opacity:1.0; text-align=\"center\"; border-style: solid; border-color: red;'>";
					html += "<span style='text-align:center; align:center;'><font size='2' color='red'><b>"+label+"</b>"+unit+"</font><span></div>";
				var popup = new OpenLayers.Popup("linePopup",  new OpenLayers.LonLat(geom.x, geom.y), 
						new OpenLayers.Size(width,height), html, false);				
				popup.opacity = 0.8;				
				popup.contentDiv.style.padding='0px';
				popup.contentDiv.style.margin='0';
				mapObj.map.addPopup(popup);			
				if(i == line.geometry.components.length-1){
					//close 버튼 추가					
					var popup = new OpenLayers.Popup("measurelinePopup",  new OpenLayers.LonLat(geom.x, geom.y), 
					new OpenLayers.Size(width,20), null, true, callBackCloseLine);
					mapObj.map.addPopup(popup);
				}
			}
		}
		clearMeasureImg();
    },  
	CLASS_NAME : "MapEvents"
});

//라인 삭제
function callBackCloseLine(){
	//measure에 라인피쳐와 라인 popup 삭제
	var mapObj = getMapObj(this.map.mapType);
	mapObj.clearVectorFeaturesById(mapObj.measureVtName, "line");
	mapObj.clearPopupsById("line");		
}

//영역 삭제
function callBackCloseArea(){
	var mapObj = getMapObj(this.map.mapType);
	mapObj.clearVectorFeaturesById(mapObj.measureVtName, "area");
	mapObj.clearPopupsById("area");
}