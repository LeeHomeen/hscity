/*********************************************
 * 
 * 지도조작도구 객체 생성
 * 
 ********************************************/
MapTools = OpenLayers.Class({
	hsctMap  :   null,  //지도 객체
	
	//생성자
	initialize : function(obj){
		this.hsctMap = obj;		
	},
	//기본 지도화면
	basicLocation : function(){
		var zoom = 14;
		//네이버맵일 경우
		if(this.hsctMap.chkNMap)
			zoom -= 6;
		this.hsctMap.map.setCenter(this.changePointCoord({lat :37.19953, lon:126.831477}, this.hsctMap.epsg4326, this.hsctMap.map.getProjectionObject()), zoom);
	},
	//1단계 zoom in, out
	zoomMap : function(flag){
		if(flag) //zoom in
			this.hsctMap.map.zoomTo(this.hsctMap.map.getZoom()+1);
		else   // zoom out
			this.hsctMap.map.zoomTo(this.hsctMap.map.getZoom()-1);
	},	
	//기본지도 변환 (기본 : N, 위성맵 : S)
	changeBaseMap : function(flag, chkInit){
		this.hsctMap.mapName = flag;
		this.hsctMap.map.baseLayer.setVisibility(false);
		
		var baseLayer = null;
		if(flag.indexOf("NV") > -1){
			//naver
			var zoomLevel = this.hsctMap.map.getZoom()-5;
			baseLayer = this.hsctMap.map.getLayersByName("NVVT");			
			this.hsctMap.map.setBaseLayer(baseLayer[0]);
			this.mapSyncLocaton('NV', zoomLevel);
			var chkClear = false;
			if(!this.hsctMap.chkNMap){
				chkClear = true;
				closeRoadView();
				this.hsctMap.clearMap(); //초기화
				this.hsctMap.chkNMap = true;
				//분석 vector레이어 다시 읽어오기
				this.reloadVtLayers();
			}
			if(flag == 'NVSAT')	//위성
				this.hsctMap.nMap.setMapTypeId(naver.maps.MapTypeId.SATELLITE);
			else
				this.hsctMap.nMap.setMapTypeId(naver.maps.MapTypeId.NORMAL);
			baseLayer[0].setVisibility(true);
			if(chkClear){				
				this.hsctMap.mapRefresh();
			}
			$('#nMap').show();
		}else{
			//vworld
			$('#nMap').hide();			
			var zoomLevel = this.hsctMap.map.getZoom()+6;
			baseLayer = this.hsctMap.map.getLayersByName(flag);					
			this.hsctMap.map.setBaseLayer(baseLayer[0]);
			this.mapSyncLocaton('VW', zoomLevel);
			var chkClear = false;
			if(this.hsctMap.chkNMap){
				chkClear = true;	
				closeRoadView();
				this.hsctMap.clearMap(); //초기화
				this.hsctMap.chkNMap = false;
				this.hsctMap.map.events.triggerEvent("moveend");
				this.reloadVtLayers();
			}
			baseLayer[0].setVisibility(true);
			if(chkClear){				
				this.hsctMap.mapRefresh();
			}
		}
	},
	mapSyncLocaton : function(flag, zoomLevel){
		if(flag == 'NV'){		
			if(!this.hsctMap.chkNMap){
				console.log(this.hsctMap.nMap)
				this.hsctMap.nMap.setZoom(zoomLevel, true);	
				this.hsctMap.map.zoomTo(zoomLevel-1);
			}
		}else if(flag == 'VW'){
			/*
			if(this.hsctMap.chkNMap){				
				this.hsctMap.map.zoomTo(zoomLevel);
			}
			var lonlat = this.hsctMap.map.getCenter().clone().transform(this.hsctMap.map.getProjection(), this.epsg4326);
			this.hsctMap.nMap.setCenter(new naver.maps.LatLng(lonlat.lat, lonlat.lon));
			this.hsctMap.nMap.setZoom(this.hsctMap.map.getZoom()+1, true);
			this.calNMapViewPort();
			*/
		}		
	},
	calNMapViewPort : function(){
		 var transform = this.hsctMap.nMapViewPort.css('transform').split(',');
		 this.hsctMap.nMapLeft = parseInt(transform[4], 10) || 0;
		 this.hsctMap.nMapTop  = parseInt(transform[5], 10) || 0;		 
	},
	//포인트 정보 좌표변환
	changePointCoord : function(pos, origin, dest){
		var lonlat = new OpenLayers.LonLat(pos.lon, pos.lat).transform(origin, dest);
		return lonlat;
	},	
	//분석 vector레이어 다시 읽어오기
	reloadVtLayers : function(){
		var layers = this.hsctMap.map.layers;
		var arrChgLayer = []; //변경할 벡터레이어 정보
		for(var i=0; i<layers.length; i++){
			var layer = layers[i];
			//매출 분석결과 및 교통 공통정보(정류장)
			if(layer.name.indexOf("popLayer") > -1 || 
				layer.name.indexOf("StationLayer") > -1){
				//레이어 설정 체크
				if(layer.CLASS_NAME.toUpperCase().indexOf("VECTOR") > -1 ||
						layer.id.toUpperCase().indexOf("VECTOR") > -1){
					if($('#chk'+layer.name)[0] != undefined){
						layer.attributes.params.params.visible = $('#chk'+layer.name)[0].checked;	
					}
					layer.attributes.params.params.opacity = layer.opacity;
					arrChgLayer.push(layer.attributes.params);
				}
			}
		}
		//벡터레이어 reload
		for(var i=0; i<arrChgLayer.length; i++){
			reloadVtLayer(arrChgLayer[i], this.hsctMap.mapType);
		}
	},
	CLASS_NAME : "MapTools"
});