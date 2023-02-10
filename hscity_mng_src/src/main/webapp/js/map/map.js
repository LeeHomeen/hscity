
proj4.defs("EPSG:4326", "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_def");    
proj4.defs("EPSG:5181", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
ol.proj.proj4.register(proj4)

var defaultCenter = [14126669.41589247, 4493404.190498611];	// 최초 extend
var defaultZoomLevel = '10';	// 최초 zoom	
var map;
var mapElementId;
var addressLayer, analysisLayer, flowLayer;
var vworldLayer, kakaoLayer;
var interactionList = [];	// 인터렉션 목록
var roadviewInteraction;	// 로드뷰 유틸
var lengthInteraction;	// 거리 계산유틸
var areaInteraction;	// 면적 계산유틸
var polygonInteraction, pointInteraction;
var self;

function Map() {
	self = this;
	this.updateInteractionEvent;
	this.extentChange;
}

Map.prototype.setElementId = function(elementId) {
	mapElementId = elementId;
}

Map.prototype.setDefaultCenter = function(center) {
	defaultCenter = center;
}

Map.prototype.setDefaultZoomLevel = function(zoomLevel) {
	defaultZoomLevel = zoomLevel;
}
// 지도 초기화
Map.prototype.init = function() {
	
	// 브이월드 레이어
	vworldLayer = new VworldLayer();
	
	// 카카오 레이어
	kakaoLayer = new KakaoLayer();
	var t = new ol.proj.Projection({
		code: "EPSG:3857",
		units: "m",
		axisOrientation: "neu"
	});
	
	map = new ol.Map({
		target: mapElementId,
		layers: [
			vworldLayer.baseLayer,
			vworldLayer.satelliteLayer,
			vworldLayer.hybridLayer,
			kakaoLayer.physicalLayer,
			kakaoLayer.roadviewLayer
		],
		controls : ol.control.defaults({
	        zoom : false,
	        attribution : false
	    }),
        overlays: [],
	    view: new ol.View({
			projection: t,
			center: defaultCenter,
			zoom: defaultZoomLevel,
			minZoom: 7,
			maxZoom: 19
		})
	});
	
	// 인터렉션 변경 이벤트
	map.on("interactionChange", function(evt) {
		if( self.updateInteractionEvent ) self.updateInteractionEvent.call(this, evt);
	})
	
	// address 레이어
	addressLayer = new ol.layer.Vector({
		name: "addressLayer",
		source: new ol.source.Vector(),
		style: new ol.style.Style({
			image: new ol.style.Icon({
				scale : 1,
				src: '/images/cmm/map-pin.png',
				anchor: [0.5, 0.5],
				anchorXUnits: 'fraction',
				anchorYUnits: 'fraction'
			})
		})
	});
	map.addLayer(addressLayer);
	
	analysisLayer = new ol.layer.Vector({
		name: "analysisLayer",
		source: new ol.source.Vector(),
		style: new ol.style.Style({
			fill: new ol.style.Fill({
				color: 'rgba(255, 255, 255, 0)',
			}),
			stroke: new ol.style.Stroke({
				color: '#999999',
				lineDash: [4,8],
				width: 2,
			})
		})
	});
	map.addLayer(analysisLayer);
	
	polygonInteraction = new ol.interaction.Draw({
		source: analysisLayer.getSource(),
		type: "Polygon",
		style: new ol.style.Style({
			fill: new ol.style.Fill({
				color: 'rgba(255, 255, 255, 0.2)'
			}),
			stroke: new ol.style.Stroke({
				color: '#0099ff',
				width: 2
			}),
			image: new ol.style.Circle({
				radius: 7,
				fill: new ol.style.Fill({color: '#0099ff'}),
				stroke: new ol.style.Stroke({
					color: [255,255,255],
					width: 2
				})
			})
		})
	});
	polygonInteraction.on('drawstart', function(evt) {
		analysisLayer.getSource().clear();
	});
	polygonInteraction.on('drawend', function(evt) {
		setTimeout(function() {
			evt.feature.set("type", "user");
			polygonInteraction.setActive(false);
		}, 100)
	});
	polygonInteraction.setActive(false);
	map.addInteraction( polygonInteraction );

	pointInteraction = new ol.interaction.Draw({
		source: analysisLayer.getSource(),
		type: "Point",
		style: new ol.style.Style({
			fill: new ol.style.Fill({
				color: 'rgba(255, 255, 255, 0.2)'
			}),
			stroke: new ol.style.Stroke({
				color: '#0099ff',
				width: 2
			}),
			image: new ol.style.Circle({
				radius: 7,
				fill: new ol.style.Fill({color: '#0099ff'}),
				stroke: new ol.style.Stroke({
					color: [255,255,255],
					width: 2
				})
			})
		})
	});
	pointInteraction.on('drawstart', function(evt) {
		analysisLayer.getSource().clear();
	});
	pointInteraction.on('drawend', function(evt) {
		
		self.drawCircle(evt.feature.getGeometry().getCoordinates(), $("#selUsrRadius").val() );
		self.zoomToLayer("analysisLayer");
		
		setTimeout(function() {
			pointInteraction.setActive(false);			
		}, 100)
	});
	pointInteraction.setActive(false);
	map.addInteraction( pointInteraction );
	
	var zoomslider = new ol.control.ZoomSlider();
	map.addControl(zoomslider);
	
	// 로드뷰 tool
	roadviewInteraction = new RoadviewInteraction();
	roadviewInteraction.init(map);
	interactionList.push(roadviewInteraction);
	
	// 거리계산 tool
	lengthInteraction = new LengthInteraction();
	lengthInteraction.init(map);
	lengthInteraction.setRepeat(false);
	interactionList.push(lengthInteraction);
	
	// 면적계산 tool
	areaInteraction = new AreaInteraction();
	areaInteraction.init(map);
	areaInteraction.setRepeat(false);
	interactionList.push(areaInteraction);
}

// 지도 리사이즈
Map.prototype.mapRefresh = function() {
	map.updateSize();
}

// 줌 인
Map.prototype.zoomIn = function() {
	map.getView().animate({zoom: map.getView().getZoom() + 1, duration:150});
}

// 줌 아웃
Map.prototype.zoomOut = function() {
	map.getView().animate({zoom: map.getView().getZoom() - 1, duration:150});
}

//폴리곤 인터렉트 토글
Map.prototype.togglePolygonInteraction = function(activation) {
	this.removeAllInteraction();
	polygonInteraction.setActive(activation);
}

//포인트 인터렉트 토글
Map.prototype.togglePointInteraction = function(activation) {
	this.removeAllInteraction();
	pointInteraction.setActive(activation);
}

//로드뷰 토글
Map.prototype.toggleRoadViewInteraction = function(activation) {
	this.removeAllInteraction();
	kakaoLayer.roadviewLayer.setVisible(activation);
	
	if( activation ) roadviewInteraction.activation();
	else roadviewInteraction.deactivation();
}

// 거리 측정 토글
Map.prototype.toggleLengthInteraction = function(activation) {
	this.removeAllInteraction();
	if( activation ) lengthInteraction.activation();
	else lengthInteraction.deactivation();
}

// 면적 측정 토글
Map.prototype.toggleAreaInteraction = function(activation) {
	this.removeAllInteraction();
	if( activation ) areaInteraction.activation();
	else areaInteraction.deactivation();
}

// Interaction 비활성화
Map.prototype.removeAllInteraction = function() {
	interactionList.forEach(function (interaction) {
		interaction.deactivation();
	});
	polygonInteraction.setActive(false);
	pointInteraction.setActive(false);
}

// 원 그리기
Map.prototype.drawCircle = function(point, radius) {
	var layer = this.getLayer("analysisLayer");
	
	var circleCoords = createCirclePointCoords(point[0],point[1], $("#selUsrRadius").val(),36);
	var geom = new ol.geom.Polygon([circleCoords]);
	var circleFeature = new ol.Feature(geom);
	circleFeature.set("type", "user");
	layer.getSource().addFeatures([circleFeature]);
	
	// 원 그리기
	function createCirclePointCoords(circleCenterX,circleCenterY,circleRadius,pointsToFind){
		circleRadius = circleRadius * 1.25;
		var angleToAdd = 360/pointsToFind;
		var coords = [];  
		var angle = 0;
		for (var i=0;i<pointsToFind;i++){
			angle = angle+angleToAdd;
			var coordX = circleCenterX + circleRadius * Math.cos(angle*Math.PI/180);
			var coordY = circleCenterY + circleRadius * Math.sin(angle*Math.PI/180);
			coords.push([coordX,coordY]);
		}
		coords.push(coords[0]);
		return coords;
	}
}

Map.prototype.getProjection = function() {
	return map.getView().getProjection().getCode();
}

Map.prototype.getWktFromGeometry = function(geometry, srid) {
	var format = new ol.format.WKT();
	var tempGeometry = geometry.clone();
	if( srid ) {
		tempGeometry.transform('EPSG:3857', srid);
	}
	return format.writeGeometry(tempGeometry);
}

Map.prototype.getWktToGeometry = function(wkt, srid) {
	var format = new ol.format.WKT();
	var feature = format.readFeature(wkt);
	if( srid ) {
		feature.getGeometry().transform(srid, 'EPSG:3857');
	}
	return feature;
}

// 배경지도 토글
Map.prototype.toggleBaseLayer = function(layerName) {
	vworldLayer.baseLayer.setVisible(layerName == "base");
	vworldLayer.satelliteLayer.setVisible(layerName == "hybrid");
	vworldLayer.hybridLayer.setVisible(layerName == "hybrid");
	kakaoLayer.physicalLayer.setVisible(layerName == "kakao");
}

// 지도 내보내기
Map.prototype.exportMapImage = function(saveFileName) {
	if( saveFileName == undefined ) saveFileName = 'screenshot';
	var today = new Date();
	var filename = today.getTime();
	map.once('postcompose', function(event) {
		var canvas = document.getElementsByTagName("canvas")[0];
		if (navigator.msSaveBlob) {
			navigator.msSaveBlob(canvas.msToBlob(), saveFileName+'_'+filename+'.png');
		} else {
			canvas.toBlob(function(blob) {
				saveAs(blob, saveFileName+'_'+filename+'.png');
			});
		}
	});
	map.renderSync();
}

// 좌표로 이동
Map.prototype.zoomToPoint = function(x, y, level, srid) {
	var center = [x, y];
	if( srid != undefined ) {
		center = ol.proj.transform(center,srid,'EPSG:3857')
	}
	map.getView().setCenter(center);
	map.getView().setZoom(level);
}
Map.prototype.zoomToLayer = function(layerName) {
	var layer = this.getLayer(layerName);
	if( layer.getSource().getFeatures().length == 0 ) return;
	map.getView().fit(layer.getSource().getExtent());
}
// 레이어 선택
Map.prototype.getLayer = function(layerName) {
	var selectedLayer;
	map.getLayers().forEach(function (layer) {
		if( layer.get("name") == layerName)
			selectedLayer = layer;
	});
	return selectedLayer;
};
// 레이어 visible 설정
Map.prototype.setVisibleLayer = function(layerName, visible) {
	var layer = this.getLayer(layerName);
	if( layer ) layer.setVisible(visible);
}

Map.prototype.addAnalsPopWmsLayer = function(datas) {
	var viewParams = "analsDate:" + datas.params.syear;
	viewParams += ";admiCd:" + datas.params.dongcd.split(",").join("_");
	viewParams += ";stdYm:" + datas.params.smonth.split("-").join("");
	viewParams += ";area:" + datas.params.area.split(",").join("_").split(" ").join("-");
	viewParams += ";gender:" + datas.params.wmsGender;
	viewParams += ";year:" + datas.params.wmsYear;
	viewParams += ";sage:" + datas.params.wmsSage;
	viewParams += ";eage:" + datas.params.wmsEage;
	viewParams += ";smember:" + datas.params.wmsSmember;
	viewParams += ";emember:" + datas.params.wmsEmember;
	console.log("viewParams", viewParams);
	var layerName = "hscity:" + datas.params.wmsUrl + "_" + datas.params.atype + "_" + datas.params.vtype;
	console.log("layerName", layerName);
	
	var layerStyle = "hscity_" + datas.params.vtype + "_" + datas.params.vcolor;
	if( datas.params.vtype != "heat" )
		layerStyle = "hscity_grid_" + datas.params.vcolor;

	console.log("layerStyle", layerStyle);
	var layer = this.getLayer("geoserverLayer");
	if( layer == null ) {
		var url = "http://210.103.137.243:8088/geoserver/hscity/wms";
		var source = new ol.source.ImageWMS({
			url: url,
			params: {
				"SERVICE": 'WMS',
		        "REQUEST": 'GetMap',
		        "VERSION": '1.1.0',
		        "FORMAT": 'image/png',
		        "TRANSPARENT": true,
		        "VIEWPARAMS": viewParams,
				"LAYERS": layerName,
				"STYLES": layerStyle
			},
			ratio: 1,
			serverType: 'geoserver',
			crossOrigin: 'anonymous'
		});
		var layer = new ol.layer.Image({
			id: "geoserverLayer",
			name: "geoserverLayer",
			visible: true,
			source: source
		});
	    map.addLayer( layer );
	}
	layer.attributes = datas;
	layer.getSource().updateParams({
		"VIEWPARAMS": viewParams,
		"LAYERS": layerName,
		"STYLES": layerStyle
	});
	layer.setVisible(true);
}

Map.prototype.addAnalsfPopWmsLayer = function(datas) {
	console.log("datas", datas);
	
	var viewParams = "";
	
	if (datas.params.age != null)
		viewParams += "age:" + datas.params.age.split(",").join("_");
	
	if (datas.params.time != null)
		viewParams += "timeslot:" + datas.params.time.split(",").join("_");
		
	if( datas.params.atype == "area" ) {
		viewParams += ";admiCd:" + datas.params.dongcd.split(",").join("_");
	}
	else {
		viewParams += ";area:" + datas.params.area.split(",").join("_").split(" ").join("-");
	}
	
	if( datas.params.std == "month" ) {
		viewParams += ";smonth:" + datas.params.smonth.split("-").join("");
		viewParams += ";emonth:" + datas.params.emonth.split("-").join("");
	}
	else {
		viewParams += ";smonth:" + datas.params.sday.split("-").join("").substring(0,6);
		viewParams += ";emonth:" + datas.params.eday.split("-").join("").substring(0,6);
		viewParams += ";sday:" + datas.params.sday.split("-").join("");
		viewParams += ";eday:" + datas.params.eday.split("-").join("");
	};
	console.log("viewParams", viewParams);
	var layerName = "hscity:" + datas.params.wmsUrl + "_area_" + datas.params.vtype;
	console.log("layerName", layerName);
	
	var layerStyle = "hscity_" + datas.params.vtype + "_" + datas.params.vcolor;
	if( datas.params.vtype != "heat" )
		layerStyle = "hscity_grid_" + datas.params.vcolor;

	console.log("layerStyle", layerStyle);
	var layer = this.getLayer("geoserverLayer");
	if( layer == null ) {
		var url = "http://210.103.137.243:8088/geoserver/hscity/wms";
		var source = new ol.source.ImageWMS({
			url: url,
			params: {
				"SERVICE": 'WMS',
		        "REQUEST": 'GetMap',
		        "VERSION": '1.1.0',
		        "FORMAT": 'image/png',
		        "TRANSPARENT": true,
		        "VIEWPARAMS": viewParams,
				"LAYERS": layerName,
				"STYLES": layerStyle
			},
			ratio: 1,
			serverType: 'geoserver',
			crossOrigin: 'anonymous'
		});
		var layer = new ol.layer.Image({
			id: "geoserverLayer",
			name: "geoserverLayer",
			visible: true,
			source: source
		});
	    map.addLayer( layer );
	}
	layer.attributes = datas;
	layer.getSource().updateParams({
		"VIEWPARAMS": viewParams,
		"LAYERS": layerName,
		"STYLES": layerStyle
	});
	layer.setVisible(true);
}

// 전입 전출 그리기
Map.prototype.addAnalsVtLayer = function(params, data) {
	var layer = this.getLayer("flowLayer");
	if( layer == null ) {
		layer = new ol.layer.Vector({
			name: "flowLayer",
			source: new ol.source.Vector()
		});
		layer.attributes = {};
		layer.attributes.params = params;
		map.addLayer(layer);
	}
	layer.getSource().clear();
	layer.setVisible(params.params.visible);
	layer.setOpacity(params.params.opacity);
	
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
				console.log("style", color, stkWidth);
				lineGeom = this.getWktToGeometry(line);
				lineGeom.setStyle(new ol.style.Style({
					stroke: new ol.style.Stroke({
						color: color,
						width: stkWidth,
					})
				}));
				lines.push(lineGeom);
			}
			if(bpoint != undefined && bpoint != null){
				pointGeom = this.getWktToGeometry(bpoint);
				if(cnt != undefined && cnt != null && cnt != '')
					cnt = Number(cnt).toLocaleString('en');
				pointGeom.setStyle(new ol.style.Style({
					stroke: new ol.style.Stroke({
						color: color,
						width: stkWidth,
					}),
					text: new ol.style.Text({
			            font: '14px dotum',
			            text: cnt,
			            fill: new ol.style.Fill({ color: 'black' }),
			            stroke: new ol.style.Stroke({ color: 'white', width: 1 })
			        }),
					image: new ol.style.Circle({
						radius: (20 + rsize),
						fill: new ol.style.Fill({color: 'rgba(255,255,255,0.4)'}),
						stroke: new ol.style.Stroke({
							color: color,
							width: 4
						})
					})
				}));
				//pointGeom.attributes.fontSize = 10;
				//pointGeom.attributes.favColor = "black";
				//pointGeom.attributes.stkColor = color;					
				//pointGeom.attributes.radius = (20 + rsize);
				//if(cnt != undefined && cnt != null && cnt != '')
				//	cnt = Number(cnt).toLocaleString('en');
				//pointGeom.attributes.name = data[i].item+"\n("+cnt+")";
				points.push(pointGeom);
			}
		}
		if(lines.length > 0)
			layer.getSource().addFeatures(lines);
		if(points.length > 0)
			layer.getSource().addFeatures(points);
	}
}

// feature 조회
Map.prototype.getFeatures = function(layerName) {
	var layer = this.getLayer(layerName);
	return layer.getSource().getFeatures();
}

Map.prototype.createPoint = function(x, y, srid) {
	var center = [x, y];
	if( srid != undefined ) {
		center = ol.proj.transform(center,srid,'EPSG:3857')
	}
	return new ol.Feature({
		geometry: new ol.geom.Point(center)
	});
}

Map.prototype.clearRemoveLayer = function() {
	this.removeAll();
	this.removeAllInteraction();
}
// feature 삭제
Map.prototype.removeAll = function() {
	map.getLayers().forEach(function (layer) {
		if( layer instanceof ol.layer.Vector ) {
			layer.getSource().forEachFeature(function(f){
				var overlay = f.get("overlay");
				if(overlay){
					map.removeOverlay(overlay);
					$(overlay.element).remove();
				}
			});
			layer.getSource().clear();
		}
	});
	var layer = this.getLayer("geoserverLayer");
	if( layer ) layer.setVisible(false);
}