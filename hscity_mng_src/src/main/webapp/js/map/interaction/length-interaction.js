function LengthInteraction() {
	var self;
	var map;
	var repeatYn = false;
	var lengthMeasureLayer;
	var lengthTextMeasureLayer;
	var lengthInteraction;
	var lengthMeasureTooltipElement;
	var lengthMeasureTooltip;
	var lengthMeasureHelpTooltipElement;
	var lengthMeasureHelpTooltip;
	var sketch;
	var listener;
	this.init = function(_map) {
		self = this;
		
		map = _map;
		// length 레이어 생성
		lengthMeasureLayer = new ol.layer.Vector({
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
		
		// length 텍스트 레이어 생성
		lengthTextMeasureLayer = new ol.layer.Vector({
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
		lengthInteraction = new ol.interaction.Draw({
			source: lengthMeasureLayer.getSource(),
			type: "LineString",
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
		
		lengthInteraction.on('drawstart', function(evt) {
			sketch = evt.feature;			
			var tooltipCoord = evt.coordinate;			
			listener = sketch.getGeometry().on('change', function(evt) {
				var geom = evt.target;
				var output = geometryLength(geom);
				tooltipCoord = geom.getLastCoordinate();
				lengthMeasureTooltipElement.innerHTML = output;
				lengthMeasureTooltip.setPosition(tooltipCoord);
	        }, this);
		});
		lengthInteraction.on('change:active', function(evt) {
			map.dispatchEvent({type:'interactionChange', name:'measureLength', status:evt.target.getActive()});
		});
		
		lengthInteraction.setActive(false);
		map.addInteraction( lengthInteraction );
		
		map.addLayer(lengthMeasureLayer);
		map.addLayer(lengthTextMeasureLayer);
		
		lengthInteraction.on('drawend', function(evt) {
			var position = lengthMeasureTooltip.getPosition();
			var text = $(lengthMeasureTooltipElement).text();
			lengthMeasureTooltipElement.className = 'gis-tooltip tooltip-static hidden';
			lengthMeasureTooltip.setOffset([0, -7]);
			sketch = null;
			lengthMeasureTooltipElement = null;
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

			lengthTextMeasureLayer.getSource().addFeature(marker);
			
			if( repeatYn == false ) {
				setTimeout(function() {
					self.deactivation();
				}, 50);					
			}
				
		}, this);
	}
	
	// 반복 여부
	this.setRepeat = function(repeat) {
		repeatYn = repeat;
	}
	
	// 활성화
	this.activation = function() {
		if( lengthMeasureHelpTooltipElement ) lengthMeasureHelpTooltipElement.classList.add('hidden');
		createMeasureTooltip();
		createMeasureHelpTooltip();
		map.addEventListener('pointermove', mouseMoveHandler);
		map.getViewport().addEventListener('mouseout', mouseOutHandler);
		lengthInteraction.setActive(true);
	}
	
	// 비활성화
	this.deactivation = function() {
		if (lengthMeasureHelpTooltipElement) {
			lengthMeasureHelpTooltipElement.classList.add('hidden');
		}
		map.removeEventListener('pointermove', mouseMoveHandler);
		map.getViewport().removeEventListener('mouseout', mouseOutHandler);
		lengthInteraction.setActive(false);
	}
	
	function createMeasureTooltip() {
		if (lengthMeasureTooltipElement) {
			lengthMeasureTooltipElement.parentNode.removeChild(lengthMeasureTooltipElement);
		}
		lengthMeasureTooltipElement = document.createElement('div');
		lengthMeasureTooltipElement.className = 'gis-tooltip tooltip-measure';
		lengthMeasureTooltip = new ol.Overlay({
			element: lengthMeasureTooltipElement,
			offset: [0, -15],
			positioning: 'bottom-center'
		});
		map.addOverlay(lengthMeasureTooltip);
	}
	
	function createMeasureHelpTooltip() {
		if (lengthMeasureHelpTooltipElement) {
			lengthMeasureHelpTooltipElement.parentNode.removeChild(lengthMeasureHelpTooltipElement);
		}
		lengthMeasureHelpTooltipElement = document.createElement('div');
		lengthMeasureHelpTooltipElement.className = 'gis-tooltip hidden';
		lengthMeasureHelpTooltip = new ol.Overlay({
			element: lengthMeasureHelpTooltipElement,
			offset: [15, 0],
			positioning: 'center-left'
		});
		map.addOverlay(lengthMeasureHelpTooltip);
	}
	
	function mouseOutHandler() {
		lengthMeasureHelpTooltipElement.classList.add('hidden');
	}
	
	function mouseMoveHandler(evt) {
		if (evt.dragging) {
			return;
		}
		var helpMsg = "그리기 시작하려면 클릭하세요.";
		if (sketch) {
			var geom = (sketch.getGeometry());
			if (geom instanceof ol.geom.LineString) {
				helpMsg = "계속해서 선을 그리려면 클릭하세요.";
			}
		}
		lengthMeasureHelpTooltipElement.innerHTML = helpMsg;
		lengthMeasureHelpTooltip.setPosition(evt.coordinate);
		lengthMeasureHelpTooltipElement.classList.remove('hidden');
	}

	
	function geometryLength(line) {
		var length = ol.sphere.getLength(line);
		var output;
		if (length > 100) {
			output = (Math.round(length / 1000 * 100) / 100) + ' ' + 'km';
		} else {
			output = (Math.round(length * 100) / 100) + ' ' + 'm';
		}
		return output;
	}
}
