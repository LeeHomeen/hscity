/* Copyright (c) 2006-2012 by OpenLayers Contributors (see authors.txt for
 * full list of contributors). Published under the 2-clause BSD license.
 * See license.txt in the OpenLayers distribution or repository for the
 * full text of the license. */

/**
 * @requires OpenLayers/Layer/XYZ.js
 */

OpenLayers.Layer.VWorldSatellite = OpenLayers.Class(OpenLayers.Layer.XYZ, {
	name : "VWorld Satellite Map",
	url : [
		"${vworldHost}/2d/Satellite/${version}/${z}/${x}/${y}.jpeg"
	],
    vworldHost: 'http://xdworld.vworld.kr:8080',
	version: '201612',
	resolutions : [
		156543.0339, 78271.51695, 39135.758475, 19567.8792375, 9783.93961875,
		4891.969809375, 2445.9849046875, 1222.99245234375, 611.496226171875, 305.7481130859375,
		152.87405654296876, 76.43702827148438, 38.21851413574219, 19.109257067871095, 9.554628533935547,
		4.777314266967774, 2.388657133483887, 1.1943285667419434, 0.5971642833709717
	],
	attribution: '<a target="_blank" href="http://map.vworld.kr/" '
		+ 'style="float: left; width: 353px; height: 29px; cursor: pointer; background-image: url(http://map.vworld.kr/images/maps/logo_openplatform.png); background-repeat: no-repeat no-repeat; " '
		+ 'title="VWorld 지도로 보시려면 클릭하세요."></a>',
	sphericalMercator : true,
	numZoomLevels : 19,
	maxResolution : 156543.0339,
	minResolution : 0.5971642833709717,
	units : "m",
	projection : new OpenLayers.Projection("EPSG:900913"),
	// maxExtent : new OpenLayers.Bounds(-20037508.34,-20037508.34, 20037508.34, 20037508.34),
	initialize : function(name, options) {
		this.version = options.version || this.version;
        this.vworldHost = options.vworldHost || this.vworldHost;
		for(var i = 0, len = this.url.length; i < len; i++) {
			this.url[i] = OpenLayers.String.format(this.url[i], {version: this.version, vworldHost: this.vworldHost, x: '${x}', y: '${y}', z: '${z}'});
		}

		if (!options)
			options = {
				resolutions : this.resolutions
			};
		else if (!options.resolutions)
			options.resolutions = this.resolutions;
		var newArgs = [ name, null, options ];
		OpenLayers.Layer.XYZ.prototype.initialize.apply(this, newArgs);
	},
	clone : function(obj) {
		if (obj == null) {
			obj = new OpenLayers.Layer.VWorldSatellite(this.name, this.getOptions());
		}
		obj = OpenLayers.Layer.XYZ.prototype.clone.apply(this, [ obj ]);
		return obj;
	},

	CLASS_NAME : "OpenLayers.Layer.VWorldSatellite"
});
