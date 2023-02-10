function AreaInteraction() {
	var self;
	var map;
	var repeatYn = false;
	var areaMeasureLayer;
	var areaTextMeasureLayer;
	var areaInteraction;
	var areaMeasureTooltipElement;
	var areaMeasureTooltip;
	var areaMeasureHelpTooltipElement;
	var areaMeasureHelpTooltip;
	var sketch;
	var listener;
	this.init = function(_map) {
		self = this;
		
		map = _map;
		// area 레이어 생성
		areaMeasureLayer = new ol.layer.Vector({
			source: new ol.source.Vector(),
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: 'rgba(255, 255, 255, 0.2)'
				}),
				stroke: new ol.style.Stroke({
					color: '#0000ff',
					width: 2
				}),
				image: new ol.style.Circle({
					radius: 7,
					fill: new ol.style.Fill({
						color: '#0000ff'
					})
				})
			})
		});
		
		// area 텍스트 레이어 생성
		areaTextMeasureLayer = new ol.layer.Vector({
			source: new ol.source.Vector(),
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: 'rgba(255, 255, 255, 0.2)'
				}),
				stroke: new ol.style.Stroke({
					color: '#0000ff',
					width: 2
				}),
				image: new ol.style.Circle({
					radius: 7,
					fill: new ol.style.Fill({
						color: '#0000ff'
					})
				})
			})
		});
		
		// 그리기 툴 생성
		areaInteraction = new ol.interaction.Draw({
			source: areaMeasureLayer.getSource(),
			type: "Polygon",
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: 'rgba(255, 255, 255, 0.2)'
				}),
				stroke: new ol.style.Stroke({
					color: '#0000ff',
					width: 2
				}),
				image: new ol.style.Circle({
					radius: 7,
					fill: new ol.style.Fill({
						color: '#0000ff'
					})
				})
			})
		});
		
		areaInteraction.on('drawstart', function(evt) {
			var _this = this;
			sketch = evt.feature;			
			var tooltipCoord = evt.coordinate;			
			listener = sketch.getGeometry().on('change', function(evt) {
				var geom = evt.target;
				var output = geometryArea(geom);
				tooltipCoord = geom.getInteriorPoint().getCoordinates();
				areaMeasureTooltipElement.innerHTML = output;
				areaMeasureTooltip.setPosition(tooltipCoord);
	        }, this);
		});
		
		map.addLayer(areaMeasureLayer);
		map.addLayer(areaTextMeasureLayer);
		
		areaInteraction.on('drawend', function(evt) {
			var position = areaMeasureTooltip.getPosition();
			var text = $(areaMeasureTooltipElement).text();
			areaMeasureTooltipElement.className = 'gis-tooltip tooltip-static hidden';
			areaMeasureTooltip.setOffset([0, -7]);
			sketch = null;
			areaMeasureTooltipElement = null;
			createMeasureTooltip();
			ol.Observable.unByKey(listener);
			var marker = new ol.Feature({
				geometry: new ol.geom.Point(position)
			});
			marker.setStyle(
				new ol.style.Style({
					text: new ol.style.Text({
						text: text,
						offsetY: -10,
						font: '14px "Roboto", dotom, Arial, sans-serif',
						fill: new ol.style.Fill({ color: 'white' }),
						stroke: new ol.style.Stroke({ color: 'black', width: 3 })
					})
				})
			)

			areaTextMeasureLayer.getSource().addFeature(marker);
			
			if( repeatYn == false ) {
				setTimeout(function() {
					self.deactivation();
				}, 50);					
			}
		}, this);
		
		areaInteraction.on('change:active', function(evt) {
			map.dispatchEvent({type:'interactionChange', name:'measureArea', status:evt.target.getActive()});
		});
		
		areaInteraction.setActive(false);
		map.addInteraction( areaInteraction );
	}
	
	// 반복 여부
	this.setRepeat = function(repeat) {
		repeatYn = repeat;
	}
	
	// 활성화
	this.activation = function() {
		if( areaMeasureHelpTooltipElement ) areaMeasureHelpTooltipElement.classList.add('hidden');
		createMeasureTooltip();
		createMeasureHelpTooltip();
		map.addEventListener('pointermove', mouseMoveHandler);
		map.getViewport().addEventListener('mouseout', mouseOutHandler);
		areaInteraction.setActive(true);
	}
	
	// 비활성화
	this.deactivation = function() {
		if (areaMeasureHelpTooltipElement) {
			areaMeasureHelpTooltipElement.classList.add('hidden');
		}
		map.removeEventListener('pointermove', mouseMoveHandler);
		map.getViewport().removeEventListener('mouseout', mouseOutHandler);
		areaInteraction.setActive(false);
	}
	
	function createMeasureTooltip() {
		if (areaMeasureTooltipElement) {
			areaMeasureTooltipElement.parentNode.removeChild(areaMeasureTooltipElement);
		}
		areaMeasureTooltipElement = document.createElement('div');
		areaMeasureTooltipElement.className = 'gis-tooltip tooltip-measure';
		areaMeasureTooltip = new ol.Overlay({
			element: areaMeasureTooltipElement,
			offset: [0, -15],
			positioning: 'bottom-center'
		});
		map.addOverlay(areaMeasureTooltip);
	}
	
	function createMeasureHelpTooltip() {
		if (areaMeasureHelpTooltipElement) {
			areaMeasureHelpTooltipElement.parentNode.removeChild(areaMeasureHelpTooltipElement);
		}
		areaMeasureHelpTooltipElement = document.createElement('div');
		areaMeasureHelpTooltipElement.className = 'gis-tooltip hidden';
		areaMeasureHelpTooltip = new ol.Overlay({
			element: areaMeasureHelpTooltipElement,
			offset: [15, 0],
			positioning: 'center-left'
		});
		map.addOverlay(areaMeasureHelpTooltip);
	}
	
	function mouseOutHandler() {
		areaMeasureHelpTooltipElement.classList.add('hidden');
	}
	
	function mouseMoveHandler(evt) {
		if (evt.dragging) {
			return;
		}
		var helpMsg = "그리기 시작하려면 클릭하세요.";
		if (sketch) {
			var geom = (sketch.getGeometry());
			if (geom instanceof ol.geom.Polygon) {
				helpMsg = "계속해서 다각형을 그리려면 클릭하세요.";
			}
		}
		areaMeasureHelpTooltipElement.innerHTML = helpMsg;
		areaMeasureHelpTooltip.setPosition(evt.coordinate);
		areaMeasureHelpTooltipElement.classList.remove('hidden');
	}

	
	function geometryArea(polygon) {
		var area = ol.sphere.getArea(polygon);
		var output;
		if (area > 10000) {
			output = (Math.round(area / 1000000 * 100) / 100) + ' ' + 'km<sup>2</sup>';
		} else {
			output = (Math.round(area * 100) / 100) + ' ' + 'm<sup>2</sup>';
		}
		return output;
	}
}
