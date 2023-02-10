var vworldTileUrl = 'https://xdworld.vworld.kr';
var vworldWmsUrl = 'http://api.vworld.kr/req/wms';
var vworldDataUrl = '/api/open/vworldData';

function VworldLayer() {
	// 기본배경 레이어
	this.baseLayer = new ol.layer.Tile({
		source: new ol.source.XYZ({
			url: vworldTileUrl+'/2d/Base/service/{z}/{x}/{y}.png',
			crossOrigin: 'anonymous'
		}),
		visible: true
	});
	
	// 영상 레이어
	this.satelliteLayer = new ol.layer.Tile({
		source: new ol.source.XYZ({
			url: vworldTileUrl+'/2d/Satellite/service/{z}/{x}/{y}.jpeg',
			crossOrigin: 'anonymous'
		}),
		visible: false
	});
	
	// 하이브리드 레이어
	this.hybridLayer = new ol.layer.Tile({
		source: new ol.source.XYZ({
			url: vworldTileUrl+'/2d/Hybrid/service/{z}/{x}/{y}.png',
			crossOrigin: 'anonymous'
		}),
		visible: false
	});
}