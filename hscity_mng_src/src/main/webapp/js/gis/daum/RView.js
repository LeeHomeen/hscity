/**
 * Daum 로드뷰
 */
var DaumRoadView = {
	rv : null,
	rvClient : null,	
	mapObj : null,    
	daumLayer : null,
	
	init : function(mapObj){
		this.mapObj = mapObj;
		//this.mapObj.openRoadLine(); //지도상에 라인
	},
	activeOpenRoadView :function(){		
		var that = this;
		
		this.daumLayer = this.mapObj.map.getLayersByName(this.mapObj.roadViewMarkers)[0];
		if(this.daumLayer == undefined || this.daumLayer == null){
			this.daumLayer = new OpenLayers.Layer.Vector(this.mapObj.roadViewMarkers, {
				 styleMap: new OpenLayers.StyleMap({'default':{
					 externalGraphic: 'http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/roadview_wk.png',
		                graphicWidth: 35, 
		                graphicHeight: 39, 
		                graphicXOffset: -(35/2), 
		                graphicYOffset: -(39/2),
		                title: '로드뷰'
			        }}),
		        isBaseLayer: false,
		        renderers: this.mapObj.getRenderer()		       
		    });
			this.mapObj.map.addLayer(this.daumLayer);
			/*
			var oldCenter = this.mapObj.map.getCenter().clone();
			var pointMarker = new OpenLayers.Geometry.Point(oldCenter.lon,oldCenter.lat);
			*/			 
			var pointMarker = new OpenLayers.Geometry.Point(0,0);
	        var pointFeature = new OpenLayers.Feature.Vector(pointMarker);	            
			this.daumLayer.addFeatures([pointFeature]);
			
            this.mapObj.rvDragControl = new OpenLayers.Control.DragFeature(this.daumLayer, {
				  autoActivate: true,
				  onComplete: function (e) {
					  that.openRoadView(e.geometry.getCentroid());
				  }
			});
            this.mapObj.map.addControl(this.mapObj.rvDragControl);
			this.mapObj.rvDragControl.activate();			
			this.mapObj.useChkRv = true;
			this.mapObj.useMoveRv = true;
		}
	},		
	openRoadView : function(lonlat){		
		var that = this;
        try {
        	this.rv = new daum.maps.Roadview($('#roadmap')[0]); //로드뷰 객체
        	//this.rv.setViewpoint({pan: 45,tilt: 10,zoom:1});
        	this.rvClient = new daum.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
            $('#gis-map').css({cursor: 'pointer'});
            // 로드뷰의 초기화 되었을때 map walker를 생성한다.
            daum.maps.event.addListener(this.rv, 'init', function() {            	
                //var initPos = that.rv.getPosition();
                daum.maps.event.addListener(that.rv, 'position_changed', function() {
                	var changePos = that.rv.getPosition();                	
                	 var lonlat = null;
                	 lonlat = that.mapObj.mapTools.changePointCoord({lon:changePos.getLng() ,lat:changePos.getLat()}, that.mapObj.epsg4326, that.mapObj.map.getProjection());                                  	
                     that.mapObj.map.panTo(lonlat);
                     //feature 이동
                     that.daumLayer.features[0].move(lonlat);
                });
            });
            
            lonlat = this.mapObj.mapTools.changePointCoord({lon:lonlat.x, lat:lonlat.y}, this.mapObj.map.getProjection(), this.mapObj.epsg4326);
            var position = new daum.maps.LatLng(lonlat.lat, lonlat.lon);
            
            this.rvClient.getNearestPanoId(position, 50, function(panoId) {
                if (panoId === null) {                    
                    openInfoPopup(true,'로드뷰 정보를 가져올 수 없는 지역입니다.');
                } else {                	
                	//gis-map, nMap
                	$('#gis-main').css({width:'50%', height:'100%'});
                	$('#roadmap').css({width:'50%', height:'100%'});
                	$('#roadmap').show();
                	
                	that.rv.setPanoId(panoId, position); //panoId를 통한 로드뷰 실행
                	that.rv.relayout(); //로드뷰를 감싸고 있는 영역이 변경됨에 따라, 로드뷰를 재배열합니다
                	that.mapObj.mapRefresh();                		
                }
            });
            
        } catch (e) {
            console.error(e);
            if (e.message === "daum.maps.Roadview requires Flash Player 10 or above or a browser with CSS Transform 3D.") {
                // alert('이 사이트에서 flash를 허용 해줘야 합니다. \n 1. chrome 브라우저의 주소창 왼쪽 (!)느낌표 아이콘을 클릭 \n 2. flash 이 사이트에서 항상 허용.');
                $('#setupFlash').show();
            } else {                
                openInfoPopup(true,e.message);
            }
            this.deactiveOpenRoadView();
            
            return false;
        }
	},	
	deactiveOpenRoadView : function(){
		$('#roadmap').css({width:'0%', height:'100%'});
    	$('#roadmap').show();    	
		$('#gis-main').css({width:'100%', height:'100%'});
		if(this.mapObj != undefined && this.mapObj != null)
			this.mapObj.mapRefresh(); //초기화 수준
	},	
	//로드뷰 종료
	exitRoadView : function(){
		this.deactiveOpenRoadView();
		if(this.mapObj != undefined && this.mapObj != null){
			this.mapObj.clearDaumRV();
			this.mapObj.useChkRv = false;
			this.mapObj.useMoveRv = false;
		}	
	}
};