/*********************************************
 * 
 * 지도 객체 생성
 * 
 ********************************************/
HsctMap = OpenLayers.Class({
	map             :   null,  //Openlayers map객체	
	mapTools        :   null,
	mapEvents       :   null,
	mapCustom       :   null,
	mapType         :   null,  //맵타입 pop, traffic	
	mapName         :   "VWST",  //VWST, VWSAT, NVST, NVSAT
	mapMenu         :   null,
	//naver 관련
	nMap            :   null,  //naver map
	nMapViewPort    :   null,  //naver viewport
	nMapLeft        :   null,
	nMapTop         :   null,
	chkNMap         :   false, //naver map 여부
	
	//control
	controls        :   null,  //일반 컨트롤
	measureControls :   null,  //측정 컨트롤
	measureVtName     :  'MeasureLayer', //거리구하기 vecotr명	
	drawRadiusPoints  :  [],  //다각형 그리기시 points정보 저장
	
	drawControls    :   null,  //draw 컨트롤
	vectorRdName    :  'RadiusLayer', //다각형, 원형 반경 vector명
	//다은 로드뷰 레이어
	roadViewMarkers :  'DaumRvLaer',
	rvDragControl   :   null,  //다음로드뷰 드래그 컨트럴
	useChkRv        :   false,  //로드뷰 사용중 체크
	useMoveRv       :   false,  //로드뷰 mousemove 이동
	
	//highlight layer
	highlightName   :  'HighlightLayer',	
    
	epsg4326        :   new OpenLayers.Projection("EPSG:4326"), //4236좌표정보
	epsg900913      :   new OpenLayers.Projection("EPSG:900913"), //900913좌표정보	
	epsg5179        :   new OpenLayers.Projection("EPSG:5179"), //5179좌표정보\
	
	//인구분석 행정동 정보 제공
	popAreaSearch   :   false,  //현재 행정동 정보 조회중인지 체크 (분석시 true로 설정되어 있어야 조회 가능)
	popAreaPoly     :   null,   //행정동  polygon wkt 정보
	
	//분석결과 레이어 정보 저장 map (map tools 분석결과 레이어용)
	chkTsUse        :  null,
	wmsLayerInfos   :  null,
	
	stationName : "StationLayer",  //교통 공통 레이어 (정류장)
	
	//시계열 체크  (화면이 로딩중인지 체크)	
	chkLyrLoading : false, //현재 레이어 로딩중 체크
		
	initialize : function(mapType){
		this.mapType = mapType; //맵타입 저장
		this.wmsLayerInfos = new Map();
	},	
	initHsctMap : function(div, options){		
		//기본 옵션 생성
		var mapOptions = {
			mapType : this.mapType, //지도이벤트시 this객체를 맵으로 판단하여 추가				
			autoUpdateSize : false,			
			controls : [],			
			maxExtent: new OpenLayers.Bounds(-20037508.34, -20037508.34, 20037508.34, 20037508.34),
		    eventListeners : {
		    	"move" : this.mapEvent,
	            "moveend": this.mapEvent,
	            "zoomstart" : this.mapEvent,
	            "zoomend": this.mapEvent,	            
	            "click" :  this.mapEvent,
	            "dblclick" : this.mapEvent,
	            "mousemove" : this.mapEvent,
	            "mousedown" : this.mapEvent,
	            "changelayer": this.mapLayerChanged,
	            "changebaselayer": this.mapBaseLayerChanged,
	            "featureclick" : this.mapEvent
	        }
		};
		
		var newOption = null;
		if(options){
			var nControl = mapOptions.controls.slice().concat(options.controls);
			newOption =  OpenLayers.Util.extend(mapOptions, options );
			newOption.controls = nControl;
		}else{
			newOption = mapOptions;
		}		
		this.map = new OpenLayers.Map(div, newOption);		
		//base map 추가		 
		this.addVWorldBaseMap('Street', false);
		this.addVWorldBaseMap('Satellite', false);		
		this.addVWorldBaseMap('Hybrid', false);
		
		// 브이월드 레이어 추가
//		this.addAnalsGeoserverLayer('hscity:hm_bnd_adm_dong_pg_a', "1<>1", false);
		
		//반경관련 (다각형, 원형)벡터레이어 추가
		this.addDrawRadiusVector(); //반경검색용 (선택) 반경, 다각형 vector		
		
		this.mapEvents = new MapEvents(this); //지도 이벤트 설정
		this.mapTools =  new MapTools(this); //지도 툴 설정	
		this.mapCustom = new MapCustom(this); //지도관련 custom 기능
		this.addBasicControl();				
		this.mapTools.basicLocation();
		
		// this.addNaverBaseMap('Normal'); //네이버
	},	
	mapRefresh : function(){
		if(this.chkNMap){
			this.map.updateSize();
			this.nMap._resizeMapListener.listener();			
			//네이버와 맵레이어 사이의 이격문제로 우선 임시처리 로직			
			this.map.setCenter(this.nMap.getCenter());
			this.map.zoomOut();
			this.map.zoomIn();	
			this.map.events.triggerEvent("move");			
		}else{			
			this.map.updateSize();		
		}
		//base layer만 한번더 update
		this.map.baseLayer.redraw();
	},
	addNaverBaseMap : function(){
		var that = this;
		if(this.nMap == null){
			if ($('#nMap').length === 0) {
				$('.olMap').parent().append($('<div id="nMap" class="custom-map">'));				
		     }
			this.nMap = new naver.maps.Map('nMap');
			this.nMap.setMapTypeId(naver.maps.MapTypeId.NORMAL);
			this.nMap.setOptions({ //지도 인터랙션 끄기
	            draggable: false,
	            pinchZoom: false,
	            scrollWheel: false,
	            keyboardShortcuts: false,
	            disableDoubleTapZoom: true,
	            disableDoubleClickZoom: true,
	            disableTwoFingerTapZoom: true,
	            disableKineticPan : true,
	            tileTransition : false
	        });
			naver.maps.Event.addListener(this.nMap, 'zoom_changed', function(zoom){				
				that.allWMSLayerShow(true);
				that.allVectorLayerShow(true);
				that.allPopupsShow(true);				
			});
			this.nMapViewPort = $('#nMap div:first-child').eq(2);
			
			var nVector = new OpenLayers.Layer.Vector('NVVT', {				
				numZoomLevels: 14,
			    minResolution: 0.25,
			    maxResolution: 2048,
			    units : "m",
			    resolutions: [2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1, 0.5, 0.25],
			    maxExtent: new OpenLayers.Bounds(90112, 1192896, 1990673, 2761664),
			    projection: this.epsg5179,
			    isBaseLayer: true
		    });			
			this.map.addLayer(nVector);
			$('#nMap').css({position:"absolute"});
		}
	},
	addVWorldBaseMap : function (gbn, view){
		var layer = null;
		if(gbn == 'Hybrid')
			layer = new OpenLayers.Layer.VWorldHybrid();
		else if(gbn == 'Satellite')
			layer = new OpenLayers.Layer.VWorldSatellite();
		else if(gbn == 'Street')
			layer = new OpenLayers.Layer.VWorldStreet();		
		layer.setVisibility(view);
		this.map.addLayer(layer);
	},
	//분석용 wms layer 추가
	addAnalsGeoserverLayer : function(layer, filter, view){
		var params = {
			LAYERS: layer,
			format: 'image/png',
			tiled: true,
			transparent: true
		}
		if( filter != '' ) params.CQL_FILTER = filter;
		var layer = new OpenLayers.Layer.WMS(
			"sample",
			"http://210.103.137.243:8088/geoserver/wms",
			params
		);
		layer.type = "geoserver";	
		layer.setVisibility(view);
		this.map.addLayer(layer);
	},
	//사용자지정 관련 벡터 레이어
	addDrawRadiusVector : function(){
		var styleMap = new OpenLayers.StyleMap({'default':{  
            strokeColor: "#99004C",
            strokeOpacity: 0.8,
            strokeWidth: 2,
            strokeDashstyle: 'dash', //(“dot”, “dash”, “dashdot”, “longdash”, “longdashdot”            
            fillColor: "white",		            
            fillOpacity: 0.3,
            fillWidth: "${fillWidth}",
            label : "${name}",	            
            fontColor: "${favColor}",
            fontSize: "${fontSize}",
            fontFamily: "Courier New, monospace",
            fontWeight: "bold",
            labelAlign: "${align}",
            labelXOffset: "${xOffset}",
            labelYOffset: "${yOffset}",
            labelOutlineColor: "black",
            labelOutlineWidth: 4
        },
        'select': {
        	strokeColor: "#99004C",
            strokeOpacity: 0.8,
            strokeWidth: 2,
            strokeDashstyle: 'dash', //(“dot”, “dash”, “dashdot”, “longdash”, “longdashdot”
            fillColor: "#F2CB61",		            
            fillOpacity: 0.2 
        }
        });
		var vectorLayer = new OpenLayers.Layer.Vector(this.vectorRdName, {
	        styleMap: styleMap,
	        isBaseLayer: false,
	        renderers: this.getRenderer()	       	
	    });
		this.map.addLayer(vectorLayer);
	},
	//분석용 wms layer 추가
	addAnalsWMSLayer : function(datas){
		var that = this;
		var layer = this.map.getLayersByName(datas.layerName)[0];
		if(layer == undefined || layer == null){		
			layer = new OpenLayers.Layer.WMS(datas.layerName, 
					datas.analsUrl,	
			    {		
					layers:datas.layerName,
					transparent: true,				
					isBaseLayer: false,
					makeTheUrlLong: new Array(205).join("1234567890")			
				},
			    {	
					ratio: 1,
					singleTile: true,
					transitionEffect: 'resize'
				}
			);	
			layer.events.register("loadstart", layer, function() {
				console.log("loadstart");
				 $('#divLoadingBar').show();
				 that.chkLyrLoading = true;
	        });
			layer.events.register("loadend", layer, function() {
				console.log("loadend");
				 $('#divLoadingBar').hide();
				 //시계열 정보 체크하여 연속적인 view처리
				 that.chkLyrLoading = false;
	        });		
			layer.attributes = {};
			layer.attributes.params = datas.params;
			this.chkAnalsWMSLayer(layer, datas.mapType);		
			this.map.addLayer(layer);		
			this.wmsLayerInfos.put(datas.layerName, datas.analsName);
		}else{
			layer.setVisibility(true);
			layer.setOpacity(1);
			layer.attributes.params = datas.params;
			this.chkAnalsWMSOne(datas.layerName, datas.mapType);
		}
	},
	//공통 vector layer생성
	addCommVtLayer : function(params){
		var layer = this.map.getLayersByName(params.layerName)[0];
		if(layer == undefined || layer == null){		
			layer = new OpenLayers.Layer.Vector(params.layerName, {
				//해당스타일은 거리 측정시 라인모양 및 라벨 스타일 지정
				//“dot”, “dash”, “dashdot”, “longdash”, “longdashdot”, or “solid”
		        styleMap: new OpenLayers.StyleMap({
		        	"default": { 
			            strokeColor: "${stkColor}",
			            strokeOpacity: 0.6,
			            strokeWidth: "${stkWidth}",
			            strokeDashstyle: "${stkStyle}",
			            fillOpacity: "${fllOpacity}",
			            pointRadius: "${radius}",
			            fillColor: "${fllColor}",
			            pointRadius: "${radius}",
			            fillWidth: "${fllWidth}",
			            label : "${name}",      
		            	labelAlign: "${align}",
		                labelXOffset: "${xOffset}",
		                labelYOffset: "${yOffset}",
			            fontColor: "${favColor}",
			            fontSize: "${fontSize}",
			        }
		        }),
		        renderers: this.getRenderer()
		    });		
			
			layer.events.on({
                'featureselected': function(obj) {
                	setFeatureLabel(obj); //traffic.js
                },
                'featureunselected': function(obj) {
                    
                }
            });    
	        
			layer.attributes = {};
			layer.attributes.params = params;
			this.map.addLayer(layer);
			
			if(params.analsName != undefined && params.analsName != '')
				this.wmsLayerInfos.put(params.layerName, params.analsName);	//지도툴기능중 레이어조절기능에 필요
			
			//select feature허용 여부 true시 활성화
			if(params.chksel){
				var selectFeature = new OpenLayers.Control.SelectFeature(
	                layer, 
	                {
	                    clickout: true, toggle: false,
	                    multiple: false, hover: false,
	                    toggleKey: "ctrlKey", // ctrl key removes from selection
	                    multipleKey: "shiftKey", // shift key adds to selection
	                    box: false
	                }
		        );
				selectFeature.id = "SELFEATURE";	
				this.map.addControl(selectFeature);
		        selectFeature.activate();	
			}
		}
		return layer;
	},
	//전입-전출
	addAnalsVtLayer : function(params, data){
		var layer = this.map.getLayersByName(params.layerName)[0];
		if(layer == undefined || layer == null){		
			layer = new OpenLayers.Layer.Vector(params.layerName, {
				//해당스타일은 거리 측정시 라인모양 및 라벨 스타일 지정
		        styleMap: new OpenLayers.StyleMap({
		        	"default": { 
			            strokeColor: "${stkColor}",
			            strokeOpacity: 0.6,
			            strokeWidth: "${stkWidth}",			            
			            fillOpacity: 0.5,
			            pointRadius: "${radius}",
			            fillColor: "white",
			            fillWidth: "2",			            
			            label : "${name}",	            
			            fontColor: "${favColor}",
			            fontSize: "${fontSize}",
			        }
		        }),
		        renderers: this.getRenderer()
		    });
			layer.attributes = {};
			layer.attributes.params = params;
			this.map.addLayer(layer);
			this.wmsLayerInfos.put(params.layerName, params.analsName);	//지도툴기능중 레이어조절기능에 필요		
		}
		layer.setVisibility(params.params.visible);
		layer.setOpacity(params.params.opacity);		
		//max && list 2개 조회		
		if(data.length > 1){
			var lndParams = {};
			var lines = [];
			var points = [];
			var max = 0;
			var div = 0;
			var color = params.params.colpck; //default	
			for(var i=0; i<data.length; i++){				
				if(i == 0){
					max = data[i].cnt;
					min = data[i].min;
					div = Math.floor(max / 10);
					//범례정보 표시
					lndParams.max = max;
					lndParams.min = min;
					lndParams.vtype = 'round';
					lndParams.vcolor = color;
					showLegendInfo(lndParams); //범례구성
					var id = params.params.aid;
					if(id == '1-1-3')
						id = "1-1-3-1"; 
					else if(id == '1-1-4')
						id = "1-1-4-1";
					setLndGridParams("tblGrid"+id, lndParams); //범례 param임시저장
					continue;
				}
				if(i == 31)	break;
				
				var line = data[i].line;
				var bpoint = data[i].bpoint;
				var lineGeom = null;
				var pointGeom = null;
				var cnt = data[i].cnt;				
				var rsize = 0; //radius size
				var stkWidth = 0;
				if(cnt < div){
					rsize = 1;
					stkWidth = 1;
				}else if(cnt < (div * 2)){
					rsize = 4;
					stkWidth = 2;
				}else if(cnt < (div * 3)){
					rsize = 8;
					stkWidth = 3;
				}else if(cnt < (div * 4)){
					rsize = 12;
					stkWidth = 4;
				}else if(cnt < (div * 5)){
					rsize = 16;
					stkWidth = 5;
				}else if(cnt < (div * 6)){
					rsize = 20;
					stkWidth = 6;
				}else if(cnt < (div * 7)){
					rsize = 24;
					stkWidth = 7;
				}else if(cnt < (div * 8)){
					rsize = 28;
					stkWidth = 8;
				}else if(cnt < (div * 9)){
					rsize = 32;
					stkWidth = 9;
				}else{
					rsize = 36;
					stkWidth = 10;
				}
				if(line != undefined && line != null){
					lineGeom = new OpenLayers.Format.WKT().read(line);
					if(lineGeom.attributes == undefined || lineGeom.attributes == null)
						lineGeom.attributes = {};
					lineGeom.attributes.name = "";
					lineGeom.attributes.stkColor = color;
					lineGeom.attributes.stkWidth = stkWidth;
					lines.push(lineGeom);
				}
				if(bpoint != undefined && bpoint != null){
					pointGeom = new OpenLayers.Format.WKT().read(bpoint);					
					pointGeom.attributes.fontSize = 10;
					pointGeom.attributes.favColor = "black";
					pointGeom.attributes.stkColor = color;					
					pointGeom.attributes.radius = (20 + rsize);
					if(cnt != undefined && cnt != null && cnt != '')
						cnt = Number(cnt).toLocaleString('en');
					pointGeom.attributes.name = data[i].item+"\n("+cnt+")";
					points.push(pointGeom);
				}				
			}
			if(lines.length > 0)
				layer.addFeatures(lines);
			if(points.length > 0)
				layer.addFeatures(points);
		}
	},
	getRenderer : function(){
		var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
		renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;
		return renderer;
	},
	addBasicControl: function(){
		this.controls = [
            new OpenLayers.Control.Navigation({ 
            	id : "pan",
            	dragPanOptions: {
                    enableKinetic: true
                },
                wheelUp : function(evt){
                	getMapObj(this.map.mapType).mapTools.zoomMap(true);
                },
                wheelDown : function(evt){
                	getMapObj(this.map.mapType).mapTools.zoomMap(false);
                }
            }), //이동          
            new OpenLayers.Control.NavigationHistory({
            	id : "navigationHistory"
            }),
            new OpenLayers.Control.ScaleLine({ id : "scaleline" }),
            new OpenLayers.Control.Attribution()
		];		
		//map에 컨트롤 추가
		for(var i=0; i < this.controls.length; i++) {
			this.map.addControl(this.controls[i]);
		}
		
		/////////////////////////////////////////////////////////////
		/////////////////// 측정도구 컨트롤 ///////////////////////////////
		/////////////////////////////////////////////////////////////
		
		var styleLineMap = new OpenLayers.StyleMap({
	        "default": { 
	            strokeColor: "red",
	            strokeOpacity: 0.5,
	            strokeWidth: 5,
	            fillOpacity: 0.8
	        }
		});
		var stylePolyMap = new OpenLayers.StyleMap({
	        "default": { 
	            strokeColor: "#003399",
	            strokeOpacity: 0.4,
	            strokeWidth: 4,
	            fillColor: "white",		            
	            fillOpacity: 0.5
	        }
		});
		
		//측정도구 컨트롤
		this.measureControls = {
            line: new OpenLayers.Control.Measure(
                OpenLayers.Handler.Path, {
                    persist: true,
                    handlerOptions: {
                        layerOptions: {
                            renderers: this.getRenderer(),
                            styleMap: styleLineMap
                        }
                    },
                    callbacks : {
                		"done" : this.mapEvents.distLineFinish
                	}
                }
            ),
            area: new OpenLayers.Control.Measure(
                OpenLayers.Handler.Polygon, {
                    persist: true,
                    handlerOptions: {
                        layerOptions: {
                            renderers: this.getRenderer(),
                            styleMap: stylePolyMap
                        }
                    },
                    callbacks : {
                		"done" : this.mapEvents.distPolygonFinish,
                		"point" : this.mapEvents.handleAreaMeasurements
                	}
                }
            )
         };	
		
		//측정도구 컨트롤 추가
		for(var key in this.measureControls) {
			var control = this.measureControls[key];
			control.events.on({
				"measure": this.mapEvents.handleLineMeasurements,
                "measurepartial": this.mapEvents.handleLineMeasurements
			});
			this.map.addControl(control);
		}
		
		//////////////////////////////////////////////////////////////////////////
		//////////////////// 사용자 영역지정 컨트롤 ///////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////
		
		this.drawControls = { //다각형 반경그리기		    
            polygon: new OpenLayers.Control.DrawFeature(this.map.getLayersByName(this.vectorRdName)[0], OpenLayers.Handler.Polygon,{
            	callbacks : {
            		"done"  : this.mapEvents.radiusPolygonFinish,
            		"point" : this.mapEvents.handleUsrAreaDraw
            	}
            }),
            point: new OpenLayers.Control.DrawFeature(this.map.getLayersByName(this.vectorRdName)[0], OpenLayers.Handler.Point,{
            	callbacks : {
            		"done"  : this.mapEvents.radiusPointFinish
            	}
            })            
        };
		for(var key in this.drawControls) {
            this.map.addControl(this.drawControls[key]);
        }
	},
	//거리구하기 컨트롤 활성화
	activeControl : function(flag, key){
		if(flag == 'ms'){ //거리 측정시 지도상의 피쳐정보 삭제			
			this.clearVectorFeaturesById(this.measureVtName, key);
			this.clearPopupsById(key);
		}else if(flag == 'ds') //사용자 지정영역
			this.clearAllVectorFeatures(this.vectorRdName);
		
		this.clearControl(flag);		
		if(flag == 'ms'){ //ms:measure 측정컨트롤
			for(controlId in this.measureControls) {
				var control = this.measureControls[controlId];
	            if(controlId == key) {
	            	control.activate();
	            }else{
	                control.deactivate();
	            }
			}
		}else if(flag == 'ds'){ //ds : drawControl 반경 컨트롤
			for(controlId in this.drawControls) {
				var control = this.drawControls[controlId];
		        if(controlId == key) {		        	
		        	control.activate();
		        }else{
		            control.deactivate();
		        }
		    }	
		}
	},
	//컨트롤 비활성화
	clearControl : function(flag){
		if(flag == 'ms' || flag == 'all'){  //ms:measure 측정컨트롤
			for(key in this.measureControls) {
				var control = this.measureControls[key];
				if(control != undefined && control != null)
					control.deactivate();       
			}	
		}else if(flag == 'ds' || flag == 'all'){
			for(key in this.drawControls) {
				var control = this.drawControls[key];
				if(control != undefined && control != null)
					control.deactivate();       
			}	
		}
	},
	//해당 vector레이어의 전체 피쳐정보 삭제
	clearAllVectorFeatures : function(layerName){
		var layer = this.map.getLayersByName(layerName);
		if(layer.length > 0){
			layer = layer[0];
			layer.removeFeatures(layer.features);
			layer.redraw();
		}
	},	
	//feature id별 삭제
	clearVectorFeaturesById : function (layerName, id){
		var layer = this.map.getLayersByName(layerName)[0];
		if(layer != undefined && layer != null){		
			var cnt = layer.features.length;
			var removeFeatures = [];
			for(var i=0; i<cnt; i++){
				var feature = layer.features[i];
				if(feature.attributes.type == id)
					removeFeatures.push(feature);
			}	
			if(removeFeatures.length >  0)
				layer.removeFeatures(removeFeatures);
		}
	},
	//판업 전체 초기화
	clearAllPoups : function(){
		var cnt = this.map.popups.length;
		for(var i=0; i<cnt; i++){
			var popup = this.map.popups[i];
			if(popup != undefined && popup != null){
				this.map.removePopup(popup);
			}else{
				this.clearAllPoups(); //다시 재 삭제
				break;
			}
		}
	},
	//popup id별 삭제
	clearPopupsById : function(id){
		var cnt = this.map.popups.length;
		var removePopups = [];
		for(var i=0; i<cnt; i++){
			var popup = this.map.popups[i];
			if(popup.id.indexOf(id) > -1){				
				removePopups.push(popup);
			}
		}
		if(removePopups.length > 0){
			for(var i=0; i<removePopups.length; i++){
				this.map.removePopup(removePopups[i]);
			}
		}
	},
	//전체 벡터레이어 visible 설정  (벡터 + 팝업)	
	allVectorLayerShow : function(show){
		var layers = this.map.layers;
		var cnt = layers.length;
		for(var i=0; i<cnt; i++){
			var layer = layers[i];
			if(layer.CLASS_NAME.toUpperCase().indexOf("VECTOR") > -1 ||
					layer.id.toUpperCase().indexOf("VECTOR") > -1){
				if(layer.name.indexOf("nNmMap") <= -1){
					if($('#chk'+layer.name)[0] != undefined){
						if(show)
							layer.setVisibility($('#chk'+layer.name)[0].checked);
						else
							layer.setVisibility(show);
					}else
						layer.setVisibility(show);
				}
			}	
		}		
	},
	//전체 wms layer visible 설정
	allWMSLayerShow : function(show){
		var layers = this.map.layers;
		var cnt = layers.length;
		for(var i=0; i<cnt; i++){
			var layer = layers[i];
			if(layer.CLASS_NAME.toUpperCase().indexOf("WMS") > -1){
				if(show){
					//분석레이어 결과 체크
					if($('#chk'+layer.name)[0] != undefined)
						layer.setVisibility($('#chk'+layer.name)[0].checked);
					else
						layer.setVisibility(show);
				}else				
					layer.setVisibility(show);
			}	
		}		
	},
	//팝업 show 여부
	allPopupsShow : function(show){
		var popups = this.map.popups;
		var cnt = popups.length;
		for(var i=0; i<cnt; i++){
			var popup = popups[i];
			if(show)
				popup.show();
			else
				popup.hide();
		}
	},
	//wms 레이어 개별 삭제
	clearRemoveLayer : function(layerName){		
		var layer = this.map.getLayersByName(layerName)[0];
		if(layer != undefined && layer != null){		
			this.map.removeLayer(layer);
			this.wmsLayerInfos.remove(layerName);	
		}
	},
	//id별 control정보 제거
	clearRemoveControl : function(id){
		var control = this.map.getControl(id);
		if(control != undefined && control != null)
			this.map.removeControl(control);
	},
	//wms, vt 분석레이어 제거
	clearAllAnalsLayers : function(){
		var layers = this.map.layers;
		var cnt = layers.length;
		for(var i=0; i<cnt; i++){
			var layer = layers[i];
			if(layer.CLASS_NAME.toUpperCase().indexOf("WMS") > -1){
				this.map.removeLayer(layer);
				i = 0;
				cnt = layers.length;				
			}else{
				//vt중에서는 popLayer~
				if(layer.name.indexOf("popLayer") > -1 ||
					layer.name == 'HighlightLayer' ||
					layer.name == 'StationLayer'
				){
					this.map.removeLayer(layer);
					i = 0;
					cnt = layers.length;
				}
			}
		}
	},
	//지도 초기화
	clearMap : function(){
		this.clearControl("all");
		this.clearAllPoups();
		
		var layers = this.map.layers;
		var cnt = layers.length;
		for(var i=0; i<cnt; i++){
			var layer = layers[i];
			if(layer.CLASS_NAME.toUpperCase().indexOf("VECTOR") > -1 ||
					layer.id.toUpperCase().indexOf("VECTOR") > -1){
				if(layer.name.indexOf("nNmMap") <= -1 
						&& 
					layer.name.indexOf(this.roadViewMarkers) <= -1
						&&
						layer.name.indexOf("popLayer") <= -1
				){					
					this.clearAllVectorFeatures(layer.name);
				}
			}	
		}
	},
	//다음 로드맵 관련 초기화
	clearDaumRV: function(){
		var daumLayer = this.map.getLayersByName(this.roadViewMarkers)[0];
		if(daumLayer != null){
			this.map.removeLayer(daumLayer);
			if(this.rvDragControl != null)
				this.map.removeControl(this.rvDragControl);
		}
	},	
	//줌레벨 제한
	mapLimitZoom : function(mapObj){
		var zoom = 0;	
		var maxZoom = (mapObj.map.getNumZoomLevels()+1); //v:19, n:14
		if(mapObj.chkNMap){
			zoom = mapObj.map.getZoom() + 1;
			maxZoom += 1;
		}else{
			zoom = mapObj.map.getZoom() - 4;
			maxZoom -= 4;
		}
		if(zoom > 1){
			var loc = ((maxZoom-zoom) * 10) + "px";			
			$('#toolSliser').css({'top': loc});
		}else{			
			zoom = 2;
		}		
		return zoom;
	},
	//지도 이벤트
	mapEvent : function(evt, obj){		
		var mapObj = getMapObj(this.mapType);
		if(evt.type == 'moveend'){
			 var zoom = mapObj.mapLimitZoom(mapObj);
			 var lonlat = mapObj.map.getCenter().clone().transform(mapObj.map.getProjection(), mapObj.epsg4326);			 
			 if(mapObj.chkNMap){
				mapObj.nMap.setCenter(new naver.maps.LatLng(lonlat.lat, lonlat.lon));				
				mapObj.nMap.setZoom(zoom, true);
                var left = parseInt(mapObj.nMapViewPort.css('left'), 10);
                var top = parseInt(mapObj.nMapViewPort.css('top'), 10);
                if (left || top) {
                	mapObj.nMapViewPort.css('transform', '');
                }                
			}else{
				if (mapObj.nMap) {
					mapObj.nMap.setCenter(new naver.maps.LatLng(lonlat.lat, lonlat.lon));
					mapObj.nMap.setZoom((mapObj.map.getZoom()+1) - mapObj.map.getMinZoom(), true);
                }				
			}
			 
			//wms params update (조회완료된 분석wms레이어 params update)
			mapObj.chkAnalsWMSLayers(this.mapType);				
		}else if(evt.type == 'mousemove'){
			//로드뷰일 경우
			if(mapObj.useChkRv && mapObj.useMoveRv){	
				evt.xy.x = evt.xy.x + 0;
				evt.xy.y = evt.xy.y - 25;
				DaumRoadView.daumLayer.features[0].move(evt.xy);								
				DaumRoadView.daumLayer.redraw();
			}
		}else if(evt.type == 'move'){
			if(mapObj.chkNMap){
				 var $oMapViewPort = $(mapObj.map.viewPortDiv.firstChild);
				 mapObj.nMapLeft = mapObj.nMapLeft || 0;
				 mapObj.nMapTop = mapObj.nMapTop || 0;
				 var left = parseInt($oMapViewPort.css('left'), 10) - parseInt(mapObj.nMapViewPort.css('left'), 10) || 0 + mapObj.nMapLeft;				 
	             var top =  parseInt($oMapViewPort.css('top'), 10) - parseInt(mapObj.nMapViewPort.css('top'), 10) || 0 + mapObj.nMapTop;	             
	             mapObj.nMapViewPort.css('transform', OpenLayers.String.format('matrix(1, 0, 0, 1, '+left+','+top+')'));
			}
		}else if(evt.type == 'zoomstart'){			
			if(mapObj.chkNMap){				
				mapObj.nMapLeft = 0;
				mapObj.nMapTop = 0;
				mapObj.allWMSLayerShow(false);
				mapObj.allVectorLayerShow(false);				
				mapObj.allPopupsShow(false);
			}
		}else if(evt.type == 'zoomend'){
			if(mapObj.chkNMap){				
				mapObj.nMapLeft = 0;
				mapObj.nMapTop = 0;
			}		
		}else if(evt.type == 'click'){
			//지도 클릭시 로드뷰일경우만 체크하여 사용
			if(mapObj.useChkRv){
				mapObj.useMoveRv = false;
				var geom = mapObj.map.getLonLatFromPixel(evt.xy);
				var lonlat = new OpenLayers.Geometry.Point(geom.lon, geom.lat);	
				DaumRoadView.openRoadView(lonlat);
			}
		}
	},
	//포인트 지점의 라벨정보 출력
	pointLineLabel : function(pos, label){		
		var vectorLayer = this.map.getLayersByName(this.measureVtName);		
		if(vectorLayer == undefined || vectorLayer == null || vectorLayer.length == 0){		
			vectorLayer = new OpenLayers.Layer.Vector(this.measureVtName, {
				//해당스타일은 거리 측정시 라인모양 및 라벨 스타일 지정
		        styleMap: new OpenLayers.StyleMap({'default':{
		        	strokeColor: "${strokeColor}",
		            strokeOpacity: 0.8,
		            strokeWidth: 2,		            
		            graphicName: "square",
		            pointRadius: "${pointRadius}",
		            fillColor: "white",
		            fillOpacity: "0.5",
		            fillWidth: "${fillWidth}",
		            label : "${name}",	            
		            fontColor: "${favColor}",
		            fontSize: "${fontSize}",
		            fontFamily: "Courier New, monospace",
		            fontWeight: "bold",
		            labelAlign: "${align}",
		            labelXOffset: "${xOffset}",
		            labelYOffset: "${yOffset}",
		            labelOutlineColor: "black",
		            labelOutlineWidth: 4
		        }}),
		        renderers: this.getRenderer()
		    });	
		}else{
			vectorLayer = vectorLayer[0];
		}
		var point = new OpenLayers.Geometry.Point(pos.lon, pos.lat);
		var pointFeature = new OpenLayers.Feature.Vector(point);
		pointFeature.attributes = {
				name: label,
				favColor: 'white',
				align: "cm",
				fillOpacity: 0.7,
				fillColor: "white",
				pointRadius : 4,
				fillWidth: 3,
				fontSize : "12px",
				xOffset: 25,
				yOffset: -05,
				type : "line",
				strokeColor : "red"
		};    
		this.map.addLayer(vectorLayer);
		vectorLayer.addFeatures([pointFeature]);
	},
	pointAreaLabel : function(pos, label, layerName){		
		var vectorLayer = this.map.getLayersByName(layerName);		
		if(vectorLayer == undefined || vectorLayer == null || vectorLayer.length == 0){		
			vectorLayer = new OpenLayers.Layer.Vector(layerName, {
				//해당스타일은 거리 측정시 라인모양 및 라벨 스타일 지정
		        styleMap: new OpenLayers.StyleMap({'default':{		        	
		            strokeColor: "${strokeColor}",
		            strokeOpacity: 0.8,
		            strokeWidth: 2,
		            fillColor: "white",
		            fillOpacity: "0.5",
		            graphicName: "square",
		            pointRadius: "${pointRadius}",		            
		            fillWidth: "{fillWidth}",
		            label : "${name}",	            
		            fontColor: "${favColor}",
		            fontSize: "{fontSize}",
		            fontFamily: "Courier New, monospace",
		            fontWeight: "bold",
		            labelAlign: "${align}",
		            labelXOffset: "${xOffset}",
		            labelYOffset: "${yOffset}",
		            labelOutlineColor: "black",
		            labelOutlineWidth: 4
		        }}),
		        renderers: this.getRenderer()
		    });	
		}else{
			vectorLayer = vectorLayer[0];
		}
		var point = new OpenLayers.Geometry.Point(pos.lon, pos.lat);
		var pointFeature = new OpenLayers.Feature.Vector(point);
		pointFeature.attributes = {
				name: label,
				favColor: 'white',
				align: "cm",
				xOffset: 25,
				fontSize : "13px",
				fillOpacity: 0.2,
				fillWidth: 3,
				fillColor: "#B2CCFF",
				pointRadius : 0,
				yOffset: -05,
				type : "area",
				strokeColor : "red"
		};    
		this.map.addLayer(vectorLayer);
		vectorLayer.addFeatures([pointFeature]);
	},	
	//분석 wms레이어 업데이트 (하나의 분석레이어 대상 : 기등록된 레이어)
	chkAnalsWMSOne : function(layerName, mapType){
		console.log("chkAnalsWMSOne");
		var mapObj = getMapObj(mapType);
		var wmsLayer = mapObj.map.getLayersByName(layerName)[0];
		if(wmsLayer != undefined && wmsLayer != null){
			var params = mapObj.mergeParams(mapType, wmsLayer.attributes.params);			
			wmsLayer.mergeNewParams(params);
			//wmsLayer.redraw();
		}			
	},
	//레이어 단위 업데이트 (최초 등록전 세팅)
	chkAnalsWMSLayer : function(layer, mapType){
		console.log("chkAnalsWMSLayer");
		var mapObj = getMapObj(mapType);
		var params = mapObj.mergeParams(mapType,layer.attributes.params);		
		layer.mergeNewParams(params);
	},
	//기본 params과 분석정보 merge
	mergeParams : function(mapType, layerParams){
		var mapObj = getMapObj(mapType);
		var params = mapObj.updateWMSParams(mapType);
		if(layerParams != undefined){
			var dongcd = layerParams.dongcd;
			if(dongcd != undefined && dongcd != null)
				params.dongcd = dongcd;
			var atype = layerParams.atype;
			if(atype != undefined && atype != null)
				params.atype = atype;
			var area = layerParams.area;
			if(area != undefined && area != null)
				params.area = area;
			var vtype = layerParams.vtype;
			if(vtype != undefined && vtype != null)
				params.vtype = vtype;	
			var vcolor = layerParams.vcolor;
			if(vcolor != undefined && vcolor != null)
				params.vcolor = vcolor;
			var smonth = layerParams.smonth;
			if(smonth != undefined && smonth != null)
				params.smonth = smonth;
			var emonth = layerParams.emonth;
			if(emonth != undefined && emonth != null)
				params.emonth = emonth;
			var sage = layerParams.sage;
			if(sage != undefined && sage != null)
				params.sage = sage;			
			var eage = layerParams.eage;
			if(eage != undefined && eage != null)
				params.eage = eage;
			var period =  layerParams.period;
			if(period != undefined && period != null)
				params.period = period;
			var syear = layerParams.syear;
			if(syear != undefined && syear != null)
				params.syear = syear;
			var gyear = layerParams.gyear;   //시계열을 위한 그리드 첫번째 row 기간정보
			if(gyear != undefined && gyear != null)
				params.gyear = gyear;		
			var sday = layerParams.sday;
			if(sday != undefined && sday != null)
				params.sday = sday;		
			var eday = layerParams.eday;
			if(eday != undefined && eday != null)
				params.eday = eday;
			var gubun = layerParams.gubun;
			if(gubun != undefined && gubun != null)
				params.gubun = gubun;
			var std = layerParams.std;
			if(std != undefined && std != null)
				params.std = std;
			//성연령별
			var age = layerParams.age; //성연령별 연령
			if(age != undefined && age != null)
				params.age = age;
			var smember = layerParams.smember;
			if(smember != undefined && smember != null)
				params.smember = smember;
			var emember = layerParams.emember;
			if(emember != undefined && emember != null)
				params.emember = emember;
			var gender = layerParams.gender;
			if(gender != undefined && gender != null)
				params.gender = gender;
			var mgender = layerParams.mgender;
			if(mgender != undefined && mgender != null)
				params.mgender = mgender;
			var wgender = layerParams.wgender;
			if(wgender != undefined && wgender != null)
				params.wgender = wgender;	
			//범례 최대값
			var max = layerParams.max;
			if(max != undefined && max != null)
				params.max = max;
			var min = layerParams.min;
			if(min != undefined && min != null)
				params.min = min;
			else
				params.min = 0;
			//유동인구 시간대
			var time = layerParams.time;
			if(time != undefined && time != null)
				params.time = time;
		}
		return params;
	},
	//분석 레이어 전체 찾아 params교체 
	chkAnalsWMSLayers : function(mapType){
		console.log("chkAnalsWMSLayers")
		//wms레이어 전체를 찾아 교체
		var mapObj = getMapObj(mapType);
		var layers = mapObj.map.layers;
		var cnt = layers.length;
		for(var i=0; i<cnt; i++){
			var layer = layers[i];
			if(layer.CLASS_NAME.toUpperCase().indexOf("WMS") > -1 && layer.type != "geoserver"){
				var params = mapObj.mergeParams(mapType, layer.attributes.params);				
				layer.mergeNewParams(params);
			}	
		}
	},
	//wms params udpate
	updateWMSParams : function(mapType){
		var mapObj = getMapObj(mapType);		
		var extents = mapObj.map.getExtent();		
		var minObj = mapObj.mapTools.changePointCoord({lon:extents.left, lat:extents.bottom}, mapObj.map.getProjection(), mapObj.epsg4326);
		var maxObj = mapObj.mapTools.changePointCoord({lon:extents.right, lat:extents.top}, mapObj.map.getProjection(), mapObj.epsg4326);
		
		var params = {
			"SCREENX" : mapObj.map.size.w,
			"SCREENY" : mapObj.map.size.h,
			"RESOLUTION" : mapObj.map.resolution,
			"COORD": mapObj.map.getProjection().replace("EPSG:", ''),
			"MAXX" : mapObj.map.getExtent().right,
			"MAXY" : mapObj.map.getExtent().top,
			"MINX" : mapObj.map.getExtent().left,
			"MINY" : mapObj.map.getExtent().bottom,
			"ZOOM" : mapObj.map.getZoom(),
			//4326 변환 영역
			"CMAXX" : maxObj.lon,  
			"CMAXY" : maxObj.lat,
			"CMINX" : minObj.lon,
			"CMINY" : minObj.lat
		};
		return params;
	},
	CLASS_NAME : "HsctMap"
});

//현재 화면상의 map객체 가져오기
function getMapObj(mapType){
	if(mapType == 'pop')
		return popMap;
	else if(mapType == 'traffic')
		return trafficMap;
	else 
		return null;
}

//highlight layer 생성
/*
 * 교통 vt 레이어 만들기
 *  params, 기존 레이어 피처 삭제여부, selectFeature허용여부
 */
function setHighlightLayer(params, chkDel, chkSel){	
	var mapObj = getMapObj(params.mapType);
	if(chkDel == undefined){ 
		chkDel = true;
	}
	if(chkDel){
		mapObj.clearRemoveLayer(params.layerName);
	}
	params.chksel = chkSel;
	var layer = mapObj.addCommVtLayer(params);
	return layer;
}