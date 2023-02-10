/*********************************************
 * 
 * 지도관련 custom 기능 객체 생성
 * 
 ********************************************/
MapCustom = OpenLayers.Class({
	hsctMap  :   null,  //지도 객체
	//생성자
	initialize : function(obj){		
		this.hsctMap = obj;
	},
	//분석시 맵 위치 이동
	mapAnalsLocation : function(mapType){
		//행정동 경계
		var flag = true;
		if($('#liChkArea').hasClass("active")){
			// 행정동 경계 불러오기
			
			
			
			//행정동 경계일 경우 위치 이동
			if(this.hsctMap.popAreaPoly != null){
				this.hsctMap.map.zoomToExtent(this.hsctMap.popAreaPoly.geometry.getBounds());
			}else
				flag = false;
		}else if($('#liChkUser').hasClass("active")){
			//사용자 지정영역 (vector layer에서 영역정보 가져온다.)
			var featureWkt = this.getUserArea();
			if(featureWkt != null){
				var userGeom = new OpenLayers.Format.WKT().read(featureWkt);
				this.hsctMap.map.zoomToExtent(userGeom.geometry.getBounds());						
			}else{
				flag = false;
				openInfoPopup(true,"설정된 영역정보가 없습니다.");
			}			
		}
		return flag;
	},
	//분석시 맵위치 이동 (법정동)
	mapCstAnalsLocation : function(mapType, emdcd, params){
		var flag = true;
		var wkt = getSelectCstAreas(mapType, emdcd);
		if(wkt != null){			
			this.hsctMap.map.zoomToExtent(wkt.geometry.getBounds());
			//벡터 레이어에 wkt 경계 추가
			var layer = this.hsctMap.map.getLayersByName(params.layerName)[0];
			if(layer == undefined || layer == null){		
				layer = new OpenLayers.Layer.Vector(params.layerName, {
					//해당스타일은 거리 측정시 라인모양 및 라벨 스타일 지정
			        styleMap: new OpenLayers.StyleMap({
			        	"default": { 
				            strokeColor: "#002266",
				            strokeOpacity: 0.1,
				            strokeWidth: "1",
				            fillOpacity: 0.4,				            
				            fillColor: "#FFEE85"		            
				        }
			        }),
			        renderers: this.hsctMap.getRenderer()
			    });
				layer.attributes = {};
				var vs = params.params.visible;
				if(vs != undefined && vs != null && vs != '')
					params.params.visible = vs;
				else
					params.params.visible = true;
				var op = params.params.opacity;
				if(op != undefined && op != null && op != '')
					params.params.opacity = op;
				else
					params.params.opacity = 1;
				params.params.emdcd = emdcd;
				layer.attributes.params = params;								
				this.hsctMap.map.addLayer(layer);
				this.hsctMap.wmsLayerInfos.put(params.layerName, params.analsName);
			}						
			layer.setVisibility(params.params.visible);
			layer.setOpacity(params.params.opacity);
			layer.addFeatures([wkt]);
			layer.redraw();
		}else
			flag = false;
		return flag;
	},
	//사용자 지정영역 wkt
	getUserArea : function(){
		var featureWkt = null;
		var layer = this.hsctMap.map.getLayersByName(this.hsctMap.vectorRdName)[0];
		if(layer != undefined && layer != null){
			if(layer.features.length > 0){
				featureWkt = new OpenLayers.Format.WKT().write(layer.features[0]);
			}
		}	
		return featureWkt;
	},
	//레이어 투명도 조절
	changeLayerOpacity : function(val, layerId){
		var layer = this.hsctMap.map.getLayersByName(layerId)[0];
		if(layer != undefined && layer != null){
			layer.setOpacity(val/100);
			layer.redraw();
		}
	},	
	//선택 레이어 view
	changeLayerShow : function(view, layerId){
		var layer = this.hsctMap.map.getLayersByName(layerId)[0];
		if(layer != undefined && layer != null){
			layer.setVisibility(view);
			layer.redraw();
		}		
	},
	//사용자 지정영역 지도에 추가
	userBmkFeature : function(){
		this.hsctMap.clearAllVectorFeatures(this.hsctMap.vectorRdName);
		var layer = this.hsctMap.map.getLayersByName(this.hsctMap.vectorRdName)[0];
		var feature = new OpenLayers.Format.WKT().read( $('#'+"hid"+$('#selBookmark').val()).val() );
		feature.attributes.name = "";
		feature.geometry.transform(this.hsctMap.epsg4326, this.hsctMap.map.getProjection());
		layer.addFeatures([feature]);
		layer.redraw();
		this.hsctMap.map.zoomToExtent(feature.geometry.getBounds());
	},
	//시계열 중지
	tsPopStop : function(){		
		if(this.hsctMap.chkTsUse != null){
			clearInterval(this.hsctMap.chkTsUse);
			this.hsctMap.chkTsUse = null;
		}
	},
	CLASS_NAME : "MapCustom"
});

//다시 읽어들이기
function reloadVtLayer(datas, mapType){
	var mapObj = getMapObj(mapType);	
	if(datas.params.aid == '1-4-5'){ 
		//매출분석_외국인분석
		mapObj.clearRemoveLayer("popLayer"+datas.params.aid);
		mapObj.mapCustom.mapCstAnalsLocation(mapType,  datas.params.emdcd, datas);		
	}else if(datas.params.aid == '2-1-1'
		|| datas.params.aid == '2-2-1' 
		|| datas.params.aid == '2-3-1'
		|| datas.params.aid == '2-4-1'
		|| datas.params.aid == '2-5-1'
		|| datas.params.aid == '2-5-2'
		|| datas.params.aid == '2-7-1'
	){
		mapVtReload(mapType, datas);
	}else if(datas.params.aid == '2-6-1'){
		mapVtReload(mapType, datas);
		//setLineResult(datas.params.aid, mapType);
	}else if(datas.params.aid == 'station'){
		mapObj.clearRemoveLayer("popLayer"+datas.params.aid);
		setStaResult(mapType);
	}else{
		mapObj.clearRemoveLayer("popLayer"+datas.params.aid);
		$.ajax({
			type:"POST",
			url:datas.params.url,
			dataType:'json',
			async:true,
			data: {
				atype:datas.params.atype,
				area: datas.params.area,			
				dongcd: datas.params.dongcd,
				smonth:datas.params.smonth,
				emonth:datas.params.emonth,
				range: datas.params.range,
				coord: mapObj.map.getProjection().replace("EPSG:", ''),
				gubun: datas.params.gubun
			},			
			success:function(data){
				mapObj.addAnalsVtLayer(datas, data.geom);				
			}
		});
	}
}

//vector피쳐정보 다시 읽어들이기
function mapVtReload(mapType, datas){	
	var mapObj = getMapObj(mapType);
	var id = datas.params.aid;	
	var coord = mapObj.map.getProjection().replace("EPSG:", '');
	
	var layer = mapObj.map.getLayersByName("popLayer"+id)[0];
	if(layer != undefined && layer != null && layer != ''){
		var features = layer.features;
		mapObj.clearAllVectorFeatures("popLayer"+id); //피처정보 삭제
		//피처정보 다시 그리기		
		for(var i=0; i<features.length; i++){			
			features[i].geometry.transform(new OpenLayers.Projection("EPSG:"+features[i].attributes.coord), mapObj.map.getProjection());			
			features[i].attributes.coord = coord; 			
		}
		layer.addFeatures(features);
	}
}