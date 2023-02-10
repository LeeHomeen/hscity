function RoadviewInteraction() {
	var self;
	var map;
	var roadviewElementId = "roadmap";
	var roadviewIconUrl = "/images/gis/icon/roadview_icon.png";
	var overlay;
	var roadview;
	var roadviewLayer;	
	var roadviewInteraction;
	var roadviewClient;
	this.init = function(_map) {
		self = this;
		
		map = _map;
		
		// 로드뷰 레이어 생성
		roadviewLayer = new ol.layer.Vector({
			source: new ol.source.Vector(),
			style: new ol.style.Style({
				image: new ol.style.Icon({
					anchor: [0.5, 46],
					anchorXUnits: 'fraction',
					anchorYUnits: 'pixels',
					src: roadviewIconUrl
				})
			})
		});
		map.addLayer(roadviewLayer);

		// 로드뷰 팝업 생성
		// overlay = createOverlayPopup();
		// map.addOverlay(overlay);
		roadviewClient = new kakao.maps.RoadviewClient();
		roadview = new kakao.maps.Roadview(document.getElementById(roadviewElementId)); //로드뷰 객체

		// 로드뷰 툴 생성
		roadviewInteraction = new ol.interaction.Draw({
			source: roadviewLayer.getSource(),
			type: "Point",
			style: new ol.style.Style({
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
		
		roadviewInteraction.on('drawstart', function(evt) {
			roadviewLayer.getSource().clear();
		});
		
		roadviewInteraction.on('drawend', function(evt) {
			var coordinate = evt.feature.getGeometry().getCoordinates();
			var coordinate4326 = ol.proj.transform(coordinate, 'EPSG:900913', 'EPSG:4326');
			
			var position = new kakao.maps.LatLng(coordinate4326[1], coordinate4326[0]);
			
			// 로드뷰 이벤트 설정
			roadviewClient.getNearestPanoId(position, 50, function(panoId) {
				if( panoId == null ) return;
				roadview.setPanoId(panoId, position);
				$('#gis-main').css({width:'50%', height:'100%'});
				$('#roadmap').css({width:'50%', height:'100%'});
				map.updateSize();
				// overlay.setPosition(coordinate);
			});
			
		}, this);
		
		roadviewInteraction.on('change:active', function(evt) {
			map.dispatchEvent({type:'interactionChange', name:'roadview', status:evt.target.getActive()});
		});
		
		roadviewInteraction.setActive(false);
		map.addInteraction( roadviewInteraction );
	}
	
	// 활성화
	this.activation = function() {
		// overlay.setPosition(undefined);
		roadviewInteraction.setActive(true);
	}
	
	// 비활성화
	this.deactivation = function() {
		// overlay.setPosition(undefined);
		roadviewLayer.getSource().clear();
		roadviewInteraction.setActive(false);
	}
	
	function createOverlayPopup() {
		/*
		var overlayDiv = document.createElement('div')
		overlayDiv.setAttribute("id","roadviewPopup");
		overlayDiv.setAttribute("class","ol-overlay");
		overlayDiv.innerHTML = '<a href="#" id="roadview-closer" class="ol-overlay-closer"></a><div id="'+roadviewElementId+'" style="width:400px;height:400px"></div>';
		document.body.appendChild(overlayDiv);
		
		var closer = document.getElementById('roadview-closer');
		closer.onclick = function() {
			overlay.setPosition(undefined);
			closer.blur();
			return false;
		};
		
		return new ol.Overlay({
			element: document.getElementById('roadviewPopup'),
			autoPan: true,
			html: true,
			placement: "bottom",
			autoPanAnimation: {
				duration: 250
			}
		});
		*/
	}
}
