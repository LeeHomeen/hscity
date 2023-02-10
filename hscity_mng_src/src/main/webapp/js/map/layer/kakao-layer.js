var Daum_resolutions = [2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1, 0.5, 0.25];
var Daum_extent      = [-30000, -60000, 494288, 988576];

var DaumMapURL = config.proxyUrl+"https://map{0-3}.daumcdn.net/map_2d/2103dor/L{z}/{y}/{x}.png";
var RoadviewURL = config.proxyUrl+"https://map{0-3}.daumcdn.net/map_roadviewline/7.00/L{z}/{y}/{x}.png";
function KakaoLayer() {

	// 기본배경 레이어
	this.physicalLayer = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: DaumMapURL,
            tileSize: 256,
            projection: new ol.proj.Projection({
                code: "EPSG:5181",
                extent: Daum_extent,
                units: 'm'
            }),
            tileGrid: new ol.tilegrid.TileGrid({
                origin: [Daum_extent[0], Daum_extent[1]],
				resolutions: Daum_resolutions,
				tileSize: [256, 256]
            }),
            tileUrlFunction: function(tileCoord) {
                var s = Math.floor(Math.random() * 4);
				var _level = Daum_resolutions.length - tileCoord[0];
				return DaumMapURL.replace('{0-3}', s)
					.replace('{z}', _level.toString())
					.replace('{x}', tileCoord[1].toString())
					.replace('{y}', (-tileCoord[2]-1).toString());
            }
        }),
        visible: false
    });
    

	// 기본배경 레이어
	this.roadviewLayer = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: RoadviewURL,
            tileSize: 256,
            projection: new ol.proj.Projection({
                code: "EPSG:5181",
                extent: Daum_extent,
                units: 'm'
            }),
            tileGrid: new ol.tilegrid.TileGrid({
                origin: [Daum_extent[0], Daum_extent[1]],
				resolutions: Daum_resolutions,
				tileSize: [256, 256]
            }),
            tileUrlFunction: function(tileCoord) {
                var s = Math.floor(Math.random() * 4);
				var _level = Daum_resolutions.length - tileCoord[0];
				return RoadviewURL.replace('{0-3}', s)
					.replace('{z}', _level.toString())
					.replace('{x}', tileCoord[1].toString())
					.replace('{y}', (-tileCoord[2]-1).toString());
            }
        }),
        visible: false
    });
}
